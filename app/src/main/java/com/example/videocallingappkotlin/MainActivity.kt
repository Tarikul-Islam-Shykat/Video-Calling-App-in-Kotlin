package com.example.videocallingappkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.videocallingappkotlin.databinding.ActivityMainBinding
import com.example.videocallingappkotlin.signIn.signIn
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.jitsi.meet.sdk.JitsiMeet
import org.jitsi.meet.sdk.JitsiMeetActivity
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions
import java.net.MalformedURLException
import java.net.URL


class MainActivity : AppCompatActivity() {

    private  lateinit var  mainBinding: ActivityMainBinding
    lateinit var  auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        supportActionBar?.hide()

        auth = Firebase.auth

        if (auth.currentUser == null) {
            Toast.makeText(applicationContext, "Please Log In", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, signIn::class.java))
            finish()
        }

        val serverUrl: URL

        try {
            serverUrl = URL("https://meet.jit.si")
            val options = JitsiMeetConferenceOptions.Builder()
                .setServerURL(serverUrl)
                .setWelcomePageEnabled(false)
                .build()
            JitsiMeet.setDefaultConferenceOptions(options)
        } catch (e: MalformedURLException) {
            Toast.makeText(applicationContext, "Server Error", Toast.LENGTH_SHORT).show()
        }

        mainBinding.joinBtn.setOnClickListener {
            val option = JitsiMeetConferenceOptions.Builder()
                .setRoom(mainBinding.codeBox.getText().toString())
                .setWelcomePageEnabled(false)
                .build()

            JitsiMeetActivity.launch(this, option)
        }
        mainBinding.logOut.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, signIn::class.java))
            finish()
        }
    }
}