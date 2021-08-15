package com.nixbyte.platform.view

import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.nixbyte.platform.model.Result
import com.nixbyte.rickandmortymvvm.R
import com.nixbyte.rickandmortymvvm.databinding.PartResultBinding
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_recyclerview.view.*

abstract class ListFragment<T> : AbstractFragment() {
    protected val subscriptions = CompositeDisposable()
        protected fun <T> showResult(root: ViewGroup
                                     , result: Result<T>
                                     , onPending: (T) -> Unit = {}
                                     , onError: (T) -> Unit = {}
                                     , onSuccess: (T) -> Unit) {
            val binding = PartResultBinding.bind(root)
            renderResult(
                root = root,
                result = result,
                onPending = {
                    binding.root.swipe_refresh.visibility = View.VISIBLE
                    binding.root.swipe_refresh.isRefreshing = true
                    onPending
                },
                onError = {
                    binding.errorContainer.visibility = View.VISIBLE
                    binding.root.swipe_refresh.isRefreshing = false
                    binding.root.swipe_refresh.visibility = View.GONE
                    onError
                },
                onSuccess = { successData ->
                    root.swipe_refresh.isRefreshing = false
                    root.swipe_refresh.visibility = View.GONE
                    root.children
                        .filter {it.id != R.id.errorContainer }
                        .forEach { it.visibility = View.VISIBLE }
                    onSuccess(successData)
                }
            )
        }

    protected fun setSwipeOnRefresh(root: View, onTryAgain: () -> Unit) {
        root.findViewById<SwipeRefreshLayout>(R.id.swipe_refresh).setOnRefreshListener {
            onTryAgain()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        subscriptions.dispose()
    }
}


