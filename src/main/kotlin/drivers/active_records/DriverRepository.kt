package drivers.active_records

object DriverRepositoryFactory {
    fun create(): DriverRepository = DriverRepositoryImpl()
}

interface DriverRepository {

    fun findById(id: Int): Driver?

    fun findByIdOrThrow(id: Int): Driver

    fun save(driver: Driver)

    fun add(driver: Driver)
}

private class DriverRepositoryImpl : DriverRepository {

    private val drivers = mutableMapOf<Int, Driver>()

    override fun findById(id: Int): Driver? = drivers[id]

    override fun findByIdOrThrow(id: Int): Driver {
        return drivers[id] ?: throw IllegalArgumentException("Водитель с id $id не найден")
    }

    override fun save(driver: Driver) {
        drivers[driver.id] = driver
    }

    override fun add(driver: Driver) {
        require(drivers[driver.id] == null) { "Водитель с id ${driver.id} уже существует" }
        drivers[driver.id] = driver
    }
}
