package com.pablichj.app.amadeusHotel.hoteloffers

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.pablichj.app.amadeusHotel.ui.FullScreenLoader
import com.pablichj.app.amadeusHotel.ui.LoadableState
import com.pablichj.incubator.amadeus.endpoint.hotels.model.HotelListing
import com.pablichj.incubator.amadeus.endpoint.offers.hotel.model.HotelOfferSearch

@Composable
internal fun HotelOffersSearchView(
    hotelListing: HotelListing,
    hotelOffers: State<LoadableState<List<HotelOfferSearch>>?>,//List<HotelOfferSearch>,
    onHotelOffersRequest: (HotelOffersRequestData) -> Unit,
    onOfferSelected: (HotelOfferSearch.Offer) -> Unit
) {
    val focus = LocalFocusManager.current
    Column(
        modifier = Modifier.fillMaxSize()
            .verticalScroll(rememberScrollState())
            .clickable { focus.clearFocus() }
    ) {
        HotelOffersSearchTopForm(
            hotelListing = hotelListing,
            onHotelOffersRequest = {
                onHotelOffersRequest(it)
            }
        )
        when (val loadableStateCopy = hotelOffers.value) {
            LoadableState.Error -> {
                Text("Error fetching hotel offers")
            }

            is LoadableState.Success -> {
                loadableStateCopy.value.forEach { hotelOffer ->
                    Text("Results for ${hotelOffer.hotel.name}:")
                    hotelOffer.offers.forEach { offer ->
                        HotelOfferView(
                            modifier = Modifier,
                            offer = offer,
                            onOfferClick = {
                                onOfferSelected(offer)
                            }
                        )
                    }
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun HotelOffersSearchTopForm(
    hotelListing: HotelListing,
    onHotelOffersRequest: (HotelOffersRequestData) -> Unit
) {
    val focus = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    var checkingDate by remember { mutableStateOf("2023-12-31") }
    var numberOfAdultGuests by remember { mutableStateOf("1") }
    var numberOfRooms by remember { mutableStateOf("1") }
    var errorMessage by remember { mutableStateOf("") }
    var acceptedTerms by remember { mutableStateOf(true) }

    Column(
        Modifier
            .padding(top = 32.dp)
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Fill reservation details",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = "Offers availability will be based on the data you input",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(Modifier.height(24.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = hotelListing.name ?: "No Name Provided"
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = checkingDate,
            label = { Text("CheckingDate") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    //focus.moveFocus(FocusDirection.Next)
                }
            ),
            onValueChange = { checkingDate = it },
            singleLine = true
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = numberOfAdultGuests,
            label = { Text("How Many Adult Guest") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focus.clearFocus()
                    keyboardController?.hide()
                    //onSubmit()
                }
            ),
            onValueChange = { numberOfAdultGuests = it },
            singleLine = true
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = numberOfRooms,
            label = { Text("How Many Rooms") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focus.clearFocus()
                    keyboardController?.hide()
                    onHotelOffersRequest(
                        HotelOffersRequestData(
                            hotelListing.hotelId!!,
                            checkingDate,
                            numberOfAdultGuests,
                            numberOfRooms
                        )
                    )
                }
            ),
            onValueChange = { numberOfRooms = it },
            singleLine = true
        )
        Button(
            onClick = {
                onHotelOffersRequest(
                    HotelOffersRequestData(
                        hotelListing.hotelId!!,
                        checkingDate,
                        numberOfAdultGuests,
                        numberOfRooms
                    )
                )
            },
            modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp)
        ) {
            Text("Search")
        }
    }
}