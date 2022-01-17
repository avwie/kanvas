package nl.avwie.kanvas.ecs

import kotlin.test.Test
import kotlin.test.assertEquals

class SystemRunnerTests {

    @Test
    fun simple() {
        val names = mutableSetOf<String>()
        val ages = mutableSetOf<Int>()

        val ecs = ECS.default()
        val query = Query(String::class, Int::class)
        ecs.register {
            query().forEach { (name, age) ->
                names.add(name)
                ages.add(age)
            }
        }

        val e1 = ecs.create()
        val e2 = ecs.create()
        val e3 = ecs.create()

        ecs.set(e1, "Foo")
        ecs.set(e2, "Bar")
        ecs.set(e3, "Baz")

        ecs.set(e1, 42)
        ecs.set(e3, 24)

        ecs.run()

        assertEquals(2, names.size)
        assertEquals(2, ages.size)

        assertEquals(setOf("Foo", "Baz"), names)
        assertEquals(setOf(24, 42), ages)
    }
}