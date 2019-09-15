package com.jurgitau;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

public class ReportsPageTest {
    private WicketTester tester;

    @Before
    public void setUp()
    {
        tester = new WicketTester(new WicketApplication());
    }

    @Test
    public void rendersPage() {
        tester.startPage(ReportsPage.class);

        tester.assertRenderedPage(ReportsPage.class);
    }

    @Test
    public void submitsInput() {
        tester.startPage(ReportsPage.class);
        tester.newFormTester("form")
            .setValue("reportTemplate", "0")
            .setValue("format", "0");

        tester.executeAjaxEvent("form:generate", "click");

        tester.assertFeedback("form:feedback", "Report successfully saved in C:/tmp/r-lithuania-cities.pdf");
    }

}
