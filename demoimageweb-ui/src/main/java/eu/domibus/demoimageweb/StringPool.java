package eu.domibus.demoimageweb;

/**
 * Created by nguyhoa on 27/07/2016.
 */
public class StringPool {

    public static final String Home_Caption = "Home";
    public static final String Consoles_Caption = "Consoles";
    public static final String Contact_Caption = "Contact";

    public static final String BackendC1_Caption = "BackendC1";
    public static final String AccessPointC2_Caption = "AccessPointC2";
    public static final String AccessPointC3_Caption = "AccessPointC3";
    public static final String BackendC4_Caption = "BackendC4";

    public static final String C1PartyIDStr = "cefsupportID01";
    public static final String C4PartyIDStr = "ceftestparty1ID01";

    public static final String cefsupportID01 = "cefsupportID01";
    public static final String cefsupportID02 = "cefsupportID02";
    public static final String ceftestparty1ID01 = "ceftestparty1ID01";
    public static final String ceftestparty1ID02 = "ceftestparty1ID02";

    public static final String C1BackendIntroLabel ="This section is stimulating Corner 1 and it will be used to send a message payload to Corner 2";
    public static final String FromPartyIDLabel = "From Party ID: ";
    public static final String ToPartyIDLabel =   "To Party ID: ";
    public static final String SeePmodeLabel = "See Pmode ";
    public static final String SendMessageLabel    = "Send Message";
    public static final String MessageIDLabel      ="Message ID :";
    public static final String ErrorLabel          ="Error :";
    public static final String DashLine      ="--------------------------------------";

    public static final String MessagePropertieslabel  ="Message Properties";
    public static final String Valuelabel  = "Value";
    public static final String ResultLabel  = "Result";

    public static final String MessagePayloadLabel  = "Message Payload";
    public static final String MessagePayloadTextBox   = "MessagePayloadTextBox";

    public static final String ORIGINAL_SENDER ="ORIGINAL SENDER";
    public static final String FINAL_RECIPIENT ="FINAL RECIPIENT";
    public static final String FROM            ="FROM PARTY";
    public static final String TO            =  "TO PARTY";
    public static final String STATUS            =  "STATUS";
    public static final String MESSAGE_ID            =  "MESSAGE ID";

    public static final String CONTENT            =  "CONTENT";

    public static final String Query4C1Str ="SELECT  " +
    "tbPropertyS.value           AS \"ORIGINAL SENDER\","+
    "tbPropertyD.value           AS \"FINAL RECIPIENT\","+
    "tbPartyIdFrom.VALUE         AS \"FROM PARTY\","+
    "tbPartyIdTo.VALUE           AS \"TO PARTY\","+
    "tbMessageLog.MESSAGE_STATUS AS \"STATUS\","+
    "tbMessageInfo.MESSAGE_ID    AS \"MESSAGE ID\""+


    " FROM "+
    "TB_PROPERTY tbPropertyS,"+
    "TB_PROPERTY tbPropertyD,"+
    "TB_USER_MESSAGE tbUserMessage,"+
    "TB_MESSAGE_INFO tbMessageInfo,"+
    "TB_MESSAGE_LOG tbMessageLog,"+
    "tb_party_id tbPartyIdFrom,"+
    "tb_party_id tbPartyIdTo,"+
    "TB_PART_INFO tbPartInfo "+

    " WHERE "+
    "tbPartyIdTo.TO_ID                  = tbUserMessage.ID_PK "+
    "AND tbPartyIdFrom.FROM_ID            = tbUserMessage.ID_PK "+
    "AND tbPartyIdFrom.FROM_ID            = tbPropertyS.MESSAGEPROPERTIES_ID "+
    "AND tbPropertyD.MESSAGEPROPERTIES_ID = tbUserMessage.ID_PK "+
    "AND tbUserMessage.messageinfo_id_pk  = tbMessageInfo.ID_PK "+
    "AND tbMessageLog.MESSAGE_ID          = tbMessageInfo.MESSAGE_ID "+
    "AND tbPartInfo.PAYLOADINFO_ID        = tbUserMessage.ID_PK  "+
    "AND tbPropertyS.name                 = 'originalSender'  "+
    "AND tbPropertyD.name                 = 'finalRecipient'  " ;

        //"tbPartInfo.BINARY_DATA      AS \"CONTENT\" "+

    public static final String QuerytbMessageLogStr ="SELECT  tbMessageLog.MESSAGE_ID FROM   TB_MESSAGE_LOG tbMessageLog";




}
