package me.borlehandro.distributed_systems.lab1.di

import me.borlehandro.distributed_systems.lab1.dao.BZip2Decompressor
import me.borlehandro.distributed_systems.lab1.dao.XmlParser
import me.borlehandro.distributed_systems.lab1.repository.OpenStreetMapRepository
import me.borlehandro.distributed_systems.lab1.repository.OpenStreetMapRepositoryImpl
import org.koin.dsl.module

val mainModule = module {

    // dao
    single { XmlParser() }
    single { BZip2Decompressor() }

    // repo
    single<OpenStreetMapRepository> { OpenStreetMapRepositoryImpl(get(), get()) }
}