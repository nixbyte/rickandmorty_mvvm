package com.nixbyte.rickandmortymvvm.model.characters

import com.nixbyte.platform.model.RxTasksFactory
import com.nixbyte.platform.model.Task
import com.nixbyte.rickandmortymvvm.model.api.API
import com.nixbyte.rickandmortymvvm.model.api.domain.Character

class NetworkCharactersRepository(private val taskFactory: RxTasksFactory
) : CharactersRepository {

    override fun getAllCharacters(): Task<List<Character>?> = taskFactory.async {
        return@async API.CharacterApiService().getAllCharacters().map {
            return@map it.body()?.results
        }
    }

    override fun getCharacterById(id: String): Task<Character?> = taskFactory.async {
        return@async API.CharacterApiService().getCharacterById(id).map {
            return@map it.body()
        }
    }

    override fun getMultipleCharacters(ids: String): Task<List<Character>?> = taskFactory.async {
        return@async API.CharacterApiService().getMultipleCharacters(ids).map {
            return@map it.body()
        }
    }
}