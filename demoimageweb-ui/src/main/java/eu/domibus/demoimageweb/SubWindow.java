package eu.domibus.demoimageweb;

import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Created by nguyhoa on 29/07/2016.
 */
import com.vaadin.ui.Button.ClickListener;
class MySub extends Window {
    public MySub() {
        super("Subs on Sale"); // Set window caption
        center();

        // Some basic content for the window
        VerticalLayout content = new VerticalLayout();
        content.addComponent(new Label("Just say it's OK!"));
        content.setMargin(true);
        setContent(content);

        // Disable the close button
        setClosable(false);

        // Trivial logic for closing the sub-window
        Button ok = new Button("OK");
        ok.addClickListener(new ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                close(); // Close the sub-window
            }
        });
        content.addComponent(ok);
    }
}