package com.vozye.sms.dogbreeds.Abstracts.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vozye.sms.dogbreeds.Abstracts.onFragmentChangeCallback;
import com.vozye.sms.dogbreeds.Adapters.BreedListingAdapter;
import com.vozye.sms.dogbreeds.DogsMainActivity;
import com.vozye.sms.dogbreeds.R;
import com.vozye.sms.dogbreeds.Utilities.RestClientTask;
import com.vozye.sms.dogbreeds.Utilities.JsonParser;
import com.vozye.sms.dogbreeds.Utilities.recyclerviewenhanced.SimpleDividerItemDecoration;
import com.vozye.sms.dogbreeds.Utilities.recyclerviewenhanced.RecyclerTouchListener;

import org.apache.http.client.methods.HttpGet;

import java.net.URI;
import java.util.List;

/**
 * Created by sumair on 12/16/2017.
 */

public class BreedsListingFragment extends BaseFragment {


    onFragmentChangeCallback fragmentChangeCallback;

    private static final String BREEDS_API = "http://dog.ceo/api/breeds/list";
    private static final String ACTION_FOR_INTENT_CALLBACK = "BREEDS_LIST_INTENT_CALLBACK";

    RecyclerView rvBreeds;
    private RecyclerTouchListener onTouchListener;
    List<String> breedsList = null;
    BreedListingAdapter adapter = null;

    String selectedBreed = "";

    public static BreedsListingFragment newInstance() {

        Bundle args = new Bundle();
        BreedsListingFragment fragment = new BreedsListingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_breeds_listing, null);
        rvBreeds = (RecyclerView) view.findViewById(R.id.rvBreeds);
        onTouchListener = new RecyclerTouchListener(getActivity(), rvBreeds);
        rvBreeds.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvBreeds.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        rvBreeds.addOnItemTouchListener(onTouchListener);


        if (fragmentChangeCallback != null)
            fragmentChangeCallback.onFragmentChange("Dog Breeds");


        setRecyclerViewListener();
        fetchDogsBreedFromServer();


        return view;
    }
    private void setRecyclerViewListener() {

        onTouchListener.setClickable(new RecyclerTouchListener.OnRowClickListener() {
                    @Override
                    public void onRowClicked(int position) {
                        selectedBreed = breedsList.get(position);
                        ((DogsMainActivity) getActivity()).addDockableFragment(BreedImagesFragment.newInstance(selectedBreed));

                    }

                    @Override
                    public void onIndependentViewClicked(int independentViewID, int position) {

                    }
                });


        rvBreeds.addOnItemTouchListener(onTouchListener);
    }
    private void fetchDogsBreedFromServer() {
        showLoader();

        try
        {
            HttpGet httpGet = new HttpGet(new URI(BREEDS_API));
            RestClientTask task = new RestClientTask(getActivity(), ACTION_FOR_INTENT_CALLBACK);
            task.execute(httpGet);
        }
        catch (Exception e)
        {
            Log.e("BreedListingFragment", e.getMessage());
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
            breedsList = jsonParser.parseResponse(response);
            Log.i("Response from service", "Breed List RESPONSE = " + response);
            hideLoader();
            if(breedsList != null) {
                adapter = new BreedListingAdapter(getActivity(), breedsList);
                rvBreeds.setAdapter(adapter);
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
        getActivity().registerReceiver(receiver, new IntentFilter(ACTION_FOR_INTENT_CALLBACK));
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
