package nl.avwie.kanvas.ecs

interface SystemRunner {
    fun <Q> register(system: System<Q>, query: Query<Q>): Unregister
    fun <Q> register(query: Query<Q>, block: (data: Iterable<Pair<Index, Q>>) -> Unit): Unregister = register(query.system(block), query)

    fun run()

    fun interface Unregister {
        operator fun invoke()
    }

    data class SystemAndQuery<Q>(val system: System<Q>, val query: Query<Q>) {
        fun run(entities: Iterable<Entity>, backend: Backend) {
            val data = entities.map { entity -> entity to query.result(entity, backend)!! }
            system.run(data)
        }

        fun run(backend: Backend) {
            val data = backend.entities()
                .asSequence()
                .filter { entity -> query.applies(backend.componentTypes(entity)) }
                .map { entity -> entity to query.result(entity, backend)!! }
            system.run(data.asIterable())
        }
    }

    companion object {
        fun default(backend: Backend): SystemRunner = SequentialSystemRunner(backend)
    }
}