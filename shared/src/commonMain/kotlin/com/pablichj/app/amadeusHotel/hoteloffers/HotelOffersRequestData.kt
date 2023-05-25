package com.pablichj.app.amadeusHotel.hoteloffers

data class HotelOffersRequestData(
    val hotelId: String,
    val checkingDate: String,
    val numberOfAdults: String,
    val roomQuantity: String
)
