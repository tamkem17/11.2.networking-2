package com.example.tam.a112networking_2;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

/**
 * Created by tam on 7/25/2017.
 */

public class HttpUtils {
    public static String urlContent (String address) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(address);
        ResponseHandler<String> handler = new BasicResponseHandler();
        return client.execute(get, handler);
    }
}
