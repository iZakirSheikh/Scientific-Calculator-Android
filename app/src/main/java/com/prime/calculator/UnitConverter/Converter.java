package com.prime.calculator.UnitConverter;

import java.util.List;

import androidx.annotation.IntDef;

/**
 * This Class Defines Converter for Unit Converter
 *
 * @author Zakir Ahmad Sheikh 12-03-2019
 */

public class Converter {

    // Converter types.
    // Use @IntDef for type safety
    // The metric system is a system of measurement used in most of the world. It is also called the
    // International System of Units, or SI.
    public static final int LENGTH = 0;
    static final int MASS = 1;
    static final int TIME = 2;
    //public static final int ELECTRIC_CURRENT = 3;
    static final int TEMPERATURE = 3;
    //public static final int AMOUNT_OF_SUBSTANCE = 5;
    //public static final int LUMINIOUS_INTENSITY = 6;
    static final int DATA = 4;
    static final int ANGLE = 5;

    //Derived Units
    static final int AREA = 6;
    static final int VOLUME = 7;
    static final int PRESSURE = 8;
    static final int ENERGY = 9;
    static final int POWER = 10;
    static final int SPEED = 11;
    public static final int CURRENCY = 12;

    //Instance Variables
    private int id;
    private int labelResId;
    private List<Unit> units;

    /**
     * Create a Converter object
     *
     * @param id         id of the conversion
     * @param labelResId string resource id for the conversion
     * @param unit       list of units contained in conversion
     */
    public Converter(@id int id, int labelResId, List<Unit> unit) {
        this.id = id;
        this.labelResId = labelResId;
        this.units = unit;
    }

    @id
    public int getId() {
        return id;
    }

    public int getLabelResID() {
        return labelResId;
    }

    public List<Unit> getUnits() {
        return units;
    }

    public int getUnitCount() {
        return units.size();
    }

    @IntDef({LENGTH, MASS, TIME, TEMPERATURE, DATA, ANGLE,
            AREA, VOLUME, PRESSURE, ENERGY, POWER, SPEED, CURRENCY})
    public @interface id {
    }
}