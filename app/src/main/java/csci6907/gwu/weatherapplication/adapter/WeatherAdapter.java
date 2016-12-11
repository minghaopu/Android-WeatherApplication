package csci6907.gwu.weatherapplication.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

import csci6907.gwu.weatherapplication.Constant;
import csci6907.gwu.weatherapplication.R;
import csci6907.gwu.weatherapplication.model.WeatherData;

/**
 * Created by Minghao Pu on 10/28/16.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {
    private ArrayList<WeatherData> mWeatherDatas = null;
    private Context mContext;
    private SharedPreferences mSharedPreferences;

    public WeatherAdapter(ArrayList<WeatherData> weatherDatas, Context context) {
        mWeatherDatas = weatherDatas;
        mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(Constant.PREF, Context.MODE_PRIVATE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String condition = mWeatherDatas.get(position).getConditions();
        String highF = mWeatherDatas.get(position).getHighFahrenheit();
        String lowF = mWeatherDatas.get(position).getLowFahrenheit();
        String highC = mWeatherDatas.get(position).getHighCelsius();
        String lowC = mWeatherDatas.get(position).getLowCelsius();
        String url = mWeatherDatas.get(position).getIconUrl();
        String weekday = mWeatherDatas.get(position).getWakeDay();

        boolean isFahrenheit = mSharedPreferences.getBoolean("isFahrenheit", true);

        holder.mLowCView.setText(lowC + "˚C");
        holder.mLowFView.setText(lowF + "˚F");
        holder.mHighFView.setText(highF + "˚F");
        holder.mHighCView.setText(highC + "˚C");

        if (isFahrenheit) {
            holder.mLowCView.setVisibility(View.GONE);
            holder.mHighCView.setVisibility(View.GONE);
            holder.mHighFView.setVisibility(View.VISIBLE);
            holder.mLowFView.setVisibility(View.VISIBLE);
        } else {
            holder.mLowCView.setVisibility(View.VISIBLE);
            holder.mHighCView.setVisibility(View.VISIBLE);
            holder.mHighFView.setVisibility(View.GONE);
            holder.mLowFView.setVisibility(View.GONE);
        }


        // if get the picture then hide condition else show the condition;
        Ion.with(holder.mIconView).load(url).setCallback(new FutureCallback<ImageView>() {
            @Override
            public void onCompleted(Exception e, ImageView result) {
                if(e == null) {
                    holder.mConditionView.setVisibility(View.GONE);
                }
                else {
                    holder.mConditionView.setVisibility(View.VISIBLE);
                }
            }
        });

        holder.mConditionView.setText(condition);
        holder.mWeekDayView.setText(weekday);

    }

    @Override
    public int getItemCount() {
        return mWeatherDatas.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mIconView;
        public TextView mConditionView;
        public TextView mHighFView;
        public TextView mLowFView;
        public TextView mHighCView;
        public TextView mLowCView;

        public TextView mWeekDayView;

        // constructor of item viewholder
        public ViewHolder(View itemView){
            super(itemView);
            mIconView = (ImageView) itemView.findViewById(R.id.image_icon);
            mConditionView = (TextView) itemView.findViewById(R.id.item_condition);
            mHighCView = (TextView) itemView.findViewById(R.id.item_highC);
            mHighFView = (TextView) itemView.findViewById(R.id.item_highF);
            mLowCView = (TextView) itemView.findViewById(R.id.item_lowC);
            mLowFView = (TextView) itemView.findViewById(R.id.item_lowF);
            mWeekDayView = (TextView) itemView.findViewById(R.id.item_weekday);

        }
    }

}
