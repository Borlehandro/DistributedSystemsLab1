package me.borlehandro.distributed_systems.lab1.cli

import org.apache.commons.cli.*

class CliOptions(args: Array<String>) {

    companion object {

        private const val FILE_OPTION = "f"
        private const val HELP_OPTION = "h"
        private const val LONG_FILE_OPTION = "file"
        private const val LONG_HELP_OPTION = "help"
    }

    private val options: Options = makeOptions()
    private val cli: CommandLine = DefaultParser().parse(options, args, true)

    fun printHelp() = HelpFormatter()
        .printHelp("Extract and analyze Open Street Map data", options)

    // Option value can be null by contract
    fun fileName(): String? = cli.getOptionValue(LONG_FILE_OPTION)

    fun needsHelp() = cli.hasOption(LONG_HELP_OPTION)

    private fun makeOptions(): Options {

        val readFileOption = Option.builder(FILE_OPTION)
            .hasArg(true)
            .longOpt(LONG_FILE_OPTION)
            .desc("Specify path to file.")
            .build()

        val helpOption = Option.builder()
            .option(HELP_OPTION)
            .longOpt(LONG_HELP_OPTION)
            .desc("Print help message.")
            .build()

        return Options().apply {
            addOption(readFileOption)
            addOption(helpOption)
        }
    }
}
