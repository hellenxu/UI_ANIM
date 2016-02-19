package com.six.ui_anim.sample.richEditor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.six.ui_anim.R;
import com.six.ui_anim.richEditor.span.RichText;

/**
 * Created by xxl on 16/2/11.
 */

public class SpanSample extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = SpanSample.class.getSimpleName();
    private RichText rtEditor;
//    private ImageButton imgBtnBullet;
//    private ImageButton imgBtnBold;
//    private ImageButton imgBtnItalic;
//    private ImageButton imgBtnUnderline;
//    private ImageButton imgBtnStrikeThrough;
//    private ImageButton imgBtnQuote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_re_span);
        init();
    }

    private void init() {
        rtEditor = (RichText) findViewById(R.id.editor);
        ImageButton imgBtnBullet = (ImageButton) findViewById(R.id.format_bullet);
        ImageButton imgBtnBold = (ImageButton) findViewById(R.id.format_bold);
        ImageButton imgBtnItalic = (ImageButton) findViewById(R.id.format_italic);
        ImageButton imgBtnUnderline = (ImageButton) findViewById(R.id.format_underline);
        ImageButton imgBtnStrikeThrough = (ImageButton) findViewById(R.id.format_strike_through);
        ImageButton imgBtnQuote = (ImageButton) findViewById(R.id.format_block_quote);
        ImageButton imgBtnLinker = (ImageButton) findViewById(R.id.format_linker);

        imgBtnBullet.setOnClickListener(this);
        imgBtnBold.setOnClickListener(this);
        imgBtnItalic.setOnClickListener(this);
        imgBtnUnderline.setOnClickListener(this);
        imgBtnStrikeThrough.setOnClickListener(this);
        imgBtnQuote.setOnClickListener(this);
        imgBtnLinker.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.format_bullet:
                rtEditor.bullet(!rtEditor.contains(RichText.FORMAT_BULLET));
                break;
            case R.id.format_bold:
                rtEditor.bold(!rtEditor.contains(RichText.FORMAT_BOLD));
                break;
            case R.id.format_italic:
                rtEditor.italic(!rtEditor.contains(RichText.FORMAT_ITALIC));
                break;
            case R.id.format_underline:
                rtEditor.underline(!rtEditor.contains(RichText.FORMAT_UNDERLINED));
                break;
            case R.id.format_strike_through:
                rtEditor.strikeThrough(!rtEditor.contains(RichText.FORMAT_STRIKETHROUGH));
                break;
            case R.id.format_block_quote:
                rtEditor.quote(!rtEditor.contains(RichText.FORMAT_QUOTE));
                break;
            case R.id.format_linker:
                showLinkDialog();
                break;
            default:
                break;
        }
    }

    private void showLinkDialog() {
        final int start = rtEditor.getSelectionStart();
        final int end = rtEditor.getSelectionEnd();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);

        View view = getLayoutInflater().inflate(R.layout.dialog_link, null, false);
        final EditText editText = (EditText) view.findViewById(R.id.edit);
        builder.setView(view);
        builder.setTitle(R.string.dialog_title);

        builder.setPositiveButton(R.string.dialog_button_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String link = editText.getText().toString().trim();
                if (TextUtils.isEmpty(link)) {
                    return;
                }

                rtEditor.link(link, start, end);
            }
        });

        builder.setNegativeButton(R.string.dialog_button_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.create().show();
    }

    private void showToast(String msg) {
        Toast.makeText(SpanSample.this, msg, Toast.LENGTH_SHORT).show();
    }
}
