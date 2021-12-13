package joaomenna1.com.github.pageheroes.app

import android.content.Context
import android.os.Process
import joaomenna1.com.github.pageheroes.BuildConfig
import com.microsoft.appcenter.crashes.Crashes
import java.io.PrintWriter
import java.io.StringWriter
import kotlin.system.exitProcess

class ExceptionHandler(
    private val myContext: Context,
    private val myActivityClass: Class<*>
) : Thread.UncaughtExceptionHandler {

    override fun uncaughtException(thread: Thread, exception: Throwable) {
        val stackTrace = StringWriter()
        exception.printStackTrace(PrintWriter(stackTrace))

        if (BuildConfig.ANALYTICS) {
            Crashes.trackError(exception)
        }

//        val intent = Intent(myContext, myActivityClass)
//        val s = stackTrace.toString()
//        intent.putExtra("uncaughtException", "Exception is: $stackTrace")
//        intent.putExtra("stacktrace", s)
//        myContext.startActivity(intent)

        Process.killProcess(Process.myPid())
        exitProcess(0)
    }
}