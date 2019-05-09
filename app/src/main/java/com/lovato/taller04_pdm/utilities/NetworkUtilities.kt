package com.lovato.taller04_pdm.utilities

import android.net.Uri
import java.net.URL

class NetworkUtilities{



    companion object {
        const val BASE_URL = "https://coinsapipdm.herokuapp.com/"
        const val PATH_COIN = "coins"
        const val TOKEN = "AS"


        fun buildURL(id: String) = URL(
            Uri.parse(BASE_URL)
                .buildUpon()
                .appendPath(PATH_COIN)
                .appendPath(id)
                .build().toString()
        )

        fun buildURL() = URL(
            Uri.parse(BASE_URL)
                .buildUpon()
                .build().toString()
        )

        fun getHTTPResult(url: URL) = url.readText()
}
}