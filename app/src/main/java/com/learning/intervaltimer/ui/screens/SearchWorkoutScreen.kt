package com.learning.intervaltimer.ui.screens

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.learning.intervaltimer.navigation.SearchWorkoutScreenRoute

fun NavGraphBuilder.searchWorkoutScreen(navController: NavController) {
    composable(
        route = SearchWorkoutScreenRoute::class
    ) {
        SearchWorkoutScreen(navController)
    }

}

class SearchWorkoutScreen(
    navController: NavController,
    viewModel: SearchWorkoutViewModel = koinViewModel()
) {
}