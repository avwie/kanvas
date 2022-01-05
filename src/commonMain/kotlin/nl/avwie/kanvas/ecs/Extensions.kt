package nl.avwie.kanvas.ecs

inline fun <reified T : Any> Backend.get(entity: Entity): T? = this.get(entity, T::class)
inline fun <reified T : Any> Backend.has(entity: Entity): Boolean = this.has(entity, T::class)
inline fun <reified T : Any> Backend.remove(entity: Entity): T? = this.remove(entity, T::class)
inline fun <reified T : Any> Backend.getResource(): T? = this.getResource(T::class)
inline fun <reified T : Any> Backend.removeResource(): T? = this.removeResource(T::class)