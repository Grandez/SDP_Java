package com.jgg.sdp.core.config;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.Map.Entry;

import com.jgg.sdp.core.ctes.*;
import com.jgg.sdp.core.exceptions.ParameterException;
import com.jgg.sdp.core.msg.Messages;
import com.jgg.sdp.core.tools.Propiedades;

public class Configuration {

    protected HashMap<String, String> conf   = new HashMap<String, String>();
    protected HashSet<String>         ignore = new HashSet<String>();

	private static Configuration cfg = null;
	
    private ArrayList<Integer> titles = new ArrayList<Integer>();
    private Args     parms = null;
    private Messages msg   = Messages.getInstance();
    
	protected Configuration() {
//		titles.add(200);
		loadConfigResource(CFG.CFG_DEFAULT);
		setDefaultConfigDir();
        loadFilesToIgnore();
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
		String[] resto = null;
		parms = new Args(prm);

		try {
           resto = parms.parseArgs(args);
		}
        catch (ParameterException e) {
			msg.exception(e);
			System.exit(RC.BAD_PRM);
		}

		HashMap<String, String> cmdLine = parms.getArgs();
 
        for(Entry<String, String> parm : cmdLine.entrySet()) {
        	conf.put(parm.getKey(), parm.getValue());
        }
			
		if (cmdLine.containsKey(CFG.PROPS)) {
			loadConfigFile(mountConfigFile(cmdLine.get(CFG.PROPS)));
		}
		else {
			loadConfigFile(mountConfigFile(CFG.CFG_SDP));
		}
		
        for(Entry<String, String> parm : cmdLine.entrySet()) {
        	conf.put(parm.getKey(), parm.getValue());
        }
        
        if (cfg.getBoolean(CFG.DEF))  showDefinition();        
        if (cfg.getBoolean(CFG.HELP)) showHelp();
        
        return resto;
	}

	public boolean isIgnored(String name) {
		return ignore.contains(name);
	}
	
	public void setTitles(Integer... datos) {
		for (int idx = 0; idx < datos.length; idx++) {
			titles.add(datos[idx]);
		}
	}
	
	public String getString(String key) {
	    return getString(key, null);
	}
	
	public String getString(String key, String defValue) {
		String tmp = conf.get(key);
		return (tmp == null) ? defValue : tmp;
	}
	
	public void setParameter(String key, String value) {
		conf.put(key, value);
	}
	
	/****************************************************/
	/* Nombres amigables para capturar las variables    */
	/****************************************************/

	public String  getInputDir()        { return setFullDirectory(getValue(CFG.DIR_INPUT));  }
	public String  getOutputDir()       { return setFullDirectory(getValue(CFG.DIR_OUTPUT)); }
    public String  getConfigDir()       { return setFullDirectory(getValue(CFG.DIR_CONFIG)); }
    public String  getDocDir()          { return setFullDirectory(getValue(CFG.DIR_DOCS));   }
    
	public Integer getMarginLeft()      { return getInteger(CFG.MARGIN_LEFT); }
	public String  getCobolDialect()    { return getString(CFG.COBOL_LANG);   }
	public Integer getVerbose()         { return getInteger(CFG.VERBOSE, 0);  }
	public String  getQueue()           { return getValue(CFG.JMS_QUEUE);     }
	public String  getOutputQueue()     { return getValue(CFG.JMS_OUTPUT); 	  }
	public String  getQueueParser()     { return getValue(CFG.JMS_PARSER);    }
	public String  getQueueTrapper()    { return getValue(CFG.JMS_TRAPPER);   }
	public String  getQueueCollector()  { return getValue(CFG.JMS_COLLECTOR); }
	public String  getTempName()        { return getValue(CFG.TEMP_NAME);     }
	public String  getJMSManager()      { return getValue(CFG.JMS_MANAGER);   }
	public String  getJMSProvider()     { return getValue(CFG.JMS_TYPE);      }
	public int     getJMSWaitInterval() { return getInteger(CFG.JMS_WAIT);    }
	public String  getJMSQueue()        { return getValue(CFG.JMS_TRAPPER);   }
	public String  getJMSOutputQueue()  { return getValue(CFG.JMS_OUTPUT);    }
	public String  getJMSHostName()     { return getValue(CFG.JMS_HOST);      }
	public int     getJMSPort()         { return getInteger(CFG.JMS_PORT);    }
    public String  getMemberName()      { return getValue(CFG.CURR_MODULE);   }
	
	private static String setFullDirectory(String tmp) {
		if (tmp == null) return null;
		if (tmp.endsWith(File.separator)) return tmp;
		return tmp + File.separator;
	}
	
	public char getCollectorProcess() {
		String value = getValue(CFG.COLLECTOR);
		return value.toUpperCase().charAt(0);
	}
	
	public Integer getInteger(String key) {
	    return getInteger(key, null);
	}
	
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
	
	public Boolean getBoolean(String key) {
		return getBoolean(key, false);
	}
	
	public Boolean getBoolean(String key, boolean def) {
		String aux = getValue(key);
		if (aux == null) return def;
		if (aux.compareToIgnoreCase("true") == 0) return true;
		if (aux.compareToIgnoreCase("yes")  == 0) return true;
		if (aux.compareToIgnoreCase("si")   == 0) return true;
		if (aux.compareToIgnoreCase("1")    == 0) return true;
		if (aux.compareToIgnoreCase("-1")   == 0) return true;
		return false;
	}

	/*
	 * Devuelve una lista de valores para una clave clave.n
	 * Donde n es un numero
	 * Para cuando haya un cierto numero de errores
	 */
	public ArrayList<String> getList(String key) {
		ArrayList<String> lista = new ArrayList<String>();
		String value = conf.get(key);
		if (value != null) lista.add(value);
		
		int idx = 0;
		int errs = 0;
		while (errs < 5) {
			value = conf.get(key + "." + idx);
			if (value == null) {
				errs++;
			}
			else {
				lista.add(value);
			}
			idx++;
		}
		return lista;
	}
	
	private String getValue(String key) {
		  return conf.get(key);
	}

	private void loadConfigFile(String name) {
	    Propiedades props = new Propiedades();
	    loadParms(props.loadConfigFile(name));		
	}

	private void loadConfigResource(String name) {
	    Propiedades props = new Propiedades();
	    loadParms(props.loadResource(name));		
	}

	private void loadParms(HashMap<String, String> parms) {
	    for (Map.Entry<String, String> entry : parms.entrySet()) {
	        conf.put(entry.getKey(), entry.getValue());
	    }
	}
	
	private String mountConfigFile(String fileName) {
		String dirBase = cfg.getConfigDir();
		return dirBase + fileName;
	}

	private void setDefaultConfigDir() {
		int pos = 0;
		
		URL url = Configuration.class.getResource("Configuration.class");
		
		// Caso file: Se esta ejecutando en eclipse. Utilizar la configuracion
		if (url.getProtocol().compareToIgnoreCase("file") == 0) return;

		// No se puede utilizar file.separator. Java pone /		
		String path = Configuration.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		pos = path.lastIndexOf('/');
		path = path.substring(0, pos);
		pos = path.lastIndexOf('/');
		path = path.substring(0, pos);
		path = path + File.separator + conf.get(CFG.DIR_CONFIG_REL);
		conf.put(CFG.DIR_CONFIG, path);
	}

    private void loadFilesToIgnore() {
    	String f = conf.get(CFG.FILE_IGNORE);
    	String d = getConfigDir();
    	
    	if (f == null) return;
    	
		FileInputStream fis = null;
		try {
		   fis = new FileInputStream(d + f);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
 
	        String line = null;
            while ((line = br.readLine()) != null) {
			   line = line.trim();
			   if (line.length() > 0) {
				   if (line.charAt(0) != '#') {
					   String toks[] = line.split("[ ;\t]");
					   ignore.add(toks[0]);  
				   }
			   }
		    }
	        br.close();	
		}
		catch (Exception e) { // No existe el fichero, se ignora el error
			return;
		}
	}

    private void showHelp() {
		String align = "                        ";
		
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
		    if (prm[2].length() > 0) {
			    System.out.print("--" + prm[2]);
		    }    
			for (int idx = prm[2].length(); idx < 16; idx++) System.out.print(' ');
			String txts[] = prm[6].split(";");
			System.out.println(msg.getMsg(txts[0]));
			for (int idx = 1 ; idx < txts.length; idx++) {
				System.out.println(align + msg.getMsg(txts[idx]));
			}
		}

		System.exit(RC.HELP);
	}

	private void showDefinition() {
		ArrayList<String> lineas = new ArrayList<String>();
		StringBuilder linea = new StringBuilder();

		for (int idx = 0; idx < titles.size(); idx++) {
			System.out.println(msg.getMsg(titles.get(idx)));
		}
		System.out.println(msg.getMsg(MSG.TITLE_DEFINITION));

	    for (Map.Entry<String, String> entry : conf.entrySet()) {
	    	linea.setLength(0);
	    	linea.append(entry.getKey());
	    	while (linea.length() < 30) linea.append(" ");
	    	linea.append("= ");
	    	linea.append(entry.getValue());
	        lineas.add(linea.toString());
	    }

	    Collections.sort(lineas);

	    for (int idx = 0; idx < lineas.size(); idx++) {
	    	System.out.println(lineas.get(idx));
	    }
		System.exit(RC.HELP);
	}
	
}
