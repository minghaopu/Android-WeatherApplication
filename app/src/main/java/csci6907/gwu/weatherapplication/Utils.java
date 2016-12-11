package csci6907.gwu.weatherapplication;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by Minghao Pu on 10/27/16.
 */

public class Utils {
    public static JSONObject getJSONFromUrl(URL url) {
        HttpURLConnection urlConnection = null;
        String jsonString = "";
        JSONObject jsonObject = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            jsonString = stringBuilder.toString();
        } catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            urlConnection.disconnect();
        }

        try {
            jsonObject = new JSONObject(jsonString);
        }  catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        
        return jsonObject;
    }
}
