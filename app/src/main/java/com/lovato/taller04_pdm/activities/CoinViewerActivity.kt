package com.lovato.taller04_pdm.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.lovato.taller04_pdm.R
import kotlinx.android.synthetic.main.viewer_coin.*

class CoinViewerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.viewer_coin)


        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        Glide.with(this)
                .load(intent.extras.getString("img"))
                .placeholder(R.drawable.ic_launcher_background)
                .into(image_viewer_coin)

        coin_title_vc.text = intent.extras.getString("name")
        vc_country_tv.text = intent.extras.getString("country")
        vc_value_us_tv.text = intent.extras.getDouble("value_us").toString()
        vc_year_tv.text = intent.extras.getInt("year").toString()
        vc_isAvailable_tv.text = when(intent.extras.getInt("isAvailable")){
            1-> "yes"
            0-> "no"
            else-> "no information"
        }
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {onBackPressed();true}
            else -> super.onOptionsItemSelected(item)
        }
    }
}

