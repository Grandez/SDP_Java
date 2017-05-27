/**
 * Encapsula toda la informacion estatica de un modulo
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.module.base;

import java.util.*;

import com.jgg.sdp.core.ctes.CDG;
import com.jgg.sdp.core.tools.*;
import com.jgg.sdp.module.factorias.ModulesFactory;
import com.jgg.sdp.module.graph.Graph;
import com.jgg.sdp.module.items.*;
import com.jgg.sdp.module.tables.*;
import com.jgg.sdp.tools.Cadena;

public class Module {

	private int           estado  = 0;      // Resultado del analisis
	
    private String        cpyName      = null;
    private int           cpyType      = 0;
    private boolean       cpyIgnored   = false;
	
//	private Stack<Source>   sources      = new Stack<Source>();
	private Summary         summary      = new Summary();
	private Codigo          codigo       = new Codigo();	
	private TBCics          tbCics       = new TBCics();
	private Sections        sections     = new Sections();	
	
	private TBFiles       tbFiles      = new TBFiles();
    private Graph         grafo        = new Graph(this);
    private TBBadStmts    tbBad        = new TBBadStmts();

	private TBBloques     tbBloques    = new TBBloques();
    private TBParagraphs  tbParagraphs = new TBParagraphs();
	private TBVars        tbVars       = new TBVars();
    private TBIssues      tbIssues     = new TBIssues();
	private TBCopys       tbCopys      = new TBCopys();
	private TBRoutines    tbRuts       = new TBRoutines();
	private TBSql         tbSql        = new TBSql();	
	
	private boolean fullParsed  = true;
	private String  fullName    = null;
	private String  name        = null;

	private String  author      = "N/A";
    private int     tipo        = -1;
    
    // Por defecto completo, se actualiza si hay fallos
    private int     copys       = CDG.CPY_ST_FULL;
    private int     tree        = 0;
    
    
    // Firma digital del nuevo fuente
    private String newHash     = null;
    private String xmlHash     = null;
    private String sdpGroup    = null;
    
	private StringBuilder desc = new StringBuilder();
	
	private int     statements    = 0;
	private int     outputs       = 0;
    
	private ArrayList<String> chgTokens = null;
	
	public Module() {
		
	}

	public Module(String fullName) {
		Archivo a = new Archivo(fullName);
		name = a.getBaseName();
		this.fullName = fullName; 
	}

	/************************************************************************/
	/*** ESTADO DEL ANALISIS                                              ***/
	/************************************************************************/
	
	public void setFull()         { estado = CDG.STATUS_FULL;    }
	public boolean isFull()       { return estado == CDG.STATUS_FULL; }
	
	public void setStatus(int e)  { estado = e; }
	public int  getStatus()       { return estado; }
	
	/************************************************************************/
	/*** GETTERS DE LOS OTROS OBJETOS                                     ***/
	/************************************************************************/
	
	public Sections        getSections()        { return sections;       }
    public Summary         getSummary()         { return summary;        }
    public Codigo          getCodigo()          { return codigo;         }    
    
	public ArrayList<Persistence> getFicheros()   { return tbFiles.getFiles();           }
	public ArrayList<Block>       getBloques()    {	return tbBloques.getBloques();       }
	public ArrayList<Paragraph>   getParagraphs() { return tbParagraphs.getParagraphs(); }
	public ArrayList<BadStmt>     getBadStmts()   { return tbBad.getBadStmts();          }
	public ArrayList<Issue>       getIssues()     { return tbIssues.getIssues();         }

	
	public TBParagraphs getTBParagraphs()     { return tbParagraphs;   }
    public TBVars       getTBVars()           { return tbVars;         }

    public void   setNewHash(String hash)     { newHash = hash;        }
	public String getNewHash()                { return newHash;        }
	public void   setXMLHash(String hash)     { xmlHash = hash;        }
	public String getXMLHash()                { return xmlHash;        }
	public void   setSDPGroup(String var)     { sdpGroup = var;        }
	public String getSDPGroup()               { return sdpGroup;       }
	
	/************************************************************************/
	/*** setters y getters de las variables privadas                      ***/
	/************************************************************************/
	
	public int     getType()                 { return tipo; 	}
	public void    setType(int type)         {	tipo = type; 	}

	public String  getFullName()             { return fullName;      }
	public String  getName()                 { return name;          }
	
    public void    setName(String name)      { 
    	this.name = name;
    	ModulesFactory.setModuleName(name);
    }
    
    public void    setStatements(int stmt)   { statements = stmt;    }
    public int     getStatements()           { return statements;    }
	public void    setAuthor (String author) { this.author = author; }
	public String  getAuthor ()              { return author;        }

	public boolean isFullParsed()            { return fullParsed;    }
	public void    setFullParsed(boolean f)  { fullParsed = f;       }

	public void    setCopyStatus(int cpy)    { copys = cpy;          }
	public int     getCopyStatus()           { return copys;         }

	// Numero de variables no procesadas
	public void    addTreeVariable()          { tree++;             }
	public void    setNumTreeVariables(int t) { tree = t;           }
	public int     getTreeStatus()            { return tree;        }
	
	/*
	public String  getMemberName()           {
        if (sources.size() == 1) return getName();
        Source s = sources.peek();
        return getName() + "[" + s.getBaseName() + "]";
	}
	*/
    public String  getDescription() { 
    	return (desc.length() == 0) ? "N/A" : desc.toString();      
    }
    
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
    
    public int getNumFiles() {
    	return tbFiles.getCount();
    }

    public void setOutputs(int outputs) { this.outputs = outputs; }
    public int  getOutputs()            { return outputs;         }
    public void incOutputs()            { incOutputs(1);          }
    public void incOutputs(int n)       { outputs += n;           }
    
    
	/************************************************************************/
	/*** INTERFAZ AL OBJETO CODIGO                                        ***/
	/************************************************************************/

    public void incCommentBlocks()           { codigo.incCommentBlocks();   }
    public void incComments(boolean comment) { codigo.incComments(comment); }
    public void incLines   (boolean data)    { codigo.incLines(data);       }    
    
    public void incStmtArit()                { codigo.incStmtArit();        }
    public void incStmtControl()             { codigo.incStmtControl();     }
    public void incStmtIO()                  { codigo.incStmtIO();          }
    public void incStmtFlujo()               { codigo.incStmtFlujo();       }
    public void incStmtDatos()               { codigo.incStmtDatos();       }
    public void incStmtLang()                { codigo.incStmtLang();        }
    public void incStmtCics()                { codigo.incStmtCics();        }
    public void incStmtSql()                 { codigo.incStmtSql();         }
        
	/************************************************************************/
	/*** INTERFAZ AL OBJETO SUMMARY                                       ***/
	/************************************************************************/

    public void setCICS()  { summary.setCICS(); }
    public void setSQL()   { summary.setSQL();  }
    public void setFile()  { summary.setFile(); }
    
    //// ********************************************************************
    //// Dependencias                                          
    //// ********************************************************************
    
    public TBCopys getTableDependences() {
    	return tbCopys;
    }

    
    public Copy getDependence(String name) {
    	return tbCopys.get(name);
    }

    public ArrayList<Copy> getCopys() { 
    	   return tbCopys.getCopys();           
    }

    public Copy addCopy(Copy dep) {
 //   	System.out.println("COPY " + dep.getNombre());
    	return tbCopys.add(dep);
    }
    
    public void addRoutine(Routine rut) {
    	tbRuts.add(rut);
    }

    public void addRoutineChecked(Routine rut) {
    	tbRuts.addChecked(rut);
    }
    
    public ArrayList<Routine> getDynamicCalls() {
    	return tbRuts.getDynamicRoutines();
    }
    
    public ArrayList<Routine> getCalls() {
    	return tbRuts.getCheckedRoutines();
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
    
    public void  addParagraphReference(String from, String to) {
    	   tbParagraphs.addReference(from, to);
    }
	
	public Paragraph  addParagraph(String name, int line, int stmts) {
		return tbParagraphs.addParagraph(name, line, stmts);
	}
    
	public void setParagraphReferences() {
	   tbParagraphs.setReferences();
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
	public void lastBlock(int end, int stmts)           { tbBloques.lastBloque(end, stmts);}
	public void closeBlock(int beg, int end, int stmts) { tbBloques.closeBloque(beg, end, stmts); }
    public void beginBlock(int begin)                   { tbBloques.beginBlock(begin);  }
    public void endBlock(int end, int stmts)            { tbBloques.endBlock(end, stmts); }
    public int  getNumBloques()                         { return tbBloques.getCount();  }

    //// *********************************************************************    
    //// Tratamiento de SQL                                           
    //// *********************************************************************

	public void addSql(SQLItem sqli, SQLCode sqlc)      { tbSql.addSql(sqli,  sqlc);  }
	public List<SQLItem> getTableSql()                  { return tbSql.getSqlItems(); }
	public List<SQLCode> getTableSqlCode()              { return tbSql.getSqlCodes(); }
	
    //// *********************************************************************    
    //// Tratamiento de issues                                           
    //// *********************************************************************
    
    public void addIssue(Issue issue)  { tbIssues.addIssue(issue);  }
    
    //// ********************************************************************
    //// Tratamiento de persistencia                                      
    //// ********************************************************************

    public void        addFile(Persistence f)       { tbFiles.addFile(f); 	                }
	public Persistence getFile(String name)         { return tbFiles.getFile(name); 	    }
	public Persistence getFileByRecord(String name) { return tbFiles.getFileByRecord(name); }

	public void setRecordName(Object fileName, ArrayList<String> recordNames) {
		Persistence p = tbFiles.getFile((String) fileName);
		p.setRecordNames(recordNames);
	}

    //// *********************************************************************
	//// Tratamiento de variables                                         
	//// *********************************************************************
	
    /**
     * Obtiene un objeto Variable por nombre
     * @param name Nombre de la variable
     * @return El objeto Var
     */
    	
	public Variable getVariable(String name) {
         return tbVars.getVariable(name);		
	}
	
	public Variable addVariable(Variable var) {
		if (var == null) {
			System.err.println("Have VAR NULL");
			return var;
		}
//		var.setModule(sources.peek().getBaseName());
 		
		tbVars.addVariable(var);
		return var;
	}
	
	public void setRedefines(Variable var, String name) {
		tbVars.addHermano(var.getName(), name);
	}
	
	public void setVarRead(String name, String parent) {
		if (tbVars.setVarRead(name, parent)) setVarMissing();
	}

	public void setVarWrite(String name) {
		if(setVarWrite(name, null)) setVarMissing();
	}

	public boolean setVarWrite(String name, String parent) {
		if (name == null) return true;
		return tbVars.setVarWrite(name, parent);
	}

	public void setVarValue(String name, String value, boolean id) {
		Variable v = tbVars.getVariable(name);
		if (v == null) return;
		if (id) {
			v.setVariable(value);
		}
		else {
			v.setValue(value);
		}
	}
	
	public void setVarMissing() {
	   	summary.setMissing(1);
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

    //// *********************************************************************
    ////  Tratamiento CICS                          
    //// *********************************************************************

    public void addCICSVerb(String verb, boolean ts, int type) {
    	   tbCics.add(verb, type);
           summary.setTS(type);
    }
    
    public ArrayList<CICSVerb> getCICSVerbs() {
    	return tbCics.getList();
    }

    //// *********************************************************************
    ////  Tratamiento COPY REPLACING                          
    //// *********************************************************************
    
    public void              markCopyIgnored()               { cpyIgnored = true;    }
    public boolean           isCopyIgnored()                 { return cpyIgnored;    }
    public void              setCopyMember(String name)      { cpyName = name;       }
    public String            getCopyMember()                 { return cpyName;       }
    public void              setCopyType(int type)           { cpyType = type;       }
    public int               getCopyType()                   { return cpyType;       }
    public void              resetReplacing()                { chgTokens = null;     }  
    public void              addReplacingToken(String token) { chgTokens.add(token); }
    public ArrayList<String> getReplacingTokens()            { return chgTokens;     }
    
    //// *********************************************************************
    ////  Tratamiento GRAFO                          
    //// *********************************************************************
    
    public Graph           getGraph()           { return grafo;          }
    public void            makeGraph()          { 
//JGG    	grafo.parse();
    	
    }

}
