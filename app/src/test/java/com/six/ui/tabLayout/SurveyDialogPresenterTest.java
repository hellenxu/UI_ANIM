package com.six.ui.tabLayout;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;

/**
 * @CopyRight six.ca
 * Created by Heavens on 2018-05-07.
 */
public class SurveyDialogPresenterTest {
    private Survey.View view;
    private SurveyDialogPresenter presenter;

    @Before
    public void setUp() {
        view = Mockito.mock(Survey.View.class);
        presenter = Mockito.spy(SurveyDialogPresenter.class);
        presenter.setSurveyView(view);
    }

    @Test
    public void handlePositive() {
        presenter.handlePositive();

        verify(view).showThreeButtonDialog();
    }

}
