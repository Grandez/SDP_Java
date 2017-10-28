package com.jgg.sdp.domain.module;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="MOD_ISSUES")
public class MODIssue implements Serializable {

	private static final long serialVersionUID = -2053116334409955488L;

	public static final String issuesByVersion = "SELECT i FROM MODIssue i WHERE idVersion = ?1 ORDER BY i.idSeq";
	
	public static final String sqlCountIssues =  
		"SELECT C.Severity, Count(*) AS Total " +
	    "      FROM ( SELECT A.idIssue AS Id, B.severity AS Severity " + 
        "                    FROM MOD_ISSUES AS A, RUL_ISSUES AS B   " +
        "                    WHERE A.idVersion = ?1                  " +
        "                    AND A.idIssue=B.idIssue                 " + 
        "           ) AS C " +
        "      GROUP by C.Severity " +
        "      ORDER BY C.Severity";

	@Id
    @Column(name="idVersion")
    Long idVersion;
    
    @Id
    @Column(name="idSeq")
    Integer idSeq;

    @Column(name="idGroup")
    Long idGroup;

    @Column(name="idItem")
    Long idItem;
    
    @Column(name="idRule")
    Long idRule;

    @Column(name="prefix")
    String prefix;
    
    @Column(name="severity")
    Integer severity;
    
    @Column(name="begLine")
    Integer begLine;

    @Column(name="begColumn")
    Integer begColumn;

    @Column(name="endLine")
    Integer endLine;

    @Column(name="endColumn")
    Integer endColumn;

    @Column(name="bloque")
    String bloque;
    
    @Column(name="firma")
    String firma;

    @Column(name="idException")
    Long idException;
    
    @Transient
    private String desc = null;

	public Long getIdVersion() {
		return idVersion;
	}

	public void setIdVersion(Long idVersion) {
		this.idVersion = idVersion;
	}

	public Integer getIdSeq() {
		return idSeq;
	}

	public void setIdSeq(Integer idSeq) {
		this.idSeq = idSeq;
	}

	public Long getIdGroup() {
		return idGroup;
	}

	public void setIdGroup(Long idGroup) {
		this.idGroup = idGroup;
	}

	public Long getIdItem() {
		return idItem;
	}

	public void setIdItem(Long idItem) {
		this.idItem = idItem;
	}

	public Long getIdRule() {
		return idRule;
	}

	public void setIdRule(Long idRule) {
		this.idRule = idRule;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	public Integer getSeverity() {
		return severity;
	}

	public void setSeverity(Integer severity) {
		this.severity = severity;
	}

	public Integer getBegLine() {
		return begLine;
	}

	public void setBegLine(Integer begLine) {
		this.begLine = begLine;
	}

	public Integer getBegColumn() {
		return begColumn;
	}

	public void setBegColumn(Integer begColumn) {
		this.begColumn = begColumn;
	}

	public Integer getEndLine() {
		return endLine;
	}

	public void setEndLine(Integer endLine) {
		this.endLine = endLine;
	}

	public Integer getEndColumn() {
		return endColumn;
	}

	public void setEndColumn(Integer endColumn) {
		this.endColumn = endColumn;
	}

	public String getBloque() {
		return bloque;
	}

	public void setBloque(String bloque) {
		this.bloque = bloque;
	}

	public String getFirma() {
		return firma;
	}

	public void setFirma(String firma) {
		this.firma = firma;
	}

	public Long getIdException() {
		return idException;
	}

	public void setIdException(Long idException) {
		this.idException = idException;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((begColumn == null) ? 0 : begColumn.hashCode());
		result = prime * result + ((begLine == null) ? 0 : begLine.hashCode());
		result = prime * result + ((bloque == null) ? 0 : bloque.hashCode());
		result = prime * result + ((desc == null) ? 0 : desc.hashCode());
		result = prime * result + ((endColumn == null) ? 0 : endColumn.hashCode());
		result = prime * result + ((endLine == null) ? 0 : endLine.hashCode());
		result = prime * result + ((firma == null) ? 0 : firma.hashCode());
		result = prime * result + ((idException == null) ? 0 : idException.hashCode());
		result = prime * result + ((idGroup == null) ? 0 : idGroup.hashCode());
		result = prime * result + ((idItem == null) ? 0 : idItem.hashCode());
		result = prime * result + ((idRule == null) ? 0 : idRule.hashCode());
		result = prime * result + ((idSeq == null) ? 0 : idSeq.hashCode());
		result = prime * result + ((idVersion == null) ? 0 : idVersion.hashCode());
		result = prime * result + ((severity == null) ? 0 : severity.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MODIssue other = (MODIssue) obj;
		if (begColumn == null) {
			if (other.begColumn != null)
				return false;
		} else if (!begColumn.equals(other.begColumn))
			return false;
		if (begLine == null) {
			if (other.begLine != null)
				return false;
		} else if (!begLine.equals(other.begLine))
			return false;
		if (bloque == null) {
			if (other.bloque != null)
				return false;
		} else if (!bloque.equals(other.bloque))
			return false;
		if (desc == null) {
			if (other.desc != null)
				return false;
		} else if (!desc.equals(other.desc))
			return false;
		if (endColumn == null) {
			if (other.endColumn != null)
				return false;
		} else if (!endColumn.equals(other.endColumn))
			return false;
		if (endLine == null) {
			if (other.endLine != null)
				return false;
		} else if (!endLine.equals(other.endLine))
			return false;
		if (firma == null) {
			if (other.firma != null)
				return false;
		} else if (!firma.equals(other.firma))
			return false;
		if (idException == null) {
			if (other.idException != null)
				return false;
		} else if (!idException.equals(other.idException))
			return false;
		if (idGroup == null) {
			if (other.idGroup != null)
				return false;
		} else if (!idGroup.equals(other.idGroup))
			return false;
		if (idItem == null) {
			if (other.idItem != null)
				return false;
		} else if (!idItem.equals(other.idItem))
			return false;
		if (idRule == null) {
			if (other.idRule != null)
				return false;
		} else if (!idRule.equals(other.idRule))
			return false;
		if (idSeq == null) {
			if (other.idSeq != null)
				return false;
		} else if (!idSeq.equals(other.idSeq))
			return false;
		if (idVersion == null) {
			if (other.idVersion != null)
				return false;
		} else if (!idVersion.equals(other.idVersion))
			return false;
		if (severity == null) {
			if (other.severity != null)
				return false;
		} else if (!severity.equals(other.severity))
			return false;
		return true;
	}
    
    


}    