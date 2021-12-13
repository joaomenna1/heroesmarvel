package joaomenna1.com.github.pageheroes.service.repository

import joaomenna1.com.github.pageheroes.data.Characters
import joaomenna1.com.github.pageheroes.service.api.OrderBy
import joaomenna1.com.github.pageheroes.service.datasource.CharacterDataSource
import rx.Observable
import java.util.*

class CharacterRepository(dataSource: CharacterDataSource): BaseRepository<Characters>(dataSource) {

    fun getCharacters(
        nameStartsWith: String? = null,
        orderBy: String = OrderBy.NAME.name.toLowerCase(Locale.ROOT),
        limit: Long? = 4,
        offset: Long? = null
    ): Observable<Characters> =
        Observable.create {
            val call = (dataSource as CharacterDataSource)
                .getCharacter(nameStartsWith, orderBy, limit, offset)
            callback(call, it)
        }
}