package com.lovato.taller04_pdm.models

data class Coin(var name: String,
                var country: String,
                var year: Int,
                var isAvailable:Int,
                var value_us:Double,
                var img:String)
