package com.nixbyte.rickandmortymvvm.screens.episodes.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nixbyte.platform.view.AbstractFragment
import com.nixbyte.platform.view.HasScreenTitle
import com.nixbyte.platform.view.screenViewModel
import com.nixbyte.platform.viewmodel.SerializableScreen
import com.nixbyte.rickandmortymvvm.databinding.FragmentEpisodeDetailBinding
import com.nixbyte.rickandmortymvvm.model.api.domain.Episode

class EpisodeDetailFragment : AbstractFragment(), HasScreenTitle {

    class Screen(val episode: Episode?) : SerializableScreen

    override val viewModel by screenViewModel<EpisodeDetailViewModel>()

    override fun getScreenTitle(): String? = viewModel.episode.value?.name

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEpisodeDetailBinding.inflate(inflater,container,false)

        viewModel.episode.observe(viewLifecycleOwner) {
            binding.model = it
            notifyScreenUpdates()
        }

        return binding.root
    }
}