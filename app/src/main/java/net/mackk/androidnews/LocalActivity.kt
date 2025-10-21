package net.mackk.androidnews

import android.annotation.SuppressLint
import android.app.Activity
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LocalActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ActivityBoilerplate { innerPadding -> ActivityContent(innerPadding) }
        }
    }
}

@Composable
private fun ActivityContent(innerPadding: PaddingValues = PaddingValues()) {

    // context
    val context = LocalContext.current
    val activity = when (context) {
        is Activity -> context
        else -> null
    }
    val coroutineScope = rememberCoroutineScope()

    // state
    var apiManager = remember { ApiManager() }
    var articlesList by remember { mutableStateOf<List<ArticleData>>(emptyList()) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(38.4221, -77.4083), 10f)
    }
    var markerPosition by remember { mutableStateOf<LatLng?>(null) }
    var addressInfo by remember { mutableStateOf("Long Click on Map") }
    var buttonColor by remember { mutableStateOf(Color.Red) }

    // effects
    LaunchedEffect(markerPosition) {
        markerPosition?.let { latLng ->
            addressInfo = "Resolving address..."
            addressInfo = withContext(Dispatchers.IO) {
                getAddressGeocodeCurrent(context, latLng)
            }
            val result = withContext(Dispatchers.IO) {
                apiManager.getLocalArticles(
                    context.getString(R.string.news_api_key), "Washington, DC", 1
                )
            }
            articlesList = result
        }
    }

    Column(modifier = Modifier.padding(innerPadding)) {
        GoogleMap(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f),
            cameraPositionState = cameraPositionState,
            onMapLongClick = { latLng ->
                markerPosition = latLng
                addressInfo = "Resolving address..."
            }) {
            markerPosition?.let { position ->
                Marker(
                    state = MarkerState(position = position),
                    title = addressInfo,
                    snippet = "Lat: ${position.latitude}, Lng: ${position.longitude}"
                )
                buttonColor = Color.Blue
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(articlesList) { ArticleCard(it) }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun ActivityContentPreview() {
    ActivityBoilerplate { innerPadding -> ActivityContent(innerPadding) }
}

@SuppressLint("NewApi")
suspend fun getAddressGeocodeCurrent(context: android.content.Context, latLng: LatLng): String =
    suspendCoroutine { continuation ->
        val geocoder = Geocoder(context, Locale.getDefault())
        geocoder.getFromLocation(
            latLng.latitude, latLng.longitude, 1, object : Geocoder.GeocodeListener {
                override fun onGeocode(addressList: MutableList<Address>) {
                    val result = if (addressList.isNotEmpty()) {
                        val address = addressList[0]
                        val city = address.locality ?: "Unknown City"
                        val state = address.adminArea ?: "Unknown State"
                        "$city, $state"
                    } else {
                        "No address found."
                    }
                    continuation.resume(result)
                }

                override fun onError(errorMessage: String?) {
                    continuation.resume("Geocoding failed: ${errorMessage ?: "Unknown error"}")
                }
            })
    }

//@RequiresApi(Build.VERSION_CODES.TIRAMISU)
//suspend fun getAddressGeocodeCurrent(context: Context, latLng: LatLng): String {
//    suspendCoroutine { continuation ->
//        val geocoder = Geocoder(context, Locale.getDefault())
//        geocoder.getFromLocation(
//            latLng.latitude, latLng.longitude, 1, object : Geocoder.GeocodeListener {
//                override fun onGeocode(addressList: MutableList<Address>) {
//                    val result = if (addressList.isNotEmpty()) {
//                        val address = addressList[0]
//                        val city = address.locality ?: "Unknown City"
//                        val state = address.adminArea ?: "Unknown State"
//                        "$city, $state"
//                    } else {
//                        "No address found."
//                    }
//                    continuation.resume(result)
//                }
//
//                override fun onError(errorMessage: String?) {
//                    continuation.resume("Geocoding failed: ${errorMessage ?: "Unknown error"}")
//                }
//            })
//    }
//}
