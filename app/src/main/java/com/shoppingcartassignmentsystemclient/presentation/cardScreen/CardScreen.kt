package com.shoppingcartassignmentsystemclient.presentation.cardScreen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.shoppingcartassignmentsystemclient.presentation.cardScreen.components.CardListItem
import com.shoppingcartassignmentsystemclient.presentation.cardScreen.components.CardScreenBottomBar
import com.shoppingcartassignmentsystemclient.presentation.cardScreen.components.CardScreenTopBar
import com.shoppingcartassignmentsystemclient.presentation.MainViewModel
import com.shoppingcartassignmentsystemclient.presentation.productScreen.UIEvent

@Composable
fun CardScreen(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel,
    navController: NavController
) {
    val cardProducts by mainViewModel.cartProducts.collectAsState()
    val totalPrice by mainViewModel.totalPrice.collectAsState()
    val cardLimitState by mainViewModel.cardLimitState.collectAsState()

    Log.d("cart screen size", cardProducts.productsWithQuantity.size.toString())

    var serverIp by remember { mutableStateOf("") }
    var serverPort by remember { mutableStateOf("") }
    var isPortDialogOpen by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CardScreenTopBar(
                onClearAllClicked = { mainViewModel.onEvent(UIEvent.DeleteAllProductsFromCard) },
                onNavigateBack = { navController.popBackStack() })
        },
        bottomBar = {
            CardScreenBottomBar(
                cardLimitState = cardLimitState.toString(),
                totalPrice = totalPrice.toString(),
                isPortDialogOpen = isPortDialogOpen,
                onSendClicked = {
                    isPortDialogOpen = true
                },
                onPortDialogDismiss = { isPortDialogOpen = false },
                onPortDialogConfirm = { ip, port ->
                    serverIp = ip
                    serverPort = port
                    isPortDialogOpen = false
                }
            )
        }
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(it),
        ) {
            if (cardProducts.productsWithQuantity.isNotEmpty()) {
                LazyColumn {
                    items(cardProducts.productsWithQuantity.toList()) { (product, quantity) ->
                        CardListItem(
                            product = product,
                            quantity = quantity,
                            onDeleteClicked = {
                                mainViewModel.onEvent(UIEvent.DeleteProductFromCard(product))
                            },
                            onAddClicked = {
                                mainViewModel.onEvent(UIEvent.AddProductToCard(product))
                            }
                        )
                    }
                }
            } else {
                Text(
                    text = "No products in the cart",
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                        .padding(vertical = 16.dp)
                )
            }
        }
    }
}
