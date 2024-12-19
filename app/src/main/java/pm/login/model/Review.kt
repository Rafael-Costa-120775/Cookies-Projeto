package pm.login.model

data class Review(
    val movieName: String,
    val userId: Int,
    val username: String,
    val photo: String,
    val reviewText: String,
    val score: Double,
    val createdAt: String
)