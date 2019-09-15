package com.jurgitau;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

public class NavigationPanel extends Panel {

    public NavigationPanel(String id) {
        super(id);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new Link<Void>("reports") {
            @Override
            public void onClick() {
                setResponsePage(new ReportsPage(getPage().getPageParameters()));
            }},

            new Link<Void>("resources") {
            @Override
            public void onClick() {
                setResponsePage(new ResourcePage(getPage().getPageParameters()));
            }},

            new Link<Void>("improvements") {
            @Override
            public void onClick() {
                setResponsePage(new ImprovementIdeasPage(getPage().getPageParameters()));
            }},

            new Link<Void>("home") {
            @Override
            public void onClick() {
                setResponsePage(new HomePage(getPage().getPageParameters()));
            }
        });
    }

}
