package csci6907.gwu.weatherapplication.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;

import csci6907.gwu.weatherapplication.Constant;
import csci6907.gwu.weatherapplication.R;
import csci6907.gwu.weatherapplication.layout.DaysLimitPicker;
import csci6907.gwu.weatherapplication.layout.ZipCodeEditor;

/**
 * Created by Minghao Pu on 10/29/16.
 */

public class SettingDialog extends AlertDialog {
    private DaysLimitPicker mDaysLimitPicker;
    private ZipCodeEditor mZipCodeEditor;
    private Context mContext;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private buttonClickListener mListener;
    private boolean mIsZipCode;

    public interface buttonClickListener {
        public void positiveListener(int limit);
        public void positiveListener(String zipCode);
        public void NegativeListener();
    }
    public void setButtonClickListener(SettingDialog.buttonClickListener listener) {
        mListener = listener;
    }

    public SettingDialog(Context context, boolean isZipCode) {
        super(context);

        mSharedPreferences = context.getSharedPreferences(Constant.PREF, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        mIsZipCode = isZipCode;
        mContext = context;

        if (mIsZipCode) {
            mZipCodeEditor = new ZipCodeEditor(mContext);
            setView(mZipCodeEditor);
            setTitle(R.string.label_zipcode);
            setButton(BUTTON_POSITIVE, mContext.getResources().getString(R.string.Confirm),
                    new DialogInterface.OnClickListener() {
                        @Override

                        public void onClick(DialogInterface dialogInterface, int id) {
                            String zipCode = mZipCodeEditor.getValue();
                            mEditor.putString("zipCode", zipCode);
                            mEditor.commit();
                            mListener.positiveListener(zipCode);
                        }
                    });
        } else {
            mDaysLimitPicker = new DaysLimitPicker(mContext);
            setView(mDaysLimitPicker);
            setTitle(R.string.label_days);
            setButton(BUTTON_POSITIVE, mContext.getResources().getString(R.string.Confirm),
                    new DialogInterface.OnClickListener() {
                        @Override

                        public void onClick(DialogInterface dialogInterface, int id) {
                            int limit = mDaysLimitPicker.getValue();
                            mEditor.putInt("daysLimit", limit);
                            mEditor.commit();
                            mListener.positiveListener(limit);
                        }
                    });

        }
        setButton(BUTTON_NEGATIVE, mContext.getResources().getString(R.string.Cancel),
                new DialogInterface.OnClickListener() {
                    @Override

                    public void onClick(DialogInterface dialogInterface, int id) {
                        mListener.NegativeListener();
                    }
                });

    }


}
