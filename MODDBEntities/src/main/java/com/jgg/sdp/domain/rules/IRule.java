package com.jgg.sdp.domain.rules;

import java.sql.Timestamp;

public interface IRule {

	public Long       getIdDesc();
	public Long       getIdTitle();
	public Long       getIdMsg();
	public String     getName();
    public Long       getActive();
    public String     getPrefix();
    public String     getUid();
    public Timestamp  getTms();
}
