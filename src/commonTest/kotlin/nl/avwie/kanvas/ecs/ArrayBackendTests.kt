package nl.avwie.kanvas.ecs

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ArrayBackendTests {

    @Test
    fun createAndDestroy() {
        val backend = Backend.default()
        val entity = backend.create()

        assertTrue(backend.exists(entity))
        backend.destroy(entity)
        assertFalse(backend.exists(entity))
    }

    @Test
    fun setAndGet() {
        val backend = Backend.default()
        val e1 = backend.create()
        val e2 = backend.create()

        backend.set(e1, "Foo")
        backend.set(e2, "Bar")
        backend.set(e1, 42)
        backend.set(e2, 24)

        assertEquals("Foo", backend.get(e1))
        assertEquals("Bar", backend.get(e2))

        assertEquals(42, backend.get(e1))
        assertEquals(24, backend.get(e2))
    }

    @Test
    fun setAndRemove() {
        val backend = Backend.default()
        val e1 = backend.create()
        val e2 = backend.create()

        backend.set(e1, "Foo")
        backend.set(e2, "Bar")
        backend.set(e1, 42)
        backend.set(e2, 24)
        backend.remove<String>(e1)
        backend.remove<Int>(e2)

        assertEquals(null, backend.get<String>(e1))
        assertEquals("Bar", backend.get(e2))

        assertEquals(42, backend.get(e1))
        assertEquals(null, backend.get<Int>(e2))
    }

    @Test
    fun setAndOverwrite() {
        val backend = Backend.default()
        val e1 = backend.create()

        backend.set(e1, "Foo")
        backend.set(e1, "Foo2")

        assertEquals("Foo2", backend.get(e1))
    }

    @Test
    fun setAndDestroy() {
        val backend = Backend.default()
        val e1 = backend.create()
        val e2 = backend.create()

        backend.set(e1, "Foo")
        backend.set(e2, "Bar")
        backend.set(e1, 42)
        backend.set(e2, 24)
        backend.destroy(e1)
        backend.destroy(e2)

        assertEquals(null, backend.get<String>(e1))
        assertEquals(null, backend.get<String>(e2))
        assertEquals(null, backend.get<Int>(e1))
        assertEquals(null, backend.get<Int>(e2))
    }

    @Test
    fun resources() {
        val backend = Backend.default()

        backend.setResource("Foobar")
        backend.setResource("W00t")
        backend.setResource(42)
        backend.setResource(24)

        assertEquals("W00t", backend.getResource())
        assertEquals(24, backend.getResource())

        backend.removeResource<Int>()
        assertEquals(null, backend.getResource<Int>())
    }
}