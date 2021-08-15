package com.nixbyte.rickandmortymvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nixbyte.platform.navigation.IntermediateNavigator
import com.nixbyte.platform.navigation.StackFragmentNavigator
import com.nixbyte.platform.uiactions.AndroidUiActions
import com.nixbyte.platform.utils.viewModelCreator
import com.nixbyte.platform.view.FragmentsHolder
import com.nixbyte.platform.viewmodel.ActivityScopeViewModel
import com.nixbyte.rickandmortymvvm.databinding.ActivityMainBinding
import com.nixbyte.rickandmortymvvm.screens.characters.CharactersFragment
import com.nixbyte.rickandmortymvvm.screens.episodes.list.EpisodesFragment
import com.nixbyte.rickandmortymvvm.screens.locations.list.LocationsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), FragmentsHolder {

    private lateinit var navigator: StackFragmentNavigator

    private val activityViewModel by viewModelCreator<ActivityScopeViewModel> {
        ActivityScopeViewModel(
            uiActions = AndroidUiActions(applicationContext),
            navigator = IntermediateNavigator()
        )
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_RickAndMortyMVVM)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        setSupportActionBar(binding.toolbar)

        navigator = StackFragmentNavigator(
            activity = this,
            containerId = R.id.fragmentContainer,
            defaultTitle = getString(R.string.app_name),
            animations = StackFragmentNavigator.Animations(
                enterAnim = R.anim.enter,
                exitAnim = R.anim.exit,
                popEnterAnim = R.anim.pop_enter,
                popExitAnim = R.anim.pop_exit
            ),
            initialScreenCreator = {
                navigation.selectedItemId = R.id.action_character
                CharactersFragment.Screen()
            }
        )
        navigator.onCreate(savedInstanceState)

        binding.navigation.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.action_location -> {
                    navigator.showScreenAndClearStack(LocationsFragment.Screen())
                    true
                }
                R.id.action_character -> {
                    navigator.showScreenAndClearStack(CharactersFragment.Screen())
                    true
                }
                R.id.action_episode -> {
                    navigator.showScreenAndClearStack(EpisodesFragment.Screen())
                    true
                }
                else -> {
                    true
                }
            }
        }
    }

    override fun onDestroy() {
        navigator.onDestroy()
        super.onDestroy()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onResume() {
        super.onResume()
        // execute navigation actions only when activity is active
        activityViewModel.navigator.setTarget(navigator)
    }

    override fun onPause() {
        super.onPause()
        // postpone navigation actions if activity is not active
        activityViewModel.navigator.setTarget(null)
    }

    override fun notifyScreenUpdates() {
        navigator.notifyScreenUpdates()
    }

    override fun getActivityScopeViewModel(): ActivityScopeViewModel {
        return activityViewModel
    }
}