package com.lovato.taller04_pdm.models

data class Coin(
                var name: String = "N/A",
                var country: String = "N/A",
                var year: Int = 3,
                var isAvailable:Int = 3,
                var value_us:Double= 0.0,
                var img:String = "N/A")
