package com.example.boshen.ghost_gear;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Boshen on 4/23/2016.
 */
public class server extends Activity {

    String url_post = "http://boshencui.com/postTrap.php";
    String url_get ="http://boshencui.com/getTrap.php";
    String url_getall = "http://boshencui.com/getAllTraps.php";
    String user, Id, latitute, longitute;
    String [] ids;
    double [] lats;
    double [] longs;
    boolean [] publics;
    String [] times;
    int arrlen;
    int ispublic;

    public void logTrap(String id, double lat, double longit, boolean ispub){
        Id=id;
        latitute=Double.toString(lat);
        longitute=Double.toString(longit);

        if(ispub)
            ispublic = 1;
        else
            ispublic = 0;

        postTrap pt = new postTrap();
        pt.execute();
    }

    public void getAllTraps(){

        getAllTraps gat = new getAllTraps();
        try{
            gat.execute().get();
        }
        catch(Exception e){
            Log.d("trap", e.getMessage());
        }
    }

    public void getMyTraps(String id){
        user=id;

        getMyTraps gmt = new getMyTraps();
        try{
            gmt.execute().get();
        }
        catch(Exception e){
            Log.d("trap", e.getMessage());
        }
    }

    class postTrap extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... args){
            try {
                URL url = new URL(url_post);

                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();

                urlConnection.setDoOutput(true);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));

                Log.d("posttrap async", ispublic+"");

                writer.write("user="+user+"&id="+Id+"&lat="+latitute+"&long="+longitute+"&ispub="+ispublic);
                writer.flush();
                writer.close();
                os.close();

                InputStream is = urlConnection.getInputStream();
                BufferedReader r = new BufferedReader(new InputStreamReader(is));
                StringBuilder total = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    total.append(line);
                }

                line = total.toString();

                Log.d("posttrap", line);

                return line;
            }
            catch(Exception e){
                Log.d("posttrap err", e.getMessage());
            }

            return null;
        }

        protected void onPostExecute(String s) {

            try {
                JSONObject result = new JSONObject(s);

                if(result.getInt("success") == 1) {

                    Log.d("posttrap", "success!!");
                }
                else {

                    Log.d("posttrap", "not posted!");
                }
            }
            catch(Exception e){
                Log.d("posttrap err1", e.getMessage());
            }


        }
    }

    class getAllTraps extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... args){
            try {
                URL url = new URL(url_getall);

                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();

                urlConnection.setDoOutput(true);

                InputStream is = urlConnection.getInputStream();
                BufferedReader r = new BufferedReader(new InputStreamReader(is));
                StringBuilder total = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    total.append(line);
                }

                line = total.toString();

                Log.d("posttrap", line);


                Log.d("trap", "duringexecute");
                try {
                    JSONArray result = new JSONArray(line);
                    arrlen = result.length();

                    lats = new double[arrlen];
                    longs = new double[arrlen];
                    times = new String[arrlen];


                    Log.d("trap", "arrlength" + arrlen);
                    for(int i=0; i< result.length(); i++) {

                        lats[i]= result.getJSONArray(i).getDouble(0);
                        longs[i]= result.getJSONArray(i).getDouble(1);
                        times[i]= result.getJSONArray(i).getString(2);

                    }

                }
                catch(Exception e){
                    Log.d("posttrap err1", e.getMessage());
                }

                return line;
            }
            catch(Exception e){
                Log.d("posttrap err", e.getMessage());
            }

            return null;
        }
    }

    class getMyTraps extends AsyncTask<String, Void, String>{
        protected String doInBackground(String... args){
            try {
                URL url = new URL(url_get);

                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();

                urlConnection.setDoOutput(true);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));

                Log.d("get my trap async", ispublic+"");

                writer.write("user="+user);
                writer.flush();
                writer.close();
                os.close();

                InputStream is = urlConnection.getInputStream();
                BufferedReader r = new BufferedReader(new InputStreamReader(is));
                StringBuilder total = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    total.append(line);
                }

                line = total.toString();

                Log.d("posttrap", line);

                return line;
            }
            catch(Exception e){
                Log.d("posttrap err", e.getMessage());
            }

            return null;
        }

        protected void onPostExecute(String s) {

            try {
                JSONArray result = new JSONArray(s);
                arrlen = result.length();

                lats = new double[arrlen];
                longs = new double[arrlen];
                times = new String[arrlen];

                for(int i=0; i< result.length(); i++) {
                    lats[i]= result.getJSONArray(i).getDouble(0);
                    longs[i]= result.getJSONArray(i).getDouble(1);
                    times[i]= result.getJSONArray(i).getString(2);
                }

            }
            catch(Exception e){
                Log.d("posttrap err1", e.getMessage());
            }


        }
    }
}