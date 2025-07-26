package com.calculator.model;

import lombok.Data;

/**
 * 计算请求的数据模型类
 * 用于封装从前端接收的计算表达式和相关参数
 */
@Data
public class CalculationRequest {

    /**
     * 数学表达式，如 "2+3*sin(45)"
     */
    private String expression;

    /**
     * 角度模式：true表示使用角度，false表示使用弧度
     */
    private boolean degreeMode = true;

    /**
     * 计算精度（小数位数）
     */
    private int precision = 10;
}
