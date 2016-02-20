package com.example.me.sunshineapplication;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsActivityFragment extends Fragment {

    public DetailsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root=inflater.inflate(R.layout.fragment_details, container, false);

        TextView tv=(TextView) root.findViewById(R.id.textView);
        String ForecastData=null;

        Bundle extrasFromIntent=getActivity().getIntent().getExtras();

        if(extrasFromIntent!=null)
        {

            ForecastData=extrasFromIntent.getString("ForecastData");
            tv.setText(ForecastData);

        }

        return root;
    }
}
