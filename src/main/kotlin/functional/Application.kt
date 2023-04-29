package functional

import org.http4k.routing.routes
import org.http4k.server.PolyHandler
import org.http4k.server.Undertow
import org.http4k.server.asServer
import java.lang.management.ManagementFactory
import java.lang.management.PlatformLoggingMXBean
import java.time.Duration


fun theApplication(): PolyHandler {
    val service = Users()
    return PolyHandler(
        http = routes(
            HtmlRoutes(
                users = { service.users }
            ),
            ApiRoutes(
                users = { service.users }
            )
        ),
        sse = SseRoutes(user = { service.user() })
    )
}

fun main() {

//    ManagementFactory.getPlatformMXBean(PlatformLoggingMXBean::class.java).setLoggerLevel("", "SEVERE") - not in graal??

    val time = System.currentTimeMillis()

    val server = theApplication().asServer(Undertow(port = 8081)).start()

    val started = Duration.ofMillis(System.currentTimeMillis() - time)

    println("Server started in $started")
    server.block()
}