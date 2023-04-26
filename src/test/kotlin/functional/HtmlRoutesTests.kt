package functional

import org.http4k.core.*
import org.http4k.core.ContentType.Companion.TEXT_HTML
import org.http4k.strikt.bodyString
import org.http4k.strikt.contentType
import org.http4k.strikt.status
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import org.junit.jupiter.api.Test
import strikt.api.Assertion
import strikt.api.DescribeableBuilder
import strikt.api.expectThat
import strikt.assertions.contains
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo
import strikt.assertions.isNotEmpty

object Html {
    operator fun invoke(response: Response): Document {
        return Jsoup.parse(response.bodyString())
    }
}

fun Assertion.Builder<Document>.select(selector: String): DescribeableBuilder<Elements> {
    return get { this.select(selector) }
}

class HtmlRoutesTests {

    val app = theApplication().http!!

    @Test
    fun `home page has some links`() {
        val request = htmlRequest(Uri.of("/"))

        val response = app(request)
        expectThat(response) {
            status.isEqualTo(Status.OK)
            contentType.isEqualTo(TEXT_HTML)
            get { Html(this) }.select("li").hasSize(3)
        }
    }

    @Test
    fun `users html page contains some users`() {
        val request = htmlRequest(Uri.of("/users"))

        val response = app(request)
        expectThat(response) {
            status.isEqualTo(Status.OK)
            contentType.isEqualTo(TEXT_HTML)
            bodyString.contains("Foo")
            bodyString.contains("Bar")
            bodyString.contains("Baz")
        }
    }

    @Test
    fun `sse page contains script tag`() {
        val request = htmlRequest(Uri.of("/sse"))

        expectThat(app(request)) {
            status.isEqualTo(Status.OK)
            get { Html(this) }.select("script").isNotEmpty()
        }
    }

    private fun htmlRequest(uri: Uri) = Request(Method.GET, uri).header("Accept", "text/html")

    @Test
    fun `loading static files`() {
        expectThat(app(Request(Method.GET, Uri.of("/logo.png")))) {
            status.isEqualTo(Status.OK)
            contentType.isEqualTo(ContentType("image/png"))
        }

        expectThat(app(Request(Method.GET, Uri.of("/sse.js")))) {
            status.isEqualTo(Status.OK)
            contentType.isEqualTo(ContentType("application/javascript"))
        }
    }
}