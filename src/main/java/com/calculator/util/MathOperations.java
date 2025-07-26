package com.calculator.util;

/**
 * 数学运算工具类
 * 提供各种科学计算器所需的数学函数和常量
 */
public class MathOperations {

    // 数学常量
    public static final double PI = Math.PI;
    public static final double E = Math.E;
    
    /**
     * 角度转弧度
     */
    public static double toRadians(double degrees) {
        return Math.toRadians(degrees);
    }
    
    /**
     * 弧度转角度
     */
    public static double toDegrees(double radians) {
        return Math.toDegrees(radians);
    }
    
    /**
     * 正弦函数
     * @param angle 角度值（根据isDegree决定是角度还是弧度）
     * @param isDegree 是否为角度模式
     * @return 正弦值
     */
    public static double sin(double angle, boolean isDegree) {
        if (isDegree) {
            angle = toRadians(angle);
        }
        return Math.sin(angle);
    }
    
    /**
     * 余弦函数
     * @param angle 角度值（根据isDegree决定是角度还是弧度）
     * @param isDegree 是否为角度模式
     * @return 余弦值
     */
    public static double cos(double angle, boolean isDegree) {
        if (isDegree) {
            angle = toRadians(angle);
        }
        return Math.cos(angle);
    }
    
    /**
     * 正切函数
     * @param angle 角度值（根据isDegree决定是角度还是弧度）
     * @param isDegree 是否为角度模式
     * @return 正切值
     */
    public static double tan(double angle, boolean isDegree) {
        if (isDegree) {
            angle = toRadians(angle);
        }
        return Math.tan(angle);
    }
    
    /**
     * 反正弦函数
     * @param value 值
     * @param isDegree 是否返回角度（否则返回弧度）
     * @return 反正弦值
     */
    public static double asin(double value, boolean isDegree) {
        double result = Math.asin(value);
        if (isDegree) {
            result = toDegrees(result);
        }
        return result;
    }
    
    /**
     * 反余弦函数
     * @param value 值
     * @param isDegree 是否返回角度（否则返回弧度）
     * @return 反余弦值
     */
    public static double acos(double value, boolean isDegree) {
        double result = Math.acos(value);
        if (isDegree) {
            result = toDegrees(result);
        }
        return result;
    }
    
    /**
     * 反正切函数
     * @param value 值
     * @param isDegree 是否返回角度（否则返回弧度）
     * @return 反正切值
     */
    public static double atan(double value, boolean isDegree) {
        double result = Math.atan(value);
        if (isDegree) {
            result = toDegrees(result);
        }
        return result;
    }
    
    /**
     * 自然对数
     */
    public static double ln(double value) {
        return Math.log(value);
    }
    
    /**
     * 以10为底的对数
     */
    public static double log10(double value) {
        return Math.log10(value);
    }
    
    /**
     * 以指定底数的对数
     */
    public static double log(double base, double value) {
        return Math.log(value) / Math.log(base);
    }
    
    /**
     * 指数函数 e^x
     */
    public static double exp(double value) {
        return Math.exp(value);
    }
    
    /**
     * 幂函数
     */
    public static double pow(double base, double exponent) {
        return Math.pow(base, exponent);
    }
    
    /**
     * 平方根
     */
    public static double sqrt(double value) {
        return Math.sqrt(value);
    }
    
    /**
     * 立方根
     */
    public static double cbrt(double value) {
        return Math.cbrt(value);
    }
    
    /**
     * 阶乘
     */
    public static double factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("阶乘不能用于负数");
        }
        if (n == 0 || n == 1) {
            return 1;
        }
        double result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }
    
    /**
     * 绝对值
     */
    public static double abs(double value) {
        return Math.abs(value);
    }
    
    /**
     * 四舍五入到指定小数位
     */
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException("小数位数不能为负数");
        
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
    
    /**
     * 将数字格式化为科学记数法（如果需要）
     */
    public static String formatScientific(double value, int precision) {
        if (value == 0) return "0";
        
        double absValue = Math.abs(value);
        if (absValue >= 1e10 || absValue < 1e-4) {
            // 使用科学记数法
            return String.format("%." + precision + "e", value);
        } else {
            // 使用普通表示法
            return String.format("%." + precision + "f", value).replaceAll("0*$", "").replaceAll("\\.$", "");
        }
    }

    /**
     * 进制转换：十进制转二进制
     */
    public static String decimalToBinary(long decimal) {
        return Long.toBinaryString(decimal);
    }

    /**
     * 进制转换：十进制转八进制
     */
    public static String decimalToOctal(long decimal) {
        return Long.toOctalString(decimal);
    }

    /**
     * 进制转换：十进制转十六进制
     */
    public static String decimalToHex(long decimal) {
        return Long.toHexString(decimal).toUpperCase();
    }

    /**
     * 进制转换：二进制转十进制
     */
    public static long binaryToDecimal(String binary) {
        return Long.parseLong(binary, 2);
    }

    /**
     * 进制转换：八进制转十进制
     */
    public static long octalToDecimal(String octal) {
        return Long.parseLong(octal, 8);
    }

    /**
     * 进制转换：十六进制转十进制
     */
    public static long hexToDecimal(String hex) {
        return Long.parseLong(hex, 16);
    }
}
