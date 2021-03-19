package castelles.com.github.pageheroes.data

data class Characters(
    val code: Int,
    val status: String,
    val etag: String,
    val data: Data
)

data class Data(
    val offset: Long,
    val limit: Long,
    val total: Long,
    val results: List<Character>
)

data class Character(
    val id: Long,
    val name: String,
    val description: String,
    val thumbnail: Thumbnail,
    val comics: Comics,
    val series: Series,
    val stories: Stories
)

data class Thumbnail(
    val path: String,
    val extension: String
)

data class Comics(
    val available: Int
)

data class Series(
    val available: Int
)

data class Stories(
    val available: Int
)
