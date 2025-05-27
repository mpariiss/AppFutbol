package com.example.appfutbol.api



import com.example.appfutbol.model.MatchResponse
import retrofit2.Response
import retrofit2.http.GET

interface FootballApiService {
    @GET("matches?competitions=PD&dateFrom=2025-05-19&dateTo=2025-05-26")
    suspend fun getMatches(): Response<MatchResponse>
}
