package com.example.oqur


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.oqur.fragments.AnaliticsFragment
import com.example.oqur.fragments.MainFragment
import com.example.oqur.fragments.SettingsFragment
import com.example.oqur.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        hidingPanel(window)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mainFragment = MainFragment()
        val settingsFragment = SettingsFragment()
        val analiticsFragment = AnaliticsFragment()

        makeCurrentFragment(mainFragment)
        val mBottomNavigationView = findViewById<View>(R.id.bNavigation) as BottomNavigationView
        mBottomNavigationView.menu.findItem(R.id.mainFragment2).isChecked = true

        binding.bNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId){
                R.id.analiticsFragment2 -> makeCurrentFragment(analiticsFragment)
                R.id.mainFragment2 -> makeCurrentFragment(mainFragment)
                R.id.settingsFragment2 ->makeCurrentFragment(settingsFragment)
            }
            true
        }

    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.myFragment, fragment)
            commit()
        }

}

