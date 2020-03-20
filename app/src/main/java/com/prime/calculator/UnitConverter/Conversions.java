package com.prime.calculator.UnitConverter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.SparseArray;

import com.prime.calculator.R;
import com.prime.calculator.Utility.DatabaseHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import static com.prime.calculator.UnitConverter.Unit.*;


/**
 * This Class actually dos the work of Conversion
 * used models from Unit and Converter
 * @author Zakir Ahmad Sheikh
 */
public class Conversions {
    public static final int CURRENCY_COUNT = 168;
    private DatabaseHelper mDatabaseHelper;
    private static Conversions mConversions = null;
    private SparseArray<Converter> mConverter = new SparseArray<>();
    private Context context;



    private Conversions(Context context) {
        this.context = context;
       // key = context.getString(R.string.currency_layer_api_live_key);
        mDatabaseHelper = new DatabaseHelper(context);
        //Fill conversions HashMap
        getLengthConversions();
        getMassConversions();
        getTimeConversions();
        getTemperatureConversions();
        getDataConversions();
        getAngleConversions();
        getAreaConversions();
        getVolumeConversions();
        getPressureConversions();
        getEnergyConversions();
        getPowerConversions();
        getSpeedConversions();
        getCurrencyConversions();
    }

    /**
     * Get instance of Conversions objects, which contains mapping of type and Conversion object
     *
     * @return Conversions mInstance
     */
    public static Conversions getInstance(Context context) {
        //Create singleton to contain all conversions
        if(mConversions == null)
            mConversions = new Conversions(context);
        return mConversions;
    }

    /**
     * Get Conversion object by its id
     *
     * @param id id of conversion
     * @return Conversion object
     */
    public Converter getById(@Converter.id int id) {
        return mConverter.get(id);
    }

    /**
     * Method to add conversion to hashmap, encapsulated in a separate method for type safety
     *
     * @param id        conversion id
     * @param converter Conversion object
     */
    private void addConversion(@Converter.id int id, Converter converter) {
        mConverter.put(id, converter);
    }

    public List<String> getConverterLabels() {
        List<String> labels = new ArrayList<>();
        for (int i = 0; i < mConverter.size(); i++) {
            Converter converter = mConverter.get(i);
            labels.add(context.getString(converter.getLabelResID()));
        }
        return labels;
    }

    public List<Unit> getUnits(@Converter.id int id) {
        return mConverter.get(id).getUnits();
    }

    //checked
    private void getSpeedConversions() {
        List<Unit> units = new ArrayList<>();
        //base unit - km/h
        units.add(new Unit(CENTIMETRES_PER_SEC, R.string.centimetres_per_second, 0.036, 27.777777777777777777777777777778));
        units.add(new Unit(METRES_PER_SECOND, R.string.metres_per_second, 3.6, 0.27777777777777777777777777777778));
        units.add(new Unit(KILOMETRES_PER_HOUR, R.string.kilometres_per_hour, 1.0, 1.0));
        units.add(new Unit(FEET_PER_SECOND, R.string.feet_per_second, 1.09728, 0.91134441528142315543890347039953));
        units.add(new Unit(MILES_PER_HOUR, R.string.miles_per_hour, 1.60934, 0.62137273664980675307890191009979));
        units.add(new Unit(KNOTS, R.string.knots, 1.85184, 0.54000345602211854155866597546224));
        units.add(new Unit(MACH, R.string.mach, 1225.08, 8.1627322297319358735756032259118E-4));
        addConversion(Converter.SPEED, new Converter(Converter.SPEED, R.string.speed, units));
    }


    //checked
    private void getPowerConversions() {
        //basic unit watt
        List<Unit> units = new ArrayList<>();
        units.add(new Unit(WATT, R.string.watts, 1.0, 1.0));
        units.add(new Unit(KILOWATT, R.string.kilowatts, 1000, 0.001));
        units.add(new Unit(HORSEPOWER_US, R.string.horse_power_us, 745.6998715822702, 0.001341022089595028));
        units.add(new Unit(FOOT_POUNDS_PER_MINUTE, R.string.foot_pounds_per_minute, 0.022596966, 44.253728575774287574712463611265));
        units.add(new Unit(BRITISH_THERMAL_UNITS_PER_MINUTE, R.string.british_thermal_units_per_minute, 17.5842641667, 0.05686902735991299602317751615514));
        addConversion(Converter.POWER, new Converter(Converter.POWER, R.string.power, units));
    }


    //Checked
    private void getEnergyConversions() {
        //Base Unit Joule
        List<Unit> units = new ArrayList<>();
        units.add(new Unit(ELECTRON_VOLT, R.string.electron_volt, 1.602176565E-19, 6241509343260179317.3775450897324));
        units.add(new Unit(JOULE, R.string.joules, 1, 1));
        units.add(new Unit(KILOJOULE, R.string.kilojoules, 1000, 0.001));
        units.add(new Unit(THERMAL_CALORIE, R.string.thermal_calories, 4.184, 0.239006));
        units.add(new Unit(FOOD_CALORIE, R.string.food_calories, 4184, 0.000239));
        units.add(new Unit(FOOT_POUND, R.string.foot_pounds, 1.3558179483314003, 0.73756214927726541827787446962097));
        units.add(new Unit(BRITISH_THERMAL_UNIT, R.string.british_thermal_units, 1055.05585262, 9.4781712031331720001278504447561E-4));
        addConversion(Converter.ENERGY, new Converter(Converter.ENERGY, R.string.energy, units));
    }


    //Checked
    private void getPressureConversions() {
        List<Unit> units = new ArrayList<>();
        //Basic Unit Pascal
        units.add(new Unit(ATMOSPHERE, R.string.atmospheres, 101325, 9.8692326671601283000246730816679E-6));
        units.add(new Unit(BAR, R.string.bars, 99999.99999999998, 0.000010000000000000002));
        units.add(new Unit(KILOPASCAL, R.string.kilopascals, 1000, 0.001));
        units.add(new Unit(MILLIMETRE_OF_MERCURY, R.string.mm_of_mercury, 133.3224, 0.00750061505043413559911912776848));
        units.add(new Unit(PASCAL, R.string.pascals, 1.0, 1.0));
        units.add(new Unit(POUNDS_PER_INCH, R.string.pounds_per_inch, 6894.757, 1.4503774389728310946999292360848E-4));
        addConversion(Converter.PRESSURE, new Converter(Converter.PRESSURE, R.string.pressure, units));
    }

    private void getVolumeConversions() {
        List<Unit> units = new ArrayList<>();
        // Base unit - cubic metre
        units.add(new Unit(MILLILITRE, R.string.millilitres, 0.000001, 1000000));
        units.add(new Unit(CUBIC_CENTIMETRE, R.string.cbc_centimetres, 0.000001, 1000000));
        units.add(new Unit(LITRE, R.string.litres, 0.001, 1000));
        units.add(new Unit(CUBIC_METRE, R.string.cbc_metres, 1.0, 1.0));
        units.add(new Unit(TEASPOON_US, R.string.teaspoon_us, 0.000005, 202884.1));
        units.add(new Unit(TABLESPOON_US, R.string.tablespoon_us, 0.000015, 67628.04));
        units.add(new Unit(FLUID_OUNCE_US, R.string.fluid_ounce_us, 0.00003, 33814.02));
        units.add(new Unit(CUP_US, R.string.cups_us, 0.000237, 4226.753));
        units.add(new Unit(PINT_US, R.string.pints_us, 0.000473, 2113.376));
        units.add(new Unit(QUART_US, R.string.quarts_us, 0.000946, 1056.688));
        units.add(new Unit(GALLON_US, R.string.gallons_us, 0.003785, 264.1721));
        units.add(new Unit(CUBIC_INCH, R.string.cbc_inches, 0.000016, 61023.74));
        units.add(new Unit(CUBIC_YARD, R.string.cbc_yards, 0.764555, 1.307951));
        units.add(new Unit(TEASPOON_UK, R.string.teaspoon_uk, 0.000006, 168936.4));
        units.add(new Unit(TABLESPOON_UK, R.string.tablespoon_uk, 0.000018, 56312.13));
        units.add(new Unit(FLUID_OUNCE_UK, R.string.fluid_ounce_uk, 0.000028, 35195.08));
        units.add(new Unit(PINT_UK, R.string.pints_uk, 0.000568, 1759.754));
        units.add(new Unit(QUART_UK, R.string.quarts_uk, 0.001137, 879.877));
        units.add(new Unit(GALLON_UK, R.string.gallons_uk, 0.004546, 219.9692));
        addConversion(Converter.VOLUME, new Converter(Converter.VOLUME, R.string.volume, units));
    }

    private void getAreaConversions() {
        List<Unit> units = new ArrayList<>();
        //Base unit: square metre
        units.add(new Unit(SQUARE_MILIMETRE, R.string.sq_millimetres, 0.000001, 1000000));
        units.add(new Unit(SQUARE_CENTIMETRE, R.string.sq_centimetres, 0.0001, 10000));
        units.add(new Unit(SQUARE_METRE, R.string.sq_metres, 1.0, 1.0));
        units.add(new Unit(HECTARE, R.string.hectares, 10000, 0.0001));
        units.add(new Unit(SQUARE_KILOMETRE, R.string.sq_kilometres, 1000000, 0.000001));
        units.add(new Unit(SQUARE_INCH, R.string.sq_inches, 0.000645, 1550.003));
        units.add(new Unit(SQUARE_FOOT, R.string.sq_feet, 0.092903, 10.76391));
        units.add(new Unit(SQUARE_YARD, R.string.sq_yards, 0.836127, 1.19599));
        units.add(new Unit(ACRE, R.string.acres, 4046.856, 0.000247));
        units.add(new Unit(SQUARE_MILE, R.string.sq_miles, 2589988, 0.00000038610216));
        addConversion(Converter.AREA, new Converter(Converter.AREA, R.string.area, units));
    }

    private void getAngleConversions() {
        List<Unit> units = new ArrayList<>();
        //Base Unit Degrees
        units.add(new Unit(DEGREE, R.string.degrees, 1.0, 1.0));
        units.add(new Unit(RADIAN, R.string.radians, 57.295779513082320876798154814105, 0.01745329251994329576923690768489));
        units.add(new Unit(GRADIAN, R.string.gradians, 0.9, 1.11111111111111111111));
        addConversion(Converter.ANGLE, new Converter(Converter.ANGLE, R.string.angle, units));
    }


    private void getTemperatureConversions() {
        //Base Unit Kelvin
        List<Unit> units = new ArrayList<>();
        units.add(new Unit(CELSIUS, R.string.celsius));
        units.add(new Unit(FAHRENHEIT, R.string.fahrenheit));
        units.add(new Unit(KELVIN, R.string.kelvin));
        units.add(new Unit(RANKINE, R.string.rankine));
        units.add(new Unit(DELISLE, R.string.delisle));
        units.add(new Unit(NEWTON, R.string.newton));
        units.add(new Unit(REAUMUR, R.string.reaumur));
        units.add(new Unit(ROMER, R.string.romer));
        addConversion(Converter.TEMPERATURE, new Converter(Converter.TEMPERATURE, R.string.temperature, units));
    }
    private void getDataConversions() {
        List<Unit> units = new ArrayList<>();
        //Base Unit = megabyte
        units.add(new Unit(BIT, R.string.bit, 0.000000125, 8000000));
        units.add(new Unit(BYTE, R.string.bytes, 0.000001, 1000000));
        units.add(new Unit(KILOBIT, R.string.kilobits, 0.000125, 8000));
        units.add(new Unit(KIBIBIT, R.string.kibibits, 0.000128, 7812.5));
        units.add(new Unit(KILOBYTE, R.string.kilobytes, 0.001, 1000));
        units.add(new Unit(KIBIBYTE, R.string.kibibytes, 0.001024, 976.5625));
        units.add(new Unit(MEGABIT, R.string.megabits, 0.125, 8));
        units.add(new Unit(MIBIBIT, R.string.mebibits, 0.131072, 7.629395));
        units.add(new Unit(MEGABYTE, R.string.megabytes, 1.0, 1.0));
        units.add(new Unit(MIBIBYTE, R.string.mebibytes, 1.048576, 0.953674));
        units.add(new Unit(GIGABIT, R.string.gigabits, 125, 0.008));
        units.add(new Unit(GIBIBIT, R.string.gibibits, 134.2177, 0.007451));
        units.add(new Unit(GIGABYTE, R.string.gigabytes, 1000, 0.001));
        units.add(new Unit(GIBIBYTE, R.string.gibibytes, 1073.742, 0.000931));
        units.add(new Unit(TERABIT, R.string.terabits, 125000, 0.000008));
        units.add(new Unit(TEBIBIT, R.string.tebibits, 137439, 0.000007));
        units.add(new Unit(TERABYTE, R.string.terabytes, 1000000, 0.000001));
        units.add(new Unit(TEBIBYTE, R.string.tebibytes, 1099512, 0.0000009094947));
        units.add(new Unit(PETABIT, R.string.petabits, 125000000, 0.000000008));
        units.add(new Unit(PEBIBIT, R.string.pebibits, 140737488, 0.00000000710543));
        units.add(new Unit(PETABYTE, R.string.petabytes, 1000000000, 0.000000001));
        units.add(new Unit(PEBIBYTE, R.string.pebibytes, 1125899907, 0.00000000088818));
        units.add(new Unit(EXABIT, R.string.exabits, 125000000000.0, 0.000000000008));
        units.add(new Unit(EXBIBIT, R.string.exbibits, 144115188076.0, 0.00000000000694));
        units.add(new Unit(EXABYTE, R.string.exabytes, 1000000000000.0, 0.000000000001));
        units.add(new Unit(EXBIBYTE, R.string.exbibytes, 1152921504607.0, 0.00000000000087));
        units.add(new Unit(ZETABIT, R.string.zetabits, 125000000000000.0, 8.000000E-15));
        units.add(new Unit(ZEBIBIT, R.string.zebibits, 147573952589676.0, 6.776264E-15));
        units.add(new Unit(ZETABYTE, R.string.zetabytes, 1.000000E15, 1.000000E-15));
        units.add(new Unit(ZEBIBYTE, R.string.zebibytes, 1.180592E15, 8.470329E-16));
        units.add(new Unit(YOTTABIT, R.string.yotabits, 1.250000E17, 8.000000E-18));
        units.add(new Unit(YOBIBIT, R.string.yobibits, 1.511157E17, 6.617445E-18));
        units.add(new Unit(YOTTABYTE, R.string.yottabytes, 1.000000E18, 1.000000E18));
        units.add(new Unit(YOBIBYTE, R.string.yobibytes, 1.208926E18, 8.271806E-19));
        addConversion(Converter.DATA, new Converter(Converter.DATA, R.string.data, units));
    }



    //Time Conversions Checked
    private void getTimeConversions() {
        //Base unit - Second
        List<Unit> units = new ArrayList<>();
        units.add(new Unit(NANOSECOND, R.string.nanosecond, 1E-9, 1E9));
        units.add(new Unit(MICROSECOND, R.string.microsecond, 1E-6, 1E6));
        units.add(new Unit(MILLISECOND, R.string.millisecond, 0.001, 1000.0));
        units.add(new Unit(SECOND, R.string.second, 1.0, 1.0));
        units.add(new Unit(MINUTE, R.string.minute, 60.0, 0.01666666666666666666666666666667));
        units.add(new Unit(HOUR, R.string.hour, 3600.0, 2.7777777777777777777777777777778E-4));
        units.add(new Unit(DAY, R.string.day, 86400.0, 1.1574074074074074074074074074074E-5));
        units.add(new Unit(WEEK, R.string.week, 604800.0, 1.6534391534391534391534391534392E-6));
        units.add(new Unit(YEAR, R.string.year, 31557600, 3.1688087814028950237026896848937E-8));
        addConversion(Converter.TIME, new Converter(Converter.TIME, R.string.time, units));
    }


    //checked
    private void getMassConversions() {
        //Base unit - Kilograms
        List<Unit> units = new ArrayList<>();
        units.add(new Unit(CARAT, R.string.carat, 0.0002, 5000));
        units.add(new Unit(MILLIGRAM, R.string.milligram, 1E-6, 1E6));
        units.add(new Unit(CENTIGRAM, R.string.centigram, 1E-5, 1E5));
        units.add(new Unit(DECIGRAM, R.string.decigram, 0.0001, 10000.0));
        units.add(new Unit(GRAM, R.string.gram, 0.001, 1000.0));
        units.add(new Unit(DECAGRAM, R.string.decagram, 0.01, 100.0));
        units.add(new Unit(HECTOGRAM, R.string.hectogram, 0.1, 10.0));
        units.add(new Unit(KILOGRAM, R.string.kilogram, 1.0, 1.0));
        units.add(new Unit(METRIC_TONNE, R.string.metric_ton, 1000, 0.001));
        units.add(new Unit(OUNCE, R.string.ounce, 0.028349523125, 35.27396194958041291568));
        units.add(new Unit(POUND, R.string.pound, 0.45359237, 2.2046226218487758072297380134503));
        units.add(new Unit(STONE, R.string.stone, 6.35029318, 0.15747304441776970051640985810359));
        units.add(new Unit(SHORT_TONNE_US, R.string.short_ton, 907.18474, 0.00110231131092438790361486900673));
        units.add(new Unit(LONG_TONNE_UK, R.string.long_ton, 1016.0469088, 0.0009842065276110606282276));
        addConversion(Converter.MASS, new Converter(Converter.MASS, R.string.weight_and_mass, units));

    }

    @SuppressLint("WrongConstant")
    public void getCurrencyConversions() {
        //Base unit - USD
        List<Unit> units = new ArrayList<>();
        String[] names = mDatabaseHelper.getCurrencyNames();
        String[] tags = mDatabaseHelper.getCurrencyTags();
        double[] rates = mDatabaseHelper.getCurrencyRates();
        int cons = 1600;
        if(names[0] != null) {
            for (int i = 0; i < CURRENCY_COUNT; i++) {
                units.add(new Unit(cons, names[i], tags[i], 1 / rates[i], rates[i]));
                cons++;
            }
        }
        addConversion(Converter.CURRENCY, new Converter(Converter.CURRENCY, R.string.currency, units));
    }

    //Checked
    private void getLengthConversions() {
        //Base unit - Metre
        List<Unit> units = new ArrayList<>();
        units.add(new Unit(NANOMETRE, R.string.nanometre, 1E-9, 1E9));
        units.add(new Unit(MICROMETRE, R.string.micrometre, 1E-6, 1E6));
        units.add(new Unit(MILLIMETRE, R.string.millimetre, 0.001, 1000.0));
        units.add(new Unit(CENTIMETRE, R.string.centimetre, 0.01, 100.0));
        units.add(new Unit(METRE, R.string.metre, 1.0, 1.0));
        units.add(new Unit(KILOMETRE, R.string.kilometre, 1000.0, 0.001));
        units.add(new Unit(INCH, R.string.inch, 0.0254, 39.370078740157480314));
        units.add(new Unit(FOOT, R.string.foot, 0.3048, 3.280839895013123359));
        units.add(new Unit(YARD, R.string.yard, 0.9144, 1.0936132983377077865));
        units.add(new Unit(MILE, R.string.mile, 1609.344, 0.00062137119223733397));
        units.add(new Unit(NAUTICAL_MILE, R.string.nautical_mile, 1852.0, 0.0005399568034557235));
        units.add(new Unit(ASTRONIMICAL_UNIT, R.string.astronomical_unt, 149597870700.0, 6.6845871222684454959E-12));
        units.add(new Unit(LIGHT_YEAR, R.string.light_year, 9460730472580800.0, 1.0570008340246154637094605244851E-16));
        addConversion(Converter.LENGTH, new Converter(Converter.LENGTH, R.string.length, units));
    }

    /**
     * This function converts value between Units
     *
     * @param ConverterId : Currently Selected Converter Id e.g. length
     * @param unitFromId  : currently selected Unit from id;
     * @param unitToId    : Currently selected Unit To id;
     * @param value       : value to be converted;
     * @return double value;
     */
    public double convert(int ConverterId, int unitFromId, int unitToId, double value) {
        if (unitFromId == 0 || unitToId == 0 || Double.isNaN(value))
            return 0;
        if (unitFromId == unitToId) //if same units
            return value;
        Unit fromUnit = null;
        Unit toUnit = null;
        Converter c = Conversions.getInstance(context).getById(ConverterId);
        for (Unit unit : c.getUnits()) {
            if (unit.getId() == unitFromId)
                fromUnit = unit;
            if (unit.getId() == unitToId)
                toUnit = unit;
        }
        switch (ConverterId) {
            case Converter.TEMPERATURE:
                return convertTemperature(unitFromId, unitToId, value);
            default:
                BigDecimal multiplier = new BigDecimal(fromUnit.getInBaseValue()).multiply(new BigDecimal(toUnit.getBaseInValue()));
                BigDecimal bdResult = new BigDecimal(value).multiply(multiplier);
                return bdResult.setScale(18, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
    }

    public double convertTemperature(int fromId, int toId, double value) {
        double result = value;
        if (fromId != toId) {
            switch (toId) {
                case (CELSIUS):
                    result = toCelsius(fromId, value);
                    break;
                case (FAHRENHEIT):
                    result = toFahrenheit(fromId, value);
                    break;
                case (KELVIN):
                    result = toKelvin(fromId, value);
                    break;
                case (RANKINE):
                    result = toRankine(fromId, value);
                    break;
                case (DELISLE):
                    result = toDelisle(fromId, value);
                    break;
                case (NEWTON):
                    result = toNewton(fromId, value);
                    break;
                case (REAUMUR):
                    result = toReaumur(fromId, value);
                    break;
                case (ROMER):
                    result = toRomer(fromId, value);
                    break;
            }
        }
        return result;
    }

    private double toCelsius(int fromId, double value) {
        double result = value;
        switch (fromId) {
            case (FAHRENHEIT):    // F to C
                result = (value - 32) * 5 / 9;
                break;
            case (KELVIN):    // K to C
                result = value - 273.15;
                break;
            case (RANKINE):    // R to C
                result = (value - 491.67) * 5 / 9;
                break;
            case (DELISLE):    // D to C
                result = 100 - value * 2 / 3;
                break;
            case (NEWTON):    //N to C
                result = value * 100 / 33;
                break;
            case (REAUMUR):    //Re to C
                result = value * 5 / 4;
                break;
            case (ROMER):    //Ro to C
                result = (value - 7.5) * 40 / 21;
                break;
        }
        return result;
    }

    private double toFahrenheit(int fromId, double value) {
        double result = value;
        switch (fromId) {
            case (CELSIUS):    // C to F
                result = value * 9 / 5 + 32;
                break;
            case (KELVIN):    // K to F
                result = value * 9 / 5 - 459.67;
                break;
            case (RANKINE):    // R to F
                result = value - 459.67;
                break;
            case (DELISLE):    //D to F
                result = 212 - value * 6 / 5;
                break;
            case (NEWTON):    //N to F
                result = value * 60 / 11 + 32;
                break;
            case (REAUMUR):    //Re to F
                result = value * 9 / 4 + 32;
                break;
            case (ROMER):    //Ro to F
                result = (value - 7.5) * 24 / 7 + 32;
                break;
        }
        return result;
    }

    private double toKelvin(int fromId, double value) {
        double result = value;
        switch (fromId) {
            case (CELSIUS):    // C to K
                result = value + 273.15;
                break;
            case (FAHRENHEIT):    // F to K
                result = (value + 459.67) * 5 / 9;
                break;
            case (RANKINE):    // R to K
                result = value * 5 / 9;
                break;
            case (DELISLE):    //D to K
                result = 373.15 - value * 2 / 3;
                break;
            case (NEWTON):    //N to K
                result = value * 100 / 33 + 273.15;
                break;
            case (REAUMUR):    //Re to K
                result = value * 5 / 4 + 273.15;
                break;
            case (ROMER):    //Ro to K
                result = (value - 7.5) * 40 / 21 + 273.15;
                break;
        }
        return result;
    }

    private double toRankine(int fromId, double value) {
        double result = value;
        switch (fromId) {
            case (CELSIUS):    // C to R
                result = (value + 273.15) * 9 / 5;
                break;
            case (FAHRENHEIT):    // F to R
                result = value + 459.67;
                break;
            case (KELVIN):    // K to R
                result = value * 9 / 5;
                break;
            case (DELISLE):    //D to R
                result = 671.67 - value * 6 / 5;
                break;
            case (NEWTON):    //N to R
                result = value * 60 / 11 + 491.67;
                break;
            case (REAUMUR):    //Re to R
                result = value * 9 / 4 + 491.67;
                break;
            case (ROMER):    //Ro to R
                result = (value - 7.5) * 24 / 7 + 491.67;
                break;
        }
        return result;
    }

    private double toDelisle(int fromId, double value) {
        double result = value;
        switch (fromId) {
            case (CELSIUS):    // C to D
                result = (100 - value) * 1.5;
                break;
            case (FAHRENHEIT):    // F to D
                result = (212 - value) * 5 / 6;
                break;
            case (KELVIN):    // K to D
                result = (373.15 - value) * 1.5;
                break;
            case (RANKINE):    // R to D
                result = (671.67 - value) * 5 / 6;
                break;
            case (NEWTON):    //N to D
                result = (33 - value) * 50 / 11;
                break;
            case (REAUMUR):    //Re to D
                result = (80 - value) * 1.875;
                break;
            case (ROMER):    //Ro to D
                result = (60 - value) * 20 / 7;
                break;
        }
        return result;
    }

    private double toNewton(int fromId, double value) {
        double result = value;
        switch (fromId) {
            case (CELSIUS):    // C to N
                result = value * 33 / 100;
                break;
            case (FAHRENHEIT):    // F to N
                result = (value - 32) * 11 / 60;
                break;
            case (KELVIN):    // K to N
                result = (value - 273.15) * 33 / 100;
                break;
            case (RANKINE):    // R to N
                result = (value - 491.67) * 11 / 60;
                break;
            case (DELISLE):    //D to N
                result = 33 - value * 11 / 50;
                break;
            case (REAUMUR):    //Re to N
                result = value * 33 / 80;
                break;
            case (ROMER):    //Ro to N
                result = (value - 7.5) * 22 / 35;
                break;
        }
        return result;
    }

    private double toReaumur(int fromId, double value) {
        double result = value;
        switch (fromId) {
            case (CELSIUS):    // C to Re
                result = value * 4 / 5;
                break;
            case (FAHRENHEIT):    // F to Re
                result = (value - 32) * 4 / 9;
                break;
            case (KELVIN):    // K to Re
                result = (value - 273.15) * 4 / 5;
                break;
            case (RANKINE):    // R to Re
                result = (value - 491.67) * 4 / 9;
                break;
            case (DELISLE):    //D to Re
                result = 80 - value * 8 / 15;
                break;
            case (NEWTON):    //N to Re
                result = value * 80 / 33;
                break;
            case (ROMER):    //Ro to Re
                result = (value - 7.5) * 32 / 21;
                break;
        }
        return result;
    }

    private double toRomer(int fromId, double value) {
        double result = value;
        switch (fromId) {
            case (CELSIUS):    // C to Ro
                result = value * 21 / 40 + 7.5;
                break;
            case (FAHRENHEIT):    // F to Ro
                result = (value - 32) * 7 / 24 + 7.5;
                break;
            case (KELVIN):    // K to Ro
                result = (value - 273.15) * 21 / 40 + 7.5;
                break;
            case (RANKINE):    // R to Ro
                result = (value - 491.67) * 7 / 24 + 7.5;
                break;
            case (DELISLE):    //D to Ro
                result = 60 - value * 7 / 20;
                break;
            case (NEWTON):    //N to Ro
                result = value * 35 / 22 + 7.5;
                break;
            case (REAUMUR):    //Re to Ro
                result = value * 21 / 32 + 7.5;
                break;
        }
        return result;
    }

    public static class ExchangeRates extends AsyncTask<Void, Void, Void> {
        private JSONObject jsonObj_rates = null, jsonObj_names = null;
        private String url_currency_rates, ulr_currency_names;
        private String currencyRates, currencyNames;
        private String[] codes = new String[CURRENCY_COUNT];
        private StatusListener mListener;

        public ExchangeRates(StatusListener listener, String ulr_currency_names, String url_currency_rates) {
            this.ulr_currency_names = ulr_currency_names;
            this.url_currency_rates = url_currency_rates;
            mListener = listener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            ServiceHandler sh = new ServiceHandler();
            String json_currency_rates = sh.makeServiceCall(url_currency_rates, ServiceHandler.GET);
            String json_currency_names = sh.makeServiceCall(ulr_currency_names, ServiceHandler.GET);
            try {
                jsonObj_rates = new JSONObject(json_currency_rates);
                jsonObj_names = new JSONObject(json_currency_names);
                currencyRates = jsonObj_rates.getJSONObject("quotes").toString();
                //Log.d("currencyRates ", "" + currencyRates);
                currencyNames = jsonObj_names.getJSONObject("currencies").toString();
                //Log.d("currencyNames ", "" + currencyNames);
            } catch (JSONException e) {
                 Log.e("JSON Parser", "Error parsing data " + e.toString());
            }
            return null;
        }

        private double[] getCurrencyRates() {
            currencyRates = currencyRates.replace("{", "");
            currencyRates = currencyRates.replace("}", "");
            currencyRates = currencyRates.replace("\"", "");

            double rates[] = new double[CURRENCY_COUNT];
            Arrays.fill(rates, Double.NaN);
            StringTokenizer stock = new StringTokenizer(currencyRates, ",");
            for (int i = 0; stock.hasMoreElements(); i++) {
                String temp = stock.nextElement().toString();
                String split[] = temp.split(":");
                double amount = Double.parseDouble(split[1]);
                rates[i] = amount;
            }

            return rates;
        }

        private String[] getCurrencyNames() {
            currencyNames = currencyNames.replace("{", "");
            currencyNames = currencyNames.replace("}", "");
            currencyNames = currencyNames.replace("\"", "");

            String names[] = new String[CURRENCY_COUNT];
            Arrays.fill(names, null);
            StringTokenizer stock = new StringTokenizer(currencyNames, ",");
            for (int i = 0; stock.hasMoreElements(); i++) {
                String temp = stock.nextElement().toString();
                String split[] = temp.split(":");
                names[i] = split[1];
                codes[i] = split[0];
            }
            return names;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if((currencyRates != null))
                mListener.onTaskCompleted(false, getCurrencyNames(), codes, getCurrencyRates());
            else
                mListener.onTaskCompleted(true, null, null, null);
        }

        public interface StatusListener{
            void onTaskCompleted(boolean error, String[] currencyNames, String[] codes, double[] currencyRates);
        }
    }
}

