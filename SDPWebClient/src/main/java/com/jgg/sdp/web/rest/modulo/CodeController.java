/*
 * 
 */
package com.jgg.sdp.web.rest.modulo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jgg.sdp.domain.core.*;
import com.jgg.sdp.domain.module.*;
import com.jgg.sdp.domain.services.core.*;
import com.jgg.sdp.domain.services.module.*;
import com.jgg.sdp.web.json.Fuente;

@RestController
public class CodeController {

	private final static int LINE_EMPTY    = 0; 
	private final static int LINE_COMMENT  = 1;
	private final static int LINE_DIVISION = 2;
	private final static int LINE_SECTION  = 3;
	
	private final static int LINE_PARRAFO = 20;
	private final static int LINE_CODE    = 21;
	
	@Autowired
	SDPModuloService    modService;
	@Autowired
	MODVersionService   verService;
	@Autowired
	SDPFileService      fteService;
	@Autowired
	SDPSourceService    srcService;

    @Autowired
    MODSeccionesService   seccionService;
    @Autowired
    MODBloqueService    bloqueService;
    @Autowired
    MODBadStmtService   badService;
    @Autowired
    MODIssueService    issueService;
    
    @RequestMapping("/source/{idModulo}")
    public List<Fuente> getSourceLastVersion(@PathVariable Long idModulo) {
    	SDPModulo mod = modService.findById(idModulo);
        return getSourceByVersion(idModulo, mod.getIdVersion());
    }
    
    @RequestMapping("/source/{idModulo}/{idVersion}")
    public List<Fuente> getSourceByVersion(@PathVariable Long idModulo, @PathVariable Long idVersion) {
    	SDPModulo mod = modService.findById(idModulo);
    	MODVersion ver = verService.findById(idVersion);
        if (ver == null) return new ArrayList<Fuente>();

       return prepareSource(mod.getIdFile(), ver);
    }
    
    private List<Fuente> prepareSource(Long idFile, MODVersion ver) {
       SDPSource source = srcService.getSource(idFile, ver.getIdVersionFile());
       if (source == null) return new ArrayList<Fuente>();
       ArrayList<Fuente> fuente = mountSourceCode(source, ver);
       setLineType(fuente);
       applySecciones(fuente, ver.getIdVersion());
//       applyBloques(fuente, idVersion);
//       applyErrores(fuente, idVersion);
//       applyIssues(fuente, idVersion);
       return fuente;
    }
    
    private ArrayList<Fuente> mountSourceCode(SDPSource source, MODVersion ver) {
    	ArrayList<Fuente> fuente = new ArrayList<Fuente>();
    	// Zipper zipper = new Zipper();
        
    	//char[] raw = zipper.unzip("",source.getSource());
        //String[] lineas = (new String(raw)).split("\\r\\n|\\n", -1);
    	String[] lineas = (new String(source.getSource())).split("\\r\\n|\\n", -1);
    	 
        int max = ver.getOffsetEnd();
        if (max == -1) max = lineas.length;
         
        for (int iLine = ver.getOffsetBeg(); iLine < max; iLine++) {
        	Fuente fte = new Fuente();
        	fte.setParent(0);
        	fte.setLinea(iLine + 1);
        	fte.setCode(lineas[iLine]);
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
    		fte.setType(LINE_EMPTY);
    		return;    		    		
    	}
    	
    	char c = linea.charAt(6);
    	if (c == '*' || c == '/' || c == 'D' || c == 'd') {
    		fte.setType(LINE_COMMENT);
    		return;
    	}

    	int max = linea.length() > 72 ? 72 : linea.length();
    	
    	if (linea.substring(6, max).trim().length() == 0) {
    		fte.setType(LINE_EMPTY);
    		return;    		
    	}
    	if (linea.charAt(7) == ' ') {
    		fte.setType(LINE_CODE);
    		return;
    	}
    	// Aqui es algo en linea 8. puede ser parrafo o puede ser
    	// 01{espacio}algo
    	if (linea.charAt(9) != ' ') {
    		fte.setType(LINE_PARRAFO);
    	} else {	
    		fte.setType(LINE_CODE);
    	}
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
    	int idx;
    	int[] pos = new int[5];
    	MODSecciones secciones = seccionService.getSecciones(idVersion);
    	pos[1] = secciones.getDivIdentification();
    	pos[2] = secciones.getDivEnvironment();
    	pos[3] = secciones.getDivData();
    	pos[4] = secciones.getDivProcedure();
    	
    	for (idx = 0; idx < 4; idx++) {
    		if (pos[idx] > -1) fuente.get(idx).setType(LINE_DIVISION);
    	}
    	
    	int bloque = 4;
    	
    	for (idx = fuente.size() - 1; idx >= 0; idx--) {
    		Fuente fte = fuente.get(idx);
    		fte.setBloque(bloque);
    		if (bloque > 0 && pos[bloque] == idx) { // Fin seccion
    		    while(pos[--bloque] == -1);
    		}
    	}
    	ajustaSecciones(fuente, pos);
    }
    
    /**
     * Asigna los posibles comentarios existentes encima de una division
     * a su division correspondiente
     * @param pos Posiciones de las divisiones
     */

    private void ajustaSecciones(ArrayList<Fuente> fuente, int[] pos) {
    	int aux;
    	Fuente fte;
    	for (int idx = 1; idx < pos.length; idx++) {
    		aux = pos[idx] - 1;
    		if (aux > -1) {
    		   fte = fuente.get(aux);
    		   while (aux > -1 && (fte.getType() == LINE_EMPTY || 
    			                   fte.getType() == LINE_COMMENT)) {
    			     fte.setBloque(idx);
    			     aux--;
    		   }
    		}
    	}
    }
  
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
			   fuente.get(idx).addIssue(i.getIdRule());
		   }
	   }
   }

}
