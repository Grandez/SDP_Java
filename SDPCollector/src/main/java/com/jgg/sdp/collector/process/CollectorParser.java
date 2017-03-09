/*
 * 
 */
package com.jgg.sdp.collector.process;


import javax.xml.parsers.*;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StringReader;
import java.util.HashSet;

import com.jgg.sdp.domain.core.*;
import com.jgg.sdp.domain.module.*;
import com.jgg.sdp.domain.services.CommonService;
import com.jgg.sdp.domain.services.core.*;
import com.jgg.sdp.domain.services.log.LOGInputService;
import com.jgg.sdp.domain.services.module.*;
import com.jgg.sdp.domain.services.summary.SUMParrafoService;
import com.jgg.sdp.domain.summary.SUMParrafo;
import com.jgg.sdp.core.ctes.*;

// import com.jgg.sdp.core.jms.*;
import com.jgg.sdp.core.msg.Messages;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

public class CollectorParser implements IProcess {

//	private JMSQueue jms = null;
	private Document doc = null;
	
//	private Configuration cfg = Configuration.getInstance();
	
	private SDPAplicacionService  appService         = new SDPAplicacionService();
	private SDPModuloService      moduloService      = new SDPModuloService();
	private SDPFilesService       fileService        = new SDPFilesService();
	
	private MODVersionService     versionService     = new MODVersionService();
	private MODParrafoService     parrafoService     = new MODParrafoService();	
	private MODCallService        callService        = new MODCallService();
	
    private SUMParrafoService      sumParrafoService = new SUMParrafoService();
    
    private SDPRelModuloAppService relModAppService  = new SDPRelModuloAppService(); 

    private SDPDependenciaService depService = new SDPDependenciaService();
    
    private CommonService     commonService     = new CommonService();
    
    private LOGInputService log = new LOGInputService();
    
    Long   idModulo  = null;
    Long   idVersion = null;
    
    public CollectorParser() {
           Messages.setLogger("COLLECTOR.PARSER");
    }
	
	public int process() {
//		initJMS();
//		String txt = jms.get();
		String txt = null;
		while (txt != null) {
			loadXML(txt);
            processMessage();
//			txt = jms.get();
		}	
//		endJMS();
		return 0;
	}
	
	public int processXMLData(String txt) {
		loadXML(txt);
        processMessage();
        return 0;   		
	}
	
	private void loadXML(String txt) {
	
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
	    DocumentBuilder builder;  
	    try  {  
	        builder = factory.newDocumentBuilder();  
	        doc = builder.parse(new InputSource(new StringReader(txt)));
	        doc.getDocumentElement().normalize();
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    } 
	}

	private void processMessage() {
		
		moduloService.beginTrans();
		// Coger el nodo raiz, solo hay uno
		NodeList rootNodes = doc.getChildNodes();
		NodeList nodes = rootNodes.item(0).getChildNodes();

		for (int idx = 0 ; idx < nodes.getLength() ; idx++) {
			Node node = nodes.item(idx);
			
			String data = node.getTextContent();
			// Si existe esa version con la misma firma, se actualiza el timestamp
			// y se dejan los datos como estan
			if (processTable(node.getNodeName(), data) == true) return;
		}
		moduloService.commitTrans();
//		moduloService.beginTrans();
	}

	private boolean processTable(String tblName, String ser) {
//		System.out.println("Procesando tabla " + tblName);
		byte[] decoded;
		try {
			decoded = Base64.decode(ser);
			ByteArrayInputStream bs= new ByteArrayInputStream(decoded);
			ObjectInputStream is = new ObjectInputStream(bs);
			if (tblName.compareTo("SDPModulo") == 0) {
		    	idModulo = updateModulo((SDPModulo) is.readObject());
			} else if (tblName.compareTo("MODVersion") == 0) {
		    	idVersion = updateVersion((MODVersion) is.readObject());
			} else if (tblName.compareTo("MODCall") == 0) {
			   	updateCall((MODCall) is.readObject());
			} else if (tblName.compareTo("MODParrafo") == 0) {
			   	updateParrafo((MODParrafo) is.readObject());
			} else if (tblName.compareTo("SDPFile") == 0) {
				updateFile((SDPFile) is.readObject());
			} else if (tblName.compareTo("SDPFuente") == 0) {
			   	// No hacer nada
			} else {
            	commonService.update(is.readObject());
            }

			is.close();
		} catch (ClassNotFoundException e) {
				e.printStackTrace();
		} catch (IOException e) {
				e.printStackTrace();
		} catch (Base64DecodingException e1) {
			e1.printStackTrace();
		} 
		
        return false;
	}
/*	
	private void initJMS() {
//	    Messages.setLogger("COLLECTOR.PARSER");
		jms = JMSFactory.getJMSQueue();
		String qname = cfg.getQueue();
		if (qname == null) {
			qname = cfg.getQueueParser();
			if (qname == null) throw new SDPException(MSG.ERR_NO_QUEUE);
		}
		jms.openInput(qname);
//		moduloService.beginTrans();
	}

	private void endJMS() {
		jms.close();
//		moduloService.commitTrans();
	}
*/
    private Long updateModulo(SDPModulo modulo) {
    	SDPModulo mod = moduloService.findByNameAndType(modulo.getNombre(), modulo.getTipo());
    	if (mod == null) {
    		mod = new SDPModulo();
    		mod.setIdModulo(modulo.getIdModulo());
    		mod.setNombre(modulo.getNombre());
    		mod.setIdAppl(relModAppService.getApplication(modulo.getNombre()));
    		mod.setTipo(modulo.getTipo());
    		mod.setActivo(CDG.MODULE_ACTIVE);
    		mod.setVersiones(1);
    		actualizaAplicacion(mod.getIdAppl());
    		insertaEstado(mod);
    		log.registra(LOG.MOD_NEW, mod);
    	}
    	
		mod.setUid(modulo.getUid());    	
		mod.setTms(modulo.getTms());
		mod.setIdVersion(modulo.getIdVersion());
		mod.setVersiones(mod.getVersiones() + 1);
		
    	moduloService.update(mod);
    	return mod.getIdModulo();
    }

    /*
     * JGG 
     * A partir de cierto momento (C3P0 o asi) en lugar de hacer un UPDATE
     * hace un INSERT con lo que genera duplicados
     * Se cambia por una sentencia UPDATE
     */
    private void actualizaAplicacion(Long idAppl) {
        SDPAplicacion app = appService.findById(idAppl);
        if (app != null) {
            appService.updateVolumen(app.getAplicacion());
        }
    }

    private void insertaEstado(SDPModulo mod) {
        SDPStatus status = new SDPStatus();
        status.setIdModulo(mod.getIdModulo());
        status.setIdVersion(0L);
        status.setNombre(mod.getNombre());
        status.setUid(SYS.DEF_USER);
        status.setEstado(CDG.ST_PENDING);
        status.setTms(System.currentTimeMillis());
    }
    
    private Long updateVersion(MODVersion ver) {
    	ver.setIdModulo(idModulo);
    	versionService.update(ver);
    	moduloService.updateVersion(idModulo, ver.getIdVersion());
    	log.registra(LOG.MOD_VERSION, ver);
    	versionService.flush();
    	return ver.getIdVersion();
    }

    /**
     * Si la rutina no esta identificada (Es una variable) se busca si existe
     * su traduccion en la tabla de dependencias
     * Si no existe, se guarda la variable 
     * 
     * @param call
     */
    
    private void updateCall(MODCall call) {
    	boolean exist = false;
    	MODCall manual = null;
       if (call.getEstado() != CDG.DEP_ST_VARIABLE) {
    	   commonService.update(call);
    	   return;
       }
       
       // Al buscar la rutinas manuales
       // Puede haber varias lineas para una misma variable
       // Mientras que el arbol contiene el numero de variables
       
       HashSet<String> vars = new HashSet<String>();
       
       // No puede ser null
       MODVersion ver = versionService.getByVersion(call.getIdVersion());
       
       for (SDPDependencia dep : depService.getDependencias(ver.getNombre(), call.getModulo())) {
    	   exist = true;
    	   if (dep.getCalled() != null) {
    		   manual = callService.getModule(ver.getIdVersion(), dep.getCalled());
    		   if (manual == null) {
    		       manual = new MODCall(call, dep.getCalled());
    		       manual.setEstado(CDG.DEP_ST_MANUAL);
    		   }
    		   else {
    			   manual.setRefs(manual.getRefs() + call.getRefs());
    			   manual.setMetodo(manual.getMetodo() | call.getMetodo());
    			   manual.setModo(manual.getModo() | call.getModo());
    			   manual.setEstado(manual.getEstado() | CDG.DEP_ST_MANUAL);
    		   }
    		   commonService.update(manual);
    		   vars.add(dep.getVariable());
    	   }
       }
       
	   ver.setArbol(ver.getArbol() - vars.size());
       if (exist) {
    	   commonService.update(ver);
       }
       else {
    	   commonService.update(call);
       }
    }
    
    /*
     * Por cada parrafo, se actualiza la tabla sumarizada
     * para evitar tener que buscar si existe el parrafo
     * y realizar el proceso de rellenar los parrafos no ejecutados
     * Algunos programas pueden no tener parrafos, en ese caso se 
     * recibe un parrafo con el nombre vacio
     */
    private void updateParrafo(MODParrafo parrafo) {
        //JGG En algun caso hay espacios
        parrafo.setNombre(parrafo.getNombre().trim());
//JGG        System.out.println(parrafo.getNombre());
        
        if (parrafo.getNombre().length() > 0) {
//JGG            updateSumParrafo(parrafo);
            parrafoService.update(parrafo);
        }
       
    }

    private void updateSumParrafo(MODParrafo parrafo) {
        SUMParrafo sum = new SUMParrafo();
        sum.setIdVersion(parrafo.getIdVersion());
        sum.setNombre(parrafo.getNombre());
        sum.setLinea(parrafo.getLinea());
        sumParrafoService.update(sum);
    }
    
    private void updateFile(SDPFile file) {
    	fileService.deleteByNameAndType(file.getArchivo(), file.getTipo());
    	fileService.update(file);
    }

}
