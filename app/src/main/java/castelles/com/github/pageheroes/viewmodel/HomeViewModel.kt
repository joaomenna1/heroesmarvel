package castelles.com.github.pageheroes.viewmodel

import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import castelles.com.github.pageheroes.data.Characters
import castelles.com.github.pageheroes.service.api.OrderBy
import castelles.com.github.pageheroes.service.datasource.CharacterDataSource
import castelles.com.github.pageheroes.service.repository.CharacterRepository
import kotlinx.android.parcel.Parcelize
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*

@Parcelize
class HomeViewModel: ViewModel(), Parcelable {

    companion object { const val TAG = "HOME_VIEW_MODEL" }

    private lateinit var characterRepository: CharacterRepository
    private val getCharacters: MutableLiveData<Characters> = MutableLiveData()
    val getCharactersLiveData: LiveData<Characters> = getCharacters

    private val errorGetChars: MutableLiveData<Throwable> = MutableLiveData()
    val errorGetCharsLiveData: LiveData<Throwable> = errorGetChars

    init {
        loadRepositories()
    }

    private fun loadRepositories() {
        characterRepository = CharacterRepository(CharacterDataSource())
    }

    fun getList(nameStartsWith: String? = null, page: Int = 1) {

        val offset = (page - 1).times(4L)

        characterRepository.getCharacters(nameStartsWith = nameStartsWith, offset = offset)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                getCharacters::postValue,
                errorGetChars::postValue
            )
    }

}