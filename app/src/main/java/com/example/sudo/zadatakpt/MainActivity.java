package com.example.sudo.zadatakpt;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.sudo.zadatakpt.adapter.NewsAdapter;
import com.example.sudo.zadatakpt.helpers.AppConstants;
import com.example.sudo.zadatakpt.helpers.DBHelper;
import com.example.sudo.zadatakpt.helpers.Popup;
import com.example.sudo.zadatakpt.models.News;
import com.example.sudo.zadatakpt.models.NewsResponse;
import com.example.sudo.zadatakpt.rest.ApiClient;
import com.example.sudo.zadatakpt.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity implements Callback<NewsResponse> {

    private Long responseTime;
    private ListView lvList;
    private DBHelper dbHelper;
    private List<News> newsList;
    private SharedPreferences sharedPreferences;
    private Handler handler;
    private ProgressBar pbLoad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUI();

        sharedPreferences = getSharedPreferences(AppConstants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        responseTime = sharedPreferences.getLong(AppConstants.KEY_RESPONSE_TIME, 0);
        dbHelper = DBHelper.getInstance(this);
        newsList = dbHelper.getAllNews();

        handler = new Handler();
        handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //Log.e("Are u working", "Ja");
                                    if (System.currentTimeMillis() - responseTime > 300000) { //5 min * 60 s * 1000 ms
                                        findNews();
                                    }
                                    handler.postDelayed(this, 1000);
                                }
                            }
                , 1000);

        if (newsList == null && newsList.size() == 0) {
            Log.e("sasa", "jaja");
            findNews();
        } else {
            Log.e("sasa", "xaxaxa");
            showList();
        }

    }

    private void showList() {

        newsList = dbHelper.getAllNews();
        NewsAdapter adapter = new NewsAdapter(newsList);
        this.lvList.setAdapter(adapter);
        this.lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent readIntent = new Intent(MainActivity.this, Read.class);
                readIntent.putExtra(AppConstants.NEWS_ID, newsList.get(position).getId());
//                Log.e("sasa", String.valueOf(newsList.get(position).getId()));
                MainActivity.this.startActivity(readIntent);
            }
        });
    }

    private void findNews() {
        pbLoad.setVisibility(View.VISIBLE);
        Log.e("sasa", "findNews");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<NewsResponse> call = apiService.getNews(ApiClient.API, "top", "bbc-news");
        call.enqueue(this);

    }


    private void setUI() {
        lvList = (ListView) findViewById(R.id.lvList);
        pbLoad = (ProgressBar) findViewById(R.id.pbLoad);
    }


    @Override
    public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
        Log.e("Response", String.valueOf(response.errorBody()));
        if (response.body() != null) {
            List<News> responseList = response.body().getNews();
            Log.e("Response if", "ušo u if ");
            dbHelper.deleteNews();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Long currentResponseTime = System.currentTimeMillis();
            editor.putLong(AppConstants.KEY_RESPONSE_TIME, currentResponseTime);
            responseTime = currentResponseTime;
            for (int i = 0; i < responseList.size() - 1; i++) {

                News item = responseList.get(i);
                dbHelper.addNews(item);
            }
            pbLoad.setVisibility(View.GONE);
            showList();
        } else {
            Log.e("else", "response");
            Popup popup = new Popup();
            popup.show(getFragmentManager(), "tag");
            responseTime = System.currentTimeMillis();
        }
    }

    @Override
    public void onFailure(Call<NewsResponse> call, Throwable t) {
//        Log.d("Fail", t.getMessage());
        Popup popup = new Popup();
        popup.show(getFragmentManager(), "tag");
        responseTime = System.currentTimeMillis();
        pbLoad.setVisibility(View.GONE);
    }

}