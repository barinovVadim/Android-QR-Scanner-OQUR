package com.example.wym_002

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wym_002.databinding.FragmentAnaliticsFragmentBinding

class AnaliticsFragment : Fragment() {

    private lateinit var binding: FragmentAnaliticsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAnaliticsFragmentBinding.inflate(layoutInflater)

        return binding.root
    }


}