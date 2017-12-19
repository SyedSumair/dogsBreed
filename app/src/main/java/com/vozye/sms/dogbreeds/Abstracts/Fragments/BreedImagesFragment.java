package com.vozye.sms.dogbreeds.Abstracts.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.vozye.sms.dogbreeds.Abstracts.onFragmentChangeCallback;
import com.vozye.sms.dogbreeds.Adapters.ImageAdapter;
import com.vozye.sms.dogbreeds.R;
import com.vozye.sms.dogbreeds.Utilities.JsonParser;
import com.vozye.sms.dogbreeds.Utilities.RestClientTask;

import org.apache.http.client.methods.HttpGet;

import java.net.URI;
import java.util.List;

/**
 * Created by sumair on 12/17/2017.
 */

public class BreedImagesFragment extends BaseFragment {


    onFragmentChangeCallback fragmentChangeCallback;

    private static final String BREEDS_IMAGES_API = "http://dog.ceo/api/breed/";

    private static final String BREEDS_IMAGES_LIST_INTENT_CALLBACK= "BREEDS_IMAGES_LIST_INTENT_CALLBACK";

    List<String> breedsImagesList = null;

    String breedName = "";
    GridView gvImages;
    ImageAdapter adapter;

    public static BreedImagesFragment newInstance(String breedName) {

        Bundle args = new Bundle();
        args.putString("breedName", breedName);
        BreedImagesFragment fragment = new BreedImagesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_breeds_images, null);
        breedName = getArguments().getString("breedName");

        gvImages = (GridView) view.findViewById(R.id.gvImages);
        if (fragmentChangeCallback != null)
            fragmentChangeCallback.onFragmentChange(breedName);
        fetchDogsBreedImagesFromServer();
        return view;
    }

    private void fetchDogsBreedImagesFromServer() {
        showLoader();

        try
        {
            HttpGet httpGet = new HttpGet(new URI(BREEDS_IMAGES_API + breedName +"/images"));
            RestClientTask task = new RestClientTask(getActivity(), BREEDS_IMAGES_LIST_INTENT_CALLBACK);
            task.execute(httpGet);
        }
        catch (Exception e)
        {
            Log.e("BreedImagesFragment", e.getMessage());
            hideLoader();
        }
        hideLoader();

    }

    private BroadcastReceiver receiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            String response = intent.getStringExtra(RestClientTask.HTTP_RESPONSE);
            JsonParser jsonParser = new JsonParser();
            breedsImagesList = jsonParser.parseResponse(response);
            Log.i("Response from service", "Breed Images RESPONSE = " + response);
            hideLoader();
            if(breedsImagesList != null) {
                adapter = new ImageAdapter(getActivity(),breedsImagesList);
                gvImages.setAdapter(adapter);
            }
            else{
                Toast.makeText(getActivity(),"No Data Found",Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    public void onResume()
    {
        super.onResume();
        getActivity().registerReceiver(receiver, new IntentFilter(BREEDS_IMAGES_LIST_INTENT_CALLBACK));
    }

    @Override
    public void onPause()
    {
        super.onPause();
        getActivity().unregisterReceiver(receiver);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            fragmentChangeCallback = (onFragmentChangeCallback) getActivity();
        } catch (Exception e) {
            //e.printStackTrace();
        }

    }
}
