package com.nixbyte.rickandmortymvvm.screens.locations.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nixbyte.platform.view.AbstractFragment
import com.nixbyte.platform.view.onTryAgain
import com.nixbyte.platform.view.renderSimpleResult
import com.nixbyte.platform.view.screenViewModel
import com.nixbyte.platform.viewmodel.SerializableScreen
import com.nixbyte.rickandmortymvvm.R
import com.nixbyte.rickandmortymvvm.common.recyclerview.PaginationListAdapter
import com.nixbyte.rickandmortymvvm.databinding.FragmentLocationsBinding

class LocationsFragment : AbstractFragment() {

    class Screen : SerializableScreen

    override val viewModel by screenViewModel<LocationsViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentLocationsBinding.inflate(inflater,container,false)
        viewModel.currentLocations.observe(viewLifecycleOwner) { result ->
            renderSimpleResult(
                root = binding.root,
                result = result,
                onSuccess = {
                    binding.list.run {
                        layoutManager = LinearLayoutManager(context)
                        adapter = PaginationListAdapter(R.layout.locations_list_item, items = it)

                    }
                }
            )
        }

        onTryAgain(binding.root) {
            viewModel.tryAgain()
        }

        return binding.root
    }

}