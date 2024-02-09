package com.pablichj.app.amadeusHotel.booking

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.macaosoftware.component.core.BackPressHandler
import com.macaosoftware.component.core.Component
import com.pablichj.app.amadeusHotel.InMemoryAccessTokenDao
import com.pablichj.app.amadeusHotel.ui.FullScreenLoader
import com.pablichj.app.amadeusHotel.ui.LoadableState
import com.pablichj.incubator.amadeus.common.CallResult
import com.pablichj.incubator.amadeus.endpoint.accesstoken.ResolveAccessTokenUseCaseSource
import com.pablichj.incubator.amadeus.endpoint.offers.hotel.GetOfferRequest
import com.pablichj.incubator.amadeus.endpoint.offers.hotel.GetOfferUseCase
import com.pablichj.incubator.amadeus.endpoint.offers.hotel.model.HotelOfferSearch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OfferFullDetailComponent(
    private val offerId: String,
    private val onPurchaseClick: (HotelOfferSearch.Offer) -> Unit
) : Component() {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val accessTokenDao = InMemoryAccessTokenDao()

    override fun onActive() {
        doRoomBooking()
    }

    private suspend fun getFullOfferDetails(offerId: String): HotelOfferSearch.Offer? {
        val accessToken = ResolveAccessTokenUseCaseSource(
            Dispatchers,
            accessTokenDao
        ).doWork()

        if (accessToken == null) {
            println("No saved token")
            return null
        } else {
            println("Using saved token: ${accessToken.accessToken}")
        }

        val callResult = GetOfferUseCase(
            Dispatchers,
            GetOfferRequest(
                accessToken,
                offerId
            )
        ).doWork()

        return when (callResult) {
            is CallResult.Error -> {
                println("Error in get offer by id: ${callResult.error}")
                null
            }

            is CallResult.Success -> {
                println("Offer in Hotel: ${callResult.responseBody.data.hotel.name}")
                return callResult.responseBody.data.offers.getOrNull(0)
            }
        }
    }

    private fun doRoomBooking() {
        coroutineScope.launch {
            accessTokenDao.lastOrNull() ?: run {
                // getAccessToken()
                println("Token not found, start the flow again to get 1 token in the first step")
            }

            val offer = getFullOfferDetails(offerId)
            loadableState = if (offer == null) {
                LoadableState.Error
            } else {
                LoadableState.Success(offer)
            }
        }
    }

    private var loadableState by mutableStateOf<LoadableState<HotelOfferSearch.Offer>?>(null)

    @Composable
    override fun Content(modifier: Modifier) {
        println("OfferFullDetailComponent::Composing()")
        BackPressHandler()
        when (val loadableStateCopy = loadableState) {
            LoadableState.Error -> {
                Text("Error confirming the offer price")
            }

            is LoadableState.Success -> {
                OfferFullDetailView(
                    modifier = modifier,
                    offer = loadableStateCopy.value,
                    onPurchaseClick = onPurchaseClick
                )
            }

            null,
            LoadableState.Loading -> {
                FullScreenLoader()
            }
        }
    }

}
