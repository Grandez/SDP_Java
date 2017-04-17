package com.jgg.sdp.domain.session;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name="SES_IO")

@NamedQueries({
	@NamedQuery( name="SESIO.find"
			    ,query="SELECT s FROM SESIO s WHERE s.idSesion = ?1 AND s.idVersion = ?2")
   ,@NamedQuery( name="SESIO.findBySesion"
                ,query="SELECT s FROM SESIO s WHERE s.idSesion = ?1")
   ,@NamedQuery( name="SESIO.sumModulo"
                ,query="SELECT COUNT(s.idVersion), SUM(s.acOpen), SUM(s.acClose),  " +
                       "       SUM(s.acInsert)   , SUM(s.acRead), SUM(s.acUpdate), " +
                       "       SUM(s.acDelete)   , SUM(s.acSelect) "                 + 
                       "       FROM SESIO s "                                        +
                       "       WHERE s.idVersion = ?1 AND s.tms > ?2")

})

public class SESIO implements Serializable {

    private static final long serialVersionUID = 12150803455548963L;

    @Id
	@Column(name="idSesion")
	String idSesion;

	@Id
    @Column(name="idVersion")
    Long idVersion;
	
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

    @Column(name="tms")
    Timestamp tms;
	
	public SESIO() {
		
	}
	public SESIO(String idSesion, Long idVersion) {
		this.idSesion  = idSesion;
		this.idVersion = idVersion;
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

	public Timestamp getTms() {
        return tms;
    }
	
    public void setTms(Timestamp tms) {
        this.tms = tms;
    }
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
                + ((idSesion == null) ? 0 : idSesion.hashCode());
        result = prime * result
                + ((idVersion == null) ? 0 : idVersion.hashCode());
        result = prime * result + ((tms == null) ? 0 : tms.hashCode());
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
        SESIO other = (SESIO) obj;
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
        if (idSesion == null) {
            if (other.idSesion != null)
                return false;
        } else if (!idSesion.equals(other.idSesion))
            return false;
        if (idVersion == null) {
            if (other.idVersion != null)
                return false;
        } else if (!idVersion.equals(other.idVersion))
            return false;
        if (tms == null) {
            if (other.tms != null)
                return false;
        } else if (!tms.equals(other.tms))
            return false;
        return true;
    }
    
}
