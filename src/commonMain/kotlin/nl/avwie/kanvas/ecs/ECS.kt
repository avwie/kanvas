package nl.avwie.kanvas.ecs

import kotlin.reflect.KClass

typealias Entity = Index

interface ECS  {
    fun create(): Entity
    fun destroy(entity: Entity): Boolean
    fun exists(entity: Entity): Boolean

    fun <T : Any> set(entity: Entity, component: T)
    fun <T : Any> get(entity: Entity, kClass: KClass<T>): T?
    fun <T : Any> remove(entity: Entity, kClass: KClass<T>): T?

    fun <T : Any> setResource(resource: T)
    fun <T : Any> getResource(kClass: KClass<T>): T?
    fun <T : Any> removeResource(kClass: KClass<T>): T?

    companion object {
        fun default(): ECS = ArrayECS()
    }
}

inline fun <reified T : Any> ECS.get(entity: Entity): T? = this.get(entity, T::class)
inline fun <reified T : Any> ECS.remove(entity: Entity): T? = this.remove(entity, T::class)
inline fun <reified T : Any> ECS.getResource(): T? = this.getResource(T::class)
inline fun <reified T : Any> ECS.removeResource(): T? = this.removeResource(T::class)
