package csci6907.gwu.weatherapplication.layout;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.FrameLayout;
import android.widget.NumberPicker;

import csci6907.gwu.weatherapplication.Constant;
import csci6907.gwu.weatherapplication.R;

/**
 * Created by Minghao Pu on 10/29/16.
 */

public class DaysLimitPicker extends FrameLayout {
    private NumberPicker mNumberPicker = null;
    private SharedPreferences mSharedPreferences = null;
    private SharedPreferences.Editor mEditor = null;
    private int mCurrrentLimit;
    private Context mContext;
    private int mLimit;

    public DaysLimitPicker(Context context) {
        super(context);
        mContext = context;

        mSharedPreferences = mContext.getSharedPreferences(Constant.PREF, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        mCurrrentLimit = mSharedPreferences.getInt("daysLimit", 100);

        inflate(context, R.layout.layout_number, this);


        mNumberPicker = (NumberPicker) this.findViewById(R.id.np_days);
        mNumberPicker.setMinValue(3);
        mNumberPicker.setMaxValue(10);
        mNumberPicker.setValue(mCurrrentLimit);
        mNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mLimit = newVal;
            }
        });
    }

    public int getValue(){
        return mLimit;
    }

}
