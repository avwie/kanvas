package nl.avwie.kanvas.ecs

class ECS(private val backend: Backend, private val runner: SystemRunner) : Backend by backend, SystemRunner by runner