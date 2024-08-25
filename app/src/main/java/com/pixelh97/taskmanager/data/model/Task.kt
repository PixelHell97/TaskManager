package com.pixelh97.taskmanager.data.model

import java.sql.Timestamp

data class Task(
    val id: Int,
    val title: String,
    val description: String? = null,
    val date: Timestamp,
    val timeFrom: Timestamp,
    val timeTo: Timestamp? = null,
    val state: TaskState,
)
