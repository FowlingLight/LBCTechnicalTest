package com.example.lbctechnicaltest

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.lbctechnicaltest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    val loaderVisible = MutableLiveData(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.v(TAG, "onCreate")

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.apply {
            lifecycleOwner = this@MainActivity
            activity = this@MainActivity
        }
        setContentView(binding.root)

        navController =
            (supportFragmentManager.findFragmentById(R.id.main_fragment_container) as NavHostFragment).navController

        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.v(TAG, "onOptionsItemSelected")

        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return true
    }
}