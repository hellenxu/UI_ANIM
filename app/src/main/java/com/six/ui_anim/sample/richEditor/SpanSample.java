package com.six.ui_anim.sample.richEditor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.six.ui_anim.R;
import com.six.ui_anim.richEditor.span.RichText;

/**
 * Created by xxl on 16/2/11.
 */

public class SpanSample extends AppCompatActivity {
    private RichText rtEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_re_span);
        init();
        setupBullet();
    }

    private void init(){
        rtEditor = (RichText) findViewById(R.id.editor);
    }

    private void setupBullet(){
        ImageButton bullet = (ImageButton) findViewById(R.id.format_bullet);
        bullet.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                rtEditor.bullet(!rtEditor.contains(RichText.FORMAT_BULLET));
            }
        });

        bullet.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(SpanSample.this, "bullet: long click", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
}
