package csci6907.gwu.weatherapplication.activity.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TableRow;
import android.widget.TextView;

import csci6907.gwu.weatherapplication.Constant;
import csci6907.gwu.weatherapplication.R;
import csci6907.gwu.weatherapplication.dialog.SettingDialog;


public class SettingFragment extends Fragment implements SettingDialog.buttonClickListener {
    private View mView = null;
    private Switch mFormatSwitch = null;
//    private String mZipCode = null;
    private Switch mCurrentSwitch = null;
    private TableRow mZipRow = null;
    private TableRow mDayRow = null;
    private TextView mZipTextView = null;
    private TextView mDaysTextView = null;
    private SharedPreferences mSharedPreferences = null;
    private SharedPreferences.Editor mEditor = null;
    private Context mContext;

    public SettingFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSharedPreferences = getActivity().getSharedPreferences(Constant.PREF, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        mView = inflater.inflate(R.layout.fragment_setting, container, false);
        mFormatSwitch = (Switch)mView.findViewById(R.id.format_switch);
        mCurrentSwitch= (Switch)mView.findViewById(R.id.current_switch);
        mZipRow = (TableRow)mView.findViewById(R.id.row_zip);
        mDayRow = (TableRow)mView.findViewById(R.id.row_days);
        mZipTextView = (TextView)mView.findViewById(R.id.value_zipcode);
        mDaysTextView = (TextView)mView.findViewById(R.id.value_days);


        // get current setting
        boolean isFahrenheit = mSharedPreferences.getBoolean("isFahrenheit", true);
        boolean isCurrent = mSharedPreferences.getBoolean("isCurrent", true);
        int daysLimit =  mSharedPreferences.getInt("daysLimit", Constant.DEFAULTDAYS);

        // set the widget with the value of current setting
        mFormatSwitch.setChecked(isFahrenheit);
        mCurrentSwitch.setChecked(isCurrent);
        mDaysTextView.setText(Integer.toString(daysLimit));


        if (isCurrent) {
            // if use current location, zip code is invisible
            mZipRow.setVisibility(View.GONE);
        } else {
            // if use zip code
            mZipRow.setVisibility(View.VISIBLE);
            String zipCode = mSharedPreferences.getString("zipCode", "");
            mZipTextView.setText(zipCode);
        }


        // set listener for temperature switch, on -> ˚F, off -> ˚C
        mFormatSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    mEditor.putBoolean("isFahrenheit", true);
                } else {
                    mEditor.putBoolean("isFahrenheit", false);
                }
                mEditor.commit();
            }
        });

        // set listener for current location switch, on -> use current, off -> not use
        mCurrentSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mZipRow.setVisibility(View.GONE);
                    mEditor.putBoolean("isCurrent", true);
                } else {
                    mZipRow.setVisibility(View.VISIBLE);
                    mEditor.putBoolean("isCurrent", false);
                }
                mEditor.commit();
            }
        });

        mZipRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rowClick(true);
            }
        });

        mDayRow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                getResources().getString();
                rowClick(false);
            }
        });


        return mView;

    }

    @Override
    public void onDetach() {
        mContext = null;
        super.onDetach();
    }

    public void setDaysTextView(int limit) {
        mDaysTextView.setText(limit);
    }

    @Override
    public void positiveListener(int limit) {
        mDaysTextView.setText(Integer.toString(limit));
    }

    @Override
    public void positiveListener(String zipCode) {
        mZipTextView.setText(zipCode);
    }


    @Override
    public void NegativeListener() {

    }


    // show setting dialog for zipcode or days limit
    public void rowClick(boolean isZip) {
        SettingDialog dialog = new SettingDialog(getActivity(), isZip);
        dialog.setButtonClickListener(this);
        dialog.show();
    }
}
