/**
 * Se encarga de persistir toda la informacion obtenida del parser
 * 
 * La envia al servidor en forma de un documento XML con la informacion
 * de las tablas serializada
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.parser;

import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jgg.sdp.core.config.Configuration;
import com.jgg.sdp.core.ctes.CDG;
import com.jgg.sdp.core.jms.*;
import com.jgg.sdp.core.tools.*;
import com.jgg.sdp.domain.core.*;
import com.jgg.sdp.domain.module.*;
import com.jgg.sdp.module.base.*;
import com.jgg.sdp.module.items.*;
import com.jgg.sdp.module.items.Source;
import com.sun.org.apache.xml.internal.security.utils.Base64;

public class Serializer {

    XMLTable xml = new XMLTable();	
    JMSQueue jms = null;
    Configuration cfg = Configuration.getInstance();

    Document doc = null;
	Element  root = null;
    
    Module module    = null;
    Long   id        = null;
    Long   idVersion = null;
    
	/**
     * Recibe el objeto Module
     * Serializa y envia cada uno de sus componentes
     *
     * @param module El objet Module
     */
	public void storeModuleInfo(Module module) {
		openJMS();
		createXML();
		
		this.module = module;
    	id = updateModule();
        idVersion = updateVersion();
   	    updateSource();
   	    updateResumen();
  	    updateFiles();
   	    updateBloques();
   	    updateParrafos();
   	    updateSections();
        updateDependencias();
        updateGrafo();
        updateBadStatements();
        sendXML();
   	    
        closeJMS();
	}

    /**
     * Si el analisis ha resultado erroneo
     * El modulo fuente es enviado al servidor para su posterior analisis
     *
     * @param module El modulo erroneo
     */
    public void storeModuleErr(Module module) {
        this.module = module;
        openJMS();
        createXML();
        updateSourceErr();        
        sendXML();        
        closeJMS();
    }
	
    private Long updateModule() {
        SDPModulo modulo = new SDPModulo();
       	modulo.setIdAppl(0L);
       	modulo.setNombre(module.getName());
       	modulo.setUid(System.getProperty("user.name"));
       	modulo.setTms(new Timestamp(System.currentTimeMillis()));
       	modulo.setTipo(module.getType());
       	modulo.setEstado(1);
       	modulo.setIdVersion(0L);
    	generate(modulo);
        return modulo.getIdModulo();
    }

    private Long updateVersion() {
        Summary summary = module.getSummary();
    	
    	MODVersion version = new MODVersion(id);

    	version.setFirma(module.getIdModule());
        version.setNombre(module.getName());
    	version.setTipo(module.getType());

        version.setFichero(summary.getFiles() ? 1 : 0);
    	
    	version.setDesc(module.getDescription());
    	version.setUid(System.getProperty("user.name"));
    	
    	version.setTms();

    	generate(version);
        return version.getIdVersion();
    }
    
    private void updateSource() {
    	MODFuente fuente = new MODFuente();
    	
    	Zipper zipper = new Zipper();
    	Source source = module.getSource();
    	fuente.setIdVersion(idVersion);
        fuente.setSource(zipper.zip(module.getName(), source.getRawData()));
        generate(fuente);
    }

    private void updateSourceErr() {
        MODFuenteError fuente = new MODFuenteError();
        
        Zipper zipper = new Zipper();
        Source source = module.getSource();
        fuente.setTms(new Timestamp(System.currentTimeMillis()));
        fuente.setNombre(module.getName());
        fuente.setUid(System.getProperty("user.name"));
        fuente.setSource(zipper.zip(module.getName(), source.getRawData()));
        generate(fuente);
    }
    
    private void updateResumen() {
    	Summary summary = module.getSummary();
    	Source  source  = module.getSource();
    	
    	MODResumen resumen = new MODResumen();

    	resumen.setIdVersion(idVersion);
    	resumen.setBlancos(summary.getBlanks());
    	resumen.setBytes(source.getSize());
    	resumen.setComentarios(summary.getComments());
    	resumen.setDecoradores(summary.getDecorators());
    	resumen.setLineas(summary.getLines());
    	resumen.setParrafos(module.getNumParagraphs());
    	resumen.setSentencias(module.getStatements());
    	resumen.setVerbosArit(summary.getStmtArit());
    	resumen.setVerbosControl(summary.getStmtControl());
    	resumen.setVerbosData(summary.getStmtDatos());
    	resumen.setVerbosFlujo(summary.getStmtFlujo());
        resumen.setVerbosIO(summary.getStmtIO());
    	resumen.setVerbosLang(summary.getStmtOtras());
        resumen.setFicheros(module.getNumFiles());

        generate(resumen);
    }
    
    
    private void updateFiles() {
    	int numFiles = 0;
    	for (Persistence file : module.getFicheros()) {
    		MODFichero f = new MODFichero(idVersion);
    		f.setIdFile(++numFiles);
    		f.setNombreLogico(file.getLogicalName());
    		f.setNombreFisico(file.getPhysicalName());
    		f.setTipo(file.getType());
    		f.setAcceso(file.getAccess());
    		f.setMaestro(file.isMaster() ? CDG.MASTER_PGM : CDG.MASTER_NO);
    		f.setLeido(0L);
            generate(f);
    	}
    }

    private void updateBloques() {
    	for (Block bloque : module.getBloques()) {
    		MODBloque b = new MODBloque(idVersion);
    		b.setBeg(bloque.getBegin());
    		b.setEnd(bloque.getEnd());
    		b.setOrden(bloque.getOrder());
    		b.setSentencias(bloque.getStatements());
    		b.setUsado(0);
    		generate(b);
    	}
    }
    
    private void updateParrafos() {
        Paragraph parr = null;
    	ArrayList<Paragraph> parrs = module.getParagraphs();
    	
    	for (int idx = parrs.size() - 1; idx > -1 ; idx--) {
            parr = parrs.get(idx); 
    		MODParrafo p = new MODParrafo(idVersion);
    		p.setLinea(parr.getLine());
    		p.setNombre(parr.getName());
    		p.setReferencias(parr.getReferences());
    		p.setSentencias(parr.getSentences());
    		p.setIndice(parr.getIndex());
    		p.setCc(parr.getCiclomatic());
    		p.setIsExit(parr.isExit() ? 1 : 0);
    		generate(p);
    	}
    }

    private void updateSections() {
        Sections sect = module.getSections();
        MODSecciones secciones = new MODSecciones();
        
        secciones.setIdVersion(idVersion);
        secciones.setDivData(sect.getData());
        secciones.setDivEnvironment(sect.getEnvironment());
        secciones.setDivIdentification(sect.getIdentification());
        secciones.setDivProcedure(sect.getProcedure());
        secciones.setSectConfiguration(sect.getConfiguration());
        secciones.setSectFile(sect.getFile());
        secciones.setSectInputOutput(sect.getInputOutput());
        secciones.setSectLinkage(sect.getLinkage());
        secciones.setSectScreen(sect.getScreen());
        secciones.setSectWorking(sect.getWorkingStorage());
        secciones.setSectLocal(sect.getLocalStorage());
        generate(secciones);
    }

    private void updateDependencias() {
        Dependencias deps = module.getDependencias();
        for (int index = 0; index < deps.numDependencias(); index++) {
            MODDependencia dep = new MODDependencia();
            dep.setIdVersion(idVersion);
            dep.setModulo(deps.getNombre(index));
            dep.setTipo(deps.getTipo(index));
            dep.setSubTipo(deps.getSubTipo(index));
            dep.setEstado(CDG.DEP_DECLARED);
            generate(dep);
        }
    }
 
    private void updateGrafo() {
        Paragraph parr = null;
        ArrayList<Paragraph> parrs = module.getParagraphs();
        
        for (int idx = parrs.size() - 1; idx > -1 ; idx--) {
            parr = parrs.get(idx); 
            MODGrafo p = new MODGrafo();
            p.setIdVersion(idVersion);
            p.setIdGrafo(1L);
            p.setOrden(idx);
            p.setTipo(1);
            p.setNombre(parr.getName());
            p.setPeso(parr.getCiclomatic());
            generate(p);
        }
    }

    private void updateBadStatements() {
        BadStmt bad = null;
        ArrayList<BadStmt> lista = module.getBadStmts();
        
        for (int idx = 0; idx < lista.size(); idx++) {
            bad = lista.get(idx); 
            MODBadStmt p = new MODBadStmt();
            p.setIdVersion(idVersion);
            p.setOrden(idx + 1);;
            p.setSentencia(bad.getStmt());
            p.setBegLine(bad.getBegLine());
            p.setEndLine(bad.getEndLine());
            p.setColumna(bad.getColumna());
            generate(p);
        }
    }
    
    private void openJMS() {

    	jms = JMSFactory.getJMSQueue();
    	jms.openOutput(cfg.getQueueParser());
    	
    }
    
    private void closeJMS() {
    	jms.close();
    }


    private void createXML() {
  	  try {
  		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
  		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

  		doc = docBuilder.newDocument();
  		root = doc.createElement("parser");
  		doc.appendChild(root);
  	  } catch (ParserConfigurationException pce) {
  		pce.printStackTrace();
  	  }    	
    }

    private void sendXML() {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			
	        StringWriter sw=new StringWriter();
			StreamResult sr=new StreamResult(sw);
			transformer.transform(source,sr);
			String xmlText = sw.toString();
			jms.put(xmlText);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
	   }
    }
    
   /**
     * Generate.
     *
     * @param o
     *            the o
     */
   public void generate(Object o) {
	  String node = o.getClass().getName();
	  node = node.substring(node.lastIndexOf('.') + 1);
	  
	  Element ele = doc.createElement(node);
	  String ser = serialize(o);
	  ele.appendChild(doc.createTextNode(ser));
	  root.appendChild(ele);
   }

    private String serialize(Object obj) {
    	try {
    	   ByteArrayOutputStream bs= new ByteArrayOutputStream();
    	   ObjectOutputStream os = new ObjectOutputStream (bs);
    	   os.writeObject(obj); 
    	   os.close();
    	   return Base64.encode(bs.toByteArray());
    	}
    	catch (Exception e) {
    		System.err.println(e.getLocalizedMessage());
    	}
    	return "";
    }
}