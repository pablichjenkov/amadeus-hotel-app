package com.pablichj.app.amadeusHotel.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.topbar.TopBarComponent
import com.macaosoftware.component.topbar.TopBarComponentViewModel
import com.macaosoftware.component.topbar.TopBarItem
import com.macaosoftware.component.topbar.TopBarStatePresenterDefault
import com.pablichj.app.amadeusHotel.booking.BookingResultComponent
import com.pablichj.app.amadeusHotel.booking.OfferFullDetailComponent
import com.pablichj.app.amadeusHotel.booking.PaymentComponent
import com.pablichj.app.amadeusHotel.hoteloffers.HotelOffersComponent
import com.pablichj.app.amadeusHotel.hotelsearch.HotelSearchComponent
import com.pablichj.incubator.amadeus.endpoint.booking.hotel.model.HotelBookingConfirmation
import com.pablichj.incubator.amadeus.endpoint.hotels.model.HotelListing
import com.pablichj.incubator.amadeus.endpoint.offers.hotel.model.HotelOfferSearch

class HomeComponentViewModel : TopBarComponentViewModel<TopBarStatePresenterDefault>() {

    private lateinit var homeComponent: TopBarComponent<TopBarStatePresenterDefault>
    private var hotelSearchComponent: HotelSearchComponent? = null
    private var hotelOffersComponent: HotelOffersComponent? = null
    private var offerFullDetailComponent: OfferFullDetailComponent? = null
    private var paymentComponent: PaymentComponent? = null
    private var bookingResultComponent: BookingResultComponent? = null

    override fun onCreate(topBarComponent: TopBarComponent<TopBarStatePresenterDefault>) {
        homeComponent = topBarComponent
    }

    override fun onStart() {
        println("HomeComponentViewModel::onStart()")
        launchHotelSearchComponent()
    }

    override fun onStop() {
    }

    override fun onDestroy() {
    }

    override fun mapComponentToStackBarItem(topComponent: Component): TopBarItem {
        return when (topComponent) {
            is HotelSearchComponent -> {
                TopBarItem(
                    label = "Hotel Search",
                    icon = Icons.Default.Home,
                )
            }

            is HotelOffersComponent -> {
                TopBarItem(
                    label = "Hotel Offers",
                    icon = Icons.Default.Search,
                )
            }

            else -> {
                TopBarItem(
                    label = "Offer Booking",
                    icon = Icons.Default.Add,
                )
            }
        }
    }

    override fun onBackstackEmpty() {
    }

    override fun onCheckChildForNextUriFragment(nextUriFragment: String): Component? {
    return null
    }

    private fun launchHotelSearchComponent() {
        HotelSearchComponent(
            onHotelSelected = { launchHotelOffersComponent(it) }
        ).also {
            hotelSearchComponent = it
            it.setParent(homeComponent)
            homeComponent.backStack.push(it)
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
            it.setParent(homeComponent)
            homeComponent.backStack.push(it)
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
            it.setParent(homeComponent)
            homeComponent.backStack.push(it)
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
            it.setParent(homeComponent)
            homeComponent.backStack.push(it)
        }
    }

    private fun launchBookingResultComponent(confirmations: List<HotelBookingConfirmation>) {
        BookingResultComponent(
            confirmations = confirmations,
            onAcceptClick = {
                homeComponent.backStack.popToIndex(0)
            }
        ).also {
            bookingResultComponent = it
            it.setParent(homeComponent)
            homeComponent.backStack.push(it)
        }
    }

}
