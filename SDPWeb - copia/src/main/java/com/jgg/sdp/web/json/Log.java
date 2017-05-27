/*
 * 
 */
package com.jgg.sdp.web.json;

import com.jgg.sdp.domain.log.LOGLogging;

public class Log {

    private String msg;
    private String codigo;
    
    private LOGLogging log;
    
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public LOGLogging getLog() {
        return log;
    }
    public void setLog(LOGLogging log) {
        this.log = log;
    }
    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    
    
}
