package com.lovato.taller04_pdm.utilities


import com.lovato.taller04_pdm.models.Coin
import org.json.JSONArray
import org.json.JSONObject

class CoinSerializer{
    companion object {
        fun parseCoins(coinsText: String): List<Coin>{
            var coinsJSON = JSONArray(coinsText)
            return MutableList(coinsJSON.length()){
                parseCoin(coinsJSON[it].toString())
            }
        }

        fun parseCoin(coinsText: String): Coin{
            val coinJSON = JSONObject(coinsText)
            return with(coinJSON){
                Coin(getString("name"),
                    getString("country"),
                    getInt("year"),
                    getInt("isAvailable"),
                    getDouble("value_us"),
                    getString("img"))

            }
        }
    }
}