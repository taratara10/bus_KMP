package io.github.kabos.extension

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.getOr
import com.github.michaelbull.result.runCatching
import kotlinx.datetime.LocalTime

/**
 * Subtracts the given [before] from this LocalTime, returning a new LocalTime.
 *
 * @param before The LocalTime earlier than this one.
 */
fun LocalTime.subtract(before: LocalTime): Result<LocalTime, Throwable> {
    return runCatching {
        val diff = this.toSecondOfDay() - before.toSecondOfDay()
        if (diff < 0) Err(IllegalArgumentException("before must be earlier than this"))
        LocalTime.fromSecondOfDay(diff)
    }
}

/**
 * @return The formatted time string in "HH:mm" format.
 */
fun LocalTime.toHHmm(): String {
    return runCatching {
        val hour = this.hour.toString().padStart(2, '0')
        val minute = this.minute.toString().padStart(2, '0')
        "$hour:$minute"
    }.getOr("")
}

/**
 * @return The formatted time string in "mm:ss" format.
 */
fun LocalTime.tommss(): String {
    return runCatching {
        val minute = (this.hour * 60 + this.minute).toString().padStart(2, '0')
        val second = this.second.toString().padStart(2, '0')
        "$minute:$second"
    }.getOr("")
}
