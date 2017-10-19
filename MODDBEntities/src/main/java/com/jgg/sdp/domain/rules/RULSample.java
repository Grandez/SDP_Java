package com.jgg.sdp.domain.rules;

import java.io.Serializable;
import java.util.Arrays;

import javax.persistence.*;

@Entity
@Table(name="RUL_SAMPLE")
public class RULSample implements Serializable {

	private static final long serialVersionUID = 1096712879906298164L;

	@Id
	@Column(name="idSample")
	private Long idSample;

	@Lob
	@Column(name="dataBad")
	byte[] dataBad;

	@Lob
	@Column(name="dataGood")
	byte[] dataGood;

	public Long getIdSample() {
		return idSample;
	}

	public void setIdSample(Long idSample) {
		this.idSample = idSample;
	}

	public byte[] getDataBad() {
		return dataBad;
	}

	public void setDataBad(byte[] dataBad) {
		this.dataBad = dataBad;
	}

	public byte[] getDataGood() {
		return dataGood;
	}

	public void setDataGood(byte[] dataGood) {
		this.dataGood = dataGood;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(dataBad);
		result = prime * result + Arrays.hashCode(dataGood);
		result = prime * result + ((idSample == null) ? 0 : idSample.hashCode());
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
		RULSample other = (RULSample) obj;
		if (!Arrays.equals(dataBad, other.dataBad))
			return false;
		if (!Arrays.equals(dataGood, other.dataGood))
			return false;
		if (idSample == null) {
			if (other.idSample != null)
				return false;
		} else if (!idSample.equals(other.idSample))
			return false;
		return true;
	}

	
}
