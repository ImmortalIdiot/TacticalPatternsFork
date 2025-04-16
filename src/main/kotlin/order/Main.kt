package order

import order.domain_models.*
import order.transaction_scripts.FraudDetectionService
import order.transaction_scripts.OrderLifecycleService
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

val repository = TransportationOrderRepositoryFactory.create()
val lifecycleService = OrderLifecycleService(repository)
val fraudDetectionService = FraudDetectionService()

fun main() {
    val route = DeliveryRoute("Санкт-Петербург – Нижний Новгород", 4)
    val order = TransportationOrderFactory.create(route)

    repository.add(order)
    println("Создана заявка #${order.orderId} со статусом: ${order.getStatus()}")

    lifecycleService.activateOrder(order.orderId)
    println("Заявка активирована. Текущий статус: ${lifecycleService.getOrderStatus(order.orderId)}")

    val message1 = Message(
        content = "Груз отправлен со склада",
        sender = Participant.CLIENT,
        recipient = Participant.DRIVER
    )
    message1.addAttachment(Attachment("packing_list.pdf", "application/pdf"))

    order.addMessage(message1)
    println("Добавлено сообщение от клиента: \"${message1.content}\" с вложениями: ${message1.getAttachments().size}")

    println("Сообщение подтверждено водителем: ${message1 isConfirmedBy Participant.DRIVER}")
    println("Сообщение подтверждено клиентом (должно быть false): ${message1 isConfirmedBy Participant.CLIENT}")

    repeat(5) {
        order.addMessage(
            Message(
                content = "Сообщение $it",
                sender = Participant.CLIENT,
                recipient = Participant.DRIVER
            )
        )
    }

    val isSuspicious = fraudDetectionService.isSuspicious(order.getMessages())
    println("Обнаружена подозрительная активность? $isSuspicious")

    println("Эмуляция бездействия более 3-х дней...")

    val lastActivityField = TransportationOrder::class.java.getDeclaredField("lastActivityAt")
    lastActivityField.isAccessible = true
    lastActivityField.set(order, LocalDateTime.now().minus(4, ChronoUnit.DAYS))

    val statusAfterInactivity = order.getStatus()
    println("Статус заявки после проверки на неактивность: $statusAfterInactivity")

    lifecycleService.closeOrder(order.orderId)
    println("Статус после ручного закрытия: ${lifecycleService.getOrderStatus(order.orderId)}")
}
