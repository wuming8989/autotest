package com.calculator.service;

import com.calculator.model.CalculationRequest;
import com.calculator.model.CalculationResponse;

/**
 * 计算器服务接口
 * 定义科学计算器的核心功能
 */
public interface CalculatorService {

    /**
     * 计算数学表达式
     * @param request 包含表达式和计算参数的请求对象
     * @return 计算结果响应
     */
    CalculationResponse calculate(CalculationRequest request);

    /**
     * 进制转换
     * @param value 要转换的值
     * @param fromBase 源进制（2=二进制，8=八进制，10=十进制，16=十六进制）
     * @param toBase 目标进制（2=二进制，8=八进制，10=十进制，16=十六进制）
     * @return 转换结果
     */
    String convertBase(String value, int fromBase, int toBase);

    /**
     * 计算统计值（平均值）
     * @param values 数值数组
     * @return 平均值
     */
    double calculateMean(double[] values);

    /**
     * 计算统计值（标准差）
     * @param values 数值数组
     * @param isSample 是否为样本标准差（n-1）
     * @return 标准差
     */
    double calculateStandardDeviation(double[] values, boolean isSample);
}
