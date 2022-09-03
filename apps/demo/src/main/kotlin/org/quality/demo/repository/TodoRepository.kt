package org.quality.demo.repository

import org.quality.demo.model.Todo
import org.springframework.stereotype.Component

@Component
class TodoRepository {

    fun save(todo: Todo): Todo {
        return todo;
    }

}