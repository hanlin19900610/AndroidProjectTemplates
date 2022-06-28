
/**
 * 页面状态 VM -> V
 */
open class UIState()

/**
 * V -> VM
 */
open class UIAction()

/**
 * 一次性 VM -> V
 */
open class UIEvent()

sealed class PageState<out T> {
    data class Success<T>(val data: T, val info: String): PageState<T>()
    data class Error(val code: String, val msg: String): PageState<Nothing>()
    object Loading : PageState<Nothing>()
}

sealed class FetchStatus {
    object Fetching : FetchStatus()
    object Fetched : FetchStatus()
    object NotFetched : FetchStatus()
}