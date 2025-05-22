package com.example.appfutbol.api



import com.example.appfutbol.model.MatchResponse
import retrofit2.Response
import retrofit2.http.GET

interface FootballApiService {
    @GET("/matches?status=SCHEDULED")
    suspend fun getMatches(): Response<MatchResponse>
}
