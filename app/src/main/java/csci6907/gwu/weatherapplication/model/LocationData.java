package csci6907.gwu.weatherapplication.model;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
/**
 * Created by Minghao Pu on 10/27/16.
 */

public class LocationData {
    private String mCurCity;
    private String mCurState;
    private boolean mIsCurrent;
    private String mZipCode;
    private String mCity;
    private String mState;
    private String mCountry;



    private ArrayList<WeatherData> mWeatherDatas;


    public LocationData(JSONObject cityObject, JSONObject weatherObject) {
        mWeatherDatas = new ArrayList<WeatherData>();
        try {
            JSONObject current_observation = cityObject.getJSONObject("current_observation");
            JSONObject display_location = current_observation.getJSONObject("display_location");
            mCity = display_location.getString("city");
            mState = display_location.getString("state");
            mCountry = display_location.getString("country");
            mZipCode = display_location.getString("zip");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JSONArray forecastsObj = weatherObject.getJSONObject("forecast").getJSONObject("simpleforecast").getJSONArray("forecastday");
            JSONObject dayObj = null;
            JSONObject dateObj = null;
            for (int i = 0; i < forecastsObj.length(); i++){
                dayObj = forecastsObj.getJSONObject(i);
                dateObj = dayObj.getJSONObject("date");
                String condition = dayObj.getString("conditions");
                String highF = dayObj.getJSONObject("high").getString("fahrenheit");
                String lowF = dayObj.getJSONObject("low").getString("fahrenheit");
                String highC = dayObj.getJSONObject("high").getString("celsius");
                String lowC = dayObj.getJSONObject("low").getString("celsius");
                String url = dayObj.getString("icon_url");
                String date = dateObj.getString("monthname_short") + " " + dateObj.getString("day");
                String weekday = dateObj.getString("weekday");
                String humidity = dayObj.getString("avehumidity");
                WeatherData weatherData = new WeatherData(weekday, condition, highF, lowF, highC, lowC, date, url, humidity);
                mWeatherDatas.add(weatherData);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mWeatherDatas.get(0);
    }

    public ArrayList<WeatherData> getWeatherDatas() {
        return mWeatherDatas;
    }


    public String getCity() {
        return mIsCurrent?mCurCity:mCity;
    }

    public String getState() {
        return mIsCurrent?mCurState:mState;
    }
}
