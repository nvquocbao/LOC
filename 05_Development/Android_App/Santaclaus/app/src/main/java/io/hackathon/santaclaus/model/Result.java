package io.hackathon.santaclaus.model;

/**
 * Created by trinhnt on 2016/12/10.
 */

public class Result {

    private int resultCode;

    private Object returnObject;

    // Avatar path from PHP server
    private String url;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public Object getReturnObject() {
        return returnObject;
    }

    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
