package com.nixbyte.rickandmortymvvm.screens.characters.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nixbyte.platform.view.AbstractFragment
import com.nixbyte.platform.view.HasScreenTitle
import com.nixbyte.platform.view.screenViewModel
import com.nixbyte.platform.viewmodel.SerializableScreen
import com.nixbyte.rickandmortymvvm.databinding.FragmentCharacterDetailBinding
import com.nixbyte.rickandmortymvvm.model.api.domain.Character

class CharacterDetailsFragment : AbstractFragment(), HasScreenTitle {

    class Screen(val character: Character?) : SerializableScreen

    override val viewModel by screenViewModel<CharacterDetailsViewModel>()

    override fun getScreenTitle(): String? = viewModel.character.value?.name

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCharacterDetailBinding.inflate(inflater,container,false)

        viewModel.character.observe(viewLifecycleOwner) {
            binding.character = it
        }

        viewModel.location.observe(viewLifecycleOwner) { result ->
            renderSimpleResult(
                result = result
               ,onSuccess = {
                    binding.location = it
                }
            )
        }

        return binding.root
    }
}