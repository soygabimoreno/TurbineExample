package soy.gabimoreno.turbineexample.presentation

import app.cash.turbine.test
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Before
import org.junit.Test
import soy.gabimoreno.turbineexample.domain.GetRandomBooleanUseCase

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    private val getRandomBooleanUseCase: GetRandomBooleanUseCase = mockk()
    private val dispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        viewModel = MainViewModel(
            getRandomBooleanUseCase,
            dispatcher
        )
    }

    @Test
    fun `WHEN init THEN uiState should be Idle`() = runTest(dispatcher) {
        viewModel.uiState.value shouldBeInstanceOf MainViewModel.UiState.Idle::class
    }

    @Test
    fun `WHEN init THEN state should be Idle using Turbine`() = runTest(dispatcher) {
        viewModel.uiState.test {
            val item = awaitItem()
            item shouldBeInstanceOf MainViewModel.UiState.Idle::class
        }
    }

    @Test
    fun `GIVEN success WHEN changeUiState THEN uiState should be Success`() =
        runTest(dispatcher) {
            every { getRandomBooleanUseCase() } returns true

            viewModel.changeUiState()

            viewModel.uiState.value shouldBeInstanceOf MainViewModel.UiState.Success::class
        }

    @Test
    fun `GIVEN success WHEN changeUiState THEN uiState should be Success using Turbine`() =
        runTest(dispatcher) {
            every { getRandomBooleanUseCase() } returns true

            viewModel.changeUiState()

            viewModel.uiState.test {
                val item = awaitItem()
                item shouldBeInstanceOf MainViewModel.UiState.Success::class
            }
        }

    @Test
    fun `GIVEN error WHEN changeUiState THEN uiState should be Error`() = runTest(dispatcher) {
        every { getRandomBooleanUseCase() } returns false

        viewModel.changeUiState()

        viewModel.uiState.value shouldBeInstanceOf MainViewModel.UiState.Error::class
    }

    @Test
    fun `GIVEN error WHEN changeUiState THEN uiState should be Error using Turbine`() =
        runTest(dispatcher) {
            every { getRandomBooleanUseCase() } returns false

            viewModel.changeUiState()

            viewModel.uiState.test {
                val item = awaitItem()
                item shouldBeInstanceOf MainViewModel.UiState.Error::class
            }
        }
}
