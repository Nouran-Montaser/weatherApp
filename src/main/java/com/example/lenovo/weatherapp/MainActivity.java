package com.example.lenovo.weatherapp;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.lenovo.weatherapp.Pojo.Current;
import com.example.lenovo.weatherapp.Pojo.Day;
import com.example.lenovo.weatherapp.Pojo.Forecastday;
import com.example.lenovo.weatherapp.Pojo.Location;
import com.example.lenovo.weatherapp.Pojo.MyPojo;
import com.example.lenovo.weatherapp.Pojo.Condition;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity  {

    private static final int SETTINGS_RESULT = 1;

    private TextView dayTxt;
    private TextView timeTxt;
    private TextView conditionTxt;
    private TextView tempTxt;
    private TextView toolbarText;
    private ImageView icon;
    private Toolbar mToolbar;
    private ImageButton mLocationBtn;
    private Button b;
    private LinearLayoutManager layoutManager;
    private ArrayList<Forecastday> details;
    private MyAdapter myAdapter;
    private RecyclerView recyclerView;
    private TextView location_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intialize();

        mLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), UserSettingActivity.class);
                startActivityForResult(i, SETTINGS_RESULT);
            }
        });

        displayUserSettings();

        display();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==SETTINGS_RESULT)
        {
            displayUserSettings();
        }
    }

    private void displayUserSettings()
    {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String  settings = "";
        settings = sharedPrefs.getString("prefUserLocation", "egypt");
//        toolbarText.setText(settings);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.apixu.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //Creating an object of our api interface
        Client api = retrofit.create(Client.class);
        Call<MyPojo> call = api.addlocation(settings);
        call.enqueue(new Callback<MyPojo>() {
            @Override
            public void onResponse(Call<MyPojo> call, Response<MyPojo> response) {
                try {
                    us(response);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<MyPojo> call, Throwable t) {
            }
        });
    }

    public void us(Response<MyPojo> response) throws ParseException {

        Location la = response.body().getLocation();
        String name = la.getName();
        String localTime = la.getLocaltime();
        String[] datetime = localTime.split(" ");


        SimpleDateFormat inFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date datee = inFormat.parse(datetime[0]);
        SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
        String goal = outFormat.format(datee);
        dayTxt.setText(goal);
        timeTxt.setText(datetime[1]);

        Current c = response.body().getCurrent();
        String temp = c.getTemp_c();
        tempTxt.setText(temp);

        Condition cond = c.getCondition();
        String text = cond.getText();
        conditionTxt.setText(text);

        String img = cond.getIcon();
        Picasso.get().load("http:"+img).into(icon);

        toolbarText.setText(name);
        location_txt.setText(name);

        details.clear();
        for (int i=0;i<response.body().getForecast().getForecastday().length ; i++)
        {

            String date = response.body().getForecast().getForecastday()[i].getDate();

            SimpleDateFormat inFormat2 = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
            Date date2 = inFormat.parse(date);

            Calendar c2 = Calendar.getInstance();
            c2.setTime(date2); // yourdate is an object of type Date
          //  int dayOfWeek = c2.get(Calendar.DAY_OF_WEEK); // this will for example return 3 for tuesday
            String dayOfWeek = c2.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US).toUpperCase();
            String daycls = response.body().getForecast().getForecastday()[i].getDay().getMaxtemp_c();


            String dayIcon = response.body().getForecast().getForecastday()[i].getDay().getCondition().getIcon();
            details.add(new Forecastday(new Day(new Condition(dayIcon),daycls),dayOfWeek));
        }
        myAdapter = new MyAdapter(MainActivity.this,details);
        recyclerView.setAdapter(myAdapter);


    }

    public void display()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.apixu.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //Creating an object of our api interface
        Client api = retrofit.create(Client.class);
        Call<MyPojo> call = api.getRes();
        call.enqueue(new Callback<MyPojo>() {
            @Override
            public void onResponse(Call<MyPojo> call, Response<MyPojo> response) {

                try {
                    us(response);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

//                for (int i=0;i<response.body().getForecast().getForecastday().length ; i++)
//                {
//                    String date = response.body().getForecast().getForecastday()[i].getDate();
//                    String daycls = response.body().getForecast().getForecastday()[i].getDay().getMaxtemp_c();
//                    String dayIcon = response.body().getForecast().getForecastday()[i].getDay().getCondition().getIcon();
//                    details.add(new Forecastday(new Day(new Condition(dayIcon),daycls),date));
//                }
//                myAdapter = new MyAdapter(MainActivity.this,details);
//                recyclerView.setAdapter(myAdapter);

            }
            @Override
            public void onFailure(Call<MyPojo> call, Throwable t) {
            }
        });
    }

    public void intialize()
    {
        dayTxt = findViewById(R.id.single_dayW_txt);
        timeTxt = findViewById(R.id.single_timeW_txt);
        conditionTxt = findViewById(R.id.single_conditionTextW_txt);
        tempTxt = findViewById(R.id.single_celW_txt);
        icon = findViewById(R.id.single_statusW_image);
        location_txt = findViewById(R.id.location_text);


        mToolbar = findViewById(R.id.main_page_toobar);
        setSupportActionBar(mToolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();



        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View actionBarView = layoutInflater.inflate(R.layout.custom_bar, null);
        actionBar.setCustomView(actionBarView);

        toolbarText = findViewById(R.id.tool_bar_text);

        mLocationBtn = findViewById(R.id.ChatAddButton);

        recyclerView = findViewById(R.id.days_recycler_view);
        details = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());

        recyclerView.addItemDecoration(dividerItemDecoration);

    }
}

