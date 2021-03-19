package castelles.com.github.pageheroes.service.datasource

import castelles.com.github.pageheroes.BuildConfig
import castelles.com.github.pageheroes.service.api.CharacterApi
import castelles.com.github.pageheroes.service.api.OrderBy
import java.util.*

class CharacterDataSource: BaseDataSource() {

    private lateinit var api: CharacterApi
    init {
        setApi()
    }

    private fun setApi() {
        api = retrofit.create(CharacterApi::class.java)
    }

    fun getCharacter(
        nameStartsWith: String?,
        orderBy: String,
        limit: Long?,
        offset: Long?
    ) = api.getCharacters(
        nameStartsWith,
        orderBy,
        limit,
        offset,
        BuildConfig.API_KEY,
        "thesoer",
        BuildConfig.HASH_KEY
    )

}