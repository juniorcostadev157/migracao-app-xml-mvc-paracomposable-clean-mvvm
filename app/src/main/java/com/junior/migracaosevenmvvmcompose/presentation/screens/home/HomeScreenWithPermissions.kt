package com.junior.migracaosevenmvvmcompose.presentation.screens.home

import android.annotation.SuppressLint
import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.junior.migracaosevenmvvmcompose.core.contants.Constants
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreenWithPermissions(
    activity: Activity,
    onMenuClick: ()-> Unit) {

    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope ()

    // 🔹 Lista de permissões necessárias
    val permissions = buildList {
        add(android.Manifest.permission.CAMERA)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            add(android.Manifest.permission.POST_NOTIFICATIONS)
        }
        add(android.Manifest.permission.INTERNET)
        add(android.Manifest.permission.ACCESS_NETWORK_STATE)
    }
    // 🔹 Gerenciador de permissões

    val multiplePermissionsState = rememberMultiplePermissionsState(permissions)

    // 🔹 Pedir permissões assim que entrar na tela
    LaunchedEffect(Unit) {
        multiplePermissionsState.launchMultiplePermissionRequest()
    }

    val appUpdateManager = remember { AppUpdateManagerFactory.create(activity) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
    ) {result->
        scope.launch {
            if (result.resultCode != Activity.RESULT_OK){
                snackBarHostState.showSnackbar(
                    message = Constants.Permissions.MESSAGE_UPDATE_FAILED,
                    withDismissAction = true
                )
            }else{
                snackBarHostState.showSnackbar(
                    message = Constants.Permissions.MESSAGE_UPDATE_SUCCESS
                )
            }
        }

    }

    LaunchedEffect(Unit){
        val appUpdateInoTask = appUpdateManager.appUpdateInfo
        appUpdateInoTask.addOnSuccessListener { appUpdateInfo->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE){
                val options = AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build()

                appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    launcher,
                    options
                )
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) {innerPadding->
        if (multiplePermissionsState.allPermissionsGranted){
            HomeScreen(onMenuClick = onMenuClick)
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(Constants.Permissions.MESSAGE_PERMISSIONS)
            }
        }

    }



}


