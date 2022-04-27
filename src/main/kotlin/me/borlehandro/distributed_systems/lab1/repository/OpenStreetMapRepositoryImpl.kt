package me.borlehandro.distributed_systems.lab1.repository

import me.borlehandro.distributed_systems.lab1.dao.BZip2Decompressor
import me.borlehandro.distributed_systems.lab1.dao.ParsedXmlData
import me.borlehandro.distributed_systems.lab1.dao.XmlParser
import me.borlehandro.distributed_systems.lab1.exception.ParsingException
import me.borlehandro.distributed_systems.lab1.model.OpenStreetMapStatistic
import java.io.IOException

class OpenStreetMapRepositoryImpl(
    private val decompressor: BZip2Decompressor,
    private val parser: XmlParser
) : OpenStreetMapRepository {

    override fun getStatistic(
        filename: String,
        userChangesSort: OpenStreetMapRepository.SortType
    ): OpenStreetMapStatistic {
        try {
            return parser.parseXml(decompressor.getDecompressedInputStream(filename))
                .toStatistic(userChangesSort)
        } catch (e: Exception) {
            if(e is IOException) {
                throw ParsingException(
                    "Cannot read data from xml input stream, filename \"$filename\" might be wrong.",
                    cause = e
                )
            }
            throw ParsingException("Parsing gone wrong, check file format.", cause = e)
        }
    }
}

fun ParsedXmlData.toStatistic(userChangesSort: OpenStreetMapRepository.SortType) =
    OpenStreetMapStatistic(
        tagUsages.size,
        userChanges
            .map { it.key to it.value.size }
            .sortedBy {
                when (userChangesSort) {
                    OpenStreetMapRepository.SortType.ASC -> it.second
                    OpenStreetMapRepository.SortType.DESC -> -it.second
                }
            }
            .toMap(),
        tagUsages
    )