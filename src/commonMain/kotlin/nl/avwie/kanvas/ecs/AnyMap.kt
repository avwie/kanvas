package nl.avwie.kanvas.ecs

import kotlin.reflect.KClass

class AnyMap {
    private val buffer = hashMapOf<KClass<*>, Any>()

    fun <T : Any> insert(item: T) {
        buffer[item::class] = item
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> get(kClass: KClass<T>): T? = buffer[kClass] as? T

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> remove(kClass: KClass<T>): T? = buffer.remove(kClass) as? T
}