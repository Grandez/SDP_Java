/**
 * Encapsula toda la informacion estatica de un modulo
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.module.base;

import java.io.File;
import java.util.ArrayList;

import com.jgg.sdp.core.ctes.CDG;
import com.jgg.sdp.core.tools.Cadena;
import com.jgg.sdp.module.grafo.Grafo;
import com.jgg.sdp.module.items.*;
import com.jgg.sdp.module.tablas.*;
import com.jgg.sdp.module.tools.Injector;

public class Module {

	private Source       source       = null;
	private Summary      summary      = new Summary();
	private Dependencias deps         = new Dependencias();
	private Sections     sections     = new Sections();
	private TBFiles      tbFiles      = new TBFiles();
	private TBVars       vars         = new TBVars();
	private TBBloques    tbBloques    = new TBBloques();
    private TBParagraphs tbParagraphs = new TBParagraphs();
    private Grafo        grafo        = new Grafo();
    private TBBadStmts   tbBad        = new TBBadStmts();
	private Injector     injector     = new Injector();

	
	private String fullName    = null;
	private String name        = null;
	private String author      = "N/A";
    private int    tipo        = -1;
    
	private StringBuilder desc = new StringBuilder();
	
	private int    statements = 0;
	
	/**
     * Genera un nuevo Module
     *
     * @param fullName Path completo del ficchero fuente
     */
	public Module(String fullName) {
		this.fullName = fullName;
		int beg = fullName.lastIndexOf(File.separator);
		int last = fullName.lastIndexOf(".");
		beg++;
		if (last == -1) last = fullName.length();
		name = fullName.substring(beg, last);
	}

	/************************************************************************/
	/*** GETTERS DE LOS OTROS OBJETOS                                     ***/
	/************************************************************************/
	
	public Source       getSource()       { return source;   }
	public Sections     getSections()     { return sections; }
    public Summary      getSummary()      { return summary;  }
    public Injector     getInjector()     { return injector; }
    public Grafo        getGrafo()        { return grafo;    }
    public Dependencias getDependencias() { return deps;     }
    
	public ArrayList<Persistence> getFicheros()   { return tbFiles.getFiles();           }
	public ArrayList<Block>       getBloques()    {	return tbBloques.getBloques();       }
	public ArrayList<Paragraph>   getParagraphs() { return tbParagraphs.getParagraphs(); }
	public ArrayList<BadStmt>     getBadStmts()   { return tbBad.getBadStmts();          }
	
	/************************************************************************/
	/*** setters y getters de las variables privadas                      ***/
	/************************************************************************/
	
	public int     getType()                 { return tipo; 	}
	public void    setType(int type)         {	tipo = type; 	}

	public String  getFullName()             { return fullName;      }
	public String  getName()                 { return name;          }
    public void    setName(String name)      { this.name = name;     }
    public void    setStatements(int stmt)   { statements = stmt;    }
    public int     getStatements()           { return statements;    }
	public void    setAuthor (String author) { this.author = author; }
	public String  getAuthor ()              { return author;        }

    /**
     * @return Devuelve la descripcion del modulo o N/A si no existe
     */
    public String  getDescription() { 
    	return (desc.length() == 0) ? "N/A" : desc.toString();      
    }
    
    /**
     * Establece la descripcion del modulo
     *
     * @param desc  La descripcion
     * @param concat Si cierto concatena el texto a la descripcion existente
     */
    public void    setDescription(Object desc, boolean concat) { 
    	String d = Cadena.rtrim((String) desc);
    	if (this.desc.length() != 0) {
    		if (concat) {
    			this.desc.append(" ");
    		}
    		else {
    		   this.desc.append("<br>");
    		}
    	}
    	this.desc.append(d);
    }

	public void setSource(Source source) {		this.source = source; 	}
    
    public String   getIdModule()  { 
    	return source.getDigest(); 
    }
    
    public int getNumFiles() {
    	return tbFiles.getCount();
    }

    //// ********************************************************************
    //// Tratamiento de parrafos                                          
    //// ********************************************************************

	/**
     * Obtiene el objeto Paragraph asociado al nombre
     * @parm El nombre del parrafo
     * @return  El objeto asociado
     */
	
	public Paragraph            getParagraph(String name) { return tbParagraphs.getParagraph(name); }
    public int                  getNumParagraphs()        { return tbParagraphs.getCount();         }
    
    /**
     * Acumula una referencia al parrafo indicado
     *
     * @param name Nombre del parrafo
     * @return el objeto Paragraph asociado
     */
    public Paragraph            addParagraphReference(String name) {
    	   return tbParagraphs.addReference(name);
    }
	
	/**
     * Crea un nuevo parrafo en la tabla de parrafos
     *
     * @param name  Nombre del parrafo
     * @param line  Linea donde esta declarado
     * @param stmts Numero de sentencias
     * @return      el objeto Paragraph creado
     */
	public Paragraph  addParagraph(String name, int line, int stmts) {
		return tbParagraphs.addParagraph(name, line, stmts);
	}
    
    /**
     * Ajusta la informacion del ultimo parrafo
     *
     * @param lastLine Ultima linea del codigo fuente
     * @param stmts    Numero de instrucciones del codigo fuente
     */
    public void lastParagraph(int lastLine, int stmts) {
        Paragraph p = tbParagraphs.getCurrentParagraph();
        if (p != null) p.setSentences(stmts - p.getSentences());
    }

    //// *********************************************************************    
    //// Tratamiento de bloques                                           
    //// *********************************************************************

    /**
     * Crea un nuevo bloque
     * @param bloque El objeto bloque a crear
     * @param endLine La ultima linea analizada
     */
	
	public void addBlock(Block bloque, int endLine)     { tbBloques.addBloque(bloque, endLine);    }
	
	/**
     * Crea el bloque final
     *
     * @param end   La linea donde esta el bloque
     * @param stmts El numero de sentencias analizadas 
     */
	public void lastBlock(int end, int stmts)           { tbBloques.lastBloque(end, stmts);}
	
	/**
     * Ajusta los datos del bloque abierto
     *
     * @param beg   Linea de inicio
     * @param end   Linea de fin
     * @param stmts Sentencias analizadas 
     */
	public void closeBlock(int beg, int end, int stmts) { tbBloques.closeBloque(beg, end, stmts); }
    
    /**
     * Inicia un bloque
     *
     * @param begin Linea de inicio
     */
    public void beginBlock(int begin)                   { tbBloques.beginBlock(begin);  }
    
    /**
     * Cierra un bloque
     *
     * @param end   Linea de fin
     * @param stmts Numero de sentencias analizadas
     */
    public void endBlock(int end, int stmts)            { tbBloques.endBlock(end, stmts); }

    public int  getNumBloques()                         { return tbBloques.getCount();  }

    //// ********************************************************************
    //// Tratamiento de persistencia                                      
    //// ********************************************************************

	/**
     * Inserta un nuevo fichero en la tabla de ficheros
     * @param f El obejto Persitence a insertar
     */

    public void        addFile(Persistence f)       { tbFiles.addFile(f); 	                }
	
	/**
     * Obtiene el objeto Persistence con el nombre asociado
     *
     * @param name Nombre del objeto Persistence
     * @return El objeto asociado
     */
	public Persistence getFile(String name)         { return tbFiles.getFile(name); 	    }
	
    /**
     * Obtiene el objeto Persistence en funcion de su descriptor de registro
     *
     * @param name Nombre del descriptor
     * @return El objeto asociado
     */
	public Persistence getFileByRecord(String name) { return tbFiles.getFileByRecord(name); }
	
	/**
     * Establece el nombre del desriptor de registro
     *
     * @param fileName   Nombre del fichero
     * @param recordName Nombre del descriptor del registro
     */
	public void setRecordName(Var fileName, Var recordName) {
		Persistence p = tbFiles.getFile(fileName.getName());
		p.setRecordName(recordName.getName());
	}

	/**
     * Añade una COPY a la lista de COPYS
     *
     * @param name Nombre de la COPY
     */
	public void addCopy(String name) {
	    deps.addDependencia(name,  CDG.DEP_CPY, sections.getTypeByPosition());
	}

    //// *********************************************************************
	//// Tratamiento de variables                                         
	//// *********************************************************************
	
    /**
     * Obtiene un objeto Variable por nombre
     * @param name Nombre de la variable
     * @return El objeto Var
     */
    	
	public Var getVariable(String name) {
         return vars.getVariable(name);		
	}
	
	/**
     * Añade una objeto Var a la lista de variables
     *
     * @param var El objeto a añadir
     * @return    El propio objeto
     */
	public Var addVariable(Var var) {
		vars.addVariable(var);
		return var;
	}

    //// *********************************************************************
    ////  Tratamiento de sentencias no permitidas                          
    //// *********************************************************************
	
	/**
     * Añade una sentencia no permitida a la lista de sentencias no permitidas
     * @param stmt La sentencia no permitida
     * @param begLine Linea de inicio de la sentencia
     * @param endLine Linea de fin de la sentencia
     * @param columna Columna de inicio de la sentencia
     */
        
    public void addBadSentence(String stmt, 
                               Integer begLine, 
                               Integer endLine, 
                               Integer columna) {
        BadStmt bad = new BadStmt();
        bad.setStmt(stmt);
        bad.setBegLine(begLine);
        bad.setEndLine(endLine);
        bad.setColumna(columna);
        tbBad.addStmt(bad);    
    }

}
