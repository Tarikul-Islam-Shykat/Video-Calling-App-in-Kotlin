package com.example.videocallingappkotlin.signUp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.videocallingappkotlin.MainActivity
import com.example.videocallingappkotlin.R
import com.example.videocallingappkotlin.databinding.ActivitySignUpBinding
import com.example.videocallingappkotlin.signIn.signIn
import com.example.videocallingappkotlin.userDatabase.user
import com.google.firebase.auth.ActionCodeEmailInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class signUp : AppCompatActivity() {

    private  lateinit var  singUpBinding: ActivitySignUpBinding
    lateinit var email : String
    lateinit  var password : String
    lateinit var  name : String
    lateinit var  auth: FirebaseAuth
    lateinit var  database: FirebaseFirestore

    var emailPattern =  "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_sign_up)
        singUpBinding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(singUpBinding.root)
        supportActionBar?.hide()

        auth = Firebase.auth
        database = FirebaseFirestore.getInstance()

        singUpBinding.btnSignUp.setOnClickListener {
            name = singUpBinding.edtSignUpName.text.toString()
            email =  singUpBinding.edtSignUpEmail.text.toString()
            password = singUpBinding.edtSignUpPassword.text.toString()

            if(name.isBlank() || email.isBlank() || password.isBlank())
            { Toast.makeText(applicationContext, "Please fill up the credential", Toast.LENGTH_SHORT).show() }

            else if(password.length< 6)
            { singUpBinding.edtSignUpPassword.setError("Password less than 6") }

            else if(!email.matches(emailPattern))
            { Toast.makeText(applicationContext, "Email Can't Be accepted", Toast.LENGTH_SHORT).show()}

            else
            { authentication_signUp(name , email, password) }

        }
        singUpBinding.txtSingUpAlready.setOnClickListener {
            startActivity(Intent(this, signIn::class.java))
            finish()
        }
    }
    fun authentication_signUp(name: String, email: String, password: String)
    {
        val user = user(name, email, password)
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                database.collection("Users")
                    .document()
                    .set(user)
                    .addOnSuccessListener {
                        Toast.makeText(applicationContext, "Successful", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, signIn::class.java))
                        finish()
                    }
            }
            else {
                Toast.makeText(applicationContext, "Unsuccessful", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

