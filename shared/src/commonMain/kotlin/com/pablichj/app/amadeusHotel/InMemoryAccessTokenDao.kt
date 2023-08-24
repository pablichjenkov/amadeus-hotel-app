package com.pablichj.app.amadeusHotel

import com.pablichj.incubator.amadeus.endpoint.accesstoken.IAccessTokenDao
import com.pablichj.incubator.amadeus.endpoint.accesstoken.model.AccessToken

class InMemoryAccessTokenDao : IAccessTokenDao {

    companion object {
        private val accessTokens = mutableListOf <AccessToken>()
    }

    override suspend fun all(): List<AccessToken> {
        return accessTokens
    }

    override suspend fun insert(accessToken: AccessToken) {
        accessTokens.add(accessToken)
    }

    override suspend fun lastOrNull(): AccessToken? {
        return accessTokens.lastOrNull()
    }

}