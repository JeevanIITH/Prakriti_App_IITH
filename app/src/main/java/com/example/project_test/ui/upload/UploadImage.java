package com.example.project_test.ui.upload;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import kotlin.Suppress;

public  class UploadImage {
    static Connection con;
    @SuppressLint("NewApi")
    UploadImage(){con=null;}
    public static Connection conclass() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String conUrl=null;
        try {
            //Class.forName("net.sourceforge.jtds.jdbc.Driver");
            conUrl="jdbc:postgresql://iith-prakrithi.postgres.database.azure.com:5432/Images?user=Jeevan&password=Nodens@123&sslmode=require";
            connection= DriverManager.getConnection(conUrl);
        } catch (Exception e)
        {
            Log.e("Error",e.getMessage());
        }
        return connection;


    }

    public static int SendImage(Connection conn , String query) throws SQLException {
        int flag=0;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs;
            rs =stmt.executeQuery(query);
        }
        catch (SQLException e)
        {
            System.err.println(e.getMessage());
            flag=1;
        }
        return flag;

    }

}
