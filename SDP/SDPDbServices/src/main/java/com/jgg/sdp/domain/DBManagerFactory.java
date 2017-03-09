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

import javax.persistence.Persistence;

import org.hibernate.jpa.internal.EntityManagerFactoryImpl;
import org.hibernate.jpa.internal.EntityManagerImpl;

import com.jgg.sdp.core.ctes.MSG;
import com.jgg.sdp.core.ctes.SYS;
import com.jgg.sdp.core.exceptions.SDPException;

public class DBManagerFactory {

	EntityManagerFactoryImpl emf = null;
    EntityManagerImpl em = null; 
    
    private static DBManagerFactory db = null;

    public static EntityManagerImpl getEntityManager() {
        if (db == null) db = new DBManagerFactory();
        return db.em;
    }
    
	private DBManagerFactory() {
	    Properties props = getDBProperties();
		emf = (EntityManagerFactoryImpl) Persistence.createEntityManagerFactory(SYS.PERSISTENCIA, props);
		em = (EntityManagerImpl) emf.createEntityManager();
	}

    /**
     * Busca el ficherode configuracion de la base de datos
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
       if (dbCfg != null) {
           System.out.println("JGG: Encontrado " + dbCfg);
           int pos = dbCfg.lastIndexOf(File.separatorChar);
           if (pos > -1) {
               int len = dbCfg.substring(pos + 1).length();
               if (len == 0) {
                   dbCfg = dbCfg + SYS.DBCONFIG_FILE;
               }
               else {
                   len = dbCfg.lastIndexOf('.');
                   if (len == -1) {
                       dbCfg = dbCfg + File.separator + SYS.DBCONFIG_FILE;
                   }
               }
           }
       }
       else {
           dbCfg = SYS.DBCONFIG_FILE;
           System.out.println("JGG: No lo ha encotrado. USARA " + SYS.DBCONFIG_FILE);
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