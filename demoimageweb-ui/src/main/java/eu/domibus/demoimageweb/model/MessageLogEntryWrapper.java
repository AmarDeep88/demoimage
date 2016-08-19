package eu.domibus.demoimageweb.model;

import eu.domibus.common.model.logging.MessageLogEntry;
import eu.domibus.common.model.org.oasis_open.docs.ebxml_msg.ebms.v3_0.ns.core._200704.UserMessage;
/**
 * Created by nguyhoa on 08/08/2016.
 */


public class MessageLogEntryWrapper {

    private final MessageLogEntry  messageLogEntryDetail;

    private final UserMessage userMessageDetail;

    public MessageLogEntryWrapper(MessageLogEntry messageLogEntryDetail,UserMessage userMessageDetail) {
        this.messageLogEntryDetail = messageLogEntryDetail;
        this.userMessageDetail = userMessageDetail;
    }

}
