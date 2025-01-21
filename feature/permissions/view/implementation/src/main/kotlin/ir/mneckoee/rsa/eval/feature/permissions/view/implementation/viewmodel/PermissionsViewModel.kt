package ir.mneckoee.rsa.eval.feature.permissions.view.implementation.viewmodel

import android.content.Context
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import ir.mneckoee.rsa.eval.feature.permissions.view.implementation.ui.entry.bluetoothPermissions
import ir.mneckoee.rsa.eval.feature.permissions.view.implementation.ui.entry.locationPermissions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PermissionsViewModel @Inject constructor(
    @ApplicationContext
    private val context: Context
) : ViewModel() {
    private val allPermissions = bluetoothPermissions + locationPermissions
    val permissionChecked = MutableStateFlow(mapOf("" to false))
    val proceedButtonEnable = permissionChecked.map { map -> map.values.all { it } }

    val allPermissionGranted = MutableStateFlow(false)
    val showUi = MutableStateFlow(false)

    fun onCheckChanged(manifestName: String, state: Boolean) = viewModelScope.launch {
        val currentPermissionChecked = permissionChecked.value.toMutableMap()
        currentPermissionChecked[manifestName] = state
        permissionChecked.emit(currentPermissionChecked.toMap())
    }

    fun checkPermissions() = viewModelScope.launch {
        val grantedStatus = allPermissions.associate {
            it.manifestName to (context.checkSelfPermission(
                it.manifestName
            ) == PackageManager.PERMISSION_GRANTED)
        }
        val allGranted =
            grantedStatus.values.all { it }
        allPermissionGranted.emit(allGranted)
        showUi.emit(!allGranted)
        permissionChecked.emit(grantedStatus)
    }

}