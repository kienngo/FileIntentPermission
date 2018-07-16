package com.example.admin.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private final String mError = "error";

    private final int SPAN_COUNT_GRIDLAYOUT = 2;
    private final int PICK_FROM_GALLERY = 1;
    private final int INIT_INDEX = 0;
    private ArrayList<String> mListImage = new ArrayList<>();
    private GalleryAdapter mGalleryAdapter;
    private RecyclerView mRecyclerGallery;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        inItData();
        inItView();
    }

    private void inItData() {
        try {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
            } else {
                mListImage = getListImage();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void inItView() {
        mRecyclerGallery = (RecyclerView) findViewById(R.id.recycler_gallery);
        mGalleryAdapter = new GalleryAdapter(mListImage, this);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, SPAN_COUNT_GRIDLAYOUT);
        mRecyclerGallery.setLayoutManager(layoutManager);
        mRecyclerGallery.setAdapter(mGalleryAdapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PICK_FROM_GALLERY:
                if (grantResults.length > INIT_INDEX && grantResults[INIT_INDEX] == PackageManager.PERMISSION_GRANTED) {
                    mListImage = getListImage();
                } else {
                    Log.e(TAG, mError);
                }
                break;
        }
    }

    private ArrayList<String> getListImage() {
        ArrayList<String> listOfAllImages = new ArrayList<String>();

        int column_index_data;
        String absolutePathOfImage = null;

        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        Cursor cursor = getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);

            listOfAllImages.add(absolutePathOfImage);
        }
        return listOfAllImages;
    }
}
