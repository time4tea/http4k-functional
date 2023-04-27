package functional

import com.github.jknack.handlebars.Handlebars
import com.github.jknack.handlebars.Options
import org.http4k.core.Body
import org.http4k.core.ContentType.Companion.TEXT_HTML
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.with
import org.http4k.format.Moshi
import org.http4k.format.Moshi.auto
import org.http4k.routing.ResourceLoader
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.routing.sse
import org.http4k.routing.static
import org.http4k.sse.Sse
import org.http4k.sse.SseMessage.Data
import org.http4k.template.HandlebarsTemplates
import org.http4k.template.ViewModel
import org.http4k.template.viewModel
import kotlin.concurrent.timer

open class Page(val name: String) : ViewModel {
    val title = name
    override fun template(): String = name
}

class UsersPage(name: String, val users: List<User>) : Page(name)

fun HtmlRoutes(users: () -> List<User>): RoutingHttpHandler {
    val templates = templates().CachingClasspath("templates")

    val html = Body.viewModel(templates, TEXT_HTML).toLens()

    fun html(model: () -> ViewModel): HttpHandler = {
        Response(OK).with(html of model())
    }

    return routes(
        "/" bind GET to html { Page("home") },
        "/sse" bind GET to html { Page("sse") },
        "/users" bind GET to html { UsersPage("users", users()) },
        "/" bind static(ResourceLoader.Classpath("static"))
    )
}

fun ApiRoutes(users: () -> List<User>): RoutingHttpHandler {
    val json = Body.auto<List<User>>().toLens()

    return routes(
        "/api/users" bind GET to { Response(OK).with(json of users()) }
    )
}

fun SseRoutes(user: () -> User) = sse(
        "/api/users" bind { sse: Sse ->
            timer(period = 100) {
                sse.send(Data(Moshi.asFormatString(user())))
            }
        }
    )

fun templates() = HandlebarsTemplates(handlebarsConfiguration());

private fun handlebarsConfiguration(): (Handlebars) -> Handlebars = {
    it.apply {
        registerHelperMissing { _: Any, options: Options ->
            throw IllegalArgumentException("Missing value for: " + options.helperName)
        }
    }
}