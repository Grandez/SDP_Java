/*
 * 
 */
package com.jgg.sdp.web.json.modulo;

import java.util.List;

import com.jgg.sdp.core.ctes.SYS;

public class Modulo {

    private boolean exist = false;
    
    private Long    idModulo;
    private Long    idVersion;
    private String  nombre;
    private String  desc;

    private int status = SYS.STATUS_OK;
    
    private Summary summary;
    private Comment comment;
    private Verbs  verbos;
    private Attrs   attrs;
    
    private List<Version> versiones;
    
	public boolean isExist() {
		return exist;
	}

	public void setExist(boolean exist) {
		this.exist = exist;
	}

	public Long getIdModulo() {
		return idModulo;
	}

	public void setIdModulo(Long idModulo) {
		this.idModulo = idModulo;
	}

	public Long getIdVersion() {
		return idVersion;
	}

	public void setIdVersion(Long idVersion) {
		this.idVersion = idVersion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Summary getSummary() {
		return summary;
	}

	public void setSummary(Summary summary) {
		this.summary = summary;
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public Verbs getVerbs() {
		return verbos;
	}

	public void setVerbs(Verbs verbos) {
		this.verbos = verbos;
	}
	
	public Attrs getAttrs() {
		return attrs;
	}

	public void setAttrs(Attrs attrs) {
		this.attrs = attrs;
	}

	public List<Version> getVersiones() {
		return versiones;
	}

	public void setVersiones(List<Version> versiones) {
		this.versiones = versiones;
	}
    
	
/*    
    private Resumen resumen;

    
    private Integer maxCCParr;
    private Integer maxCC;
    private Integer maxStmt;
    

    private Comment comment;

    
    
    private List<MODParrafo> parrafos;
    
    private Long   maxCobertura;
    private Long   cobertura;
    
    
    public boolean isExist() {
        return exist;
    }
    public void setExist(boolean exist) {
        this.exist = exist;
    }
    public Long getIdModulo() {
        return idModulo;
    }
    public void setIdModulo(Long idModulo) {
        this.idModulo = idModulo;
    }
    public Long getIdVersion() {
        return idVersion;
    }
    public void setIdVersion(Long idVersion) {
        this.idVersion = idVersion;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Long getCobertura() {
        return cobertura;
    }
    public void setCobertura(Long cobertura) {
        this.cobertura = cobertura;
    }
    public Long getMaxCobertura() {
        return maxCobertura;
    }
    public void setMaxCobertura(Long cobertura) {
        this.maxCobertura = cobertura;
    }
    
    public void setDesc(String desc) {
    	this.desc = desc;
    }
    
    public String getDesc() {
    	return desc;
    }
    
    public Integer getMaxCCParr() {
        return maxCCParr;
    }
    public void setMaxCCParr(Integer maxCCParr) {
        this.maxCCParr = maxCCParr;
    }
    public Integer getMaxCC() {
        return maxCC;
    }
    public void setMaxCC(Integer maxCC) {
        this.maxCC = maxCC;
    }
    public Integer getMaxStmt() {
        return maxStmt;
    }
    public void setMaxStmt(Integer maxStmt) {
        this.maxStmt = maxStmt;
    }

    public List<MODParrafo> getParrafos() {
        return parrafos;
    }
    public void setParrafos(List<MODParrafo> parrafos) {
        this.parrafos = parrafos;
    }
    public List<Version> getVersiones() {
        return versiones;
    }
    public void setVersiones(List<Version> versiones) {
        this.versiones = versiones;
    }

    public void setResumen(Resumen resumen) {
        this.resumen = resumen;
    }
    public Resumen getResumen() {
        return resumen;
    }
    public void setAttrs(Attrs attrs) {
        this.attrs = attrs;
    }
    public Attrs getAttrs() {
        return attrs;
    }

    public void setStatus(int st) {
    	this.status = st;
    }
    public int getStatus() {
    	return this.status;
    }
    public void setComment(Comment comment) {
    	this.comment = comment;
    }
    public Comment getComment() {
    	return this.comment;
    }
    public void setVerbos(Verbos verbos) {
    	this.verbos = verbos;
    }
    public Verbos getVerbos() {
    	return this.verbos;
    }
*/
}
