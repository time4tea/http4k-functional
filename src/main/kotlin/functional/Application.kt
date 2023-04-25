package functional

import org.http4k.core.HttpHandler
import org.http4k.core.Response
import org.http4k.core.Status

fun theApplication(): HttpHandler = { Response(Status.OK) }

fun main() {

}