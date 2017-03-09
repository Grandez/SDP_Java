/*
 * 
 */
package com.jgg.sdp.core.config;

import java.io.File;
import java.util.*;

import com.jgg.sdp.core.ctes.*;
import com.jgg.sdp.core.msg.Messages;
import com.jgg.sdp.core.tools.Propiedades;

public class Configuration {
	
	private static Configuration cfg = null;
    protected HashMap<String, String> conf = new HashMap<String, String>();

    private ArrayList<Integer> titles = new ArrayList<Integer>();

    private Args parms = null;

    private Messages msg = Messages.getInstance();
    
	protected Configuration() {
		titles.add(200);
		loadDefaultValues();
	}
	
	public static Configuration getInstance() {
		if (cfg == null) cfg = new Configuration();
		return cfg;
	}

    /**
     * Analiza la linea de comandos, configurando las diferentes opciones
     * pasadas en el Si como parametro se ha pasado un fichero alternativo de
     * configuracion, se procesa este y se vuelven a procesar la linea de
     * comandos.
     *
     * @param prm
     *            Tabla de parametros soportados
     * @param args
     *            Argumentos pasados por la linea de comandos
     * @return Array de argumentos de la linea de comandos que no estan
     *         asociados a ninguna opcion
     */
	public String[] processCommandLine(String[][] prm, String[] args) {
		parms = new Args(prm);
		String[] resto = parms.parseArgs(args);
        if (cfg.getString(CFG.HELP)   != null) showHelp();
        if (cfg.getString(CFG.CONFIG) != null) {
            loadAlternateConfig(cfg.getString(CFG.CONFIG));
            resto = parms.parseArgs(args);
        }
        return resto;
	}

	public void setTitles(Integer... datos) {
		for (int idx = 0; idx < datos.length; idx++) {
			titles.add(datos[idx]);
		}
	}
	
	/**
     * Gets the string.
     *
     * @param key
     *            the key
     * @return the string
     */
	public String getString(String key) {
	    return getString(key, null);
	}
	
	/**
     * Gets the string.
     *
     * @param key
     *            the key
     * @param defValue
     *            the def value
     * @return the string
     */
	public String getString(String key, String defValue) {
		String tmp = conf.get(key);
		return (tmp == null) ? defValue : tmp;
	}
	
	/**
     * Sets the parameter.
     *
     * @param key
     *            the key
     * @param value
     *            the value
     */
	public void setParameter(String key, String value) {
		conf.put(key, value);
	}
	
	/****************************************************/
	/* Nombres amigables para capturar las variables    */
	/****************************************************/
	
	public Integer getVerbose() {
	    return getInteger(CFG.VERBOSE, 0);
	}
	
	public String getQueue() {
		return getValue(CFG.JMS_QUEUE);
	}
	public String getOutputQueue() {
		return getValue(CFG.JMS_OUTPUT);
	}
	
	public String getQueueParser() {
		return getValue(CFG.JMS_PARSER);
	}
	public String getQueueTrapper() {
		return getValue(CFG.JMS_TRAPPER);
	}

	public String getQueueCollector() {
		return getValue(CFG.JMS_COLLECTOR);
	}

	public String getInputDir() {
		return setFullDirectory(getValue(CFG.DIR_INPUT));
	}

	public String getOutputDir() {
		return setFullDirectory(getValue(CFG.DIR_OUTPUT));
	}

	public String getTempName() {
        return getValue(CFG.TEMP_NAME);
    }

	private static String setFullDirectory(String tmp) {
		if (tmp == null) return null;
		if (tmp.endsWith(File.separator)) return tmp;
		return tmp + File.separator;
	}
	
	public String getJMSManager() {
		return getValue(CFG.JMS_MANAGER);
	}
	public String getJMSProvider() {
		return getValue(CFG.JMS_TYPE);
	}
	
	public int getJMSWaitInterval() {
		return getInteger(CFG.JMS_WAIT);
	}
	public String getJMSQueue() {
		return getValue(CFG.JMS_TRAPPER);
	}
	public String getJMSOutputQueue() {
		return getValue(CFG.JMS_OUTPUT);
	}
	public String getJMSHostName() {
		return getValue(CFG.JMS_HOST);
	}
	public int getJMSPort() {
		return getInteger(CFG.JMS_PORT);
	}
	
	public char getCollectorProcess() {
		String value = getValue(CFG.COLLECTOR);
		return value.toUpperCase().charAt(0);
	}
	
	/**
     * Gets the integer.
     *
     * @param key
     *            the key
     * @return the integer
     */
	public Integer getInteger(String key) {
	    return getInteger(key, null);
	}
	
	/**
     * Gets the integer.
     *
     * @param key
     *            the key
     * @param defValue
     *            the def value
     * @return the integer
     */
	public Integer getInteger(String key, Integer defValue) {
        String aux = getValue(key);
        if (aux == null) return defValue;
        try {
            return Integer.parseInt(aux);
        }
        catch (Exception e) {
            return defValue;
        }	    
	}
	
	
	/**
     * Gets the boolean.
     *
     * @param key
     *            the key
     * @return the boolean
     */
	public Boolean getBoolean(String key) {
		String aux = getValue(key);
		if (aux == null) return false;
		if (aux.compareToIgnoreCase("true") == 0) return true;
		if (aux.compareToIgnoreCase("yes")  == 0) return true;
		if (aux.compareToIgnoreCase("si")   == 0) return true;
		if (aux.compareToIgnoreCase("1")    == 0) return true;
		if (aux.compareToIgnoreCase("-1")   == 0) return true;
		return false;
	}
	
	private String getValue(String key) {
		  return conf.get(key);
	}

    private void loadDefaultValues() {
	    Propiedades props = new Propiedades();
	    loadParms(props.loadResource(CFG.CFG_DEFAULT));
	}

    private void loadAlternateConfig(String propiedades) {
        Propiedades props = new Propiedades();
        loadParms(props.loadConfiguration(propiedades));        
    }

	private void loadParms(HashMap<String, String> parms) {
	    for (Map.Entry<String, String> entry : parms.entrySet()) {
	        conf.put(entry.getKey(), entry.getValue());
	    }
	}
	
	private void showHelp() {
		for (int idx = 0; idx < titles.size(); idx++) {
			System.out.println(msg.getMsg(titles.get(idx)));
		}
		System.out.println(msg.getMsg(MSG.TITLE_OPTIONS));
		
		for (String[] prm : parms.getParameters()) {
		    if (prm[1].length() > 0) {
		        System.out.print("   -" + prm[1] + " | ");
		    }
		    else {
		        System.out.print("        ");
		    }
			System.out.print("--" + prm[2]);
			for (int idx = prm[2].length(); idx < 16; idx++) System.out.print(' ');
			System.out.println(msg.getMsg(prm[6]));
		}

		System.exit(RC.HELP);
	}

}
