package nl.avwie.kanvas.ecs

class IndexArray<T> {

    data class Entry<T>(val item: T, val generation: Int)

    private val buffer = ArrayList<Entry<T>?>()

    fun insert(index: Index, item: T) {
        while (buffer.size <= index.index) {
            buffer.add(null)
        }

        val prevGen = buffer.getOrNull(index.index)?.generation ?: -1
        require(prevGen <= index.generation) { "Attempted to write to IndexArray with an index from a previous generation" }

        buffer[index.index] = Entry(item, index.generation)
    }

    fun get(index: Index): T? {
        if (index.index >= buffer.size) return null

        val entry = buffer.getOrNull(index.index) ?: return null
        if (entry.generation != index.generation) return null
        return entry.item
    }

    fun remove(index: Index): T? {
        if (index.index >= buffer.size) return null
        val entry = buffer.getOrNull(index.index)
        buffer[index.index] = null
        return entry?.item
    }
}