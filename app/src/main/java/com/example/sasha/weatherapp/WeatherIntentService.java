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
                for (City city : City.values()) {
                    Weather weather = WeatherUtils.getInstance().loadWeather(city);
                    WeatherStorage.getInstance(getApplicationContext())
                            .saveWeather(city, weather);
                }
                Intent resultIntent = new Intent(MainActivity.ACTION_NEW_WEATHER);
                sendBroadcast(resultIntent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
