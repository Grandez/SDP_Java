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
package com.jgg.sdp.analyzer;

import java.io.*;
import java.util.ArrayList;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jgg.sdp.domain.core.*;
import com.jgg.sdp.domain.graph.*;
import com.jgg.sdp.domain.module.*;
import com.jgg.sdp.domain.services.CommonService;
import com.jgg.sdp.domain.services.core.SDPModuloService;
import com.jgg.sdp.domain.services.core.SDPRelModuloAppService;
import com.jgg.sdp.domain.services.log.LOGInputService;
import com.jgg.sdp.domain.sql.*;

import com.jgg.sdp.core.config.Configuration;
import com.jgg.sdp.core.ctes.CDG;
// import com.jgg.sdp.core.jms.*;
import com.jgg.sdp.core.ctes.LOG;
import com.jgg.sdp.core.tools.*;
import com.jgg.sdp.module.base.*;
import com.jgg.sdp.module.graph.*;
import com.jgg.sdp.module.items.*;
import com.jgg.sdp.module.unit.SDPUnit;
import com.jgg.sdp.tools.Zipper;
import com.sun.org.apache.xml.internal.security.utils.Base64;

public class Persister {

    private CommonService          commonService     = new CommonService();
    private SDPModuloService       moduloService     = new SDPModuloService();
    private SDPRelModuloAppService relModAppService  = new SDPRelModuloAppService();

    private LOGInputService log = new LOGInputService();
    
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

    public Persister() {
       idModule  = Fechas.serial();
       idVersion = Fechas.serial();
       idFile    = Fechas.serial();
    }
    
    public String getXMLMessage() {
    	return xmlText;
    }
    
    public void  persistModule(Module module, long idFile)  {
	   this.module = module;	   
       persistModule();   
    }

    public void persistUnit(SDPUnit unit) {
        
    }
    
    private void persistCompileUnit(SDPUnit unit) {
    	
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

    private void persistSource(SDPUnit unit) {        
    	SDPFuente fuente = new SDPFuente();
    	
    	Zipper zipper = new Zipper();
    	fuente.setIdFile(unit.getId());
        fuente.setSource(zipper.zip(unit.getNombre(), unit.getMainSource().getRawData()));
        generate(fuente);
        
    }
    
    private void persistModule() {
       updateModule();
       updateVersion();
       
       updateSummary();
       updateCodigo();
 	   updateFiles();
       updateBloques();
  	   updateParrafos();
  	   updateSections();
   	   updateCopys();
   	   updateCalls();
   	   updateBadStatements();
   	   updateIssues();
   	   updateCICS();
   	   updateSQL();
   	   updateGrafo();
    }

    private void updateModule() {
        SDPModulo mod = moduloService.findByNameAndType(module.getName(), module.getType());
        if (mod == null) {
            mod = new SDPModulo();
            mod.setIdModulo(idModule);
            mod.setNombre(module.getName());
            mod.setIdAppl(relModAppService.getApplication(module.getName()));
            mod.setTipo(module.getType());
            mod.setActivo(CDG.MODULE_ACTIVE);
            mod.setIdVersion(idVersion);
            mod.setVersiones(0);
//            actualizaAplicacion(mod.getIdAppl());
//            insertaEstado(mod);
            log.registra(LOG.MOD_NEW, mod);
        }
        
       	mod.setUid(System.getProperty("user.name"));
       	mod.setTms(Fechas.getTimestamp());
       	mod.setTipo(module.getType());
       	mod.setActivo(CDG.MODULE_ACTIVE);
       	mod.setVersiones(mod.getVersiones() + 1);
    	generate(mod);
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
    
    private void updateSQL() {
    	updateSQLInfo();
    	updateSQLCode();    	
    }
    
    private void updateSQLInfo() {
        for (SQLItem verbo : module.getTableSql()) {
            MODSql verb = new MODSql();
//            System.out.println("SQL: " + verbo.getBegLine() + " - " + verbo.getVerb());
            verb.setIdVersion(idVersion);
            verb.setBegLine(verbo.getBegLine());
            verb.setVerb(verbo.getVerb());
            verb.setComplexity(verbo.getComplexity());
            verb.setExplanation(verbo.getExplanation());
            verb.setFirma(verbo.getFirma());
            generate(verb);           
        }
    }

    private void updateSQLCode() {
        for (SQLCode verbo : module.getTableSqlCode()) {
            MODSqlStmt verb = new MODSqlStmt();
//            System.out.println("SQLSTMT: " + verbo.getBegLine() );
            verb.setIdVersion(idVersion);
            verb.setBegLine(verbo.getBegLine());
            verb.setFirma(verbo.getFirma());
            verb.setStmt(verbo.getStmt());
            generate(verb);
        }    	
    }
    
    private void updateGrafo() {
    	Graph g = module.getGraph();
    	
    	for (Grafo grf : g.getGraphs()) {
    		DCGGraph grafo = new DCGGraph();
    		grafo.setIdVersion(idVersion);
    		grafo.setIdGrafo(grf.getId());
    		grafo.setLevel(grf.getLevel());
    		grafo.setName(grf.getName());
    		generate(grafo);
    	}
    	
    	for (Node nodo : g.getNodes()) {
    		DCGNode node = new DCGNode();
    		node.setIdVersion(idVersion);
    		node.setIdGrafo(nodo.getGraphParent());
    		node.setIdNode(nodo.getId());
    		node.setSubgraph(nodo.getGraphChild());
    		node.setNombre(nodo.getName());
    		node.setTipo(nodo.getSubtype().ordinal());
    		generate(node);
    	}
    	
    	for (Edge e : g.getEdges()) {
    		DCGEdge edge = new DCGEdge();
    		edge.setIdVersion(idVersion);
    		edge.setIdGrafo(e.getIdGrafo());
    		edge.setIdFrom(e.getFrom().getId());
    		edge.setIdTo(e.getTo().getId());
    		generate(edge);
    	}
    }

        
    public void generate(Object o) {
        commonService.update(o);
   }

}