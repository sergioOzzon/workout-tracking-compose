package com.learning.intervaltimer.di.data

import com.learning.intervaltimer.data.usecases.GetWorkoutUseCase
import com.learning.intervaltimer.data.usecases.GetWorkoutUseCaseImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
    // UseCases
    factoryOf(::GetWorkoutUseCaseImpl) bind GetWorkoutUseCase::class
}