package com.shoppingcartassignmentsystemclient.presentation

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoppingcartassignmentsystemclient.domain.model.Product
import com.shoppingcartassignmentsystemclient.domain.use_cases.DeleteProductUseCase
import com.shoppingcartassignmentsystemclient.domain.use_cases.GetsProductUseCase
import com.shoppingcartassignmentsystemclient.domain.use_cases.SaveProductUseCase
import com.shoppingcartassignmentsystemclient.domain.use_cases.SearchProductUseCase
import com.shoppingcartassignmentsystemclient.domain.use_cases.UpdateProductUseCase
import com.shoppingcartassignmentsystemclient.presentation.cardScreen.CartState
import com.shoppingcartassignmentsystemclient.data.remote.dto.CartRequest
import com.shoppingcartassignmentsystemclient.data.remote.dto.CartResponse
import com.shoppingcartassignmentsystemclient.domain.model.Cart
import com.shoppingcartassignmentsystemclient.domain.use_cases.SaveCartUseCase
import com.shoppingcartassignmentsystemclient.domain.use_cases.SendCartToServerUseCase
import com.shoppingcartassignmentsystemclient.presentation.cardScreen.CartLimitState
import com.shoppingcartassignmentsystemclient.presentation.productScreen.ProductState
import com.shoppingcartassignmentsystemclient.presentation.productScreen.UIEvent
import com.shoppingcartassignmentsystemclient.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val deleteProduct: DeleteProductUseCase,
    private val updateProduct: UpdateProductUseCase,
    private val saveProduct: SaveProductUseCase,
    private val getProducts: GetsProductUseCase,
    private val searchProduct: SearchProductUseCase,
    private val saveCart: SaveCartUseCase,
    private val sendCartDataToServer: SendCartToServerUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _products = MutableStateFlow(ProductState())
    val products = _products.asStateFlow()

    private val _cartProducts = MutableStateFlow(CartState())
    val cartProducts = _cartProducts.asStateFlow()

    private val _totalPrice = MutableStateFlow(0.0)
    val totalPrice: StateFlow<Double> get() = _totalPrice

    private val _cardLimitState = MutableStateFlow(CartLimitState())
    val cardLimitState = _cardLimitState.asStateFlow()


    private var searchJob: Job? = null

    init {
        getProducts()
    }


    fun onEvent(event: UIEvent) {
        when (event) {
            is UIEvent.DeleteProduct -> deleteProduct(event.product)
            is UIEvent.UpdateProduct -> updateProduct(event.product)
            is UIEvent.SaveProduct -> saveProduct(event.product)
            is UIEvent.SearchProduct -> searchProducts(event.query)
            is UIEvent.AddProductToCard -> addCartProduct(event.product)
            is UIEvent.DeleteProductFromCard -> deleteProductFromCart(event.product)
            is UIEvent.DeleteAllProductsFromCard -> deleteAllProductsFromCard()
            is UIEvent.UpdateCardLimit -> updateCardLimit(event.limit)
            is UIEvent.SendCartDataToServer -> sendCartDataToServer(
                event.cartData,
                event.serverIp,
                event.serverPort
            )
            is UIEvent.ShowToast -> showToast(event.message)
            is UIEvent.UpdateProductQuantity -> updateProductQuantity(event.product)
        }
    }

    private fun updateProductQuantity(product: Product) {
        val currentState = _cartProducts.value
        val updatedMap = currentState.productsWithQuantity.toMutableMap()

        if (updatedMap.containsKey(product)) {
            val currentQuantity = updatedMap.getValue(product)
            if (currentQuantity > 1) {
                updatedMap[product] = currentQuantity - 1
            } else {
                updatedMap.remove(product)
            }
        }

        _cartProducts.value = currentState.copy(productsWithQuantity = updatedMap)
        calculateTotalPrice()

    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun sendCartDataToServer(cartData: CartRequest, serverIp: String, serverPort: String) {
        Log.d("cart data", cartData.toString())
        Log.d("server ip", serverIp)
        Log.d("server port", serverPort)
        viewModelScope.launch(Dispatchers.IO) {
            sendCartDataToServer.invoke(cartData, serverIp, serverPort).collect { result ->
                Log.d("result", result.toString())
                when (result) {
                    is Resource.Error -> Log.d(result.message,"Error")
                    is Resource.Loading -> Log.d(result.message,"Loading")
                    is Resource.Success -> processServerResponse(result.data ?: CartResponse(emptyList()))
                }
            }
        }
    }

    private suspend fun processServerResponse(response: CartResponse) {
        Log.d("response", response.toString())

        val currentDateTime =
            DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(Date())
        val soldProducts = response.cart.filter { it.isSold }

        for (product in soldProducts) {

            val productPrice = getProductPrice(product.id)

            val cartItem = Cart(
                productId = product.id,
                price = productPrice,
                cartDateTime = currentDateTime
            )
            saveCart(cartItem)
        }
    }

    private fun getProductPrice(id: Long): Double {
        val product = _products.value.products.find { it.id == id }
        return product?.price ?: 0.0
    }

    private fun updateCardLimit(newLimit: Double) {
        _cardLimitState.value = _cardLimitState.value.copy(cartLimit = newLimit)
    }

    private fun calculateTotalPrice() {
        val total = _cartProducts.value.productsWithQuantity.entries.sumOf { (product, quantity) ->
            product.price * quantity
        }
        _totalPrice.value = total
    }

    private fun deleteAllProductsFromCard() {
        _cartProducts.value = _cartProducts.value.copy(productsWithQuantity = mutableMapOf())
        calculateTotalPrice()
    }

    private fun deleteProductFromCart(product: Product) {
        val currentState = _cartProducts.value
        val updatedMap = currentState.productsWithQuantity.toMutableMap()

        if (updatedMap.containsKey(product)) {
            val currentQuantity = updatedMap.getValue(product)
            if (currentQuantity > 1) {
                updatedMap[product] = currentQuantity - 1
            } else {
                updatedMap.remove(product)
            }
            _cartProducts.value = currentState.copy(productsWithQuantity = updatedMap)
            calculateTotalPrice()
        }
    }

    private fun addCartProduct(product: Product) {
        val currentState = _cartProducts.value
        val updatedMap = currentState.productsWithQuantity.toMutableMap()

        if (updatedMap.containsKey(product)) {
            updatedMap[product] = updatedMap.getValue(product) + 1
        } else {
            updatedMap[product] = 1
        }

        _cartProducts.value = currentState.copy(productsWithQuantity = updatedMap)
        calculateTotalPrice()
    }

    private fun searchProducts(query: String) {
        searchJob?.cancel()
        viewModelScope.launch(Dispatchers.IO) {
            searchJob = launch {
                if (query.isBlank()) {
                    getProducts.invoke().collect { products ->
                        _products.value = _products.value.copy(
                            products = products,
                            isLoading = false,
                            error = null
                        )
                    }
                } else {
                    _products.value = _products.value.copy(
                        products = searchProduct.invoke(query),
                        isLoading = false,
                        error = null
                    )
                }
            }
        }
    }

    private fun getProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            getProducts.invoke().collect { products ->
                _products.value = _products.value.copy(
                    products = products,
                    isLoading = false,
                    error = null
                )
            }
        }
    }

    private fun updateProduct(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            updateProduct.invoke(product)
            calculateTotalPrice()
        }
    }

    private fun deleteProduct(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteProduct.invoke(product)
            deleteProductFromCartAfterDeletion(product)
            calculateTotalPrice()
        }
    }

    private fun deleteProductFromCartAfterDeletion(product: Product) {
        val currentState = _cartProducts.value
        val updatedMap = currentState.productsWithQuantity.toMutableMap()

        if (updatedMap.containsKey(product)) {
            updatedMap.remove(product)
        }

        _cartProducts.value = currentState.copy(productsWithQuantity = updatedMap)
    }

    private fun saveProduct(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            saveProduct.invoke(product)
            calculateTotalPrice()
        }
    }
}