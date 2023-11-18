package com.example.wym_002.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wym_002.databinding.FragmentSettingsFragmentBinding

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsFragmentBinding
    lateinit var pref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSettingsFragmentBinding.inflate(layoutInflater)

        pref = context!!.getSharedPreferences("pref", Context.MODE_PRIVATE)
        // resultCardBalance        key: R.drawable.credit_card_white.toString()
        // resultWalletBalance      key: R.drawable.wallet_white.toString()
        // resultBankBalance        key: R.drawable.account_balance_white.toString()
        // resultTotalBalance       key: getString(R.string.keyTotalBalance

        buttonToClear()

        return binding.root
    }

    private fun buttonToClear() {    // TODO(УБРАТЬ И ПЕРЕДЕЛАТЬ)
        // TODO(ДОБАВИТЬ УДАЛЕНИЕ БД И ПЕРЕНСТИ КНОПКУ В НАСТРОЙКИ)
        binding.clearPref.setOnClickListener {
            clearPref()

        }
    }

    private fun clearPref() {    // очищает переменные в памяти
        val editor = pref.edit()
        editor.clear()
        editor.apply()
    }
}