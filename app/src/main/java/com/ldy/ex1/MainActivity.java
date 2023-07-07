package com.ldy.ex1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ldy.ex1.util.Operation;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView result;
    private final Calculator calculator = new Calculator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    // 获取界面组件对象，设置点击事件监听
    private void init() {
        result = findViewById(R.id.result);
        Button btn_0 = findViewById(R.id.btn_0);
        Button btn_1 = findViewById(R.id.btn_1);
        Button btn_2 = findViewById(R.id.btn_2);
        Button btn_3 = findViewById(R.id.btn_3);
        Button btn_4 = findViewById(R.id.btn_4);
        Button btn_5 = findViewById(R.id.btn_5);
        Button btn_6 = findViewById(R.id.btn_6);
        Button btn_7 = findViewById(R.id.btn_7);
        Button btn_8 = findViewById(R.id.btn_8);
        Button btn_9 = findViewById(R.id.btn_9);
        Button btn_1div = findViewById(R.id.btn_1div);
        Button btn_c = findViewById(R.id.btn_C);
        Button btn_div = findViewById(R.id.btn_div);
        Button btn_dot = findViewById(R.id.btn_dot);
        Button btn_plus = findViewById(R.id.btn_plus);
        Button btn_mul = findViewById(R.id.btn_mul);
        Button btn_que = findViewById(R.id.btn_que);
        Button btn_sqrt = findViewById(R.id.btn_sqrt);
        Button btn_sub = findViewById(R.id.btn_sub);
        Button btn_x2 = findViewById(R.id.btn_X2);
        btn_0.setOnClickListener(this);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);
        btn_7.setOnClickListener(this);
        btn_8.setOnClickListener(this);
        btn_9.setOnClickListener(this);
        btn_1div.setOnClickListener(this);
        btn_c.setOnClickListener(this);
        btn_div.setOnClickListener(this);
        btn_dot.setOnClickListener(this);
        btn_plus.setOnClickListener(this);
        btn_mul.setOnClickListener(this);
        btn_que.setOnClickListener(this);
        btn_sqrt.setOnClickListener(this);
        btn_sub.setOnClickListener(this);
        btn_x2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_0) {
            outputAns(calculator.clickNum("0"));
        } else if (view.getId() == R.id.btn_1) {
            outputAns(calculator.clickNum("1"));
        } else if (view.getId() == R.id.btn_2) {
            outputAns(calculator.clickNum("2"));
        } else if (view.getId() == R.id.btn_3) {
            outputAns(calculator.clickNum("3"));
        } else if (view.getId() == R.id.btn_4) {
            outputAns(calculator.clickNum("4"));
        } else if (view.getId() == R.id.btn_5) {
            outputAns(calculator.clickNum("5"));
        } else if (view.getId() == R.id.btn_6) {
            outputAns(calculator.clickNum("6"));
        } else if (view.getId() == R.id.btn_7) {
            outputAns(calculator.clickNum("7"));
        } else if (view.getId() == R.id.btn_8) {
            outputAns(calculator.clickNum("8"));
        }
//        按下数字键9
        else if (view.getId() == R.id.btn_9) {
            outputAns(calculator.clickNum("9"));
        }
//        按下运算符C
        else if (view.getId() == R.id.btn_C) {
            outputAns(calculator.clickOperator(Operation.C));
        } else if (view.getId() == R.id.btn_plus) {
            outputAns(calculator.clickOperator(Operation.PLUS));
        } else if (view.getId() == R.id.btn_sub) {
            outputAns(calculator.clickOperator(Operation.SUB));
        } else if (view.getId() == R.id.btn_mul) {
            outputAns(calculator.clickOperator(Operation.MUL));
        } else if (view.getId() == R.id.btn_div) {
            outputAns(calculator.clickOperator(Operation.DIV));
        } else if (view.getId() == R.id.btn_que) {
            outputAns(calculator.clickOperator(Operation.EQUAL));
        } else if (view.getId() == R.id.btn_dot) {
            if (!calculator.getDoted()) {
                calculator.setDoted(true);
                outputAns(calculator.clickOperator(Operation.DOT));
            }
        } else if (view.getId() == R.id.btn_sqrt) {
            outputAns(calculator.clickOperator(Operation.SQRT));
        } else if (view.getId() == R.id.btn_X2) {
            outputAns(calculator.clickOperator(Operation.X2));
        } else if (view.getId() == R.id.btn_1div) {
            outputAns(calculator.clickOperator(Operation.DIV1));
        }
    }

    // 处理并显示计算结果，如果计算出错会通过Toast提示
    void outputAns(Ans ans) {
        String expression = ans.getExpression();
        String res = ans.getAns();
        if (res.equals("NaN")) {
            Toast.makeText(this, "计算错误：负数开平方", Toast.LENGTH_SHORT).show();
        } else if (res.equals("Infinity")) {
            Toast.makeText(this, "计算错误：除数为0", Toast.LENGTH_SHORT).show();
        }
        result.setText(expression + "\n" + res);
    }
}