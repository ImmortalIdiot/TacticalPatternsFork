package order.active_records

import order.domain_models.TransportationOrder

object TransportationOrderRepositoryFactory {
    fun create(): OrderRepository = OrderRepositoryImpl()
}

interface OrderRepository {

    fun findById(id: Int): TransportationOrder?

    fun findByIdOrThrow(id: Int): TransportationOrder

    fun save(order: TransportationOrder)

    fun add(order: TransportationOrder)
}

private class OrderRepositoryImpl : OrderRepository {
    private val orders = mutableMapOf<Int, TransportationOrder>()

    override fun findById(id: Int): TransportationOrder? = orders[id]

    override fun findByIdOrThrow(id: Int): TransportationOrder {
        return orders[id] ?: throw IllegalArgumentException("Заявка с id $id не найдена")
    }

    override fun save(order: TransportationOrder) { orders[order.orderId] = order }

    override fun add(order: TransportationOrder) {
        require(orders[order.orderId] == null) {
            "Заявка с id ${order.orderId} уже существует"
        }

        orders[order.orderId] = order
    }
}
