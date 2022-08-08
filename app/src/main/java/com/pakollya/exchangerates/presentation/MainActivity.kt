package com.pakollya.exchangerates.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.pakollya.exchangerates.R
import com.pakollya.exchangerates.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var activityBinding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        activityBinding = binding
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.currencyListFragment,
                R.id.favoritesCurrencyListFragment
            )
        )

        setSupportActionBar(binding.mainToolbar)

        setupActionBarWithNavController(navController, appBarConfiguration)
        setupWithNavController(binding.bottomNavigationView, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    fun setToolbarTitle(title: String? = null) {
        activityBinding?.mainToolbar?.title = title
    }

    fun setToolbarNavigation(direction: Int? = null) {
        activityBinding.let { binding ->
            binding.mainToolbar.setNavigationOnClickListener {
                if (direction != null) {
                    navController.navigate(direction)
                } else {
                    navController.popBackStack() || super.onSupportNavigateUp()
                }
            }
            binding.mainToolbar.setNavigationIcon(R.drawable.ic_arrow_left)
        }
    }
}