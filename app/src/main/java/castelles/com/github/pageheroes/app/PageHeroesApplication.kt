package castelles.com.github.pageheroes.app

import android.app.Application
import castelles.com.github.pageheroes.BuildConfig
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import com.microsoft.appcenter.distribute.Distribute

class PageHeroesApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Thread.setDefaultUncaughtExceptionHandler(
            ExceptionHandler(this, PageHeroesApplication::class.java)
        )

        AppCenter.start(
            this,
            BuildConfig.APP_CENTER_KEY,
            Analytics::class.java,
            Crashes::class.java,
            Distribute::class.java
        )
    }
}