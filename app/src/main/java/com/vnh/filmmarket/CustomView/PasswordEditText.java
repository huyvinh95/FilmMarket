package com.vnh.filmmarket.CustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.vnh.filmmarket.R;

/**
 * Created by HUYVINH on 28-Feb-17.
 */

public class PasswordEditText extends EditText {
    Boolean useValidate =false;
    public PasswordEditText(Context context) {
        super(context);
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        khoitao(attrs);
    }

    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        khoitao(attrs);
    }

    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        khoitao(attrs);
    }

    private void khoitao(AttributeSet attrs){
        if (attrs !=null){
            TypedArray array = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.PasswordEditText,0,0);
            this.useValidate = array.getBoolean(R.styleable.PasswordEditText_useValidate,false);
        }
        if (this.useValidate){
            setOnFocusChangeListener(new OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    Log.d("Forcus",hasFocus + "");
                }
            });
        }
    }
}
