package com.prime.calculator.UnitConverter;

import androidx.annotation.IntDef;

/**
 * This class defines Model for Unit used in conversion process
 * @author Zakir Ahmad Sheikh
 * @since 26-03-2019
 * @version 1.0
 */

public class Unit {
    //Units of Length in increasing Order
     static final int YOCTOMETRE = 100;
     static final int ZEPTOMETRE = 101;
     static final int ATTOMETRE = 102;
     static final int FEMTOMETRE = 103;
     static final int PICOMETRE = 104;
     static final int NANOMETRE = 105;
     static final int MICROMETRE = 106;
     static final int MILLIMETRE = 107;
     static final int CENTIMETRE = 108;
     public static final int METRE = 109;
     public static final int KILOMETRE = 110;

     static final int MILE = 120;
     static final int NAUTICAL_MILE = 121;
     static final int INCH = 122;
     static final int FOOT = 123;
     static final int YARD = 124;

     static final int ASTRONIMICAL_UNIT = 130;
     static final int LIGHT_YEAR = 131;
     static final int PARSEC = 132;

     static final int PLANK_LENGTH = 140;

    //Units of Mass

     static final int CARAT = 200;
     static final int MILLIGRAM = 201;
     static final int CENTIGRAM = 202;
     static final int DECIGRAM = 203;
     static final int GRAM = 204;
     static final int DECAGRAM = 205;
     static final int HECTOGRAM = 206;
     static final int KILOGRAM = 207;
     static final int METRIC_TONNE = 208;
     static final int OUNCE = 220;
     static final int POUND = 221;
     static final int STONE = 222;
     static final int SHORT_TONNE_US = 223;
     static final int LONG_TONNE_UK = 224;

    //Units of Time
     static final int PLANK_TIME = 300;
     static final int YOCTOSECOND = 301;
     static final int JIFFY_PHYSICS = 302;
     static final int ZEPTOZSECOND = 303;
     static final int ATTOSECOND = 304;
     static final int FEMTOSECOND = 305;
     static final int SEVEDBERG = 306;
     static final int PICOSECOND = 307;
     static final int NANOSECOND = 308;
     static final int SHAKE = 309;
     static final int MICROSECOND = 310;
     static final int MILLISECOND = 311;
     static final int JEFFY_ELECTRONICS = 312;
     static final int SECOND = 313;
     static final int MINUTE = 314;
     static final int MOMENT = 315;
     static final int KILOSECOND = 316;
     static final int HOUR = 317;
     static final int DAY = 318;
     static final int WEEK = 319;
     static final int MEGASECOND = 320;
     static final int MONTH = 321;
     static final int YEAR = 322;
     static final int DECADE = 323;
     static final int SCORE = 324;
     static final int JUBLEE = 325;
     static final int CENTURY = 326;
     static final int MILLENNIUM = 327;
     static final int EPOCH = 328;
     static final int EON = 329;
     static final int AEON = 330;
     static final int TERRASECOND = 331;
     static final int MEGANNUM = 332;
     static final int PETASECOND = 333;
     static final int DAY_OF_GOD = 334;
     static final int EXASECOND = 335;
     static final int ZETTASECOND = 336;
     static final int YOTTASECOND = 337;

    //UNits of ELECTRIC CUrrent
     static final int MILLIAMPHERE = 401;
     static final int AMPHERE = 402;
     static final int KILOAMPHERE = 403;

    //Units of Tempareture

     static final int CELSIUS = 501;
     static final int FAHRENHEIT = 502;
     static final int KELVIN = 503;
     static final int RANKINE = 504;
     static final int DELISLE = 505;
     static final int NEWTON = 506;
     static final int REAUMUR = 507;
     static final int ROMER = 508;

    //Units of Amount of substance

    //UNits of Luminous Intensity

    //Units of Data
     static final int BIT = 800;
     static final int BYTE = 801;
     static final int KILOBIT = 802;
     static final int KIBIBIT = 803;
     static final int KILOBYTE = 804;
     static final int KIBIBYTE = 805;
     static final int MEGABIT = 806;
     static final int MIBIBIT = 807;
     static final int MEGABYTE = 808;
     static final int MIBIBYTE = 809;
     static final int GIGABIT = 810;
     static final int GIBIBIT = 811;
     static final int GIGABYTE = 812;
     static final int GIBIBYTE = 813;
     static final int TERABIT = 814;
     static final int TEBIBIT = 815;
     static final int TERABYTE = 816;
     static final int TEBIBYTE = 817;
     static final int PETABIT = 818;
     static final int PEBIBIT = 819;
     static final int PETABYTE = 820;
     static final int PEBIBYTE = 821;
     static final int EXABIT = 822;
     static final int EXBIBIT = 823;
     static final int EXABYTE = 824;
     static final int EXBIBYTE = 825;
     static final int ZETABIT = 826;
     static final int ZEBIBIT = 827;
     static final int ZETABYTE = 828;
     static final int ZEBIBYTE = 829;
     static final int YOTTABIT = 830;
     static final int YOBIBIT = 831;
     static final int YOTTABYTE = 832;
     static final int YOBIBYTE = 833;

    //Units of AREA
     static final int SQUARE_MILIMETRE = 900;
     static final int SQUARE_CENTIMETRE = 901;
     static final int SQUARE_METRE = 902;
     static final int HECTARE = 903;
     static final int SQUARE_KILOMETRE = 904;
     static final int SQUARE_INCH = 905;
     static final int SQUARE_FOOT = 906;
     static final int SQUARE_YARD = 907;
     static final int ACRE = 908;
     static final int SQUARE_MILE = 909;

    //Units of Volume
     static final int MILLILITRE = 1001;
     static final int CUBIC_CENTIMETRE = 1002;
     static final int LITRE = 1003;
     static final int CUBIC_METRE = 1004;
     static final int TEASPOON_US = 1005;
     static final int TABLESPOON_US = 1006;
     static final int FLUID_OUNCE_US = 1007;
     static final int CUP_US = 1008;
     static final int PINT_US = 1009;
     static final int QUART_US = 1010;
     static final int GALLON_US = 1011;
     static final int CUBIC_INCH = 1012;
     static final int CUBIC_FOOT = 1013;
     static final int CUBIC_YARD = 1014;
     static final int TEASPOON_UK = 1015;
     static final int TABLESPOON_UK = 1016;
     static final int FLUID_OUNCE_UK = 1017;
     static final int PINT_UK = 1018;
     static final int QUART_UK = 1019;
     static final int GALLON_UK = 1020;

    //Units of Pressure
     static final int ATMOSPHERE = 1100;
     static final int BAR = 1101;
     static final int KILOPASCAL = 1102;
     static final int MILLIMETRE_OF_MERCURY = 1103;
     static final int PASCAL = 1104;
     static final int POUNDS_PER_INCH = 1105;


    //Units of Energy
     static final int ELECTRON_VOLT = 1200;
     static final int JOULE = 1201;
     static final int KILOJOULE = 1202;
     static final int THERMAL_CALORIE = 1203;
     static final int FOOD_CALORIE = 1204;
     static final int FOOT_POUND = 1205;
     static final int BRITISH_THERMAL_UNIT = 1206;

    //Units of Power
     static final int WATT = 1300;
     static final int KILOWATT = 1301;
     static final int HORSEPOWER_US = 1302;
     static final int FOOT_POUNDS_PER_MINUTE = 1303;
     static final int BRITISH_THERMAL_UNITS_PER_MINUTE = 1304;

    //Units of Speed
     static final int CENTIMETRES_PER_SEC = 1401;
     static final int METRES_PER_SECOND = 1402;
     static final int KILOMETRES_PER_HOUR = 1403;
     static final int MILES_PER_HOUR = 1404;
     static final int KNOTS = 1405;
     static final int MACH = 1406;
     static final int LIGHT_SPEED = 1407;
     static final int FEET_PER_SECOND = 1408;

    //Units of Angle
     static final int DEGREE = 1500;
     static final int RADIAN = 1501;
     static final int GRADIAN = 1502;

    //Units of Currency
     static final int UNITED_ARAB_EMIRATES_DIRHAM = 1600;
     static final int AFGHAN_AFGHANI = 1601;
     static final int ALBANIAN_LEK = 1602;
     static final int ARMENIAN_DRAM = 1603;
     static final int NETHERLANDS_ANTILLEAN_GUILDER = 1604;
     static final int ANGOLAN_KWANZA = 1605;
     static final int ARGENTINE_PESO = 1606;
     static final int AUSTRALIAN_DOLLAR = 1607;
     static final int ARUBAN_FLORIN = 1608;
     static final int AZERBAIJANI_MANAT = 1609;
     static final int BOSNIA_HERZEGOVINA_CONVERTIBLE_MARK = 1610;
     static final int BARBADIAN_DOLLAR = 1611;
     static final int BANGLADESHI_TAKA = 1612;
     static final int BULGARIAN_LEV = 1613;
     public static final int BAHRAINI_DINAR = 1614;
     static final int BURUNDIAN_FRANC = 1615;
     static final int BERMUDAN_DOLLAR = 1616;
     static final int BRUNEI_DOLLAR = 1617;
     static final int BOLIVIAN_BOLIVIANO = 1618;
     static final int BRAZILIAN_REAL = 1619;
     static final int BAHAMIAN_DOLLAR = 1620;
     static final int BITCOIN = 1621;
     static final int BHUTANESE_NGULTRUM = 1622;
     static final int BOTSWANAN_PULA = 1623;
     static final int NEW_BELARUSIAN_RUBLE = 1624;
     static final int BELARUSIAN_RUBLE = 1625;
     static final int BELIZE_DOLLAR = 1626;
     static final int CANADIAN_DOLLAR = 1627;
     static final int CONGOLESE_FRANC = 1628;
     static final int SWISS_FRANC = 1629;
     static final int CHILEAN_UNIT_OF_ACCOUNT_UF = 1630;
     static final int CHILEAN_PESO = 1631;
     static final int CHINESE_YUAN = 1632;
     static final int COLOMBIAN_PESO = 1633;
     static final int COSTA_RICAN_COLON = 1634;
     static final int CUBAN_CONVERTIBLE_PESO = 1635;
     static final int CUBAN_PESO = 1636;
     static final int CAPE_VERDEAN_ESCUDO = 1637;
     static final int CZECH_REPUBLIC_KORUNA = 1638;
     static final int DJIBOUTIAN_FRANC = 1639;
     static final int DANISH_KRONE = 1640;
     static final int DOMINICAN_PESO = 1641;
     static final int ALGERIAN_DINAR = 1642;
     static final int EGYPTIAN_POUND = 1643;
     static final int ERITREAN_NAKFA = 1644;
     static final int ETHIOPIAN_BIRR = 1645;
     static final int EURO = 1646;
     static final int FIJIAN_DOLLAR = 1647;
     static final int FALKLAND_ISLANDS_POUND = 1648;
     static final int BRITISH_POUND_STERLING = 1649;
     static final int GEORGIAN_LARI = 1650;
     static final int GUERNSEY_POUND = 1651;
     static final int GHANAIAN_CEDI = 1652;
     static final int GIBRALTAR_POUND = 1653;
     static final int GAMBIAN_DALASI = 1654;
     static final int GUINEAN_FRANC = 1655;
     static final int GUATEMALAN_QUETZAL = 1656;
     static final int GUYANAESE_DOLLAR = 1657;
     static final int HONG_KONG_DOLLAR = 1658;
     static final int HONDURAN_LEMPIRA = 1659;
     static final int CROATIAN_KUNA = 1660;
     static final int HAITIAN_GOURDE = 1661;
     static final int HUNGARIAN_FORINT = 1662;
     static final int INDONESIAN_RUPIAH = 1663;
     static final int ISRAELI_NEW_SHEQEL = 1664;
     static final int MANX_POUND = 1665;
     static final int INDIAN_RUPEE = 1666;
     static final int IRAQI_DINAR = 1667;
     static final int IRANIAN_RIAL = 1668;
     static final int ICELANDIC_KRONA = 1669;
     static final int JERSEY_POUND = 1670;
     static final int JAMAICAN_DOLLAR = 1671;
     static final int JORDANIAN_DINAR = 1672;
     static final int JAPANESE_YEN = 1673;
     static final int KENYAN_SHILLING = 1674;
     static final int KYRGYSTANI_SOM = 1675;
     static final int CAMBODIAN_RIEL = 1676;
     static final int COMORIAN_FRANC = 1677;
     static final int NORTH_KOREAN_WON = 1678;
     static final int SOUTH_KOREAN_WON = 1679;
     static final int KUWAITI_DINAR = 1680;
     static final int CAYMAN_ISLANDS_DOLLAR = 1681;
     static final int KAZAKHSTANI_TENGE = 1682;
     static final int LAOTIAN_KIP = 1683;
     static final int LEBANESE_POUND = 1684;
     static final int SRI_LANKAN_RUPEE = 1685;
     static final int LIBERIAN_DOLLAR = 1686;
     static final int LESOTHO_LOTI = 1687;
     static final int LITHUANIAN_LITAS = 1688;
     static final int LATVIAN_LATS = 1689;
     static final int LIBYAN_DINAR = 1690;
     static final int MOROCCAN_DIRHAM = 1691;
     static final int MOLDOVAN_LEU = 1692;
     static final int MALAGASY_ARIARY = 1693;
     static final int MACEDONIAN_DENAR = 1694;
     static final int MYANMA_KYAT = 1695;
     static final int MONGOLIAN_TUGRIK = 1696;
     static final int MACANESE_PATACA = 1697;
     static final int MAURITANIAN_OUGUIYA = 1698;
     static final int MAURITIAN_RUPEE = 1699;
     static final int MALDIVIAN_RUFIYAA = 1700;
     static final int MALAWIAN_KWACHA = 1701;
     static final int MEXICAN_PESO = 1702;
     static final int MALAYSIAN_RINGGIT = 1703;
     static final int MOZAMBICAN_METICAL = 1704;
     static final int NAMIBIAN_DOLLAR = 1705;
     static final int NIGERIAN_NAIRA = 1706;
     static final int NICARAGUAN_CORDOBA = 1707;
     static final int NORWEGIAN_KRONE = 1708;
     static final int NEPALESE_RUPEE = 1709;
     static final int NEW_ZEALAND_DOLLAR = 1710;
     static final int OMANI_RIAL = 1711;
     static final int PANAMANIAN_BALBOA = 1712;
     static final int PERUVIAN_NUEVO_SOL = 1713;
     static final int PAPUA_NEW_GUINEAN_KINA = 1714;
     static final int PHILIPPINE_PESO = 1715;
     public static final int PAKISTANI_RUPEE = 1716;
     static final int POLISH_ZLOTY = 1717;
     static final int PARAGUAYAN_GUARANI = 1718;
     static final int QATARI_RIAL = 1719;
     static final int ROMANIAN_LEU = 1720;
     static final int SERBIAN_DINAR = 1721;
     static final int RUSSIAN_RUBLE = 1722;
     static final int RWANDAN_FRANC = 1723;
     static final int SAUDI_RIYAL = 1724;
     static final int SOLOMON_ISLANDS_DOLLAR = 1725;
     static final int SEYCHELLOIS_RUPEE = 1726;
     static final int SUDANESE_POUND = 1727;
     static final int SWEDISH_KRONA = 1728;
     static final int SINGAPORE_DOLLAR = 1729;
     static final int SAINT_HELENA_POUND = 1730;
     static final int SIERRA_LEONEAN_LEONE = 1731;
     static final int SOMALI_SHILLING = 1732;
     static final int SURINAMESE_DOLLAR = 1733;
     static final int DOBRA = 1734;
     static final int SALVADORAN_COLON = 1735;
     static final int SYRIAN_POUND = 1736;
     static final int SWAZI_LILANGENI = 1737;
     static final int THAI_BAHT = 1738;
     static final int TAJIKISTANI_SOMONI = 1739;
     static final int TURKMENISTANI_MANAT = 1740;
     static final int TUNISIAN_DINAR = 1741;
     static final int TONGAN_PAANGA = 1742;
     static final int TURKISH_LIRA = 1743;
     static final int TRINIDAD_AND_TOBAGO_DOLLAR = 1744;
     static final int NEW_TAIWAN_DOLLAR = 1745;
     static final int TANZANIAN_SHILLING = 1746;
     static final int UKRAINIAN_HRYVNIA = 1747;
     static final int UGANDAN_SHILLING = 1748;
     static final int UNITED_STATES_DOLLAR = 1749;
     static final int URUGUAYAN_PESO = 1750;
     static final int UZBEKISTAN_SOM = 1751;
     static final int VENEZUELAN_BOLIVAR_FUERTE = 1752;
     static final int VIETNAMESE_DONG = 1753;
     static final int VANUATU_VATU = 1754;
     static final int SAMOAN_TALA = 1755;
     static final int CFA_FRANC_BEAC = 1756;
     static final int SILVER_TROY_OUNCE = 1757;
     static final int GOLD_TROY_OUNCE = 1758;
     static final int EAST_CARIBBEAN_DOLLAR = 1759;
     static final int SPECIAL_DRAWING_RIGHTS = 1760;
     static final int CFA_FRANC_BCEAO = 1761;
     static final int CFP_FRANC = 1762;
     static final int YEMENI_RIAL = 1763;
     static final int SOUTH_AFRICAN_RAND = 1764;
     static final int ZAMBIAN_KWACHA_PRE_2013 = 1765;
     static final int ZAMBIAN_KWACHA = 1766;
     static final int ZIMBABWEAN_DOLLAR = 1767;



    private int id;
    private double inBase; //value in base units 1 km in base equals 1000m
    private double baseIn ; // value from base 1 km from base equals 1/1000 = 0.001km
    private int labelResId;
    private String currency;
    private String code;

    /**
     * Create a unit object for General Unit
     *
     * @param id         id of the unit
     * @param labelResId string resource id of the currency
     * @param inBase     the value to convert to the base unit of the conversion
     * @param baseIn     the value to convert from the base unit of the conversion
     */
    public Unit(@id int id, int labelResId, double inBase, double baseIn) {
        this.id = id;
        this.labelResId = labelResId;
        this.inBase = inBase;
        this.baseIn = baseIn;
    }

    /**
     * Create a unit object for Unit With Formula Like Temperature
     * @param id : id of Unit
     * @param labelResId : its resource id
     */
    public Unit(@id int id, int labelResId) {
        this.id = id;
        this.labelResId = labelResId;
    }

    /**
     * Creates a Unit Object for Currency
     * @param id : its id
     * @param currency : name of Currency
     * @param code : short name;
     * @param inBase     the value to convert to the base unit of the conversion
     * @param baseIn     the value to convert from the base unit of the conversion
     */
    public Unit(@id int id, String currency, String code, double inBase, double baseIn){
        this.id = id;
        this.currency = currency;
        this.code = code;
        this.inBase = inBase;
        this.baseIn = baseIn;
    }

    String getCode(){
        return code;
    }

    public String getCurrency(){
        return currency;
    }

    @id
    public int getId() {
        return id;
    }

    double getInBaseValue() {
        return inBase;
    }

    double getBaseInValue() {
        return baseIn;
    }

    public int getLabelResId() {
        return labelResId;
    }

    @IntDef({
            YOCTOMETRE, ZEPTOMETRE, ATTOMETRE, FEMTOMETRE, PICOMETRE, NANOMETRE, MICROMETRE,
            MILLIMETRE, CENTIMETRE, METRE, KILOMETRE, MILE, NAUTICAL_MILE, INCH, FOOT, YARD, ASTRONIMICAL_UNIT,
            LIGHT_YEAR, PARSEC, PLANK_LENGTH,

            CARAT, MILLIGRAM, CENTIGRAM, DECIGRAM, GRAM, DECAGRAM, HECTOGRAM, KILOGRAM, METRIC_TONNE,
            OUNCE, POUND, STONE, SHORT_TONNE_US, LONG_TONNE_UK,

            PLANK_TIME, YOCTOSECOND, JIFFY_PHYSICS, ZEPTOZSECOND, ATTOSECOND, FEMTOSECOND, SEVEDBERG,
            PICOSECOND, NANOSECOND, SHAKE, MICROSECOND, MILLISECOND, JEFFY_ELECTRONICS, SECOND, MINUTE,
            MOMENT, KILOSECOND, HOUR, DAY, WEEK, MEGASECOND, MONTH, YEAR, DECADE, SCORE, JUBLEE,
            CENTURY, MILLENNIUM, EPOCH, EON, AEON, TERRASECOND, MEGANNUM, PETASECOND, DAY_OF_GOD, EXASECOND,
            ZETTASECOND, YOTTASECOND,

            AMPHERE, MILLIAMPHERE, KILOAMPHERE,

            CELSIUS, FAHRENHEIT, KELVIN, RANKINE, DELISLE, NEWTON, REAUMUR, ROMER,

            BIT, BYTE, KILOBIT, KIBIBIT, KILOBYTE, KIBIBYTE, MEGABIT, MIBIBIT, MEGABYTE, MIBIBYTE, GIGABIT, GIBIBIT,
            GIGABYTE, GIBIBYTE, TERABIT, TEBIBIT, TERABYTE, TEBIBYTE, PETABIT, PEBIBIT, PETABYTE,
            PEBIBYTE, EXABIT, EXBIBIT, EXABYTE, EXBIBYTE, ZETABIT, ZEBIBIT, ZETABYTE, ZEBIBYTE, YOTTABIT,
            YOBIBIT, YOTTABYTE, YOBIBYTE,

            SQUARE_MILIMETRE, SQUARE_CENTIMETRE, SQUARE_METRE, HECTARE, SQUARE_KILOMETRE, SQUARE_INCH,
            SQUARE_FOOT, SQUARE_YARD, ACRE, SQUARE_MILE,

            MILLILITRE, CUBIC_CENTIMETRE, LITRE, CUBIC_METRE, TEASPOON_US, TABLESPOON_US, FLUID_OUNCE_US,
            CUP_US, PINT_US, QUART_US, GALLON_US, CUBIC_INCH, CUBIC_FOOT, CUBIC_YARD, TEASPOON_UK, TABLESPOON_UK,
            FLUID_OUNCE_UK, PINT_UK, QUART_UK, GALLON_UK,

            ATMOSPHERE, BAR, KILOPASCAL, MILLIMETRE_OF_MERCURY, PASCAL, POUNDS_PER_INCH,

            ELECTRON_VOLT, JOULE, KILOJOULE, THERMAL_CALORIE, FOOD_CALORIE, FOOT_POUND, BRITISH_THERMAL_UNIT,

            WATT, KILOWATT, HORSEPOWER_US, FOOT_POUNDS_PER_MINUTE, BRITISH_THERMAL_UNITS_PER_MINUTE,

            CENTIMETRES_PER_SEC, METRES_PER_SECOND, KILOMETRES_PER_HOUR, MILES_PER_HOUR, KNOTS, MACH, LIGHT_SPEED,
            FEET_PER_SECOND,

            DEGREE, RADIAN, GRADIAN,

            UNITED_ARAB_EMIRATES_DIRHAM, AFGHAN_AFGHANI, ALBANIAN_LEK, ARMENIAN_DRAM, NETHERLANDS_ANTILLEAN_GUILDER,
            ANGOLAN_KWANZA, ARGENTINE_PESO, AUSTRALIAN_DOLLAR, ARUBAN_FLORIN, AZERBAIJANI_MANAT,
            BOSNIA_HERZEGOVINA_CONVERTIBLE_MARK, BARBADIAN_DOLLAR, BANGLADESHI_TAKA, BULGARIAN_LEV,
            BAHRAINI_DINAR, BURUNDIAN_FRANC, BERMUDAN_DOLLAR, BRUNEI_DOLLAR, BOLIVIAN_BOLIVIANO, BRAZILIAN_REAL,
            BAHAMIAN_DOLLAR, BITCOIN, BHUTANESE_NGULTRUM, BOTSWANAN_PULA, NEW_BELARUSIAN_RUBLE, BELARUSIAN_RUBLE,
            BELIZE_DOLLAR, CANADIAN_DOLLAR, CONGOLESE_FRANC, SWISS_FRANC, CHILEAN_UNIT_OF_ACCOUNT_UF,
            CHILEAN_PESO, CHINESE_YUAN, COLOMBIAN_PESO, COSTA_RICAN_COLON, CUBAN_CONVERTIBLE_PESO,
            CUBAN_PESO, CAPE_VERDEAN_ESCUDO, CZECH_REPUBLIC_KORUNA, DJIBOUTIAN_FRANC, DANISH_KRONE,
            DOMINICAN_PESO, ALGERIAN_DINAR, EGYPTIAN_POUND, ERITREAN_NAKFA, ETHIOPIAN_BIRR, EURO,
            FIJIAN_DOLLAR, FALKLAND_ISLANDS_POUND, BRITISH_POUND_STERLING, GEORGIAN_LARI, GUERNSEY_POUND,
            GHANAIAN_CEDI, GIBRALTAR_POUND, GAMBIAN_DALASI, GUINEAN_FRANC, GUATEMALAN_QUETZAL, GUYANAESE_DOLLAR,
            HONG_KONG_DOLLAR, HONDURAN_LEMPIRA, CROATIAN_KUNA, HAITIAN_GOURDE, HUNGARIAN_FORINT, INDONESIAN_RUPIAH,
            ISRAELI_NEW_SHEQEL, MANX_POUND, INDIAN_RUPEE, IRAQI_DINAR, IRANIAN_RIAL, ICELANDIC_KRONA, JERSEY_POUND,
            JAMAICAN_DOLLAR, JORDANIAN_DINAR, JAPANESE_YEN, KENYAN_SHILLING, KYRGYSTANI_SOM, CAMBODIAN_RIEL,
            COMORIAN_FRANC, NORTH_KOREAN_WON, SOUTH_KOREAN_WON, KUWAITI_DINAR, CAYMAN_ISLANDS_DOLLAR,
            KAZAKHSTANI_TENGE, LAOTIAN_KIP, LEBANESE_POUND, SRI_LANKAN_RUPEE, LIBERIAN_DOLLAR, LESOTHO_LOTI,
            LITHUANIAN_LITAS, LATVIAN_LATS, LIBYAN_DINAR, MOROCCAN_DIRHAM, MOLDOVAN_LEU, MALAGASY_ARIARY,
            MACEDONIAN_DENAR, MYANMA_KYAT, MONGOLIAN_TUGRIK, MACANESE_PATACA, MAURITANIAN_OUGUIYA,
            MAURITIAN_RUPEE, MALDIVIAN_RUFIYAA, MALAWIAN_KWACHA, MEXICAN_PESO, MALAYSIAN_RINGGIT,
            MOZAMBICAN_METICAL, NAMIBIAN_DOLLAR, NIGERIAN_NAIRA, NICARAGUAN_CORDOBA, NORWEGIAN_KRONE,
            NEPALESE_RUPEE, NEW_ZEALAND_DOLLAR, OMANI_RIAL, PANAMANIAN_BALBOA, PERUVIAN_NUEVO_SOL,
            PAPUA_NEW_GUINEAN_KINA, PHILIPPINE_PESO, PAKISTANI_RUPEE, POLISH_ZLOTY, PARAGUAYAN_GUARANI,
            QATARI_RIAL, ROMANIAN_LEU, SERBIAN_DINAR, RUSSIAN_RUBLE, RWANDAN_FRANC, SAUDI_RIYAL,
            SOLOMON_ISLANDS_DOLLAR, SEYCHELLOIS_RUPEE, SUDANESE_POUND, SWEDISH_KRONA, SINGAPORE_DOLLAR,
            SAINT_HELENA_POUND, SIERRA_LEONEAN_LEONE, SOMALI_SHILLING, SURINAMESE_DOLLAR, DOBRA,
            SALVADORAN_COLON, SYRIAN_POUND, SWAZI_LILANGENI, THAI_BAHT, TAJIKISTANI_SOMONI, TURKMENISTANI_MANAT,
            TUNISIAN_DINAR, TONGAN_PAANGA, TURKISH_LIRA, TRINIDAD_AND_TOBAGO_DOLLAR, NEW_TAIWAN_DOLLAR,
            TANZANIAN_SHILLING, UKRAINIAN_HRYVNIA, UGANDAN_SHILLING, UNITED_STATES_DOLLAR, URUGUAYAN_PESO,
            UZBEKISTAN_SOM, VENEZUELAN_BOLIVAR_FUERTE, VIETNAMESE_DONG, VANUATU_VATU, SAMOAN_TALA,
            CFA_FRANC_BEAC, SILVER_TROY_OUNCE, GOLD_TROY_OUNCE, EAST_CARIBBEAN_DOLLAR, SPECIAL_DRAWING_RIGHTS,
            CFA_FRANC_BCEAO, CFP_FRANC, YEMENI_RIAL, SOUTH_AFRICAN_RAND, ZAMBIAN_KWACHA_PRE_2013, ZAMBIAN_KWACHA, ZIMBABWEAN_DOLLAR,
    })
    public @interface id {
    }
}