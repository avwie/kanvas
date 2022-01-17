package nl.avwie.kanvas.ecs

import kotlin.reflect.KClass

interface Query<R> {
    operator fun invoke(backend: Backend): Iterable<R>

    companion object {

        private fun Iterable<Entity>.fetch(backend: Backend, vararg ts: KClass<out Any>): Iterable<List<Any>> {
            return this.map { entity ->
                ts.map { k -> backend.get(entity, k)!! }
            }
        }

        @Suppress("UNCHECKED_CAST")
        operator fun <T1 : Any> invoke(t1: KClass<T1>) = object : Query<Result1<T1>> {
            override operator fun invoke(backend: Backend) = backend.filter(t1).fetch(backend, t1)
                .map { (c1) -> Result1(c1 as T1) }
        }

        @Suppress("UNCHECKED_CAST")
        operator fun <T1 : Any, T2 : Any> invoke(t1: KClass<T1>, t2: KClass<T2>) = object : Query<Result2<T1, T2>> {
            override operator fun invoke(backend: Backend) = backend.filter(t1, t2).fetch(backend, t1, t2)
                .map { (c1, c2) -> Result2(c1 as T1, c2 as T2) }
        }

        @Suppress("UNCHECKED_CAST")
        operator fun <T1 : Any, T2 : Any, T3 : Any> invoke(t1: KClass<T1>, t2: KClass<T2>, t3: KClass<T3>) = object : Query<Result3<T1, T2, T3>> {
            override operator fun invoke(backend: Backend) = backend.filter(t1, t2, t3).fetch(backend, t1, t2, t3)
                .map { (c1, c2, c3) -> Result3(c1 as T1, c2 as T2, c3 as T3) }
        }

        @Suppress("UNCHECKED_CAST")
        operator fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any> invoke(t1: KClass<T1>, t2: KClass<T2>, t3: KClass<T3>, t4: KClass<T4>) = object : Query<Result4<T1, T2, T3, T4>> {
            override operator fun invoke(backend: Backend) = backend.filter(t1, t2, t3, t4).fetch(backend, t1, t2, t3, t4)
                .map { (c1, c2, c3, c4) -> Result4(c1 as T1, c2 as T2, c3 as T3, c4 as T4) }
        }

        @Suppress("UNCHECKED_CAST")
        operator fun <T1 : Any, T2 : Any, T3 : Any, T4: Any, T5: Any> invoke(t1: KClass<T1>, t2: KClass<T2>, t3: KClass<T3>, t4: KClass<T4>, t5: KClass<T5>) = object : Query<Result5<T1, T2, T3, T4, T5>> {
            override operator fun invoke(backend: Backend) = backend.filter(t1, t2, t3, t4, t5).fetch(backend, t1, t2, t3, t4, t5)
                .map { (c1, c2, c3, c4, c5) -> Result5(c1 as T1, c2 as T2, c3 as T3, c4 as T4, c5 as T5) }
        }
    }

    data class Result1<T1>(val c1: T1)
    data class Result2<T1, T2>(val c1: T1, val c2: T2)
    data class Result3<T1, T2, T3>(val c1: T1, val c2: T2, val c3: T3)
    data class Result4<T1, T2, T3, T4>(val c1: T1, val c2: T2, val c3: T3, val c4: T4)
    data class Result5<T1, T2, T3, T4, T5>(val c1: T1, val c2: T2, val c3: T3, val c4: T4, val c5: T5)
}