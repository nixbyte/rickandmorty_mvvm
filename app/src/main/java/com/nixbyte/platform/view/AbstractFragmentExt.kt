package com.nixbyte.platform.view

import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.nixbyte.platform.model.Result
import com.nixbyte.rickandmortymvvm.R
import com.nixbyte.rickandmortymvvm.databinding.PartResultBinding
import kotlinx.android.synthetic.main.fragment_locations.view.*
import kotlinx.android.synthetic.main.part_result.view.*

/**
 * Default [Result] rendering.
 * - if [result] is [PendingResult] -> only progress-bar is displayed
 * - if [result] is [ErrorResult] -> only error container is displayed
 * - if [result] is [SuccessResult] -> error container & progress-bar is hidden, all other views are visible
 */
fun <T> AbstractFragment.renderSimpleResult(root: ViewGroup, result: Result<T>, onSuccess: (T) -> Unit) {
    val binding = PartResultBinding.bind(root)
    renderResult(
        root = root,
        result = result,
        onPending = {
            binding.root.swipe_refresh.visibility = View.VISIBLE
            binding.root.swipe_refresh.isRefreshing = true
        },
        onError = {
            binding.errorContainer.visibility = View.VISIBLE
            binding.root.swipe_refresh.isRefreshing = false
            binding.root.swipe_refresh.visibility = View.GONE
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

/**
 * Assign onRefresh listener for default try-again.
 */
fun AbstractFragment.onTryAgain(root: View, onTryAgain: () -> Unit) {
    root.findViewById<SwipeRefreshLayout>(R.id.swipe_refresh).setOnRefreshListener {
        onTryAgain()
    }
}