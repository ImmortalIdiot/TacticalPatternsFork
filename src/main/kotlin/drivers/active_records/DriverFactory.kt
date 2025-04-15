package drivers.active_records

object DriverFactory {
    private var idCounter: Int = 1

    fun create(fullName: String, isActive: Boolean = true): Driver {
        return Driver(idCounter++, fullName, isActive)
    }
}
