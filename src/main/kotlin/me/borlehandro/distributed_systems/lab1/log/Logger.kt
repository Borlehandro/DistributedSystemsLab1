package me.borlehandro.distributed_systems.lab1.log

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class Logger : ReadOnlyProperty<Any, Logger> {

    override fun getValue(thisRef: Any, property: KProperty<*>): Logger =
        LogManager.getLogger(thisRef::class.java)
}