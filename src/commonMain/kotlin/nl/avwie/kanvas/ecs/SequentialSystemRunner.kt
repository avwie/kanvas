package nl.avwie.kanvas.ecs

class SequentialSystemRunner(private val backend: Backend) : SystemRunner {

    private val systems = mutableListOf<SystemRunner.SystemAndQuery<*>>()

    override fun <Q> register(system: System<Q>, query: Query<Q>): SystemRunner.Unregister {
        val systemAndQuery = SystemRunner.SystemAndQuery(system, query)
        systems.add(systemAndQuery)
        return SystemRunner.Unregister { systems.remove(systemAndQuery) }
    }

    override fun run() {
        systems.forEach { systemAndQuery ->
            systemAndQuery.run(backend)
        }
    }
}