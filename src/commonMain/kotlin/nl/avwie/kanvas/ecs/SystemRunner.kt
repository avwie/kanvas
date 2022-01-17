package nl.avwie.kanvas.ecs

interface SystemRunner {
    fun register(system: System): Unregister
    fun run(backend: Backend)

    fun interface Unregister {
        operator fun invoke()
    }

    companion object {
        fun default(): SystemRunner = SequentialSystemRunner()
    }
}