import com.google.gson.annotations.SerializedName

data class PostModel(
    // La anotación @SerializedName vincula la clave exacta del JSON con nuestra variable [cite: 30, 31]
    @SerializedName("userId")
    val userId: Int,

    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("body")
    val body: String,

    // Estos campos son opcionales en el JSON del lab, por eso usamos el signo '?' (Nullable) [cite: 30]
    @SerializedName("link")
    val link: String? = null,

    @SerializedName("comment_count")
    val commentCount: Int? = null
)