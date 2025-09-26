package com.junior.migracaosevenmvvmcompose.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import com.junior.migracaosevenmvvmcompose.R
import com.junior.migracaosevenmvvmcompose.presentation.theme.RedBlackGradient
import com.junior.migracaosevenmvvmcompose.presentation.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    onMenuClick: ()-> Unit,
    showSearch: Boolean = false,
    onSearchQueryChange: (String) -> Unit = {},

){

    var isSearching by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    val focusRequest = remember { FocusRequester() }
    val keyBoardController = LocalSoftwareKeyboardController.current




    TopAppBar(
        title = {
            if (isSearching) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = {
                        searchQuery = it
                        onSearchQueryChange(it)
                    },
                    textStyle = LocalTextStyle.current.copy(White),
                    placeholder = {
                        Text(
                            text = "Pesquisar ....",
                            color = White) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().focusRequester(focusRequest),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        cursorColor = White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    )

                )
                LaunchedEffect(Unit) {

                        focusRequest.requestFocus()
                        keyBoardController?.show()

                }
            } else {
                Text(title)
            }
        }, navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(
                    painter = painterResource(R.drawable.ic_menu_principal),
                    contentDescription = "Menu",
                    tint = White
                )

            }
        },
        actions = {
            if (showSearch){
                if (isSearching){
                    IconButton(onClick = {
                        isSearching = false
                        searchQuery = ""
                        onSearchQueryChange("")

                    }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_close),
                            contentDescription = "Close",
                            tint = White
                        )
                    }
                }else{
                    IconButton(onClick = { isSearching = true }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_search),
                            contentDescription = "Search",
                            tint = White
                        )
                    }

                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = White,
            navigationIconContentColor = White,
            actionIconContentColor = White
        ),
        modifier = Modifier.background(RedBlackGradient).statusBarsPadding()


    )


}