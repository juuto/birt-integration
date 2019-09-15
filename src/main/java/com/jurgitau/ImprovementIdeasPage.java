package com.jurgitau;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class ImprovementIdeasPage extends WebPage {

    public ImprovementIdeasPage(final PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new NavigationPanel("menu"));
    }

}
