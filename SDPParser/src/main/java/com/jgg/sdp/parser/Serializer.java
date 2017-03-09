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
import java.util.ArrayList;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jgg.sdp.domain.core.*;
import com.jgg.sdp.domain.module.*;
import com.jgg.sdp.core.config.Configuration;
import com.jgg.sdp.core.ctes.CDG;
// import com.jgg.sdp.core.jms.*;
import com.jgg.sdp.core.tools.*;
import com.jgg.sdp.module.base.*;
import com.jgg.sdp.module.items.*;
import com.jgg.sdp.module.unit.SDPUnit;
import com.sun.org.apache.xml.internal.security.utils.Base64;

public class Serializer {

    XMLTable xml = new XMLTable();	
//    JMSQueue jms = null;
    Configuration cfg = Configuration.getInstance();

    Document doc = null;
	Element  root = null;
    
    Module module    = null;
    Long   idFile    = null;
    Long   idModule  = null;
    Long   idVersion = null;
    
    private String xmlText;

    public Serializer() {
       idModule  = Fechas.serial();
       idVersion = Fechas.serial();
       idFile    = Fechas.serial();
    }
    
    public String getXMLMessage() {
    	return xmlText;
    }
    
    public String generateXMLFile(SDPUnit unit) {
         createXML("SDPUnit");
         serializeCompileUnit(unit);
         serializeSource(unit);
         return convertXMLToString();
    }
    
    public String generateXMLModule(Module module, long idFile, boolean full)  {
		   
	   createXML("SDPModule");	
	   
	   this.module = module;
	   
       serializeModule(full);   
	   return convertXMLToString();
    }

    private void serializeCompileUnit(SDPUnit unit) {
    	
    	SDPFile file = new SDPFile();

    	file.setIdFile(unit.getId());
        file.setArchivo(unit.getNombre());
        file.setTipo(unit.getTipo());
        file.setEstado(unit.getStatus());
        file.setFirma(unit.getFirma());
        file.setNumModulos(unit.getNumModulos());
        file.setUid(System.getProperty("user.name"));
        file.setTms(Fechas.getTimestamp());
        
        generate(file);
    }    

    private void serializeSource(SDPUnit unit) {        
    	SDPFuente fuente = new SDPFuente();
    	
    	Zipper zipper = new Zipper();
    	fuente.setIdFile(unit.getId());
        fuente.setSource(zipper.zip(unit.getNombre(), unit.getMainSource().getRawData()));
        generate(fuente);
        
    }
    
    private void serializeModule(boolean full) {
       updateModule();
       updateVersion();
       
       if (!full) return;
       
       updateSummary();
       updateCodigo();
 	   updateFiles();
       updateBloques();
  	   updateParrafos();
  	   updateSections();
   	   updateCopys();
   	   updateCalls();
   	   updateGrafo();
   	   updateBadStatements();
   	   updateIssues();
   	   updateCICS();
    }
    
    private void updateModule() {
        SDPModulo modulo = new SDPModulo(idModule);
       	modulo.setIdAppl(0L);
       	modulo.setNombre(module.getName());

       	modulo.setUid(System.getProperty("user.name"));
       	modulo.setTms(Fechas.getTimestamp());
       	modulo.setTipo(module.getType());
       	modulo.setActivo(CDG.MODULE_ACTIVE);
       	modulo.setIdVersion(idVersion);
       	modulo.setVersiones(1);
    	generate(modulo);
    }

    private void updateVersion() {
    	MODVersion version = new MODVersion(idModule, idVersion);

    	version.setIdFile(idFile);
        version.setNombre(module.getName());
    	version.setTipo(module.getType());
    	version.setDesc(module.getDescription());
    	version.setUid(System.getProperty("user.name"));
    	version.setTms(Fechas.getTimestamp());
    	version.setEstado  (module.getStatus());
    	version.setMissing(module.getCopyStatus());
    	version.setArbol(module.getTreeStatus());
    	    	
    	generate(version);
    }
    

    private void updateSummary() {
    	
    	MODSummary summary = new MODSummary();
        Summary   summ     = module.getSummary();
        
    	summary.setIdVersion(idVersion);
    	summary.setFichero(summ.hasFile());
    	summary.setSgdb(summ.hasSQL());
    	summary.setCics(summ.hasCICS());
    	summary.setMq(summ.hasMQ());
    	summary.setCallsCount(summ.getCallCount());
    	summary.setCallsMode(summ.getCallMode());
    	summary.setCallsType(summ.getCallType());    	
    	summary.setTs(summ.isThreadsafe());
    	summary.setTsMode(summ.getTSMode());
    	summary.setMemoria(summ.getMemory());
    	summary.setDinamico(summ.getAllocate());
        generate(summary);
    }
    
    private void updateCodigo() {
    	Codigo codigo = module.getCodigo();
    	
    	MODCodigo resumen = new MODCodigo();

    	resumen.setIdVersion(idVersion);
    	resumen.setBlancos(codigo.getBlanks());

    	resumen.setComentarios  (codigo.getComments());
    	resumen.setDecoradores  (codigo.getDecorators());
    	resumen.setLineas       (codigo.getLines());
    	resumen.setVerbosArit   (codigo.getStmtArit());
    	resumen.setVerbosControl(codigo.getStmtControl());
    	resumen.setVerbosData   (codigo.getStmtDatos());
    	resumen.setVerbosFlujo  (codigo.getStmtFlujo());
        resumen.setVerbosIO     (codigo.getStmtIO());
    	resumen.setVerbosLang   (codigo.getStmtOtras());
    	resumen.setBloques      (codigo.getCommentBlocks());
    	resumen.setLoops        (codigo.getLoops());
    	resumen.setVerbosCics   (codigo.getStmtCics());
    	resumen.setVerbosSql    (codigo.getStmtSql());
    	
    	resumen.setParrafos  (module.getNumParagraphs());
    	resumen.setSentencias(module.getStatements());
    	resumen.setFicheros  (module.getNumFiles());

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
    	
    	for (int idx = 0 ; idx < parrs.size() ; idx++) {
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
        secciones.setSectWorking(sect.getWorkingStorage());
        secciones.setSectLocal(sect.getLocalStorage());
        generate(secciones);
    }

    private void updateCopys() {
        for (Copy d : module.getCopys()) {
//        	System.out.println("MODCOPY " + d.getNombre());
            MODCopy dep = new MODCopy();
            
            dep.setIdVersion(idVersion);
            dep.setModulo(d.getNombre());
            dep.setTipo(d.getTipo());
            dep.setSubTipo(d.getSubtipo());
            dep.setEstado(d.getEstado());
            dep.setRefs(d.getRefs());
            dep.setIdFile(d.getIdFile());
            dep.setIdCopy(d.getIdCopy());
            generate(dep);
            
/*            
            // Se ha procesado o no se ha podido
            if (d.getTipo() == CDG.DEP_COPY && d.getEstado() == CDG.DEP_DECLARED) {
            	MODMissing missing = new MODMissing();
            	missing.setIdVersion(idVersion);
            	missing.setModulo(d.getNombre());
            	missing.setMotivo(0);
            	generate(missing);
            }
*/            
        }
    }

    private void updateCalls() {
        for (Routine d : module.getCalls()) {
            MODCall dep = new MODCall();
            dep.setIdVersion(idVersion);
            dep.setModulo(d.getNombre());
            dep.setMetodo(d.getMetodo());
            dep.setModo(d.getModo());
            dep.setEstado(d.getEstado());
            dep.setRefs(d.getRefs());
            dep.setEjecutado(0); // Siempre 0 (Se actualiza al ejecutarse)
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

    private void updateIssues() {
    	int idx = 0;
        
        for (Issue issue : module.getIssues()) {
            MODIssue i = new MODIssue();
            i.setIdVersion(idVersion);
            i.setIdSeq(++idx);
            i.setIdIssue(issue.getIdIssue());
            i.setSeverity(issue.getSeverity());
            i.setBegLine(issue.getBegLine());
            i.setEndLine(issue.getEndLine());
            i.setBegColumn(issue.getBegColumn());
            i.setEndColumn(issue.getEndColumn());
            i.setBloque(issue.getBloque());
            i.setFirma(issue.getFirma());
            generate(i);
        }
    }

    private void updateCICS() {
        ArrayList<CICSVerb> lista = module.getCICSVerbs();
        
        for (CICSVerb verbo : lista) {
            MODCics verb = new MODCics();
            verb.setIdVersion(idVersion);
            verb.setVerbo(verbo.getVerbo());
            verb.setVeces(verbo.getCount());
            verb.setTipo(verbo.getTipo());
            generate(verb);
        }
    }
    
    private void openJMS() {
/*
    	jms = JMSFactory.getJMSQueue();
    	jms.openOutput(cfg.getQueueParser());
  */  	
    }
    
    private void closeJMS() {
//    	jms.close();
    }


    private void createXML(String rootName) {
  	  try {
  		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
  		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

  		doc = docBuilder.newDocument();
  		root = doc.createElement(rootName);
  		doc.appendChild(root);
  	  } catch (ParserConfigurationException pce) {
  		pce.printStackTrace();
  	  }    	
    }

    private String convertXMLToString() {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		StringWriter sw = new StringWriter();;
		Transformer transformer;
		
		try {
			transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult sr=new StreamResult(sw);
			transformer.transform(source,sr);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
	   }
	   
		String xmlText = sw.toString();
//		module.setXMLHash(Firma.calculate(xmlText.getBytes()));
		return xmlText;
    }

    private void sendXML(boolean local) {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			
	        StringWriter sw=new StringWriter();
			StreamResult sr=new StreamResult(sw);
			transformer.transform(source,sr);
			String xmlText = sw.toString();
			module.setXMLHash(Firma.calculate(xmlText.getBytes()));
//			if (!local) jms.put(xmlText);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
	   }
    }
   
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