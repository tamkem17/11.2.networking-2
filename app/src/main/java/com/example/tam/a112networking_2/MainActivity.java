package com.example.tam.a112networking_2;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
    private EditText mEdt_url;
    private TextView mTxt_result;
    private int numberLine = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEdt_url = (EditText) findViewById(R.id.edt_url);
        mTxt_result = (TextView) findViewById(R.id.txt_result);
        Button btn_numberLine = (Button) findViewById(R.id.btn_numberLine);
        Button btn_numberCharacter = (Button) findViewById(R.id.btn_numberCharacter);
        btn_numberLine.setOnClickListener(new ClickNumberLine());
        btn_numberCharacter.setOnClickListener(new ClickNumberCharacter());
    }

    private class ClickNumberLine implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String url = mEdt_url.getText().toString();
            new MyDownloadTask().execute(url);
        }
    }

    private class ClickNumberCharacter implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            String url = mEdt_url.getText().toString();
            new numberCharacter().execute(url);
        }
    }

    class MyDownloadTask extends AsyncTask<String,Void,Integer>
    {
        protected Integer doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line;
                int numberLine = 0;
                while ((line = in.readLine()) != null) {
                    numberLine ++;
                }
                return numberLine;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            mTxt_result.setText("The number of line in page at the URL is : "+String.valueOf(integer));
        }
    }

    class numberCharacter extends AsyncTask<String, Void, Integer>{

        @Override
        protected Integer doInBackground(String... params) {
            String urlBody = null;
            try {
                urlBody = HttpUtils.urlContent(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String[] lines = urlBody.split("[\\n\\r]+");
            StringBuilder matches = new StringBuilder("");
            for (String line : lines){
                matches.append(line);
            }
            int lineNum;
            lineNum = matches.length();
            return lineNum;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            mTxt_result.setText(" the number of characters in the page at that URL: "+String.valueOf(integer));
        }
    }
}