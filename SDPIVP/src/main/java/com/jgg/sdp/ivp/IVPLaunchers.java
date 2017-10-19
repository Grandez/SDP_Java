package com.jgg.sdp.ivp;

import java.io.*;

import com.jgg.sdp.common.config.*;
import com.jgg.sdp.common.ctes.CFG;
import com.jgg.sdp.domain.services.CommonService;
import com.jgg.sdp.domain.services.cfg.DBConfiguration;
import com.jgg.sdp.ivp.items.*;
import com.jgg.sdp.ivp.loaders.XMLIVPLoader;
import com.jgg.sdp.printer.Printer;

import static com.jgg.sdp.ivp.items.IVPAction.*;

public class IVPLaunchers {

    private Configuration cfg = DBConfiguration.getInstance();

    private CommonService dbService = new CommonService();
    
    private Printer printer = new Printer();

    private boolean envChanged = false;
    private String  oldWD;
    
	public int setEnvironment(int level) {
		int rc = 0;
		printer.lineBeg("Estableciendo entorno para bloque " + level);
		File module = getLoaderName(level);
		if (module == null) {
			printer.lineEnd("NO");
			return -1;
		}
		try {
		   rc = processLoader(module);
		}
		catch(Exception e) {
			rc = 99;
		}
		String end = ( rc == 0) ? "OK" : "KO"; 
		printer.lineEnd(end);
		return rc;
	}
	
	private File getLoaderName(int level) {
		String dir = cfg.getConfigDir();
		String base = cfg.getString(CFG.IVP_LOADER);
		String fileName = "P:\\SDP\\config\\IVPLoader_" + level + ".xml";
		File f = new File(fileName);
		return (f.exists()) ? f : null;
	}
	
	private int processLoader(File loader) throws Exception {
		XMLIVPLoader xmlLoader = new XMLIVPLoader(loader);
		setBlockEnvironment(xmlLoader.getConfig());
		
		dbService.beginTrans();
		for (IVPAction action : xmlLoader.getActions()) {
			switch (action.getType()) {
			   case SQL_STMT:   executeSQL(action.getValue());       break;
			   case SQL_SCRIPT:	processSQLScript(action.getValue()); break;
			}
		}
		dbService.commitTrans();
		return 0;
	}
	
	private void executeSQL(String sqlStmt) {
		String tmp = sqlStmt.trim();
		if (tmp.length() > 0) dbService.execute(sqlStmt);	
	}
	

	private void processSQLScript(String scriptName) {
		StringBuilder fileName = new StringBuilder(System.getProperty("user.dir"));
		StringBuilder stmt = new StringBuilder();
		String tmp = "";
		BufferedReader br = null;
		FileReader fr = null;
		String line;
		
		fileName.append(File.separatorChar);
		fileName.append(scriptName);

		try {
			fr = new FileReader(fileName.toString());
			br = new BufferedReader(fr);
			while ((line = br.readLine()) != null) {
				line = line.trim();
				System.out.println("-" + line + "-");
				if (prepareSQLStatement(line, stmt)) {
					int pos = stmt.indexOf( ";"); 
					if ( pos == -1) {
						executeSQL(stmt.toString());
					}
					else {
						tmp = stmt.substring(pos + 1);
						executeSQL(stmt.substring(0, pos).toString());
					}
					stmt = new StringBuilder(tmp);
				}
			}		
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) br.close();
				if (fr != null)	fr.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}

		}

	}

    private boolean prepareSQLStatement(String line, StringBuilder stmt) {
		String cad = line.trim();
		if (cad.length() == 0)        return false;
		if (cad.startsWith("--"))     return false;
		if (cad.startsWith("USE"))    return false;
		if (cad.startsWith("COMMIT")) return false;
		
		stmt.append(cad);
		return (cad.indexOf(";") == -1) ? true : false;
    }
    
	private void setBlockEnvironment(IVPConfig config) {
		envChanged = false;
		if (config == null) return;
		if (config.getWorkDir() == null) return;
		oldWD = System.getProperty("user.dir");
		System.setProperty("user.dir", config.getWorkDir());
		envChanged = true;
	}
	
	private void resetBlockEnvironment() {
		if (!envChanged) return;
		System.setProperty("user.dir", oldWD);
	}
}

