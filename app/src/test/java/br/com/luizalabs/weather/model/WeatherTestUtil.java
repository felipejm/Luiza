package br.com.luizalabs.weather.model;


public class WeatherTestUtil {

    public static Weather create() {
        Weather weather = new Weather();
        weather.setIcon("ic_icon");
        weather.setTemperaturaUnit(TEMPERATURA_UNIT.CELSIUS);
        weather.setCityName("Name");
        weather.setDescription("Description");
        weather.setLatitude(-8.05428);
        weather.setLongitude(-34.8813);
        weather.setTemperatureMax("1");
        weather.setTemperatureMin("1");
        weather.setTemperature("1");

        return weather;
    }

    public static Weather create(Double lat, Double lon) {
        Weather weather = new Weather();
        weather.setIcon("ic_icon");
        weather.setTemperaturaUnit(TEMPERATURA_UNIT.CELSIUS);
        weather.setCityName("Name");
        weather.setDescription("Description");
        weather.setLatitude(lat);
        weather.setLongitude(lon);
        weather.setTemperatureMax("1");
        weather.setTemperatureMin("1");
        weather.setTemperature("1");

        return weather;
    }

}
