package me.borlehandro.distributed_systems.lab1.repository

import me.borlehandro.distributed_systems.lab1.model.OpenStreetMapStatistic

interface OpenStreetMapRepository {

    enum class SortType {
        ASC,
        DESC
    }

    fun getStatistic(
        filename: String,
        userChangesSort: SortType = SortType.ASC
    ): OpenStreetMapStatistic
}