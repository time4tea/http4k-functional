package functional

import com.github.jknack.handlebars.Handlebars
import com.github.jknack.handlebars.Options
import org.http4k.core.*
import org.http4k.format.Moshi
import org.http4k.format.Moshi.auto
import org.http4k.routing.*
import org.http4k.sse.Sse
import org.http4k.sse.SseMessage
import org.http4k.template.HandlebarsTemplates
import org.http4k.template.ViewModel
import org.http4k.template.viewModel
import kotlin.concurrent.timer

open class Page(val name: String) : ViewModel {
    val title = name
    override fun template(): String = name
}

class UsersPage(name: String, val users: List<User>) : Page(name)

object HtmlRoutes {
    operator fun invoke(users: () -> List<User>): RoutingHttpHandler {

        val templates = templates().CachingClasspath("templates")
        val html = Body.viewModel(templates, ContentType.TEXT_HTML).toLens()

        fun html(model: () -> ViewModel): HttpHandler = {
            Response(Status.OK).with(html of model())
        }

        return routes(
            "/" bind Method.GET to html { Page("home") },
            "/sse" bind Method.GET to html { Page("sse") },
            "/users" bind Method.GET to html { UsersPage("users", users()) },
            "/" bind static(ResourceLoader.Classpath("static"))
        )
    }
}

object ApiRoutes {
    operator fun invoke(users: () -> List<User>): RoutingHttpHandler {

        val json = Body.auto<List<User>>().toLens()

        return routes(
            "/api/users" bind Method.GET to { Response(Status.OK).with(json of users()) }
        )
    }
}

object SseRoutes {
    operator fun invoke(user: () -> User): RoutingSseHandler {
        return sse(
            "/api/users" bind { sse: Sse ->
                timer(period = 100) {
                    sse.send(SseMessage.Data(Moshi.asFormatString(user())))
                }
            }
        )
    }
}

fun templates() = HandlebarsTemplates(handlebarsConfiguration());

private fun handlebarsConfiguration(): (Handlebars) -> Handlebars = {
    it.also {
        it.registerHelperMissing { _: Any, options: Options ->
            throw IllegalArgumentException(
                "Missing value for: " + options.helperName
            )
        }
    }
}