package com.example.sasha.weatherapp;

import android.app.IntentService;
import android.content.Intent;

import java.io.IOException;

import ru.mail.weather.lib.City;
import ru.mail.weather.lib.Weather;
import ru.mail.weather.lib.WeatherStorage;
import ru.mail.weather.lib.WeatherUtils;

public class WeatherIntentService extends IntentService {

    public WeatherIntentService() {
        super("WeatherIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            try {
                City currentCity = WeatherStorage.getInstance(getApplicationContext())
                        .getCurrentCity();
                Weather weather = WeatherUtils.getInstance().loadWeather(currentCity);
                WeatherStorage.getInstance(getApplicationContext())
                        .saveWeather(currentCity, weather);
                Intent resultIntent = new Intent(MainActivity.ACTION_NEW_WEATHER);
                sendBroadcast(resultIntent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
