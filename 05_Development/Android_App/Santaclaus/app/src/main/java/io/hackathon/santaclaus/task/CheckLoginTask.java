package io.hackathon.santaclaus.task;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import io.hackathon.santaclaus.model.Result;
import io.hackathon.santaclaus.util.Constants;
import io.hackathon.santaclaus.util.Utils;

/**
 * Created by trinhnt on 2016/12/11.
 */

public class CheckLoginTask extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... uri) {
        Gson gson = new Gson();
        String result_string = Utils.makePOSTRequest(Constants.CHECK_LOGIN_URL, uri[0]);
        Type resultType = new TypeToken<Result>() {}.getType();
        Result result = gson.fromJson(result_string, resultType);
        if (null == result.getReturnObject()) {
            return null;
        } else {
            LinkedTreeMap<String, String> yourMap = (LinkedTreeMap<String, String>) result.getReturnObject();
            JsonObject jsonObject = gson.toJsonTree(yourMap).getAsJsonObject();
            return jsonObject.toString();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        //Do anything with response..
    }
}
