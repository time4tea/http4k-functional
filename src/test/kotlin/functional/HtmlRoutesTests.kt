package functional

import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.Uri
import org.http4k.strikt.status
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class HtmlRoutesTests {

    val app: HttpHandler = { Response(Status.OK).body("Ok") }

    @Test
    fun homepage() {
        val request = Request(Method.GET, Uri.of("/api/users"))

        expectThat(app(request)).status.isEqualTo(Status.OK)
    }
}