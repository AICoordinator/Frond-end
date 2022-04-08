package com.example.frontapp;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.PulseRing;

public class ProgressDialog extends Dialog {

    public ProgressDialog(@NonNull Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.progress_dialog);

        //라이브러리 로딩 이미지 사용
        ProgressBar progressBar = findViewById(R.id.spinKit);
        Sprite pulseRing = new PulseRing();
        progressBar.setIndeterminateDrawable(pulseRing);
    }

}