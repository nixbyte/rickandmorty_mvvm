package com.nixbyte.rickandmortymvvm.common

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nixbyte.rickandmortymvvm.common.recyclerview.PaginationListenable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import java.lang.Exception

class PaginatedRecyclerView @JvmOverloads constructor(
    recyclerContext: Context, attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(recyclerContext, attributeSet, defStyleAttr) {

    var pageSize = 5

    class OffsetAndSize(val offset: Int, val pageSize: Int)

    private var paginationListenable: PaginationListenable? = null
    private val scrollingChanel = PublishSubject.create<OffsetAndSize>()

    override fun setLayoutManager(layout: LayoutManager?) {
        if (layout !is LinearLayoutManager)
            throw Exception("layoutManager need to be the instance of LinearLayoutManager")
        else
            super.setLayoutManager(layout)
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        if (adapter !is PaginationListenable)
            throw Exception("Adapter need to implement PaginationListenable interface")
        paginationListenable = adapter
        super.setAdapter(adapter)
    }

    fun startScrollingChanel() {
        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = layoutManager?.childCount ?: 0
                val totalItemCount = layoutManager?.itemCount ?: 0
                val firstVisibleItemPosition =
                    (layoutManager as? LinearLayoutManager)?.findFirstVisibleItemPosition() ?: 0


                if (paginationListenable?.isPaginationEnabled() == true) {
                    if ((firstVisibleItemPosition + visibleItemCount) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= pageSize) {
                        val offset = adapter?.itemCount ?: 0 - 1
                        scrollingChanel.onNext(OffsetAndSize(offset, pageSize))
                    }
                }
            }
        })
    }

    fun subscribeToLoadingChanel(subscriptions: CompositeDisposable, onNext: (OffsetAndSize) -> Unit) {
        subscriptions.add(scrollingChanel.subscribe(onNext))
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
    }
}