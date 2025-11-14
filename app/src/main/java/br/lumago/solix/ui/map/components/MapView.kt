package br.lumago.solix.ui.map.components

import android.location.Location
import android.util.Log
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.lumago.solix.data.entities.relations.CustomerPosition
import br.lumago.solix.data.viewModels.map.MapViewModel
import br.lumago.solix.ui.theme.boldStyle
import br.lumago.solix.ui.theme.corGradienteHome2
import br.lumago.solix.ui.theme.corTexto
import br.lumago.solix.ui.utils.components.Header
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.Marker
import com.mapbox.maps.plugin.locationcomponent.createDefault2DPuck
import com.mapbox.maps.plugin.locationcomponent.location

@OptIn(MapboxExperimental::class)
@Composable
fun MapView(viewModel: MapViewModel) {
    val activity = LocalActivity.current!!
    //
    val mapViewportState = rememberMapViewportState()
    val nearbyRadius = 1000f
    //
    val customers = viewModel.customers.collectAsState().value

    var userLocation by remember { mutableStateOf<Point?>(null) }
    var nearbyCustomers by remember { mutableStateOf(emptyList<CustomerPosition>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        Header(title = "Mapa", activity = activity)

        Spacer(modifier = Modifier.height(15.dp))

        Row(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Clientes próximos",
                style = boldStyle,
                color = corTexto,
                fontSize = 20.sp
            )
        }

        HorizontalDivider(
            thickness = 15.dp,
            color = corGradienteHome2
        )

        MapboxMap(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            mapViewportState = mapViewportState
        ) {
            MapEffect(Unit) { mapView ->
                val locationPlugin = mapView.location
                locationPlugin.updateSettings {
                    locationPuck = createDefault2DPuck(withBearing = true)
                    enabled = true
                }
                mapViewportState.transitionToFollowPuckState()

                locationPlugin.addOnIndicatorPositionChangedListener { point ->
                    if (userLocation == null) {
                        userLocation = point
                    } else {
                        val userLat = point.latitude()
                        val userLng = point.longitude()

                        nearbyCustomers = customers.filter {
                            distanceBetween(
                                userLat,
                                userLng,
                                it.latitude,
                                it.longitude
                            ) <= nearbyRadius
                        }
                    }
                }
            }

            if (userLocation != null) {
                nearbyCustomers.forEach { customer ->
                    Marker(
                        point = Point.fromLngLat(customer.longitude, customer.latitude),
                        color = Color.Red,
                        stroke = Color.Transparent,
                        innerColor = Color.White,
                        text = customer.name
                    )
                }
            }
        }
    }
}

// Distance helper
fun distanceBetween(
    lat1: Double, lon1: Double,
    lat2: Double, lon2: Double
): Float {
    val result = FloatArray(1)
    Location.distanceBetween(lat1, lon1, lat2, lon2, result)
    return result[0]
}