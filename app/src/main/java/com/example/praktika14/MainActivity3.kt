package com.example.praktika14

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import org.json.JSONObject
import java.io.IOException
import java.net.URL
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.net.ssl.HttpsURLConnection

private lateinit var Ip2TextV: TextView
private lateinit var hostnameTextV: TextView
private lateinit var cityTextV: TextView
private lateinit var regionTextV: TextView
private lateinit var countryTextV: TextView
private lateinit var locTextV: TextView
private lateinit var postalTextV: TextView
private lateinit var timezoneTextV: TextView
private lateinit var sepIp: String

class MainActivity3 : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_info)
        sepIp = intent.getStringExtra("superIp").toString()
        Ip2TextV = findViewById(R.id.IpTextView2)
        hostnameTextV = findViewById(R.id.hostnameTextView)
        cityTextV = findViewById(R.id.cityTextView)
        regionTextV = findViewById(R.id.regionTextView)
        countryTextV = findViewById(R.id.countryTextView)
        locTextV = findViewById(R.id.locTextView)
        postalTextV = findViewById(R.id.postalTextView)
        timezoneTextV = findViewById(R.id.timezoneTextView)
        val executorService: ExecutorService = Executors.newSingleThreadExecutor()
        val objectMogeIfo =  executorService.submit(Callable {
            httpsRequest("https://ipinfo.io/$sepIp/json?")
        }).get()

        Ip2TextV.text = objectMogeIfo.ip
        hostnameTextV.text = objectMogeIfo.hostname
        cityTextV.text = objectMogeIfo.city
        regionTextV.text = objectMogeIfo.region
        countryTextV.text = objectMogeIfo.country
        locTextV.text = objectMogeIfo.loc
        postalTextV.text = objectMogeIfo.postal
        timezoneTextV.text = objectMogeIfo.timezone
    }

    @Throws(IOException::class)
    fun httpsRequest(urlString: String): MoreInfoForIp {
        val url = URL(urlString)
        val connection = url.openConnection() as HttpsURLConnection
        connection.requestMethod = "GET"
        var data: Int = connection.inputStream.read()
        var str = ""
        while (data != -1) {
            str += data.toChar()
            data = connection.inputStream.read()
        }
        val mainObject = JSONObject(str)
        val item = MoreInfoForIp(
            mainObject.getString("ip"),
            mainObject.getString("hostname"),
            mainObject.getString("city"),
            mainObject.getString("region"),
            mainObject.getString("country"),
            mainObject.getString("loc"),
            mainObject.getString("postal"),
            mainObject.getString("timezone"),
            mainObject.getString("readme"),
        )
        return item
    }
}