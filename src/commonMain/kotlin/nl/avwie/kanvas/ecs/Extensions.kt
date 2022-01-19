package nl.avwie.kanvas.ecs

import kotlin.reflect.KClass

inline fun <reified T : Any> Backend.get(entity: Entity): T? = this.get(entity, T::class)
inline fun <reified T : Any> Backend.has(entity: Entity): Boolean = this.has(entity, T::class)
inline fun <reified T : Any> Backend.remove(entity: Entity): T? = this.remove(entity, T::class)
inline fun <reified T : Any> Backend.getResource(): T? = this.getResource(T::class)
inline fun <reified T : Any> Backend.removeResource(): T? = this.removeResource(T::class)

fun system(block: System.Scope.() -> Unit): System = object : System {
    override fun run(backend: Backend) {
        val scope = System.Scope(backend)
        block(scope)
    }
}

fun Backend.filter(vararg ks: KClass<out Any>): Iterable<Entity> = this.entities().filter { entity -> this.componentTypes(entity).let { cs -> ks.all { cs.contains(it) } } }
fun Iterable<Entity>.fetch(backend: Backend, vararg ts: KClass<out Any>): Iterable<List<Any>> {
    return this.map { entity ->
        ts.map { k -> backend.get(entity, k)!! }
    }
}



/*fun SystemRunner.register(block: SystemScope.() -> Unit): SystemRunner.Unregister {
    val system = object : System {
        override fun run(backend: Backend) {
            val scope = SystemScope(backend)
            block(scope)
        }
    }
    return this.register(system)
}*/