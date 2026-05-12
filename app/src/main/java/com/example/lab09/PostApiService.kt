import retrofit2.http.GET
import retrofit2.http.Path

interface PostApiService {
    // @GET indica que es una petición de lectura al endpoint "posts" [cite: 35, 36]
    @GET("posts")
    suspend fun getUserPosts(): List<PostModel>

    // {id} es un parámetro dinámico que se reemplaza con @Path 
    @GET("posts/{id}")
    suspend fun getUserPostById(
        @Path("id") id: Int
    ): PostModel
}