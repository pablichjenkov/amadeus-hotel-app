package com.pablichj.app.amadeusHotel.booking

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.macaosoftware.component.core.BackPressHandler
import com.macaosoftware.component.core.Component
import com.pablichj.app.amadeusHotel.InMemoryAccessTokenDao
import com.pablichj.incubator.amadeus.common.CallResult
import com.pablichj.incubator.amadeus.endpoint.accesstoken.ResolveAccessTokenUseCaseSource
import com.pablichj.incubator.amadeus.endpoint.booking.hotel.HotelBookingRequest
import com.pablichj.incubator.amadeus.endpoint.booking.hotel.HotelBookingUseCase
import com.pablichj.incubator.amadeus.endpoint.booking.hotel.model.HotelBookingConfirmation
import com.pablichj.incubator.amadeus.endpoint.booking.hotel.model.HotelBookingRequestBody
import com.pablichj.incubator.amadeus.endpoint.offers.hotel.model.HotelOfferSearch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PaymentComponent(
    private val offer: HotelOfferSearch.Offer,
    private val onPaymentResult: (List<HotelBookingConfirmation>) -> Unit
) : Component() {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val accessTokenDao = InMemoryAccessTokenDao()

    private suspend fun doOfferBookingRequest(
        bookingRequestBody: HotelBookingRequestBody
    ): List<HotelBookingConfirmation> {
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

        val callResult = HotelBookingUseCase(
            Dispatchers,
            HotelBookingRequest(
                accessToken,
                bookingRequestBody
            )
        ).doWork()

        return when (callResult) {
            is CallResult.Error -> {
                println("Error in hotel booking: ${callResult.error}")
                emptyList()
            }

            is CallResult.Success -> {
                callResult.responseBody.data.forEach {
                    println(
                        """
                            Booking Confirmation Id: ${it.id}
                            Provider Confirmation Id: ${it.providerConfirmationId}
                            Booking Confirmation Type: ${it.type}
                            Booking Associated Records:
                        """.trimIndent()
                    )
                    it.associatedRecords.forEach {
                        println(
                            """
                                Record Reference: ${it.reference}
                                Record Origin System Code: ${it.originSystemCode}
                            """.trimIndent()
                        )
                    }
                }
                callResult.responseBody.data
            }
        }
    }

    private fun doRoomOfferBooking(paymentFormState: PaymentFormState) {
        coroutineScope.launch {
            accessTokenDao.lastOrNull() ?: run {
                // getAccessToken()
                println("Token not found, start the flow again to get 1 token in the first step")
            }

            val offerBookingRequestBody = paymentFormState.toRoomOfferBookingRequestBody(offer.id)
            onPaymentResult(doOfferBookingRequest(offerBookingRequestBody))
        }
    }

    @Composable
    override fun Content(modifier: Modifier) {
        println("PaymentComponent::Composing()")
        BackPressHandler()
        PaymentForm(
            modifier = modifier,
            onSubmitBookingRequest = {
                doRoomOfferBooking(it)
            }
        )
    }

}
