package org.quality.demo.service

import org.quality.demo.dtos.TodoDTO
import org.quality.demo.model.Todo
import org.quality.demo.repository.TodoRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.Date
import java.util.UUID

@Service
class TodoService(
    private val repository: TodoRepository
) {

    fun Create(todoDTO: TodoDTO):Todo {

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

    fun Update(todoId: UUID) = repository
            .update(
                Todo(
                    id = todoId,
                    description = "new desc",
                    dueDate = LocalDateTime.now()
                )
            )

    fun sayHello() {
        print("Now you has less than 80% of coverage!")
    }

}