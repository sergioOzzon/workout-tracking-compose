package com.learning.intervaltimer.network.mappers

import com.learning.intervaltimer.domain.IntervalDTO
import com.learning.intervaltimer.domain.TimerDTO
import com.learning.intervaltimer.domain.WorkoutDTO
import com.learning.intervaltimer.network.models.responses.IntervalResponseBody
import com.learning.intervaltimer.network.models.responses.TimerResponseBody
import com.learning.intervaltimer.network.models.responses.WorkoutResponseBody

fun WorkoutResponseBody.toDTO(): WorkoutDTO {
    return WorkoutDTO(
        timer = timer.toDTO()
    )
}

fun TimerResponseBody.toDTO(): TimerDTO {
    return TimerDTO(
        id = timerId,
        title = title,
        totalTime = totalTime,
        intervals = intervals.map { it.toDTO() }
    )
}

fun IntervalResponseBody.toDTO(): IntervalDTO {
    return IntervalDTO(
        title = title,
        time = time
    )
}
