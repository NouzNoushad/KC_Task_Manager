package com.example.taskmanager.common

enum class Screens {
    HOME_SCREEN,
    CREATE_TASK_SCREEN,
}

object Constants {
    val status = listOf("pending", "progress", "completed", "canceled")

    val categories = listOf(
        "personal",
        "family",
        "friends",
        "work",
        "date",
        "exercise",
        "sports",
        "shopping",
        "meditation",
        "food",
        "sleep",
        "clean",
        "read",
        "movie",
        "music",
        "gaming"
    )
}