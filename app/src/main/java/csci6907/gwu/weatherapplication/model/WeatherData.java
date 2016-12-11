package csci6907.gwu.weatherapplication.model;

/**
 * Created by Minghao Pu on 10/27/16.
 */

public class WeatherData {

    private String mConditions;
    private String mHighFahrenheit;
    private String mLowFahrenheit;
    private String mHighCelsius;
    private String mLowCelsius;
    private String mDate;
    private String mIconUrl;
    private String mIcon;
    private String mWakeDay;
    private String mAvehumidity;

    public WeatherData(String wakeDay, String conditions, String highFahrenheit, String lowFahrenheit, String highCelsius, String lowCelsius, String date, String iconUrl, String avehumidity) {
        mWakeDay = wakeDay;
        mConditions = conditions;
        mHighFahrenheit = highFahrenheit;
        mLowFahrenheit = lowFahrenheit;
        mHighCelsius = highCelsius;
        mLowCelsius = lowCelsius;
        mDate = date;
        mIconUrl = iconUrl;
        mAvehumidity = avehumidity;
    }


    public String getWakeDay() {
        return mWakeDay;
    }

    public void setWakeDay(String wakeDay) {
        mWakeDay = wakeDay;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public String getIconUrl() {
        return mIconUrl;
    }

    public void setIconUrl(String iconUrl) {
        mIconUrl = iconUrl;
    }

    public String getLowCelsius() {
        return mLowCelsius;
    }

    public void setLowCelsius(String lowCelsius) {
        mLowCelsius = lowCelsius;
    }

    public String getHighCelsius() {
        return mHighCelsius;
    }

    public void setHighCelsius(String highCelsius) {
        mHighCelsius = highCelsius;
    }

    public String getLowFahrenheit() {
        return mLowFahrenheit;
    }

    public void setLowFahrenheit(String lowFahrenheit) {
        mLowFahrenheit = lowFahrenheit;
    }

    public String getHighFahrenheit() {
        return mHighFahrenheit;
    }

    public void setHighFahrenheit(String highFahrenheit) {
        mHighFahrenheit = highFahrenheit;
    }

    public String getConditions() {
        return mConditions;
    }

    public void setConditions(String conditions) {
        mConditions = conditions;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getAvehumidity() {
        return mAvehumidity;
    }

    public void setAvehumidity(String avehumidity) {
        mAvehumidity = avehumidity;
    }
}
