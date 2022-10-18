package com.pakollya.exchangerates.main.presentation

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.pakollya.exchangerates.R
import com.pakollya.exchangerates.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ToolbarTitle, ToolbarNavigation {

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
                R.id.currenciesFragment,
                R.id.favoritesFragment
            )
        )

        setSupportActionBar(binding.mainToolbar)

        setupActionBarWithNavController(navController, appBarConfiguration)
        setupWithNavController(binding.bottomNavigationView, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun setTitle(title: String?) {
        title?.let { activityBinding.mainToolbar.title = it }
    }

    override fun setDirection(direction: Int?) {
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