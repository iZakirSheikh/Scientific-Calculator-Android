package com.prime.calculator;

import android.content.Context;

import com.prime.calculator.Utility.Utils;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.operator.Operator;

class Evaluator {
    /**
     * This Function Receives an Expression as Parameter and formats it in such way That it is fit for
     * mxParser to Parse
     *
     * @param exp : String to be Formatted
     * @return Formatted String
     */
    private static String formatExpression(String exp, Context context) {
        exp = exp.replace(context.getString(R.string.op_sqrt), Utils.SQRT_PLACE_HOLDER)
                .replace(context.getString(R.string.op_cbrt), Utils.CBRT_PLACE_HOLDER)
                .replace(("√"), Utils.ROOT_PLACE_HOLDER)
                .replace(context.getString(R.string.fun_archypsin) + "(", "asinh(")
                .replace(context.getString(R.string.fun_archypcos) + "(", "acosh(")
                .replace(context.getString(R.string.fun_archyptan) + "(", "atanh(")
                .replace(context.getString(R.string.fun_log_base_10), "log10")
                .replace(context.getString(R.string.fun_log_base_2), "log2")
                .replace(context.getString(R.string.fun_log_base_e), "ln")
                .replace(context.getString(R.string.fun_log_base_n), "logn")
                .replace(context.getString(R.string.op_sub), "-")
                .replace(context.getString(R.string.op_mul), "*")
                .replace(context.getString(R.string.op_div), "/")
                .replace(context.getString(R.string.fun_arcsin) + "(", "asin(")
                .replace(context.getString(R.string.fun_arccos) + "(", "acos(")
                .replace(context.getString(R.string.fun_arctan) + "(", "atan(")
                .replace(String.valueOf(Utils.STANDARD_THOUSAND_SEPARATOR), "");
        return exp;
    }


    /** This Method Evaluates An incoming expression as String
     *
     * Used EvalEx and BigDecimalMath
     *      * no urnary factorial operator +slow
     *      * <p>
     *      * Removed mxParser as no Operator can be added which makes it hard to implement.
     *      * E.g. Instead of Sqrt symbol u have to use Sqrt function.
     *
     * @param exp Expression as String to be Evaluated
     * @param context Application Context
     * @param angleMode Angle mode DEG/GRAD/RAD use, works only if Expression contains Trigonometric ratios
     * @return Evaluated String i.e, Result
     */
    static String Evaluate(String exp, Context context, final int angleMode) {
        String Result = "";
        try {
            Operator factorial = new Operator(context.getString(R.string.op_fact),
                    1, true, Operator.PRECEDENCE_POWER + 1) {
                @Override
                public double apply(double... args) {
                    final int arg = (int) args[0];
                    if ((double) arg != args[0]) {
                        throw new IllegalArgumentException("Operand for factorial has to be an integer");
                    }
                    if (arg < 0) {
                        throw new IllegalArgumentException("The operand of the factorial can not be less than zero");
                    }
                    double result = 1;
                    for (int i = 1; i <= arg; i++) {
                        result *= i;
                    }
                    return result;
                }
            };
            /*Calculates Percent of A Double*/
            Operator percent = new Operator(context.getString(R.string.op_pct), 1,
                    true, Operator.PRECEDENCE_DIVISION) {
                @Override
                public double apply(double... args) {
                    final double arg = args[0];
                    return arg / 100;
                }
            };
            /*Calculates Modulus of A Double*/
            Operator modulus = new Operator(context.getString(R.string.op_mod), 2,
                    true, Operator.PRECEDENCE_MODULO) {
                @Override
                public double apply(double... args) {
                    return args[0] % args[1];
                }
            };
            /*Calculates SQRT of A Double*/
            Operator sqrt = new Operator(Utils.SQRT_PLACE_HOLDER, 1, true,
                    Operator.PRECEDENCE_POWER) {
                @Override
                public double apply(double... doubles) {
                    return Math.sqrt(doubles[0]);
                }
            };
            /*Calculates CBRT of A Double*/
            Operator cbrt = new Operator(Utils.CBRT_PLACE_HOLDER, 1, true,
                    Operator.PRECEDENCE_POWER) {
                @Override
                public double apply(double... doubles) {
                    return Math.cbrt(doubles[0]);
                }
            };
            /*Calculates nThRoot of A Double*/
            Operator root = new Operator(Utils.ROOT_PLACE_HOLDER, 2, true,
                    Operator.PRECEDENCE_POWER) {
                @Override
                public double apply(double... doubles) {
                    if (Double.isNaN(doubles[0]) || Double.isNaN(doubles[1])) return Double.NaN;
                    if (Double.isInfinite(doubles[0]) || Double.isInfinite(doubles[1]))
                        return Double.NaN;
                    if (doubles[0] == 1) return doubles[1];
                    if (doubles[0] == 2) return Math.sqrt(doubles[1]);
                    if (doubles[0] % 2 == 1) {
                        if (doubles[1] >= 0) return Math.pow(doubles[1], 1.0 / doubles[0]);
                        else return -Math.pow(Math.abs(doubles[1]), 1.0 / doubles[0]);
                    } else {
                        if (doubles[1] >= 0) return Math.pow(doubles[1], 1.0 / doubles[0]);
                        else return Double.NaN;
                    }
                }
            };

            /*Calculates arcsin of A Double*/
            Function asinh = new Function("asinh", 1) {
                @Override
                public double apply(double... doubles) {
                    if (Double.isNaN(doubles[0]))
                        return Double.NaN;
                    return Math.log(doubles[0] + Math.sqrt(doubles[0] * doubles[0] + 1));
                }
            };
            /*Calculates arccos of A Double*/
            Function acosh = new Function("acosh", 1) {
                @Override
                public double apply(double... doubles) {
                    if (Double.isNaN(doubles[0]))
                        return Double.NaN;
                    return Math.log(doubles[0] + Math.sqrt(doubles[0] * doubles[0] - 1));
                }
            };
            /*Calculates arctan of A Double*/
            Function atanh = new Function("atanh", 1) {
                @Override
                public double apply(double... doubles) {
                    if (Double.isNaN(doubles[0]))
                        return Double.NaN;
                    double result = Double.NaN;
                    if (1 - doubles[0] != 0)
                        result = 0.5 * Math.log((1 + doubles[0]) / (1 - doubles[0]));
                    return result;
                }
            };
            Function ln = new Function("ln", 1) {
                @Override
                public double apply(double... doubles) {
                    if (Double.isNaN(doubles[0]))
                        return Double.NaN;
                    return Math.log(doubles[0]);
                }
            };

            /**
             * General logarithm.
             *
             * @param      a                   the a function parameter (base)
             * @param      b                   the b function parameter (number)
             * @return if a,b &lt;&gt; Double.NaN and log(b) &lt;&gt; 0 returns Math.log(a) / Math.log(b),
             *             otherwise returns Double.NaN.
             */
            Function logn = new Function("logn", 2) {
                @Override
                public double apply(double... args) {
                    if (Double.isNaN(args[0]) || Double.isNaN(args[1]))
                        return Double.NaN;
                    double result = Double.NaN;
                    double logb = Math.log(args[1]);
                    if (logb != 0)
                        result = Math.log(args[0]) / logb;
                    return result;
                }
            };

            //Trigonometric sine Function
            Function sin = new Function("sin", 1) {
                @Override
                public double apply(double... doubles) {
                    if (Double.isNaN(doubles[0]))
                        return Double.NaN;
                    if (angleMode == Utils.MODE_DEG)
                    {
                        if(Utils.isClose(doubles[0]%180, 0))
                            doubles[0] = 0;
                        else if(Utils.isClose(doubles[0]%180, 30))
                            doubles[0] = Math.PI/6;
                        else if(Utils.isClose(doubles[0]%180, 90))
                            doubles[0] = Math.PI/2;
                        else
                            doubles[0] = toRadians(doubles[0], Utils.MODE_DEG);

                    }else if(angleMode == Utils.MODE_GRAD){
                        if(Utils.isClose(doubles[0]%200, 0 ))
                            doubles[0] = 0;
                        else if (Utils.isClose(doubles[0]%200, 33.3333333333333))
                            doubles[0] = Math.PI/6;
                        else if (Utils.isClose(doubles[0]%200, 100 ))
                            doubles[0] = Math.PI/2;
                        else
                            doubles[0] = toRadians(doubles[0], Utils.MODE_GRAD);

                    }else{
                        if(Utils.isClose(doubles[0]%Math.PI, 0 ))
                            doubles[0] = 0;
                        else if (Utils.isClose(doubles[0]%Math.PI, Math.PI/6))
                            doubles[0] = Math.PI/6;
                        else if (Utils.isClose(doubles[0]%Math.PI, Math.PI/2 ))
                            doubles[0] = Math.PI/2;
                    }

                    return Math.sin(doubles[0]);
                }
            };

            Function cos = new Function("cos", 1) {
                @Override
                public double apply(double... doubles) {
                    if (Double.isNaN(doubles[0]))
                        return Double.NaN;
                    if (angleMode == Utils.MODE_DEG)
                    {
                        if(Utils.isClose(doubles[0]%180, 0))
                            doubles[0] = 0;
                        else if(Utils.isClose(doubles[0]%180, 60))
                            doubles[0] = Math.PI/3;
                        else if(Utils.isClose(doubles[0]%180, 90))
                            doubles[0] = Math.PI/2;
                        else
                            doubles[0] = toRadians(doubles[0], Utils.MODE_DEG);

                    }else if(angleMode == Utils.MODE_GRAD){
                        if(Utils.isClose(doubles[0]%200, 0 ))
                            doubles[0] = 0;
                        else if (Utils.isClose(doubles[0]%200, 66.6666666666666))
                            doubles[0] = Math.PI/3;
                        else if (Utils.isClose(doubles[0]%200, 100 ))
                            doubles[0] = Math.PI/2;
                        else
                            doubles[0] = toRadians(doubles[0], Utils.MODE_GRAD);

                    }else{
                        if(Utils.isClose(doubles[0]%Math.PI, 0 ))
                            doubles[0] = 0;
                        else if (Utils.isClose(doubles[0]%Math.PI, Math.PI/3))
                            doubles[0] = Math.PI/3;
                        else if (Utils.isClose(doubles[0]%Math.PI, Math.PI/2 ))
                            doubles[0] = Math.PI/2;
                    }

                    return Math.cos(doubles[0]);
                }
            };


            Function tan = new Function("tan", 1) {
                @Override
                public double apply(double... doubles) {
                    if (Double.isNaN(doubles[0]))
                        return Double.NaN;
                    if (angleMode == Utils.MODE_DEG)
                    {
                        if(Utils.isClose(doubles[0]%180, 0))
                            doubles[0] = 0;
                        else if(Utils.isClose(doubles[0]%180, 45))
                            doubles[0] = Math.PI/4;
                        else if(Utils.isClose(doubles[0]%180, 90))
                            doubles[0] = Double.NaN;
                        else
                            doubles[0] = toRadians(doubles[0], Utils.MODE_DEG);

                    }else if(angleMode == Utils.MODE_GRAD){
                        if(Utils.isClose(doubles[0]%200, 0 ))
                            doubles[0] = 0;
                        else if (Utils.isClose(doubles[0]%200, 50))
                            doubles[0] = Math.PI/4;
                        else if (Utils.isClose(doubles[0]%200, 100))
                            doubles[0] = Double.NaN;
                        else
                            doubles[0] = toRadians(doubles[0], Utils.MODE_GRAD);

                    }else{
                        if(Utils.isClose(doubles[0]%Math.PI, 0 ))
                            doubles[0] = 0;
                        else if (Utils.isClose(doubles[0]%Math.PI, Math.PI/4))
                            doubles[0] = Math.PI/4;
                        else if (Utils.isClose(doubles[0]%Math.PI, Math.PI/2))
                            doubles[0] = Double.NaN;
                    }

                    return Math.tan(doubles[0]);
                }
            };

            Function asin = new Function("asin", 1) {
                @Override
                public double apply(double... doubles) {
                    if (Double.isNaN(doubles[0]))
                        return Double.NaN;
                    double result = Math.asin(doubles[0]);
                    if (angleMode == Utils.MODE_DEG) {
                        result = toAngle(Utils.MODE_DEG, result);
                    } else if (angleMode == Utils.MODE_GRAD) {
                        result = toAngle(Utils.MODE_GRAD, result);
                    }
                    return result;
                }
            };

            Function acos = new Function("acos", 1) {
                @Override
                public double apply(double... doubles) {
                    if (Double.isNaN(doubles[0]))
                        return Double.NaN;
                    double result = Math.acos(doubles[0]);
                    if (angleMode == Utils.MODE_DEG) {
                        result = toAngle(Utils.MODE_DEG, result);
                    } else if (angleMode == Utils.MODE_GRAD) {
                        result = toAngle(Utils.MODE_GRAD, result);
                    }
                    return result;
                }
            };

            Function atan = new Function("atan", 1) {
                @Override
                public double apply(double... doubles) {
                    if (Double.isNaN(doubles[0]))
                        return Double.NaN;
                    double result = Math.atan(doubles[0]);
                    if (angleMode == Utils.MODE_DEG) {
                        result = toAngle(Utils.MODE_DEG, result);
                    } else if (angleMode == Utils.MODE_GRAD) {
                        result = toAngle(Utils.MODE_GRAD, result);
                    }
                    return result;
                }
            };
            /**
             * This Section Actually Solves The Expression
             */
            Expression e = new ExpressionBuilder(formatExpression(Utils.getBalanced(exp), context))
                    .operator(factorial, percent, modulus, sqrt, cbrt, root)
                    .functions(acosh, asinh, atanh, logn, ln, sin, cos, tan, asin, acos, atan)
                    .build();
            Result = Utils.doubleToString(e.evaluate(), Utils.MAX_DIGITS, Utils.ROUNDING_DIGITS);

        } catch (Exception e) {
           // Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT);
        }
        return Result;
    }


    /**
     * This Function Receives An Angle and Converts it into Gradian/Degree
     *
     * @param mode   Convert to DEG/GRAD
     * @param angle: Angle in Radian
     * @return Angle in mode
     */

    private static double toAngle(int mode, double angle) {
        if (mode == Utils.MODE_DEG)
            return Math.toDegrees(angle);
        else
            return 200 / Math.PI * angle;
    }

    /**
     * This Function Converts angle from Degree/Gradian to Radian
     *
     * @param angle : in Deg/Grad
     * @param Mode  :DEG OR GRAD
     * @return Angle in Radian
     */

    private static double toRadians(double angle, int Mode) {
        if (Mode == Utils.MODE_DEG)
            return Math.toRadians(angle);
        else
            return Math.PI / 200 * angle;
    }
}
