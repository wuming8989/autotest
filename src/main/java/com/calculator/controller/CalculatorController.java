package com.calculator.controller;

import com.calculator.model.CalculationRequest;
import com.calculator.model.CalculationResponse;
import com.calculator.service.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 计算器控制器
 * 处理与计算器相关的HTTP请求
 */
@RestController
@RequestMapping("/api/calculator")
@CrossOrigin(origins = "*") // 允许跨域请求
public class CalculatorController {

    private final CalculatorService calculatorService;

    @Autowired
    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    /**
     * 计算数学表达式
     * @param request 计算请求
     * @return 计算结果
     */
    @PostMapping("/calculate")
    public ResponseEntity<CalculationResponse> calculate(@RequestBody CalculationRequest request) {
        CalculationResponse response = calculatorService.calculate(request);
        return ResponseEntity.ok(response);
    }

    /**
     * 进制转换
     * @param requestBody 包含value(要转换的值)、fromBase(源进制)和toBase(目标进制)的请求体
     * @return 转换结果
     */
    @PostMapping("/convert-base")
    public ResponseEntity<Map<String, String>> convertBase(@RequestBody Map<String, Object> requestBody) {
        try {
            String value = (String) requestBody.get("value");
            int fromBase = Integer.parseInt(requestBody.get("fromBase").toString());
            int toBase = Integer.parseInt(requestBody.get("toBase").toString());

            String result = calculatorService.convertBase(value, fromBase, toBase);

            return ResponseEntity.ok(Map.of(
                "result", result,
                "success", "true"
            ));
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of(
                "error", e.getMessage(),
                "success", "false"
            ));
        }
    }

    /**
     * 计算统计值
     * @param requestBody 包含values(数值数组)和type(统计类型，如"mean"或"stdDev")的请求体
     * @return 统计结果
     */
    @PostMapping("/statistics")
    public ResponseEntity<Map<String, Object>> calculateStatistics(@RequestBody Map<String, Object> requestBody) {
        try {
            // 从请求体中获取数值数组
            Object[] valuesObj = (Object[]) requestBody.get("values");
            double[] values = Arrays.stream(valuesObj)
                    .mapToDouble(obj -> Double.parseDouble(obj.toString()))
                    .toArray();

            // 获取统计类型
            String type = (String) requestBody.get("type");

            double result;
            if ("mean".equals(type)) {
                result = calculatorService.calculateMean(values);
            } else if ("stdDev".equals(type)) {
                boolean isSample = Boolean.parseBoolean(requestBody.getOrDefault("isSample", "true").toString());
                result = calculatorService.calculateStandardDeviation(values, isSample);
            } else {
                throw new IllegalArgumentException("不支持的统计类型: " + type);
            }

            return ResponseEntity.ok(Map.of(
                "result", result,
                "success", true
            ));
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of(
                "error", e.getMessage(),
                "success", false
            ));
        }
    }

    /**
     * 健康检查端点
     * @return 服务状态
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        return ResponseEntity.ok(Map.of("status", "UP"));
    }
}
