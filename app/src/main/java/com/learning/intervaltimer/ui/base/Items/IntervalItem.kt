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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.learning.intervaltimer.ui.base.components.TimerState
import com.learning.intervaltimer.ui.theme.Orange
import com.learning.intervaltimer.ui.theme.Primary
import com.learning.intervaltimer.ui.theme.PrimaryLight
import com.learning.intervaltimer.ui.theme.Secondary
import com.learning.intervaltimer.ui.theme.TextPrimary
import com.learning.intervaltimer.ui.theme.TextSecondary

@Composable
fun IntervalItem(
    name: String,
    duration: String,
    state: TimerState,
    progress: Float = 0f,
    modifier: Modifier = Modifier,
) {
    val isCompleted = state == TimerState.COMPLETED
    val alpha = if (isCompleted) 0.45f else 1f

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .then(
                if (state == TimerState.RUNNING || state == TimerState.PAUSED) {
                    Modifier.border(
                        1.5.dp,
                        if (state == TimerState.RUNNING) Primary else Orange,
                        RoundedCornerShape(12.dp)
                    )
                } else Modifier
            )
    ) {
        // Progress Fill
        if (state == TimerState.RUNNING || state == TimerState.PAUSED) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(progress)
                    .background(if (state == TimerState.RUNNING) PrimaryLight else Orange.copy(alpha = 0.1f))
            )
        }

        Row(
            modifier = Modifier
                .padding(horizontal = 14.dp, vertical = 12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = name, style = MaterialTheme.typography.labelLarge.copy(
                    textDecoration = if (isCompleted) TextDecoration.LineThrough else null
                ), color = TextPrimary.copy(alpha = alpha)
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = duration,
                    style = MaterialTheme.typography.titleSmall,
                    color = TextSecondary.copy(alpha = alpha)
                )
                if (isCompleted) {
                    Icon(
                        painterResource(android.R.drawable.checkbox_on_background),
                        contentDescription = null,
                        modifier = Modifier
                            .size(16.dp)
                            .padding(start = 4.dp),
                        tint = Secondary
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun IntervalItemPreview() {
    IntervalItem(
        name = "Warmup",
        duration = "00:30",
        state = TimerState.RUNNING,
    )
}
