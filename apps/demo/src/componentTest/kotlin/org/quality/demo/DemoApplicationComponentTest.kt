package org.quality.demo


import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@Suppress("FunctionNaming")
@SpringBootTest
class DemoApplicationComponentTest {

    @Test
    fun `should do something`() {
        val a = true;

        a shouldBe true;
    }

}
