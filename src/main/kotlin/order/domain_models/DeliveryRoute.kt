package order.domain_models

data class DeliveryRoute(val route: String, val days: Int) {
    init {
        require(route.isNotBlank()) {
            "Маршрут доставки не может быть пустым"
        }

        require(days > 0) {
            "Количество дней должно быть положительным"
        }
    }
}
