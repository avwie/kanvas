package nl.avwie.kanvas.ecs

fun interface System<Q> {
    fun run(data: Iterable<Pair<Index, Q>>)
}