package com.undercurrent.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId


fun nsToSeconds(ns: Long): Long {
    return ns / 1000000000
}

fun msToSeconds(ms: Long): Long {
    return ms / 1000
}

fun nsToMinutes(ns: Long): Long {
    val seconds = nsToSeconds(ns)
    return secondsToMinutes(seconds)
}

fun msToHours(ms: Long): Long {
    val seconds = msToSeconds(ms)
    return secondsToHours(seconds)
}

fun secondsToHours(seconds: Long): Long {
    val minutes = secondsToMinutes(seconds)
    val hours = minutesToHours(minutes)
    return hours
}

fun secondsToMinutes(seconds: Long): Long {
    return seconds / 60
}

fun minutesToHours(minutes: Long): Long {
    return minutes / 60
}

fun fetchTimeDiffs(now: Long, timestamp: Long): Long {
    return nsToMinutes(now - timestamp)
}

fun makeMinAgoString(
    now: Long, timestamp: Long
): String = "(updated ${fetchTimeDiffs(now, timestamp)} minutes ago)"


const val BILLION = 1000000000
const val MILLION = 1000000
const val THOUSAND = 1000

class EpochNano(
    valueIn: Long? = null
) {
    val value: Long = valueIn ?: getNanoLong()

    fun toLong(): Long {
        return value
    }

    companion object {
        private fun getNanoLong(nsInFuture: Long = 0L): Long {
            val now = Instant.now()
            return now.epochSecond * BILLION + now.nano + nsInFuture
        }

        fun fromEpochMilli(epochMilli: Long): EpochNano {
            val epochSecond = epochMilli / THOUSAND
            val nanoAdjustment = (epochMilli % THOUSAND) * MILLION
            return EpochNano(epochSecond * BILLION + nanoAdjustment)
        }

        fun fromEpochMicro(epochMicro: Long): EpochNano {
            val epochSecond = epochMicro / MILLION
            val nanoAdjustment = (epochMicro % MILLION) * THOUSAND
            return EpochNano(epochSecond * BILLION + nanoAdjustment)
        }

        fun localDateTimeToEpochNano(dateTime: LocalDateTime): EpochNano {
            val instant = dateTime.atZone(ZoneId.systemDefault()).toInstant()
            return EpochNano(instant.epochSecond * BILLION + instant.nano)
        }

        fun msInPast(millis: Long): EpochNano {
            val pastNano = getNanoLong(-millis * MILLION)
            return EpochNano(pastNano)
        }

        fun microInPast(micros: Long): EpochNano {
            val pastNano = getNanoLong(-micros * THOUSAND)
            return EpochNano(pastNano)
        }

        fun nanoInPast(nanos: Long): EpochNano {
            val pastNano = getNanoLong(-nanos)
            return EpochNano(pastNano)
        }

        fun secInFuture(secondsInFuture: Long): EpochNano {
            return EpochNano(getNanoLong(secondsInFuture * BILLION))
        }

        fun msInFuture(millisecondsInFuture: Long): EpochNano {
            return EpochNano(getNanoLong(millisecondsInFuture * MILLION))
        }
    }

    fun toLocalDateTime(): LocalDateTime {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(value / BILLION, value % BILLION), ZoneId.systemDefault())
    }

    fun toEpochMicro(): Long {
        return value / THOUSAND
    }

    fun toEpochMilli(): Long {
        return value / MILLION
    }
}
