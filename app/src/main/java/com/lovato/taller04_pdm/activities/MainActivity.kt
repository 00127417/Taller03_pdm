package com.lovato.taller04_pdm.activities

import android.content.ContentValues
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.lovato.taller04_pdm.R
import com.lovato.taller04_pdm.adapter.CoinAdapter
import com.lovato.taller04_pdm.data.Database
import com.lovato.taller04_pdm.data.DatabaseContract
import com.lovato.taller04_pdm.models.Coin
import com.lovato.taller04_pdm.utilities.CoinSerializer
import com.lovato.taller04_pdm.utilities.NetworkUtilities
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var dbHelper = Database(this)

    lateinit var viewAdapter: CoinAdapter
    lateinit var viewManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_main)

        setSupportActionBar(toolbar)

        viewManager = LinearLayoutManager(this)
        viewAdapter = CoinAdapter(listOf<Coin>()) {
            startActivity(Intent(this, CoinViewerActivity::class.java).putExtra("name",it.name)
                .putExtra("country",it.country)
                .putExtra("value_us",it.value_us)
                .putExtra("isAvailable",it.isAvailable)
                .putExtra("year",it.year)
                .putExtra("img",it.img))

        }

        rv_moneda.apply {
            adapter = viewAdapter
            layoutManager = viewManager
        }

        CoinsFetch().execute()


        // TODO (10) Click Listener para el boton flotante
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }


        // TODO (11) Permite administrar el DrawerLayout y el ActionBar
        // TODO (11.1) Implementa las caracteristicas recomendas
        // TODO (11.2) Un DrawerLayout (drawer_layout)
        // TODO (11.3) Un lugar donde dibujar el indicador de apertura (la toolbar)
        // TODO (11.4) Una String que describe el estado de apertura
        // TODO (11.5) Una String que describe el estado cierre
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )

        // TODO (12) Con el Listener Creado se asigna al  DrawerLayout
        drawer_layout.addDrawerListener(toggle)


        // TODO(13) Se sincroniza el estado del menu con el LISTENER
        toggle.syncState()

        // TODO (14) Se configura el listener del menu que aparece en la barra lateral
        // TODO (14.1) Es necesario implementar la inteface {{@NavigationView.OnNavigationItemSelectedListener}}
        nav_view.setNavigationItemSelectedListener(this)

        // TODO (20) Para saber si estamos en modo dos paneles



        /*
         * TODO (Instrucciones)Luego de leer todos los comentarios añada la implementación de RecyclerViewAdapter
         * Y la obtencion de datos para el API de Monedas
         */
    }


    // TODO (16) Para poder tener un comportamiento Predecible
    // TODO (16.1) Cuando se presione el boton back y el menu este abierto cerralo
    // TODO (16.2) De lo contrario hacer la accion predeterminada
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    // TODO (17) LLena el menu que esta en la barra. El de tres puntos a la derecha
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    // TODO (18) Atiende el click del menu de la barra
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    // TODO (14.2) Funcion que recibe el ID del elemento tocado
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            // TODO (14.3) Los Id solo los que estan escritos en el archivo de MENU
            R.id.search_all -> {

                viewAdapter.setData(readCoins())

            }
            R.id.search_salvador -> {
                viewAdapter.setData(readCoin("el salvador"))

            }
            R.id.search_guatemala -> {
                viewAdapter.setData(readCoin("guatemala"))

            }
            R.id.search_honduras -> {
                viewAdapter.setData(readCoin("honduras"))

            }
            R.id.search_nicaragua -> {
                viewAdapter.setData(readCoin("nicaragua"))

            }
            R.id.search_panama -> {
                viewAdapter.setData(readCoin("panama"))

            }

            R.id.search_belice -> {
                viewAdapter.setData(readCoin("belice"))

            }
        }

        // TODO (15) Cuando se da click a un opcion del menu se cierra de manera automatica
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
    private fun readCoin(query:String): List<Coin> {

// TODO(13) Para obtener los datos almacenados, es necesario solicitar una instancia de lectura de la base de datos.
        val db = dbHelper.readableDatabase

        val projection = arrayOf(
            BaseColumns._ID,
            DatabaseContract.CoinEntry.COLUMN_NAME,
            DatabaseContract.CoinEntry.COLUMN_COUNTRY,
            DatabaseContract.CoinEntry.COLUMN_YEAR,
            DatabaseContract.CoinEntry.COLUMN_ISAVAILABLE,
            DatabaseContract.CoinEntry.COLUMN_VALUE_US,
            DatabaseContract.CoinEntry.COLUMN_IMG
        )


        val whereClaus = "country = '${query}'"


        val cursor = db.query(
            DatabaseContract.CoinEntry.TABLE_NAME, // nombre de la tabla
            projection, // columnas que se devolverán
            whereClaus, // Columns where clausule
            null, // values Where clausule
            null, // Do not group rows
            null, // do not filter by row
            null // sort order
        )

        var lista = mutableListOf<Coin>()

        with(cursor) {
            while (moveToNext()) {
                var coin = Coin(
                    getString(getColumnIndexOrThrow(DatabaseContract.CoinEntry.COLUMN_NAME)),
                    getString(getColumnIndexOrThrow(DatabaseContract.CoinEntry.COLUMN_COUNTRY)),
                    getInt(getColumnIndexOrThrow(DatabaseContract.CoinEntry.COLUMN_YEAR)),
                    getInt(getColumnIndexOrThrow(DatabaseContract.CoinEntry.COLUMN_ISAVAILABLE)),
                    getDouble(getColumnIndexOrThrow(DatabaseContract.CoinEntry.COLUMN_VALUE_US)),
                    getString(getColumnIndexOrThrow(DatabaseContract.CoinEntry.COLUMN_IMG))
                )

                lista.add(coin)
            }
        }

        return lista
    }

    private fun readCoins(): List<Coin> {

// TODO(13) Para obtener los datos almacenados, es necesario solicitar una instancia de lectura de la base de datos.
        val db = dbHelper.readableDatabase

        val projection = arrayOf(
            BaseColumns._ID,
            DatabaseContract.CoinEntry.COLUMN_NAME,
            DatabaseContract.CoinEntry.COLUMN_COUNTRY,
            DatabaseContract.CoinEntry.COLUMN_YEAR,
            DatabaseContract.CoinEntry.COLUMN_ISAVAILABLE,
            DatabaseContract.CoinEntry.COLUMN_VALUE_US,
            DatabaseContract.CoinEntry.COLUMN_IMG
        )


        val cursor = db.query(
            DatabaseContract.CoinEntry.TABLE_NAME, // nombre de la tabla
            projection, // columnas que se devolverán
            null, // Columns where clausule
            null, // values Where clausule
            null, // Do not group rows
            null, // do not filter by row
            null // sort order
        )

        var lista = mutableListOf<Coin>()

        with(cursor) {
            while (moveToNext()) {
                var coin = Coin(
                    getString(getColumnIndexOrThrow(DatabaseContract.CoinEntry.COLUMN_NAME)),
                    getString(getColumnIndexOrThrow(DatabaseContract.CoinEntry.COLUMN_COUNTRY)),
                    getInt(getColumnIndexOrThrow(DatabaseContract.CoinEntry.COLUMN_YEAR)),
                    getInt(getColumnIndexOrThrow(DatabaseContract.CoinEntry.COLUMN_ISAVAILABLE)),
                    getDouble(getColumnIndexOrThrow(DatabaseContract.CoinEntry.COLUMN_VALUE_US)),
                    getString(getColumnIndexOrThrow(DatabaseContract.CoinEntry.COLUMN_IMG))
                )

                lista.add(coin)
            }
        }

        return lista
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }

    inner class CoinFetch : AsyncTask<String, Unit, List<Coin>>() {
        override fun doInBackground(vararg params: String): List<Coin> {


            val url = NetworkUtilities.buildURL(params[0])
            val resultString = NetworkUtilities.getHTTPResult(url)


            val resultJSON = JSONObject(resultString)

            return if(resultJSON.getBoolean("success")){
                CoinSerializer.parseCoins(
                    resultJSON.getJSONArray("coin").toString())
            }else{
                listOf()
            }
        }

        override fun onPostExecute(result: List<Coin>) {
            if(!result.equals("")){

                viewAdapter.setData(result)

            }else{
                Snackbar.make(rv_moneda,"No se pudo obtener monedas", Snackbar.LENGTH_SHORT).show()
            }
        }
    }


    inner class CoinsFetch : AsyncTask<Unit, Unit, List<Coin>>() {
        override fun doInBackground(vararg params: Unit?): List<Coin> {
            val url = NetworkUtilities.buildURL()
            val resultString = NetworkUtilities.getHTTPResult(url)
            val resultString2 = NetworkUtilities.getHTTPResult(url)

            val resultJSON = JSONObject(resultString)

            return if(resultJSON.getBoolean("success")){
                CoinSerializer.parseCoins(
                    resultJSON.getJSONArray("docs").toString())
            }else{
                listOf<Coin>()
            }
        }

        override fun onPostExecute(result: List<Coin>) {
            if(result.isNotEmpty()){

                val db = dbHelper.writableDatabase
                for (i in result){

                    val values = ContentValues().apply {
                        put(DatabaseContract.CoinEntry.COLUMN_NAME,i.name)
                        put(DatabaseContract.CoinEntry.COLUMN_COUNTRY,i.country)
                        put(DatabaseContract.CoinEntry.COLUMN_YEAR,i.year)
                        put(DatabaseContract.CoinEntry.COLUMN_ISAVAILABLE,i.isAvailable)
                        put(DatabaseContract.CoinEntry.COLUMN_VALUE_US,i.value_us)
                        put(DatabaseContract.CoinEntry.COLUMN_IMG,i.img)
                    }
                    val newRowId = db?.insert(DatabaseContract.CoinEntry.TABLE_NAME, null, values)

                    if (newRowId == -1L) {
                        Toast.makeText(this@MainActivity,R.string.alert_coin_not_saved,Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this@MainActivity, getString(R.string.alert_coin_saved_success) + newRowId, Toast.LENGTH_SHORT).show()
                    }
                }
                viewAdapter.setData(result)


            }else{
                Snackbar.make(rv_moneda,"No se pudo obtener monedas", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}
