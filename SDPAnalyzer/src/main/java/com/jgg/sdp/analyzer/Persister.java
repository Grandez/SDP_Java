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

import java.util.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jgg.sdp.common.config.*;

import com.jgg.sdp.domain.core.*;
import com.jgg.sdp.domain.graph.*;
import com.jgg.sdp.domain.module.*;
import com.jgg.sdp.domain.services.CommonService;
import com.jgg.sdp.domain.services.cfg.DBConfiguration;
import com.jgg.sdp.domain.services.core.*;
import com.jgg.sdp.domain.services.log.LOGInputService;
import com.jgg.sdp.domain.sql.*;
import com.jgg.sdp.common.ctes.CDG;

import com.jgg.sdp.core.ctes.LOG;
import com.jgg.sdp.module.base.*;
import com.jgg.sdp.module.graph.*;
import com.jgg.sdp.module.items.*;
import com.jgg.sdp.module.status.StatusItem;
import com.jgg.sdp.module.unit.Source;
import com.jgg.sdp.module.unit.Unit;
import com.jgg.sdp.tools.*;

public class Persister {

    private CommonService          commonService     = new CommonService();
    private SDPModuloService       moduloService     = new SDPModuloService();
    private SDPRelModuloAppService relModAppService  = new SDPRelModuloAppService();
    private SDPStatusService       statusService     = new SDPStatusService();
    
    private LOGInputService log = new LOGInputService();
    
    XMLTable xml = new XMLTable();	
//    JMSQueue jms = null;
    Configuration cfg = DBConfiguration.getInstance();

    Document doc = null;
	Element  root = null;
    
    Module  module    = null;
    Unit unit      = null;
    
    Long   idFile         = null;
    Long   idFileVersion  = null;    
    Long   idModule       = null;
    Long   idVersion      = null;
    
    private String xmlText;

    public Persister() {
       idModule      = Fechas.serial();
       idVersion     = Fechas.serial();
       idFile        = Fechas.serial();
       idFileVersion = Fechas.serial();
    }
    
    public void beginTrans() {
    	commonService.beginTrans();
    }
    
    public void commit() {
    	commonService.commitTrans();
    }
    
    public String getXMLMessage() {
    	return xmlText;
    }
    
    public void  persistModule(Module module)  {
	   this.module = module;	   
       persistModuleData();   
    }

    public SDPFile persistUnit(Unit unit) {
// 	   this.idFile = unit.getId();
// 	   this.idFileVersion = unit.getIdVersion();

    	SDPFile f = persistCompileUnit(unit);
    	persistSource(unit);
    	return f;
    }

    public void persistStatus(SDPFile file) {
    	generate(file);
    }
    
    private SDPFile persistCompileUnit(Unit unit) {

        Source src = unit.getMainSource();
        
    	SDPFile file = new SDPFile();
    	file.setIdFile(idFile);
    	file.setIdVersion(idFileVersion);
        file.setArchivo(src.getBaseName());
        file.setFullName(src.getFullName());
        file.setTipo(src.getTipo());
        file.setEstado(unit.getStatus());
        file.setFirma(src.getFirma());
        file.setNumModulos(unit.getNumModulos());
        file.setUid(System.getProperty("user.name"));
        file.setTms(Fechas.getTimestamp());
        
        generate(file);
        
        return file;        
    }    

    private void persistSource(Unit unit) {        
    	SDPSource fuente = new SDPSource();

    	fuente.setIdFile(idFile);
    	fuente.setIdVersion(idFileVersion);
    	fuente.setEncoded("ZIP");
        fuente.setSource(Zipper.zip(unit.getMainSource().getFullName(), unit.getMainSource().getRawData()));
        generate(fuente);
    }
    
    private void persistModuleData() {
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
   	   updateStatus();
   	   updateCICS();
   	   updateSQL();
   	   updateGrafo();
   	   
    }

    private void updateModule() {
    	int logAction = LOG.MOD_COMP;
        SDPModulo mod = moduloService.findByNameAndType(module.getName(), module.getType());
        if (mod == null) {
            mod = new SDPModulo();
            mod.setIdModulo(idModule);
            mod.setNombre(module.getName());
            mod.setIdAppl(relModAppService.getApplication(module.getName()));
            mod.setTipo(module.getType());
            mod.setActivo(CDG.MODULE_ACTIVE);
            mod.setVersiones(0);
//            actualizaAplicacion(mod.getIdAppl());
//            insertaEstado(mod);
            logAction = LOG.MOD_NEW;
        }
        
        idModule = mod.getIdModulo();
        idVersion = Fechas.serial();
        
        mod.setIdVersion(idVersion);
        mod.setIdFile(module.getSource().getIdFile());
       	mod.setUid(System.getProperty("user.name"));
       	mod.setTms(Fechas.getTimestamp());
       	mod.setTipo(module.getType());
       	mod.setActivo(CDG.MODULE_ACTIVE);
       	mod.setVersiones(mod.getVersiones() + 1);
       	
       	log.registra(logAction, mod);

       	generate(mod);
    }

    private void updateVersion() {
    	MODVersion version = new MODVersion(idModule, idVersion);

    	version.setIdVersionFile(module.getSource().getIdFileVersion());
        version.setNombre(module.getName());
    	version.setTipo(module.getType());
    	version.setDesc(module.getDescription());
    	version.setUid(System.getProperty("user.name"));
    	version.setTms(Fechas.getTimestamp());
    	version.setEstado  (module.getParserStatus());
    	version.setMissing(module.getCopyStatus());
    	version.setArbol(module.getTreeStatus());
    	version.setAuthor(module.getAuthor());
    	    	
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

    	resumen.setLineas       (codigo.getLines());
    	resumen.setVerbosArit   (codigo.getStmtArit());
    	resumen.setVerbosControl(codigo.getStmtControl());
    	resumen.setVerbosData   (codigo.getStmtDatos());
    	resumen.setVerbosFlujo  (codigo.getStmtFlujo());
        resumen.setVerbosIO     (codigo.getStmtIO());
    	resumen.setVerbosLang   (codigo.getStmtOtras());
    	resumen.setLoops        (codigo.getLoops());
    	resumen.setVerbosCics   (codigo.getStmtCics());
    	resumen.setVerbosSql    (codigo.getStmtSql());

    	Comment cmt = module.getComment();
    	
    	resumen.setBloques      (cmt.getBloques());    	
    	resumen.setComentarios  (cmt.getLines());
    	resumen.setDecoradores  (cmt.getDecorators());
    	
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
    		p.setSentencias(parr.getNumStatements());
    		p.setBloques(parr.getNumBlocks());
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
            dep.setEjecutado(0L); // Siempre 0 (Se actualiza al ejecutarse)
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
            i.setIdGroup(issue.getIdGroup());
            i.setIdItem(issue.getIdItem());
            i.setIdRule(issue.getIdRule());
            i.setSeverity(issue.getSeverity());
            i.setBegLine(issue.getBegLine());
            i.setEndLine(issue.getEndLine());
            i.setBegColumn(issue.getBegColumn());
            i.setEndColumn(issue.getEndColumn());
            i.setBloque(issue.getBloque());
            i.setFirma(issue.getFirma());
            i.setPrefix(issue.getPrefix());
            i.setIdException(0L);
            generate(i);
        }
    }

    private void updateStatus() {
    	List<StatusItem> items = module.getStatus().getStatusItemList();
    	for (StatusItem item : items) {
    		MODStatus st = new MODStatus();
    		st.setIdVersion(idVersion);
    		st.setIdGrupo(item.getIdGroup());
    		st.setIdItem(item.getIdItem());
    		st.setActual(item.getActual());
    		st.setDelta(item.getDelta());
    		st.setExcepcion(item.getExcepcion());
    		
    		st.setMaximo(item.getMaximo());
    		st.setProgreso(item.getProgreso());
    		st.setStatus(item.getStatus());
    		generate(st);
    	}
        updateStatusModule(items);
    }
    
    private void updateStatusModule(List<StatusItem> items) {
    	
    	int iCurr = 0;
    	int iNew = 0;
    	boolean update = false;
    	
    	List<SDPStatus> current = statusService.listAll(module.getName());
    	Collections.sort(items);
    	
    	while (iCurr < current.size() && iNew < items.size()) {
    	   update = false;
    	   SDPStatus c = current.get(iCurr);
    	   StatusItem i = items.get(iNew);
    	   switch (compareItemStatus(c, i)) {
    	       case 0: c = updateSDPActual(c, i);
    	    	   iCurr++;
    	    	   iNew++;
    	    	   update = true;
    	    	   break;
    	       case 1: 
    	    	   c = createSDPActual(i);
    	    	   iNew++;
    	    	   update = true;
    	    	   break;
    	       case -1: 
    	    	   iCurr++;
                   break;
    	   }
    	   if (update) generate(c);
    	}

    	while (iNew < items.size()) {
    		SDPStatus c = createSDPActual(items.get(iNew++));
    		generate(c);
    	}
    }

    private SDPStatus createSDPActual(StatusItem i) {
 	   SDPStatus c = new SDPStatus();
 	   c.setIdModulo(idModule);
	   c.setIdGrupo(i.getIdGroup());
	   c.setIdItem(i.getIdItem());
        return updateSDPActual(c, i);
     }
    
    private SDPStatus updateSDPActual(SDPStatus c, StatusItem i) {
 	   c.setActual(i.getActual());
 	   c.setDelta(i.getDelta());
 	   c.setExcepcion(i.getExcepcion());
 	   c.setMaximo(i.getMaximo());
 	   c.setProgreso(i.getStatus());
 	   c.setStatus(i.getStatus());
       return c;
    }
    
    private int compareItemStatus(SDPStatus c, StatusItem i) {
    	int diff = c.getIdGrupo() - i.getIdGroup();
    	if (diff != 0) return diff / Math.abs(diff);
    	diff = c.getIdItem() - i.getIdItem();
    	if (diff != 0) diff = diff / Math.abs(diff);
    	return diff;
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
    	Graph g = module.getComponentGraph();
    	
    	for (SubGraph grf : g.getGraphs()) {
    		DCGGraph grafo = new DCGGraph();
    		grafo.setIdVersion(idVersion);
    		grafo.setIdGrafo(grf.getId());
//    		grafo.setLevel(grf.getLevel());
    		grafo.setLevel(0);    		
    		grafo.setName(grf.getName());
    		generate(grafo);
    	}
    	
    	for (Node nodo : g.getNodes()) {
    		DCGNode node = new DCGNode();
    		node.setIdVersion(idVersion);
    		node.setIdGrafo(nodo.getIdGraph());
    		node.setIdNode(nodo.getId());
    		node.setSubgraph(nodo.getGraphChild());
    		node.setNombre(nodo.getName());
    		node.setTipo(nodo.getType());
    		generate(node);
    	}
    	
    	for (Edge e : g.getEdges()) {
    		DCGEdge edge = new DCGEdge();
    		edge.setIdVersion(idVersion);
    		edge.setIdGrafo(e.getIdGrafo());
    		edge.setIdFrom(e.getFrom().getId());
    		edge.setIdTo(e.getTo().getId());
    		edge.setType(e.getType());
    		generate(edge);
    	}
    }

        
    public void generate(Object o) {
        commonService.update(o);
   }

}