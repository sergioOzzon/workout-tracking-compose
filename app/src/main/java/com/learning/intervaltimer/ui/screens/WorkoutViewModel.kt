package com.learning.intervaltimer.ui.screens

import androidx.lifecycle.viewModelScope
import com.learning.intervaltimer.domain.WorkoutDTO
import com.learning.intervaltimer.ui.base.BaseViewModel
import com.learning.intervaltimer.ui.base.components.TimerState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

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
                val totalTimeSeconds = wish.workout.timer?.totalTime ?: 0
                val intervalTimeSeconds =
                    wish.workout.timer?.intervals[currentState.currentIntervalIndex]?.time ?: 0

                currentState.copy(
                    workout = wish.workout,
                    timerState = TimerState.IDLE,
                    totalTimeSeconds = totalTimeSeconds,
                    intervalTimeSeconds = intervalTimeSeconds,
                    formattedTotalTime = formatTime(totalTimeSeconds),
                    formattedInitiallyTotalTime = formatTime(totalTimeSeconds),
                    formattedIntervalTime = formatTime(intervalTimeSeconds),
                    currentInterval = wish.workout.timer?.intervals[currentState.currentIntervalIndex]?.title
                        ?: ""
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

                val totalTimeSeconds = currentState.workout?.timer?.totalTime ?: 0
                val intervalTimeSeconds =
                    currentState.workout?.timer?.intervals[currentState.currentIntervalIndex]?.time
                        ?: 0

                currentState.copy(
                    workout = currentState.workout,
                    timerState = TimerState.IDLE,
                    totalTimeSeconds = totalTimeSeconds,
                    intervalTimeSeconds = intervalTimeSeconds,
                    formattedTotalTime = formatTime(totalTimeSeconds),
                    formattedInitiallyTotalTime = formatTime(totalTimeSeconds),
                    formattedIntervalTime = formatTime(intervalTimeSeconds),
                    currentInterval = currentState.workout?.timer?.intervals[currentState.currentIntervalIndex]?.title
                        ?: ""
                )
            }

            is Wish.Tick -> {

                val newTotalTimeSeconds = currentState.totalTimeSeconds - 1

                var newIntervalTimeSeconds = currentState.intervalTimeSeconds - 1

                val totalProgress =
                    1f - newTotalTimeSeconds.toFloat() / currentState.workout?.timer?.totalTime!!

                val totalIntervalTime =
                    currentState.workout.timer.intervals[currentState.currentIntervalIndex].time.toFloat()
                val intervalProgress = 1f - newIntervalTimeSeconds.toFloat() / totalIntervalTime

                var newCurrentIntervalIndex: Int
                if (newIntervalTimeSeconds == 0) {
                    newCurrentIntervalIndex = currentState.currentIntervalIndex + 1
                    newIntervalTimeSeconds =
                        currentState.workout.timer.intervals[currentState.currentIntervalIndex].time
                } else {
                    newCurrentIntervalIndex = currentState.currentIntervalIndex
                }


                if (currentState.totalTimeSeconds == 0) {
                    currentState.copy(
                        timerState = TimerState.COMPLETED
                    )
                } else {
                    currentState.copy(
                        totalTimeSeconds = newTotalTimeSeconds,
                        intervalTimeSeconds = newIntervalTimeSeconds,
                        formattedTotalTime = formatTime(newTotalTimeSeconds),
                        formattedIntervalTime = formatTime(newIntervalTimeSeconds),
                        formattedTotalTimePassed = formatTime(currentState.workout.timer.totalTime - currentState.totalTimeSeconds),
                        timerState = TimerState.RUNNING,
                        totalProgress = totalProgress,
                        intervalProgress = intervalProgress,
                        currentIntervalIndex = newCurrentIntervalIndex,
                        currentInterval = currentState.workout.timer.intervals[newCurrentIntervalIndex].title
                    )
                }
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
        val totalTimeSeconds: Int = 0,
        val intervalTimeSeconds: Int = 0,
        val formattedTotalTime: String = "00:00",
        val formattedInitiallyTotalTime: String = "00:00",
        val formattedIntervalTime: String = "00:00",
        val formattedTotalTimePassed: String = "00:00",
        val currentIntervalIndex: Int = 0,
        val currentInterval: String = "",
        val totalProgress: Float = 0f,
        val intervalProgress: Float = 0f,
    )

    sealed interface SideEffect {
        object WorkoutFinished : SideEffect
    }
}
