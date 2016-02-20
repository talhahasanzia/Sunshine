package com.example.me.sunshineapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    ArrayAdapter<String> mAdapter;
    String ZipCode="94043";
    String forecast=null;
    String Source_URL="http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7";
    String API_Key="b45c4a4178ceaa8cfbf6a36b2156cf34";

    View rootView=null;


   private TextWatcher tw=new TextWatcher() {
       @Override
       public void beforeTextChanged(CharSequence s, int start, int count, int after) {

       }

       @Override
       public void onTextChanged(CharSequence s, int start, int before, int count) {

       }

       @Override
       public void afterTextChanged(Editable s) {
            ZipCode=s.toString();
       }
   };


    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView=inflater.inflate(R.layout.fragment_main, container, false);

        setHasOptionsMenu(true);


        //Toolbar tb=(Toolbar)getActivity().findViewById(R.id.toolbar);

        TextView toolbarTextView  = (TextView) ((MainActivity) this.getActivity()).findViewById(R.id.toolbar);
        if( toolbarTextView==null)
            Log.d("Toolbar","Toolbar not found");
        else
            Log.d("Toolbar","Toolbar found");
        try {
            //EditText et = (EditText) tb.findViewById(R.id.myEditText);
        }
        catch (Exception ec)
        {


            Log.d("Edit Text"," New Edit Text not found",ec);
        }
      //    et.addTextChangedListener(tw);


        return rootView;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {


        inflater.inflate(R.menu.forecast_fragment,menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if(item.getItemId()==R.id.action_refresh) {
            new MainActivityFragment.NetworkTask().execute(ZipCode);
            return true;
        }
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



                Uri.Builder ub=new Uri.Builder();
                ub.scheme("http").authority("api.openweathermap.org").appendPath("data")
                        .appendPath("2.5")
                        .appendPath("forecast")
                        .appendPath("daily")
                        .appendQueryParameter("q",ZipCode)
                        .appendQueryParameter("mode","json")
                        .appendQueryParameter("units","metric")
                        .appendQueryParameter("cnt","7")
                        .appendQueryParameter("APPID", API_Key);

                List<String> dataList = new ArrayList<String>();

                dataList.add("Refresh to get data..");


                dataList.add(ub.toString());

               mAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_layout, R.id.list_text_view,dataList);
                lv.setAdapter(mAdapter);








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

                Uri.Builder ub=new Uri.Builder();
                ub.scheme("http").authority("api.openweathermap.org").appendPath("data")
                        .appendPath("2.5")
                        .appendPath("forecast")
                        .appendPath("daily")
                        .appendQueryParameter("q", params[0])
                        .appendQueryParameter("mode", "json")
                        .appendQueryParameter("units","metric")
                        .appendQueryParameter("cnt","7")
                        .appendQueryParameter("APPID",API_Key);

                URL requestUrl = new URL(ub.toString());  // set link
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
                Log.e("Wrong URL", "Error processing  API URL", e);
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
                try {
                    String[] data = WeatherData.getWeatherDataFromJson(s, 7);
                    List<String> dataList = new ArrayList<String>(Arrays.asList(data));
                    ListView lv = (ListView) rootView.findViewById(R.id.listView);
                    mAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_layout, R.id.list_text_view,dataList);
                    lv.setAdapter(mAdapter);

                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                            forecast=mAdapter.getItem(position);
                            Toast t=Toast.makeText(getContext(),"Launching...",Toast.LENGTH_SHORT);
                            t.show();

                            Intent i=new Intent(getActivity(),DetailsActivity.class);
                            i.putExtra("ForecastData", forecast);
                            startActivity(i);



                        }
                    });




                }
                catch (JSONException j_ex)
                {

                    Log.d("JOSN Exception",j_ex.getMessage().toString());
                }
                catch (Exception ex)
                {

                    Log.d("Unexpexted Error",ex.getMessage().toString());

                }

            }
            else
            {
                Log.d("Result ","is null....") ;


            }
        }
    }


}
