package com.learning.intervaltimer.network.services

import com.learning.intervaltimer.network.models.responses.WorkoutResponseBody
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path

interface WorkoutService {
    @GET("interval-timers/{id}")
    suspend fun getWorkout(
        @Path("id") workoutId: String,
    ): WorkoutResponseBody

}