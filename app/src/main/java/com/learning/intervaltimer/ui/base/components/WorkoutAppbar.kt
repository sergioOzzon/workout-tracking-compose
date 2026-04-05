package com.learning.intervaltimer.ui.base.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.learning.intervaltimer.R
import com.learning.intervaltimer.ui.theme.Border
import com.learning.intervaltimer.ui.theme.Orange
import com.learning.intervaltimer.ui.theme.Primary
import com.learning.intervaltimer.ui.theme.Secondary
import com.learning.intervaltimer.ui.theme.Surface
import com.learning.intervaltimer.ui.theme.TextSecondary
import com.learning.intervaltimer.ui.theme.WorkoutTheme

enum class TimerState { IDLE, RUNNING, PAUSED, COMPLETED }

@Composable
fun WorkoutAppbar(
    title: String,
    time: String,
    timerState: TimerState,
    onBackClick: () -> Unit,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = WorkoutTheme.spacing.xxl)
            .height(48.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .border(1.dp, Border, CircleShape)
                .clickable(
                    indication = ripple(),
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    onBackClick()
                }
                .background(Surface)

        ) {
            Icon(
                painter = painterResource(androidx.appcompat.R.drawable.abc_ic_ab_back_material),
                contentDescription = "",
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.Center)

            )
        }
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .align(Alignment.CenterVertically)
        )
        WorkoutTimer(time = time, timerState = timerState)

    }
}

@Composable
fun WorkoutTimer(time: String, timerState: TimerState) {
    Row(
        modifier = Modifier
            .fillMaxHeight()
    ) {
        if (timerState == TimerState.RUNNING || timerState == TimerState.PAUSED)
            Icon(
                painter = when (timerState) {
                    TimerState.RUNNING -> painterResource(R.drawable.ic_circle)
                    TimerState.PAUSED -> painterResource(R.drawable.ic_pause)
                    else -> {
                        painterResource(0) //never use
                    }
                },
                tint = getTimerColor(timerState),
                modifier = Modifier
                    .size(12.dp)
                    .align(Alignment.CenterVertically),
                contentDescription = ""
            )
        Text(
            text = if (timerState != TimerState.COMPLETED) time else stringResource(R.string.workout_timer_completed),
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = WorkoutTheme.spacing.xs),
            style = MaterialTheme.typography.labelSmall,
            color = getTimerColor(timerState)
        )
    }
}

@Composable
private fun getTimerColor(timerState: TimerState): Color = when (timerState) {
    TimerState.IDLE -> TextSecondary
    TimerState.RUNNING -> Primary
    TimerState.PAUSED -> Orange
    TimerState.COMPLETED -> Secondary
}

@Composable
@Preview(showBackground = true)
fun WorkoutAppbarPreview() {
    WorkoutAppbar("Тренировка 7", "12:18", TimerState.PAUSED) {}
}