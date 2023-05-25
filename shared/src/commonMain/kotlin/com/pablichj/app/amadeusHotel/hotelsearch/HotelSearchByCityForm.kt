package com.pablichj.app.amadeusHotel.hotelsearch

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.pablichj.app.amadeusHotel.ui.FullScreenLoader
import com.pablichj.app.amadeusHotel.ui.LoadableState
import com.pablichj.incubator.amadeus.endpoint.hotels.model.HotelListing

@Composable
internal fun HotelSearchByCityForm(
    modifier: Modifier = Modifier,
    onCitySearchRequest: (String) -> Unit,
    onHotelSelected: (HotelListing) -> Unit,
    hotelList: State<LoadableState<List<HotelListing>>?>//List<HotelListing>
) {
    var cityName by remember { mutableStateOf("Miami") }

    Column(modifier.verticalScroll(rememberScrollState())) {
        Text(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            text = "Select a place to visit",
            style = MaterialTheme.typography.h4
        )
        Spacer(Modifier.height(8.dp))
        Text(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            text = "Type an exact address of the place you want to go or just type the city name.",
            style = MaterialTheme.typography.subtitle1
        )
        Spacer(Modifier.height(24.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp),
            value = cityName,
            label = { Text("Destination City") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            /*keyboardActions = KeyboardActions(
                onNext = {
                    focus.moveFocus(FocusDirection.Next)
                }
            ),*/
            onValueChange = { cityName = it },
            singleLine = true
        )
        Button(
            onClick = { onCitySearchRequest(cityName.trim()) },
            modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp)
        ) {
            Text("Search")
        }
        when (val loadableStateCopy = hotelList.value) {
            LoadableState.Error -> {
                Text("Error fetching hotel list")
            }

            is LoadableState.Success -> {
                Text(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                    text = "Results:",
                    style = MaterialTheme.typography.subtitle1
                )
                loadableStateCopy.value.forEach { hotelListing ->
                    HotelListingView(
                        hotelListing = hotelListing,
                        onHotelSelected = {
                            onHotelSelected(hotelListing)
                        }
                    )
                }
            }

            LoadableState.Loading -> {
                FullScreenLoader()
            }

            null -> {
                // NoOp
            }
        }
    }
}
