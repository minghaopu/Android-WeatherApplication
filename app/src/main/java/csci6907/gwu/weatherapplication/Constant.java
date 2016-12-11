package csci6907.gwu.weatherapplication;

/**
 * Created by Minghao Pu on 10/27/16.
 */

public class Constant {
    public static final String FORCAST_URL_PREFIX = "http://api.wunderground.com/api/4df818b4b9678e90/forecast/q/";
    public static final String CURRENT_URL_PREFIX = "http://api.wunderground.com/api/4df818b4b9678e90/conditions/q/";
    public static final String FORCAST_10DAYS_URL_PREFIX = "http://api.wunderground.com/api/4df818b4b9678e90/forecast10day/q/";
    public static final String WEATHER_SURFIX = ".json";
    public static final String CITY_LANG_PREFIX = "http://maps.googleapis.com/maps/api/geocode/json?latlng=";
    public static final String CITY_ZIP_PREFIX = "http://maps.googleapis.com/maps/api/geocode/json?address=";

    public static final String CITY_SURFIX = "&sensor=true";
    public static final String PREF = "myperference";
    public static final int DEFAULTDAYS = 3;
//    http://api.wunderground.com/api/4df818b4b9678e90/forecast10day/q/37.776289,-122.395234.json
//    http://maps.googleapis.com/maps/api/geocode/json?latlng=38.8501078,-77.03917539999999&sensor=true
}
