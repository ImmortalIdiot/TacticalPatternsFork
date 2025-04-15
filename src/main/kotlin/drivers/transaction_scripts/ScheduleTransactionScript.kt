package drivers.transaction_scripts

import drivers.active_records.DriverRepository
import drivers.active_records.Shift
import java.time.LocalDateTime

class ScheduleTransactionScript(private val repository: DriverRepository) {

    /**
     * Adds a shift to the driver by their id
     */
    fun addShiftToDriver(driverId: Int, start: LocalDateTime, end: LocalDateTime) {
        val driver = repository.findByIdOrThrow(driverId)

        try {
            println("Добавление смены с $start до $end")
            driver addShift Shift(start, end)
            repository.save(driver)
            println("Смена успешно добавлена.")
        } catch (e: Exception) {
            println("Ошибка при добавлении смены: ${e.message}")
        }
    }

    /**
     * Returns all shifts for the driver on the given date
     */
    fun getShiftsOnDate(driverId: Int, date: LocalDateTime): List<Shift> {
        val driver = repository.findByIdOrThrow(driverId)
        return driver.shiftOn(date)
    }

    /**
     * Checks if the driver is available at the given time
     */
    fun isDriverAvailableAt(driverId: Int, dateTime: LocalDateTime): Boolean {
        val driver = repository.findByIdOrThrow(driverId)
        return driver isAvailableAt dateTime
    }
}
