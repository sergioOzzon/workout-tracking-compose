package com.learning.intervaltimer.ui.base.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.learning.intervaltimer.R
import com.learning.intervaltimer.ui.theme.Orange
import com.learning.intervaltimer.ui.theme.Secondary
import com.learning.intervaltimer.ui.theme.WorkoutTheme

@Composable
fun WorkoutControlButtons(
    timerState: TimerState,
    onStartClick: () -> Unit,
    onPauseClick: () -> Unit,
    onResumeClick: () -> Unit,
    onRestartClick: () -> Unit,
    onResetClick: () -> Unit,
    onNewWorkoutClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = WorkoutTheme.spacing.xxl)
    ) {
        when (timerState) {
            TimerState.IDLE -> {
                PrimaryButton(
                    text = stringResource(R.string.workout_button_start),
                    onClick = onStartClick
                )
            }

            TimerState.RUNNING -> {
                PrimaryButton(
                    text = stringResource(R.string.workout_button_pause),
                    onClick = onPauseClick,
                    color = Orange
                )
            }

            TimerState.PAUSED -> {
                PrimaryButton(
                    text = stringResource(R.string.workout_button_resume),
                    onClick = onResumeClick
                )
            }

            TimerState.COMPLETED -> {
                PrimaryButton(
                    text = stringResource(R.string.workout_button_again),
                    onClick = onRestartClick,
                    color = Secondary
                )
            }
        }

        Spacer(modifier = Modifier.height(WorkoutTheme.spacing.m))

        GhostButton(
            text = if (timerState == TimerState.COMPLETED) {
                stringResource(R.string.workout_button_new)
            } else {
                stringResource(R.string.workout_button_reset)
            },
            onClick = if (timerState == TimerState.COMPLETED) onNewWorkoutClick else onResetClick
        )
    }
}
