package functional

import org.http4k.core.ContentType
import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.Uri
import org.http4k.strikt.contentType
import org.http4k.strikt.status
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class HtmlRoutesTests {

    val app = theApplication()

    @Test
    fun `users html page contains some users`() {
        val request = Request(Method.GET, Uri.of("/users"))

        val response = app(request)
        expectThat(response) {
            status.isEqualTo(Status.OK)
            contentType.isEqualTo(ContentType.TEXT_HTML)
        }
    }
}