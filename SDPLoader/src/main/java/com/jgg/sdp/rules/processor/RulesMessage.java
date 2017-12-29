package com.jgg.sdp.rules.processor;

import com.jgg.sdp.domain.services.rules.RULMessagesService;
import com.jgg.sdp.loader.jaxb.rules.Message;
import com.jgg.sdp.loader.jaxb.rules.TextType;
import com.jgg.sdp.adt.ADTBag;
import com.jgg.sdp.core.ctes.SYS;
import com.jgg.sdp.domain.rules.RULMessage;

public class RulesMessage {

	private RULMessagesService msgService = new RULMessagesService();
	
	private ADTBag<RULMessage> msgs = new ADTBag<RULMessage>();
	
    private static RulesMessage msg = null;
    
    private RulesMessage() {
    	
    }
    
    public static RulesMessage getInstance() {
    	if (msg == null) msg = new RulesMessage();
    	return msg;
    }
    
    public ADTBag<RULMessage> getMessages() {
    	return msgs;
    }
    
    public Long createMessage(Long key, Message msg) {
    	return createMessage(key, msg, 0L);
    }
    
    public Long createMessage(Long key, Message msg, Long idMsg) {
    	Long id = key;
    	
		if (msg == null) return idMsg;		

		msgService.deleteMessage(id);
		
		for (TextType v : msg.getText()) {
			createMsg(id, v.getLang(), v.getDialect(), v.getValue());
		}
		return key;
    }

    private void createMsg(Long key, String lang, String dialect, String text) {
		
		String l = lang    == null ? SYS.DEF_LANG    : lang;
		String d = dialect == null ? SYS.DEF_DIALECT : dialect;
		RULMessage r = new RULMessage();
		r.setIdMsg(key);
		r.setIdLang(l);
		r.setIdDialect(d);
		r.setTxt(text);
		msgs.add(r);			
    }
    
    public void clear() {
    	msgs = new ADTBag<RULMessage>();
    }
}
