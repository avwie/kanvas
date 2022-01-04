package nl.avwie.kanvas.ecs

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ArrayECSTests {

    @Test
    fun createAndDestroy() {
        val ecs = ECS.default()
        val entity = ecs.create()

        assertTrue(ecs.exists(entity))
        ecs.destroy(entity)
        assertFalse(ecs.exists(entity))
    }

    @Test
    fun setAndGet() {
        val ecs = ECS.default()
        val e1 = ecs.create()
        val e2 = ecs.create()

        ecs.set(e1, "Foo")
        ecs.set(e2, "Bar")
        ecs.set(e1, 42)
        ecs.set(e2, 24)

        assertEquals("Foo", ecs.get(e1))
        assertEquals("Bar", ecs.get(e2))

        assertEquals(42, ecs.get(e1))
        assertEquals(24, ecs.get(e2))
    }

    @Test
    fun setAndRemove() {
        val ecs = ECS.default()
        val e1 = ecs.create()
        val e2 = ecs.create()

        ecs.set(e1, "Foo")
        ecs.set(e2, "Bar")
        ecs.set(e1, 42)
        ecs.set(e2, 24)
        ecs.remove<String>(e1)
        ecs.remove<Int>(e2)

        assertEquals(null, ecs.get<String>(e1))
        assertEquals("Bar", ecs.get(e2))

        assertEquals(42, ecs.get(e1))
        assertEquals(null, ecs.get<Int>(e2))
    }

    @Test
    fun setAndOverwrite() {
        val ecs = ECS.default()
        val e1 = ecs.create()

        ecs.set(e1, "Foo")
        ecs.set(e1, "Foo2")

        assertEquals("Foo2", ecs.get(e1))
    }

    @Test
    fun setAndDestroy() {
        val ecs = ECS.default()
        val e1 = ecs.create()
        val e2 = ecs.create()

        ecs.set(e1, "Foo")
        ecs.set(e2, "Bar")
        ecs.set(e1, 42)
        ecs.set(e2, 24)
        ecs.destroy(e1)
        ecs.destroy(e2)

        assertEquals(null, ecs.get<String>(e1))
        assertEquals(null, ecs.get<String>(e2))
        assertEquals(null, ecs.get<Int>(e1))
        assertEquals(null, ecs.get<Int>(e2))
    }

    @Test
    fun resources() {
        val ecs = ECS.default()

        ecs.setResource("Foobar")
        ecs.setResource("W00t")
        ecs.setResource(42)
        ecs.setResource(24)

        assertEquals("W00t", ecs.getResource())
        assertEquals(24, ecs.getResource())

        ecs.removeResource<Int>()
        assertEquals(null, ecs.getResource<Int>())
    }
}