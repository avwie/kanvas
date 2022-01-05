package nl.avwie.kanvas.ecs

import kotlin.reflect.KClass

class ObservableBackend(private val backend: Backend) : Backend by backend {
    interface Observer {
        fun onCreate(entity: Entity)
        fun onDestroy(entity: Entity)
        fun <T : Any> onSet(entity: Entity, component: T)
        fun <T : Any> onRemove(entity: Entity, component: T)

        fun <T : Any> onSetResource(resource: T)
        fun <T : Any> onRemoveResource(resource: T)
    }

    fun interface Unregister {
        operator fun invoke()
    }

    private val observers = mutableListOf<Observer>()

    fun register(observer: Observer): Unregister {
        observers.add(observer)
        return Unregister { observers.remove(observer) }
    }

    override fun create(): Entity {
        return backend.create().also { entity -> observers.forEach { it.onCreate(entity) } }
    }

    override fun destroy(entity: Entity): Boolean {
        return backend.destroy(entity).also { result ->
            if (result) observers.forEach { it.onDestroy(entity) }
        }
    }

    override fun <T : Any> set(entity: Entity, component: T) {
        return backend.set(entity, component).also { observers.forEach { it.onSet(entity, component) } }
    }

    override fun <T : Any> remove(entity: Entity, kClass: KClass<T>): T? {
        return backend.remove(entity, kClass)?.also { removed -> observers.forEach { it.onRemove(entity, removed) } }
    }

    override fun <T : Any> setResource(resource: T) {
        return backend.setResource(resource).also { observers.forEach { it.onSetResource(resource) } }
    }

    override fun <T : Any> removeResource(kClass: KClass<T>): T? {
        return backend.removeResource(kClass)?.also { removed -> observers.forEach { it.onRemoveResource(removed) } }
    }
}