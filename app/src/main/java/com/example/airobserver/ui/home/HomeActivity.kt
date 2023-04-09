package com.example.airobserver.ui.home


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updatePadding
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.airobserver.R
import com.example.airobserver.databinding.ActivityHomeBinding
import com.example.airobserver.ui.home.home_fragment.HomeFragmentDirections
import com.example.airobserver.ui.home.profile_fragment.ProfileActivity
import com.example.airobserver.utils.showToast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    lateinit var binding:ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHomeFragment=supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController=navHomeFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)

        setSupportActionBar(binding.materialToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val adapter = ArrayAdapter.createFromResource(
            this, R.array.spinner_items, R.layout.item_spinner
        )
        adapter.setDropDownViewResource(R.layout.item_dropdown_spinner)
        binding.spinnerToolbar.adapter = adapter
        binding.spinnerToolbar.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                showToast("Selected item: $selectedItem")
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.profile ->{
                this.findNavController(R.id.fragmentContainerView).navigate(HomeFragmentDirections.actionHomeFragmentToProfileActivity())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}