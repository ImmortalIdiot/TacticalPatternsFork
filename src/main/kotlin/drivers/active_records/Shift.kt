package drivers.active_records

import java.time.LocalDateTime

data class Shift(val start: LocalDateTime, val end: LocalDateTime) {
    init {
        require(!end.isBefore(start)) {
            "Время окончания смены не может быть раньше начала смены"
        }
    }

    /**
     * A driver's shift can't start while another is still ongoing
     */
    fun overlapsWith(other: Shift): Boolean = !(end.isBefore(other.start) || start.isAfter(other.end))

    /**
     * A shift is considered to be on a date if it starts on the same day
     */
    fun isOnDate(date: LocalDateTime): Boolean = start.toLocalDate() == date.toLocalDate()

    /**
     * A shift includes a moment if it hasn't ended yet and it has already started
     */
    fun isDuring(dateTime: LocalDateTime): Boolean = !dateTime.isBefore(start) && !dateTime.isAfter(end)
}
