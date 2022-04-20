package com.example.videocallingappkotlin.signIn

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.example.videocallingappkotlin.MainActivity
import com.example.videocallingappkotlin.R
import com.example.videocallingappkotlin.databinding.ActivitySignInBinding
import com.example.videocallingappkotlin.signUp.signUp
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.math.sign

class signIn : AppCompatActivity() {

    private lateinit var  signIn: ActivitySignInBinding
    lateinit var  email: String
    lateinit var  password: String
    lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signIn = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(signIn.root)
        supportActionBar?.hide()

        auth = Firebase.auth

        signIn.btnSignIn.setOnClickListener {
            email = signIn.edtSignInEmail.text.toString()
            password= signIn.edtSignInPassword.text.toString()

            Toast.makeText(applicationContext, email +" "+ password, Toast.LENGTH_SHORT).show()

            if(email.isBlank() || password.isBlank()) { Toast.makeText(applicationContext, "Please Enter credential", Toast.LENGTH_SHORT).show() }


            if(password.length < 6) { signIn.edtSignInPassword.setError("Password Is less than 6") }

            else { authentication_sign(email, password) }
        }
        signIn.txtNoAccount.setOnClickListener {
            startActivity(Intent(this, signUp::class.java))
            finish()
        }
    }

    fun authentication_sign(email: String , password: String){
        auth.signInWithEmailAndPassword(email, password ).addOnCompleteListener(this) { task ->

            if(task.isSuccessful)
            {
                Toast.makeText(applicationContext, "Log in Successful", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            else{ Toast.makeText(applicationContext, "User Don't Exist", Toast.LENGTH_SHORT).show() }
        }
    }
}