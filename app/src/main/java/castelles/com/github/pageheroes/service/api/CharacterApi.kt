package castelles.com.github.pageheroes.service.api

import castelles.com.github.pageheroes.data.Characters
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterApi {

    @GET("/v1/public/characters")
    fun getCharacters(
        @Query("nameStartsWith") nameStartsWith: String?,
        @Query("orderBy") orderBy: String,
        @Query("limit") limit: Long?,
        @Query("offset") offset: Long?,
        @Query("apikey") key: String,
        @Query("ts") ts: String,
        @Query("hash") hash: String
    ): Call<Characters>
}

enum class OrderBy(value: String) {
    NAME("name"),
    MODIFIED("modified"),
    NameDescending("-name"),
    ModifiedDescending("-modified")
}