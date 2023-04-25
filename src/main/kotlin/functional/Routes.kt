package functional

import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.routing.bind
import org.http4k.routing.routes


object UserRoutes {
    operator fun invoke():HttpHandler {
        return routes(
            "/" bind Method.GET to { _: Request -> Response(Status.OK) },
            "/sse" bind Method.GET to { _: Request -> Response(Status.OK) },
            "/users" bind Method.GET to { _: Request -> Response(Status.OK) },
        )
    }
}