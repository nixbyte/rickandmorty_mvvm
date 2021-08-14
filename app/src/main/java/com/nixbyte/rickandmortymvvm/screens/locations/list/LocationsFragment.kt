package com.nixbyte.rickandmortymvvm.screens.locations.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nixbyte.platform.view.AbstractFragment
import com.nixbyte.platform.view.onTryAgain
import com.nixbyte.platform.view.renderSimpleResult
import com.nixbyte.platform.view.screenViewModel
import com.nixbyte.platform.viewmodel.SerializableScreen
import com.nixbyte.rickandmortymvvm.R
import com.nixbyte.rickandmortymvvm.common.PaginatedRecyclerView
import com.nixbyte.rickandmortymvvm.common.recyclerview.PaginationListAdapter
import com.nixbyte.rickandmortymvvm.databinding.FragmentLocationsBinding
import com.nixbyte.rickandmortymvvm.model.api.domain.Location
import kotlinx.android.synthetic.main.fragment_locations.view.*

class LocationsFragment : AbstractFragment() {

    class Screen : SerializableScreen

    override val viewModel by screenViewModel<LocationsViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val binding = FragmentLocationsBinding.inflate(inflater,container,false)
        val adapter = PaginationListAdapter<Location>(R.layout.locations_list_item)
        binding.root.list.adapter = adapter
        binding.root.list.layoutManager = LinearLayoutManager(requireContext())

        var currentOffset: PaginatedRecyclerView.OffsetAndSize = PaginatedRecyclerView.OffsetAndSize(0,10)

        binding.list.run {
            pageSize = currentOffset.pageSize
            addItemDecoration(DividerItemDecoration(this.context, RecyclerView.VERTICAL))
            subscribeToLoadingChanel(viewModel.subscriptions) { offsetAndLimit ->
                if (currentOffset.offset < offsetAndLimit.offset) {
                    viewModel.loadMoreItems(offsetAndLimit)
                    currentOffset = offsetAndLimit
                }
            }
            startScrollingChanel()
        }

        viewModel.currentLocations.observe(viewLifecycleOwner) { result ->
            renderSimpleResult(
                root = binding.root,
                result = result,
                onSuccess = {
                    adapter.addMore(it, currentOffset.pageSize)
                }
            )
        }

        onTryAgain(binding.root) {
            adapter.refresh()
            currentOffset = PaginatedRecyclerView.OffsetAndSize(0,10)
            viewModel.tryAgain()
        }

        return binding.root
    }
}