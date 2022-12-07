package projet.ift604.broomitclient

import android.Manifest
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import java.util.concurrent.TimeUnit

class LocationBGService : Service() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var locCallback: LocationCallback

    interface IObserver {
        fun onLocationUpdate(loc: Location)
    }

    val subs = ArrayList<IObserver>()

    fun subscribe(obs: IObserver) {
        subs.add(obs);
    }

    fun unsubscribe(obs: IObserver) {
        subs.remove(obs);
    }

    fun sendAll(loc: Location) {
        subs.forEach {
            it.onLocationUpdate(loc)
        }
    }

    class LocationServiceBinder(val service: LocationBGService) : Binder();

    class LocCallback(val service: LocationBGService) : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            super.onLocationResult(p0)
            service.sendAll(p0.lastLocation);
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val fine = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val coarse = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)

        if (fine == PackageManager.PERMISSION_GRANTED &&
            coarse == PackageManager.PERMISSION_GRANTED
        ) {
            locCallback = LocCallback(this)

            val locationRequest = LocationRequest.create().apply {
                interval = TimeUnit.SECONDS.toMillis(5)
                fastestInterval = TimeUnit.SECONDS.toMillis(1)
                maxWaitTime = TimeUnit.SECONDS.toMillis(10)
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }

            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locCallback,
                Looper.getMainLooper())
        }

        return super.onStartCommand(intent, flags, startId);
    }

    override fun onBind(intent: Intent?): IBinder? {
        return LocationServiceBinder(this)
    }
}