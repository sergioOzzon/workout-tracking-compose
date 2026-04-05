package com.learning.intervaltimer.ui.screens

import android.annotation.SuppressLint
import androidx.lifecycle.viewModelScope
import com.learning.intervaltimer.data.usecases.GetWorkoutUseCase
import com.learning.intervaltimer.domain.WorkoutDTO
import com.learning.intervaltimer.ui.base.BaseViewModel
import com.learning.intervaltimer.ui.screens.SearchWorkoutViewModel.SideEffect
import com.learning.intervaltimer.ui.screens.SearchWorkoutViewModel.UiState
import com.learning.intervaltimer.ui.screens.SearchWorkoutViewModel.Wish
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

private const val DEFAULT_WORKOUT_ID = "68"

@SuppressLint("StaticFieldLeak")
class SearchWorkoutViewModel(
    private val getWorkoutUseCase: GetWorkoutUseCase,
    private val workoutSharedViewModel: WorkoutSharedViewModel
) : BaseViewModel<Wish, UiState, SideEffect>() {

    override val _uiState = MutableStateFlow(UiState())

    override fun reduce(
        wish: Wish,
        currentState: UiState,
    ): UiState {
        return when (wish) {
            is Wish.GetWorkout -> {
                getWorkout(currentState.inputValue)
                currentState.copy(
                    loading = true,
                    error = false
                )
            }

            is Wish.HandleFailure -> {
                currentState.copy(
                    error = true,
                    loading = false
                )
            }

            is Wish.HandleSuccess -> {
                workoutSharedViewModel.setWorkout(wish.workoutDTO)
                sendEffect(SideEffect.GoWorkoutScreen(wish.workoutDTO))
                currentState.copy(
                    loading = false
                )
            }

            is Wish.UpdateState -> {
                currentState.copy(
                    error = false,
                    inputValue = wish.inputValue
                )
            }
        }
    }

    private fun getWorkout(workOutId: String) {
        viewModelScope.launch {
            getWorkoutUseCase.invoke(workOutId)
                .onSuccess { workoutDTO ->
                    sendWish(Wish.HandleSuccess(workoutDTO))
                }
                .onFailure {
                    sendWish(Wish.HandleFailure())
                }
        }
    }

    sealed interface Wish {
        class GetWorkout() : Wish
        class HandleSuccess(val workoutDTO: WorkoutDTO) : Wish
        class HandleFailure : Wish
        class UpdateState(val inputValue: String) : Wish
    }

    data class UiState(
        val loading: Boolean = false,
        val error: Boolean = false,
        val inputValue: String = DEFAULT_WORKOUT_ID,
    )

    sealed interface SideEffect {
        class GoWorkoutScreen(val workoutDTO: WorkoutDTO) : SideEffect
    }

}