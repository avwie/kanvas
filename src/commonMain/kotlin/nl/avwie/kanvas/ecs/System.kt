package nl.avwie.kanvas.ecs

import kotlin.reflect.KClass

interface System {
    fun run(backend: Backend)

    data class Scope(val backend: Backend) {
        operator fun <R> Query<R>.invoke(): Iterable<R> = this.invoke(backend)

        fun <T1 : Any> query(t1: KClass<T1>) = Query(t1).invoke(backend)
        fun <T1 : Any, T2 : Any> query(t1: KClass<T1>, t2: KClass<T2>) = Query(t1, t2).invoke(backend)
        fun <T1 : Any, T2 : Any, T3 : Any> query(t1: KClass<T1>, t2: KClass<T2>, t3: KClass<T3>) = Query(t1, t2, t3).invoke(backend)
        fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any> query(t1: KClass<T1>, t2: KClass<T2>, t3: KClass<T3>, t4 : KClass<T4>) = Query(t1, t2, t3, t4).invoke(backend)
        fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any> query(t1: KClass<T1>, t2: KClass<T2>, t3: KClass<T3>, t4 : KClass<T4>, t5: KClass<T5>) = Query(t1, t2, t3, t4, t5).invoke(backend)

        fun <R : Any> resource(r: KClass<R>) = backend.getResource(r)
        inline fun <reified R : Any> resource(): R? = backend.getResource()
    }
}