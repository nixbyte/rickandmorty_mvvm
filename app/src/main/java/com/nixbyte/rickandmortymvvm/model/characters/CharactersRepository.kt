package com.nixbyte.rickandmortymvvm.model.characters

import com.nixbyte.platform.model.Repository
import com.nixbyte.platform.model.Task
import com.nixbyte.rickandmortymvvm.model.api.domain.Character

interface CharactersRepository : Repository {
    fun getAllCharacters() : Task<List<Character>?>
    fun getCharacterById(id: String) : Task<Character?>
    fun getMultipleCharacters(ids: String) : Task<List<Character>?>
}