package com.learning.intervaltimer.ui.base.Items

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.learning.intervaltimer.R
import com.learning.intervaltimer.ui.base.components.NumberBadge
import com.learning.intervaltimer.ui.theme.Orange
import com.learning.intervaltimer.ui.theme.Primary
import com.learning.intervaltimer.ui.theme.PrimaryLight
import com.learning.intervaltimer.ui.theme.Secondary
import com.learning.intervaltimer.ui.theme.Surface
import com.learning.intervaltimer.ui.theme.TextPrimary
import com.learning.intervaltimer.ui.theme.TextSecondary
import com.learning.intervaltimer.ui.theme.WorkoutTheme


enum class IntervalState { IDLE, RUNNING, PAUSED, COMPLETED, DONE }

@Composable
fun IntervalItem(
    name: String,
    order: Int,
    duration: String,
    state: IntervalState,
    progress: Float = 0f,
    modifier: Modifier = Modifier,
) {
    val isRunning = state == IntervalState.RUNNING
    val isPaused = state == IntervalState.PAUSED
    val isCompleted = state == IntervalState.COMPLETED
    val isDone = state == IntervalState.DONE
    val alpha = if (isCompleted || isDone) 0.45f else 1f

    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = if (isRunning || isPaused) 2.dp
                else 0.dp,
                shape = RoundedCornerShape(12.dp)
            )
            .height(IntrinsicSize.Min)
            .clip(RoundedCornerShape(12.dp))
            .background(Surface)
            .then(
                if (isRunning || isPaused) {
                    Modifier.border(
                        0.5.dp, if (isRunning) Primary else Orange, RoundedCornerShape(12.dp)
                    )
                } else Modifier
            )
    ) {
        if (isRunning || isPaused) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(progress)
                    .background(
                        if (isRunning) PrimaryLight else Orange.copy(
                            alpha = 0.08f
                        )
                    )
            )
        }

        Row(
            modifier = Modifier
                .padding(
                    horizontal = WorkoutTheme.spacing.l, vertical = WorkoutTheme.spacing.m
                )
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.SpaceBetween
        ) {

            Box(
                modifier = Modifier.size(28.dp)
            ) {
                if (isCompleted || isDone) {
                    Icon(
                        painterResource(R.drawable.is_checked),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .size(16.dp)
                            .align(Alignment.Center),
                        tint = if (isCompleted) TextSecondary else Secondary
                    )
                } else {
                    NumberBadge(order, state)
                }
            }
            Text(
                text = name,
                style = MaterialTheme.typography.labelLarge.copy(
                    textDecoration = if (isCompleted || isDone) TextDecoration.LineThrough else null
                ),
                color = TextPrimary.copy(alpha = alpha),
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = WorkoutTheme.spacing.m)
            )
            Text(
                text = duration,
                style = MaterialTheme.typography.titleSmall,
                color = TextSecondary.copy(alpha = alpha)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun IntervalItemPreview() {
    IntervalItem(
        name = "Медленный бег Медленный бег Медленный бег",
        order = 5,
        duration = "00:30",
        progress = 0.4f,
        state = IntervalState.DONE,
    )
}
