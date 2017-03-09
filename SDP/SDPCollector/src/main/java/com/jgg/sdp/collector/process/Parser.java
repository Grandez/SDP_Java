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

import com.jgg.sdp.core.config.Configuration;
import com.jgg.sdp.core.ctes.CDG;
import com.jgg.sdp.core.ctes.LOG;
import com.jgg.sdp.core.ctes.MSG;
import com.jgg.sdp.core.ctes.SYS;
import com.jgg.sdp.core.exceptions.SDPException;
import com.jgg.sdp.core.jms.*;
import com.jgg.sdp.core.msg.Messages;
import com.jgg.sdp.domain.core.*;
import com.jgg.sdp.domain.module.*;
import com.jgg.sdp.domain.services.core.*;
import com.jgg.sdp.domain.services.log.LOGInputService;
import com.jgg.sdp.domain.services.module.*;
import com.jgg.sdp.domain.services.summary.SUMParrafoService;
import com.jgg.sdp.domain.summary.SUMParrafo;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

public class Parser implements IProcess {

	private JMSQueue jms = null;
	private Document doc = null;
	
	private Configuration cfg = Configuration.getInstance();
	
	private SDPAplicacionService  appService         = new SDPAplicacionService();
	private SDPModuloService      moduloService      = new SDPModuloService();
	
	private MODVersionService     versionService     = new MODVersionService();
	private MODSeccionesService   seccionService     = new MODSeccionesService();
	private MODFicheroService     ficheroService     = new MODFicheroService();
	private MODBloqueService      bloqueService      = new MODBloqueService();
	private MODParrafoService     parrafoService     = new MODParrafoService();	
	private MODDependenciaService dependenciaService = new MODDependenciaService();
	private MODGrafoService       grafoService       = new MODGrafoService();
    private MODBadStmtService     badService         = new MODBadStmtService();	

    private SUMParrafoService      sumParrafoService = new SUMParrafoService();
    
    private SDPRelModuloAppService relModAppService  = new SDPRelModuloAppService(); 

    private LOGInputService log = new LOGInputService();
    
    Long   idModulo  = null;
    Long   idVersion = null;
	
	public int process() {
		initSystems();
		String txt = jms.get();
		while (txt != null) {
			loadXML(txt);
		    processMessage();
			txt = jms.get();
		}	
		endSystems();
		return 0;

	}
	
	private void loadXML(String txt) {
	
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
	    DocumentBuilder builder;  
	    try  
	    {  
	        builder = factory.newDocumentBuilder();  
	        doc = builder.parse(new InputSource(new StringReader(txt)));
	        doc.getDocumentElement().normalize();
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    } 
	}

	private void processMessage() {
/*		
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
		moduloService.beginTrans();
*/	
	}

	private boolean processTable(String tblName, String ser) {
		/*
		byte[] decoded;
		try {
			decoded = Base64.decode(ser);
			ByteArrayInputStream bs= new ByteArrayInputStream(decoded);
			ObjectInputStream is = new ObjectInputStream(bs);
			if (tblName.compareTo("SDPModulo") == 0) {
		    	idModulo = updateModulo((SDPModulo) is.readObject());
			} else if (tblName.compareTo("MODVersion") == 0) {
				MODVersion version = (MODVersion) is.readObject();
				if (existVersion(version)) return true; 
		    	idVersion = updateVersion(version);
            } else if (tblName.compareTo("MODFuente") == 0) {
			   	updateSource((MODFuente) is.readObject());
            } else if (tblName.compareTo("MODResumen") == 0) {
			   	updateResumen((MODResumen) is.readObject());
            } else if (tblName.compareTo("MODFichero") == 0) {
			   	updateFichero((MODFichero) is.readObject());
            } else if (tblName.compareTo("MODBloque") == 0) {
			   	updateBloque((MODBloque) is.readObject());
            } else if (tblName.compareTo("MODParrafo") == 0) {
			   	updateParrafo((MODParrafo) is.readObject());
            } else if (tblName.compareTo("MODSecciones") == 0) {
		        updateSecciones((MODSecciones) is.readObject());
            } else if (tblName.compareTo("MODDependencia") == 0) {
                updateDependencia((MODDependencia) is.readObject());
            } else if (tblName.compareTo("MODGrafo") == 0) {
                updateGrafo((MODGrafo) is.readObject());
            } else if (tblName.compareTo("MODBadStmt") == 0) {
                updateBadStmt((MODBadStmt) is.readObject());
            } else if (tblName.compareTo("MODFuenteError") == 0) {
                updateSourceError((MODFuenteError) is.readObject());
            }    

			is.close();
		} catch (ClassNotFoundException e) {
				e.printStackTrace();
		} catch (IOException e) {
				e.printStackTrace();
		} catch (Base64DecodingException e1) {
			e1.printStackTrace();
		} 
		*/
        return false;
	}
	
	private void initSystems() {
	    Messages.setLogger("COLLECTOR.PARSER");
		jms = JMSFactory.getJMSQueue();
		String qname = cfg.getQueue();
		if (qname == null) {
			qname = cfg.getQueueParser();
			if (qname == null) throw new SDPException(MSG.ERR_NO_QUEUE);
		}
		jms.openInput(qname);
		moduloService.beginTrans();
	}

	private void endSystems() {
		jms.close();
		moduloService.commitTrans();
	}

    private Long updateModulo(SDPModulo modulo) {
    	SDPModulo mod = moduloService.findByName(modulo.getNombre());
    	if (mod == null) {
    		mod = new SDPModulo();
    		mod.setIdModulo(modulo.getIdModulo());
    		mod.setNombre(modulo.getNombre());
    		mod.setIdAppl(relModAppService.getApplication(modulo.getNombre()));
    		mod.setUid(modulo.getUid());
    		mod.setTms(modulo.getTms());
    		mod.setTipo(modulo.getTipo());
    		mod.setEstado(CDG.MODULE_ACTIVE);
    		mod.setIdVersion(0L);

    		actualizaAplicacion(mod.getIdAppl());
    		insertaEstado(mod);
    		log.registra(LOG.MOD_NEW, mod);
    	}

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
    
    private boolean existVersion(MODVersion v) {
    	MODVersion version = versionService.getByFirma(v.getFirma());
    	if (version == null) return false;
    	version.setTms();
    	versionService.update(version);
        log.registra(LOG.MOD_COMP, version);
    	return true;
    }

    private Long updateVersion(MODVersion ver) {
    	ver.setIdModulo(idModulo);
    	versionService.update(ver);
    	moduloService.updateVersion(idModulo, ver.getIdVersion());
    	log.registra(LOG.MOD_VERSION, ver);
    	return ver.getIdVersion();
    }
    
    private void updateSource(MODFuente fte) {
    	MODFuenteService fteService = new MODFuenteService();
    	fte.setIdVersion(idVersion);
    	fteService.update(fte);
    }

    private void updateResumen(MODResumen res) {
    	MODResumenService resService = new MODResumenService();
    	res.setIdVersion(idVersion);
    	resService.update(res);
    }

    private void updateFichero(MODFichero f) {
    	f.setIdVersion(idVersion);
    	ficheroService.update(f);
    }

    private void updateBloque(MODBloque bloque) {
        bloque.setIdVersion(idVersion);
        bloqueService.update(bloque);
    }

    /*
     * Por cada parrafo, se actualiza la tabla sumarizada
     * para evitar tener que buscar si exsite el parrafo
     * y realizar el proceso de rellenar los parrafos no ejecutados
     * Algunos programas pueden no tener parrafos, en ese caso se 
     * recibe un parrafo con el nombre vacio
     */
    private void updateParrafo(MODParrafo parrafo) {
        parrafo.setIdVersion(idVersion);
        //JGG En algun caso hay espacios
        parrafo.setNombre(parrafo.getNombre().trim());
        if (parrafo.getNombre().length() > 0) {
            updateSumParrafo(parrafo);
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
    private void updateSecciones(MODSecciones sect) {
        sect.setIdVersion(idVersion);
        seccionService.update(sect);
    }
    
    private void updateDependencia(MODDependencia dep) {
        dep.setIdVersion(idVersion);
        dependenciaService.update(dep);
    }
    
    private void updateGrafo(MODGrafo grafo) {
        grafo.setIdVersion(idVersion);
        grafoService.update(grafo);
    }

    private void updateBadStmt(MODBadStmt badStmt) {
        badStmt.setIdVersion(idVersion);
        badService.update(badStmt);
    }
    
    private void updateSourceError(MODFuenteError fte) {
        MODFuenteErrorService errService = new MODFuenteErrorService();
        errService.update(fte);        
    }

}
