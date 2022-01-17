package nl.avwie.kanvas.ecs

import kotlin.reflect.KClass

inline fun <reified T : Any> Backend.get(entity: Entity): T? = this.get(entity, T::class)
inline fun <reified T : Any> Backend.has(entity: Entity): Boolean = this.has(entity, T::class)
inline fun <reified T : Any> Backend.remove(entity: Entity): T? = this.remove(entity, T::class)
inline fun <reified T : Any> Backend.getResource(): T? = this.getResource(T::class)
inline fun <reified T : Any> Backend.removeResource(): T? = this.removeResource(T::class)

fun Backend.filter(vararg ks: KClass<out Any>): Iterable<Entity> = this.entities().filter { entity -> this.componentTypes(entity).let { cs -> ks.all { cs.contains(it) } } }

data class SystemScope(val backend: Backend) {
    operator fun <R> Query<R>.invoke(): Iterable<R> = this.invoke(backend)
}

fun SystemRunner.register(block: SystemScope.() -> Unit): SystemRunner.Unregister {
    val system = object : System {
        override fun run(backend: Backend) {
            val scope = SystemScope(backend)
            block(scope)
        }
    }
    return this.register(system)
}