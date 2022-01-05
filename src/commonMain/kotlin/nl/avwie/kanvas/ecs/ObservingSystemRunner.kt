package nl.avwie.kanvas.ecs

class ObservingSystemRunner(private val backend: ObservableBackend) : ObservableBackend.Observer, SystemRunner {

    val unregister: ObservableBackend.Unregister = backend.register(this)

    private val systems = mutableListOf<SystemRunner.SystemAndQuery<*>>()
    private val queries = mutableMapOf<Query<*>, MutableSet<Index>>()

    override fun <Q> register(system: System<Q>, query: Query<Q>): SystemRunner.Unregister {
        val systemAndQuery = SystemRunner.SystemAndQuery(system, query)
        systems.add(systemAndQuery)

        if (!queries.containsKey(query)) {
            val set = backend.entities().filter { entity ->
                query.applies(backend.componentTypes(entity))
            }
            queries[query] = set.toMutableSet()
        }

        return SystemRunner.Unregister {
            systems.remove(systemAndQuery)
            if (systems.none { it.query == query }) {
                queries.remove(query)
            }
        }
    }

    override fun run() {
        systems.forEach { systemAndQuery ->
            val entities = queries[systemAndQuery.query] ?: setOf()
            systemAndQuery.run(entities, backend)
        }
    }

    override fun onCreate(entity: Entity) {}
    override fun onDestroy(entity: Entity) {
        queries.values.forEach { set -> set.remove(entity) }
    }

    override fun <T : Any> onSet(entity: Entity, component: T) {
        queries.forEach { (query, set) ->
            if (query.applies(backend.componentTypes(entity))) {
                set.add(entity)
            }
        }
    }

    override fun <T : Any> onRemove(entity: Entity, component: T) {
        queries.forEach { (query, set) ->
            if (query.applies(backend.componentTypes(entity))) {
                set.remove(entity)
            }
        }
    }

    override fun <T : Any> onSetResource(resource: T) {}
    override fun <T : Any> onRemoveResource(resource: T) {}
}