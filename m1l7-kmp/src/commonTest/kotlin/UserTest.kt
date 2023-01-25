import kotlin.test.Test
import kotlin.test.assertEquals

// TODO-4 (Общие сведения): запуск тестов для различных платформ (./gradlew clean jsTest)
//  показать где лежат преобразованные под конкретную платформу артефакты
//  (build/classes/kotlin, build/klib/cache/main)
class UserTest {

    @Test
    fun test1() {
        val user = User("1", "Ivan", 24)
        assertEquals("1", user.id)
        assertEquals("Ivan", user.name)
        assertEquals(24, user.age)
    }
}