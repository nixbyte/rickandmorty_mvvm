package com.nixbyte.rickandmortymvvm.screens.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nixbyte.platform.view.HasScreenTitle
import com.nixbyte.platform.view.ListFragment
import com.nixbyte.platform.view.screenViewModel
import com.nixbyte.platform.viewmodel.SerializableScreen
import com.nixbyte.rickandmortymvvm.common.databinding.ViewDataBindingComponent
import com.nixbyte.rickandmortymvvm.databinding.FragmentCharactersBinding
import com.nixbyte.rickandmortymvvm.model.api.domain.Location
import com.nixbyte.rickandmortymvvm.screens.locations.list.LocationsFragment
import kotlinx.android.synthetic.main.fragment_locations.view.*

class CharactersFragment : ListFragment<Location>(), HasScreenTitle {

    class Screen : SerializableScreen
    override val viewModel by screenViewModel<CharactersViewModel>()
    val TAG = LocationsFragment::class.simpleName

    override fun getScreenTitle(): String? = viewModel.getTitle()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentCharactersBinding.inflate(inflater,container,false)

        binding.root.list.adapter = viewModel.adapter
        binding.root.list.layoutManager = LinearLayoutManager(requireContext())

        binding.list.run {
            pageSize = 10
            addItemDecoration(DividerItemDecoration(this.context, RecyclerView.VERTICAL))
            subscribeToLoadingChanel(subscriptions) { offsetAndLimit ->
                viewModel.loadNext(offsetAndLimit)
            }
            startScrollingChanel()
        }

        viewModel.currentList.observe(viewLifecycleOwner) { result ->
            showResult(
                root = binding.root,
                result = result,
                onSuccess = {
                    viewModel.addToAdapter(it)
                }
            )
        }

        setSwipeOnRefresh(binding.root) {
            viewModel.refresh()
        }

        return binding.root
    }
}