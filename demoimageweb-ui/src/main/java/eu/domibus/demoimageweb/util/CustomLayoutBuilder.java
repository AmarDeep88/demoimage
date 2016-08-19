package eu.domibus.demoimageweb.util;

import java.io.IOException;

import java.io.IOException;

import com.vaadin.ui.CustomLayout;
import eu.domibus.demoimageweb.MainUI;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by nguyhoa on 17/08/2016.
 */
public class CustomLayoutBuilder {

    private final String   layoutLocation;
    private final Class<?> source;
    private final String   defaultSourceFolder = "/eu/domibus/demoimageweb/";

    private static final Log logger = LogFactory.getLog(CustomLayoutBuilder.class);

    /**
     * Constructeur
     *
     * @param layoutLocation
     *            chemin d'accès au HTML définissant le layout de l'élément.
     * @param source
     *            classe gérant le {@link CustomLayout} qui fournis the chemin de base pour accèder au HTML.
     */
    public CustomLayoutBuilder(final String layoutLocation, final Class<?> source) {
        this.layoutLocation = layoutLocation;
        this.source = source;
    }

    /**
     * instanciateLayout : Instanciation du {@link CustomLayout} par rapport aà la classe fournie.
     *
     * @return le {@link CustomLayout} désiré.
     */
    public CustomLayout instanciateLayout() {
        try {
            CustomLayout customLayout = null;
            if (source == null) {
                customLayout = new CustomLayout(getClass().getResourceAsStream(defaultSourceFolder + layoutLocation));
            } else {
                logger.info("layoutLocation: " +layoutLocation);
                logger.info("source.getResourceAsStream(layoutLocation) : " +source.getResourceAsStream(layoutLocation));
                customLayout = new CustomLayout(source.getResourceAsStream(layoutLocation));
            }
            if (customLayout != null) {
                customLayout.setSizeUndefined();
            }else{
                customLayout = new CustomLayout("home-page");
            }
            logger.info("customLayout: " +customLayout);
            return customLayout;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
