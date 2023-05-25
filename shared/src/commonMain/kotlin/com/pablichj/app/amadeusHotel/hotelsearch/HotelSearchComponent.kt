package com.pablichj.app.amadeusHotel.hotelsearch

import FormParam
import QueryParam
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.pablichj.app.amadeusHotel.InMemoryAccessTokenDao
import com.pablichj.app.amadeusHotel.shared.BuildConfig
import com.pablichj.app.amadeusHotel.ui.FullScreenLoader
import com.pablichj.app.amadeusHotel.ui.LoadableState
import com.pablichj.incubator.amadeus.common.CallResult
import com.pablichj.incubator.amadeus.common.model.City
import com.pablichj.incubator.amadeus.endpoint.accesstoken.GetAccessTokenRequest
import com.pablichj.incubator.amadeus.endpoint.accesstoken.GetAccessTokenUseCase
import com.pablichj.incubator.amadeus.endpoint.accesstoken.ResolveAccessTokenUseCaseSource
import com.pablichj.incubator.amadeus.endpoint.accesstoken.model.AccessToken
import com.pablichj.incubator.amadeus.endpoint.city.CitySearchRequest
import com.pablichj.incubator.amadeus.endpoint.city.CitySearchUseCase
import com.pablichj.incubator.amadeus.endpoint.hotels.HotelsByCityRequest
import com.pablichj.incubator.amadeus.endpoint.hotels.HotelsByCityUseCase
import com.pablichj.incubator.amadeus.endpoint.hotels.model.HotelListing
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.consumeBackPressEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HotelSearchComponent(
    val onHotelSelected: (hotelListing: HotelListing) -> Unit
) : Component() {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    /*private val timeProvider: ITimeProvider = DefaultTimeProvider()
    private val accessTokenDao = AccessTokenDaoDelight(
        database,
        timeProvider
    )*/
    private val accessTokenDao = InMemoryAccessTokenDao()

    private suspend fun getAccessToken(): AccessToken? {
        val clientId = BuildConfig.AMADEUS_API_KEY
        val clientSecret = BuildConfig.AMADEUS_API_SECRET
        val callResult = GetAccessTokenUseCase(Dispatchers).doWork(
            GetAccessTokenRequest(
                listOf(
                    FormParam.ClientId(clientId),
                    FormParam.ClientSecret(clientSecret),
                    FormParam.GrantType(GetAccessTokenUseCase.AccessTokenGrantType),
                )
            )
        )
        return when (callResult) {
            is CallResult.Error -> {
                println("Error fetching access token: ${callResult.error}")
                null
            }

            is CallResult.Success -> {
                callResult.responseBody.also {
                    accessTokenDao.insert(it)
                    println("SQDelight Insert Token Success: $it")
                }
            }
        }
    }

    private suspend fun getCitiesByKeyword(city: String): List<City>? {
        val accessToken = ResolveAccessTokenUseCaseSource(
            Dispatchers,
            accessTokenDao
        ).doWork()

        if (accessToken == null) {
            println("No saved token")
            return null//todo: return no-token-error
        } else {
            println("Using saved token: ${accessToken.accessToken}")
        }

        val callResult = CitySearchUseCase(
            Dispatchers
        ).doWork(
            //?countryCode=FR&keyword=PARIS&max=10
            CitySearchRequest(
                accessToken,
                listOf(
                    QueryParam.CountryCode("US"),
                    QueryParam.Max("3"),
                    QueryParam.Keyword(city)
                )
            )
        )

        return when (callResult) {
            is CallResult.Error -> {
                println("Error in city search: ${callResult.error}")
                null
            }

            is CallResult.Success -> {
                callResult.responseBody.data
            }
        }
    }

    private suspend fun getHotelsByCity(iataCityCode: String): List<HotelListing>? {
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

        val callResult = HotelsByCityUseCase(
            Dispatchers
        ).doWork(
            //?cityCode=PAR&radius=1&radiusUnit=KM&hotelSource=ALL
            HotelsByCityRequest(
                accessToken,
                listOf(
                    QueryParam.CityCode(iataCityCode),
                    QueryParam.Radius("2"),
                    QueryParam.RadiusUnit("KM"),
                    QueryParam.HotelSource("ALL")
                )
            )
        )

        return when (callResult) {
            is CallResult.Error -> {
                println("Error in hotels by city: ${callResult.error}")
                null
            }

            is CallResult.Success -> {
                callResult.responseBody.data
            }
        }
    }

    private fun doHotelSearch(place: String) {
        coroutineScope.launch {
            loadableState.value = LoadableState.Loading
            accessTokenDao.lastOrNull() ?: run {
                getAccessToken()
            }

            val city = getCitiesByKeyword(place)?.firstOrNull { it.iataCode.isNotEmpty() }
            val iataCode = city?.iataCode
            if (iataCode.isNullOrEmpty()) {
                println("No city found within the US with that name")
                loadableState.value = LoadableState.Error
                return@launch
            }
            val hotelList = getHotelsByCity(iataCode)
            loadableState.value = if (hotelList.isNullOrEmpty()) {
                println("No hotel list returned for city with IATA code:$iataCode")
                LoadableState.Error
            } else {
                LoadableState.Success(hotelList)
            }
        }
    }

    private var loadableState = mutableStateOf<LoadableState<List<HotelListing>>?>(null)

    @Composable
    override fun Content(modifier: Modifier) {
        println("HotelSearchComponent::Composing()")
        consumeBackPressEvent()
        HotelSearchByCityForm(
            modifier = modifier,
            onCitySearchRequest = { cityName ->
                doHotelSearch(cityName)
            },
            onHotelSelected = { hotelListing ->
                onHotelSelected(hotelListing)
            },
            hotelList = loadableState
        )
    }

}
