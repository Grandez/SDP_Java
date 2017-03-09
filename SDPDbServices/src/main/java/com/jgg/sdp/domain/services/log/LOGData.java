/**
 * Clase auxiliar para los servicios de acceso a Log
 * <br>
 * El acceso al log se realiza mediante dos servicios:<br>
 * <ul>
 *    <li> Servicios de escritura</li>
 *    <li> Servicios de lectura</li>
 * </ul>
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.domain.services.log;

import java.sql.Timestamp;

public class LOGData {
    
    private Long idAppl;
    private Long idModulo;
    private Long idVersion;
    private String nombre;
    private String uid;
    private Timestamp tms;

    private Object[] data = new Object[10];
    
    public Long getIdAppl() {
        return idAppl;
    }
    public void setIdAppl(Long idAppl) {
        this.idAppl = idAppl;
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
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public Timestamp getTms() {
        return tms;
    }
    public void setTms(Timestamp tms) {
        this.tms = tms;
    }
    
    public void setParm(int index, Object value) {
        data[index] = value;
    }
    
    public String getParm(int index) {
        if (data[index] == null) return "";
        if (data[index] instanceof Long)    return ((Long)    data[index]).toString();
        if (data[index] instanceof Integer) return ((Integer) data[index]).toString();
        if (data[index] instanceof String)  return  (String)  data[index];
        return data[index].toString();
    }

}
