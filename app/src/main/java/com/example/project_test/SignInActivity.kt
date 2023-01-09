package com.example.project_test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.project_test.databinding.ActivitySignInBinding
import com.example.project_test.ui.upload.UploadImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.sql.SQLException

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Initialize Firebase Auth
        auth = Firebase.auth
        binding.buttonSignIn.setOnClickListener{
            doLogin()
        }
        binding.TextNoAccRegister.setOnClickListener{
            val intent = Intent(this,signup_activity::class.java)



            startActivity(intent)
        }
    }
    private fun doLogin()
    {
        val inputemail=binding.InputEmail.text.toString()
        val inputpass=binding.InputPassword.text.toString()
        if (inputemail.isNotEmpty() && inputpass.isNotEmpty())
        {
            auth.signInWithEmailAndPassword(inputemail,inputpass).addOnCompleteListener(this) {
                if (it.isSuccessful)
                {
                    Toast.makeText(this, "Successful login", Toast.LENGTH_SHORT).show()
                    val intent= Intent(this,MainActivity::class.java)


                    MyApplication.Companion.email_id=inputemail
                    try {


                    var query="select username from accounts where accounts.email_id = '$inputemail' ;"
                    var con=UploadImage.conclass()
                    var stmt=con.createStatement()

                    var rs=stmt.executeQuery(query)
                    while (rs.next())
                    {
                        MyApplication.Companion.Username = rs.getString("username").toString()
                    }

                    }
                    catch (e:SQLException){
                        println(e.message)
                    }
                    startActivity(intent)
                }
                else
                {
                    Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}