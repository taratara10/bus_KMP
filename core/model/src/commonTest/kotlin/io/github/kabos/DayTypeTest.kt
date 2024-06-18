package io.github.kabos

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.test.Test
import kotlin.test.assertEquals

class DayTypeTest {
    @Test
    fun `ReturnsSaturday_WhenDateIsSaturday`() {
        val testClock = FixedClock(Instant.parse("2024-06-01T00:00:00+09:00"))
        assertEquals(
            expected = DayType.Saturday,
            actual = testClock.toDayType(),
        )
    }

    @Test
    fun `ReturnsHoliday_WhenDateIsSunday`() {
        val testClock = FixedClock(Instant.parse("2024-06-02T00:00:00+09:00"))
        assertEquals(
            expected = DayType.Holiday,
            actual = testClock.toDayType(),
        )
    }

    @Test
    fun `ReturnsWeekday_WhenDateIsMonday`() {
        val testClock = FixedClock(Instant.parse("2024-06-03T00:00:00+09:00"))
        assertEquals(
            expected = DayType.Weekday,
            actual = testClock.toDayType(),
        )
    }
}

class FixedClock(private val instant: Instant) : Clock {
    override fun now(): Instant = instant
}