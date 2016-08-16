package eu.domibus.demoimageweb.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;

/**
 * Created by nguyhoa on 16/08/2016.
 */
public class SentMessageBean implements Serializable {
    private static final Log LOG = LogFactory.getLog(SentMessageBean.class);
    private String originalSender;
    private String finalRecipient;
    private String fromParty;
    private String toParty;
    private String messageStatus;
    private String messageId;


    public SentMessageBean() {
        LOG.info("SentMessageBean Construction ");
    }


    public SentMessageBean(String originalSender, String finalRecipient, String fromParty, String toParty, String messageStatus, String messageId) {
        this.originalSender = originalSender;
        this.finalRecipient = finalRecipient;
        this.fromParty = fromParty;
        this.toParty = toParty;
        this.messageStatus = messageStatus;
        this.messageId = messageId;
        LOG.info(this.toString());
    }


    public String getOriginalSender() {
        return originalSender;
    }

    public void setOriginalSender(String originalSender) {
        this.originalSender = originalSender;
    }

    public String getFinalRecipient() {
        return finalRecipient;
    }

    public void setFinalRecipient(String finalRecipient) {
        this.finalRecipient = finalRecipient;
    }

    public String getFromParty() {
        return fromParty;
    }

    public void setFromParty(String fromParty) {
        this.fromParty = fromParty;
    }

    public String getToParty() {
        return toParty;
    }

    public void setToParty(String toParty) {
        this.toParty = toParty;
    }

    public String getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @Override
    public String toString() {
        return "SentMessageBean{" +
                "originalSender='" + originalSender + '\'' +
                ", finalRecipient='" + finalRecipient + '\'' +
                ", fromParty='" + fromParty + '\'' +
                ", toParty='" + toParty + '\'' +
                ", messageStatus='" + messageStatus + '\'' +
                ", messageId='" + messageId + '\'' +
                '}';
    }
}
