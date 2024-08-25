package com.pixelh97.taskmanager.data.model

data class Project(
    val id: Int,
    val title: String,
    val description: String,
    val date: String,
    val tasks: List<Task>? = null,
)
