/**
 * Factoria de conexiones al SGDB
 * <br>
 * Se encarga de establecer la conexion y obtener el 
 * EntityManager para a ejecucion
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.domain;

import java.io.*;
import java.util.Properties;

import javax.persistence.*;

import org.hibernate.jpa.internal.*;

import com.jgg.sdp.core.config.Configuration;
import com.jgg.sdp.core.ctes.*;
import com.jgg.sdp.core.exceptions.SDPException;

public class DBManagerFactory {

	private EntityManagerFactoryImpl emf = null;
    private EntityManagerImpl em = null; 
    private EntityTransaction tx = null;
    
    private static DBManagerFactory db = null;
    
    private Configuration cfg = Configuration.getInstance();


    public static DBManagerFactory getInstance() {
    	if (db == null) db = new DBManagerFactory();
    	return db;
    }
    
    public EntityManagerImpl getEntityManager() {
        return em;
    }

    public EntityTransaction getTransaction() {
        if (tx == null) tx = em.getTransaction();
        return tx;
    }
    
	private DBManagerFactory() {
//	    Properties props = getDBProperties();
//		emf = (EntityManagerFactoryImpl) Persistence.createEntityManagerFactory(SYS.PERSISTENCIA, props);
	    emf = (EntityManagerFactoryImpl) Persistence.createEntityManagerFactory(SYS.PERSISTENCIA);
		em = (EntityManagerImpl) emf.createEntityManager();
	}

    /**
     * Busca el fichero de configuracion de la base de datos
     * 1.- Busca en el entorno la variable SDP_DBCONFIG
     * 2.- Si existe mira si es directorio o fichero
     *     verificando que es de la forma /dir/fichero.extension
     *     Busca el ultimo /
     *     si la longitud de los restante es cero
     *     es de la forma /dir/dir/ y se le añade el fichero por defecto
     * @return
     */
	
	private Properties getDBProperties() {
	   Properties props = new Properties();
       InputStream in = null;
       

       System.out.println("JGG: BUscando " + SYS.DBCONFIG_ENV);
       String dbCfg = System.getenv(SYS.DBCONFIG_ENV);
       dbCfg = cfg.getString(CFG.DIR_CONFIG);
       
       if (dbCfg != null) {
           System.out.println("JGG: Encontrado " + dbCfg);
           int pos = dbCfg.lastIndexOf(File.separatorChar);
           if (pos == -1) dbCfg = dbCfg +  File.separatorChar;
           dbCfg = dbCfg + "hibernate.properties";
       }
       try {
           in = new FileInputStream(dbCfg);
           props.load(in);
           in.close();
       } catch (Exception e) {
           throw new SDPException(MSG.EXCEPTION_DBCONFIG, 
                                  dbCfg, 
                                  SYS.DBCONFIG_ENV, 
                                  SYS.DBCONFIG_FILE);            
       }
       return props;

   }
}