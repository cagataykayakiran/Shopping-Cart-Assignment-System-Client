package com.shoppingcartassignmentsystemclient.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoppingcartassignmentsystemclient.domain.model.Product
import com.shoppingcartassignmentsystemclient.domain.use_cases.DeleteProductUseCase
import com.shoppingcartassignmentsystemclient.domain.use_cases.GetsProductUseCase
import com.shoppingcartassignmentsystemclient.domain.use_cases.SaveProductUseCase
import com.shoppingcartassignmentsystemclient.domain.use_cases.SearchProductUseCase
import com.shoppingcartassignmentsystemclient.domain.use_cases.UpdateProductUseCase
import com.shoppingcartassignmentsystemclient.presentation.cardScreen.CardState
import com.shoppingcartassignmentsystemclient.presentation.productScreen.ProductState
import com.shoppingcartassignmentsystemclient.presentation.productScreen.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val deleteProduct: DeleteProductUseCase,
    private val updateProduct: UpdateProductUseCase,
    private val saveProduct: SaveProductUseCase,
    private val getProducts: GetsProductUseCase,
    private val searchProduct: SearchProductUseCase
): ViewModel() {

    private val _products= MutableStateFlow(ProductState())
    val products = _products.asStateFlow()

    private val _cartProducts = MutableStateFlow(CardState())
    val cartProducts = _cartProducts.asStateFlow()

    private val _totalPrice = MutableStateFlow(0.0)
    val totalPrice: StateFlow<Double> get() = _totalPrice

    private val _cardLimitState = MutableStateFlow(CardState().cardLimit)
    val cardLimitState =  _cardLimitState.asStateFlow()


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
            is UIEvent.DeleteProductFromCard -> deleteProductFromCard(event.product)
            is UIEvent.DeleteAllProductsFromCard -> deleteAllProductsFromCard()
            is UIEvent.UpdateCardLimit -> updateCardLimit(event.limit)
        }
    }

    private fun updateCardLimit(newLimit: Double) {
        _cardLimitState.value = newLimit
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

    private fun deleteProductFromCard(product: Product) {
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
            calculateTotalPrice()
        }
    }

    private fun saveProduct(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            saveProduct.invoke(product)
            calculateTotalPrice()
        }
    }

}