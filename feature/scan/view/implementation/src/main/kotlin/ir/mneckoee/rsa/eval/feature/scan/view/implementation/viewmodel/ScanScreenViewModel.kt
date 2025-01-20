package ir.mneckoee.rsa.eval.feature.scan.view.implementation.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.mneckoee.rsa.eval.feature.scan.domain.api.usecase.ScanUseCase
import javax.inject.Inject

@HiltViewModel
class ScanScreenViewModel @Inject constructor(
    private val scanUseCase: ScanUseCase
) : ViewModel(){

    val isBluetoothOn = scanUseCase.isBluetoothOn()



}