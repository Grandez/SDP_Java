/*
 * 
 */
package com.jgg.sdp.web.rest.modulo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jgg.sdp.domain.module.*;
import com.jgg.sdp.domain.services.core.*;
import com.jgg.sdp.domain.services.module.*;
import com.jgg.sdp.web.json.Fuente;
import com.jgg.sdp.core.tools.Zipper;

@RestController
public class CodeController {

	private final static int LINE_EMPTY   = 0; 
	private final static int LINE_COMMENT = 1;
	private final static int LINE_PARRAFO = 2;
	private final static int LINE_CODE    = 3;
	
	@Autowired
	SDPModuloService    moduloService;
    @Autowired
    MODVersionService   versionService;
    @Autowired
    MODFuenteService    sourceService;
    @Autowired
    MODSeccionesService seccionesService;
    @Autowired
    MODBloqueService    bloqueService;
    @Autowired
    MODBadStmtService   badService;
    @Autowired
    MODIssueService    issueService;
    
    @RequestMapping("/code/{idModulo}")
    public List<Fuente> getSourceLastVersion(@PathVariable Long idModulo) {
        Long version = moduloService.getCurrentVersion(idModulo);
        if (version == 0L) return new ArrayList<Fuente>();
    	return getSourceByVersion(idModulo, version);
    }
    
    @RequestMapping("/code/{idModulo}/{idVersion}")
    public List<Fuente> getSourceByVersion(@PathVariable Long idModulo, @PathVariable Long idVersion) {

       MODFuente source = sourceService.getSource(idVersion);
       ArrayList<Fuente> fuente = mountSourceCode(source);
       setLineType(fuente);
       applySecciones(fuente, idVersion);
       applyBloques(fuente, idVersion);
       applyErrores(fuente, idVersion);
       applyIssues(fuente, idVersion);
       return fuente;
    }
    
    private ArrayList<Fuente> mountSourceCode(MODFuente source) {
    	ArrayList<Fuente> fuente = new ArrayList<Fuente>();
    	Zipper zipper = new Zipper();
        
    	char[] raw = zipper.unzip("",source.getSource());
        String[] lineas = (new String(raw)).split("\\r\\n|\\n", -1);
        
        for (int idx = 0; idx < lineas.length; idx++) {
        	Fuente fte = new Fuente();
        	fte.setLinea(idx);
        	fte.setCode(lineas[idx]);
        	fuente.add(fte);
        }
        return fuente;
    }

    private void setLineType(ArrayList<Fuente> fuente) {
    	for (Fuente fte : fuente) {
    		setType(fte);
    	}
    }
    
    private void setType(Fuente fte) {
    	String linea = fte.getCode();

    	// Control de lineas vacias
    	if (linea.length() < 7) {
    		fte.setTipo(LINE_EMPTY);
    		return;    		    		
    	}
    	
    	if (linea.charAt(6) == '*') {
    		fte.setTipo(LINE_COMMENT);
    		return;
    	}

    	int max = linea.length() > 72 ? 72 : linea.length();
    	
    	if (linea.substring(6, max).trim().length() == 0) {
    		fte.setTipo(LINE_EMPTY);
    		return;    		
    	}
    	if (linea.charAt(7) == ' ') {
    		fte.setTipo(LINE_CODE);
    		return;
    	}
    	// Aqui es algo en linea 8. puede ser parrafo o puede ser
    	// 01{espacio}algo
    	if (linea.charAt(9) == ' ') {
    		fte.setTipo(LINE_CODE);
    		return;
    	}
    	fte.setTipo(LINE_PARRAFO);
    }
    
    /**
     * Asigna la division a la que pertenece la linea de codigo
     * Para simplificar, se utiliza la tabla de posiciones
     * indexada de acuerdo con el indicador de la division
     * Ej: Identificaction division es el bloque 1
     *     Se indexa en la posicion 1
     * @param idVersion
     */
    private void applySecciones(ArrayList<Fuente> fuente, Long idVersion) {
    	int[] pos = new int[5];
    	MODSecciones secciones = seccionesService.getSecciones(idVersion);
    	pos[1] = secciones.getDivIdentification();
    	pos[2] = secciones.getDivEnvironment();
    	pos[3] = secciones.getDivData();
    	pos[4] = secciones.getDivProcedure();
    	
    	int bloque = 4;
    	
    	for (int idx = fuente.size() - 1; idx >= 0; idx--) {
    		Fuente fte = fuente.get(idx);
    		fte.setBloque(bloque);
    		if (bloque > 0 && pos[bloque] == idx) { // Fin seccion
    		    while(pos[--bloque] == -1);
    		}
    	}
//    	ajustaSecciones(pos);
    }
    
    /**
     * Asigna los posibles comentarios existentes encima de una division
     * a su division correspondiente
     * @param pos Posiciones de las divisiones
     */
/*
    private void ajustaSecciones(int[] pos) {
    	int aux;
    	Fuente fte;
    	for (int idx = 1; idx < pos.length; idx++) {
    		aux = pos[idx] - 1;
    		if (aux > -1) {
    		   fte = fuente.get(aux);
    		   while (aux > -1 && (fte.getTipo() == LINE_EMPTY || 
    			                   fte.getTipo() == LINE_COMMENT)) {
    			     fte.setBloque(idx);
    			     aux--;
    		   }
    		}
    	}
    }
  */  
    private void applyBloques(ArrayList<Fuente> fuente, Long idVersion) {
         MODBloque[] bloques = bloqueService.getBloques(idVersion);
         for (int idx = 0; idx < bloques.length; idx++) {
        	 if (bloques[idx].IsUsed()) {
        		 for (int l = bloques[idx].getBeg(); l <= bloques[idx].getEnd(); l++) {
        			 fuente.get(l).setUsado(true);
        		 }
            }
         }
    }

    private void applyErrores(ArrayList<Fuente> fuente, Long idVersion) {
        MODBadStmt b = null;
        List<MODBadStmt> lista = badService.getBadStmts(idVersion);
        for (int idx = 0; idx < lista.size(); idx++) {
            b = lista.get(idx);
            for (int linea = b.getBegLine(); linea <= b.getEndLine(); linea++) {
                fuente.get(linea).setMalo(true);
            }
        }
   }
   
   private void applyIssues(ArrayList<Fuente> fuente, Long idVersion) {
	   for (MODIssue i : issueService.getIssues(idVersion)) {
		   for (int idx = i.getBegLine(); idx <= i.getEndLine(); idx++) {
			   fuente.get(idx).addIssue(i.getIdIssue());
		   }
	   }
   }

}
