package eu.domibus.demoimageweb;

/**
 * Created by nguyhoa on 01/08/2016.
 */
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Label;
import com.vaadin.server.VaadinRequest;
public class PopupUI extends UI {
    @Override
    protected void init(VaadinRequest request) {
        System.out.println("init");
        setContent(new Label("This is a POPUP where parameter foo="
                + request.getParameter("foo") + " and fragment is set to "
                + getPage().getUriFragment()));
    }



}
