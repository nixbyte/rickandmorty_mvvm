package com.nixbyte.platform.view

import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.nixbyte.platform.model.ErrorResult
import com.nixbyte.platform.model.PendingResult
import com.nixbyte.platform.model.Result
import com.nixbyte.platform.model.SuccessResult
import com.nixbyte.platform.viewmodel.AbstractViewModel

abstract class AbstractFragment : Fragment() {

    abstract val viewModel: AbstractViewModel

    /**
     * Hide all views in the [root] and then call one of the provided lambda functions
     * depending on [result]:
     * - [onPending] is called when [result] is [PendingResult]
     * - [onSuccess] is called when [result] is [SuccessResult]
     * - [onError] is called when [result] is [ErrorResult]
     */
    fun <T> renderResult(root: ViewGroup, result:Result<T>,
                         onPending: () -> Unit,
                         onError: (Exception) -> Unit,
                         onSuccess: (T) -> Unit) {

        root.children.forEach { it.visibility = View.GONE }
        when (result) {
            is SuccessResult -> onSuccess(result.data)
            is ErrorResult -> onError(result.exception)
            is PendingResult -> onPending()
        }
    }

    /**
     * Call this method when activity controls (e.g. toolbar) should be re-rendered
     */
    fun notifyScreenUpdates() {
        (requireActivity() as FragmentsHolder).notifyScreenUpdates()
    }
}