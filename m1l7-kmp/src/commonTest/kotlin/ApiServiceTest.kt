import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ApiServiceTest {

    // TODO-general-6: использование runTest вместо runBlocking для coroutine
    //  (пропускает вызовы delay на всех платформах)
    @Test
    fun test1() =  runTest {
        assertEquals("Api call response", ApiService().call())
    }
}