package csci6907.gwu.weatherapplication.asynctask;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import csci6907.gwu.weatherapplication.Constant;
import csci6907.gwu.weatherapplication.Utils;
import csci6907.gwu.weatherapplication.location.WeatherLocationTracker;
import csci6907.gwu.weatherapplication.model.LocationData;

/**
 * Created by Minghao Pu on 10/27/16.
 */

public class LoadDataTask extends AsyncTask<String, Integer, LocationData> {
    private Context mContext;
    private boolean mIsCurrent;
    private LoadDataCompletionListener mLoadDataCompletionListener;
    private SharedPreferences mSharedPreferences;


    public interface LoadDataCompletionListener {
        public void locationDataLoaded(LocationData locationData);
        public void locationDataNotLoad();
    }
    public LoadDataTask(Context context, boolean isCurrent) {
        mContext = context;
//        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);

        mSharedPreferences = context.getSharedPreferences(Constant.PREF, Context.MODE_PRIVATE);
        mIsCurrent = isCurrent;
    }
    public void setCompletionListener(LoadDataCompletionListener listener) {
        mLoadDataCompletionListener = listener;
    }

    @Override
    protected LocationData doInBackground(String... params) {
        LocationData locationData = null;
        WeatherLocationTracker weatherLocationTracker = new WeatherLocationTracker(mContext);
        Double latitude = weatherLocationTracker.getLatitue();
        Double longtitude = weatherLocationTracker.getLongtitude();
        URL cityURL = null;
        URL weatherURL = null;
        String zipCode = mSharedPreferences.getString("zipCode", "");

        if (zipCode != "" && mIsCurrent == false) {
            try {
//                cityURL = new URL(Constant.CITY_ZIP_PREFIX + zipCode + Constant.CITY_SURFIX);
                cityURL = new URL(Constant.CURRENT_URL_PREFIX + zipCode + Constant.WEATHER_SURFIX);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                weatherURL = new URL(Constant.FORCAST_10DAYS_URL_PREFIX + zipCode + Constant.WEATHER_SURFIX);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else {
            try {
//                cityURL = new URL(Constant.CITY_LANG_PREFIX + latitude + "," + longtitude + Constant.CITY_SURFIX);
                cityURL = new URL(Constant.CURRENT_URL_PREFIX  + latitude + "," + longtitude + Constant.WEATHER_SURFIX);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                weatherURL = new URL(Constant.FORCAST_10DAYS_URL_PREFIX + latitude + "," + longtitude + Constant.WEATHER_SURFIX);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }



        if (cityURL != null && weatherURL != null) {
            JSONObject cityObject = Utils.getJSONFromUrl(cityURL);
            JSONObject weatherObject = Utils.getJSONFromUrl(weatherURL);
            locationData = new LocationData(cityObject, weatherObject);
        }
        return locationData;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(LocationData locationData) {
        super.onPostExecute(locationData);
        if(mLoadDataCompletionListener != null) {
            if(locationData.getWeatherDatas() != null && locationData.getWeatherDatas().size() > 0) {
                //success
                mLoadDataCompletionListener.locationDataLoaded(locationData);
            }
            else{
                //fail
                mLoadDataCompletionListener.locationDataNotLoad();
            }
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }


}
