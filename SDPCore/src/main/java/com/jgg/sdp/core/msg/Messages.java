/*
 * 
 */
package com.jgg.sdp.core.msg;

import java.text.SimpleDateFormat;
import java.util.*;

import org.slf4j.*;

import com.jgg.sdp.core.config.Configuration;
import com.jgg.sdp.core.exceptions.SDPException;


public class Messages {

    private static Logger LOGGER     = null;
	private static Messages messages = null;
    private static Configuration cfg = null;
	private final ResourceBundle msg = ResourceBundle.getBundle("messages");
	
	private int levelInfo = 0;
    private boolean inLine = false;
	
	private SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
	private Messages() {
	    if (LOGGER == null) {
	        LOGGER = LoggerFactory.getLogger("SDP");
	    }
	}
	private Messages(String nombre) {
	    LOGGER = LoggerFactory.getLogger(nombre);
	}
	
    public static Messages getInstance() {
        if (messages == null) messages = new Messages();
        return messages;        
    }
    
	public static Messages getInstance(String name) {
		if (messages == null) messages = new Messages(name);
		return messages;
	}
	
	public static void setLogger(String name) {
	    if (messages == null) getInstance(name);
	    LOGGER = LoggerFactory.getLogger(name);
	}


	public void warning(int code) {
	    warning(code, (Object[]) null);
	}
	
    public void warning(int code, Object... args) {
        if (LOGGER.isWarnEnabled()) {
            LOGGER.warn(makeMsg("W%05d - %s", code, getMsg(code, args)));
        }
    }
    
    public void exception(Exception e) {
        LOGGER.error("E%05d - %s", 99999, e.getMessage());
    }
    
	public void exception(SDPException e) {
	    String[] lineas = e.getMessage().split("\\n");
	    for (int index = 0; index < lineas.length; index++) {
           LOGGER.error(makeMsg("E%05d - %s", e.getErrorCode(), lineas[index]));
	    }   
     }

	public void progress(int id, Object... args) {
		progressCont(id, args);
		System.out.println("");
		inLine = false;
	}
	
	public void progressCont(int id, Object... args) {
		if (!inLine) {
	       System.out.print(df.format(new Date()) + " - ");
		}   
	    progressContEx(id, args);
	}

	public void progressContEx(int id, Object... args) {
	    System.out.print(getMsg(id, args));
	    inLine = true;
	}
	
	private String makeMsg(String format, Object... args) {
	    return String.format(format, args);
	}
	
	public String getMsg(String strCode) {
		return getMsg(Integer.parseInt(strCode), (String) null);
	}
	
	public String getMsg(int code, String arg) {
		return getMsg(code, new Object[] {arg});
	}
	
	public String getMsg(int code, Object... args) {
		String txtCode = String.format("%05d", code);
		String fmt = montaMensaje(txtCode); 
		
		return String.format(fmt, args);
	}
	
	public void trace(int requiredLevel, int msgCode, Object... vars) {
		trace(requiredLevel, true, msgCode, vars);
	}
	
	public void trace(int requiredLevel, boolean endLine, int msgCode, Object... vars) {
	    if (cfg == null) loadConfiguracion();
	    if (levelInfo >= requiredLevel) {
	    	LOGGER.info(getMsg(msgCode, vars));
	    }
	}
	
	private void loadConfiguracion() {
        cfg = Configuration.getInstance();
        levelInfo = cfg.getVerbose(); 
    }

	private String montaMensaje(String szCode) {
	    int beg = -1;
	    int end = -1;
	    String aux = msg.getString(szCode);
	    beg = aux.indexOf('{');
	    while (beg != -1) {
	        end = aux.indexOf('}');
	        String tok = "\\" + aux.substring(beg,end) + "\\}";
	        String cad = msg.getString(aux.substring(beg + 1, end));
	        aux = aux.replaceAll(tok, cad);
	        beg = aux.indexOf('{');
	    }
	    return aux;
	}

}
