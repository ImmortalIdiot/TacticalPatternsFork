package order.domain_models

enum class Participant(val participant: String) {
    CLIENT("client"),
    DRIVER("driver");

    companion object {
        fun fromString(participant: String): Participant {
            return entries.find { it.participant == participant }
                ?: throw IllegalArgumentException("Неверное имя участника")
        }
    }
}
