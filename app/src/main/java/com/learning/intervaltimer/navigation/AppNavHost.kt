package com.learning.intervaltimer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.learning.intervaltimer.ui.screens.SearchWorkoutScreen
import com.learning.intervaltimer.ui.screens.WorkoutScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Destination.SearchWorkout
    ) {
        composable<Destination.SearchWorkout> {
            SearchWorkoutScreen(navController)
        }
        composable<Destination.Workout> {
            WorkoutScreen(navController)
        }
    }
}
