package com.undercurrent.library.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

const val DEFAULT_DECIMAL_PRECISION = 30
const val DEFAULT_DECIMAL_SCALE = 18

fun BigDecimal.invert(): BigDecimal {
    return BigDecimal(1).divide(
        /* divisor = */ this,
        /* scale = */ DEFAULT_DECIMAL_SCALE,
        /* roundingMode = */ RoundingMode.HALF_UP
    )
}


object SystemUtils {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)
    fun executeRuntime(args: Array<String>) {
        Runtime.getRuntime().exec(args)
    }


    fun getSystemProperty(key: String): String? {
        return System.getProperty(key)
    }

    fun currentUTC(): LocalDateTime {
        return LocalDateTime.now(ZoneOffset.UTC)
    }

    fun systemLocale(): Locale = Locale.getDefault()

    private const val HOME_DIR = "user.home"
    val homePath: String by lazy {
        System.getProperty(HOME_DIR)
    }

    fun ensureDirectoryExistsOnSystem(systemPath: String) {
        if (logger.isInfoEnabled) {
            logger.info("Ensuring that $systemPath exists on system.")
        }

        with(File(systemPath)) {
            if (!exists()) {
                mkdirs()
                if (logger.isInfoEnabled) {
                    logger.info("Directory $systemPath created.")
                }
            } else {
                if (logger.isInfoEnabled) {
                    logger.info("Directory $systemPath already exists.")
                }
            }
        }
    }

}