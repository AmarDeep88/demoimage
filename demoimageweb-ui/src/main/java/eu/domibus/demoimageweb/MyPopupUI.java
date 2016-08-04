package eu.domibus.demoimageweb;

/**
 * Created by nguyhoa on 26/07/2016.
 */
import com.vaadin.annotations.Theme;
import com.vaadin.server.ThemeResource;
import com.vaadin.annotations.Widgetset;
import com.vaadin.ui.*;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Label;
import com.vaadin.server.VaadinRequest;


import java.io.File;
import java.util.Scanner;
import java.io.IOException;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.ClassResource;

@Theme("mytheme")
@Widgetset("eu.domibus.demoimageweb.MyAppWidgetset")
public class MyPopupUI extends UI {


    final String pmodeFilePath="pmode/domibus-cefsupportgw-pmode.xml";
    final String pmodeThemeResource="img/domibus-cefsupportgw-pmode.xml";

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout aVerticalLayout = new VerticalLayout();


        /*
        ClassLoader classLoader = getClass().getClassLoader();
        File afile = new File(classLoader.getResource(pmodeFilePath).getFile());
        StringBuilder result = new StringBuilder("");

        try (Scanner scanner = new Scanner(afile)) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append(line).append("\n");
            }

            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        final TextArea aTextArea = new TextArea();

        aTextArea.setValue(result.toString());
        aTextArea.setWidth("1200");
        aTextArea.setHeight("1200");

        aVerticalLayout.addComponent(aTextArea);
        */

        ThemeResource res = new ThemeResource("img/domibus-cefsupportgw-pmode.xml");
        Link link = new Link("Link to the image file", res);
        // Display the object
        //Embedded object = new Embedded("pmode", res);
        //object.setMimeType("xml"); // Unnecessary
        aVerticalLayout.addComponent(link);

        setContent(aVerticalLayout);

    }

}