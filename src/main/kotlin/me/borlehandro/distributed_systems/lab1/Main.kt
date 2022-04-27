package me.borlehandro.distributed_systems.lab1

import me.borlehandro.distributed_systems.lab1.di.mainModule
import org.koin.core.context.startKoin

fun main(args: Array<String>) {
    setupKoin()
    Application().run(args)
}

private fun setupKoin() {
    startKoin {
        modules(mainModule)
    }
}
