package com.nixbyte.rickandmortymvvm.screens.locations.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nixbyte.platform.view.AbstractFragment
import com.nixbyte.platform.view.HasScreenTitle
import com.nixbyte.platform.view.screenViewModel
import com.nixbyte.platform.viewmodel.SerializableScreen
import com.nixbyte.rickandmortymvvm.databinding.FragmentLocationDetailBinding
import com.nixbyte.rickandmortymvvm.model.api.domain.Location

class LocationDetailFragment : AbstractFragment(), HasScreenTitle {

    class Screen(val location: Location?) : SerializableScreen

    override val viewModel by screenViewModel<LocationDetailViewModel>()

    override fun getScreenTitle(): String? = viewModel.location.value?.name

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLocationDetailBinding.inflate(inflater,container,false)

        viewModel.location.observe(viewLifecycleOwner) {
            binding.model = it
            notifyScreenUpdates()
        }

        return binding.root
    }
}