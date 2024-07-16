package com.shoppingcartassignmentsystemclient.presentation.cardScreen.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ClearAll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.shoppingcartassignmentsystemclient.presentation.ui.theme.backgroundColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreenTopBar(
    modifier: Modifier = Modifier,
    onClearAllClicked: () -> Unit,
    onNavigateBack: () -> Unit
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = backgroundColor
        ),
        modifier = modifier,
        title = { Text(text = "Shopping Cart") },
        actions = {
            IconButton(onClick = onClearAllClicked) {
                Icon(
                    imageVector = Icons.Default.ClearAll,
                    contentDescription = "Clear All"
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
    )
}