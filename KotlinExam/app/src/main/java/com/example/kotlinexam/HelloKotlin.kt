package com.example.kotlinexam

fun main(args: Array<String>){
    println(printHello())
    var hello:HelloKotlin = HelloKotlin()
    hello.greeting("Park")

}

fun printHello(): String {
    return "Hello Kotlin World!"
}

class HelloKotlin {
    fun greeting(name:String){
        println("Hello $name")
    }
}