package drivers.active_records

data class FullName(
    val firstName: String,
    val lastName: String,
    val middleName: String? = null
) {
    fun getFullName(): String {
        return if (middleName != null) {
            "$firstName $lastName $middleName"
        } else {
            "$firstName $lastName"
        }
    }
}
