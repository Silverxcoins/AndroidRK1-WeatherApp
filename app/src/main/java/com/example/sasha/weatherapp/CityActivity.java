package com.example.sasha.weatherapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import ru.mail.weather.lib.City;
import ru.mail.weather.lib.WeatherStorage;

public class CityActivity extends AppCompatActivity {
    private final View.OnClickListener onBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            City currentCity = City.valueOf(((Button)view).getText().toString());
            WeatherStorage.getInstance(CityActivity.this).setCurrentCity(currentCity);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        findViewById(R.id.btn_vice_city).setOnClickListener(onBtnClick);
        findViewById(R.id.btn_raccoon_city).setOnClickListener(onBtnClick);
        findViewById(R.id.btn_springfield).setOnClickListener(onBtnClick);
        findViewById(R.id.btn_silent_hill).setOnClickListener(onBtnClick);
        findViewById(R.id.btn_south_park).setOnClickListener(onBtnClick);
    }
}
