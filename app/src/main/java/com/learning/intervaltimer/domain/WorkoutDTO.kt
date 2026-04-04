package com.learning.intervaltimer.domain

data class WorkoutDTO(
    val timer: TimerDTO?
)

data class TimerDTO(
    val id: Int,
    val title: String,
    val totalTime: Int,
    val intervals: List<IntervalDTO>
)

data class IntervalDTO(
    val title: String,
    val time: Int
)
