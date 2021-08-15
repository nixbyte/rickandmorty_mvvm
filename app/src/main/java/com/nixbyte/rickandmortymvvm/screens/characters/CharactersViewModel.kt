package com.nixbyte.rickandmortymvvm.screens.characters

import com.nixbyte.platform.navigation.Navigation
import com.nixbyte.platform.uiactions.UiActions
import com.nixbyte.platform.viewmodel.ListViewModel
import com.nixbyte.rickandmortymvvm.R
import com.nixbyte.rickandmortymvvm.common.recyclerview.PaginatedRecyclerView
import com.nixbyte.rickandmortymvvm.model.api.domain.Character
import com.nixbyte.rickandmortymvvm.model.characters.CharactersRepository
import com.nixbyte.rickandmortymvvm.screens.characters.details.CharacterDetailsFragment

class CharactersViewModel(private val navigation: Navigation
                         ,private val uiActions: UiActions
                         ,private val charactersRepository: CharactersRepository)
: ListViewModel<Character>(R.layout.character_list_item, { view, character, position ->
    navigation.addScreen(CharacterDetailsFragment.Screen(character))
})  {

    init {
        load()
    }

    fun getTitle() = run { uiActions.getString(R.string.characters) }

    override fun load(offsetAndSize: PaginatedRecyclerView.OffsetAndSize) {
        val ids = preparePaginationRequestParameter(offsetAndSize)
        charactersRepository.getMultipleCharacters(ids).into(_currentList)
        currentPaginationOffset.value = offsetAndSize
    }
}