package com.example.frontapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.InputStream;
import java.util.ArrayList;

public class SelectActivity extends AppCompatActivity {
    private static final String TAG = "SelectActivity";
    private static final int VIDEO_FILE_REQUEST = 101;
    Button uploadBtn, sendBtn;
    ImageView imageView;
    Uri selectedVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        uploadBtn = (Button)findViewById(R.id.uploadBtn);
        imageView = (ImageView)findViewById(R.id.imageView);
        sendBtn = (Button)findViewById(R.id.sendBtn);

        //업로드 버튼
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getVideo();
            }
        });

        //전송 버튼
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoadingActivity.class);
                intent.putExtra("videoUri", selectedVideo.toString());
                startActivity(intent);
            }
        });
    }

    //동영상 불러오기
    private void getVideo() {
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                //권한 요청 성공
                //갤러리 동영상 호출
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Video.Media.CONTENT_TYPE);
                startActivityForResult(intent, VIDEO_FILE_REQUEST);
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Log.d(TAG, "onPermissionDenied:");
                Toast.makeText(SelectActivity.this, "권한요청실패", Toast.LENGTH_SHORT);
            }

        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage("[설정] > [권한] 에서 권한을 허용할 수 있습니다.")
                .setDeniedMessage("동영상 및 파일을 저장하기 위하여 접근 권한이 필요합니다.")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    public static Bitmap createThumbnail(Context activity, String path) {
        MediaMetadataRetriever mediaMetadataRetriever = null;
        Bitmap bitmap = null;
        try {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            mediaMetadataRetriever.setDataSource(activity, Uri.parse(path));

            bitmap = mediaMetadataRetriever.getFrameAtTime(1000000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
        }

        return bitmap;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == VIDEO_FILE_REQUEST && resultCode == RESULT_OK) {
            Uri videoUri = data.getData();
            selectedVideo = data.getData();
            imageView.setImageBitmap(createThumbnail(SelectActivity.this, videoUri.toString()));
        }
    }

}