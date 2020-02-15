package com.packagename.telroc.ui;

import com.packagename.telroc.back.domain.User;
import com.packagename.telroc.back.service.UserService;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import org.springframework.beans.factory.annotation.Autowired;

@Route("")
public class MainView extends VerticalLayout {

    private Grid<User> grid = new Grid<>(User.class);
    private final UserForm form;

    private final UserService service;

    @Autowired
    public MainView(UserService service) {
        this.service = service;
        grid.asSingleSelect().addValueChangeListener(event -> editUser(event.getValue()));
        grid.setColumns("username", "password");

        form = new UserForm();
        form.addListener(UserForm.SaveEvent.class, this::saveUser);
        form.addListener(UserForm.DeleteEvent.class, this::deleteUser);
        form.setVisible(false);

        Button add = new Button("Add User", click -> addUser());

        add(new Anchor("/logout", "Log Out"),
                grid,
                new HorizontalLayout(add, new RouterLink("Go to Another Page", AnotherPageView.class)), form);
        updateList();

    }

    private void addUser() {
        grid.asSingleSelect().clear();
        editUser(new User());
        form.setVisible(true);
    }

    private void deleteUser(UserForm.DeleteEvent event) {
        service.deleteUser(event.getUser());
        updateList();
    }

    private void saveUser(UserForm.SaveEvent event) {
        service.createUser(event.getUser());
        updateList();
        form.setVisible(false);
    }

    private void editUser(User us) {
        form.setUser(us);
        form.setVisible(true);
    }

    private void updateList() {
        grid.setItems(service.findAll());
    }

}
