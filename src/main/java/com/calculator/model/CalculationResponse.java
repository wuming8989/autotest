package com.calculator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 计算响应的数据模型类
 * 用于封装计算结果和可能的错误信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalculationResponse {

    /**
     * 计算结果
     */
    private Double result;

    /**
     * 格式化后的结果（考虑精度和科学记数法）
     */
    private String formattedResult;

    /**
     * 错误信息（如果计算失败）
     */
    private String errorMessage;

    /**
     * 计算是否成功
     */
    private boolean success;

    /**
     * 创建成功响应
     */
    public static CalculationResponse success(Double result, String formattedResult) {
        return new CalculationResponse(result, formattedResult, null, true);
    }

    /**
     * 创建失败响应
     */
    public static CalculationResponse error(String errorMessage) {
        return new CalculationResponse(null, null, errorMessage, false);
    }
}
