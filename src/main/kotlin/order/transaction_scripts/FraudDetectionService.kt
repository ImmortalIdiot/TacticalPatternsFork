package order.transaction_scripts

import order.domain_models.Message
import java.time.Duration
import java.time.LocalDateTime

class FraudDetectionService {
    fun isSuspicious(messages: List<Message>, timeFrameMinutes: Long = 5, maxMessages: Int = 5): Boolean {
        val now = LocalDateTime.now()
        val recentMessages = messages.count {
            Duration.between(it.timestamp, now).toMinutes() <= timeFrameMinutes
        }

        return recentMessages > maxMessages
    }
}
