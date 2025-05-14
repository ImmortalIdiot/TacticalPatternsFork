package drivers

import drivers.active_records.DriverFactory
import drivers.active_records.DriverRepositoryFactory
import drivers.active_records.FullName
import drivers.transaction_scripts.ScheduleTransactionScript
import java.time.LocalDateTime

val driverRepository = DriverRepositoryFactory.create()
val scheduleService = ScheduleTransactionScript(driverRepository)

fun main() {
    val driver = DriverFactory.create(FullName("Иван", "Иванов"))
    driverRepository.add(driver)

    val start = LocalDateTime.now()
    val end = start.plusHours(6)

    scheduleService.addShiftToDriver(driver.id, start, end)
    println(scheduleService.getShiftsOnDate(driver.id, start))
    println(scheduleService.isDriverAvailableAt(driver.id, start))
}