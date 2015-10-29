package com.volkhart.localstorage;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.util.Log;
import android.widget.Toast;

public class DeleteActivity extends Activity {
    private static final int RC_DELETE_DOCUMENT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent, RC_DELETE_DOCUMENT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RC_DELETE_DOCUMENT:
                if (resultCode == RESULT_OK) {
                    Cursor cursor = getContentResolver().query(data.getData(), new String[]{DocumentsContract.Document.COLUMN_FLAGS}, null, null, null);

                    if (cursor == null || !cursor.moveToFirst()) {
                        Toast.makeText(this, R.string.delete_failed, Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    }

                    int flags = cursor.getInt(0);
                    if ((DocumentsContract.Document.FLAG_SUPPORTS_DELETE & flags) == DocumentsContract.Document.FLAG_SUPPORTS_DELETE) {
                        DocumentsContract.deleteDocument(getContentResolver(), data.getData());
                        Toast.makeText(this, R.string.deleted, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, R.string.delete_failed, Toast.LENGTH_SHORT).show();
                    }
                    cursor.close();
                }
                finish();
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
