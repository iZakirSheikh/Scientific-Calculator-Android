package com.prime.calculator;

import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.prime.calculator.UnitConverter.Conversions;
import com.prime.calculator.UnitConverter.Conversions.ExchangeRates;
import com.prime.calculator.UnitConverter.Conversions.ExchangeRates.StatusListener;
import com.prime.calculator.UnitConverter.Converter;
import com.prime.calculator.UnitConverter.CurrencyDialog;
import com.prime.calculator.UnitConverter.CurrencyDialog.CurrencySelectionListener;
import com.prime.calculator.UnitConverter.Unit;
import com.prime.calculator.UnitConverter.UnitAdapter;
import com.prime.calculator.Utility.DatabaseHelper;
import com.prime.calculator.Utility.PrefManager;
import com.prime.calculator.Utility.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class UnitConverterActivity extends NavigationViewActivity implements
        OnItemSelectedListener, TextWatcher, OnClickListener, CurrencySelectionListener, StatusListener {

    private Spinner mUnitFromSpinner;
    private Spinner mUnitToSpinner;
    private EditText mFromEditText, mToEditText;
    private Conversions mConversions;
    private int mUnitFromId = 0, mUnitToId = 0;
    private int mConverterId;
    private TextInputLayout textInputLayoutFrom, textInputLayoutTo;
    private double value;
    private ImageButton mBtnFrom;
    private ImageButton mBtnTo;
    private CurrencyDialog dialog = null;
    private PrefManager prefManager = null;
    private TextView mExchangeRatesTv;

    private boolean isFirstStartUp = true; //When Activity First Launches
    private boolean iteratedAllKeys = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_converter);
        initialize();
    }

    private void initialize() {
        mConversions = Conversions.getInstance(this);
        Spinner mConverterSpinner = findViewById(R.id.unt_cntr_converter);
        mUnitFromSpinner = findViewById(R.id.unt_cntr_unit_from_spinner);
        mUnitToSpinner = findViewById(R.id.unt_cntr_unit_to_spinner);
        mFromEditText = findViewById(R.id.unt_cntr_unit_from_edt);
        mToEditText = findViewById(R.id.unt_cntr_unit_to_edt);
        textInputLayoutFrom = findViewById(R.id.unt_cntr_text_input_layout1);
        textInputLayoutTo = findViewById(R.id.unt_cntr_text_input_layout2);
        mBtnFrom = findViewById(R.id.unt_cntr_btn_spinner_from);
        mBtnTo = findViewById(R.id.unt_cntr_btn_spinner_to);
        FloatingActionButton mBtnSwap = findViewById(R.id.unt_cntr_fab_unit_swapper);
        mExchangeRatesTv = findViewById(R.id.unt_cntr_exchange_rate_tv);
        ArrayList<String> converters = new ArrayList<>(mConversions.getConverterLabels());
        prefManager = PrefManager.getInstance(this);

        mExchangeRatesTv.setText(prefManager.getStringPref(PrefManager.CURRENCY_UPDATE_DATE, null));


        mConverterSpinner.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, converters));
        //set item selected listener on items
        mConverterSpinner.setOnItemSelectedListener(this);
        mUnitFromSpinner.setOnItemSelectedListener(this);
        mUnitToSpinner.setOnItemSelectedListener(this);

        mConverterSpinner.setSelection(prefManager.getIntegerPref(PrefManager.PREF_CONVERTER, Converter.LENGTH));
        //add text watcher to editText input
        mFromEditText.addTextChangedListener(this);


        //set On click listeners
        mBtnFrom.setOnClickListener(this);
        mBtnTo.setOnClickListener(this);
        mFromEditText.setOnClickListener(this);
        mBtnSwap.setOnClickListener(this);

        mToEditText.setOnLongClickListener(v -> {
            //Copy text to clipboard
            if(!mToEditText.getText().toString().isEmpty()) {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getApplication().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Conversion Result", ((EditText) v).getText().toString());
                clipboard.setPrimaryClip(clip);
                Utils.showToast(this, "Result copied to Clipboard");
            }else
                Utils.showToast(this, "Result is empty!");
            return true;
        });
    }

    @Override
    protected void setupToolbar() {
        super.setupToolbar();
        super.mToolbar.findViewById(R.id.toggle_mode).setVisibility(View.GONE);
        super.mToolbar.findViewById(R.id.btn_history).setVisibility(View.GONE);
        super.mToolbar.findViewById(R.id.btn_download).setVisibility(View.VISIBLE);
        mToolbar.findViewById(R.id.btn_download).setOnClickListener(this);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    /**
     * This method calculates and sets result on change in editText from value.
     * formats and sets result to to field.,
     * sets selection to always to end.
     * adds comma to input text field
     *
     * @param s : string which has been changed
     */
    @Override
    public void afterTextChanged(Editable s) {
        String input = s.toString().replaceAll(",", "");
        value = Utils.getDouble(input);
        convert();
        //set EditText n
        mFromEditText.removeTextChangedListener(this);
        mFromEditText.setText(Utils.addThousandSeparators(input, Utils.THOUSAND_SEPARATOR_COMMA));
        //set curser at end
        mFromEditText.setSelection(mFromEditText.getText().length());
        mFromEditText.addTextChangedListener(this);
    }

    private void convert() {
        String output;
        if(mConverterId == Converter.CURRENCY)
            output = Utils.formatDecimal((mConversions.convert(mConverterId, mUnitFromId, mUnitToId, value)), 2);
        else
            output = Utils.formatDecimal((mConversions.convert(mConverterId, mUnitFromId, mUnitToId, value)), 15);
        mToEditText.setText(output);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (prefManager.getBooleanPref(PrefManager.UNT_CTR_FIRST_RUN, true)) {
            prefManager.setBooleanPref(PrefManager.UNT_CTR_FIRST_RUN, false);
            //update currencies
            updateCurrencies();
        }
        mExchangeRatesTv.setText(prefManager.getStringPref(PrefManager.CURRENCY_UPDATE_DATE, null));
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.unt_cntr_btn_spinner_from:
                if (mConverterId == Converter.CURRENCY)
                    showCurrencyDialog(v.getId());
                else
                    mUnitFromSpinner.performClick();
                break;
            case R.id.unt_cntr_btn_spinner_to:
                if (mConverterId == Converter.CURRENCY)
                    showCurrencyDialog(v.getId());
                else
                    mUnitToSpinner.performClick();
                break;
            case R.id.unt_cntr_unit_from_edt:
                mFromEditText.setSelection(mFromEditText.getText().length());
                break;
            case R.id.unt_cntr_fab_unit_swapper:
                if (mUnitFromId != mUnitToId) {
                    int temp = mUnitFromId;
                    mUnitFromId = mUnitToId;
                    mUnitToId = temp;
                    CharSequence s = textInputLayoutFrom.getHint();
                    textInputLayoutFrom.setHint(textInputLayoutTo.getHint());
                    textInputLayoutTo.setHint(s);
                    saveDefaultPrefs(mUnitFromId, textInputLayoutFrom.getHint().toString(), mBtnFrom.getId(), mConverterId);
                    saveDefaultPrefs(mUnitToId, textInputLayoutTo.getHint().toString(), mBtnTo.getId(), mConverterId);
                    convert();
                    Utils.showToast(this, "Units Swapped");
                }
                break;
            case R.id.btn_download:
                // mConversions = Conversions.getInstance(this);
                updateCurrencies();
                break;
        }
        if (v.getId() != mFromEditText.getId()) {
            v.setEnabled(false);
            v.postDelayed(() -> v.setEnabled(true), 1000);
        }

    }

    private void showCurrencyDialog(int viewId) {
        if (dialog == null)
            dialog = new CurrencyDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("viewId", viewId);
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(), "dialog");
    }

    /**
     * This method updates unit id on selection change.
     * sets text to empty to both from and to fields as converter changes.
     * loads new units on change in converter.
     * recalculates result if units change.
     *
     * @param parent   : parent view
     * @param view     :
     * @param position : position of selected item starts from 0
     * @param id       : same as position
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.unt_cntr_converter:
                mFromEditText.setEnabled(true);
                mConverterId = position; // if position equals zero converter is Length
                if (mConverterId != Converter.CURRENCY) {
                    ArrayList<Unit> units = new ArrayList<>(mConversions.getUnits(mConverterId));
                    UnitAdapter unitAdapter = new UnitAdapter(this, units);
                    mUnitFromSpinner.setAdapter(unitAdapter);
                    mUnitToSpinner.setAdapter(unitAdapter);
                } else {
                    mUnitFromSpinner.setAdapter(null);
                    mUnitToSpinner.setAdapter(null);
                    if (isFirstStartUp) {
                        loadDefaultPrefs(mConverterId); // only when currency is selected
                        isFirstStartUp = false;
                    } else {
                        mUnitFromId = Unit.BAHRAINI_DINAR;
                        mUnitToId = Unit.PAKISTANI_RUPEE;
                        textInputLayoutFrom.setHint("Bahraini Dinar");
                        textInputLayoutTo.setHint("Pakistani Rupee");
                        saveDefaultPrefs(mUnitFromId, "Bahraini Dinar", mBtnFrom.getId(), mConverterId);
                        saveDefaultPrefs(mUnitToId, "Pakistani Rupee", mBtnTo.getId(), mConverterId);
                    }
                    if (!isCurrencyDataLoaded())
                        mFromEditText.setEnabled(false);
                }
                //As converter is changed clear already entered values
                mFromEditText.setText("");
                mToEditText.setText("");
                //set Default pef
                prefManager.setIntegerPref(PrefManager.PREF_CONVERTER, mConverterId); //set position saved
                break;
            case R.id.unt_cntr_unit_from_spinner:
                Unit unit = (Unit) parent.getItemAtPosition(position);
                mUnitFromId = unit.getId();
                textInputLayoutFrom.setHint(getString(unit.getLabelResId())); //set hint of text i
                if (!isFirstStartUp)
                    saveDefaultPrefs(unit.getId(), getString(unit.getLabelResId()), mBtnFrom.getId(), mConverterId);
                //recalculate value for changed items
                convert();
                break;
            case R.id.unt_cntr_unit_to_spinner:
                unit = (Unit) parent.getItemAtPosition(position);
                mUnitToId = unit.getId();
                textInputLayoutTo.setHint(getString(unit.getLabelResId())); //set hint of text i
                if (!isFirstStartUp)
                    saveDefaultPrefs(unit.getId(), getString(unit.getLabelResId()), mBtnTo.getId(), mConverterId);
                else {
                    loadDefaultPrefs(mConverterId);
                    isFirstStartUp = false;
                }
                //recalculate value for changed items
                convert();
                break;
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onCurrencySelected(Unit unit, int viewId) {
        if (viewId == mBtnFrom.getId()) {
            mUnitFromId = unit.getId();
            textInputLayoutFrom.setHint(unit.getCurrency());
        } else {
            mUnitToId = unit.getId();
            textInputLayoutTo.setHint(unit.getCurrency());
        }
        saveDefaultPrefs(unit.getId(), unit.getCurrency(), viewId, mConverterId);
        convert();
    }

    @Override
    protected int getSelectedNavItem() {
        return R.id.nav_converter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mConversions = null;
        dialog = null;
        prefManager = null;
    }

    private void loadDefaultPrefs(int converterId) {
        switch (converterId) {
            case Converter.CURRENCY:
                mUnitFromId = prefManager.getIntegerPref(PrefManager.CURRENCY_FROM_ID, Unit.BAHRAINI_DINAR);
                mUnitToId = prefManager.getIntegerPref(PrefManager.CURRENCY_TO_ID, Unit.PAKISTANI_RUPEE);
                textInputLayoutFrom.setHint(prefManager.getStringPref(PrefManager.CURRENCY_FROM_LABEL, "Bahraini Dinar"));
                textInputLayoutTo.setHint(prefManager.getStringPref(PrefManager.CURRENCY_TO_LABEL, "Pakistani Rupee"));
                break;
            default:
                mUnitFromId = prefManager.getIntegerPref(PrefManager.FROM_UNIT_ID, Unit.KILOMETRE);
                mUnitToId = prefManager.getIntegerPref(PrefManager.TO_UNIT_ID, Unit.METRE);
                textInputLayoutFrom.setHint(prefManager.getStringPref(PrefManager.FROM_UNIT_LABEL, "Kilometre"));
                textInputLayoutTo.setHint(prefManager.getStringPref(PrefManager.TO_UNIT_LABEL, "Metre"));
                break;
        }
    }

    private void saveDefaultPrefs(@Unit.id int id, String label, int viewId, int converterId) {
        switch (viewId) {
            case R.id.unt_cntr_btn_spinner_from:
                if (converterId != Converter.CURRENCY) {
                    prefManager.setIntegerPref(PrefManager.FROM_UNIT_ID, id);
                    prefManager.setStringPref(PrefManager.FROM_UNIT_LABEL, label);
                } else {
                    prefManager.setIntegerPref(PrefManager.CURRENCY_FROM_ID, id);
                    prefManager.setStringPref(PrefManager.CURRENCY_FROM_LABEL, label);
                }

                break;
            case R.id.unt_cntr_btn_spinner_to:
                if (converterId != Converter.CURRENCY) {
                    prefManager.setStringPref(PrefManager.TO_UNIT_LABEL, label);
                    prefManager.setIntegerPref(PrefManager.TO_UNIT_ID, id);
                } else {
                    prefManager.setIntegerPref(PrefManager.CURRENCY_TO_ID, id);
                    prefManager.setStringPref(PrefManager.CURRENCY_TO_LABEL, label);
                }
                break;
        }
    }

    private boolean isCurrencyDataLoaded() {
        return mConversions.getById(Converter.CURRENCY).getUnitCount() > 0;
    }

    private  void updateCurrencies(){
        if(!Utils.isNetworkAvailable(this)){
            Utils.showToast(this, "Please check your network connectivity...");
            return;
        }
        String BASE_URL = "http://apilayer.net/api/";
        String ENDPOINT = "live";
        String key = prefManager.getStringPref(PrefManager.CURRENCY_API_KEY, null);
        String urlRates = BASE_URL + ENDPOINT + "?access_key=" + key;
        String urlCurrencies = "http://www.apilayer.net/api/list?access_key=" + key + "&format=1";
        ExchangeRates exchangeRates = new ExchangeRates(this, urlCurrencies, urlRates);
        exchangeRates.execute();
    }

    @Override
    public void onTaskCompleted(boolean error, String names[], String codes[], double rates[]) {
        if (error && !iteratedAllKeys) {
            String keys[] = getResources().getStringArray(R.array.Currency_api_keys);
            int i = prefManager.getIntegerPref(PrefManager.CURRENCY_KEY_COUNT, 0);
            if(i >= keys.length){
                iteratedAllKeys = true;
                Utils.showToast(this, "Failed to Update Currencies");
                prefManager.setIntegerPref(PrefManager.CURRENCY_KEY_COUNT, 0);
                return;
            }
            String key = keys[i];
            prefManager.setIntegerPref(PrefManager.CURRENCY_KEY_COUNT, i + 1);
            prefManager.setStringPref(PrefManager.CURRENCY_API_KEY, key);
            updateCurrencies();
        } else if(!error) {
            new DatabaseHelper(this).updateCurrencyData(names, rates, codes);
            mConversions.getCurrencyConversions();
            Calendar now = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy 'at' HH:mm a", Locale.US);
            prefManager.setStringPref(PrefManager.CURRENCY_UPDATE_DATE, "Exchange rates updated on " + sdf.format(now.getTime()));
            mExchangeRatesTv.setText(prefManager.getStringPref(PrefManager.CURRENCY_UPDATE_DATE, ""));
            Utils.showToast(this, "Currencies Updated!");
        }
    }
}