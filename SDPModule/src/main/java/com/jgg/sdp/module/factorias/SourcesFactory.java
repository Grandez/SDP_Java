/**
 * Factoria de generacion de objetos Source
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.module.factorias;

import java.util.*;

import com.jgg.sdp.core.ctes.CDG;
import com.jgg.sdp.core.tools.Archivo;
import com.jgg.sdp.module.unit.Source;

public class SourcesFactory {

	private static HashMap<String, Source> sources = new HashMap<String, Source>();

	public static Source getSource(Archivo archivo) {
		return getSource(archivo, null);
	}

	public static Source getSourceCode(Archivo archivo) {
		Source src = getSource(archivo, null);
		src.setTipo(CDG.SOURCE_CODE);
        return src;		
	}
	
    public static Source getSource(Archivo archivo, ArrayList<String> toks) {
		Source item = null;
		
		if (sources.isEmpty() != true) item = sources.get(archivo.getFullPath());
		
		if (item != null) {
			item.rewind();
		}
		else {
		   item = new Source(archivo, toks);
		   sources.put(archivo.getFullPath(), item);
		}
		return item;
	}
	
	public static void cleanSources() {
    	sources = new HashMap<String, Source>();
	}

    public static Source getCopy(Archivo archivo, ArrayList<String> toks) {
		Source item = sources.get(archivo.getFullPath());
		
		if (item != null) {
			item.prepareData(toks);
		}
		else {
		   item = new Source(archivo, toks);
		   sources.put(archivo.getFullPath(), item);
		}
		return item;
	}
	
}
