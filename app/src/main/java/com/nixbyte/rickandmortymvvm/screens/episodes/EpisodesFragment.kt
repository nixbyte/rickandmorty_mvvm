package com.nixbyte.rickandmortymvvm.screens.episodes

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nixbyte.platform.viewmodel.SerializableScreen
import com.nixbyte.rickandmortymvvm.databinding.FragmentEpisodesBinding

class EpisodesFragment: Fragment() {

    val TAG = EpisodesFragment::class.simpleName

    class Screen : SerializableScreen

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e(TAG, "onCreateView: ")
        val binding = FragmentEpisodesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG, "onDestroy: ")
    }
}