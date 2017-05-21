package br.com.luizalabs.weather.model;


public class WeatherTestUtil {

    public static Weather create(TemperatureUnitEnum temperatureUnitEnum) {
        return create(-8.05428, -34.8813, temperatureUnitEnum);
    }

    public static Weather create(double lat, double lon) {
        return create(lat, lon, TemperatureUnitEnum.CELSIUS);
    }

    public static Weather create() {
        return create(-8.05428, -34.8813, TemperatureUnitEnum.CELSIUS);
    }

    public static Weather create(Double lat, Double lon, TemperatureUnitEnum temperatureUnitEnum) {
        Weather weather = new Weather();
        weather.setIcon("ic_icon");
        weather.setTemperaturaUnit(temperatureUnitEnum);
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
