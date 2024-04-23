package com.example.aroundme

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.Fragment

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        // Setup switch and checkbox with listeners
        val switchNotifications = view.findViewById<Switch>(R.id.switchNotifications)
        val checkboxDarkMode = view.findViewById<CheckBox>(R.id.checkboxDarkMode)

        switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(context, if (isChecked) "Notifications Enabled" else "Notifications Disabled", Toast.LENGTH_SHORT).show()
        }

        checkboxDarkMode.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(context, if (isChecked) "Dark Mode Enabled" else "Dark Mode Disabled", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}
