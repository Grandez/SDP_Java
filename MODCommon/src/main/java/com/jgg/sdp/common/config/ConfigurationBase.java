package com.jgg.sdp.common.config;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import com.jgg.sdp.common.ctes.*;
import com.jgg.sdp.common.exceptions.ParameterException;
import com.jgg.sdp.printer.color.*;
import com.jgg.sdp.printer.color.api.Ansi.*;
import com.jgg.sdp.tools.Propiedades;

public class ConfigurationBase implements Configuration {

    protected HashMap<String, String> conf   = new HashMap<String, String>();
    private Args     parms = null;
 
    private Messages msg   = Messages.getInstance();

    private ArrayList<Integer> titlesCode = new ArrayList<Integer>();
    private ArrayList<Integer> titlesType = new ArrayList<Integer>();   
    
    private ColoredPrinter cp = null;
	
    protected static Configuration cfg = null;

    public static Configuration getInstance() {
    	return cfg;
    }
    
	/****************************************************/
	/* Nombres amigables para capturar las variables    */
	/****************************************************/

	public String  getInputDir()        { return setFullDirectory(getValue(CFG.DIR_INPUT));  }
	public String  getOutputDir()       { return setFullDirectory(getValue(CFG.DIR_OUTPUT)); }
    public String  getConfigDir()       { return setFullDirectory(getValue(CFG.DIR_CONFIG)); }
    public String  getDocDir()          { return setFullDirectory(getValue(CFG.DIR_DOCS));   }
    
	public Integer getMarginLeft()      { return getInteger(CFG.MARGIN_LEFT);  }
	public String  getCobolDialect()    { return getString(CFG.COBOL_LANG);    }
	public Integer getVerbose()         { return getInteger(CFG.VERBOSE, 0);   }
	public String  getQueue()           { return getValue(CFG.JMS_QUEUE);      }
	public String  getOutputQueue()     { return getValue(CFG.JMS_OUTPUT); 	   }
	public String  getQueueParser()     { return getValue(CFG.JMS_PARSER);     }
	public String  getQueueTrapper()    { return getValue(CFG.JMS_TRAPPER);    }
	public String  getQueueCollector()  { return getValue(CFG.JMS_COLLECTOR);  }
	public String  getTempName()        { return getValue(CFG.TEMP_NAME);      }
	public String  getJMSType()         { return getValue(CFG.JMS_TYPE);       }
	public String  getJMSManager()      { return getValue(CFG.JMS_MANAGER);    }
	public String  getJMSProvider()     { return getValue(CFG.JMS_TYPE);       }
	public int     getJMSWaitInterval() { return getInteger(CFG.JMS_WAIT);     }
	public String  getJMSQueue()        { return getValue(CFG.JMS_TRAPPER);    }
	public String  getJMSOutputQueue()  { return getValue(CFG.JMS_OUTPUT);     }

    public String  getMemberName()      { return getValue(CFG.CURR_MODULE);    }
    public Integer getParserMode()      { return getInteger(CFG.PARSER_MODE);  }
    public Integer setParserMode()      { return getInteger(CFG.PARSER_MODE);  }
    
    public boolean isForcedMode()       { return getBoolean(CFG.PARSER_FORCE); }
    public boolean isIVPMode()          { return getBoolean(CFG.IVP_PROCESS);  }
    
//	public boolean isIgnored(String name) { return ignore.contains(name);        }
	

	public String  getJMSHostName()         { return getValue(CFG.JMS_HOST);      }	
	public Integer getJMSPort()             { return getInteger(CFG.JMS_PORT);    }
	public String  getJMSServicePersister() { return getValue(CFG.JMS_PERSISTER); } 

	public String  getJMSHostName(String type) {         
	       String sz = getValue(mountKey(CFG.JMS_HOST, type, 1));      
	       if (sz != null) return sz;
	       return getJMSHostName();
	}
	
	public Integer getJMSPort(String type) {             
	       Integer p = getInteger(mountKey(CFG.JMS_PORT, type, 1));
	       if (p != null) return p;
	       return getJMSPort();
	}
	
	public String  getJMSServicePersister(String type) { 
		String sz = getValue(mountKey(CFG.JMS_PERSISTER, type, 1));
	       if (sz != null) return sz;
	       return getJMSServicePersister();
	}
    
	private static String setFullDirectory(String tmp) {
		if (tmp == null) return null;
		if (tmp.endsWith(File.separator)) return tmp;
		return tmp + File.separator;
	}
	

    /**
     * Getters and Setters
     */
    
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

	public void setParameter(String key, Integer value) {
		conf.put(key, value.toString());
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
	protected void setValue(String key, String value) {
		  conf.put(key, value);
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
        
        if (conf.containsKey(CFG.DEF))  showDefinition();        
        if (conf.containsKey(CFG.HELP)) showHelp();
        
        return resto;
	}

	public void addTitle(int type, int code) {
		titlesType.add(type);
		titlesCode.add(code);
	}
	
	public void setTitles(Integer... datos) {
		for (int idx = 0; idx < datos.length; idx++) {
			titlesCode.add(datos[idx]);
			titlesType.add(CDG.TXT_NORMAL);
		}
	}

	public void setAlternateConfiguration(HashMap<String, String> props) {
		conf=props;
	}
	
	private void showDefinition() {
		ArrayList<String> lineas = new ArrayList<String>();
		StringBuilder linea = new StringBuilder();

		for (int idx = 0; idx < titlesCode.size(); idx++) {
			System.out.println(msg.getMsg(titlesCode.get(idx)));
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

	
    private void showHelp() {
    	boolean title = false;
		String align = "\t\t";
		
		cp = new ColoredPrinter.Builder()
                .foreground(FColor.WHITE)
                .background(BColor.BLACK)   //setting format
                .build();

		for (int idx = 0; idx < titlesCode.size(); idx++) {
			if (title && titlesType.get(idx) != CDG.TXT_TITLE) {
				   title = false;
				   printNormal(0);				
			}
			
			switch (titlesType.get(idx)) {
			   case CDG.TXT_TITLE: 
				   if (title = false) printNormal(0);
				    title = true;
			        printTitle(titlesCode.get(idx));
			        break;
			   case CDG.TXT_USE:   
				    printEmphasize(titlesCode.get(idx));
				    break;
			   case CDG.TXT_DESC:   
				    printDescription(titlesCode.get(idx));
				    break;
			   case CDG.TXT_SKIP:
				    printNormal(0);
				    break;
			   case CDG.TXT_ITEM:   
				   printItem(titlesCode.get(idx), true);
                   break;
			   case CDG.TXT_NEXT:   
				   printItem(titlesCode.get(idx), false);
                   break;
			   case CDG.TXT_NORMAL:   
				   printNormal(titlesCode.get(idx));
                   break;
			}
		}

		List<String[]> prms = parms.getParameters();
		
		if (prms.size() > 0) {
			printNormal(0);
			printTitle(MSG.TITLE_OPTIONS);
			printNormal(0);
            for (String[] prm : prms) {
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
		}
		System.exit(RC.HELP);
	}

    private void printTitle(int code) {
    	String padding = "                              ";
    	String txt = "";
    	if (code != 0) txt = msg.getMsg(code);
    	int pad = (60 - txt.length()) / 2;
    	txt = padding.substring(0, pad) + txt;
		cp.println(txt, Attribute.BOLD, FColor.BLUE, BColor.BLACK);
    }
    private void printEmphasize(int code) {
		cp.println(msg.getMsg(code), Attribute.BOLD, FColor.WHITE, BColor.BLACK);
    }

    private void printDescription(int code) {
		cp.println(msg.getMsg(code));
    }
    
    private void printNormal(int code) {
		cp.println(msg.getMsg(code));
    }
    private void printItem(int code, boolean head) {
    	String p = "- ";
    	if (!head) p = "  ";
		cp.println(p + msg.getMsg(code));
    }
    
	private String mountConfigFile(String fileName) {
		String dirBase = getConfigDir();
		return dirBase + fileName;
	}

	private void loadConfigFile(String name) {
	    Propiedades props = new Propiedades();
	    loadParms(props.loadConfigFile(name));		
	}

	protected void loadParms(HashMap<String, String> parms) {
	    for (Map.Entry<String, String> entry : parms.entrySet()) {
	        conf.put(entry.getKey(), entry.getValue());
	    }
	}

	protected void setConfiguration(Configuration cfg) {
		this.cfg = cfg;
	}

	String mountKey(String oldKey, String type, int pos) {
		int n = 0;
		int from = 0;
		int dot = -1;
		while (n < pos) {
			dot = oldKey.indexOf('.', from);
			from = dot + 1;
			n++;
		}
		String left = oldKey.substring(0, dot);
		String right = oldKey.substring(dot);
		return left + "." + type + right;
	}
}
