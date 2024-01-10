package com.example.wym_002.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wym_002.databinding.FragmentSettingsFragmentBinding

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSettingsFragmentBinding.inflate(layoutInflater)



        return binding.root
    }
}