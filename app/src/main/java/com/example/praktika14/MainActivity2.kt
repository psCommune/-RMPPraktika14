package com.example.praktika14

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import org.json.JSONObject
import java.io.IOException
import java.net.URL
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.net.ssl.HttpsURLConnection

private lateinit var IpTextV: TextView
private lateinit var MoreInfoB: Button
private lateinit var superIp: String

class MainActivity2 : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val executorService: ExecutorService = Executors.newSingleThreadExecutor()
        IpTextV = findViewById(R.id.IpTextView)
        MoreInfoB = findViewById(R.id.MoreInfoButton)
        IpTextV.text = executorService.submit(Callable {
            httpsRequest("https://api.ipify.org?format=json")
        }).get()
        MoreInfoB.setOnClickListener{
            val intent = Intent(this, MainActivity3::class.java)
            intent.putExtra("superIp", superIp)
            startActivity(intent)
        }
    }

    @Throws(IOException::class)
    fun httpsRequest(urlString: String): String {
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
        val item = YourIp(
            mainObject.getString("ip"),
        )
        superIp = mainObject.getString("ip")
        val Ip: String = item.ip
        return Ip
    }
}