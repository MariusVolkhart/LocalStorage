package com.volkhart.localstorage;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {
    private static final int RC_OPEN_DOCUMENT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent, RC_OPEN_DOCUMENT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RC_OPEN_DOCUMENT:
                if (resultCode == RESULT_OK) {
                    data.setAction(Intent.ACTION_VIEW);
                    try {
                        startActivity(data);
                    } catch (ActivityNotFoundException e) {
                        data.setAction(Intent.ACTION_SEND);
                        startActivity(data);
                    }
                }
                finish();
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
