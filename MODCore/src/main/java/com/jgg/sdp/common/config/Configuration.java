package com.jgg.sdp.common.config;

import java.util.ArrayList;

public interface Configuration {

	public String  getInputDir();
	public String  getOutputDir();
    public String  getConfigDir();
    public String  getDocDir();
    
	public Integer getMarginLeft();
	public String  getCobolDialect();
	public Integer getVerbose();
	public String  getQueue();
	public String  getOutputQueue();
	public String  getQueueParser();
	public String  getQueueTrapper();
	public String  getQueueCollector();
	public String  getTempName();
	public String  getJMSManager();
	public String  getJMSProvider();
	public int     getJMSWaitInterval();
	public String  getJMSType();	
	public String  getJMSQueue();
	public String  getJMSOutputQueue();
	
	public String  getJMSHostName(String type);
	public String  getJMSHostName();
	public Integer getJMSPort(String type);
	public Integer getJMSPort();	
	public String  getJMSServicePersister();
	public String  getJMSServicePersister(String type);
	
    public String  getMemberName();
    public Integer getParserMode();
    
    public boolean isForcedMode();
    public boolean isIVPMode();

	public String getString(String key);
	public String getString(String key, String defValue);
	
	public Integer getInteger(String key);
	public Integer getInteger(String key, Integer defValue);

	public Boolean getBoolean(String key);
	public Boolean getBoolean(String key, boolean def);

	public ArrayList<String> getList(String key);
	
	public String[] processCommandLine(String[][] prm, String[] args);
	public void addTitle(int type, int code);	
	public void setTitles(Integer... datos);
	public void setParameter(String key, String value);
	public void setParameter(String key, Integer value);
}
