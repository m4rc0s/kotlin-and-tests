package org.quality.demo.repository

import org.quality.demo.model.Todo
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class TodoRepository {

    fun save(todo: Todo): Todo {
        return todo;
    }

    fun update(todo: Todo): Todo {
        return todo
    }

}
