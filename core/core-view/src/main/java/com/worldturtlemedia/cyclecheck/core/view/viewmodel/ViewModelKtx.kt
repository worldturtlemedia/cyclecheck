import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.worldturtlemedia.cyclecheck.core.view.activity.BaseActivity
import com.worldturtlemedia.cyclecheck.core.view.fragment.BaseFragment
import com.worldturtlemedia.cyclecheck.core.view.viewmodel.sharedViewModel
import com.worldturtlemedia.cyclecheck.core.view.viewmodel.viewModel

/**
 * Delegate for creating a [ViewModel] that is using Dagger2.
 *
 * It passes the [BaseActivity.viewModelFactory] for creating the injected ViewModel.
 *
 * Example:
 *
 * ```
 * class SomeActivity : BaseActivity() {
 *    ...
 *    private val viewModel by injectedViewModel()
 * }
 * ```
 *
 * @see viewModel
 * @param[VM] Type of [ViewModel] to create.
 * @return Lazy delegate for creating a [ViewModel].
 */
inline fun <reified VM : ViewModel> BaseActivity.injectedViewModel() =
    viewModel<VM> { viewModelFactory }

/**
 * Delegate for creating a [ViewModel] that is using Dagger2.
 *
 * It passes the [BaseFragment.viewModelFactory] for creating the injected ViewModel.
 *
 * Example:
 *
 * ```
 * class SomeFragment : BaseFragment() {
 *    ...
 *    private val viewModel by injectedViewModel()
 * }
 * ```
 *
 * @see Fragment.viewModel
 * @param[VM] Type of [ViewModel] to create.
 * @return Lazy delegate for creating a [ViewModel].
 */
inline fun <reified VM : ViewModel> BaseFragment.injectedViewModel() =
    viewModel<VM> { viewModelFactory }

/**
 * Delegate for creating a [ViewModel] that is using Dagger2, and scoped to an [BaseActivity].
 *
 * It passes the [BaseFragment.viewModelFactory] for creating the injected ViewModel.
 *
 * @see injectedViewModel
 * @param[VM] Type of [ViewModel] to create.
 * @param[storeOwnerProducer] Lambda to produce a [ViewModelStoreOwner] scope for the [ViewModel]. Defaults to parent [FragmentActivity] scope.
 * @return Lazy delegate for creating a [ViewModel].
 */
inline fun <reified VM : ViewModel> BaseFragment.injectedSharedViewModel(
    noinline storeOwnerProducer: () -> ViewModelStoreOwner = ::requireActivity
) = sharedViewModel<VM>(storeOwnerProducer) { viewModelFactory }
