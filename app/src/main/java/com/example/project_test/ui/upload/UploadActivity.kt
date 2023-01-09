package com.example.project_test.ui.upload

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.project_test.MyApplication
import com.example.project_test.R
import com.example.project_test.databinding.ActivityUploadBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class UploadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadBinding
    lateinit var G:Uri
    private lateinit var storageref : StorageReference
    private lateinit var firebaseFirestore : FirebaseFirestore
    private lateinit var ImageName:String
    private lateinit var ImageLocation:String
    private lateinit var Imagedate:String
    private lateinit var Username:String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        binding=ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAll()

        binding.buttonSelectImage.setOnClickListener {
            SelectImage()
        }

        binding.buttonUploadImageUploadPage.setOnClickListener {
            ImageName=binding.InputName.text.toString()
            Imagedate=binding.ImageDate.text.toString()
            ImageLocation=binding.InputImageLocation.text.toString()
            doUpload()
        }



    }
    private fun initAll()
    {
        storageref = FirebaseStorage.getInstance().reference.child("All_Images")
        firebaseFirestore=FirebaseFirestore.getInstance()
        Username=MyApplication.Username
    }

    public fun  SelectImage()
    {
        ImagePicker.with(this).start()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode== Activity.RESULT_OK)
        {
            val uri:Uri=data?.data!!
            binding.imageView2.setImageURI(uri)
            G=uri
        }
    }
    public fun doUpload()
    {
        var UploadObject=UploadImage()
        var con=UploadImage.conclass()
        val inputData = contentResolver.openInputStream(G)?.readBytes()
        storageref=storageref.child(ImageName)
        storageref.putFile(G).addOnCompleteListener {
            if (it.isSuccessful)
            {
                storageref.downloadUrl.addOnCompleteListener { res1->
                    var url_string=res1.result.toString()
                    var statm="insert into all_images(username,image_url,image_name,image_location,image_date) values( '$Username','"+ url_string.toString() + "','$ImageName','$ImageLocation','$Imagedate') ;"
                    var query_execution=UploadImage.SendImage(con,statm)
                    //query_execution=UploadImage.SendImage(con,statm)
                    Toast.makeText(this, "Uploaded ", Toast.LENGTH_SHORT).show()

                }
            }
            else
            {
                Toast.makeText(this, "Failed to upload", Toast.LENGTH_SHORT).show()
            }

        }
      

    }

}