package com.nixbyte.rickandmortymvvm.common.recyclerview

interface PaginationListenable {
    fun setLoading()
    fun removeLoading()
    fun isPaginationEnabled(): Boolean
}