package com.dm.creativerun.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.dm.creativerun.databinding.FragmentSettingsBinding
import com.dm.creativerun.manager.ThemePreferencesManager
import com.google.android.material.button.MaterialButtonToggleGroup
import org.koin.android.ext.android.inject

class SettingsFragment : Fragment(), MaterialButtonToggleGroup.OnButtonCheckedListener {

    private lateinit var binding: FragmentSettingsBinding

    private val themePreferencesManager: ThemePreferencesManager by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val checkedId = when (themePreferencesManager.currentMode) {
            AppCompatDelegate.MODE_NIGHT_NO -> binding.lightThemeButton.id
            AppCompatDelegate.MODE_NIGHT_YES -> binding.darkThemeButton.id
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> binding.autoThemeButton.id
            else -> binding.lightThemeButton.id
        }

        binding.themeButtonGroup.check(checkedId)
        binding.themeButtonGroup.addOnButtonCheckedListener(this)
    }

    override fun onButtonChecked(
        group: MaterialButtonToggleGroup,
        checkedId: Int,
        isChecked: Boolean
    ) {
        if (group.checkedButtonId == View.NO_ID && !isChecked) {
            group.check(checkedId)
        }
        if (isChecked) {
            val mode = when (checkedId) {
                binding.darkThemeButton.id -> AppCompatDelegate.MODE_NIGHT_YES
                binding.autoThemeButton.id -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                else -> AppCompatDelegate.MODE_NIGHT_NO
            }
            themePreferencesManager.changeThemeMode(activity, mode)
        }
    }
}