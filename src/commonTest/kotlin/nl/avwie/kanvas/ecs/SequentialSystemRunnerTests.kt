package nl.avwie.kanvas.ecs

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SequentialSystemRunnerTests {

    @Test
    fun simple() {
        val backend = Backend.default()
        val runner = SystemRunner.default(backend)

        val names = mutableSetOf<String>()
        val ages = mutableSetOf<Int>()
        runner.register(Query(String::class, Int::class)) { data ->
            data.components().forEach { (name, age) ->
                names.add(name)
                ages.add(age)
            }
        }

        val e1 = backend.create()
        val e2 = backend.create()
        val e3 = backend.create()

        backend.set(e1, "Foo")
        backend.set(e2, "Bar")
        backend.set(e1, 42)
        backend.set(e2, 24)
        backend.set(e3, "Omitted")

        runner.run()
        assertEquals(2, names.size)
        assertEquals(2, ages.size)
        assertTrue { names.containsAll(listOf("Foo", "Bar")) }
        assertTrue { ages.containsAll(listOf(24, 42)) }
    }
}