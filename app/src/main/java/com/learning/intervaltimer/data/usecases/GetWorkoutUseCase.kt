package com.learning.intervaltimer.data.usecases

import com.learning.intervaltimer.domain.WorkoutDTO
import com.learning.intervaltimer.network.mappers.toDTO
import com.learning.intervaltimer.network.services.WorkoutService

interface GetWorkoutUseCase {

    suspend operator fun invoke(
        workoutId: String,
    ): Result<WorkoutDTO>

}

internal class GetWorkoutUseCaseImpl(
    private val workoutService: WorkoutService,
) : GetWorkoutUseCase {

    override suspend fun invoke(workoutId: String): Result<WorkoutDTO> {
        return runCatching {
            workoutService
                .getWorkout(workoutId)
                .toDTO()
        }
    }

}