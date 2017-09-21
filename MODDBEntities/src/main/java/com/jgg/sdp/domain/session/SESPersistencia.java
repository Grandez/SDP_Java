package com.jgg.sdp.domain.session;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="SES_PERSISTENCIA")
@NamedQueries({
	 @NamedQuery(name="SESPersistencia.filesBySesion"
	            ,query="SELECT p FROM SESPersistencia p WHERE p.idSesion = ?1 " +
	                   "ORDER BY p.idVersion, p.idFile"
	 )
	 ,@NamedQuery(name="SESPersistencia.totalFilesBySesion"
	            ,query="SELECT p FROM SESPersistencia p " +
                       "         WHERE p.idSesion = ?1 " +
	            	   "         AND   p.orden = 0" +
                       "ORDER BY p.idVersion, idFile"
    )
    ,@NamedQuery(name="SESPersistencia.totalFileById"
                ,query="SELECT p FROM SESPersistencia p " +
                       "         WHERE p.idSesion  = ?1  " +
                	   "         AND   p.idVersion = ?2  " +
                       "         AND   p.idFile    = ?3  " +
        	           "         AND   p.orden     =  0  " +
                       "         ORDER BY p.idVersion, idFile"
   )
   ,@NamedQuery(name="SESPersistencia.updMaestro"
               ,query="UPDATE SESPersistencia p     " +
                      "       SET p.maestro = ?3    " +
            		  "       WHERE p.idVersion = ?1 " +
                      "       AND   p.idFile    = ?2 "
   )
   ,@NamedQuery(name="SESPersistencia.maxLeido",
                query="SELECT MAX(p.acRead)   FROM  SESPersistencia p WHERE p.idSesion = ?1"
   )
   ,@NamedQuery(name="SESPersistencia.maxEscrito",
                query="SELECT MAX(p.acInsert) FROM  SESPersistencia p WHERE p.idSesion = ?1"
   )


})
public class SESPersistencia implements Serializable {

	private static final long serialVersionUID = 5413264402867719938L;

	public static final String sumByModulo = 
                   "SELECT p.idVersion, " +
                   "      SUM(p.acOpen),   SUM(p.acClose),  SUM(p.acInsert), SUM(p.acRead), " +
                   "      SUM(p.acUpdate), SUM(p.acDelete), SUM(p.acSelect), SUM(p.acTotal) " +
                   "      FROM SESPersistencia p " +
                   "      WHERE p.idSesion = ?1 "  +
                   "      GROUP BY p.idVersion";
    public static final String sumBySesion = 
                  "SELECT SUM(p.acOpen),   SUM(p.acClose),  SUM(p.acInsert), SUM(p.acRead), " +
                  "       SUM(p.acUpdate), SUM(p.acDelete), SUM(p.acSelect), SUM(p.acTotal)  " +
                  "FROM SESPersistencia p " +
                  "WHERE p.idSesion = ?1  ";
  	
	public static final String sumTotalMaestros =
			"SELECT SUM(p.acRead), SUM(p.acInsert) " +
	        "       FROM  SESPersistencia p        " +
	        "       WHERE p.idSesion = ?1          " +
	        "       AND   p.maestro > 0";

	@Id
	@Column(name="idSesion")
	String idSesion;
	
	@Id
	@Column(name="idVersion")
	Long idVersion;

	@Id
	@Column(name="orden")
	Long orden;
	
	@Id
	@Column(name="idFile")
	Integer idFile;

	@Column(name="maestro")
	Integer maestro;
	
	@Column(name="acOpen")
	Long acOpen = 0L;

	@Column(name="acClose")
	Long acClose = 0L;
	
	@Column(name="acInsert")
	Long acInsert = 0L;
	
	@Column(name="acRead")
	Long acRead = 0L;
	
	@Column(name="acUpdate")
	Long acUpdate = 0L;
	
	@Column(name="acDelete")
	Long acDelete = 0L;
	
	@Column(name="acSelect")
	Long acSelect = 0L;
	
	@Column(name="acTotal")
	Long acTotal = 0L;

	public SESPersistencia() {
	    maestro  = 0;
	    acOpen   = 0L;
        acClose  = 0L;
	    acInsert = 0L;
	    acRead   = 0L;
        acUpdate = 0L;
	    acDelete = 0L;
	    acSelect = 0L; 
	    acTotal  = 0L;    
	}
	
	public String getIdSesion() {
		return idSesion;
	}

	public void setIdSesion(String idSesion) {
		this.idSesion = idSesion;
	}

	public Long getIdVersion() {
		return idVersion;
	}

	public void setIdVersion(Long idVersion) {
		this.idVersion = idVersion;
	}

	public Long getOrden() {
		return orden;
	}

	public void setOrden(Long orden) {
		this.orden = orden;
	}

	public Integer getIdFile() {
		return idFile;
	}

	public void setIdFile(Integer idFile) {
		this.idFile = idFile;
	}

	public Integer getMaestro() {
		return maestro;
	}

	public void setMaestro(Integer maestro) {
		this.maestro = maestro;
	}
	
	public Long getAcOpen() {
		return acOpen;
	}

	public void setAcOpen(Long acOpen) {
		this.acOpen = acOpen;
	}

	public Long getAcClose() {
		return acClose;
	}

	public void setAcClose(Long acClose) {
		this.acClose = acClose;
	}

	public Long getAcInsert() {
		return acInsert;
	}

	public void setAcInsert(Long acInsert) {
		this.acInsert = acInsert;
	}

	public Long getAcRead() {
		return acRead;
	}

	public void setAcRead(Long acRead) {
		this.acRead = acRead;
	}

	public Long getAcUpdate() {
		return acUpdate;
	}

	public void setAcUpdate(Long acUpdate) {
		this.acUpdate = acUpdate;
	}

	public Long getAcDelete() {
		return acDelete;
	}

	public void setAcDelete(Long acDelete) {
		this.acDelete = acDelete;
	}

	public Long getAcSelect() {
		return acSelect;
	}

	public void setAcSelect(Long acSelect) {
		this.acSelect = acSelect;
	}

	public Long getAcTotal() {
		return acTotal;
	}

	public void setAcTotal(Long acTotal) {
		this.acTotal = acTotal;
	}

    public void addAcOpen  (long valor) { acOpen   += valor; }
    public void addAcClose (long valor) { acClose  += valor; }
    public void addAcInsert(long valor) { acInsert += valor; }
    public void addAcRead  (long valor) { acRead   += valor; }
    public void addAcUpdate(long valor) { acUpdate += valor; }
    public void addAcDelete(long valor) { acDelete += valor; }
    public void addAcSelect(long valor) { acSelect += valor; }
    public void addAcTotal (long valor) { acTotal  += valor; }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((acClose == null) ? 0 : acClose.hashCode());
		result = prime * result
				+ ((acDelete == null) ? 0 : acDelete.hashCode());
		result = prime * result
				+ ((acInsert == null) ? 0 : acInsert.hashCode());
		result = prime * result + ((acOpen == null) ? 0 : acOpen.hashCode());
		result = prime * result + ((acRead == null) ? 0 : acRead.hashCode());
		result = prime * result
				+ ((acSelect == null) ? 0 : acSelect.hashCode());
		result = prime * result + ((acTotal == null) ? 0 : acTotal.hashCode());
		result = prime * result
				+ ((acUpdate == null) ? 0 : acUpdate.hashCode());
		result = prime * result
				+ ((maestro == null) ? 0 : maestro.hashCode());
		result = prime * result + ((idFile == null) ? 0 : idFile.hashCode());
		result = prime * result
				+ ((idVersion == null) ? 0 : idVersion.hashCode());
		result = prime * result
				+ ((idSesion == null) ? 0 : idSesion.hashCode());
		result = prime * result + ((orden == null) ? 0 : orden.hashCode());
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
		SESPersistencia other = (SESPersistencia) obj;
		if (acClose == null) {
			if (other.acClose != null)
				return false;
		} else if (!acClose.equals(other.acClose))
			return false;
		if (acDelete == null) {
			if (other.acDelete != null)
				return false;
		} else if (!acDelete.equals(other.acDelete))
			return false;
		if (acInsert == null) {
			if (other.acInsert != null)
				return false;
		} else if (!acInsert.equals(other.acInsert))
			return false;
		if (acOpen == null) {
			if (other.acOpen != null)
				return false;
		} else if (!acOpen.equals(other.acOpen))
			return false;
		if (acRead == null) {
			if (other.acRead != null)
				return false;
		} else if (!acRead.equals(other.acRead))
			return false;
		if (acSelect == null) {
			if (other.acSelect != null)
				return false;
		} else if (!acSelect.equals(other.acSelect))
			return false;
		if (acTotal == null) {
			if (other.acTotal != null)
				return false;
		} else if (!acTotal.equals(other.acTotal))
			return false;
		if (acUpdate == null) {
			if (other.acUpdate != null)
				return false;
		} else if (!acUpdate.equals(other.acUpdate))
			return false;
		if (maestro == null) {
			if (other.maestro != null)
				return false;
		} else if (!maestro.equals(other.maestro))
			return false;
		if (idFile == null) {
			if (other.idFile != null)
				return false;
		} else if (!idFile.equals(other.idFile))
			return false;
		if (idVersion == null) {
			if (other.idVersion != null)
				return false;
		} else if (!idVersion.equals(other.idVersion))
			return false;
		if (idSesion == null) {
			if (other.idSesion != null)
				return false;
		} else if (!idSesion.equals(other.idSesion))
			return false;
		if (orden == null) {
			if (other.orden != null)
				return false;
		} else if (!orden.equals(other.orden))
			return false;
		return true;
	}

	
}
