package com.pablichj.app.amadeusHotel.hoteloffers

import QueryParam
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.pablichj.app.amadeusHotel.InMemoryAccessTokenDao
import com.pablichj.app.amadeusHotel.ui.FullScreenLoader
import com.pablichj.app.amadeusHotel.ui.LoadableState
import com.pablichj.incubator.amadeus.common.CallResult
import com.pablichj.incubator.amadeus.endpoint.accesstoken.ResolveAccessTokenUseCaseSource
import com.pablichj.incubator.amadeus.endpoint.hotels.model.HotelListing
import com.pablichj.incubator.amadeus.endpoint.offers.hotel.MultiHotelOffersRequest
import com.pablichj.incubator.amadeus.endpoint.offers.hotel.MultiHotelOffersUseCase
import com.pablichj.incubator.amadeus.endpoint.offers.hotel.model.HotelOfferSearch
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.consumeBackPressEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HotelOffersComponent(
    private val hotelListing: HotelListing,
    private val onOfferSelected: (HotelOfferSearch.Offer) -> Unit
) : Component() {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val accessTokenDao = InMemoryAccessTokenDao()

    private suspend fun getMultiHotelsOffers(
        hotelOffersRequestData: HotelOffersRequestData
    ): List<HotelOfferSearch> {
        val accessToken = ResolveAccessTokenUseCaseSource(
            Dispatchers,
            accessTokenDao
        ).doWork()

        if (accessToken == null) {
            println("No saved token")
            return emptyList()
        } else {
            println("Using saved token: ${accessToken.accessToken}")
        }

        val callResult = MultiHotelOffersUseCase(
            Dispatchers
        ).doWork(
            MultiHotelOffersRequest(
                accessToken,
                listOf(
                    QueryParam.HotelIds(hotelOffersRequestData.hotelId),
                    QueryParam.Adults(hotelOffersRequestData.numberOfAdults),
                    QueryParam.CheckInDate(hotelOffersRequestData.checkingDate),
                    QueryParam.RoomQuantity(hotelOffersRequestData.roomQuantity),
                    QueryParam.BestRateOnly("false")
                )
            )
        )

        return when (callResult) {
            is CallResult.Error -> {
                println("Error in multi hotel offers: ${callResult.error}")
                emptyList()
            }

            is CallResult.Success -> {
                callResult.responseBody.data
            }
        }
    }

    private fun doHotelOffersSearch(hotelOffersRequestData: HotelOffersRequestData) {
        coroutineScope.launch {
            loadableState.value = LoadableState.Loading
            accessTokenDao.lastOrNull() ?: run {
                // getAccessToken()
                println("Token not found, start the flow again to get 1 token in the first step")
            }

            val hotelOffers = getMultiHotelsOffers(hotelOffersRequestData)
            loadableState.value = if (hotelOffers.isEmpty()) {
                LoadableState.Error
            } else {
                LoadableState.Success(hotelOffers)
            }
        }
    }

    private var loadableState = mutableStateOf<LoadableState<List<HotelOfferSearch>>?>(null)

    @Composable
    override fun Content(modifier: Modifier) {
        println("HotelOffersComponent::Composing()")
        consumeBackPressEvent()
        HotelOffersSearchView(
            hotelListing = hotelListing,
            hotelOffers = loadableState,
            onHotelOffersRequest = { doHotelOffersSearch(it) },
            onOfferSelected = { onOfferSelected(it) }
        )
    }

}
