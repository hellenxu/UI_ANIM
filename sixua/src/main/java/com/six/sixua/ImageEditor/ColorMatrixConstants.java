package com.six.sixua.ImageEditor;

/**
 * @author hellenxu
 * @date 16/3/16
 * Copyright 2016 Six. All rights reserved.
 */
public interface ColorMatrixConstants {
    int EFFECT_GREY = 0;

    int EFFECT_REVERSE = 1;

    int EFFECT_OLD_DAYS = 2;

    int EFFECT_NO_COLOR = 3;

    int EFFECT_HIGH_SAT = 4;

    float[] greyMatrix =
            {0.33f, 0.59f, 0.11f, 0, 0,
                    0.33f, 0.59f, 0.11f, 0, 0,
                    0.33f, 0.59f, 0.11f, 0, 0,
                    0, 0, 0, 1, 0};

    float[] reverseMatrix =
            {-1, 0, 0, 1, 1,
                    0, -1, 0, 1, 1,
                    0, 0, -1, 1, 1,
                    0, 0, 0, 1, 0};

    float[] oldPicMatrix =
            {0.393f, 0.769f, 0.189f, 0, 0,
                    0.349f, 0.686f, 0.168f, 0, 0,
                    0.272f, 0.534f, 0.131f, 0, 0,
                    0, 0, 0, 1, 0};

    float[] noColorMatrix =
            {1.5f, 1.5f, 1.5f, 0, -1,
                    1.5f, 1.5f, 1.5f, 0, -1,
                    1.5f, 1.5f, 1.5f, 0, -1,
                    0, 0, 0, 1, 0};

    float[] highSatMatrix =
            {1.438f, -0.122f, -0.016f, 0, -0.03f,
                    -0.062f, 1.378f, -0.016f, 0, 0.05f,
                    -0.062f, -0.122f, 1.483f, 0, -0.02f,
                    0, 0, 0, 1, 0};

}
