package com.calculator.service;

import com.calculator.model.CalculationRequest;
import com.calculator.model.CalculationResponse;
import com.calculator.util.MathOperations;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.function.Function;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * 计算器服务实现类
 */
@Service
public class CalculatorServiceImpl implements CalculatorService {

    @Override
    public CalculationResponse calculate(CalculationRequest request) {
        try {
            // 获取表达式和参数
            String expression = request.getExpression();
            boolean degreeMode = request.isDegreeMode();
            int precision = request.getPrecision();

            // 创建自定义函数，处理角度/弧度模式
            Function sinFunction = new Function("sin", 1) {
                @Override
                public double apply(double... args) {
                    return MathOperations.sin(args[0], degreeMode);
                }
            };

            Function cosFunction = new Function("cos", 1) {
                @Override
                public double apply(double... args) {
                    return MathOperations.cos(args[0], degreeMode);
                }
            };

            Function tanFunction = new Function("tan", 1) {
                @Override
                public double apply(double... args) {
                    return MathOperations.tan(args[0], degreeMode);
                }
            };

            Function asinFunction = new Function("asin", 1) {
                @Override
                public double apply(double... args) {
                    return MathOperations.asin(args[0], degreeMode);
                }
            };

            Function acosFunction = new Function("acos", 1) {
                @Override
                public double apply(double... args) {
                    return MathOperations.acos(args[0], degreeMode);
                }
            };

            Function atanFunction = new Function("atan", 1) {
                @Override
                public double apply(double... args) {
                    return MathOperations.atan(args[0], degreeMode);
                }
            };

            Function lnFunction = new Function("ln", 1) {
                @Override
                public double apply(double... args) {
                    return MathOperations.ln(args[0]);
                }
            };

            Function logFunction = new Function("log", 1) {
                @Override
                public double apply(double... args) {
                    return MathOperations.log10(args[0]);
                }
            };

            Function sqrtFunction = new Function("sqrt", 1) {
                @Override
                public double apply(double... args) {
                    return MathOperations.sqrt(args[0]);
                }
            };

            Function cbrtFunction = new Function("cbrt", 1) {
                @Override
                public double apply(double... args) {
                    return MathOperations.cbrt(args[0]);
                }
            };

            Function factorialFunction = new Function("fact", 1) {
                @Override
                public double apply(double... args) {
                    return MathOperations.factorial((int) args[0]);
                }
            };

            // 构建表达式并计算
            Expression exp = new ExpressionBuilder(expression)
                    .functions(sinFunction, cosFunction, tanFunction,
                              asinFunction, acosFunction, atanFunction,
                              lnFunction, logFunction, sqrtFunction,
                              cbrtFunction, factorialFunction)
                    .variables("pi", "e")
                    .build()
                    .setVariable("pi", MathOperations.PI)
                    .setVariable("e", MathOperations.E);

            double result = exp.evaluate();

            // 格式化结果
            String formattedResult = MathOperations.formatScientific(result, precision);

            return CalculationResponse.success(result, formattedResult);
        } catch (Exception e) {
            return CalculationResponse.error("计算错误: " + e.getMessage());
        }
    }

    @Override
    public String convertBase(String value, int fromBase, int toBase) {
        try {
            // 首先转换为十进制
            long decimalValue;
            if (fromBase == 10) {
                decimalValue = Long.parseLong(value);
            } else if (fromBase == 2) {
                decimalValue = MathOperations.binaryToDecimal(value);
            } else if (fromBase == 8) {
                decimalValue = MathOperations.octalToDecimal(value);
            } else if (fromBase == 16) {
                decimalValue = MathOperations.hexToDecimal(value);
            } else {
                throw new IllegalArgumentException("不支持的进制: " + fromBase);
            }

            // 然后从十进制转换为目标进制
            if (toBase == 10) {
                return String.valueOf(decimalValue);
            } else if (toBase == 2) {
                return MathOperations.decimalToBinary(decimalValue);
            } else if (toBase == 8) {
                return MathOperations.decimalToOctal(decimalValue);
            } else if (toBase == 16) {
                return MathOperations.decimalToHex(decimalValue);
            } else {
                throw new IllegalArgumentException("不支持的进制: " + toBase);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("进制转换错误: " + e.getMessage());
        }
    }

    @Override
    public double calculateMean(double[] values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("数组不能为空");
        }

        double sum = 0;
        for (double value : values) {
            sum += value;
        }

        return sum / values.length;
    }

    @Override
    public double calculateStandardDeviation(double[] values, boolean isSample) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("数组不能为空");
        }

        // 计算平均值
        double mean = calculateMean(values);

        // 计算偏差平方和
        double sumOfSquaredDeviations = 0;
        for (double value : values) {
            double deviation = value - mean;
            sumOfSquaredDeviations += deviation * deviation;
        }

        // 计算标准差
        int divisor = isSample ? values.length - 1 : values.length;
        if (divisor == 0) {
            throw new IllegalArgumentException("样本标准差需要至少2个值");
        }

        return Math.sqrt(sumOfSquaredDeviations / divisor);
    }
}
