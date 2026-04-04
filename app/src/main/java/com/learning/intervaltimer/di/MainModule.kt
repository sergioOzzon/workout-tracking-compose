package com.learning.intervaltimer.di

import com.learning.intervaltimer.ui.screens.SearchWorkoutViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val mainModule = module {

    viewModelOf(::SearchWorkoutViewModel)

}