package nl.avwie.kanvas.ecs

import kotlin.test.*

class IndexTests {

    @Test
    fun allocateAndDeallocate() {
        val allocator = IndexAllocator()
        val idx = allocator.allocate()

        assertTrue(allocator.isLive(idx))
        assertTrue(allocator.deallocate(idx))
        assertFalse(allocator.isLive(idx))
    }

    @Test
    fun allocateAndDeallocateNonExisiting() {
        val allocator = IndexAllocator()
        val idx = Index(123, 123)

        assertFalse(allocator.isLive(idx))
        assertFalse(allocator.deallocate(idx))
        assertFalse(allocator.isLive(idx))
    }

    @Test
    fun insertAndGet() {
        val array = IndexArray<String>()
        val idx = Index(123, 123)

        array.insert(idx, "Foo")
        assertEquals("Foo", array.get(idx))
        assertEquals(null, array.get(idx.copy(generation = 1)))
    }

    @Test
    fun insertAndRemove() {
        val array = IndexArray<String>()
        val idx = Index(123, 1)

        array.insert(idx, "Foo")
        array.remove(idx)
        assertEquals(null, array.get(idx))
    }

    @Test
    fun insertOld() {
        val array = IndexArray<String>()
        val idx1 = Index(123, 1)
        val idx2 = Index(123, 2)
        array.insert(idx2, "New")
        assertFailsWith<IllegalArgumentException> { array.insert(idx1, "Old") }
    }
}