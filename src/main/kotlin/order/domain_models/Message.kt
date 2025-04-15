package order.domain_models

data class Message(
    val content: String,
    val sender: Participant,
    val recipient: Participant
) {

    init {
        require(sender != recipient) {
            "Сообщение нельзя подтвердить отправителем — только другой стороной."
        }
    }

    private val attachments = mutableListOf<Attachment>()

    fun getAttachments(): List<Attachment> = attachments.toList()

    fun addAttachment(attachment: Attachment) {
        attachments += attachment
    }

    infix fun isConfirmedBy(party: Participant): Boolean = party == recipient
}
