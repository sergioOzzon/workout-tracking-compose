package com.learning.intervaltimer.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.learning.intervaltimer.R
import com.learning.intervaltimer.ui.base.PrimaryButton
import com.learning.intervaltimer.ui.base.WorkoutInputField
import com.learning.intervaltimer.ui.theme.Bg
import com.learning.intervaltimer.ui.theme.Primary
import com.learning.intervaltimer.ui.theme.PrimaryLight
import com.learning.intervaltimer.ui.theme.TextSecondary
import com.learning.intervaltimer.ui.theme.WorkoutTheme.spacing
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SearchWorkoutScreen(
    navController: NavController,
    viewModel: SearchWorkoutViewModel = koinViewModel(),
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        content = { padding: PaddingValues ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(Bg)
                    .padding(padding)
                    .padding(horizontal = spacing.xxl, vertical = 0.dp)
                    .fillMaxSize()
            ) {
                Spacer(modifier = Modifier.padding(top = spacing.xxl))
                Box(
                    modifier = Modifier
                        .padding(top = spacing.xxl)
                        .background(
                            color = Primary,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .dropShadow(
                            shape = RoundedCornerShape(16.dp),
                            shadow = Shadow(
                                10.dp,
                                color = PrimaryLight,
                                offset = DpOffset(x = 0.dp, y = 10.dp)
                            )
                        )
                        .size(64.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier
                            .size(28.dp),
                        painter = painterResource(android.R.drawable.ic_popup_reminder),
                        contentDescription = "",
                        alignment = Alignment.Center
                    )
                }

                Text(
                    text = stringResource(R.string.search_title),
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier
                        .absoluteOffset()
                        .padding(top = spacing.xxl),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(R.string.search_description),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 16.sp,
                    color = TextSecondary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = spacing.l)
                )

                WorkoutInputField(
                    modifier = Modifier.padding(top = spacing.xl),
                    value = uiState.inputValue,
                    label = stringResource(R.string.search_id_label),
                    placeholder = stringResource(R.string.search_id_label),
                    isError = uiState.error,
                    isEnabled = !uiState.loading,
                    onValueChange = { value ->
                        viewModel.sendWish(SearchWorkoutViewModel.Wish.UpdateState(value))
                    },
                    keyboardAction = KeyboardActions {
                        viewModel.sendWish(SearchWorkoutViewModel.Wish.GetWorkout())
                    }
                )

                PrimaryButton(
                    text = getButtonTextBy(uiState),
                    onClick = {
                        viewModel.sendWish(SearchWorkoutViewModel.Wish.GetWorkout())
                    },
                    modifier = Modifier.padding(top = spacing.l),
                    enabled = !uiState.loading,
                    isLoading = uiState.loading
                )
            }

        }
    )
}

@Composable
private fun getButtonTextBy(uiState: SearchWorkoutViewModel.UiState): String {
    return if (uiState.error) {
        stringResource(R.string.search_button_retry)
    } else if (uiState.loading) {
        stringResource(R.string.search_button_loading)
    } else {
        stringResource(R.string.search_button_load)
    }
}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun SearchWorkoutLayoutPreview() {

}