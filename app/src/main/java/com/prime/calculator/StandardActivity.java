package com.prime.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.prime.calculator.Utility.History;

public class StandardActivity extends ScientificActivity {

    public static final String TAG = "StandardActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard);
        setTitle(R.string.title_activity_standard);
        initializeViews();
        mFormula.setOnTextSizeChangeListener(this);
    }

    @Override
    protected void initializeViews() {
        super.mFormula = findViewById(R.id.formula);
        super.mResult = findViewById(R.id.result);
        super.mModeToggle = findViewById(R.id.toggle_mode);
        super.mModeToggle.setVisibility(View.GONE);
        super.dBTableName = History.TABLE_STANDARD;
        super.btnDel = findViewById(R.id.del);
        btnDel.setOnLongClickListener(v -> {
            mFormula.setText("");
            mResult.setText("");
            return true;
        });
    }

    @Override
    public void onTextSizeChanged(TextView textView, float oldSize) {
        super.onTextSizeChanged(textView, oldSize);
    }

    @Override
    protected int getSelectedNavItem() {
        return R.id.nav_standard;
    }
}

