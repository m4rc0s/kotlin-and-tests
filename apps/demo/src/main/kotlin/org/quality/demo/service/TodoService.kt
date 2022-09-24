package org.quality.demo.service

import org.quality.demo.dtos.TodoDTO
import org.quality.demo.model.Todo
import org.quality.demo.repository.TodoRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

@Service
class TodoService(
    private val repository: TodoRepository
) {

    fun create(todoDTO: TodoDTO): Todo {

        if(todoDTO.dueDate <= LocalDateTime.now()) {
            throw Exception("Its impossible to complete in a time.")
        }

        return Todo(
            description = todoDTO.description,
            dueDate = todoDTO.dueDate
        ).let {
            repository.save(it)
        }

    }

    fun update(todoId: UUID) = repository
            .update(
                Todo(
                    id = todoId,
                    description = "new desc",
                    dueDate = LocalDateTime.now()
                )
            )

}