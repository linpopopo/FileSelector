package com.linpopopo.app

import android.Manifest
import android.os.Bundle
import android.os.Environment
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.linpopopo.app.theme.FileSelectorTheme
import com.linpopopo.fileselector.FileSelectorData
import com.linpopopo.fileselector.mediumPadding

class MainActivity : ComponentActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FileSelectorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val permission = Manifest.permission.WRITE_EXTERNAL_STORAGE
                    val permissionState = rememberPermissionState(permission)
                    if (permissionState.status.isGranted) {
                        val selectedPaths = remember { mutableStateListOf<String>() }
                        var fileSelectorState by remember { mutableStateOf(false) }
                        var fileSelectorData by remember { mutableStateOf(FileSelectorData(rootPath = Environment.getExternalStorageDirectory().canonicalPath)) }
                        Column(
                            modifier = Modifier.padding(top = 16.dp)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                val allOptions = listOf(
                                    stringResource(R.string.text_selected_single_file),
                                    stringResource(R.string.text_selected_single_folder),
                                    stringResource(R.string.text_selected_multiple_files),
                                    stringResource(R.string.text_selected_multiple_folders)
                                )
                                var option by remember { mutableStateOf(allOptions[0]) }
                                var expanded by remember { mutableStateOf(false) }
                                Row(
                                    modifier = Modifier.align(Alignment.Center)
                                ) {
                                    TextButton(
                                        onClick = {
                                            fileSelectorData = when (option) {
                                                allOptions[0] -> fileSelectorData.copy(
                                                    isSelectFile = true,
                                                    isMultiple = false,
                                                )

                                                allOptions[1] -> fileSelectorData.copy(
                                                    isSelectFile = false,
                                                    isMultiple = false
                                                )

                                                allOptions[2] -> fileSelectorData.copy(
                                                    isSelectFile = true,
                                                    isMultiple = true
                                                )

                                                allOptions[3] -> fileSelectorData.copy(
                                                    isSelectFile = false,
                                                    isMultiple = true
                                                )

                                                else -> fileSelectorData.copy(isSelectFile = true, isMultiple = false)
                                            }
                                            fileSelectorState = true
                                        },
                                        colors = ButtonDefaults.textButtonColors(containerColor = Color.Green),
                                        shape = ShapeDefaults.ExtraSmall
                                    ) {
                                        Text(option)
                                        IconButton(onClick = { expanded = true }) {
                                            Icon(
                                                imageVector = Icons.Filled.ArrowDropDown,
                                                contentDescription = null
                                            )
                                        }
                                    }
                                    DropdownMenu(
                                        expanded = expanded,
                                        onDismissRequest = { expanded = false }
                                    ) {
                                        allOptions.forEach {
                                            DropdownMenuItem(
                                                text = {
                                                    Text(it)
                                                },
                                                onClick = {
                                                    option = it
                                                    expanded = false
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(mediumPadding))
                            LazyColumn {
                                itemsIndexed(selectedPaths) { _, item ->
                                    Text(item)
                                }
                            }
                            if (fileSelectorState) {
                                FileSelectorDialog(
                                    fileSelectorData = fileSelectorData,
                                    onDismissRequest = { fileSelectorState = false },
                                    onClose = { fileSelectorState = false },
                                    onSelectedPaths = {
                                        fileSelectorState = false
                                        selectedPaths.clear()
                                        selectedPaths.addAll(it)
                                    }
                                )
                            }
                        }
                    } else {
                        Column {
                            // If the user has denied the permission but the rationale can be shown,
                            // then gently explain why the app requires this permission
                            val textToShow = if (permissionState.status.shouldShowRationale) {
                                "The storage is important for this app. Please grant the permission."
                            } else {
                                // If it's the first time the user lands on this feature, or the user
                                // doesn't want to be asked again for this permission, explain that the
                                // permission is required
                                "storage permission required for this feature to be available. " +
                                        "Please grant the permission"
                            }
                            Text(textToShow)
                            TextButton(
                                onClick = { permissionState.launchPermissionRequest() },
                                colors = ButtonDefaults.textButtonColors(containerColor = Color.Green)
                            ) {
                                Text("request permission")
                            }
                        }
                    }
                }
            }
        }
    }
}

