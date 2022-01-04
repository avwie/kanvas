package nl.avwie.kanvas.ecs

import kotlin.reflect.KClass

class ArrayECS : ECS {
    private val allocator = IndexAllocator()
    private val resources = hashMapOf<KClass<*>, Any?>()
    private val components = hashMapOf<KClass<*>, IndexArray<*>>()

    override fun create(): Index = allocator.allocate()
    override fun destroy(entity: Index): Boolean = allocator.deallocate(entity)
    override fun exists(entity: Index): Boolean = allocator.isLive(entity)

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> set(entity: Entity, component: T) {
        indexArrayOf(component::class as KClass<T>).insert(entity, component)
    }

    override fun <T : Any> get(entity: Entity, kClass: KClass<T>): T? {
        if (!allocator.isLive(entity)) return null
        return indexArrayOf(kClass).get(entity)
    }

    override fun <T : Any> remove(entity: Entity, kClass: KClass<T>): T? {
        if (!allocator.isLive(entity)) return null
        return indexArrayOf(kClass).remove(entity)
    }

    override fun <T : Any> setResource(resource: T) {
        resources[resource::class] = resource
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> getResource(kClass: KClass<T>): T? = resources[kClass] as? T

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> removeResource(kClass: KClass<T>): T? = resources.remove(kClass) as? T

    @Suppress("UNCHECKED_CAST")
    private fun <T : Any> indexArrayOf(kClass: KClass<T>): IndexArray<T> {
        return components.getOrPut(kClass) { IndexArray<T> ()} as IndexArray<T>
    }
}