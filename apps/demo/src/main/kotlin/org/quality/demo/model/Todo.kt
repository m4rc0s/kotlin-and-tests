package org.quality.demo.model

import java.time.LocalDateTime
import java.util.UUID

data class Todo(
    val id: UUID = UUID.randomUUID(),
    val description: String,
    val dueDate: LocalDateTime
)
