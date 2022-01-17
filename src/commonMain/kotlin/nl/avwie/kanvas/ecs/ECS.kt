package nl.avwie.kanvas.ecs

class ECS(private val backend: Backend, private val runner: SystemRunner) : Backend by backend, SystemRunner by runner {

    fun run() {
        runner.run(backend)
    }

    companion object {
        fun default(): ECS = ECS(Backend.default(), SystemRunner.default())
    }
}