package me.borlehandro.distributed_systems.lab1.model

data class OpenStreetMapStatistic(
    val tagsUsed: Int,
    val userChanges: Map<String, Int>,
    val tagUsages: Map<String, Int>,
)