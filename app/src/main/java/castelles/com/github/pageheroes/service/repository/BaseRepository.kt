package castelles.com.github.pageheroes.service.repository

import android.util.Log
import castelles.com.github.pageheroes.BuildConfig
import castelles.com.github.pageheroes.service.datasource.BaseDataSource
import com.microsoft.appcenter.crashes.Crashes
import retrofit2.Call
import rx.Subscriber


abstract class BaseRepository<T>(protected open val dataSource : BaseDataSource)
{
    protected fun <T> callback(
        callResponse: Call<T>,
        c: Subscriber<in T>
    ) {
        val response = callResponse.execute()

        if (response.isSuccessful) {
            val dataResponse = response.body()
            c.onNext(dataResponse)

            response.headers().forEach {
                if (it.first.equals("Set-Cookie")) {
                    Log.e("teste", it.second)
                }
            }

            c.onCompleted()
        } else {

            val t = Throwable(
                message =  response.errorBody()?.string(),
                cause = Throwable(response.code().toString())
            )

            if (BuildConfig.ANALYTICS) Crashes.trackError(t)

            c.onError(t)
        }
    }
}