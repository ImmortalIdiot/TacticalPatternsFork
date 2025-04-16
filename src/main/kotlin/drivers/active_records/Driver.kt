package drivers.active_records

import java.time.LocalDateTime

data class Driver(
    val id: Int,
    val fullName: FullName,
    var isActive: Boolean,
    private val schedule: MutableList<Shift> = mutableListOf()
) {

    /**
     * Add the shift to the driver if it doesn't overlap with other shifts
     */
    infix fun addShift(shift: Shift) {
        require(isActive) {
            "Водитель неактивен. Смену нельзя добавить."
        }

        if (schedule.any { it.overlapsWith(shift) }) {
            throw IllegalArgumentException("Смена пересекается с существующей")
        }

        schedule += shift
    }

    /**
     * Returns all shifts that are on the same date
     */
    fun shiftOn(date: LocalDateTime): List<Shift> = schedule.filter { it.isOnDate(date = date) }

    /**
     * A driver is available at this time if he doesn't have the shift at this moment
     */
    infix fun isAvailableAt(dateTime: LocalDateTime): Boolean = schedule.none { it.isDuring(dateTime = dateTime) }
}
