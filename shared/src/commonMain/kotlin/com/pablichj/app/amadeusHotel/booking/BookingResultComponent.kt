package com.pablichj.app.amadeusHotel.booking

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.pablichj.incubator.amadeus.endpoint.booking.hotel.model.HotelBookingConfirmation
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.consumeBackPressEvent

class BookingResultComponent(
    private val confirmations: List<HotelBookingConfirmation>,
    private val onAcceptClick: () -> Unit
) : Component() {

    private var result by mutableStateOf("")

    override fun onStart() {
        result = if (confirmations.isEmpty()) {
            "Booking Failed"
        } else {
            "Booking Success"
        }
    }

    @Composable
    override fun Content(modifier: Modifier) {
        println("PaymentComponent::Composing()")
        consumeBackPressEvent()
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = result,
                textAlign = TextAlign.Center,
                fontSize = 24.sp
            )
            Button(onClick = onAcceptClick) {
                Text("Accept")
            }
        }
    }

}
