package me.eijaz.shopifyapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import okhttp3.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Creating a LinearLayoutManager for RecyclerView for "Orders By Year"
        rv_orders_2017.layoutManager = LinearLayoutManager(this)
        rv_orders_2016.layoutManager = LinearLayoutManager(this)

        val URL = "https://shopicruit.myshopify.com/admin/orders.json?page=1&access_token=c32313df0d0ef512ca64d5b336a0d7c6"

        //Using OKHttp and Gson libraries to load and parse JSON
        val request = Request.Builder().url(URL).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()

                val gson = GsonBuilder().create()
                val results: Results = gson.fromJson(body, Results::class.java)

                var ordersIn2017: Int = 0
                var ordersIn2016: Int = 0

                var numAlabamaOrders: Int = 0
                var numAlaskaOrders: Int = 0
                var numArkansasOrders: Int = 0
                var numArizonaOrders: Int = 0
                var numCaliforniaOrders: Int = 0
                var numColoradoOrders: Int = 0
                var numConnecticutOrders: Int = 0
                var numFloridaOrders: Int = 0
                var numGeorgiaOrders: Int = 0

                val numberOfOrders = results.orders.size
                var orderList2017: ArrayList<Order> = ArrayList()
                var orderList2016: ArrayList<Order> = ArrayList()

                //loop through orders
                //add up orders in each state and in each year
                //Make a list of orders in 2017 and 2016
                if(numberOfOrders > 0) {
                    var i = 0
                    while (i < numberOfOrders) {

                        when (results.orders[i].created_at.substring(0,4)) {
                            "2017" -> {
                                ordersIn2017++
                                orderList2017.add(results.orders[i])
                            }
                            "2016" -> {
                                ordersIn2016++
                                orderList2016.add(results.orders[i])
                            }
                        }

                        when (results.orders[i].shipping_address?.province_code) {
                            "AL" -> numAlabamaOrders++
                            "AK" -> numAlaskaOrders++
                            "AR" -> numArkansasOrders++
                            "AZ" -> numArizonaOrders++
                            "CA" -> numCaliforniaOrders++
                            "CO" -> numColoradoOrders++
                            "CT" -> numConnecticutOrders++
                            "FL" -> numFloridaOrders++
                            "GA" -> numGeorgiaOrders++
                        }
                        i++
                    }
                }

                //Update UI with "Order By Province" and "Order By Year" summary
                //Load list of orders into RecyclerView adapters
                runOnUiThread {
                    rv_orders_2017.adapter = OrderAdapter(orderList2017)
                    rv_orders_2016.adapter = OrderAdapter(orderList2016)

                    tv_2017.text = getString(R.string.orders_2017, ordersIn2017)
                    tv_2016.text = getString(R.string.orders_2016, ordersIn2016)
                    tv_alabama.text = getString(R.string.orders_alabama, numAlabamaOrders)
                    tv_alaska.text = getString(R.string.orders_alaska, numAlaskaOrders)
                    tv_arkansas.text = getString(R.string.orders_arkansas, numArkansasOrders)
                    tv_arizona.text = getString(R.string.orders_arizona, numArizonaOrders)
                    tv_california.text = getString(R.string.orders_california, numCaliforniaOrders)
                    tv_colorado.text = getString(R.string.orders_colorado, numColoradoOrders)
                    tv_connecticut.text = getString(R.string.orders_connecticut, numConnecticutOrders)
                    tv_florida.text = getString(R.string.orders_florida, numFloridaOrders)
                    tv_georgia.text = getString(R.string.orders_georgia, numGeorgiaOrders)
                }
            }
            override fun onFailure(call: Call?, e: IOException?) {
                println("Failed to execute request")
            }
        })



    }
}


