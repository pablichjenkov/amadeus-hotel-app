package com.pablichj.app.amadeusHotel.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.Modifier
import com.pablichj.app.amadeusHotel.booking.BookingResultComponent
import com.pablichj.incubator.amadeus.endpoint.hotels.model.HotelListing
import com.pablichj.incubator.amadeus.endpoint.offers.hotel.model.HotelOfferSearch
import com.pablichj.app.amadeusHotel.booking.OfferFullDetailComponent
import com.pablichj.app.amadeusHotel.booking.PaymentComponent
import com.pablichj.app.amadeusHotel.hoteloffers.HotelOffersComponent
import com.pablichj.app.amadeusHotel.hotelsearch.HotelSearchComponent
import com.pablichj.incubator.amadeus.endpoint.booking.hotel.model.HotelBookingConfirmation
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.stack.StackBarItem
import com.pablichj.templato.component.core.stack.StackComponent
import com.pablichj.templato.component.core.topbar.TopBarComponent
import com.pablichj.templato.component.core.topbar.TopBarStatePresenterDefault

class HomeComponent : TopBarComponent<TopBarStatePresenterDefault>(
    topBarStatePresenter = createDefaultTopBarStatePresenter()
) {
    private var hotelSearchComponent: HotelSearchComponent? = null
    private var hotelOffersComponent: HotelOffersComponent? = null
    private var offerFullDetailComponent: OfferFullDetailComponent? = null
    private var paymentComponent: PaymentComponent? = null
    private var bookingResultComponent: BookingResultComponent? = null

    override fun onStart() {
        println("HomeComponent::start()")
        if (activeComponent.value != null) {
            activeComponent.value?.dispatchStart()
        } else {
            launchHotelSearchComponent()
        }
    }

    override fun getStackBarItemForComponent(topComponent: Component): StackBarItem {
        return when (topComponent) {
            is HotelSearchComponent -> {
                StackBarItem(
                    label = "Hotel Search",
                    icon = Icons.Default.Home,
                )
            }

            is HotelOffersComponent -> {
                StackBarItem(
                    label = "Hotel Offers",
                    icon = Icons.Default.Search,
                )
            }

            else -> {
                StackBarItem(
                    label = "Offer Booking",
                    icon = Icons.Default.Add,
                )
            }
        }
    }

    private fun launchHotelSearchComponent() {
        HotelSearchComponent(
            onHotelSelected = { launchHotelOffersComponent(it) }
        ).also {
            hotelSearchComponent = it
            it.setParent(this@HomeComponent)
            backStack.push(it)
        }
    }

    private fun launchHotelOffersComponent(hotelListing: HotelListing) {
        HotelOffersComponent(
            hotelListing = hotelListing,
            onOfferSelected = {
                launchOfferFullDetailsComponent(it)
            }
        ).also {
            hotelOffersComponent = it
            it.setParent(this@HomeComponent)
            backStack.push(it)
        }
    }

    private fun launchOfferFullDetailsComponent(offer: HotelOfferSearch.Offer) {
        OfferFullDetailComponent(
            offerId = offer.id,
            onPurchaseClick = {
                launchOfferBookingComponent(it)
            }
        ).also {
            offerFullDetailComponent = it
            it.setParent(this@HomeComponent)
            backStack.push(it)
        }
    }

    private fun launchOfferBookingComponent(offer: HotelOfferSearch.Offer) {
        PaymentComponent(
            offer = offer,
            onPaymentResult = {
                launchBookingResultComponent(it)
            }
        ).also {
            paymentComponent = it
            it.setParent(this@HomeComponent)
            backStack.push(it)
        }
    }

    private fun launchBookingResultComponent(confirmations: List<HotelBookingConfirmation>) {
        BookingResultComponent(
            confirmations = confirmations,
            onAcceptClick = {
                backStack.popToIndex(0)
            }
        ).also {
            bookingResultComponent = it
            it.setParent(this@HomeComponent)
            backStack.push(it)
        }
    }

}
