package nl.avwie.kanvas.ecs

import kotlin.test.*

class QueryTests {

    @Test
    fun query1() {
        val q1 = Query(String::class)
        assertTrue(q1.applies(listOf(String::class, Int::class)))
        assertFalse(q1.applies(listOf(Int::class, Long::class)))
    }

    @Test
    fun query2() {
        val q2 = Query(String::class, Int::class)
        assertTrue(q2.applies(listOf(String::class, Int::class)))
        assertFalse(q2.applies(listOf(Int::class, Long::class)))
    }

    @Test
    fun queryResult() {
        val backend = ArrayBackend()
        val e1 = backend.create()
        val e2 = backend.create()
        val e3 = backend.create()

        backend.set(e1, "Foo")
        backend.set(e1, 42)
        backend.set(e2, "Bar")
        backend.set(e2, 24)
        backend.set(e3, 66)

        val q2 = Query(String::class, Int::class)
        q2.result(e1, backend)!!.let { (name, age) ->
            assertEquals("Foo", name)
            assertEquals(42, age)
        }

        q2.result(e2, backend)!!.let { (name, age) ->
            assertEquals("Bar", name)
            assertEquals(24, age)
        }
        assertNull(q2.result(e3, backend))
    }
}