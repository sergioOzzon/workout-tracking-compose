package com.learning.intervaltimer.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.learning.intervaltimer.R
import com.learning.intervaltimer.domain.TimerDTO
import com.learning.intervaltimer.domain.WorkoutDTO
import com.learning.intervaltimer.ui.base.Items.IntervalItem
import com.learning.intervaltimer.ui.base.Items.IntervalState
import com.learning.intervaltimer.ui.base.components.TimerCard
import com.learning.intervaltimer.ui.base.components.TimerState
import com.learning.intervaltimer.ui.base.components.WorkoutAppbar
import com.learning.intervaltimer.ui.base.components.WorkoutControlButtons
import com.learning.intervaltimer.ui.base.components.WorkoutProgressBar
import com.learning.intervaltimer.ui.theme.Bg
import com.learning.intervaltimer.ui.theme.Orange
import com.learning.intervaltimer.ui.theme.Primary
import com.learning.intervaltimer.ui.theme.Secondary
import com.learning.intervaltimer.ui.theme.TextPrimary
import com.learning.intervaltimer.ui.theme.TextSecondary
import com.learning.intervaltimer.ui.theme.TextTertiary
import com.learning.intervaltimer.ui.theme.WorkoutTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun WorkoutScreen(
    navController: NavController,
    viewModel: WorkoutViewModel = koinViewModel(),
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val sharedViewModel: WorkoutSharedViewModel = koinViewModel()
    val workout by sharedViewModel.workout.collectAsState()

    LaunchedEffect(workout) {
        workout?.let {
            viewModel.sendWish(WorkoutViewModel.Wish.LoadWorkout(it))
        }
    }

    WorkoutScreenContent(
        uiState = uiState,
        onAction = viewModel::sendWish,
        onBackAction = { navController.popBackStack() })

}

@Composable
fun WorkoutScreenContent(
    uiState: WorkoutViewModel.UiState,
    onAction: (WorkoutViewModel.Wish) -> Unit,
    onBackAction: () -> Unit,
) {
    Scaffold(
        content = { padding: PaddingValues ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(Bg)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                WorkoutAppbar(
                    title = uiState.workout?.timer?.title ?: "",
                    time = uiState.formattedTotalTime,
                    timerState = uiState.timerState,
                    onBackClick = {
                        onBackAction()
                    })

                TimerCard(
                    state = uiState.timerState, modifier = Modifier
                        .padding(
                            top = WorkoutTheme.spacing.xxl,
                            start = WorkoutTheme.spacing.xxl,
                            end = WorkoutTheme.spacing.xxl
                        )
                        .defaultMinSize()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {


                        // Workout status
                        Text(
                            text = when (uiState.timerState) {
                                TimerState.IDLE -> stringResource(R.string.workout_state_idle)
                                TimerState.RUNNING -> stringResource(R.string.workout_state_running)
                                TimerState.PAUSED -> stringResource(R.string.workout_state_pause)
                                TimerState.COMPLETED -> stringResource(R.string.workout_state_completed)
                            }.uppercase(),
                            style = MaterialTheme.typography.titleMedium,
                            color = when (uiState.timerState) {
                                TimerState.IDLE -> TextTertiary
                                TimerState.RUNNING -> Primary
                                TimerState.PAUSED -> Orange
                                TimerState.COMPLETED -> Secondary
                            },
                            modifier = Modifier.padding(top = WorkoutTheme.spacing.xxl)
                        )

                        //Interval title
                        Text(
                            text = uiState.currentInterval,
                            style = MaterialTheme.typography.bodyLarge,
                            color = TextSecondary
                        )

                        // Timer
                        Text(
                            text = if (uiState.timerState == TimerState.IDLE) uiState.formattedTotalTime else uiState.formattedIntervalTime,
                            style = MaterialTheme.typography.displayLarge,
                            color = TextPrimary,
                            modifier = Modifier.padding(vertical = WorkoutTheme.spacing.s)
                        )

                        //Total time progress
                        Text(
                            text = stringResource(
                                R.string.workout_timer_progress,
                                uiState.formattedTotalTime,
                                uiState.formattedInitiallyTotalTime
                            ), style = MaterialTheme.typography.bodyLarge, color = TextSecondary
                        )

                        // Progress Bar
                        WorkoutProgressBar(
                            progress = uiState.totalProgress,
                            state = uiState.timerState,
                            modifier = Modifier.padding(WorkoutTheme.spacing.xxl)
                        )
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                        .padding(
                            start = WorkoutTheme.spacing.xxl,
                            top = WorkoutTheme.spacing.xxl,
                            end = WorkoutTheme.spacing.xxl,
                            bottom = WorkoutTheme.spacing.s
                        )
                        .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = stringResource(R.string.workout_intervals_title),
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextSecondary
                    )

                    Text(
                        text = stringResource(
                            R.string.workout_intervals_step,
                            uiState.currentIntervalIndex + 1,
                            uiState.workout?.timer?.intervals?.size ?: 1
                        ), style = MaterialTheme.typography.bodyMedium, color = TextSecondary
                    )

                }

                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = WorkoutTheme.spacing.xxl)
                ) {
                    items(uiState.workout?.timer?.intervals?.size ?: 0) { key ->
                        IntervalItem(
                            name = uiState.workout?.timer?.intervals?.get(key)?.title ?: "",
                            order = key + 1,
                            duration = if (key == uiState.currentIntervalIndex)
                                uiState.formattedIntervalTime
                            else formatTime(
                                uiState.workout?.timer?.intervals?.get(key)?.time ?: 0
                            ),
                            state = getIntervalState(uiState, key),
                            progress = uiState.intervalProgress,
                            modifier = Modifier.padding(vertical = WorkoutTheme.spacing.xs)
                        )
                    }
                }

                WorkoutControlButtons(
                    timerState = uiState.timerState,
                    onStartClick = { onAction(WorkoutViewModel.Wish.StartTimer) },
                    onPauseClick = { onAction(WorkoutViewModel.Wish.PauseTimer) },
                    onResumeClick = { onAction(WorkoutViewModel.Wish.StartTimer) },
                    onRestartClick = { onAction(WorkoutViewModel.Wish.PauseTimer) },
                    onResetClick = { onAction(WorkoutViewModel.Wish.ResetTimer) },
                    onNewWorkoutClick = { onBackAction.invoke() },
                )

            }
        })
}

fun getIntervalState(uiState: WorkoutViewModel.UiState, key: Int): IntervalState {
    return if (uiState.currentIntervalIndex == key) {
        when (uiState.timerState) {
            TimerState.IDLE -> IntervalState.RUNNING
            TimerState.RUNNING -> IntervalState.RUNNING
            TimerState.PAUSED -> IntervalState.PAUSED
            TimerState.COMPLETED -> IntervalState.COMPLETED
        }
    } else if (uiState.currentIntervalIndex < key) {
        IntervalState.IDLE
    } else {
        IntervalState.COMPLETED
    }
}

private fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return "%02d:%02d".format(minutes, remainingSeconds)
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun WorkoutScreenPreview() {
    WorkoutScreenContent(
        uiState = WorkoutViewModel.UiState(
            workout = WorkoutDTO(
                timer = TimerDTO(
                    id = 1, title = "Тренировка 1", totalTime = 120, intervals = listOf()
                )
            ),
            formattedTotalTime = "12:15",
            formattedInitiallyTotalTime = "15:00",
            timerState = TimerState.IDLE,
            currentInterval = "Медленный бег"
        ), onAction = {}, onBackAction = {})
}