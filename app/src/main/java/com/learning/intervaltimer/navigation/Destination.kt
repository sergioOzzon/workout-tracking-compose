package com.learning.intervaltimer.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Destination {

    @Serializable
    data object SearchWorkout : Destination()
    
    @Serializable
    data object Workout : Destination()
}
