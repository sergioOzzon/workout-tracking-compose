package com.learning.intervaltimer.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.learning.intervaltimer.domain.TimerDTO
import com.learning.intervaltimer.domain.WorkoutDTO
import com.learning.intervaltimer.ui.base.components.TimerState
import com.learning.intervaltimer.ui.base.components.WorkoutAppbar
import com.learning.intervaltimer.ui.base.components.WorkoutControlButtons
import com.learning.intervaltimer.ui.theme.Bg
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
        onBackAction = { navController.popBackStack() }
    )

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
                    .padding(padding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                WorkoutAppbar(
                    title = uiState.workout?.timer?.title ?: "",
                    time = uiState.formattedTime,
                    timerState = uiState.timerState,
                    onBackClick = {
                        onBackAction()
                    }
                )
                WorkoutControlButtons(
                    timerState = uiState.timerState,
                    onStartClick = { onAction(WorkoutViewModel.Wish.StartTimer) },
                    onPauseClick = { onAction(WorkoutViewModel.Wish.PauseTimer) },
                    onResumeClick = { onAction(WorkoutViewModel.Wish.StartTimer) },
                    onRestartClick = { onAction(WorkoutViewModel.Wish.PauseTimer) },
                    onResetClick = { },
                    onNewWorkoutClick = { },
                )

            }
        }
    )
}

@Composable
@Preview(showBackground = true)
fun WorkoutScreenPreview() {
    WorkoutScreenContent(
        uiState = WorkoutViewModel.UiState(
            workout = WorkoutDTO(
                timer = TimerDTO(
                    id = 1,
                    title = "Тренировка 1",
                    totalTime = 120,
                    intervals = listOf()
                )
            ),
            formattedTime = "12:15",
            timerState = TimerState.RUNNING


        ),
        onAction = {},
        onBackAction = {}
    )
}