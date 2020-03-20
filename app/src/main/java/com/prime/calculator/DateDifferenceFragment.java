package com.prime.calculator;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.prime.calculator.Utility.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class DateDifferenceFragment extends Fragment implements
        OnClickListener, OnItemSelectedListener {

    private static final int EXTENDED_DIFFERENCE = 0;
    private static final int ALTERNATE_UNITS = 1;

    private final Calendar mCalender = Calendar.getInstance();
    private TextView mDifferenceTv, mAlternativeUnitsTv, mAlternativeUnitsTitleTv;
    private EditText mDayFromEt, mDayToEt, mMonthFromEt, mMonthToEt, mYearFromEt, mYearToEt, mTimeFromEt, mTimeToEt;
    private Spinner mMonthFromSpinner, mMonthToSpinner;
    private int mViewID;
    private int dayFromLimit, dayToLimit;

    public DateDifferenceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_date_difference, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view) {
        mDifferenceTv = view.findViewById(R.id.date_diff_date_difference_extended);
        mAlternativeUnitsTv = view.findViewById(R.id.date_diff_date_alternative_units);
        mDayFromEt = view.findViewById(R.id.date_diff_day_from);
        mDayToEt = view.findViewById(R.id.date_diff_day_to);
        mMonthFromEt = view.findViewById(R.id.date_diff_month_from);
        mMonthToEt = view.findViewById(R.id.date_diff_month_to);
        mYearFromEt = view.findViewById(R.id.date_diff_year_from);
        mYearToEt = view.findViewById(R.id.date_diff_year_to);
        mTimeFromEt = view.findViewById(R.id.date_diff_time_from);
        mTimeToEt = view.findViewById(R.id.date_diff_time_to);
        mAlternativeUnitsTitleTv = view.findViewById(R.id.date_diff_in_alternative_units_title);
        ImageButton mDateFromPicker = view.findViewById(R.id.date_diff_date_from_picker);
        ImageButton mDateToPicker = view.findViewById(R.id.date_diff_date_to_picker);
        ImageButton mTimeFromPicker = view.findViewById(R.id.date_diff_time_from_picker);
        ImageButton mTimeToPicker = view.findViewById(R.id.date_diff_time_to_picker);
        mMonthFromSpinner = view.findViewById(R.id.date_diff_spinner_monthFrom);
        mMonthToSpinner = view.findViewById(R.id.date_diff_spinner_monthTo);

        //Set Default values of EditText's
        mDayFromEt.setText(String.valueOf(getDay(mCalender)));
        mDayToEt.setText(String.valueOf(getDay(mCalender)));
        mMonthFromEt.setText(getMonth(mCalender));
        mMonthToEt.setText(getMonth(mCalender));
        mTimeFromEt.setText(getTime(mCalender));
        mTimeToEt.setText(getTime(mCalender));
        mYearFromEt.setText(String.valueOf(getYear(mCalender)));
        mYearToEt.setText(String.valueOf(getYear(mCalender)));

        //Set Click Listeners to Pickers
        mDateFromPicker.setOnClickListener(this);
        mDateToPicker.setOnClickListener(this);
        mTimeToPicker.setOnClickListener(this);
        mTimeFromPicker.setOnClickListener(this);
        mMonthFromEt.setOnClickListener(this);
        mMonthToEt.setOnClickListener(this);

        //Add Text Change Listener To EditTexts
        mDayFromEt.addTextChangedListener(new CustomTextWatcher(mDayFromEt.getId()));
        mMonthFromEt.addTextChangedListener(new CustomTextWatcher(mMonthFromEt.getId()));
        mYearFromEt.addTextChangedListener(new CustomTextWatcher(mYearFromEt.getId()));
        mTimeFromEt.addTextChangedListener(new CustomTextWatcher(mTimeFromEt.getId()));
        mDayToEt.addTextChangedListener(new CustomTextWatcher(mDayToEt.getId()));
        mMonthToEt.addTextChangedListener(new CustomTextWatcher(mMonthToEt.getId()));
        mYearToEt.addTextChangedListener(new CustomTextWatcher(mYearToEt.getId()));
        mTimeToEt.addTextChangedListener(new CustomTextWatcher(mTimeToEt.getId()));

        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(getContext(), R.array.months,
                        android.R.layout.simple_list_item_1);
        // Specify the layout to use when the list of choices appears
        // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mMonthFromSpinner.setAdapter(adapter);
        mMonthToSpinner.setAdapter(adapter);
        mMonthFromSpinner.setOnItemSelectedListener(this);
        mMonthToSpinner.setOnItemSelectedListener(this);
        //set Default Selection of Spinners Current Month
        mMonthFromSpinner.setSelection(adapter.getPosition(getMonth(mCalender)));
        mMonthToSpinner.setSelection(adapter.getPosition(getMonth(mCalender)));

        //Set Limits To Day Field
        //for that Particular selected Month
        dayFromLimit = getMaxAllowedDays(getMonth(mCalender), getYear(mCalender));
        dayToLimit = getMaxAllowedDays(getMonth(mCalender), getYear(mCalender));

        mDayFromEt.setFilters(new InputFilter[]{new TextFilter(mDayFromEt.getId())});
        mDayToEt.setFilters(new InputFilter[]{new TextFilter(mDayToEt.getId())});
        mYearFromEt.setFilters(new InputFilter[]{new TextFilter(mYearFromEt.getId())});
        mYearToEt.setFilters(new InputFilter[]{new TextFilter(mYearToEt.getId())});
    }

    /**
     * This Function Returns difference between two dates in String Format
     *
     * @param unit  : Alternate Unit and Extended Unit
     * @param date1 : Start Date
     * @param date2 : Ending Date
     * @return Result :depends on Unit
     */
    private String getDiffIn(int unit, String date1, String date2) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy h:mm:ss a", Locale.US);
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = simpleDateFormat.parse(date1);
            d2 = simpleDateFormat.parse(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (d1 == null || d2 == null) {
            Utils.showToast(getActivity(), "Format Date As: 26 Jan 1993 1:00:00", Utils.TOAST_BELOW_TABS);
            return "";
        }

        Calendar dateFrom = Calendar.getInstance();
        dateFrom.clear();
        dateFrom.setTime(d1);
        Calendar dateTo = Calendar.getInstance();
        dateTo.clear();
        dateTo.setTime(d2);
        long diff = dateFrom.getTimeInMillis() - dateTo.getTimeInMillis();
        long diffYears = Math.abs(dateFrom.get(Calendar.YEAR) - dateTo.get(Calendar.YEAR));
        long diffMonths = Math.abs(dateFrom.get(Calendar.MONTH) - dateTo.get(Calendar.MONTH));
        long diffDays = Math.abs(dateFrom.get(Calendar.DAY_OF_MONTH) - dateTo.get(Calendar.DAY_OF_MONTH));
        long diffWeeks = diffDays / 7;
        diffDays = diffDays - diffWeeks * 7;
        long diffHours = Math.abs(dateFrom.get(Calendar.HOUR_OF_DAY) - dateTo.get(Calendar.HOUR_OF_DAY));
        long diffMinutes = Math.abs(dateFrom.get(Calendar.MINUTE) - dateTo.get(Calendar.MINUTE));
        long diffSeconds = Math.abs(dateFrom.get(Calendar.SECOND) - dateTo.get(Calendar.SECOND));

        if (diffSeconds == 0 && diffMinutes == 0 && diffHours == 0 && diffDays == 0 && diffWeeks == 0
                && diffMonths == 0 && diffYears == 0 && unit == EXTENDED_DIFFERENCE) {
            return "Same Dates";
        } else if (diffSeconds == 0 && diffMinutes == 0 && diffHours == 0 && diffDays == 0 && diffWeeks == 0
                && diffMonths == 0 && diffYears == 0 && unit == ALTERNATE_UNITS) {
            mAlternativeUnitsTitleTv.setVisibility(View.GONE);
            return "";
        } else
            mAlternativeUnitsTitleTv.setVisibility(View.VISIBLE);
        if (unit == EXTENDED_DIFFERENCE) {
            StringBuilder exDiff = new StringBuilder();
            if (diffYears != 0 && diffYears == 1)
                exDiff.append(" ").append(String.valueOf(diffYears)).append(" Year,");
            else if (diffYears > 1)
                exDiff.append(" ").append(String.valueOf(diffYears)).append(" Years,");
            if (diffMonths != 0 && diffMonths == 1)
                exDiff.append(" ").append(String.valueOf(diffMonths)).append(" Month,");
            else if (diffMonths > 1)
                exDiff.append(" ").append(String.valueOf(diffMonths)).append(" Months,");
            if (diffWeeks != 0 && diffWeeks == 1)
                exDiff.append(" ").append(String.valueOf(diffWeeks)).append(" Week,");
            else if (diffWeeks > 1)
                exDiff.append(" ").append(String.valueOf(diffWeeks)).append(" Weeks,");
            if (diffDays != 0 && diffDays == 1)
                exDiff.append(" ").append(String.valueOf(diffDays)).append(" Day,");
            else if (diffDays > 1)
                exDiff.append(" ").append(String.valueOf(diffDays)).append(" Days,");
            if (diffHours != 0 && diffHours == 1)
                exDiff.append(" ").append(String.valueOf(diffHours)).append(" Hour,");
            else if (diffHours > 1)
                exDiff.append(" ").append(String.valueOf(diffHours)).append(" Hours,");
            if (diffMinutes != 0 && diffMinutes == 1)
                exDiff.append(" ").append(String.valueOf(diffMinutes)).append(" Minute,");
            else if (diffMinutes > 1)
                exDiff.append(" ").append(String.valueOf(diffMinutes)).append(" Minutes,");
            if (diffSeconds != 0 && diffSeconds == 1)
                exDiff.append(" ").append(String.valueOf(diffSeconds)).append(" Second");
            else if (diffSeconds > 1)
                exDiff.append(" ").append(String.valueOf(diffSeconds)).append(" Seconds");
            int i = exDiff.length() - 1;
            while (i >= 0) {
                if (exDiff.charAt(i) == ',' && i == exDiff.length() - 1)
                    exDiff.deleteCharAt(i--);
                if (exDiff.charAt(i) == ',') {
                    exDiff.deleteCharAt(i);
                    exDiff.insert(i, " &");
                    break;
                }
                i--;
            }
            return exDiff.toString();
        } else if (unit == ALTERNATE_UNITS) {
            long inSeconds = TimeUnit.MILLISECONDS.toSeconds(Math.abs(diff));
            long inMinutes = TimeUnit.SECONDS.toMinutes(inSeconds);
            long inHours = TimeUnit.MINUTES.toHours(inMinutes);
            long inDays = TimeUnit.HOURS.toDays(inHours);
            long inWeeks = inDays / 7;
            long inMonths = Math.abs((dateFrom.get(Calendar.YEAR) * 12 + dateFrom.get(Calendar.MONTH))
                    - (dateTo.get(Calendar.YEAR) * 12 + dateTo.get(Calendar.MONTH)));
            long inYears = Math.abs(dateFrom.get(Calendar.YEAR) - (dateTo.get(Calendar.YEAR)));
            String altUnits = "";
            if (inSeconds != 0)
                altUnits = altUnits + "Seconds : " + Utils.formatDecimal(inSeconds, 18);
            if (inMinutes != 0)
                altUnits = altUnits + "\nMinutes : " + Utils.formatDecimal(inMinutes, 18);
            if (inHours != 0)
                altUnits = altUnits + "\nHours : " + Utils.formatDecimal(inHours, 18);
            if (inDays != 0)
                altUnits = altUnits + "\nDays : " + Utils.formatDecimal(inDays, 18);
            if (inWeeks != 0)
                altUnits = altUnits + "\nWeeks : " + Utils.formatDecimal(inWeeks, 18);
            if (inMonths != 0)
                altUnits = altUnits + "\nMonths : " + Utils.formatDecimal(inMonths, 18);
            if (inYears != 0)
                altUnits = altUnits + "\nYears : " + Utils.formatDecimal(inYears,18);
            return altUnits;
        }
        return "";
    }

    /**
     * This Function calculates difference between two dates and sets it to Extended Text View and
     * Alternate Units Text View.
     */
    private void setResult() {
        String dateFrom = mDayFromEt.getText().toString()
                + " "
                + mMonthFromEt.getText().toString()
                + " "
                + mYearFromEt.getText().toString()
                + " "
                + mTimeFromEt.getText().toString();

        String dateTo = mDayToEt.getText().toString()
                + " "
                + mMonthToEt.getText().toString()
                + " "
                + mYearToEt.getText().toString()
                + " "
                + mTimeToEt.getText().toString();
        mDifferenceTv.setText(getDiffIn(EXTENDED_DIFFERENCE, dateFrom, dateTo));
        mAlternativeUnitsTv.setText(getDiffIn(ALTERNATE_UNITS, dateFrom, dateTo));
    }

    /**
     * This Function Sets Max number of Days Allowed For a Perticular Month
     *
     * @param Month
     * @param Year
     */
    protected int getMaxAllowedDays(String Month, int Year) {
        switch (Month) {
            case "Jan":
            case "Mar":
            case "May":
            case "Jul":
            case "Aug":
            case "Oct":
            case "Dec":
                return 31;
            case "Apr":
            case "Jun":
            case "Sep":
            case "Nov":
                return 30;
            default:
                if (isLeapYear(Year))
                    return 29;
                else
                    return 28;
        }
    }

    /**
     * This Function Returns weather provided year is leap year or not
     *
     * @param year : That which is To be checked
     * @return true if leap year otherwise false
     */
    private boolean isLeapYear(int year) {
        if (year % 4 == 0) {
            if (year % 100 == 0) {
                if (year % 400 == 0)
                    return true;
                else
                    return false;
            } else
                return true;
        }
        return false;
    }

    /**
     * This Function returns year Field from a Calender Instance
     *
     * @return year mCalender.getTime() in yyyy format e.g., 1993
     */
    protected int getYear(Calendar c) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy", Locale.US);
        int year = -1;
        try {
            year = Integer.parseInt(simpleDateFormat.format(c.getTime()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return year;
    }

    /**
     * This Function returns Time Fields from a Calender Instance
     *
     * @return Time in mCalender.getTime() h:mm:ss a pattern e.g., 1:00:00 AM
     */
    protected String getTime(Calendar c) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm:ss a", Locale.US);
        return String.valueOf(simpleDateFormat.format(c.getTime()));
    }

    /**
     * This Function returns Month Fields from a Calender Instance
     *
     * @return mCalender.getTime()) in MMM e.g., Jan
     */
    protected String getMonth(Calendar c) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM", Locale.US);
        return String.valueOf(simpleDateFormat.format(c.getTime()));
    }

    /**
     * This Function returns Day Fields from a Calender Instance
     *
     * @return mCalender.getTime()) in dd e.g., 01
     */
    protected int getDay(Calendar c) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd", Locale.US);
        int day = -1;
        try {
            day = Integer.parseInt(simpleDateFormat.format(c.getTime()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return day;
    }


    @Override
    public void onClick(View view) {
        mViewID = view.getId();
        switch (view.getId()) {
            case R.id.date_diff_date_from_picker:
            case R.id.date_diff_date_to_picker:
                updateDate();
                break;
            case R.id.date_diff_time_from_picker:
            case R.id.date_diff_time_to_picker:
                updateTime();
                break;
            case R.id.date_diff_month_from:
                mMonthFromSpinner.performClick();
                break;
            case R.id.date_diff_month_to:
                mMonthToSpinner.performClick();
                break;
        }
        //hide keyboard when clicked on dialogue or month button
        ((DateCalculationActivity) getActivity()).hideKeyboard();
    }

    /**
     * This Function gets Time selected from TimePicker and sets it to EditTexts
     */
    private void updateTime() {
        final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mCalender.set(Calendar.HOUR_OF_DAY, hourOfDay);
                mCalender.set(Calendar.MINUTE, minute);
                mCalender.set(Calendar.SECOND, 00);
                switch (mViewID) {
                    case R.id.date_diff_time_from_picker:
                        mTimeFromEt.setText(getTime(mCalender));
                        break;
                    case R.id.date_diff_time_to_picker:
                        mTimeToEt.setText(getTime(mCalender));
                        break;
                }
            }
        };
        new TimePickerDialog(getActivity(), time,
                mCalender.get(Calendar.HOUR_OF_DAY), mCalender.get(Calendar.MINUTE), false).show();
    }

    /**
     * This Function gets Date selected from datePicker and sets it to EditTexts
     */
    private void updateDate() {
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // TODO Auto-generated method stub
                mCalender.set(Calendar.YEAR, year);
                mCalender.set(Calendar.MONTH, month);
                mCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                switch (mViewID) {
                    case R.id.date_diff_date_from_picker:
                        mDayFromEt.setText(String.valueOf(getDay(mCalender)));
                        mMonthFromEt.setText(getMonth(mCalender));
                        mYearFromEt.setText(String.valueOf(getYear(mCalender)));
                        break;
                    case R.id.date_diff_date_to_picker:
                        mDayToEt.setText(String.valueOf(getDay(mCalender)));
                        mMonthToEt.setText(getMonth(mCalender));
                        mYearToEt.setText(String.valueOf(getYear(mCalender)));
                        break;
                }
            }
        };
        new DatePickerDialog(getActivity(), date, mCalender.get(Calendar.YEAR),
                mCalender.get(Calendar.MONTH), mCalender.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (mViewID) {
            case R.id.date_diff_month_from:
                mMonthFromEt.setText((String) parent.getItemAtPosition(position));
                break;
            case R.id.date_diff_month_to:
                mMonthToEt.setText((String) parent.getItemAtPosition(position));
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class CustomTextWatcher implements TextWatcher {
        private int ID;

        CustomTextWatcher(int ID) {
            this.ID = ID;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            switch (ID) {
                case R.id.date_diff_month_from:
                case R.id.date_diff_year_from:
                    mDayFromEt.setFilters(new InputFilter[]{new TextFilter(mDayFromEt.getId())});
                    dayFromLimit = getMaxAllowedDays(Utils.getString(mMonthFromEt), (int) Utils.getDouble(mYearFromEt));
                    switch ((int) Utils.getDouble(mDayFromEt)) {
                        case 31:
                        case 28:
                        case 29:
                        case 30:
                            mDayFromEt.setText(String.valueOf(dayFromLimit));
                    }
                    break;
                case R.id.date_diff_month_to:
                case R.id.date_diff_year_to:
                    mDayToEt.setFilters(new InputFilter[]{new TextFilter(mDayToEt.getId())});
                    dayToLimit = getMaxAllowedDays(Utils.getString(mMonthToEt), (int) Utils.getDouble(mYearToEt));
                    switch ((int) Utils.getDouble(mDayToEt)) {
                        case 31:
                        case 28:
                        case 29:
                        case 30:
                            mDayToEt.setText(String.valueOf(dayToLimit));
                    }
                    break;
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            setResult();
        }
    }

    private class TextFilter implements InputFilter {
        private int ID;

        TextFilter(int ID) {
            this.ID = ID;
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            int input = (int) Utils.getDouble(dest.toString() + source.toString()); //total text in editText day
            switch (ID) {
                case R.id.date_diff_day_from:
                    if (input > dayFromLimit || input == 0) {
                        Utils.showToast(getContext(), "Please Select Value Between 1 & " + dayFromLimit, Utils.TOAST_BELOW_TABS);
                        return "";
                    }
                    break;
                case R.id.date_diff_day_to:
                    if (input > dayToLimit || input == 0) {
                        Utils.showToast(getContext(), "Please Select Value Between 1 & " + dayToLimit, Utils.TOAST_BELOW_TABS);
                        return "";
                    }
                    break;
                case R.id.date_diff_year_from:
                case R.id.date_diff_year_to:
                    if (input > 9999 || input < 1) {
                        Utils.showToast(getContext(), "Please Select Value Between 1 & " + 9999, Utils.TOAST_BELOW_TABS);
                        return "";
                    }
                    break;
            }
            return null;
        }
    }
}
