package com.reyy.week6labassigment.data

import kotlinx.serialization.Serializable

data class Community(
    val id: String,
    val name: String,
    val description: String,
    val memberCount: Int,
    val isJoined: Boolean = false,
    val type: String = ""
)

data class Project(
    val id: String,
    val communityId: String,
    val title: String,
    val description: String,
    val techStack: List<String>,
    val isCompleted: Boolean = false
)

@Serializable
object DashboardRoute

@Serializable
object CommunitiesRoute

@Serializable
data class ClubProjectsRoute(val communityId: String)

@Serializable
data class ProjectDetailRoute(val projectId: String)