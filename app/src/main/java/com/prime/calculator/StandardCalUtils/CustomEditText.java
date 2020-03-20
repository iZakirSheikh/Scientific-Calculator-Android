package com.prime.calculator.StandardCalUtils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.InputType;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.prime.calculator.R;
import com.prime.calculator.Utility.Utils;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.Nullable;


/**
 * CalculatorEditText disables the keyboard.
 * It also automates text resizing with animation
 * Commas will appear as numbers are typed, exponents will be raised, and backspacing
 * on sin( and log( will remove the whole word. Because of the formatting, getText() will
 * no longer return the correct value. getCleanText() has been added instead.
 */

public class CustomEditText extends androidx.appcompat.widget.AppCompatEditText {

    // Temporary objects for use in layout methods.
    //I have no idea what they do
    private final Paint mTempPaint = new TextPaint();
    //Variables fir holding Text Sizes
    private float mMaximumTextSize;
    private float mMinimumTextSize;
    private float mStepTextSize;
    //Check if
    private int mWidthConstraint = -1;
    private OnTextSizeChangeListener mOnTextSizeChangeListener;
    //Create List of Text Button Keywords
    private List<String> mKeywords;

    //Constructors
    public CustomEditText(Context context) {
        super(context);
        getKeywords(context, null);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.CalEditText, 0, 0);
        mMaximumTextSize = a.getDimension(
                R.styleable.CalEditText_maxTextSize, getTextSize());
        mMinimumTextSize = a.getDimension(
                R.styleable.CalEditText_minTextSize, getTextSize());
        mStepTextSize = a.getDimension(R.styleable.CalEditText_stepTextSize,
                (mMaximumTextSize - mMinimumTextSize) / 3);

        a.recycle();

        setTextSize(TypedValue.COMPLEX_UNIT_PX, mMaximumTextSize);
        setMinHeight(getLineHeight() + getPaddingBottom() + getPaddingTop());
        //Step 1 Hack to stop displaying Soft Keyboard
        setInputType(getInputType() | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        setShowSoftInputOnFocus(false);
        getKeywords(context, attrs);
    }

    /**
     * This Function gets Keywords from context and places them in mKeywods as List<Strings>
     * @param c context
     * @param attrs dont know its usage
     */
    private void getKeywords(Context c, @Nullable AttributeSet attrs) {
        mKeywords = Arrays.asList(
                c.getString(R.string.fun_sin) + "(",
                c.getString(R.string.fun_cos) + "(",
                c.getString(R.string.fun_tan) + "(",
                c.getString(R.string.fun_arcsin) + "(",
                c.getString(R.string.fun_arccos) + "(",
                c.getString(R.string.fun_arctan) + "(",
                c.getString(R.string.fun_hypsin) + "(",
                c.getString(R.string.fun_hypcos) + "(",
                c.getString(R.string.fun_hyptan) + "(",
                c.getString(R.string.fun_archypsin) + "(",
                c.getString(R.string.fun_archypcos) + "(",
                c.getString(R.string.fun_archyptan) + "(",
                c.getString(R.string.fun_log_base_n) + "(",
                c.getString(R.string.fun_log_base_10) + "(",
                c.getString(R.string.fun_log_base_2) + "(",
                c.getString(R.string.fun_log_base_e) + "(",
                c.getString(R.string.op_sqrt),
                c.getString(R.string.op_cbrt)
        );
    }


    //Step 2 Hack to stop displaying Soft Keyboard
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final boolean ret = super.onTouchEvent(event);
        //Hack to stop displaying Soft Keyboard
        final InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && imm.isActive(this)) {
            imm.hideSoftInputFromWindow(getApplicationWindowToken(), 0);
        }
        return ret;
    }

    /**
     * Step 3 Hack to stop displaying Soft Keyboard
     * and write android:windowSoftInputMode="stateAlwaysHidden" in Manifest.xml
     * That's it..
     */
    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedReact) {
        super.onFocusChanged(focused, direction, previouslyFocusedReact);
        final InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && imm.isActive(this)) {
            imm.hideSoftInputFromWindow(getApplicationWindowToken(), 0);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidthConstraint =
                MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        setTextSize(TypedValue.COMPLEX_UNIT_PX, getVariableTextSize(getText().toString()));
    }


    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        final int textLength = text.length();
        if (getSelectionStart() != textLength || getSelectionEnd() != textLength) {
            // Pin the selection to the end of the current text.
            setSelection(textLength);
        }
        setTextSize(TypedValue.COMPLEX_UNIT_PX, getVariableTextSize(text.toString()));
    }

    @Override
    public void setTextSize(int unit, float size) {
        setTextSizeInternal(unit, size, true);
    }

    private void setTextSizeInternal(int unit, float size, boolean notifyListener) {
        final float oldTextSize = getTextSize();
        super.setTextSize(unit, size);
        if (notifyListener && mOnTextSizeChangeListener != null && getTextSize() != oldTextSize) {
            mOnTextSizeChangeListener.onTextSizeChanged(this, oldTextSize);
        }
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        if (getText() != null) {
            setSelection(getText().length());
        }
    }

    public void setOnTextSizeChangeListener(OnTextSizeChangeListener listener) {
        mOnTextSizeChangeListener = listener;
    }

    public float getVariableTextSize(String text) {
        if (mWidthConstraint < 0 || mMaximumTextSize <= mMinimumTextSize) {
            // Not measured, bail early.
            return getTextSize();
        }

        // Capture current paint state.
        mTempPaint.set(getPaint());

        // Step through increasing text sizes until the text would no longer fit.
        float lastFitTextSize = mMinimumTextSize;
        while (lastFitTextSize < mMaximumTextSize) {
            final float nextSize = Math.min(lastFitTextSize + mStepTextSize, mMaximumTextSize);
            mTempPaint.setTextSize(nextSize);
            if (mTempPaint.measureText(text) > mWidthConstraint) {
                break;
            } else {
                lastFitTextSize = nextSize;
            }
        }

        return lastFitTextSize;
    }

    /**
     * Backspace Method Deletes chars from ediText Based on the Position of Curser
     *
     */
    public void backspace() {
        // Check and remove keywords
        String currentText = getText().toString();
        //Get Location of Original Selection Handle
        int selectionHandle = getSelectionStart();
        String text1 = currentText.substring(0, selectionHandle);
        String text2 = currentText.substring(selectionHandle);
        String result = "";
        boolean hasSpecialFunBeforeSH = false;
        if (text1.isEmpty())
            return;
        //Check if Text Before SelectionHandle Ends With mKeyworks
        for (String s : mKeywords) {
            if (text1.endsWith(s)) {
                text1 = text1.substring(0,
                        text1.length() - s.length());
                result = text1 + Utils.SELECTION_HANDLE + text2;
                hasSpecialFunBeforeSH = true;
                break;
            }
        }
        char endChar = text1.isEmpty() ? '\0' : text1
                .charAt(text1.length() - 1);
        if (!hasSpecialFunBeforeSH && endChar != Utils.STANDARD_THOUSAND_SEPARATOR && (Utils.isDigit(endChar) ||
                endChar == Utils.DECIMAL_POINT)) {
            text1 = text1
                    .substring(0, text1.length() - 1);
            result = formatNumber(text1 + Utils.SELECTION_HANDLE + text2);
        } else if (!hasSpecialFunBeforeSH && endChar == Utils.STANDARD_THOUSAND_SEPARATOR) {
            text1 = text1
                    .substring(0, text1.length() - 2);
            result = formatNumber(text1 + Utils.SELECTION_HANDLE + text2);
        } else if (!hasSpecialFunBeforeSH) {
            text1 = text1
                    .substring(0, text1.length() - 1);
            result = text1 + Utils.SELECTION_HANDLE + text2;
        }
        selectionHandle = result.indexOf(Utils.SELECTION_HANDLE);
        result = result.replace(String.valueOf(Utils.SELECTION_HANDLE), "");
        setText(result);
        setSelection(selectionHandle);
    }

    /**
     * This Function Inserts button text into editText based on the Position of curser
     * Formats numbers
     * Adds rules for inserting Operators
     *
     * @param key : the key which has been pressed
     */
    public void insert(String key) {
        String currentText = getText().toString();
        //Get Location of Original Selection Handle
        int selectionHandle = getSelectionStart();
        String text1 = currentText.substring(0, selectionHandle);
        String text2 = currentText.substring(selectionHandle);
        String result;
        // Add extra rules for decimal points and operators
        if (("∛\u00B2√").contains(key) && !key.equals("√") && !text1.isEmpty()
                && text1.charAt(text1.length() - 1) != '(' && !Utils.isOperator(text1.charAt(text1.length() - 1))) {
            key = '×' + key;
            result = text1 + key + Utils.SELECTION_HANDLE + text2;
        } else if (key.length() == 1) {
            char mChar = key.charAt(0);
            char prevChar = selectionHandle > 0 ? getText().charAt(selectionHandle - 1) : '\0';
            char nextChar = selectionHandle < getText().length() ? getText().charAt(selectionHandle) : '\0';
            if (Utils.isDigit(mChar) || mChar == Utils.DECIMAL_POINT) {
                if (getText().length() == 0)
                    result = key + Utils.SELECTION_HANDLE;
                else
                    result = formatNumber(text1 + mChar + Utils.SELECTION_HANDLE + text2);
            }
            // don't allow 2 successive minuses
            else if (mChar == Utils.OP_SUB && (prevChar == Utils.OP_SUB || nextChar == Utils.OP_SUB)) {
                return;
            }
            // don't allow the first character to be an Binary operator
            else if (Utils.isBinary(mChar) && mChar != Utils.OP_SUB && selectionHandle == 0) {
                return;
            }
            // Don't Allow 2nd Char as Operator if 1st Char is Minus
            else if (Utils.isOperator(mChar) && selectionHandle == 1 && prevChar == Utils.OP_SUB) {
                return;
            }
            // Don't Allow successive Prefix Operators like factorial factorial
            else if ((Utils.isSuffix(mChar) && selectionHandle == 0) || (Utils.isSuffix(mChar) &&
                    (Utils.isSuffix(prevChar) || Utils.isSuffix(nextChar)))) {
                return;
            }
            // don't allow multiple successive operators Binary Operators
            else if (Utils.isBinary(mChar)) {
                if (Utils.isBinary(prevChar)) {
                    text1 = text1.substring(0, text1.length() - 1);
                } else if (Utils.isBinary(nextChar)) {
                    text2 = text2.substring(1);
                }
                //Insert Current Key Press if previous Operators are Binary
                result = text1 + key + Utils.SELECTION_HANDLE + text2;
            } else
                result = text1 + key + Utils.SELECTION_HANDLE + text2;
        } else
            /*if it is Other Than above mentioned cases, then*/
            result = text1 + key + Utils.SELECTION_HANDLE + text2;
        /*
          SelectionHandle is Location of SH char
          Remove SHChar before Setting Text
          */
        selectionHandle = result.indexOf(Utils.SELECTION_HANDLE);
        //Single setText for Insertion
        setText(result.replace(String.valueOf(Utils.SELECTION_HANDLE), ""));
        setSelection(selectionHandle);
    }


    /**
     * This Function Just Formats Number Around Selection Handle
     * Repositions ., if Inserted Char is Dot
     *
     * @param s, Current text in Edittext
     * @return Whole String With Formatted Numder Around Selection Handle
     */

    private String formatNumber(String s) {
        String textBeforeNumber;
        String textAfterNumber;
        StringBuilder number = new StringBuilder();
        int selectionHandle = s.indexOf(Utils.SELECTION_HANDLE); /*Because Text mchar Has beem added to
                                                        string & selection is not yet updated*/
        int numberStart = selectionHandle - 1;
        //Get Span of Number to the left side of Selection Handle
        while (numberStart >= 0) {
            if (Utils.isDigit(s.charAt(numberStart)) || s.charAt(numberStart) == Utils.STANDARD_THOUSAND_SEPARATOR ||
                    s.charAt(numberStart) == Utils.DECIMAL_POINT)
                numberStart--;
            else
                break;
        }
        numberStart++;
        // get span of number to right side of selection Handle
        int numberEnd = selectionHandle;   // set to selection handle and no need to --j after exiting loop;
        while (numberEnd <= s.length() - 1) {
            if (Utils.isDigit(s.charAt(numberEnd)) || s.charAt(numberEnd) == Utils.STANDARD_THOUSAND_SEPARATOR ||
                    s.charAt(numberEnd) == Utils.DECIMAL_POINT || s.charAt(numberEnd) == Utils.SELECTION_HANDLE)
                numberEnd++;
            else
                break;
        }
        //Extract Number
        textBeforeNumber = s.substring(0, numberStart);
        textAfterNumber = s.substring(numberEnd);
        /*Now we Just Extract The Number*/
        number.append(s, numberStart, numberEnd);
        //Reformat Number
        number.replace(0, numberEnd, Utils.addThousandSeparators(number.toString(), Utils.STANDARD_THOUSAND_SEPARATOR));
        return textBeforeNumber + number + textAfterNumber;
    }

    //Interface
    public interface OnTextSizeChangeListener {
        void onTextSizeChanged(TextView textView, float oldSize);
    }

}