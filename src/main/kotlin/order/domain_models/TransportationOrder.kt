package order.domain_models

import java.time.Duration
import java.time.LocalDateTime

object TransportationOrderFactory {
    private var idCounter = 1

    fun create(route: DeliveryRoute): TransportationOrder {
        return TransportationOrder(
            orderId = idCounter++,
            status = OrderStatus.ACTIVE,
            route = route
        )
    }
}

data class TransportationOrder(
    val orderId: Int,
    val route: DeliveryRoute,
    private var status: OrderStatus = OrderStatus.PENDING,
    private var lastActivityAt: LocalDateTime = LocalDateTime.now()
) {
    private val messages = mutableListOf<Message>()

    fun addMessage(message: Message) {
        checkStatus()

        messages += message
        lastActivityAt = LocalDateTime.now()
    }

    fun getMessages(): List<Message> = messages.toList()

    fun getStatus(): OrderStatus {
        autoCloseIfInactive()
        return status
    }

    fun activate() {
        status = OrderStatus.ACTIVE
        lastActivityAt = LocalDateTime.now()
    }

    fun close() { status = OrderStatus.CLOSED }

    private fun checkStatus() {
        require(status == OrderStatus.ACTIVE) {
            "Действие недопустимо: заявка не активна"
        }
    }

    private fun autoCloseIfInactive() {
        val now = LocalDateTime.now()
        val inactiveDuration = Duration.between(lastActivityAt, now).toDays()
        if (inactiveDuration >= 3 && status == OrderStatus.ACTIVE) {
            status = OrderStatus.CLOSED
        }
    }
}
