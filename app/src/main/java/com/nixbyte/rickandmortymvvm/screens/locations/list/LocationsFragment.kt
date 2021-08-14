package com.nixbyte.rickandmortymvvm.screens.locations.list

import android.content.res.Configuration
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
import com.nixbyte.platform.model.takeSuccess
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
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_locations.view.*

class LocationsFragment : AbstractFragment() {

    val TAG = LocationsFragment::class.simpleName

    class Screen : SerializableScreen

    override val viewModel by screenViewModel<LocationsViewModel>()

    private val subscriptions = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentLocationsBinding.inflate(inflater,container,false)
        binding.root.list.adapter = viewModel.adapter
        binding.root.list.layoutManager = LinearLayoutManager(requireContext())

        binding.list.run {
            pageSize = 10
            addItemDecoration(DividerItemDecoration(this.context, RecyclerView.VERTICAL))
            subscribeToLoadingChanel(subscriptions) { offsetAndLimit ->
                viewModel.loadMoreItems(offsetAndLimit)
            }
            startScrollingChanel()
        }

        viewModel.currentLocations.observe(viewLifecycleOwner) { result ->
            renderSimpleResult(
                root = binding.root,
                result = result,
                onSuccess = {
                    viewModel.addToAdapter(it)
                }
            )
        }

        onTryAgain(binding.root) {
            viewModel.tryAgain()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        subscriptions.dispose()
    }
}