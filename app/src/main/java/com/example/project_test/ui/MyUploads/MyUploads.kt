package com.example.project_test.ui.MyUploads

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project_test.MyApplication
import com.example.project_test.R
import com.example.project_test.databinding.FragmentHomeBinding
import com.example.project_test.databinding.FragmentMyUploadsBinding
import com.example.project_test.ui.Image_data
import com.example.project_test.ui.MyAdapter
import com.example.project_test.ui.upload.UploadImage
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.sql.Connection
import java.sql.ResultSet
import java.sql.Statement


class MyUploads : Fragment() {

    //private var _binding= FragmentMyUploadsBinding? = null

    private lateinit var adapter : MyAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var image_data_list : ArrayList<Image_data>
    private lateinit var storageReference: StorageReference
    private lateinit var ImageName: String
    private lateinit var ImageLocation: String

    companion object {
        fun newInstance() = MyUploads()

    }

    private lateinit var viewModel: MyUploadsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var bin= FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = bin.root
        image_data_list=ArrayList<Image_data>(10)

        //return root
        return inflater.inflate(R.layout.fragment_my_uploads, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyUploadsViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        load_data()
        val layoutManager= LinearLayoutManager(context)
        recyclerView=view.findViewById(R.id.MyUploadRecycleView)
        recyclerView.layoutManager=layoutManager
        recyclerView.setHasFixedSize(true)
        adapter= MyAdapter(image_data_list)
        recyclerView.adapter=adapter
    }
    var cnt=0
    var username=MyApplication.Username
    private fun load_data()
    {
        var con=UploadImage.conclass()
        var stmt="select image_url , image_name , image_location  from all_images where username='$username';"
        var statement=con.createStatement()
        var rs=statement.executeQuery(stmt)

        while (rs.next()){
            var s1=rs.getString("image_url")
            ImageName=rs.getString("image_name")
            ImageLocation=rs.getString("image_location")
            DownloadImageFromUrl(s1)
        }
        return
        //image_data_list.add(Image_data(u,'label1'))
    }


    private fun DownloadImageFromUrl(url_string:String)
    {
        var response_code=-1
        try {
            var url=URL(url_string)
            var con=url.openConnection() as HttpURLConnection
            con.doInput
            con.connect()
            response_code=con.responseCode
            //var in:InputStream?=null
            if (response_code==HttpURLConnection.HTTP_OK)
            {
                var inp = con.inputStream
                var bmp=BitmapFactory.decodeStream(inp)
                inp.close()
                image_data_list.add(cnt,Image_data(bmp,ImageName,ImageLocation))
                cnt=cnt+1
            }
        }
        catch (e:Exception)
        {
            Toast.makeText(context, "Unable to download image"+ e.message, Toast.LENGTH_SHORT).show()
        }


    }

}