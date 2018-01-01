package com.six.ui.sample.svg;

import android.app.Activity;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.six.ui.R;

/**
 * @author hellenxu
 * @date 16/4/2
 * Copyright 2016 Six. All rights reserved.
 */
public class SVGAnimationActivity extends Activity implements View.OnClickListener{
    private ImageView ivSvg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_svg_anim);
        ivSvg = (ImageView) findViewById(R.id.iv_svg);
        ivSvg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_svg:
                svgAnim();
                break;
            default:
                break;
        }
    }

    private void svgAnim(){
        Drawable svgDrawable = ivSvg.getDrawable();
        if (svgDrawable instanceof Animatable){
            ((Animatable) svgDrawable).start();
        }
    }
}
