package nl.avwie.kanvas.ecs

import kotlin.test.Test

class SequentialSystemRunnerTests {

    @Test
    fun basicTest() {
        val backend = Backend.default()
        val runner = SystemRunner.default(backend)

        val system = System<> {  }
    }
}