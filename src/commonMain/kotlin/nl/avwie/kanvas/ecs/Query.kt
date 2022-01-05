package nl.avwie.kanvas.ecs

import kotlin.reflect.KClass

interface Query<Q> {
    fun applies(componentTypes: Iterable<KClass<*>>): Boolean
    fun result(entity: Entity, backend: Backend): Q?

    companion object {
        operator fun <T1 : Any> invoke(k1: KClass<T1>) = object : Query<Result1<T1>> {
            private val types = setOf(k1)
            override fun applies(componentTypes: Iterable<KClass<*>>): Boolean = types.all { componentTypes.contains(it) }
            override fun result(entity: Entity, backend: Backend): Result1<T1>? {
                val c1 = backend.get(entity, k1) ?: return null
                return Result1(c1)
            }
        }

        operator fun <T1 : Any, T2 : Any> invoke(k1: KClass<T1>, k2: KClass<T2>) =
            object : Query<Result2<T1, T2>> {
                private val types = setOf(k1, k2)
                override fun applies(componentTypes: Iterable<KClass<*>>): Boolean =
                    types.all { componentTypes.contains(it) }

                override fun result(entity: Entity, backend: Backend): Result2<T1, T2>? {
                    val c1 = backend.get(entity, k1) ?: return null
                    val c2 = backend.get(entity, k2) ?: return null
                    return Result2(c1, c2)
                }
            }

        operator fun <T1 : Any, T2 : Any, T3 : Any> invoke(k1: KClass<T1>, k2: KClass<T2>, k3: KClass<T3>) =
            object : Query<Result3<T1, T2, T3>> {
                private val types = setOf(k1, k2, k3)
                override fun applies(componentTypes: Iterable<KClass<*>>): Boolean =
                    types.all { componentTypes.contains(it) }

                override fun result(entity: Entity, backend: Backend): Result3<T1, T2, T3>? {
                    val c1 = backend.get(entity, k1) ?: return null
                    val c2 = backend.get(entity, k2) ?: return null
                    val c3 = backend.get(entity, k3) ?: return null
                    return Result3(c1, c2, c3)
                }
            }

        operator fun <T1 : Any, T2 : Any, T3 : Any, T4: Any> invoke(k1: KClass<T1>, k2: KClass<T2>, k3: KClass<T3>, k4: KClass<T4>) =
            object : Query<Result4<T1, T2, T3, T4>> {
                private val types = setOf(k1, k2, k3, k4)
                override fun applies(componentTypes: Iterable<KClass<*>>): Boolean =
                    types.all { componentTypes.contains(it) }

                override fun result(entity: Entity, backend: Backend): Result4<T1, T2, T3, T4>? {
                    val c1 = backend.get(entity, k1) ?: return null
                    val c2 = backend.get(entity, k2) ?: return null
                    val c3 = backend.get(entity, k3) ?: return null
                    val c4 = backend.get(entity, k4) ?: return null
                    return Result4(c1, c2, c3, c4)
                }
            }

        operator fun <T1 : Any, T2 : Any, T3 : Any, T4: Any, T5: Any> invoke(k1: KClass<T1>, k2: KClass<T2>, k3: KClass<T3>, k4: KClass<T4>, k5: KClass<T5>) =
            object : Query<Result5<T1, T2, T3, T4, T5>> {
                private val types = setOf(k1, k2, k3, k4, k5)
                override fun applies(componentTypes: Iterable<KClass<*>>): Boolean =
                    types.all { componentTypes.contains(it) }

                override fun result(entity: Entity, backend: Backend): Result5<T1, T2, T3, T4, T5>? {
                    val c1 = backend.get(entity, k1) ?: return null
                    val c2 = backend.get(entity, k2) ?: return null
                    val c3 = backend.get(entity, k3) ?: return null
                    val c4 = backend.get(entity, k4) ?: return null
                    val c5 = backend.get(entity, k5) ?: return null
                    return Result5(c1, c2, c3, c4, c5)
                }
            }
    }

    data class Result1<T1>(val c1: T1)
    data class Result2<T1, T2>(val c1: T1, val c2: T2)
    data class Result3<T1, T2, T3>(val c1: T1, val c2: T2, val c3: T3)
    data class Result4<T1, T2, T3, T4>(val c1: T1, val c2: T2, val c3: T3, val c4: T4)
    data class Result5<T1, T2, T3, T4, T5>(val c1: T1, val c2: T2, val c3: T3, val c4: T4, val c5: T5)
}