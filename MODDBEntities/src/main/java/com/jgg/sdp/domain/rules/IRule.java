package com.jgg.sdp.domain.rules;

import java.sql.Timestamp;

public interface IRule {

	public Long       getIdGroup();
	public Long       getIdDesc();
	public Long       getIdTitle();
	public Long       getIdSample();
	public Long       getIdMsg();
	public String     getName();
	public String     getPrefix();
    public Long       getActive();
    public String     getUid();
    public Timestamp  getTms();
}
