package io.hackathon.santaclaus.util;

import android.util.Base64;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by trinhnt on 2016/12/10.
 */

public class Utils {

    /**
     * Get Integer from String
     *
     * @param value
     * @return
     */
    public static Integer getIntegerValue(String value) {
        return null != value ? Integer.valueOf(value) : null;
    }

    /**
     * Get String from Integer
     *
     * @param value
     * @return
     */
    public static String getStringValue(Integer value) {
        return null != value ? String.valueOf(value) : null;
    }

    /**
     * Post json data to server
     *
     * @param url
     * @param json
     * @return
     */
    public static String makePOSTRequest(String url, String json) {
        String response = "";

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            // 6. set httpPost Entity
            httpPost.setEntity(se);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            response = EntityUtils.toString(httpEntity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static String encrypt(String value) throws Exception {
        return Base64.encodeToString(value.getBytes(), Base64.DEFAULT);
    }

    public static String decrypt(String value) throws Exception {
        return new String(Base64.decode(value, Base64.DEFAULT));
    }
}
