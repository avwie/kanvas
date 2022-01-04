package nl.avwie.kanvas.ecs

class IndexAllocator {

    data class Entry(var isLive: Boolean, var generation: Int)

    private val free = ArrayDeque<Int>()
    private val entries = ArrayList<Entry>()

    fun allocate(): Index {
        val idx = free.removeFirstOrNull()
        return if (idx != null) {
            val entry = entries[idx.toInt()]
            entry.isLive = true
            entry.generation += 1
            Index(idx, entry.generation)
        } else {
            val entry = Entry(isLive = true, generation = 0)
            entries.add(entry)
            Index(entries.size - 1, entry.generation)
        }
    }

    fun deallocate(index: Index): Boolean {
        if (!isLive(index)) return false
        entries[index.index].isLive = false
        free.add(index.index)
        return true
    }

    fun isLive(index: Index): Boolean {
        if (index.index >= entries.size) return false
        if (index.generation != entries[index.index].generation) return false
        return entries[index.index].isLive
    }
}