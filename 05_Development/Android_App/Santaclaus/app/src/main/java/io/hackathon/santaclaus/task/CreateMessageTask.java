package io.hackathon.santaclaus.task;

import android.os.AsyncTask;

import io.hackathon.santaclaus.util.Constants;
import io.hackathon.santaclaus.util.Utils;

/**
 * Created by trinhnt on 2016/12/11.
 */

public class CreateMessageTask extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... uri) {
        String result_string = Utils.makePOSTRequest(Constants.CREATE_MESSAGE_URL, uri[0]);
        return result_string.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        //Do anything with response..
    }
}
