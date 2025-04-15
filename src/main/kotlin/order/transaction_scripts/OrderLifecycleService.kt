package order.transaction_scripts

import order.active_records.OrderRepository
import order.domain_models.OrderStatus

class OrderLifecycleService(private val repository: OrderRepository) {
    fun activateOrder(orderId: Int) {
        val order = repository.findByIdOrThrow(orderId)
        order.activate()
        repository.save(order)
    }

    fun closeOrder(orderId: Int) {
        val order = repository.findByIdOrThrow(orderId)
        order.close()
        repository.save(order)
    }

    fun getOrderStatus(orderId: Int): OrderStatus {
        val order = repository.findByIdOrThrow(orderId)
        return order.getStatus()
    }
}
