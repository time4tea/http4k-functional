package functional

import org.http4k.core.*
import org.http4k.strikt.bodyString
import org.http4k.strikt.contentType
import org.http4k.strikt.status
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.contains
import strikt.assertions.isEqualTo

class ApiRoutesTests {

    val app = theApplication().http!!

    @Test
    fun `users json api`() {
        val request = Request(Method.GET, Uri.of("/api/users")).header("Accept", "application/json")

        expectThat(app(request)) {
            status.isEqualTo(Status.OK)
            contentType.isEqualTo(ContentType.APPLICATION_JSON)
            bodyString.contains("Foo")
            bodyString.contains("Bar")
            bodyString.contains("Baz")
        }
    }
}