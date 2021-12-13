package joaomenna1.com.github.pageheroes.util

import joaomenna1.com.github.pageheroes.BuildConfig
import com.microsoft.appcenter.analytics.Analytics

object Tracker {

    const val TRACKER = "EventTracker"

    enum class TrackType {
        DONE,
        FAILED
    }

    enum class EventType {
        LOAD_HERO,
        SELECT_HERO,
        SEARCH_HERO
    }

    fun track(type: TrackType, event: EventType, extraInfo: String) {
        if (BuildConfig.ANALYTICS) {
            Analytics.trackEvent(
                event.name,
                mutableMapOf(type.name to event.name, "EXTRA_INFO" to extraInfo)
            )
        }
    }

}