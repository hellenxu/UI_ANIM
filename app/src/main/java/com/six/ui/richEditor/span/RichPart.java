package com.six.ui.richEditor.span;

/**
 * Created by xxl on 16/2/14.
 */
public class RichPart {
    public int start;
    public int end;

    public RichPart(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public boolean isValid() {
        return start < end;
    }
}
