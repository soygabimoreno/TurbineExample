package soy.gabimoreno.turbineexample.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import soy.gabimoreno.turbineexample.di.IO
import soy.gabimoreno.turbineexample.domain.GetRandomBooleanUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getRandomBooleanUseCase: GetRandomBooleanUseCase,
    @IO private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState get() = _uiState.asStateFlow()

    fun changeUiState() {
        viewModelScope.launch(dispatcher) {
            if (getRandomBooleanUseCase()) {
                _uiState.emit(UiState.Success)
            } else {
                _uiState.emit(UiState.Error)
            }
        }
    }

    sealed class UiState {
        object Idle : UiState()
        object Error : UiState()
        object Success : UiState()
    }
}
