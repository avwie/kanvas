package nl.avwie.kanvas.ecs

class SequentialSystemRunner : SystemRunner {
    private val systems = mutableListOf<System>()

    override fun register(system: System): SystemRunner.Unregister {
        systems.add(system)
        return SystemRunner.Unregister{ systems.remove(system) }
    }

    override fun run(backend: Backend) {
        systems.forEach { system -> system.run(backend) }
    }
}