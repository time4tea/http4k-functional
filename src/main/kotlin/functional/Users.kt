package functional

import java.time.LocalDate

class Users {
    fun user(): User {
        return users.random()
    }

    val users = listOf(
        User("Foo", "Foo", LocalDate.now().minusDays(1)),
        User("Bar", "Bar", LocalDate.now().minusDays(10)),
        User("Baz", "Baz", LocalDate.now().minusDays(100))
    )
}
