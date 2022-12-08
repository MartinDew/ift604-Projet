package projet.ift604.broomitclient

import android.Manifest
import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.IBinder
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import projet.ift604.broomitclient.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val obs: LocationBGService.IObserver = object : LocationBGService.IObserver {
        override fun onLocationUpdate(loc: Location) {
            // HANDLE LOCATION UPDATES

            val locList = ApplicationState.instance.getLocationInProximity(loc, 0.1)

            runOnUiThread {
                Toast.makeText(applicationContext, "${locList.size} POS: ${loc.longitude}, ${loc.latitude}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as LocationBGService.LocationServiceBinder
            binder.service.subscribe(obs)
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
        }
    }

    fun requestLocationPerms() {
        val serviceIntent = Intent(this, LocationBGService::class.java)

        val request = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { perm ->
            when {
                perm.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    // precise location granted
                    startService(serviceIntent)
                    bindService(serviceIntent, connection, Service.BIND_AUTO_CREATE)
                }
                perm.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    // coars location granted
                    startService(serviceIntent)
                    bindService(serviceIntent, connection, Service.BIND_AUTO_CREATE)
                } else -> {
                    // no location granted
                }
            }
        }

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            || checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            request.launch(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            ))
        } else {
            startService(serviceIntent)
            bindService(serviceIntent, connection, Service.BIND_AUTO_CREATE)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)

        requestLocationPerms()

        val navHostFragment =
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment?)!!
        val navController = navHostFragment.navController

        binding.navView?.let {
            appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.nav_transform, R.id.nav_today, R.id.nav_locations, R.id.nav_settings
                ),
                binding.drawerLayout
            )
            setupActionBarWithNavController(navController, appBarConfiguration)
            it.setupWithNavController(navController)
        }

        binding.appBarMain.contentMain.bottomNavView?.let {
            appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.nav_transform, R.id.nav_today, R.id.nav_locations
                )
            )
            setupActionBarWithNavController(navController, appBarConfiguration)
            it.setupWithNavController(navController)
            it.selectedItemId = R.id.nav_today
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val result = super.onCreateOptionsMenu(menu)
        // Using findViewById because NavigationView exists in different layout files
        // between w600dp and w1240dp
        val navView: NavigationView? = findViewById(R.id.nav_view)
        if (navView == null) {
            // The navigation drawer already has the items including the items in the overflow menu
            // We only inflate the overflow menu if the navigation drawer isn't visible
            menuInflater.inflate(R.menu.overflow, menu)
        }
        return result
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_profile -> {
                val navController = findNavController(R.id.nav_host_fragment_content_main)
                navController.navigate(R.id.nav_profile)
            }
            R.id.nav_settings -> {
                val navController = findNavController(R.id.nav_host_fragment_content_main)
                navController.navigate(R.id.nav_settings)
            }
            R.id.nav_disconnect -> {
                val state = ApplicationState.instance
                state.logout()
                finish() // Quit MainActivity, go back to Login prompt.
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}