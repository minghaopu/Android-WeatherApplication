package csci6907.gwu.weatherapplication.layout;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;
import android.widget.FrameLayout;

import csci6907.gwu.weatherapplication.Constant;
import csci6907.gwu.weatherapplication.R;

/**
 * Created by Minghao Pu on 10/30/16.
 */

public class ZipCodeEditor  extends FrameLayout {
    private EditText mEditText = null;
    private SharedPreferences mSharedPreferences = null;
    private Context mContext;
    private String mZip;

    public ZipCodeEditor(Context context) {
        super(context);
        mContext = context;

        mSharedPreferences = mContext.getSharedPreferences(Constant.PREF, Context.MODE_PRIVATE);


        inflate(context, R.layout.layout_zip, this);


        mEditText = (EditText) this.findViewById(R.id.edit_zip);

    }

    public String getValue(){
        return mEditText.getText().toString();
    }

}
