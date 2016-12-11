package io.hackathon.santaclaus.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
     * Get json data from server
     *
     * @param url
     * @return
     */
    public static String makeGETRequest(String url) {
        String response = "";

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            httpGet.getParams().setParameter("http.protocol.content-charset", "UTF-8");
//            httpGet.getParams().setParameter("parentId", parentId);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            response = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
//            HttpEntity httpEntity = httpResponse.getEntity();
//            response = EntityUtils.toString(httpEntity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
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
            response = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
//            HttpEntity httpEntity = httpResponse.getEntity();
//            response = EntityUtils.toString(httpEntity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static String cryptWithMD5(String pass){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] passBytes = pass.getBytes();
            md.reset();
            byte[] digested = md.digest(passBytes);
            StringBuffer sb = new StringBuffer();
            for(int i=0;i<digested.length;i++){
                sb.append(Integer.toHexString(0xff & digested[i]));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
        }
        return null;
    }
}
