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

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Forecast> call = apiService.loadCurrentWeather(ID_FOR_MINSK, METRIC_UNITS, LANG_EN, APP_ID);
        call.enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                Forecast forecast = response.body();

                TextView cityText = (TextView) findViewById(R.id.city_text);
                cityText.setText(forecast.getName());
                cityText.append(", ");
                cityText.append(forecast.getSys().getCountry());

                TextView temperatureText = (TextView) findViewById(R.id.temperature_text);
                temperatureText.setText(R.string.temperature_text);
                temperatureText.append(" ");
                temperatureText.append(String.valueOf(forecast.getMain().getTemp()));
                temperatureText.append("°C");

                TextView weatherText = (TextView) findViewById(R.id.weather_text);
                weatherText.setText(R.string.weather_text);
                weatherText.append(" ");
                weatherText.append(forecast.getWeather().get(0).getMain());
                weatherText.append(" (");
                weatherText.append(forecast.getWeather().get(0).getDescription());
                weatherText.append(")");

                TextView pressureText = (TextView) findViewById(R.id.pressure_text);
                pressureText.setText(R.string.pressure_text);
                pressureText.append(" ");
                pressureText.append(String.valueOf(forecast.getMain().getPressure()));
                pressureText.append(" hPa");

                TextView humidityText = (TextView) findViewById(R.id.humidity_text);
                humidityText.setText(R.string.humidity_text);
                humidityText.append(" ");
                humidityText.append(String.valueOf(forecast.getMain().getHumidity()));
                humidityText.append("%");

                TextView windSpeedText = (TextView) findViewById(R.id.wind_speed_text);
                windSpeedText.setText(R.string.wind_speed_text);
                windSpeedText.append(" ");
                windSpeedText.append(String.valueOf(forecast.getWind().getSpeed()));
                windSpeedText.append(" m/s");

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
