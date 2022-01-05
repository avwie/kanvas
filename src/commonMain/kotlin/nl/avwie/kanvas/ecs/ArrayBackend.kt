package nl.avwie.kanvas.ecs

import kotlin.reflect.KClass

class ArrayBackend : Backend {
    private val allocator = IndexAllocator()
    private val resources = AnyMap()
    private val components = hashMapOf<KClass<*>, IndexArray<*>>()
    private val componentTypes = IndexArray<MutableSet<KClass<*>>>()

    override fun create(): Index = allocator.allocate()
    override fun destroy(entity: Index): Boolean = allocator.deallocate(entity)
    override fun exists(entity: Index): Boolean = allocator.isLive(entity)
    override fun entities(): Iterable<Entity> = allocator.indices

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> set(entity: Entity, component: T) {
        indexArrayOf(component::class as KClass<T>).insert(entity, component)
        componentTypes.getOrPut(entity) { mutableSetOf() }.add(component::class)
    }

    override fun <T : Any> get(entity: Entity, kClass: KClass<T>): T? {
        if (!allocator.isLive(entity)) return null
        return indexArrayOf(kClass).get(entity)
    }

    override fun <T : Any> has(entity: Entity, kClass: KClass<T>): Boolean {
        if (!allocator.isLive(entity)) return false
        return componentTypes.get(entity)?.contains(kClass) ?: false
    }

    override fun <T : Any> remove(entity: Entity, kClass: KClass<T>): T? {
        if (!allocator.isLive(entity)) return null
        componentTypes.get(entity)!!.remove(kClass)
        return indexArrayOf(kClass).remove(entity)
    }

    override fun componentTypes(entity: Entity): Iterable<KClass<*>> {
        return componentTypes.get(entity) ?: listOf()
    }

    override fun componentTypes(): Iterable<KClass<*>> {
        return components.keys
    }

    override fun <T : Any> setResource(resource: T) {
        resources.insert(resource)
    }

    override fun <T : Any> getResource(kClass: KClass<T>): T? = resources.get(kClass)

    override fun <T : Any> removeResource(kClass: KClass<T>): T? = resources.remove(kClass)

    @Suppress("UNCHECKED_CAST")
    private fun <T : Any> indexArrayOf(kClass: KClass<T>): IndexArray<T> {
        return components.getOrPut(kClass) { IndexArray<T> ()} as IndexArray<T>
    }
}