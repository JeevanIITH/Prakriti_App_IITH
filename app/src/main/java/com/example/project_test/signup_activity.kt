package com.example.project_test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.project_test.databinding.ActivitySignupBinding
import com.example.project_test.ui.upload.UploadImage
import com.google.firebase.auth.FirebaseAuth
import java.sql.SQLException

class signup_activity : AppCompatActivity() {
    private lateinit var binding:ActivitySignupBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth=FirebaseAuth.getInstance()
        binding.TextSignIn.setOnClickListener{
            val intent=Intent(this,SignInActivity::class.java)
            startActivity(intent)
        }
        binding.buttonSignup.setOnClickListener {
            val username=binding.signupTextInputUsername.text.toString()
            val email=binding.signupTextInputEmail.text.toString()
            val pass=binding.signupTextInputCreatePass.text.toString()
            val confirmPass=binding.signupTextInputRePass.text.toString()
            if (username.isNotEmpty() && email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty())
            {
                if (pass.equals(confirmPass))
                {

                    firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener{
                        if(it.isSuccessful)
                        {
                            val intent = Intent(this,SignInActivity::class.java)

                            // upload to db
                            //getting connection


                            try {


                                var con = UploadImage.conclass()
                                var stmt = con.createStatement()
                                var query =
                                    "insert into accounts(username,email_id,password) values('$username','$email','$pass');"
                                var rs=stmt.executeQuery(query)
                            }
                            catch(e:SQLException)
                            {
                                //Toast.makeText(this,e.message, Toast.LENGTH_SHORT).show()
                                println(e.message)
                            }


                            startActivity(intent)
                        }
                        else
                        {
                            Toast.makeText(this,it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else
                {
                    Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this, "Please fill empty fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}