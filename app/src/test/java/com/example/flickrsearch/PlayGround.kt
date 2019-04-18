package com.example.flickrsearch

import org.junit.Test

class PlayGround {


    interface Source<out T> {
        fun nextT(): T
    }

    // out 을 붙이면 리턴시 T의 상위 타입으로 다받을수있음
    //
    fun demo(strs: Source<String>) {
        val objects: Source<Any> = strs // This is OK, since T is an out-parameter
        // ...
    }

    // in을 붙이면 T의 하위 타입으로 받음
    // ex) 예제에서 1.0은 Number인데 Number의 하위타입인 Double로 받을수있음
    interface Comparable<in T> {
        operator fun compareTo(other: T): Int
    }

    fun demo(x: Comparable<Number>) {
        x.compareTo(1.0) // 1.0 has type Double, which is a subtype of Number
        // Thus, we can assign x to a variable of type Comparable<Double>
        val y: Comparable<Double> = x // OK!
    }
}