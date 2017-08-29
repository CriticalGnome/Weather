package com.revotechs.weather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.revotechs.weather.entity.Forecast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String ID_FOR_MINSK = "625144";
    public static final String APP_ID = "5fa78ed60a50f976ae9416c3795693da";
    public static final String METRIC_UNITS = "metric";
    public static final String LANG_EN = "en";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Call<Forecast> call = ApiClient.getApi().loadCurrentWeather(ID_FOR_MINSK, METRIC_UNITS, LANG_EN, APP_ID);
        call.enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                Forecast forecast = response.body();

                TextView cityText = (TextView) findViewById(R.id.city_text);
                cityText.setText(
                        new StringBuilder()
                                .append(forecast.getName())
                                .append(", ")
                                .append(forecast.getSys().getCountry())
                );

                TextView temperatureText = (TextView) findViewById(R.id.temperature_text);
                temperatureText.setText(
                        new StringBuilder()
                                .append(getString(R.string.temperature_text))
                                .append(" ")
                                .append(String.valueOf(forecast.getMain().getTemp()))
                                .append("°C")

                );

                TextView weatherText = (TextView) findViewById(R.id.weather_text);
                weatherText.setText(
                        new StringBuilder()
                                .append(getString(R.string.weather_text))
                                .append(" ")
                                .append(forecast.getWeather().get(0).getMain())
                                .append(" (")
                                .append(forecast.getWeather().get(0).getDescription())
                                .append(")")
                );

                TextView pressureText = (TextView) findViewById(R.id.pressure_text);
                pressureText.setText(
                        new StringBuilder()
                                .append(getString(R.string.pressure_text))
                                .append(" ")
                                .append(String.valueOf(forecast.getMain().getPressure()))
                                .append(" hPa")
                );

                TextView humidityText = (TextView) findViewById(R.id.humidity_text);
                humidityText.setText(
                        new StringBuilder()
                                .append(getString(R.string.humidity_text))
                                .append(" ")
                                .append(String.valueOf(forecast.getMain().getHumidity()))
                                .append("%")
                );

                TextView windSpeedText = (TextView) findViewById(R.id.wind_speed_text);
                windSpeedText.setText(
                        new StringBuilder()
                                .append(getString(R.string.wind_speed_text))
                                .append(" ")
                                .append(String.valueOf(forecast.getWind().getSpeed()))
                                .append(" m/s")
                );

                ImageView weatherImage = (ImageView) findViewById(R.id.weather_image);
                int iconId = getResources().getIdentifier("image" + forecast.getWeather().get(0).getIcon(), "drawable", getPackageName());
                weatherImage.setImageDrawable(getDrawable(iconId));
            }

            @Override
            public void onFailure(Call<Forecast> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
