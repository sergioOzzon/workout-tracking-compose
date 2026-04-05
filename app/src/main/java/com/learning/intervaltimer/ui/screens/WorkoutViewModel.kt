package com.learning.intervaltimer.ui.screens

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewModelScope
import com.learning.intervaltimer.domain.WorkoutDTO
import com.learning.intervaltimer.ui.base.BaseViewModel
import com.learning.intervaltimer.ui.base.components.TimerState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

class WorkoutViewModel :
    BaseViewModel<WorkoutViewModel.Wish, WorkoutViewModel.UiState, WorkoutViewModel.SideEffect>() {

    override val _uiState = MutableStateFlow(UiState())

    private var timerJob: Job? = null

    override fun reduce(
        wish: Wish,
        currentState: UiState,
    ): UiState {
        return when (wish) {
            is Wish.LoadWorkout -> {
                currentState.copy(
                    workout = wish.workout,
                    timerState = TimerState.IDLE,
                    currentTimeSeconds = wish.workout.timer?.totalTime ?: 0,
                    formattedTime = formatTime(wish.workout.timer?.totalTime ?: 0)
                )
            }

            is Wish.StartTimer -> {
                startTimer()
                currentState.copy(timerState = TimerState.RUNNING)
            }

            is Wish.PauseTimer -> {
                pauseTimer()
                currentState.copy(timerState = TimerState.PAUSED)
            }

            is Wish.ResetTimer -> {
                resetTimer()
                currentState.copy(
                    timerState = TimerState.IDLE,
                    currentTimeSeconds = 0,
                    formattedTime = formatTime(0)
                )
            }

            is Wish.Tick -> {
                val newSeconds = currentState.currentTimeSeconds - 1
                currentState.copy(
                    currentTimeSeconds = newSeconds,
                    formattedTime = formatTime(newSeconds),
                    timerState = TimerState.RUNNING
                )
            }
        }
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                sendWish(Wish.Tick)
            }
        }
    }

    private fun pauseTimer() {
        timerJob?.cancel()
    }

    private fun resetTimer() {
        timerJob?.cancel()
    }

    private fun formatTime(seconds: Int): String {
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        return "%02d:%02d".format(minutes, remainingSeconds)
    }

    sealed interface Wish {
        data class LoadWorkout(val workout: WorkoutDTO) : Wish
        object StartTimer : Wish
        object PauseTimer : Wish
        object ResetTimer : Wish
        object Tick : Wish
    }

    data class UiState(
        val workout: WorkoutDTO? = null,
        val timerState: TimerState = TimerState.IDLE,
        val currentTimeSeconds: Int = 0,
        val formattedTime: String = "00:00",
        val currentIntervalIndex: Int = 0,
    )

    sealed interface SideEffect {
        object WorkoutFinished : SideEffect
    }
}
