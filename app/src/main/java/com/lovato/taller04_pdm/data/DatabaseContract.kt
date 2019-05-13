package com.lovato.taller04_pdm.data

import android.provider.BaseColumns


object DatabaseContract {

    // TODO (4) Para cada tabla en la base de datos se define una entrada (Entry)
    // TODO (4.1) Cada entrada debe definir el nombre de la tabla y el de cada columna
    // TODO (4.2) Debe de heredar de BaseColumns. Para heredar el nombre de las columnas tradicionales como _ID, _COUNT y otros comportamientos básicos.
    object CoinEntry : BaseColumns { // Se guardan los datos relevantes de la tabla, como su nombre y sus campos.

        const val TABLE_NAME = "Coin"

        // Se crea una constante por cada columna de la tabla.
        const val COLUMN_NAME = "name"
        const val COLUMN_COUNTRY = "country"
        const val COLUMN_YEAR = "year"
        const val COLUMN_ISAVAILABLE = "is_available"
        const val COLUMN_VALUE_US = "value_us"
        const val COLUMN_IMG = "img"

    }


}