package me.borlehandro.distributed_systems.lab1

import me.borlehandro.distributed_systems.lab1.cli.CliOptions
import me.borlehandro.distributed_systems.lab1.log.Logger
import me.borlehandro.distributed_systems.lab1.model.OpenStreetMapStatistic
import me.borlehandro.distributed_systems.lab1.repository.OpenStreetMapRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.system.measureTimeMillis

class Application : KoinComponent {

    private val logger by Logger()

    private val repository by inject<OpenStreetMapRepository>()

    fun run(args: Array<String>) {
        val cliOptions = CliOptions(args)
        try {
            if (cliOptions.needsHelp()) {
                cliOptions.printHelp()
                return
            }

            val time = measureTimeMillis {
                val filename = cliOptions.fileName()
                if (filename == null) {
                    logger.error("Filename is empty, use --file or -f option to specify filename.")
                    return
                }

                logger.info("Start data parsing, it can takes some time...")
                logStatistics(
                    repository.getStatistic(
                        filename,
                        userChangesSort = OpenStreetMapRepository.SortType.DESC
                    )
                )
            }

            logger.info("Time to parsing and statistic calculations: $time ms")
        } catch (e: Throwable) {
            cliOptions.printHelp()
            logger.error(e.message)
        }
    }

    private fun logStatistics(parseResult: OpenStreetMapStatistic) {
        with(logger) {
            info("User changes number:")
            parseResult.userChanges
                .forEach { info("${it.key} - ${it.value}") }

            info("Unique tags used: ${parseResult.tagsUsed}")

            info("Tagged nodes number:")
            parseResult.tagUsages
                .forEach { (tag, uses) -> info("$tag - $uses") }
        }
    }
}