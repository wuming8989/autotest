/**
 * 科学计算器前端JavaScript
 * 处理用户界面交互、计算请求和结果显示
 */

// 全局变量
let currentInput = '0';
let currentHistory = '';
let lastResult = null;
let isDegreeMode = true;
let currentBase = 10;
let precision = 10;
let showHistory = true;
let currentTheme = 'light';

// DOM元素
document.addEventListener('DOMContentLoaded', function() {
    // 初始化设置
    loadSettings();
    applyTheme();

    // 初始化标签页切换
    initTabs();

    // 初始化按钮事件
    initButtons();

    // 初始化设置面板
    initSettings();

    // 更新显示
    updateDisplay();
});

// 初始化标签页切换
function initTabs() {
    const tabButtons = document.querySelectorAll('.tab-button');
    const tabContents = document.querySelectorAll('.tab-content');

    tabButtons.forEach(button => {
        button.addEventListener('click', () => {
            // 移除所有活动状态
            tabButtons.forEach(btn => btn.classList.remove('active'));
            tabContents.forEach(content => content.classList.remove('active'));

            // 设置当前标签为活动状态
            button.classList.add('active');
            const tabId = button.getAttribute('data-tab');
            document.getElementById(tabId).classList.add('active');

            // 重置输入
            currentInput = '0';
            updateDisplay();
        });
    });
}

// 初始化按钮事件
function initButtons() {
    // 数字按钮
    document.querySelectorAll('.number').forEach(button => {
        button.addEventListener('click', () => {
            const value = button.getAttribute('data-value');
            handleNumberInput(value);
        });
    });

    // 十六进制按钮
    document.querySelectorAll('.hex').forEach(button => {
        button.addEventListener('click', () => {
            if (currentBase >= 16) {
                const value = button.getAttribute('data-value');
                handleNumberInput(value);
            }
        });
    });

    // 运算符按钮
    document.querySelectorAll('.operator').forEach(button => {
        button.addEventListener('click', () => {
            const action = button.getAttribute('data-action');
            handleOperator(action);
        });
    });

    // 功能按钮
    document.querySelectorAll('.function').forEach(button => {
        button.addEventListener('click', () => {
            const action = button.getAttribute('data-action');
            handleFunction(action);
        });
    });

    // 等号按钮
    document.querySelectorAll('.equals').forEach(button => {
        button.addEventListener('click', () => {
            calculate();
        });
    });

    // 角度/弧度模式切换
    document.getElementById('degreeMode').addEventListener('click', () => {
        isDegreeMode = true;
        document.getElementById('degreeMode').classList.add('mode-active');
        document.getElementById('radianMode').classList.remove('mode-active');
    });

    document.getElementById('radianMode').addEventListener('click', () => {
        isDegreeMode = false;
        document.getElementById('radianMode').classList.add('mode-active');
        document.getElementById('degreeMode').classList.remove('mode-active');
    });

    // 进制选择按钮
    document.querySelectorAll('.base-button').forEach(button => {
        button.addEventListener('click', () => {
            const base = parseInt(button.getAttribute('data-base'));
            changeBase(base);
        });
    });

    // 统计按钮
    document.querySelectorAll('.stat-button').forEach(button => {
        button.addEventListener('click', () => {
            const statType = button.getAttribute('data-stat');
            calculateStatistics(statType);
        });
    });

    // 键盘事件
    document.addEventListener('keydown', handleKeyboardInput);
}

// 初始化设置面板
function initSettings() {
    // 精度设置
    const precisionSetting = document.getElementById('precisionSetting');
    precisionSetting.value = precision;
    precisionSetting.addEventListener('change', () => {
        precision = parseInt(precisionSetting.value);
        saveSettings();
        if (lastResult !== null) {
            updateDisplay();
        }
    });

    // 主题设置
    const themeSetting = document.getElementById('themeSetting');
    themeSetting.value = currentTheme;
    themeSetting.addEventListener('change', () => {
        currentTheme = themeSetting.value;
        applyTheme();
        saveSettings();
    });

    // 历史记录设置
    const historySetting = document.getElementById('historySetting');
    historySetting.checked = showHistory;
    historySetting.addEventListener('change', () => {
        showHistory = historySetting.checked;
        updateDisplay();
        saveSettings();
    });

    // 清除历史记录按钮
    document.getElementById('clearHistory').addEventListener('click', () => {
        currentHistory = '';
        updateDisplay();
    });
}

// 处理数字输入
function handleNumberInput(value) {
    if (currentInput === '0' && value !== '.') {
        currentInput = value;
    } else if (value === '.' && currentInput.includes('.')) {
        // 已经有小数点，不做任何操作
        return;
    } else {
        currentInput += value;
    }
    updateDisplay();
}

// 处理运算符
function handleOperator(action) {
    let symbol = '';
    switch (action) {
        case 'add':
            symbol = '+';
            break;
        case 'subtract':
            symbol = '-';
            break;
        case 'multiply':
            symbol = '*';
            break;
        case 'divide':
            symbol = '/';
            break;
    }

    currentInput += ' ' + symbol + ' ';
    updateDisplay();
}

// 处理功能按钮
function handleFunction(action) {
    switch (action) {
        case 'clear':
            currentInput = '0';
            currentHistory = '';
            lastResult = null;
            updateDisplay();
            break;

        case 'backspace':
            if (currentInput.length > 1) {
                currentInput = currentInput.slice(0, -1);
            } else {
                currentInput = '0';
            }
            updateDisplay();
            break;

        case 'negate':
            if (currentInput !== '0') {
                if (currentInput.startsWith('-')) {
                    currentInput = currentInput.substring(1);
                } else {
                    currentInput = '-' + currentInput;
                }
                updateDisplay();
            }
            break;

        case 'percent':
            try {
                const value = parseFloat(currentInput);
                currentInput = (value / 100).toString();
                updateDisplay();
            } catch (e) {
                showError('无效输入');
            }
            break;

        case 'sin':
            appendFunction('sin');
            break;

        case 'cos':
            appendFunction('cos');
            break;

        case 'tan':
            appendFunction('tan');
            break;

        case 'asin':
            appendFunction('asin');
            break;

        case 'acos':
            appendFunction('acos');
            break;

        case 'atan':
            appendFunction('atan');
            break;

        case 'log':
            appendFunction('log');
            break;

        case 'ln':
            appendFunction('ln');
            break;

        case 'exp':
            appendFunction('exp');
            break;

        case 'power':
            currentInput += ' ^ ';
            updateDisplay();
            break;

        case 'sqrt':
            appendFunction('sqrt');
            break;

        case 'cbrt':
            appendFunction('cbrt');
            break;

        case 'factorial':
            appendFunction('fact');
            break;

        case 'pi':
            currentInput += 'pi';
            updateDisplay();
            break;

        case 'e':
            currentInput += 'e';
            updateDisplay();
            break;

        case 'leftParen':
            currentInput += '(';
            updateDisplay();
            break;

        case 'rightParen':
            currentInput += ')';
            updateDisplay();
            break;

        case 'reciprocal':
            try {
                const value = parseFloat(currentInput);
                if (value !== 0) {
                    currentInput = (1 / value).toString();
                    updateDisplay();
                } else {
                    showError('除以零错误');
                }
            } catch (e) {
                showError('无效输入');
            }
            break;

        case 'abs':
            appendFunction('abs');
            break;

        case 'mod':
            currentInput += ' % ';
            updateDisplay();
            break;

        case 'and':
            if (currentBase === 10) {
                showError('请先切换到二进制、八进制或十六进制模式');
                return;
            }
            currentInput += ' & ';
            updateDisplay();
            break;

        case 'or':
            if (currentBase === 10) {
                showError('请先切换到二进制、八进制或十六进制模式');
                return;
            }
            currentInput += ' | ';
            updateDisplay();
            break;

        case 'xor':
            if (currentBase === 10) {
                showError('请先切换到二进制、八进制或十六进制模式');
                return;
            }
            currentInput += ' ^ ';
            updateDisplay();
            break;

        case 'not':
            if (currentBase === 10) {
                showError('请先切换到二进制、八进制或十六进制模式');
                return;
            }
            appendFunction('~');
            break;

        case 'leftShift':
            if (currentBase === 10) {
                showError('请先切换到二进制、八进制或十六进制模式');
                return;
            }
            currentInput += ' << ';
            updateDisplay();
            break;

        case 'rightShift':
            if (currentBase === 10) {
                showError('请先切换到二进制、八进制或十六进制模式');
                return;
            }
            currentInput += ' >> ';
            updateDisplay();
            break;

        case 'convert':
            convertBaseUI();
            break;
    }
}

// 添加函数到输入
function appendFunction(funcName) {
    if (currentInput === '0') {
        currentInput = funcName + '(';
    } else {
        currentInput += funcName + '(';
    }
    updateDisplay();
}

// 计算表达式
function calculate() {
    if (currentInput === '0') return;

    const activeTab = document.querySelector('.tab-content.active').id;

    if (activeTab === 'programmer') {
        try {
            // 程序员计算器使用JavaScript的eval
            const result = eval(currentInput);
            currentHistory = currentInput + ' = ' + result;
            currentInput = result.toString();
            lastResult = result;
            updateDisplay();
            updateBaseConversions(result);
        } catch (e) {
            showError('计算错误: ' + e.message);
        }
    } else {
        // 标准和科学计算器使用后端API
        const request = {
            expression: currentInput,
            degreeMode: isDegreeMode,
            precision: precision
        };

        fetch('/api/calculator/calculate', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(request)
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                currentHistory = currentInput + ' = ' + data.formattedResult;
                currentInput = data.formattedResult;
                lastResult = data.result;
                updateDisplay();
            } else {
                showError(data.errorMessage);
            }
        })
        .catch(error => {
            showError('API错误: ' + error.message);
        });
    }
}

// 更新显示
function updateDisplay() {
    const inputElement = document.getElementById('input');
    const historyElement = document.getElementById('history');

    inputElement.textContent = currentInput;

    if (showHistory) {
        historyElement.textContent = currentHistory;
    } else {
        historyElement.textContent = '';
    }
}

// 显示错误
function showError(message) {
    const historyElement = document.getElementById('history');
    historyElement.textContent = '错误: ' + message;

    // 闪烁显示错误
    historyElement.style.color = 'red';
    setTimeout(() => {
        historyElement.style.color = '';
    }, 2000);
}

// 处理键盘输入
function handleKeyboardInput(event) {
    const key = event.key;

    // 数字和小数点
    if (/^[0-9.]$/.test(key)) {
        handleNumberInput(key);
        event.preventDefault();
    }

    // 运算符
    switch (key) {
        case '+':
            handleOperator('add');
            event.preventDefault();
            break;
        case '-':
            handleOperator('subtract');
            event.preventDefault();
            break;
        case '*':
            handleOperator('multiply');
            event.preventDefault();
            break;
        case '/':
            handleOperator('divide');
            event.preventDefault();
            break;
        case 'Enter':
            calculate();
            event.preventDefault();
            break;
        case 'Escape':
            handleFunction('clear');
            event.preventDefault();
            break;
        case 'Backspace':
            handleFunction('backspace');
            event.preventDefault();
            break;
        case '(':
            handleFunction('leftParen');
            event.preventDefault();
            break;
        case ')':
            handleFunction('rightParen');
            event.preventDefault();
            break;
        case '%':
            handleFunction('mod');
            event.preventDefault();
            break;
    }

    // 十六进制字符
    if (/^[a-fA-F]$/.test(key) && currentBase >= 16) {
        handleNumberInput(key.toUpperCase());
        event.preventDefault();
    }
}

// 切换进制
function changeBase(base) {
    // 更新活动按钮
    document.querySelectorAll('.base-button').forEach(button => {
        button.classList.remove('active');
    });
    document.querySelector(`.base-button[data-base="${base}"]`).classList.add('active');

    // 更新当前进制
    currentBase = base;

    // 禁用/启用