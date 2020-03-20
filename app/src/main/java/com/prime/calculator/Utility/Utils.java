package com.prime.calculator.Utility;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.prime.calculator.R;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * This Class is Used to Store Some of Utility Functions to Calculator
 * It Almost Stores Functions which are common Among Activities, Fragments etc.
 */

public class Utils {
    /**
     * Map key id to corresponding (internationalized) display string.
     * Pure function.
     */
    public static final char OP_SUB = '-';
    public static final char DECIMAL_POINT = '.';
    // Used to keep a reference to the cursor in text
    public static final char SELECTION_HANDLE = '\u2620';
    public static final int MODE_DEG = 1;
    public static final int MODE_RAD = 0;
    public static final int MODE_GRAD = 2;
    public static final String SQRT_PLACE_HOLDER = "§";
    public static final String CBRT_PLACE_HOLDER = "#";
    public static final String ROOT_PLACE_HOLDER = "$";
    public static final char THOUSAND_SEPARATOR_THIN_SPACE = '\u2009';
    public static final char THOUSAND_SEPARATOR_COMMA = ',';
    public static final char STANDARD_THOUSAND_SEPARATOR = '\'';
    /**
     * The maximum number of significant digits to display.
     */
    public static final int MAX_DIGITS = 15;
    /**
     * A {@link Double} has at least 17 significant digits, we show the first {@link #MAX_DIGITS}
     * and use the remaining digits as guard digits to hide floating point precision errors.
     */
    public static final int ROUNDING_DIGITS = Math.max(17 - MAX_DIGITS, 0);
    /**
     * ACTIVITY TAGS
     */
    public static final double EPSILON = 0.00000000001;
    public static final int TOAST_BELOW_TABS = 325;
    private static final int FLOAT_PRECISION = -1;


    //Tost Position
    private static final int LEN_UNLIMITED = 100;

    /**
     * This method returns the text associated with button id
     *
     * @param context Application Context
     * @param id      id of button
     * @return String Corresponding with id
     */
    public static String toString(Context context, int id) {
        switch (id) {
            //Constants
            case R.id.const_pi:
                return context.getString(R.string.const_pi);
            case R.id.const_e:
                return context.getString(R.string.const_e);
            case R.id.const_phi:
                return context.getString(R.string.const_phi);
            //Pre Unary Operators
            case R.id.op_sqrt:
                return context.getString(R.string.op_sqrt);
            case R.id.op_cbrt:
                return context.getString(R.string.op_cbrt);
            //Post Unary Operators
            case R.id.op_fact:
                return context.getString(R.string.op_fact);
            case R.id.op_pct:
                return context.getString(R.string.op_pct);
            //Binary Operators
            case R.id.op_mul:
                return context.getString(R.string.op_mul);
            case R.id.op_div:
                return context.getString(R.string.op_div);
            case R.id.op_add:
                return context.getString(R.string.op_add);
            case R.id.op_sub:
                return context.getString(R.string.op_sub);
            case R.id.op_mod:
                return context.getString(R.string.op_mod);
            case R.id.op_sqr:
                return context.getString(R.string.op_sqr);
            case R.id.op_cube:
                return context.getString(R.string.op_cube);
            case R.id.op_pow:
                return context.getString(R.string.op_pow);
            case R.id.op_root:
                return context.getString(R.string.op_root);
            //Functions With Single Argument
            //Trigonometric Functions
            case R.id.fun_sin:
                return context.getString(R.string.fun_sin) + context.getString(R.string.lparen);
            case R.id.fun_cos:
                return context.getString(R.string.fun_cos) + context.getString(R.string.lparen);
            case R.id.fun_tan:
                return context.getString(R.string.fun_tan) + context.getString(R.string.lparen);
            //Inverse Trigonometric Functions
            case R.id.fun_arcsin:
                return context.getString(R.string.fun_arcsin) + context.getString(R.string.lparen);
            case R.id.fun_arccos:
                return context.getString(R.string.fun_arccos) + context.getString(R.string.lparen);
            case R.id.fun_arctan:
                return context.getString(R.string.fun_arctan) + context.getString(R.string.lparen);
            //Logarithmic Functions
            case R.id.fun_log_base_2:
                return context.getString(R.string.fun_log_base_2) + context.getString(R.string.lparen);
            case R.id.fun_log_base_10:
                return context.getString(R.string.fun_log_base_10) + context.getString(R.string.lparen);
            case R.id.fun_log_base_e:
                return context.getString(R.string.fun_log_base_e) + context.getString(R.string.lparen);
            //Functions With Double Argument
            case R.id.fun_log_base_n:
                return context.getString(R.string.fun_log_base_n) + context.getString(R.string.lparen);
            //Parenthesis
            case R.id.lparen:
                return context.getString(R.string.lparen);
            case R.id.rparen:
                return context.getString(R.string.rparen);
            //Decimal Point & Digits
            case R.id.dec_point:
                return context.getString(R.string.dec_point);
            case R.id.digit_0:
                return context.getString(R.string.digit_0);
            case R.id.digit_1:
                return context.getString(R.string.digit_1);
            case R.id.digit_2:
                return context.getString(R.string.digit_2);
            case R.id.digit_3:
                return context.getString(R.string.digit_3);
            case R.id.digit_4:
                return context.getString(R.string.digit_4);
            case R.id.digit_5:
                return context.getString(R.string.digit_5);
            case R.id.digit_6:
                return context.getString(R.string.digit_6);
            case R.id.digit_7:
                return context.getString(R.string.digit_7);
            case R.id.digit_8:
                return context.getString(R.string.digit_8);
            case R.id.digit_9:
                return context.getString(R.string.digit_9);
            default:
                return "";
        }
    }

    /**
     * Return a copy of the supplied string with commas added every three digits.
     * If Supplied String is Whole Number Then Commas Are added Upto End Otherwise Upto Index Of Decimal Point
     * Before Adding Commas Previous Commas Are removed
     * Inserting a digit separator every 3 digits appears to be
     * at least somewhat acceptable, though not necessarily preferred, everywhere.
     * The grouping separator in the result is NOT localized.
     */
    public static String addThousandSeparators(String s, char symbol) {
        // Resist the temptation to use Java's NumberFormat, which converts to long or double
        // and hence doesn't handle very large numbers.
        if (s.length() == 2)
            return s;
        StringBuilder result = new StringBuilder();
        /*Removing Commas if Any*/
        s = s.replaceAll(String.valueOf(symbol), "");
        int begin = 0, end = s.length();
        int current = begin;
        /*while (current < end && (s.charAt(current) == '-' || s.charAt(current) == ' ')) {
            ++current;
        }*/
        /*Removing Front Zeros if any
         * Remove */
        boolean dotSaw = false;
        boolean digitSaw = false;
        boolean isDotBeforeSH;
        if (s.indexOf(Utils.SELECTION_HANDLE) > 1)
            isDotBeforeSH = s.charAt(s.indexOf(Utils.SELECTION_HANDLE) - 1) == '.';
        else
            isDotBeforeSH = false;
        while (current < end) {
            if (isDigit(s.charAt(current)) && s.charAt(current) != '0' && !digitSaw)
                digitSaw = true;
            if (s.charAt(current) == Utils.DECIMAL_POINT && !dotSaw)
                dotSaw = true;
            if (s.charAt(current) == '0' && !dotSaw && !digitSaw) {
                current++;
                continue;
            }
            if (s.charAt(current) == Utils.DECIMAL_POINT && isDotBeforeSH &&
                    current != s.indexOf(Utils.SELECTION_HANDLE) - 1) {
                current++;
                continue;
            }
            result.append(s.charAt(current));
            current++;
        }
        int indexOfDot;
        s = result.toString();
        end = s.length();
        result.delete(begin, end); //Reinitialize Result;
        current = begin;
        /*
         * Before Reading Commas, we Have to exclude Utils.SELECTION_HANDLE from the Process but still
         * place it inside String in order to Preserve Location of SelectionHandle
         */
        if(s.contains(String.valueOf(SELECTION_HANDLE)))
        {
            if(s.contains(".")){
                if(s.indexOf(Utils.SELECTION_HANDLE) < s.indexOf('.'))
                    indexOfDot = s.indexOf(".") - 1;
                else
                    indexOfDot = s.indexOf(".");
            }else
                indexOfDot = end - 1;
        }else{
            if(s.contains("."))
                indexOfDot = s.indexOf(".");
            else
                indexOfDot = end;
        }
        while (current < end) {
            result.append(s.charAt(current));
            if (s.charAt(current) == Utils.SELECTION_HANDLE || s.charAt(current) == OP_SUB) {
                current++;
                continue;
            }
            ++current;
            if ((indexOfDot - current) % 3 == 0 && indexOfDot > current) {
                result.append(symbol);
            }
        }
        return result.toString();
    }


    /**
     * Rounds by dropping roundingDigits of double precision
     * <p>
     * (similar to 'hidden precision digits' on calculators),
     * <p>
     * and formats to String.
     *
     * @param v              the value to be converted to String
     * @param roundingDigits the number of 'hidden precision' digits (e.g. 2).
     * @return a String representation of v
     */

    public static String doubleToString(final double v, final int roundingDigits) {
        final double absv = Math.abs(v);
        final String str = roundingDigits == FLOAT_PRECISION ? Float.toString((float) absv) : Double.toString(absv);
        StringBuffer buf = new StringBuffer(str);
        int roundingStart = (roundingDigits <= 0 || roundingDigits > 13) ? 17 : (16 - roundingDigits);
        int ePos = str.lastIndexOf('E');
        int exp = (ePos != -1) ? Integer.parseInt(str.substring(ePos + 1)) : 0;
        if (ePos != -1) {
            buf.setLength(ePos);
        }
        int len = buf.length();
        //remove dot
        int dotPos;
        for (dotPos = 0; dotPos < len && buf.charAt(dotPos) != '.'; ) {
            ++dotPos;
        }
        exp += dotPos;
        if (dotPos < len) {
            buf.deleteCharAt(dotPos);
            --len;
        }
        //round
        for (int p = 0; p < len && buf.charAt(p) == '0'; ++p) {
            ++roundingStart;
        }
        if (roundingStart < len) {
            if (buf.charAt(roundingStart) >= '5') {
                int p;
                for (p = roundingStart - 1; p >= 0 && buf.charAt(p) == '9'; --p) {
                    buf.setCharAt(p, '0');
                }
                if (p >= 0) {
                    buf.setCharAt(p, (char) (buf.charAt(p) + 1));
                } else {
                    buf.insert(0, '1');
                    ++roundingStart;
                    ++exp;
                }
            }
            buf.setLength(roundingStart);
        }
        //re-insert dot
        if ((exp < -5) || (exp > 10)) {
            buf.insert(1, '.');
            --exp;
        } else {
            for (int i = len; i < exp; ++i) {
                buf.append('0');
            }
            for (int i = exp; i <= 0; ++i) {
                buf.insert(0, '0');
            }
            buf.insert((exp <= 0) ? 1 : exp, '.');
            exp = 0;
        }
        len = buf.length();
        //remove trailing dot and 0s.
        int tail;
        for (tail = len - 1; tail >= 0 && buf.charAt(tail) == '0'; --tail) {
            buf.deleteCharAt(tail);
        }
        if (tail >= 0 && buf.charAt(tail) == '.') {
            buf.deleteCharAt(tail);
        }
        if (exp != 0) {
            buf.append('E').append(exp);
        }
        if (v < 0) {
            buf.insert(0, '-');
        }
        return buf.toString();
    }

    /**
     * Renders a real number to a String (for user display).
     *
     * @param maxLen   the maximum total length of the resulting string
     * @param rounding the number of final digits to round
     */
    public static String doubleToString(double x, int maxLen, int rounding) {
        return sizeTruncate(doubleToString(x, rounding), maxLen);
    }

    /**
     * Returns an approximation with no more than maxLen chars.
     * This method is not public, it is called through doubleToString,
     * that's why we can make some assumptions about the format of the string,
     * such as assuming that the exponent 'E' is upper-case.
     *
     * @param str    the value to truncate (e.g. "-2.898983455E20")
     * @param maxLen the maximum number of characters in the returned string
     * @return a truncation no longer then maxLen (e.g. "-2.8E20" for maxLen=7).
     */

    public static String sizeTruncate(String str, int maxLen) {
        if (maxLen == LEN_UNLIMITED) {
            return str;
        }
        int ePos = str.lastIndexOf('E');
        String tail = (ePos != -1) ? str.substring(ePos) : "";
        int tailLen = tail.length();
        int headLen = str.length() - tailLen;
        int maxHeadLen = maxLen - tailLen;
        int keepLen = Math.min(headLen, maxHeadLen);
        if (keepLen < 1 || (keepLen < 2 && str.length() > 0 && str.charAt(0) == '-')) {
            return str; // impossible to truncate
        }
        int dotPos = str.indexOf('.');
        if (dotPos == -1) {
            dotPos = headLen;
        }
        if (dotPos > keepLen) {
            int exponent = (ePos != -1) ? Integer.parseInt(str.substring(ePos + 1)) : 0;
            int start = str.charAt(0) == '-' ? 1 : 0;
            exponent += dotPos - start - 1;
            String newStr = str.substring(0, start + 1) + '.' + str.substring(start + 1, headLen) + 'E' + exponent;
            return sizeTruncate(newStr, maxLen);
        }
        return str.substring(0, keepLen) + tail;
    }


    /**
     * This Method Balances parentheses of expression
     *
     * @param s: Expression as String
     * @return A balanced expression as String
     */
    public static String getBalanced(String s) {
        int BalanceFactor = 0;
        StringBuilder result = new StringBuilder();
        result.append(s);
        for (int i = 0; i <= result.length() - 1; i++) {
            if (result.charAt(i) == '(')
                BalanceFactor++;
            else if (result.charAt(i) == ')')
                BalanceFactor--;
        }
        if (BalanceFactor == 0) {
            return result.toString();
        }
        for (int i = BalanceFactor; i > 0; i--) {
            result.append(')');
        }
        return result.toString();
    }

    /**
     * This Method retreves Color From Attributes Both Default and Custom
     *
     * @param attr    the attribute from which color is to be retrieved
     * @param context The context of Application
     * @return Hex String of corresponding Color
     */
    public static int getColorFromAttr(int attr, Context context) {
        try {
            return context.getTheme().getResources().getColor(attr);
        } catch (Exception exception) {
            TypedValue typedValue = new TypedValue();
            Resources.Theme theme = context.getTheme();
            theme.resolveAttribute(attr, typedValue, true);
            return typedValue.data;
        }
    }


    /**
     * Method to generate custom toast
     *
     * @param context : Application Context
     * @param s       String to be showed on Toast
     */
    public static void showToast(Context context, String s) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) ((Activity) context).findViewById(R.id.custom_toast));
        TextView message = layout.findViewById(R.id.toast_message);
        message.setText(s);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.TOP, 0, 200);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    public static void showToast(Context context, String s, int location) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) ((Activity) context).findViewById(R.id.custom_toast));
        TextView message = layout.findViewById(R.id.toast_message);
        message.setText(s);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.TOP, 0, location);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    /**
     * This method generates custom snackbar
     *
     * @param context Application context
     * @param view    Parent View e.g., Whole activity
     * @param s       String to show on Snackbar
     */
    public static void showSnackBar(Context context, View view, String s) {
        Snackbar snackbar = Snackbar.make(view, s, Snackbar.LENGTH_LONG);
        // Get the Snackbar's layout view
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
        layout.setBackgroundColor(Utils.getColorFromAttr(R.attr.colorSurface, context));
        // Hide the text
        TextView text = layout.findViewById(R.id.snackbar_text);
        text.setTextColor(Utils.getColorFromAttr(R.attr.primaryTextColor, context));
        text.setTextSize(18);
        snackbar.show();
    }

    /**
     * This method Checks weather the expression is only composed of numbers
     *
     * @param s Expression as String
     * @return true if yes otherwise false
     */
    public static boolean isExpressionOnlyOfNumbers(String s) {
        int i = 0;
        while (i < s.length()) {
            if (Utils.isDigit(s.charAt(i)) || s.charAt(i) == Utils.STANDARD_THOUSAND_SEPARATOR
                    || s.charAt(i) == Utils.DECIMAL_POINT || s.charAt(i) == 'E')
                i++;
            else
                break;
        }
        if (i == s.length()) //if i'sLength is equal to string length, then
            return true;
        return false;
    }

    /**
     * This method Checks weather Two numbers are close to each other
     *
     * @param arg         1st number
     * @param compareWith 2nd number
     * @return true if yes otherwise false
     */
    public static boolean isClose(double arg, double compareWith) {
        arg = Double.valueOf(doubleToString(arg, MAX_DIGITS, ROUNDING_DIGITS));
        double diff = Math.abs(Math.abs(arg) - Math.abs(compareWith));
        return diff <= (EPSILON * Math.max(Math.abs(arg), Math.abs(compareWith)));
    }

    /**
     * is char a suffix operator?
     *
     * @param c Character to check
     * @return true if yes otherwise false
     */
    public static boolean isSuffix(char c) {
        switch (c) {
            case '!':
            case '%':
                return true;
            default:
                return false;
        }
    }

    /**
     * is char a binary operator?
     *
     * @param c Character to check
     * @return true if yes otherwise false
     */
    public static boolean isBinary(char c) {
        switch (c) {
            case '^':
            case '×':
            case '÷':
            case '+':
            case '−':
            case '|':
            case '√':
                return true;
            default:
                return false;
        }
    }

    /**
     * is char an Operator
     *
     * @param c Character to check
     * @return true if yes otherwise false
     */
    public static boolean isOperator(char c) {
        return isBinary(c) || isSuffix(c);
    }

    /**
     * Does a button id correspond to a trig function?
     *
     * @param id id of button
     * @return true if yes otherwise false
     */
    public static boolean isTrigFunc(int id) {
        switch (id) {
            case R.id.fun_sin:
            case R.id.fun_cos:
            case R.id.fun_tan:
            case R.id.fun_arcsin:
            case R.id.fun_arccos:
            case R.id.fun_arctan:
                return true;
            default:
                return false;
        }
    }

    /**
     * Does a button id correspond to a Hyperbolic function?
     *
     * @param id id of button
     * @return true if yes otherwise false
     */
    public static boolean isHprFunc(int id) {
        switch (id) {
            case R.id.fun_hypsin:
            case R.id.fun_hypcos:
            case R.id.fun_hyptan:
            case R.id.fun_archypsin:
            case R.id.fun_archypcos:
            case R.id.fun_archyptan:
                return true;
            default:
                return false;
        }
    }

    /**
     * Does a button id correspond to a function that introduces an implicit lparen?
     *
     * @param id id of button
     * @return true if yes otherwise false
     */
    public static boolean isFunc(int id) {
        if (isTrigFunc(id) || isHprFunc(id)) {
            return true;
        }
        switch (id) {
            case R.id.fun_log_base_2:
            case R.id.fun_log_base_10:
            case R.id.fun_log_base_e:
            case R.id.fun_log_base_n:
                return true;
            default:
                return false;
        }
    }

    /**
     * Does a button character correspond to a digit?
     *
     * @param c Character to check
     * @return true if yes otherwise false
     */
    public static boolean isDigit(char c) {
        switch (c) {
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                return true;
            default:
                return false;
        }
    }

    /**
     * Does a button character correspond to a Constant?
     *
     * @param c Character to check
     * @return true if yes otherwise false
     */
    public static boolean isConstant(char c) {
        switch (c) {
            case 'e':
            case 'φ':
            case 'π':
                return true;
            default:
                return false;
        }
    }

    /**
     * This function returns value of edittext or textview as double;
     *
     * @param editText : from which value is to be extracted
     * @return double or @Double.NaN
     */
    public static double getDouble(EditText editText) {
        double number = Double.NaN;
        try {
            number = Double.parseDouble(editText.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return number;
    }

    public static double getDouble(String s) {
        double number = Double.NaN;
        try {
            number = Double.parseDouble(s);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return number;
    }

    /**
     * Returns String From Editext
     *
     * @param editText : Incomming View
     * @return String from @EditText
     */
    public static String getString(EditText editText) {
        return editText.getText().toString();
    }

    public static String getString(double d) {
        return String.valueOf(d);
    }

    public static Drawable getDrawableFromAssets(String fileName, Context context) {
        AssetManager assetManager = context.getAssets();
        InputStream inputStream;
        Drawable drawable = null;
        try {
            inputStream = assetManager.open(fileName.toLowerCase() + ".png");
            drawable = Drawable.createFromStream(inputStream, null);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return drawable;
    }

    /**
     * this method returns the screen size of the device
     * @param activity : context
     * @return screen size in Inchs.
     */
    public static double getScreenSize(Activity activity) {
        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);

        int mWidthPixels = displayMetrics.widthPixels;
        int mHeightPixels = displayMetrics.heightPixels;
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        double x = Math.pow(mWidthPixels / displayMetrics.xdpi, 2);
        double y = Math.pow(mHeightPixels / displayMetrics.ydpi, 2);

        return Math.sqrt(x + y);
    }

    public static boolean isNetworkAvailable(Activity activity) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * This method returns formatter which contains information about how to format a number
     * @param value : value to be formatted
     * @param scale  : no. of digits round up to
     * @return formatted String
     */

    public static String formatDecimal(double value, int scale) {
        DecimalFormat decimalFormat; // = new DecimalFormat();
        if(value == 0)
            return "0";
        else if (value < 0.00000000000001 || value > 10000000000000.0)
            decimalFormat = new DecimalFormat("0.###E0");
        else
            decimalFormat = new DecimalFormat();

          //Set maximum number of decimal places
          decimalFormat.setMaximumFractionDigits(scale);
          //decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        //Set group and decimal separators
          DecimalFormatSymbols symbols = decimalFormat.getDecimalFormatSymbols();
          symbols.setGroupingSeparator(',');

      //  decimalFormat.setDecimalFormatSymbols(symbols);
        Log.d("DecimalFormat", decimalFormat.format(value));
        return decimalFormat.format(value);
    }
}
