package nl.avwie.kanvas.ecs

interface System {
    fun run(backend: Backend)
}