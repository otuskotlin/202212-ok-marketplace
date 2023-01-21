//package continuation
//
//import kotlinx.coroutines.delay
//import kotlin.coroutines.Continuation
//import kotlin.coroutines.CoroutineContext
//
//fun myFunction(continuation: Continuation<Unit>): Any {
//    val continuation = continuation as? MyFunctionContinuation
//        ?: MyFunctionContinuation(continuation)
//
//    var counter = continuation.counter
//
//    when (continuation.label) {
//        0 -> {
//            println("Before")
//            counter = 0
//            continuation.counter = counter
//            continuation.label = 1
//            if (delay(1000, continuation) == IntrinsicsKt.COROUTINE_SUSPENDED){
//                return IntrinsicsKt.COROUTINE_SUSPENDED
//            }
//        }
//        1 -> {
//            counter = (counter as Int) + 1
//            println("After: $counter")
//            return Unit
//        }
//        else -> error("Impossible")
//    }
//    return Unit
//}
//
//class MyFunctionContinuation(
//    val completion: Continuation<Unit>
//) : Continuation<Unit> {
//    override val context: CoroutineContext
//        get() = completion.context
//
//    var label = 0
//    var result: Result<Any>? = null
//
//    var counter = 0
//
//    override fun resumeWith(result: Result<Unit>) {
//        this.result = result
//        val res = try {
//            val r = myFunction(this)
//            if (r == IntrinsicsKt.COROUTINE_SUSPENDED) return
//            Result.success(r as Unit)
//        } catch (e: Throwable) {
//            Result.failure(e)
//        }
//        completion.resumeWith(res)
//    }
//}