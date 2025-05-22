package com.example.appfutbol.model

data class MatchResponse(val matches: List<Match>)

data class Match(
    val id: Int,
    val utcDate: String,
    val homeTeam: Team,
    val awayTeam: Team
)

data class Team(val name: String)
