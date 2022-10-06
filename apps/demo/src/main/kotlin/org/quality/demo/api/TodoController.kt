package org.quality.demo.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/todos")
class TodoController {

    @PostMapping
    fun create(): ResponseEntity<String> {
        return ResponseEntity.created(URI("/resource/path")).body("New Todo created")
    }

    @GetMapping
    fun getTodo(): ResponseEntity<String> {
        return ResponseEntity.ok("Heres your todo!")
    }
}
