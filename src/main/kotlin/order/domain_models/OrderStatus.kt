package order.domain_models

enum class OrderStatus(val status: String) {
    ACTIVE("active"),
    CLOSED("closed"),
    PENDING("pending");

    companion object {
        fun fromString(status: String): OrderStatus {
            return entries.find { it.status == status }
                ?: throw IllegalArgumentException("Неверный статус заявки: $status")
        }
    }
}
