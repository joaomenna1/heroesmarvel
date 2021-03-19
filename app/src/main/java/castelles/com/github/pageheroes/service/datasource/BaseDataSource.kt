package castelles.com.github.pageheroes.service.datasource

import castelles.com.github.pageheroes.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

abstract class BaseDataSource {

    companion object {
        lateinit var retrofit: Retrofit
    }

    init {
        build()
    }

    private fun build() {

        val client: OkHttpClient.Builder = OkHttpClient().newBuilder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)

        client.addInterceptor {
            val builder = it.request()
                .newBuilder()
                .addHeader("Accept", "application/json")
                .build()

            it.proceed(builder)
        }

        val log = HttpLoggingInterceptor()
        log.setLevel(HttpLoggingInterceptor.Level.BODY)

        client.addInterceptor(log)

        retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()

    }

}