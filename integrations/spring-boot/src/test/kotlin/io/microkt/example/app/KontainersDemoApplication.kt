package io.microkt.example.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class KontainersDemoApplication

fun main(args: Array<String>) {
    runApplication<KontainersDemoApplication>(*args)
}
