package com.pixelh97.taskmanager.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.sql.Timestamp
import java.util.Calendar
import java.util.Date

@Parcelize
data class Task(
    val id: Int,
    val title: String,
    val description: String? = null,
    val createdAt: Date,
    val date: Timestamp?,
    val timeFrom: Timestamp?,
    val timeTo: Timestamp? = null,
    val state: TaskState,
) : Parcelable {
    fun formatDurationFromNow(): String {
        val currentDate = Calendar.getInstance().time
        val difference = currentDate.time - this.createdAt.time

        val seconds = difference / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        val months = days / 30
        val years = months / 12

        return when {
            years > 0 -> "$years years ago"
            months > 0 -> "$months months ago"
            days > 0 -> "$days days ago"
            hours > 0 -> "$hours hours ago"
            minutes > 0 -> "$minutes minutes ago"
            else -> "$seconds seconds ago"
        }
    }
}
