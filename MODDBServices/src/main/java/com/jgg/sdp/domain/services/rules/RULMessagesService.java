package com.jgg.sdp.domain.services.rules;

import java.util.HashMap;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.core.ctes.SYS;
import com.jgg.sdp.domain.rules.RULMessage;
import com.jgg.sdp.domain.services.AbstractService;

@Repository
public class RULMessagesService extends AbstractService<RULMessage> {

	private HashMap<Long, RULMessage> msgs = new HashMap<Long, RULMessage>();

	public String getMessage(Long idMsg, String[] lang) {
		if (idMsg == null) return null;
		
		RULMessage msg = msgs.get(idMsg);
		
		if (msg != null) return msg.getTxt();
		
	    msg = findQuery(RULMessage.getMessage,idMsg, lang[0], lang[1]);
		
		// Dialect
		if (msg == null) {
            msg = findQuery(RULMessage.getMessage,idMsg, lang[0], SYS.DEF_DIALECT);
		}
		
		// Default lang
		if (msg == null) {
            msg = findQuery(RULMessage.getMessage, idMsg, SYS.DEF_LANG, SYS.DEF_DIALECT);
		}
		
		msgs.put(idMsg, msg);
		
		return (msg == null) ? null : msg.getTxt();
	}
	
	public void deleteMessage(Long idMsg) {
		deleteQuery(RULMessage.delMessages, idMsg);
	}
	
}
