/*
 * 
 */
package com.jgg.sdp.core.msg;

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
    
	/**
     * Obtiene la instancia actual de Messages.
     *
     * @param name
     *            the name
     * @return la instancia Messages
     */
	public static Messages getInstance(String name) {
		if (messages == null) messages = new Messages(name);
		return messages;
	}
	
	public static void setLogger(String name) {
	    if (messages == null) getInstance(name);
	    LOGGER = LoggerFactory.getLogger(name);
	}


	/**
     * Warning.
     *
     * @param code
     *            the code
     */
	public void warning(int code) {
	    warning(code, (Object[]) null);
	}
	
    /**
     * Warning.
     *
     * @param code
     *            the code
     * @param args
     *            the args
     */
    public void warning(int code, Object... args) {
        if (LOGGER.isWarnEnabled()) {
            LOGGER.warn(makeMsg("W%05d - %s", code, getMsg(code, args)));
        }
    }
    
    /**
     * Exception.
     *
     * @param e
     *            the e
     */
    public void exception(Exception e) {
        LOGGER.error("E%05d - %s", 99999, e.getMessage());
/*JGG No imprimir la pila        
        for (StackTraceElement ele : e.getStackTrace()) {
            LOGGER.error(ele.toString());
        }
*/        
    }
    
	/**
     * Exception.
     *
     * @param e
     *            the e
     */
	public void exception(SDPException e) {
	    String[] lineas = e.getMessage().split("\\n");
	    for (int index = 0; index < lineas.length; index++) {
           LOGGER.error(makeMsg("E%05d - %s", e.getErrorCode(), lineas[index]));
	    }   
	    /* JGG No incluir el volcado de la pila
        for (StackTraceElement ele : e.getStackTrace()) {
            LOGGER.error(ele.toString());
        }
        */
     }

	private String makeMsg(String format, Object... args) {
	    return String.format(format, args);
	}
	
	/**
     * Gets the msg.
     *
     * @param strCode
     *            the str code
     * @return the msg
     */
	public String getMsg(String strCode) {
		return getMsg(Integer.parseInt(strCode), (String) null);
	}
	
	/**
     * Gets the msg.
     *
     * @param code
     *            the code
     * @param arg
     *            the arg
     * @return the msg
     */
	public String getMsg(int code, String arg) {
		return getMsg(code, new Object[] {arg});
	}
	
	/**
     * Gets the msg.
     *
     * @param code
     *            the code
     * @param args
     *            the args
     * @return the msg
     */
	public String getMsg(int code, Object... args) {
		String txtCode = String.format("%05d", code);
		String fmt = montaMensaje(txtCode); 
		
		return String.format(fmt, args);
	}
	
	/**
     * Progress.
     *
     * @param requiredLevel
     *            the required level
     * @param msgCode
     *            the msg code
     * @param vars
     *            the vars
     */
	public void progress(int requiredLevel, int msgCode, Object... vars) {
	    if (cfg == null) loadConfiguracion();
	    if (levelInfo >= requiredLevel) LOGGER.info(getMsg(msgCode, vars));
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
