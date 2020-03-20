package com.prime.calculator;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.prime.calculator.StandardCalUtils.CustomEditText;
import com.prime.calculator.StandardCalUtils.CustomEditText.OnTextSizeChangeListener;
import com.prime.calculator.Utility.DatabaseHelper;
import com.prime.calculator.Utility.History;
import com.prime.calculator.Utility.Utils;

import androidx.fragment.app.Fragment;

import static com.prime.calculator.HistoryFragment.HistoryFragmentListener;

public class ScientificActivity extends NavigationViewActivity
        implements OnTextSizeChangeListener, HistoryFragmentListener {

    public static final String TAG = "ScientificActivity";

    protected CustomEditText mFormula;
    protected TextView mResult;
    protected double mMemory;
    protected Button mModeToggle;
    protected DatabaseHelper mDatabaseHelper = new DatabaseHelper(this);
    protected String dBTableName = History.TABLE_SCIENTIFIC;
    private View[] mButtons;
    private TextView mShift;
    private ImageView mShiftIV;
    private int mMode = Utils.MODE_DEG;
    protected Button btnDel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scientific);
        initializeViews();  /*Initialize Every View Inside This*/
        mFormula.setOnTextSizeChangeListener(this);
        btnDel.setOnLongClickListener(v -> {
            mFormula.setText("");
            mResult.setText("");
            return true;
        });
    }

    @Override
    protected int getSelectedNavItem() {
        return R.id.nav_scientific;
    }

    protected void initializeViews() {
        mFormula = findViewById(R.id.formula);
        mResult = findViewById(R.id.result);
        mShift = findViewById(R.id.shift);
        mModeToggle = findViewById(R.id.toggle_mode);
        btnDel = findViewById(R.id.del);
        mShiftIV = findViewById(R.id.shift_icon);
        /* View Arrays used to hold views in order control their visibility*/
        mButtons = new View[]{
                findViewById(R.id.op_root),
                findViewById(R.id.fun_arcsin),
                findViewById(R.id.fun_arccos),
                findViewById(R.id.fun_arctan),
                findViewById(R.id.fun_log_base_n),
                findViewById(R.id.fun_archypsin),
                findViewById(R.id.fun_archypcos),
                findViewById(R.id.fun_archyptan),
                findViewById(R.id.op_mod),
                findViewById(R.id.op_sqrt),
                findViewById(R.id.const_phi),
                findViewById(R.id.fun_log_base_2),
                findViewById(R.id.op_cbrt),
        };
        btnDel = findViewById(R.id.del);
    }

    @Override
    public void onTextSizeChanged(TextView textView, float oldSize) {
        // Calculate the values needed to perform the scale and translation animations,
        // maintaining the same apparent baseline for the displayed text.
        final float textScale = oldSize / textView.getTextSize();
        final float translationX = (1.0f - textScale) *
                (textView.getWidth() / 2.0f - textView.getPaddingEnd());
        final float translationY = (1.0f - textScale) *
                (textView.getHeight() / 2.0f - textView.getPaddingBottom());

        final AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(textView, View.SCALE_X, textScale, 1.0f),
                ObjectAnimator.ofFloat(textView, View.SCALE_Y, textScale, 1.0f),
                ObjectAnimator.ofFloat(textView, View.TRANSLATION_X, translationX, 0.0f),
                ObjectAnimator.ofFloat(textView, View.TRANSLATION_Y, translationY, 0.0f));
        animatorSet.setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.start();
    }

    public void onClick(View view) {
        mFormula.setTextColor(Utils.getColorFromAttr(R.attr.primaryTextColor, this));
        switch (view.getId()) {
            case R.id.del:
                onDelete();
                break;
            case R.id.shift:
                boolean shift_selected = !mShift.isSelected();
                mShift.setSelected(shift_selected);
                onShiftToggle(shift_selected);
                break;
            //After Which a left Parentheses is required
            case R.id.fun_cos:
            case R.id.fun_sin:
            case R.id.fun_tan:
            case R.id.fun_arcsin:
            case R.id.fun_arccos:
            case R.id.fun_arctan:
            case R.id.fun_hypsin:
            case R.id.fun_hypcos:
            case R.id.fun_hyptan:
            case R.id.fun_archypsin:
            case R.id.fun_archypcos:
            case R.id.fun_archyptan:
            case R.id.fun_log_base_2:
            case R.id.fun_log_base_e:
            case R.id.fun_log_base_10:
                // Add left parenthesis after functions.
                mFormula.insert(((Button) view).getText() + "(");
                break;
            case R.id.op_pow:
                mFormula.insert("^");
                break;
            case R.id.op_sqr:
                mFormula.insert("^2");
                break;
            case R.id.op_cube:
                mFormula.insert("^3");
                break;
            case R.id.op_root:
                mFormula.insert("√");
                break;
            case R.id.toggle_mode:
                onModeToggle();
                break;
            case R.id.equal:
                onEquals();
                break;
            case R.id.btn_history:
                loadHistoryFragment();
                break;
            case R.id.memory_add:
            case R.id.memory_recall:
            case R.id.memory_store:
            case R.id.memory_subtract:
            case R.id.memory_clear:
                onMemoryItemClicked(view.getId());
                break;
            case R.id.fun_log_base_n:
                mFormula.insert(((Button) view).getText() + "(,");
                mFormula.setSelection(mFormula.getSelectionStart() - 1);
                break;
            default:
                mFormula.insert(((Button) view).getText().toString());
                break;
        }
        if (Utils.isExpressionOnlyOfNumbers(mFormula.getText().toString()))
            mResult.setText("");
        else
            mResult.setText(String.valueOf(Evaluator.Evaluate(mFormula.getText().toString(), this, mMode)));
    }

    protected void loadHistoryFragment() {
        Bundle bundle = new Bundle();
        bundle.putString("TableName", dBTableName);
        Fragment fragment = new HistoryFragment();
        fragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                //.setCustomAnimations(R.anim.bounce, R.anim.slide_up, R.anim.bounce, R.anim.slide_up)
                .replace(R.id.activity_content, fragment, HistoryFragment.TAG)
                .addToBackStack(HistoryFragment.TAG)
                .commit();
    }

    @Override
    protected void setupToolbar() {
        super.setupToolbar();
    }

    /**
     * Invoked whenever the Shift button is toggled to update the UI.
     * Changes Color of Shift Button to Color Accent, if Shift is Selected OtherWise Back to Normal
     *
     * @param shift_selected {@code true} if Hidden Butons should be shown
     */
    private void onShiftToggle(boolean shift_selected) {
        mShift.setSelected(shift_selected);
        if (shift_selected) {
            for (View shiftInvertibleButton : mButtons) {
                shiftInvertibleButton.setVisibility(View.VISIBLE);
            }
            //Set Accent Color on Theme
            mShiftIV.setColorFilter(Utils.getColorFromAttr(R.attr.accentColorPrimary, this));
        } else {
            for (View shiftInvertibleButton : mButtons) {
                shiftInvertibleButton.setVisibility(View.GONE);
            }
            //Set Accent Color on Theme
            mShiftIV.setColorFilter(Utils.getColorFromAttr(R.attr.primaryTextColor, this));
        }
    }

    private void onDelete() {
        mFormula.backspace();
    }

    protected void onEquals() {
        if (mResult.getText().toString().isEmpty())
            return;
        mDatabaseHelper.insertData(dBTableName, mFormula.getText().toString(),
                mResult.getText().toString());
        mFormula.setText(mResult.getText().toString());
        mResult.setText("");
        mFormula.setTextColor(Utils.getColorFromAttr(R.attr.accentColorPrimary, this));
    }

    private void onMemoryItemClicked(int id) {
        double d = mResult.getText().toString().isEmpty() ? 0 : Double.valueOf(mResult.getText().toString());
        switch (id) {
            case R.id.memory_store:
                mMemory = d;
                break;
            case R.id.memory_add:
                mMemory += d;
                break;
            case R.id.memory_subtract:
                mMemory -= d;
                break;
            case R.id.memory_recall:
                if (mMemory != 0)
                    mFormula.insert(Utils.doubleToString(mMemory, Utils.MAX_DIGITS, Utils.ROUNDING_DIGITS));
                break;
            case R.id.memory_clear:
                if (mMemory != 0)
                    mMemory = 0;
                break;
        }
        if (id != R.id.memory_recall)
            Utils.showToast(this, "Memory " + String.valueOf(mMemory));
    }

    /**
     * Invoked whenever the deg/rad/grad mode may have changed to update the UI. Note that the mode has
     * not necessarily actually changed where this is invoked.
     */
    private void onModeToggle() {
        if (mMode == Utils.MODE_DEG) {
            mMode = Utils.MODE_RAD;
            mModeToggle.setText(getString(R.string.mode_rad));
        } else if (mMode == Utils.MODE_RAD) {
            mMode = Utils.MODE_GRAD;
            mModeToggle.setText(getString(R.string.mode_grad));
        } else {
            mMode = Utils.MODE_DEG;
            mModeToggle.setText(getString(R.string.mode_deg));
        }
        mResult.setText(String.valueOf(Evaluator.Evaluate(mFormula.getText().toString(), this, mMode)));
    }

    @Override
    public void onHistoryItemSelected(String expression) {
        if (mFormula.length() == 0)
            mFormula.insert(expression);
        else
            mFormula.insert("(" + expression + ")");
        mResult.setText(String.valueOf(Evaluator.Evaluate(mFormula.getText().toString(), this, mMode)));
    }

    @Override
    public void setDrawerEnabled(boolean enable) {
        toggleNavDrawerToolbar(enable, true);
    }
}