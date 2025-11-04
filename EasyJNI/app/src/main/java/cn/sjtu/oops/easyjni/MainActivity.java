package cn.sjtu.oops.easyjni;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import cn.sjtu.oops.easyjni.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {

    // Used to load the 'easyjni' library on application startup.
    static {
        System.loadLibrary("easyjni");
    }

    private ActivityMainBinding binding;
    private String s;
    private String TAG = "0ops";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.button.setOnClickListener(this);

        // Example of a call to a native method
//        TextView tv = binding.checkResult;
//        tv.setText(stringFromJNI());
    }

    /**
     * A native method that is implemented by the 'easyjni' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
    public  native  boolean checkFlag(String s);

    @Override
    public void onClick(View v) {
        s = binding.textEditor.getText().toString();
        Log.d(TAG, s);
        boolean result = checkFlag(s);

        TextView tv  = binding.checkResult;
        tv.setText(result? "Correct Flag": "Wrong Flag");
    }
}