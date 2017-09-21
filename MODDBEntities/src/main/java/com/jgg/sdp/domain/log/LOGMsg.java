package com.jgg.sdp.domain.log;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="LOG_MSG")
@NamedQueries({
    @NamedQuery(name="LOGMsg.find",
                query="SELECT l FROM LOGMsg l WHERE l.idMsg = ?1 AND l.lang = ?2")
})
public class LOGMsg implements Serializable {

    private static final long serialVersionUID = 4376082122205584098L;

    @Id
    @Column( name = "idMsg" )
    Integer idMsg;

    @Id
    @Column( name = "lang" )
    String lang;

    @Column( name = "msg" )
    String msg;

    public Integer getIdMsg() {
        return idMsg;
    }

    public void setIdMsg(Integer idMsg) {
        this.idMsg = idMsg;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idMsg == null) ? 0 : idMsg.hashCode());
        result = prime * result + ((lang == null) ? 0 : lang.hashCode());
        result = prime * result + ((msg == null) ? 0 : msg.hashCode());
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
        LOGMsg other = (LOGMsg) obj;
        if (idMsg == null) {
            if (other.idMsg != null)
                return false;
        } else if (!idMsg.equals(other.idMsg))
            return false;
        if (lang == null) {
            if (other.lang != null)
                return false;
        } else if (!lang.equals(other.lang))
            return false;
        if (msg == null) {
            if (other.msg != null)
                return false;
        } else if (!msg.equals(other.msg))
            return false;
        return true;
    }
 
    
}   