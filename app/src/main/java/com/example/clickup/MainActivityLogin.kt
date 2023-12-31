package com.example.clickup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.example.clickup.fragments.LoginFragment
import com.example.clickup.fragments.RegistrationFragment
import com.example.clickup.util.replaceFragment

class MainActivityLogin : AppCompatActivity(), RadioGroup.OnCheckedChangeListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val group = findViewById<RadioGroup>(R.id.radio_group)
        group.setOnCheckedChangeListener(this)
        replaceFragment(R.id.container, LoginFragment())
    }

    override fun onCheckedChanged(group: RadioGroup, checkId: Int) {
        val checkRadioButton = group.findViewById<RadioButton>(group.checkedRadioButtonId)

        checkRadioButton?.let {

            when (checkRadioButton.id) {
                R.id.login_radio_button -> {
                    replaceFragment(R.id.container, LoginFragment())
                }
                else -> {
                    replaceFragment(R.id.container, RegistrationFragment())
                }
            }
        }
    }
}