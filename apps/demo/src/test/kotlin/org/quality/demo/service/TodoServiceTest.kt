package org.quality.demo.service
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.quality.demo.dtos.TodoDTO
import org.quality.demo.model.Todo
import org.quality.demo.repository.TodoRepository
import java.time.LocalDateTime

@ExtendWith(MockKExtension::class)
internal class TodoServiceTest {

    @MockK
    private lateinit var repository: TodoRepository

    @InjectMockKs
    private lateinit var service: TodoService

    @Test
    fun `should create new todo`() {

        val actualTodoDto = TodoDTO(
            description = "Sample Todo",
            dueDate = LocalDateTime.now().plusDays(2)
        )

        val expectedTodo = Todo(
            description = "Sample Todo",
            dueDate = LocalDateTime.now().plusDays(2)
        )

        every { repository.save(any()) } returns expectedTodo

        val actualTodo = service.Create(actualTodoDto)

        actualTodo.description shouldBe expectedTodo.description
        actualTodo.dueDate shouldNotBe LocalDateTime.now()

        verify(exactly = 1) { repository.save(any()) }

    }
}
