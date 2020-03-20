package com.prime.calculator;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.prime.calculator.Utility.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddSubtractDatesFragment extends DateDifferenceFragment
        implements OnCheckedChangeListener {

    private final Calendar mCalender = Calendar.getInstance();
    private EditText mDay, mMonth, mYear, mASDays, mASMonths, mASYears;
    private ImageButton mDatePicker;
    private TextView mResult;
    private Spinner mMonthSpinner;
    private RadioButton mRadioBtnAdd;
    private RadioGroup mRadioGroup;
    private int dayLimit;


    public AddSubtractDatesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_subtract_dates, container, false);
        initializeViews(view);
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mMonth.setText((String) parent.getItemAtPosition(position));
    }

    private void initializeViews(View view) {
        mDay = view.findViewById(R.id.AS_dates_day);
        mMonth = view.findViewById(R.id.AS_dates_month);
        mYear = view.findViewById(R.id.AS_dates_year);
        mDatePicker = view.findViewById(R.id.AS_dates_date_from_picker);
        mASDays = view.findViewById(R.id.AS_dates_days);
        mASMonths = view.findViewById(R.id.AS_dates_months);
        mASYears = view.findViewById(R.id.AS_dates_years);
        mResult = view.findViewById(R.id.AS_dates_result);
        mMonthSpinner = view.findViewById(R.id.AS_dates_spinner_monthFrom);
        mRadioBtnAdd = view.findViewById(R.id.AS_dates_radioButtonAdd);
        mRadioGroup = view.findViewById(R.id.AS_dates_add_sub_radio_group);

        //Set Default Values
        mDay.setText(String.valueOf(getDay(mCalender)));
        mMonth.setText(getMonth(mCalender));
        mYear.setText(String.valueOf(getYear(mCalender)));


        //Set Click Listener
        mDatePicker.setOnClickListener(this);
        mMonth.setOnClickListener(this);

        //Set Check Box State Change Listener
        mRadioGroup.setOnCheckedChangeListener(this);

        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(getContext(), R.array.months,
                        android.R.layout.simple_list_item_1);
        // Specify the layout to use when the list of choices appears
        // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mMonthSpinner.setAdapter(adapter);
        mMonthSpinner.setOnItemSelectedListener(this);
        mMonthSpinner.setSelection(adapter.getPosition(getMonth(mCalender)));
        //mMonth.setSelection(adapter.getPosition(getMonth(mCalender)));

        //set TextFilters
        mDay.setFilters(new InputFilter[]{new TextFilter(mDay.getId())});
        mYear.setFilters(new InputFilter[]{new TextFilter(mYear.getId())});
        mASDays.setFilters(new InputFilter[]{new TextFilter(mASDays.getId())});
        mASMonths.setFilters(new InputFilter[]{new TextFilter(mASMonths.getId())});
        mASYears.setFilters(new InputFilter[]{new TextFilter(mASYears.getId())});
        mDay.addTextChangedListener(new CustomTextWatcher(mDay.getId()));
        mMonth.addTextChangedListener(new CustomTextWatcher(mMonth.getId()));
        mYear.addTextChangedListener(new CustomTextWatcher(mYear.getId()));
        mASDays.addTextChangedListener(new CustomTextWatcher(mASDays.getId()));
        mASYears.addTextChangedListener(new CustomTextWatcher(mASYears.getId()));
        mASMonths.addTextChangedListener(new CustomTextWatcher(mASMonths.getId()));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.AS_dates_date_from_picker:
                updateDate();
                break;
            case R.id.AS_dates_month:
                mMonthSpinner.performClick();
                break;
        }
        ((DateCalculationActivity) getActivity()).hideKeyboard();//hide keyboard
    }

    private void updateDate() {
        final DatePickerDialog.OnDateSetListener date = (view, year, month, dayOfMonth) -> {
            // TODO Auto-generated method stub
            mCalender.set(Calendar.YEAR, year);
            mCalender.set(Calendar.MONTH, month);
            mCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            mDay.setText(String.valueOf(getDay(mCalender)));
            mMonth.setText(getMonth(mCalender));
            mYear.setText(String.valueOf(getYear(mCalender)));
        };
        new DatePickerDialog(getActivity(), date, mCalender.get(Calendar.YEAR),
                mCalender.get(Calendar.MONTH), mCalender.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void setResult(){
        String dateFrom = Utils.getString(mDay)
                + " "
                + Utils.getString(mMonth)
                + " "
                + Utils.getString(mYear);
        mResult.setText(getResult(dateFrom));
    }

    private String getResult(String dateFrom) {
        char op;
        if(mRadioBtnAdd.isChecked())
            op = '+';
        else
            op = '-';
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        Date date = null;
        try {
            date = simpleDateFormat.parse(dateFrom);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date == null) {
            Utils.showToast(getActivity(), "Format Date As: 26 Jan 1993", Utils.TOAST_BELOW_TABS);
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);

        int days = !Double.isNaN(Utils.getDouble(mASDays)) ?(int)Utils.getDouble(mASDays) : 0;
        int months = !Double.isNaN(Utils.getDouble(mASMonths)) ?(int)Utils.getDouble(mASMonths) : 0;
        int years = !Double.isNaN(Utils.getDouble(mASYears)) ?(int)Utils.getDouble(mASYears) : 0;
        switch (op){
            case '+':
                calendar.add(Calendar.DATE, days);
                calendar.add(Calendar.MONTH, months);
                calendar.add(Calendar.YEAR, years);
                break;
            case '-':
                calendar.add(Calendar.DATE, -days);
                calendar.add(Calendar.MONTH, -months);
                calendar.add(Calendar.YEAR, -years);
        }
        simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy G", Locale.US);

        return simpleDateFormat.format(calendar.getTime());
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        setResult();
    }

    private class TextFilter implements InputFilter{
        int ID;
        TextFilter(int ID){
            this.ID = ID;
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            int input = (int) Utils.getDouble(dest.toString() + source.toString()); //total text in editText day
            switch (ID) {
                case R.id.AS_dates_day:
                    if(input > dayLimit || input < 1)
                    {
                        Utils.showToast(getContext(), "Please Select Value Between 1 & " + dayLimit, Utils.TOAST_BELOW_TABS);
                        return "";
                    }
                    break;
                case R.id.AS_dates_year:
                case R.id.AS_dates_years:
                    if(input > 9999 || input < 1)
                    {
                        Utils.showToast(getContext(), "Please Select Value Between 1 & " + 9999, Utils.TOAST_BELOW_TABS);
                        return "";
                    }
                    break;
                case R.id.AS_dates_days:
                    if(input > 365 || input < 1)
                    {
                        Utils.showToast(getContext(), "Please Select Value Between 1 & " + 365, Utils.TOAST_BELOW_TABS);
                        return "";
                    }
                    break;
                case R.id.AS_dates_months:
                    if(input > 12 || input < 1)
                    {
                        Utils.showToast(getContext(), "Please Select Value Between 1 & " + 12, Utils.TOAST_BELOW_TABS);
                        return "";
                    }
                    break;
            }
            return null;
        }
    }


    private class CustomTextWatcher implements TextWatcher{
        int ID;
        CustomTextWatcher(int ID){
            this.ID = ID;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            switch (ID) {
                case R.id.AS_dates_month:
                case R.id.AS_dates_year:
                    mDay.setFilters(new InputFilter[]{new TextFilter(mDay.getId())});
                    dayLimit = getMaxAllowedDays(Utils.getString(mMonth), (int) Utils.getDouble(mYear));
                    switch ((int) Utils.getDouble(mDay)) {
                        case 31:
                        case 28:
                        case 29:
                        case 30:
                            mDay.setText(String.valueOf(dayLimit));
                    }
                    break;
            }

        }

        @Override
        public void afterTextChanged(Editable s) {
            setResult();
        }
    }
}
