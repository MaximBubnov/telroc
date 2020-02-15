package com.packagename.telroc.ui;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route("page")
public class AnotherPageView extends VerticalLayout {

    private RouterLink link = new RouterLink("Go to Main Page", MainView.class);
    public AnotherPageView() {
        add(link);
    }
}
