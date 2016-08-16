package eu.domibus.demoimageweb;

import backend.ecodex.org._1_1.BackendInterface;
import backend.ecodex.org._1_1.PayloadType;
import backend.ecodex.org._1_1.SendRequest;
import backend.ecodex.org._1_1.SendResponse;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Viewport;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.PropertysetItem;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.QueryDelegate;
import com.vaadin.server.*;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomTable.RowHeaderMode;
import eu.domibus.common.dao.MessageLogDao;
import eu.domibus.common.model.logging.MessageLogEntry;
import eu.domibus.demoimageweb.model.SentMessageBean;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.oasis_open.docs.ebxml_msg.ebms.v3_0.ns.core._200704.*;
import org.tepi.filtertable.paged.PagedFilterControlConfig;
import org.tepi.filtertable.paged.PagedFilterTable;

import javax.servlet.annotation.WebServlet;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * This UI is the application entry point. A UI may either represent a browser window
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Viewport("user-scalable=no,initial-scale=1.0")
@Theme("mytheme")
@Widgetset("eu.domibus.demoimageweb.MyAppWidgetset")
public class MainUI extends UI {

    private final String payloadStr = "C:\\java\\workspaces\\demo-image\\demoimageweb\\src\\main\\resources\\payloads\\140.txt";

    private final String SenderWSDL = "http://localhost:8079/domibus/services/backend?wsdl";
    private final String RecipientWSDL = "http://localhost:8079/domibus/services/backend?wsdl";
    private final String AccesspointC2URL = "http://localhost:8079/domibus/home/messagelog";
    private final String AccesspointC3URL = "http://localhost:8081/domibus/home/messagelog";
    //public final String homeUrl="https://ec.europa.eu/cefdigital/wiki/display/CEFDIGITAL/Access+Point+software";
    private final String homeUrl = "http://ec.europa.eu/index_en.htm";

    private static final Log LOG = LogFactory.getLog(MainUI.class);

    private SimpleJDBCConnectionPool pool;


    enum FromPartyID {
        cefsupportID01,
        cefsupportID02
    }

    enum toPartyID {
        ceftestparty1ID01,
        ceftestparty1ID02
    }


    @Override
    protected void init(VaadinRequest vaadinRequest) {

        try {
            pool = new SimpleJDBCConnectionPool(
                    "com.mysql.jdbc.Driver",
                    "jdbc:mysql://localhost:3306/domibus_blue", "domibus_blue", "Domibus.123", 2, 5);

        } catch (SQLException e) {
            LOG.error(" Cannot connect to DB" + e.getMessage());
        }

        final VerticalLayout layout = new VerticalLayout();

        // vertical home tabsheet on top
        TabSheet homeTabsheet = new TabSheet();
        homeTabsheet.setHeight("100");
        homeTabsheet.setSizeFull();
        layout.addComponent(homeTabsheet);


        VerticalLayout HomeTab = new VerticalLayout();
        HomeTab.setMargin(true);
        HomeTab.setSpacing(true);
        HomeTab.setCaption("Home");
        HomeTab.setSizeFull();
        HomeTab.setHeight(DimensionPool.pageHeight);

        BrowserFrame browser = new BrowserFrame();
        browser.setSource(new ExternalResource(homeUrl));
        browser.setAlternateText("browser");
        browser.setCaptionAsHtml(true);
        browser.setImmediate(true);
        browser.setDescription("some description");
        browser.setSizeFull();

        HomeTab.addComponent(browser);


        homeTabsheet.addComponent(HomeTab);

        HorizontalLayout ConsolesTab = new HorizontalLayout();
        ConsolesTab.setMargin(true);
        ConsolesTab.setSpacing(true);
        ConsolesTab.setCaption("Consoles");
        ConsolesTab.setSizeFull();
        ConsolesTab.setHeight(DimensionPool.consolesTabHeight);
        homeTabsheet.addComponent(ConsolesTab);

        VerticalLayout ContactTab = new VerticalLayout();
        ContactTab.setMargin(true);
        ContactTab.setSpacing(true);
        ContactTab.setCaption("Contact");
        ContactTab.setHeight(DimensionPool.pageHeight);
        homeTabsheet.addComponent(ContactTab);


        ConsolesTab.setMargin(true);

        TabSheet topTabsheet = new TabSheet();
        topTabsheet.setSizeFull();

        ConsolesTab.addComponent(topTabsheet);

        ConsolesTab.setSizeFull();

        // Create the first tab
        LOG.info(" Create the first tab, going to add setBackendTabC1 ");
        VerticalLayout BackendC1Tab = new VerticalLayout();
        BackendC1Tab.setSizeFull();
        BackendC1Tab.setIcon(new ThemeResource("img/BackendC1.JPG"));

        BackendC1Tab.setHeight(DimensionPool.pageHeight);

        //setBackendTabC1(BackendC1Tab);
        setBackendTabC1(BackendC1Tab);
        topTabsheet.addTab(BackendC1Tab);


        // Create the secondTab
        VerticalLayout AccessPointC2Tab = new VerticalLayout();
        AccessPointC2Tab.setSizeFull();
        AccessPointC2Tab.setIcon(new ThemeResource("img/AccessPointC2.JPG"));

        AccessPointC2Tab.setHeight(DimensionPool.pageHeight);
        setAccessPointC2(AccessPointC2Tab);

        topTabsheet.addTab(AccessPointC2Tab);


        BrowserWindowOpener popupOpener = new BrowserWindowOpener(MyPopupUI.class);

        popupOpener.setFeatures("height=1200,width=1200");


        HomeTab.addComponent(browser);

        // Create the third tab
        VerticalLayout AccessPointC3Tab = new VerticalLayout();
        AccessPointC3Tab.setIcon(new ThemeResource("img/AccessPointC3.JPG"));

        topTabsheet.addTab(AccessPointC3Tab);
        AccessPointC3Tab.setHeight(DimensionPool.pageHeight);


        BrowserFrame AccessPointC3TabBrowser = new BrowserFrame();
        AccessPointC3TabBrowser.setSource(new ExternalResource(AccesspointC3URL));
        AccessPointC3TabBrowser.setAlternateText("browser");
        AccessPointC3TabBrowser.setCaptionAsHtml(true);
        AccessPointC3TabBrowser.setImmediate(true);
        AccessPointC3TabBrowser.setDescription("some description");
        AccessPointC3TabBrowser.setSizeFull();
        AccessPointC3TabBrowser.setHeight(DimensionPool.pageHeight);


        AccessPointC3Tab.addComponent(AccessPointC3TabBrowser);


        // Create the fourth  tab
        VerticalLayout BackendC4Tab = new VerticalLayout();
        BackendC4Tab.setIcon(new ThemeResource("img/BackendC4.JPG"));
        setBackendTabC4(BackendC4Tab);

        topTabsheet.addTab(BackendC4Tab).setCaption("");


        layout.setMargin(true);
        layout.setSpacing(true);

        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MainUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }


    public void setAccessPointC2(VerticalLayout aVerticalLayout) {


        List<MessageLogEntry> messageLogEntryList = new MessageLogDao().findAll();

        VerticalLayout messageLogVerticalLayout = new VerticalLayout();

        BeanItemContainer<MessageLogEntry> messageLogEntryBeanItemContainer = new BeanItemContainer<MessageLogEntry>(MessageLogEntry.class);


        PagedFilterTable<IndexedContainer> filterTable = new PagedFilterTable<IndexedContainer>();
        for (MessageLogEntry messageLogEntry : messageLogEntryList) {
            messageLogEntryBeanItemContainer.addItem(messageLogEntry);
        }

        filterTable.setSizeFull();
        filterTable.setFilterBarVisible(true);

        filterTable.setSelectable(true);
        filterTable.setImmediate(true);
        filterTable.setMultiSelect(true);

        filterTable.setRowHeaderMode(RowHeaderMode.INDEX);

        filterTable.setColumnCollapsingAllowed(true);

        filterTable.setColumnReorderingAllowed(true);


        filterTable.setContainerDataSource(messageLogEntryBeanItemContainer);

        filterTable.setPageLength(10);


        filterTable.setVisibleColumns(new String[]{"messageId", "messageStatus", "notificationStatus", "mshRole", "messageType", "deleted", "received", "sendAttempts", "sendAttemptsMax", "nextAttempt"});
        filterTable.setColumnHeaders(new String[]{"Message ID", "Message Satus", "Notification Status", "MshRole", "Message Type", "Deleted", "Received", "Failed Send Attemps", "Send Attemps Max", "Next Attempt"});

        messageLogVerticalLayout.addComponent(filterTable);
        messageLogVerticalLayout.addComponent(filterTable.createControls(new PagedFilterControlConfig()));

        aVerticalLayout.addComponent(messageLogVerticalLayout);


    }

    public void setMessagesSentC1(VerticalLayout aVerticalLayout) {


        VerticalLayout messageLogVerticalLayout = new VerticalLayout();


        aVerticalLayout.addComponent(messageLogVerticalLayout);


        Connection conn = null;
        Statement statement = null;
        SQLContainer container = null;
        ResultSet results;

        final BeanItemContainer<SentMessageBean> beanItemContainer = new BeanItemContainer<SentMessageBean>(SentMessageBean.class);
        ArrayList<SentMessageBean> beanlist = new ArrayList<SentMessageBean>();

        PagedFilterTable<IndexedContainer> filterTable = new PagedFilterTable<IndexedContainer>();


        try {
            conn = pool.reserveConnection();
            statement = conn.createStatement();
            results = statement.executeQuery(StringPool.Query4C1Str);
            int i = 0;

            while (results.next()) {
                PropertysetItem item = new PropertysetItem();
                SentMessageBean aSentMessageBean = new SentMessageBean(results.getString(1), results.getString(2), results.getString(3), results.getString(4), results.getString(5), results.getString(6));
                beanlist.add(aSentMessageBean);
            }

            beanItemContainer.addAll(beanlist);
            filterTable.setSizeFull();
            filterTable.setFilterBarVisible(true);

            filterTable.setSelectable(true);
            filterTable.setImmediate(true);
            filterTable.setMultiSelect(true);

            filterTable.setRowHeaderMode(RowHeaderMode.INDEX);

            filterTable.setColumnCollapsingAllowed(true);

            filterTable.setColumnReorderingAllowed(true);


            filterTable.setContainerDataSource(beanItemContainer);

            filterTable.setPageLength(10);
            filterTable.setVisibleColumns(new String[]{"originalSender", "finalRecipient", "fromParty", "toParty", "messageStatus", "messageId"});
            filterTable.setColumnHeaders(new String[]{"Original Sender", "Final Recipient", "From Party ID", "To Party ID", "Message Status", "Message Id"});

            messageLogVerticalLayout.addComponent(filterTable);
            messageLogVerticalLayout.addComponent(filterTable.createControls(new PagedFilterControlConfig()));


            LOG.info("table construction is finnished ");

        } catch (SQLException e) {
            LOG.error("SQLException error " + e.getMessage());
        } catch (UnsupportedOperationException e) {
            LOG.error("UnsupportedOperationException error " + e.getMessage());

        } catch (Exception e) {
            LOG.error("db error " + e.getMessage());
        }


    }

    public void setBackendTabC1(VerticalLayout aVerticalLayout) {
        LOG.info("setBackendTabC1");

        LOG.info("going to add sendMessagePanel");
        sendMessagePanel(aVerticalLayout);

        setMessagesSentC1(aVerticalLayout);
    }


    public void sendMessagePanel(VerticalLayout aVerticalLayout) {

        LOG.info("sendMessagePanel ");

        VerticalLayout topC1BackendText = new VerticalLayout();
        topC1BackendText.setMargin(true);
        topC1BackendText.setSpacing(true);
        topC1BackendText.setWidthUndefined();
        topC1BackendText.setHeight("150");
        topC1BackendText.addComponent(new Label(StringPool.SendMessageLabel));
        topC1BackendText.addComponent(new Label(StringPool.C1BackendIntroLabel));

        aVerticalLayout.addComponent(topC1BackendText);


        aVerticalLayout.setComponentAlignment(topC1BackendText, Alignment.TOP_RIGHT);

        HorizontalLayout messagePropertiesValue = new HorizontalLayout();
        messagePropertiesValue.setWidthUndefined();

        messagePropertiesValue.setSpacing(true);
        messagePropertiesValue.addComponent(new Label(StringPool.MessagePropertieslabel));
        messagePropertiesValue.addComponent(new Label(StringPool.Valuelabel));

        aVerticalLayout.addComponent(messagePropertiesValue);
        aVerticalLayout.setComponentAlignment(messagePropertiesValue, Alignment.TOP_RIGHT);


        HorizontalLayout aMessageAndPmodeHorizontalLayout = new HorizontalLayout();
        aMessageAndPmodeHorizontalLayout.setWidthUndefined();


        final Label messageSentLabel = new Label(StringPool.MessageIDLabel);
        final Label messageSentID = new Label();
        messageSentID.setCaption(StringPool.DashLine);

        final Label messageErrorLabel = new Label(StringPool.ErrorLabel);
        final Label messageErrorID = new Label("-----------------------------------");

        HorizontalLayout aResultHorizontalLayout = new HorizontalLayout();
        aResultHorizontalLayout.setHeightUndefined();

        HorizontalLayout aFromPartyHorizontalLayout = new HorizontalLayout();

        aFromPartyHorizontalLayout.setMargin(true);
        aFromPartyHorizontalLayout.setSpacing(true);

        aFromPartyHorizontalLayout.addComponent(new Label(StringPool.FromPartyIDLabel));

        final ComboBox fromPartyIDComboBox = new ComboBox();
        fromPartyIDComboBox.addItems();
        fromPartyIDComboBox.setInvalidAllowed(false);
        fromPartyIDComboBox.setNullSelectionAllowed(false);
        fromPartyIDComboBox.setImmediate(true);

        fromPartyIDComboBox.addItem(FromPartyID.cefsupportID01);
        fromPartyIDComboBox.addItem(FromPartyID.cefsupportID02);


        fromPartyIDComboBox.setValue(FromPartyID.cefsupportID01);
        aFromPartyHorizontalLayout.addComponent(fromPartyIDComboBox);
        aVerticalLayout.addComponent(aFromPartyHorizontalLayout);
        aVerticalLayout.setComponentAlignment(aFromPartyHorizontalLayout, Alignment.TOP_RIGHT);


        HorizontalLayout aToPartyHorizontalLayout = new HorizontalLayout();

        aToPartyHorizontalLayout.setWidthUndefined();
        aToPartyHorizontalLayout.addComponent(new Label(StringPool.ToPartyIDLabel));
        aToPartyHorizontalLayout.setHeightUndefined();


        // Prepare the itemList
        List<PartyID> PartyIDList = new ArrayList<PartyID>();
        PartyIDList.add(new PartyID(StringPool.ceftestparty1ID01));
        PartyIDList.add(new PartyID(StringPool.ceftestparty1ID02));

        final ComboBox toPartyIDComboBox = new ComboBox();
        toPartyIDComboBox.setInvalidAllowed(false);
        toPartyIDComboBox.setNullSelectionAllowed(false);
        toPartyIDComboBox.setImmediate(true);
        toPartyIDComboBox.addItem(toPartyID.ceftestparty1ID01);
        toPartyIDComboBox.addItem(toPartyID.ceftestparty1ID02);


        toPartyIDComboBox.setValue(toPartyID.ceftestparty1ID01);
        aToPartyHorizontalLayout.addComponent(toPartyIDComboBox);
        aVerticalLayout.addComponent(aToPartyHorizontalLayout);
        aVerticalLayout.setComponentAlignment(aToPartyHorizontalLayout, Alignment.TOP_RIGHT);


        final Label MessagePayloadLabel= new Label(StringPool.MessagePayloadLabel);
        aVerticalLayout.addComponent(MessagePayloadLabel);
        aVerticalLayout.setComponentAlignment(MessagePayloadLabel, Alignment.TOP_LEFT);

        final TextArea messagePayloadTextArea  = new TextArea();
        messagePayloadTextArea.setValue("hello world");
        messagePayloadTextArea.setRows(5);
        messagePayloadTextArea.setColumns(20);

        messagePayloadTextArea.setImmediate(true);
        messagePayloadTextArea.setWordwrap(true);

        aVerticalLayout.addComponent(messagePayloadTextArea);
        aVerticalLayout.setComponentAlignment(messagePayloadTextArea, Alignment.TOP_RIGHT);


        final Button sendMessageButton = new Button(StringPool.SendMessageLabel);

        HorizontalLayout anErrorHorizontalLayout = new HorizontalLayout();
        anErrorHorizontalLayout.setHeightUndefined();


        sendMessageButton.addClickListener(new Button.ClickListener() {
            SendResponse response = null;

            public void buttonClick(ClickEvent event) {
                sendMessageButton.setCaption("Send Message");
                try {
                    LOG.info("fromPartyIDComboBox :" + fromPartyIDComboBox.getValue().toString() + toPartyIDComboBox.getValue()+", messagePayloadTextArea : " + messagePayloadTextArea.getValue());
                    response = sendMessage2wsdl(messagePayloadTextArea.getValue(), SenderWSDL, fromPartyIDComboBox.getValue().toString(), toPartyIDComboBox.getValue().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    messageSentID.setCaption(" Message has not yet sent");
                }
                LOG.info("main" + " - messageId: " + response.getMessageID().get(0));
                messageSentID.setCaption(response.getMessageID().get(0));
            }
        });

        ThemeResource pmodeThemeResource = new ThemeResource("img/domibus-cefsupportgw-pmode.xml");
        Link popupButton = new Link(StringPool.SeePmodeLabel, pmodeThemeResource);
        popupButton.setSizeFull();

        aMessageAndPmodeHorizontalLayout.addComponent(sendMessageButton);
        aMessageAndPmodeHorizontalLayout.addComponent(popupButton);
        aMessageAndPmodeHorizontalLayout.setSpacing(true);
        aMessageAndPmodeHorizontalLayout.setMargin(true);

        aVerticalLayout.addComponent(aMessageAndPmodeHorizontalLayout);
        aVerticalLayout.setComponentAlignment(aMessageAndPmodeHorizontalLayout, Alignment.TOP_RIGHT);

        Label resultLabel = new Label(StringPool.ResultLabel);
        aVerticalLayout.addComponent(resultLabel);
        aVerticalLayout.setComponentAlignment(resultLabel, Alignment.TOP_RIGHT);


        aResultHorizontalLayout.addComponent(messageSentLabel);
        aResultHorizontalLayout.addComponent(messageSentID);
        aResultHorizontalLayout.setSpacing(true);
        aResultHorizontalLayout.setMargin(true);

        aVerticalLayout.addComponent(aResultHorizontalLayout);
        aVerticalLayout.setComponentAlignment(aResultHorizontalLayout, Alignment.TOP_RIGHT);


        anErrorHorizontalLayout.addComponent(messageErrorLabel);
        anErrorHorizontalLayout.addComponent(messageErrorID);
        anErrorHorizontalLayout.setSpacing(true);
        anErrorHorizontalLayout.setMargin(true);


        aVerticalLayout.addComponent(anErrorHorizontalLayout);
        aVerticalLayout.setComponentAlignment(anErrorHorizontalLayout, Alignment.TOP_RIGHT);
    }

    public void setBackendTabC4(VerticalLayout aVerticalLayout) {
        LOG.info("setBackendTabC4 ");

        final TextField textField = new TextField();
        final Label messageSentLabel = new Label("Message ID  :");
        final Label messageSentID = new Label("----------------------------");
        final Label messageErrorLabel = new Label("Error:");
        final Label messageErrorID = new Label("Error:");
        textField.setCaption("Type your PartyID here:");

        final Button button = new Button("Send");


        button.addClickListener(new Button.ClickListener() {
            SendResponse response = null;

            public void buttonClick(ClickEvent event) {
                button.setCaption("Send");
                try {
                    response = sendMessage2wsdl(payloadStr, RecipientWSDL, StringPool.C4PartyIDStr, StringPool.C1PartyIDStr);
                    System.out.println(" from C4PartyIDStr: " + StringPool.C4PartyIDStr + "  to  C1PartyIDStr:  " + StringPool.C1PartyIDStr);
                } catch (Exception e) {
                    e.printStackTrace();
                    messageErrorID.setCaption(" Message has not yet sent");
                }
                System.out.println("main" + " - messageId: " + response.getMessageID().get(0));
                messageSentID.setCaption(response.getMessageID().get(0));
            }
        });

        aVerticalLayout.addComponent(button);
        aVerticalLayout.addComponent(messageSentLabel);
        aVerticalLayout.addComponent(messageSentID);


    }


    public static SendResponse sendMessage2wsdl(String  payloadStr, String wsdlStr, String senderStr, String recepientStr) throws Exception {
        URL wsdlURL = new URL(wsdlStr);
        QName SERVICE_NAME = new QName("http://org.ecodex.backend/1_1/", "BackendService_1_1");
        Service service = Service.create(wsdlURL, SERVICE_NAME);
        BackendInterface client = service.getPort(BackendInterface.class);
        String payloadHref = "cid:message";

        SendRequest sendRequest = createSendRequest(payloadHref,  payloadStr);

        Messaging ebMSHeaderInfo = createMessage_sender_2_recipient(payloadHref, null, senderStr, recepientStr);

        return client.sendMessage(sendRequest, ebMSHeaderInfo);

    }


    protected static SendRequest createSendRequest(String payloadHref,  String payloadStr) {
        SendRequest sendRequest = new SendRequest();
        PayloadType payload = new PayloadType();
        payload.setPayloadId(payloadHref);
        payload.setContentType("text/xml");
        byte[] payloadBytes = payloadStr.getBytes();

      /*  byte[] payloadBytes = null;
        try {
            payloadBytes = IOUtils.toByteArray(new FileInputStream(new File(filename)));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        payload.setValue(Base64.decodeBase64(payloadBytes));
        sendRequest.getPayload().add(payload);

        return sendRequest;
    }


    protected static Messaging createMessage_sender_2_recipient(String payloadHref, String mimeType, String senderStr, String recipientStr) {
        Messaging ebMSHeaderInfo = new Messaging();
        UserMessage userMessage = new UserMessage();
        CollaborationInfo collaborationInfo = new CollaborationInfo();
        collaborationInfo.setAction("submitMessage");
        org.oasis_open.docs.ebxml_msg.ebms.v3_0.ns.core._200704.Service service = new org.oasis_open.docs.ebxml_msg.ebms.v3_0.ns.core._200704.Service();
        service.setValue("http://ec.europa.eu/e-delivery/services/connectivity-service");
        service.setType("e-delivery");
        collaborationInfo.setService(service);
        userMessage.setCollaborationInfo(collaborationInfo);
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.getProperty().add(createProperty("originalSender", "urn:oasis:names:tc:ebcore:partyid-type:unregistered:C1", "string"));
        messageProperties.getProperty().add(createProperty("finalRecipient", "urn:oasis:names:tc:ebcore:partyid-type:unregistered:C4", "string"));
        userMessage.setMessageProperties(messageProperties);
        PartyInfo partyInfo = new PartyInfo();
        From from = new From();
        from.setRole("http://docs.oasis-open.org/ebxml-msg/ebms/v3.0/ns/core/200704/initiator");
        PartyId sender = new PartyId();

        sender.setType("urn:oasis:names:tc:ebcore:partyid-type:unregistered");
        //sender.setValue("cefsupportgw");
        sender.setValue(senderStr);


        //sender.setType("urn:oasis:names:tc:ebcore:partyid-type:unregistered");
        from.getPartyId().add(sender);
        partyInfo.setFrom(from);
        To to = new To();
        to.setRole("http://docs.oasis-open.org/ebxml-msg/ebms/v3.0/ns/core/200704/responder");
        PartyId receiver = new PartyId();
        receiver.setType("urn:oasis:names:tc:ebcore:partyid-type:unregistered");
        //receiver.setValue("ceftestparty1gw");
        receiver.setValue(recipientStr);
        to.getPartyId().add(receiver);
        partyInfo.setTo(to);
        userMessage.setPartyInfo(partyInfo);
        PayloadInfo payloadInfo = new PayloadInfo();
        PartInfo partInfo = new PartInfo();
        partInfo.setHref(payloadHref);
        payloadInfo.getPartInfo().add(partInfo);
        userMessage.setPayloadInfo(payloadInfo);
        ebMSHeaderInfo.setUserMessage(userMessage);
        return ebMSHeaderInfo;
    }


    protected static Property createProperty(String name, String value, String type) {
        Property aProperty = new Property();
        aProperty.setValue(value);
        aProperty.setName(name);
        aProperty.setType(type);
        return aProperty;
    }


    /**
     * Table should never end up calling indexOfId in this case
     */
    private class LimitedSQLContainer extends SQLContainer {

        public LimitedSQLContainer(QueryDelegate delegate) throws SQLException {
            super(delegate);
        }

        @Override
        public int indexOfId(Object itemId) {
            throw new RuntimeException("This function should not be called");
        }
    }
}
