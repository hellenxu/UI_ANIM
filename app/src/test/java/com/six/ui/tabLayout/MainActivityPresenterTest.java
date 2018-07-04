package com.six.ui.tabLayout;


import com.six.ui.BuildConfig;
import com.six.ui.MyApplication;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, application = MyApplication.class )
public class MainActivityPresenterTest {
    private Main.View view;
    private MainActivityPresenter presenter;

    private static final String TITLE = "online";

    @Before
    public void setup() {
        view = Mockito.mock(Main.View.class);
        presenter = spy(new MainActivityPresenter());
        presenter.setView(view);
    }

    @Test
    public void checkSurveyStatus() {

        // option 1
        presenter.setBool(2);

        // option 2
//        when(presenter.checkState()).thenReturn(true);

        presenter.checkSurveyStatus();

        verify(view).showSurveyDialog();
    }

    @Test
    public void piaoLin() {
        presenter.piaoLin();
        verify(view).setTitle(TITLE);
        verify(presenter).lookAtLin();
    }

    @Test
    public void checkEmpty() {
        boolean result = presenter.checkEmpty("eere");
        Assert.assertFalse(result);
    }

}
