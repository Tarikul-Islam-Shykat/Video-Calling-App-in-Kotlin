package com.example.videocallingappkotlin.splash

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import com.example.videocallingappkotlin.MainActivity
import com.example.videocallingappkotlin.R
import com.example.videocallingappkotlin.signUp.signUp

class splash_screen : AppCompatActivity() {

    lateinit var  handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()

        if(isConnected() == false)
        {
            var builder = AlertDialog.Builder(applicationContext)

            builder.setTitle("No internet Connection")
            builder.setMessage("Do you want to exit ? ")

            builder.setPositiveButton("Yes"){ dialogInterface, which->
                finish()
                System.exit(0)
            }

            builder.setNegativeButton("Check Wifi"){ dialogInterface, which->
                startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
                finish()
                System.exit(0)

            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }
        else
        {
            handler = Handler()
            handler.postDelayed({
                val intent  = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            }, 2000)
        }


    }

    fun isConnected(): Boolean{
        var connectivityManager: ConnectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var netwrork : NetworkInfo? = connectivityManager.activeNetworkInfo
        if(netwrork!= null){
            if(netwrork.isConnected){
                return true
            }
        }
        return false
    }
}