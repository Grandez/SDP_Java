package com.jgg.sdp.domain.rules;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="RUL_SAMPLES")
public class RULSample implements Serializable {

	private static final long serialVersionUID = 1814047234974235293L;

	public static final String delSample        = "DELETE FROM RULSample s WHERE s.idSample = ?1";
	public static final String listSampleByType = "SELECT s FROM RULSample s WHERE s.idSample = ?1 AND s.type = ?2 ORDER BY s.line";
	
	@Id
	@Column(name="idSample")
	private Long idSample;

	@Id
	@Column(name="type")
	private Integer type;

	@Id
	@Column(name="line")
	private Integer line;
	
	@Column(name="data")
	private String data;

	public Long getIdSample() {
		return idSample;
	}

	public void setIdSample(Long idSample) {
		this.idSample = idSample;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getLine() {
		return line;
	}

	public void setLine(Integer line) {
		this.line = line;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((idSample == null) ? 0 : idSample.hashCode());
		result = prime * result + ((line == null) ? 0 : line.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (idSample == null) {
			if (other.idSample != null)
				return false;
		} else if (!idSample.equals(other.idSample))
			return false;
		if (line == null) {
			if (other.line != null)
				return false;
		} else if (!line.equals(other.line))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	} 

	
}
