package com.example.sasha.weatherapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ru.mail.weather.lib.City;
import ru.mail.weather.lib.Weather;
import ru.mail.weather.lib.WeatherStorage;
import ru.mail.weather.lib.WeatherUtils;

public class MainActivity extends AppCompatActivity {
    public static final String ACTION_NEW_WEATHER = "action.NEW_WEATHER";
    private static boolean isUpdateInBgOn = false;

    private final View.OnClickListener onCityClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        final Intent intent = new Intent(MainActivity.this, CityActivity.class);
        startActivity(intent);
        }
    };

    private final View.OnClickListener onUpdateInBgOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setUpdateInBg(true);
        }
    };

    private final View.OnClickListener onUpdateInBgOffClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setUpdateInBg(false);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WeatherStorage.getInstance(this).setCurrentCity(City.VICE_CITY);

        findViewById(R.id.btn_city).setOnClickListener(onCityClick);
        findViewById(R.id.btn_update_in_bg_on).setOnClickListener(onUpdateInBgOnClick);
        findViewById(R.id.btn_update_in_bg_off).setOnClickListener(onUpdateInBgOffClick);

        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                printWeather();
            }
        };
        IntentFilter intentFilter = new IntentFilter(ACTION_NEW_WEATHER);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadWeatherOnce();
    }

    private void printWeather() {
        City currentCity = WeatherStorage.getInstance(this).getCurrentCity();
        Weather weather = WeatherStorage.getInstance(this).getLastSavedWeather(currentCity);
        String weatherText = weather.getTemperature() + " " + weather.getDescription();
        ((TextView) findViewById(R.id.text_weather)).setText(weatherText);
        ((Button) findViewById(R.id.btn_city)).setText(currentCity.name());
    }

    private void setUpdateInBg(boolean isUpdateInBgOn) {
        if (MainActivity.isUpdateInBgOn != isUpdateInBgOn) {
            MainActivity.isUpdateInBgOn = isUpdateInBgOn;
            WeatherUtils weatherUtils = WeatherUtils.getInstance();
            Intent intent = new Intent(MainActivity.this, WeatherIntentService.class);
            if (isUpdateInBgOn) {
                weatherUtils.schedule(this, intent);
            } else {
                weatherUtils.unschedule(this, intent);
            }
        }
    }

    private void loadWeatherOnce() {
        Intent intent = new Intent(MainActivity.this, WeatherIntentService.class);
        startService(intent);
    }
}
