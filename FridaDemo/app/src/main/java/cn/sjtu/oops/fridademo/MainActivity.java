package cn.sjtu.oops.fridademo;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import  cn.sjtu.oops.fridademo.HookDemo;

public class MainActivity extends AppCompatActivity {
    private TextView tvc;
    private EditText edta, edtb;
    private Button btn;
    private int numC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button)this.findViewById(R.id.btn);
        edta = (EditText)this.findViewById(R.id.a);
        edtb = (EditText)this.findViewById(R.id.b);
        tvc = (TextView)this.findViewById(R.id.c);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputA = edta.getText().toString();
                String inputB = edtb.getText().toString();
                int numA = Integer.valueOf(inputA).intValue();
                int numB = Integer.valueOf(inputB).intValue();
                numC = HookDemo.Add(numA, numB);
                tvc.setText(String.valueOf(numC));
            }
        });
    }
}