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

public class SpanSample extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = SpanSample.class.getSimpleName();
    private RichText rtEditor;
    private ImageButton imgBtnBullet;
    private ImageButton imgBtnBold;
    private ImageButton imgBtnItalic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_re_span);
        init();
    }

    private void init(){
        rtEditor = (RichText) findViewById(R.id.editor);
        imgBtnBullet = (ImageButton) findViewById(R.id.format_bullet);
        imgBtnBold = (ImageButton) findViewById(R.id.format_bold);
        imgBtnItalic = (ImageButton) findViewById(R.id.format_italic);
        imgBtnBullet.setOnClickListener(this);
        imgBtnBold.setOnClickListener(this);
        imgBtnItalic.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.format_bullet:
                rtEditor.bullet(!rtEditor.contains(RichText.FORMAT_BULLET));
                break;
            case R.id.format_bold:
                rtEditor.bold(!rtEditor.contains(RichText.FORMAT_BOLD));
                break;
            case R.id.format_italic:
                rtEditor.italic(!rtEditor.contains(RichText.FORMAT_ITALIC));
                break;
            default:
                break;
        }
    }

    private void showToast(String msg){
        Toast.makeText(SpanSample.this, msg, Toast.LENGTH_SHORT).show();
    }
}
