package br.com.luizalabs.utils;


public class TemperatureConverterHelper {

    public static String convertCelsiusToFahrenheit(String celsius) {
        int celsiusValue = Integer.valueOf(celsius);
        return String.format("%.0f", ((celsiusValue * 9 / 5.0) + 32));
    }

    public static String convertFahrenheitToCelsius(String fahrenheit) {
        int fahrenheitValue = Integer.valueOf(fahrenheit);
        return String.format("%.0f", ((fahrenheitValue - 32) * (5 / 9.0)));
    }
}
