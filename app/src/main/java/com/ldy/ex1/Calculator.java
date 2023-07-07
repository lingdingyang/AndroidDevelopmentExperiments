package com.ldy.ex1;

import com.ldy.ex1.util.Operation;

import java.util.Formatter;

public class Calculator {
    // 用于显示在界面上的两行字符串，是返回的结果
    private String line1 = "", line2 = "";
    // 接收用户输入的字符串
    private String input1 = "0", input2 = "0";
    // 用于计算的两个变量
    private double num1 = 0, num2 = 0;
    // 用于记录是否已经输入过小数点
    private Boolean doted = false;
    // 用于记录是否已经输入数字，用于判断是切换运算符还是完成运算
    private Boolean inputted = false;
    // 当前输入的运算符
    private Operation op = null;

    // 返回当前是否可以输入小数点
    public Boolean getDoted() {
        return doted;
    }

    // 设置是否可以输入小数点
    public void setDoted(Boolean doted) {
        this.doted = doted;
    }

    // 完成二元运算
    private void calculate() {
        // 进行加法运算
        if (op == Operation.PLUS) {
//            num1保存计算后的结果，用作下一次运算的数据
            num1 = num1 + num2;
//            num2计算完后变为默认值0
            num2 = 0;
            input2 = "0";
            input1 = "" + num1;
//            清空运算符
            op = null;
        } else if (op == Operation.SUB) {
            num1 = num1 - num2;
            num2 = 0;
            input2 = "0";
            input1 = "" + num1;
            op = null;
        } else if (op == Operation.MUL) {
            num1 = num1 * num2;
            num2 = 0;
            input2 = "0";
            input1 = "" + num1;
            op = null;
        } else if (op == Operation.DIV) {
            num1 = Double.parseDouble(new Formatter().format("%.5f", num1 / num2).toString());
            num2 = 0;
            input2 = "0";
            input1 = "" + num1;
            op = null;
        }
//  这里的else理论上不会执行，如果不执行，不改变num1，只会将num2清零
        else {
            num2 = 0;
            input2 = "0";
            input1 = "" + num1;
            op = null;
        }
    }

    // 用户点击数字进行的操作
    public Ans clickNum(String n) {
//        如果op为null，说明num1还没有输入，先将输入的数据保存到input1，之后input1强转成double后会赋值给num1
        if (op == null) {
//            如果当前input1是0，不进行拼接，而是直接修改
            if (input1.equals("0")) {
                input1 = n;
            } else {
                input1 += n;
            }
//            line1下面一行的显示内容，当前显示的内容就是用户输入的内容，返回input1就行
            line1 = input1;
            return new Ans("", line1);
        } else {
//            inputted设置为true表明已经开始输入第二个数字了，之后输入二元操作符就会进行运算而不是只修改不运算
            inputted = true;
            if (input2.equals("0")) {
                input2 = n;
            } else {
                input2 += n;
            }
            line1 = input2;
            return new Ans(line2, line1);
        }
    }

    // 用户点击运算符进行的操作
    public Ans clickOperator(Operation code) {
//        如果用户点击的是C键，将所有数据初始化
        if (code == Operation.C) {
            op = null;
            num1 = 0;
            num2 = 0;
            input2 = "0";
            input1 = "0";
            doted = false;
            return new Ans("", "0");
        }
//        先处理一元运算
//        如果用户点击了等于号
        else if (code == Operation.EQUAL) {
//            获取num1和num2的值
            num1 = Double.parseDouble(input1);
            num2 = Double.parseDouble(input2);
//            如果op保存的带运算的运算符是二元运算，line2保存的是运算公式
            if (op != null && op != Operation.EQUAL && op != Operation.SQRT && op != Operation.DIV1 && op != Operation.X2) {
                line2 = num1 + operationToString(op) + num2 + "=";
            }
//            如果是null或一元运算line2直接显示
            else {
                line2 = num1 + "=";
            }
//            进行计算
            calculate();
//            line1显示计算结果
            line1 = num1 + "";
// 更新op
            op = Operation.EQUAL;
//            更新下次可以输入小数点
            doted = false;
            return new Ans(line2, line1);
        }
//        如果输入的是小数点，不进行运算，不修改Op，只是判断是给num1还是num2添加小数点
        else if (code == Operation.DOT) {
            if (op == null) {
                input1 += ".";
                line1 = input1;
                return new Ans("", line1);
            } else {
                input2 += ".";
                line1 = input2;
                return new Ans(line2, line1);
            }
        }
//        如果输入的是开根
        else if (code == Operation.SQRT) {
//            和等于号一样，先修改line2保存运算式
            num1 = Double.parseDouble(input1);
            num2 = Double.parseDouble(input2);
            if (op != null && op != Operation.EQUAL && op != Operation.SQRT && op != Operation.DIV1 && op != Operation.X2) {
                line2 = "sqrt(" + num1 + operationToString(op) + num2 + ")";
            } else {
                line2 = "sqrt(" + num1 + ")";
            }
//            再进行运算获得上一次运算的结果
            calculate();
//            对结果进行开根
            num1 = Double.parseDouble(new Formatter().format("%.5f", Math.sqrt(num1)).toString());
//            获得最终的结果，保存到line1，输出的界面
            input1 = num1 + "";
            line1 = input1;
            op = Operation.SQRT;
            doted = false;
            return new Ans(line2, line1);
        } else if (code == Operation.DIV1) {
            num1 = Double.parseDouble(input1);
            num2 = Double.parseDouble(input2);
            if (op != null && op != Operation.EQUAL && op != Operation.SQRT && op != Operation.DIV1 && op != Operation.X2) {
                line2 = "-(" + num1 + operationToString(op) + num2 + ")";
            } else {
                line2 = "-(" + num1 + ")";
            }
            calculate();
            num1 = Double.parseDouble(new Formatter().format("%.5f", 0 - num1).toString());
            input1 = num1 + "";
            line1 = input1;
            op = Operation.DIV1;
            doted = false;
            return new Ans(line2, line1);
        } else if (code == Operation.X2) {
            num1 = Double.parseDouble(input1);
            num2 = Double.parseDouble(input2);
            if (op != null && op != Operation.EQUAL && op != Operation.SQRT && op != Operation.DIV1 && op != Operation.X2) {
                line2 = "(" + num1 + operationToString(op) + num2 + ")^2";
            } else {
                line2 = "(" + num1 + ")^2";
            }
            calculate();
            num1 = Double.parseDouble(new Formatter().format("%.5f", num1 * num1).toString());
            input1 = num1 + "";
            line1 = input1;
            op = Operation.X2;
            doted = false;
            return new Ans(line2, line1);
        }
//        如果输入的是二元运算符
        else {
//            如果op为null，说明没有上一次输入的运算符，不需要进行运算
            if (op == null) {
                num1 = Double.parseDouble(input1);
                num2 = Double.parseDouble(input2);
                line1 = "0";
                doted = false;
//                修改op为对应的运算符，修改line2用于显示对应的式子
                if (code == Operation.PLUS) {
                    op = Operation.PLUS;
                    line2 = num1 + operationToString(Operation.PLUS);
                } else if (code == Operation.SUB) {
                    op = Operation.SUB;
                    line2 = num1 + operationToString(Operation.SUB);
                } else if (code == Operation.MUL) {
                    op = Operation.MUL;
                    line2 = num1 + operationToString(Operation.MUL);
                } else if (code == Operation.DIV) {
                    op = Operation.DIV;
                    line2 = num1 + operationToString(Operation.DIV);
                } else {
                    line1 = "wrong";
                }
                return new Ans(line2, line1);
            }
//            如果op不为null，说明已经有待处理的运算
            else {
//                判断第二个数是否已经输入了，如果已经输入，这时就要进行上一次的运算并显示结果了
                if (inputted) {
//                    获取num1和num2并进行运算
                    num1 = Double.parseDouble(input1);
                    num2 = Double.parseDouble(input2);
                    calculate();
                    inputted = false;
//                    将line1设置为0接收用户输入的下一个数
                    line1 = "0";
                    doted = false;
                    if (code == Operation.PLUS) {
                        line2 = num1 + operationToString(Operation.PLUS);
                        op = Operation.PLUS;
                    } else if (code == Operation.SUB) {
                        line2 = num1 + operationToString(Operation.SUB);
                        op = Operation.SUB;
                    } else if (code == Operation.MUL) {
                        line2 = num1 + operationToString(Operation.MUL);
                        op = Operation.MUL;
                    } else if (code == Operation.DIV) {
                        line2 = num1 + operationToString(Operation.DIV);
                        op = Operation.DIV;
                    } else {
                        line1 = "wrong";
                    }
                }
//                还没有输入第二个数，这时点击二元运算符只是修改上一次的运算符
                else {
                    if (code == Operation.PLUS) {
                        op = Operation.PLUS;
                        line2 = num1 + operationToString(Operation.PLUS);
                    } else if (code == Operation.SUB) {
                        op = Operation.SUB;
                        line2 = num1 + operationToString(Operation.SUB);
                    } else if (code == Operation.MUL) {
                        op = Operation.MUL;
                        line2 = num1 + operationToString(Operation.MUL);
                    } else if (code == Operation.DIV) {
                        op = Operation.DIV;
                        line2 = num1 + operationToString(Operation.DIV);
                    } else {
                        line1 = "wrong";
                    }
                }
                return new Ans(line2, line1);
            }
        }
    }

    // 返回操作符对应的字符串，用于显示
    private String operationToString(Operation code) {
        if (code == Operation.C) {
            return "C";
        } else if (code == Operation.PLUS) {
            return "+";
        } else if (code == Operation.SUB) {
            return "-";
        } else if (code == Operation.MUL) {
            return "*";
        } else if (code == Operation.DIV) {
            return "/";
        } else if (code == Operation.EQUAL) {
            return "=";
        } else if (code == Operation.DOT) {
            return ".";
        } else if (code == Operation.SQRT) {
            return "sqrt";
        } else if (code == Operation.DIV1) {
            return "1/";
        } else if (code == Operation.X2) {
            return "x2";
        }
        return "wrong";
    }
}
