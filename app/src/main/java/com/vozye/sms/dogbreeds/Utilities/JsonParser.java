package com.vozye.sms.dogbreeds.Utilities;

import com.google.gson.Gson;
import com.vozye.sms.dogbreeds.Models.BreedsListModel;

import java.util.ArrayList;
import java.util.List;
import org.json.*;
/**
 * Created by sumair on 12/16/2017.
 */

public class JsonParser {

    List<String> responseList;
    public List<String> parseResponse(String response) {
        try {
            BreedsListModel ent = new Gson().fromJson(response, BreedsListModel.class);

            if(ent.getStatus().equals("success")) {
                responseList = ent.getMessage();
            }
            else{
                return null;
            }


        } catch (Exception e){
            e.printStackTrace();
        }
        return responseList;
    }
}
