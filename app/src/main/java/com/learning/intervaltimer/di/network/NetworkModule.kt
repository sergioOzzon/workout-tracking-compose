package com.learning.intervaltimer.di.network

import com.learning.intervaltimer.network.API_BASE_URL
import com.learning.intervaltimer.network.HttpClientFactory
import com.learning.intervaltimer.network.NetworkResponseHandler
import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.ktorfit
import io.ktor.client.HttpClient
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val networkModule = module {
    singleOf(::HttpClientFactory)
    singleOf(::NetworkResponseHandler)

    single<HttpClient>(qualifier = HttpClientQualifier.WorkoutQualifier) {
        get<HttpClientFactory>().createWorkoutHttpClient(
            networkResponseHandler = get()
        )
    }
    single<Ktorfit>(qualifier = KtorfitQualifier.WorkoutQualifier) {
        ktorfit {
            baseUrl(url = API_BASE_URL)
            httpClient(client = get(HttpClientQualifier.WorkoutQualifier))
        }
    }


    // single<WorkoutService> {
    //get<Ktorfit>(qualifier = KtorfitQualifier.WorkoutQualifier).createWorkoutService()
    //}
}