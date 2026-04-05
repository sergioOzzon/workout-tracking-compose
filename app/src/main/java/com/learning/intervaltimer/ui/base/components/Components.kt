package com.learning.intervaltimer.ui.base.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ProgressIndicatorDefaults.CircularDeterminateStrokeCap
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.learning.intervaltimer.R
import com.learning.intervaltimer.ui.base.Items.IntervalState
import com.learning.intervaltimer.ui.theme.Border
import com.learning.intervaltimer.ui.theme.DisabledBg
import com.learning.intervaltimer.ui.theme.DisabledText
import com.learning.intervaltimer.ui.theme.Error
import com.learning.intervaltimer.ui.theme.Orange
import com.learning.intervaltimer.ui.theme.Primary
import com.learning.intervaltimer.ui.theme.PrimaryLight
import com.learning.intervaltimer.ui.theme.Secondary
import com.learning.intervaltimer.ui.theme.Surface
import com.learning.intervaltimer.ui.theme.TextPrimary
import com.learning.intervaltimer.ui.theme.TextSecondary
import com.learning.intervaltimer.ui.theme.TextTertiary
import com.learning.intervaltimer.ui.theme.WorkoutTheme

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    color: Color = MaterialTheme.colorScheme.primary,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        enabled = enabled && !isLoading,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(
            width = 0.5.dp, brush = SolidColor(if (enabled) color else Primary)
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isLoading) PrimaryLight else color,
            contentColor = Color.White,
            disabledContainerColor = PrimaryLight,
            disabledContentColor = Primary
        ),
        contentPadding = PaddingValues(0.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 2.dp,
                strokeCap = CircularDeterminateStrokeCap
            )
        }
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(horizontal = WorkoutTheme.spacing.m)
        )
    }
}

@Composable
fun GhostButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    borderColor: Color = Error,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp),
        enabled = enabled,
        shape = RoundedCornerShape(12.dp),
        border = ButtonDefaults.outlinedButtonBorder(enabled).copy(
            width = 0.5.dp, brush = SolidColor(if (enabled) borderColor else DisabledBg)
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = if (borderColor == Error) Error else TextPrimary,
            disabledContentColor = DisabledText
        )
    ) {
        Text(
            text = text, style = MaterialTheme.typography.labelMedium
        )
    }
}

@Composable
fun SearchWorkoutInputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    label: String? = null,
    isError: Boolean = false,
    isEnabled: Boolean = false,
    keyboardAction: KeyboardActions = KeyboardActions { KeyboardActions.Default },
) {
    Column(modifier = modifier) {
        if (label != null) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary,
                modifier = Modifier.padding(bottom = WorkoutTheme.spacing.xs)
            )
        }
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            enabled = isEnabled,
            placeholder = {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextTertiary
                )
            },
            maxLines = 1,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = Border,
                disabledBorderColor = Border,
                errorBorderColor = Error
            ),
            textStyle = MaterialTheme.typography.bodyLarge,
            isError = isError,
            modifier = Modifier
                .height(52.dp)
                .fillMaxWidth(),
            keyboardActions = keyboardAction,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search, keyboardType = KeyboardType.Number
            )
        )
        if (isError) {
            Row(
                modifier = Modifier.padding(top = WorkoutTheme.spacing.s)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_alert),
                    tint = Error,
                    contentDescription = "",
                    modifier = Modifier
                        .size(12.dp)
                        .align(Alignment.CenterVertically),
                )
                Text(
                    text = stringResource(R.string.search_error_not_found),
                    color = Error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = WorkoutTheme.spacing.s)
                )
            }
        }
    }
}

@Composable
fun TimerCard(
    state: TimerState,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    val (borderColor, backgroundBrush) = when (state) {
        TimerState.IDLE -> Border to SolidColor(Color.White)
        TimerState.RUNNING -> MaterialTheme.colorScheme.primary to Brush.verticalGradient(
            listOf(Primary.copy(alpha = 0.04f), Color.White)
        )

        TimerState.PAUSED -> Orange to Brush.verticalGradient(
            listOf(Orange.copy(alpha = 0.04f), Color.White)
        )

        TimerState.COMPLETED -> Secondary to Brush.verticalGradient(
            listOf(Secondary.copy(alpha = 0.04f), Color.White)
        )
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundBrush)
            .border(0.5.dp, borderColor, RoundedCornerShape(16.dp))
            .fillMaxWidth()
    ) {
        Column { content() }
    }
}

@Composable
fun NumberBadge(
    number: Int,
    state: IntervalState,
) {
    Box(
        modifier = Modifier
            .size(28.dp)
            .background(
                when (state) {
                    IntervalState.IDLE -> DisabledText
                    IntervalState.RUNNING -> Primary
                    IntervalState.PAUSED -> Orange
                    else -> Color.Transparent
                }, CircleShape
            ),

        contentAlignment = Alignment.Center
    ) {
        if (state == IntervalState.COMPLETED) Icon(
            painterResource(android.R.drawable.checkbox_on_background),
            contentDescription = null,
        )
        else Text(
            text = number.toString(),
            style = MaterialTheme.typography.labelMedium,
            color = when (state) {
                IntervalState.IDLE -> TextSecondary
                IntervalState.RUNNING -> Surface
                IntervalState.PAUSED -> Surface
                else -> Color.Transparent
            }
        )
    }
}

@Composable
fun WorkoutProgressBar(
    progress: Float,
    state: TimerState,
    modifier: Modifier = Modifier,
) {
    val color = when (state) {
        TimerState.RUNNING -> Primary
        TimerState.PAUSED -> Orange
        TimerState.COMPLETED -> Secondary
        else -> Border
    }

    LinearProgressIndicator(
        progress = { progress },
        modifier = modifier
            .fillMaxWidth()
            .height(4.dp)
            .clip(RoundedCornerShape(2.dp)),
        color = color,
        trackColor = Border,
        strokeCap = StrokeCap.Round,
        gapSize = 0.dp
    )
}
