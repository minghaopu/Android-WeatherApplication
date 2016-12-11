package csci6907.gwu.weatherapplication.activity.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import csci6907.gwu.weatherapplication.Constant;
import csci6907.gwu.weatherapplication.R;
import csci6907.gwu.weatherapplication.activity.MainActivity;
import csci6907.gwu.weatherapplication.adapter.WeatherAdapter;
import csci6907.gwu.weatherapplication.asynctask.LoadDataTask;
import csci6907.gwu.weatherapplication.model.LocationData;
import csci6907.gwu.weatherapplication.model.WeatherData;

import static android.content.Context.MODE_PRIVATE;


public class WeatherFragment extends Fragment implements LoadDataTask.LoadDataCompletionListener{


    private View mView = null;
    private Context mContext;
    private LocationData mLocationData;
    private FragmentManager mFragmentManager;
    private SettingFragment mSettingFragment;
    private LoadDataTask mLoadDataTask;
    private SharedPreferences mSharedPreferences = null;
    private int mDaysLimit = 100;
    private boolean mIsCurrent = true;

    public WeatherFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        mFragmentManager = getFragmentManager();
        mSettingFragment = (SettingFragment) mFragmentManager.findFragmentById(R.id.fragment_setting);
        mSharedPreferences = mContext.getSharedPreferences(Constant.PREF, MODE_PRIVATE);

        mDaysLimit = mSharedPreferences.getInt("daysLimit", Constant.DEFAULTDAYS);

        mIsCurrent = mSharedPreferences.getBoolean("isCurrent", true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
//        return inflater.inflate(R.layout.fragment_weather, container, false);
        mView = inflater.inflate(R.layout.fragment_weather, container, false);
        mContext = getActivity();
        if (mContext != null) {
            mLoadDataTask = new LoadDataTask(mContext, mIsCurrent);
            mLoadDataTask.setCompletionListener(this);
            mLoadDataTask.execute("");
            setRetainInstance(true);
        }
        return mView;

    }

    @Override
    public void onResume() {

        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        // once detach, stop asynctask
        mContext = null;
        if(mLoadDataTask != null && mLoadDataTask.getStatus() == AsyncTask.Status.RUNNING)
            mLoadDataTask.cancel(true);
        super.onDetach();
    }



    // create dummy dataset
//    public static ArrayList<WeatherData> getData(){
//        ArrayList<WeatherData> weatherDatas = new ArrayList<WeatherData>();
//        for (int i = 1; i <= 10; i++) {
//            String condition = "condition " + i;
//            String highF = "HighF " + i;
//            String lowF = "lowF " + i;
//            String highC = "highC " + i;
//            String lowC = "lowC " + i;
//            String url = "url " + i;
//            String date = "date " + i;
//            String weekday = "weekday " + i;
//
//            WeatherData item = new WeatherData(weekday, condition, highF, lowF, highC, lowC, date, url, Integer.toString(i));
//            weatherDatas.add(item);
//        }
//        return  weatherDatas;
//    }

    public void locationDataLoaded(LocationData locationData) {
        mLocationData = locationData;

        if (mContext != null) {
            // 1. get a reference to recyclerView
            RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.recycler_view_weather);

            // 2. set layoutManger
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext));


            ArrayList<WeatherData> weatherDatas = mLocationData.getWeatherDatas();
            ArrayList<WeatherData> visibleDatas = new ArrayList<WeatherData>();

            // set the limit for the number of data showed on the screen
            for(int i = 0; i < mDaysLimit && i < weatherDatas.size(); i++) {
                visibleDatas.add(weatherDatas.get(i));
            }

            // 3. create an adapter
            WeatherAdapter mAdapter = new WeatherAdapter(visibleDatas, getActivity());
            // 4. set adapter
            recyclerView.setAdapter(mAdapter);
            // 5. set item animator to DefaultAnimator
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            ((MainActivity)getActivity()).setActionBarTitle(mLocationData.getCity());
        }

    }
    public void locationDataNotLoad() {

    }
}
