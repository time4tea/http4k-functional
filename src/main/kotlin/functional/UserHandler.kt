package functional

import java.time.LocalDate

class UserHandler {

    private val users = listOf(
        User("Foo", "Foo", LocalDate.now().minusDays(1)),
        User("Bar", "Bar", LocalDate.now().minusDays(10)),
        User("Baz", "Baz", LocalDate.now().minusDays(100))
    )
}

class UserDto(val firstName: String, val lastName: String, val birthDate: String)

fun User.toDto() = UserDto(firstName, lastName, birthDate.formatDate())