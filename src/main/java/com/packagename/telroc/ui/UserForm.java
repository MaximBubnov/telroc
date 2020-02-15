package com.packagename.telroc.ui;

import com.packagename.telroc.back.domain.User;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

public class UserForm extends FormLayout {

    private TextField username = new TextField("Username");
    private TextField password = new TextField("Password");

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    private Binder<User> binder = new BeanValidationBinder<>(User.class);

    public UserForm() {

        binder.bindInstanceFields(this);
        add(username, password, createButtons());
    }

    public void setUser(User user) {
        binder.setBean(user);
    }

    private Component createButtons() {

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);

        save.addClickShortcut(Key.ENTER);

        save.addClickListener(click -> checkAndSave());
        delete.addClickListener(click -> fireEvent(new DeleteEvent(this, binder.getBean())));

        binder.addStatusChangeListener(event -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete);
    }

    private void checkAndSave() {
        if(binder.isValid()) {
            fireEvent(new SaveEvent(this, binder.getBean()));
        }
    }

    public static abstract class ContactFormEvent extends ComponentEvent<UserForm> {
        private User user;

        protected ContactFormEvent(UserForm source, User user) { //
            super(source, false);
            this.user = user;
        }

        public User getUser() {
            return user;
        }
    }

    public static class SaveEvent extends ContactFormEvent {
        SaveEvent(UserForm source, User contact) {
            super(source, contact);
        }
    }

    public static class DeleteEvent extends ContactFormEvent {
        DeleteEvent(UserForm source, User contact) {
            super(source, contact);
        }

    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) { //
        return getEventBus().addListener(eventType, listener);
    }

}
