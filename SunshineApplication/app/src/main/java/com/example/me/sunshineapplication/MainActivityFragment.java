package com.example.me.sunshineapplication;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {


    View rootView=null;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView=inflater.inflate(R.layout.fragment_main, container, false);

        setHasOptionsMenu(true);


        return rootView;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.action_refresh,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_refresh_label)
            return true;
        else
            return false;
    }

    @Override
    public void onResume() {
        super.onResume();


        if(rootView!=null)
        {


            try {
                ListView lv = (ListView) rootView.findViewById(R.id.listView);

                List<String> dataList = new ArrayList<String>();

                dataList.add("One data");
                dataList.add("Two data");
                dataList.add("Three data");
                dataList.add("Big data");

                ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_layout, R.id.list_text_view,dataList);
                lv.setAdapter(mAdapter);





                String Source_URL="http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7";
                String API_Key="&APPID=b45c4a4178ceaa8cfbf6a36b2156cf34";

                new NetworkTask().execute(Source_URL.concat(API_Key));

            }
            catch (Exception ex)
            {


                Log.d("Array Adapter", ex.getMessage().toString());
            }
        }
        else
        {


            Log.d("Null Rootview", "rootview is null");
        }
    }
    // async tasks allows code to run in background
    class NetworkTask extends AsyncTask<String, Void, String>
    {
        String finalData=null;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();



        }

        @Override
        protected String doInBackground(String... params) {

            try {

                URL requestUrl = new URL(params[0]);  // set link
                HttpURLConnection connection = (HttpURLConnection)requestUrl.openConnection(); // create connection
                connection.setRequestMethod("GET");  // set HTTP Method type
                connection.connect();  // connect to server

                int responseCode = connection.getResponseCode(); // get response code from server

                if (responseCode == HttpURLConnection.HTTP_OK) { // check if server says "Ok! i will respond to your request"

                    finalData="";
                    BufferedReader reader = null; //Get buffer ready

                    InputStream inputStream = connection.getInputStream(); // get input stream from server ready

                    if (inputStream == null) { // if there is nothing in stream
                        return "";
                    }
                    reader = new BufferedReader(new InputStreamReader(inputStream));  // else pass stream data to buffer

                    String line;
                    while ((line = reader.readLine()) != null) {  // read each line

                        finalData+="\n"+line; // save them to string
                    }

                    if (finalData.length() == 0) { // check if string is empty
                        return "";
                    }


                }
                else {
                    Log.i("Unsuccessful", "Unsuccessful HTTP Response Code: " + responseCode);
                }
            } catch (MalformedURLException e) {
                Log.e("Wrong URL", "Error processing API URL", e);
            } catch (IOException e) {
                Log.e("Error", "Error connecting to API", e);
            }

            return finalData;


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            if(s!=null)
            {

                Log.d("Result is",s); // show current data in logcat

            }
            else
            {
                Log.d("Result ","is null....") ;


            }
        }
    }


}
