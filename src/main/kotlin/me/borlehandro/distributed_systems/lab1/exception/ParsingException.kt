package me.borlehandro.distributed_systems.lab1.exception

import java.lang.Exception

class ParsingException(
    message: String? = null,
    cause: Exception? = null
) : Exception(message, cause)