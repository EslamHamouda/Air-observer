package com.example.airobserver.ui.home


import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updatePadding
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.airobserver.R
import com.example.airobserver.databinding.ActivityHomeBinding
import com.example.airobserver.ui.auth.AuthActivity
import com.example.airobserver.ui.home.home_fragment.HomeFragmentDirections
import com.example.airobserver.ui.home.profile_fragment.ProfileActivity
import com.example.airobserver.utils.logout
import com.example.airobserver.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    lateinit var binding:ActivityHomeBinding
    @Inject
    lateinit var pref: SharedPreferences
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
       /* binding.spinnerToolbar.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {}
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }*/

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.profile ->{
                startActivity(Intent(this, ProfileActivity::class.java))
                true
            }
            R.id.logout -> {
                showLogoutDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showLogoutDialog() {
        val alert = AlertDialog.Builder(binding.root.context, R.style.AlertDialog)
            .setTitle(getString(R.string.logout))
            .setPositiveButton("Yes") { _, _ ->
                logout(pref,this)
                startActivity(Intent(this, AuthActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))

            }
            .setNegativeButton("No") { _, _ -> }
            .create()
        alert.show()
        alert.window?.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }
}