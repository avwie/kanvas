package nl.avwie.kanvas.ecs

import kotlin.reflect.KClass

interface Backend  {
    fun create(): Entity
    fun destroy(entity: Entity): Boolean
    fun exists(entity: Entity): Boolean
    fun entities(): Iterable<Entity>

    fun <T : Any> set(entity: Entity, component: T)
    fun <T : Any> has(entity: Entity, kClass: KClass<T>): Boolean
    fun <T : Any> get(entity: Entity, kClass: KClass<T>): T?
    fun <T : Any> remove(entity: Entity, kClass: KClass<T>): T?
    fun componentTypes(entity: Entity): Iterable<KClass<*>>
    fun componentTypes(): Iterable<KClass<*>>

    fun <T : Any> setResource(resource: T)
    fun <T : Any> getResource(kClass: KClass<T>): T?
    fun <T : Any> removeResource(kClass: KClass<T>): T?

    companion object {
        fun default(): Backend = ArrayBackend()
    }
}
