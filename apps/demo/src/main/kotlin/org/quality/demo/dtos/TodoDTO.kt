package org.quality.demo.dtos

import java.time.LocalDateTime

data class TodoDTO(
    val description: String,
    val dueDate: LocalDateTime
)
