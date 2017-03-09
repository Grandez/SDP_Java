package com.jgg.sdp.domain.log;

import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name="LOG_LOGGING")
@NamedQueries({
     @NamedQuery(name="LOGLogging.list",
                 query="SELECT l FROM LOGLogging l WHERE l.tms > ?1")
    ,@NamedQuery(name="LOGLogging.listByCode",
                 query="SELECT l FROM LOGLogging l WHERE l.idMsg = ?1 AND l.tms > ?2")
    ,@NamedQuery(name="LOGLogging.listByType",
                 query="SELECT l FROM LOGLogging l WHERE l.tms > ?1 AND l.idTipo = ?2")

})
public class LOGLogging {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "secuencia" )
    Long secuencia;

    @Column(name="idAppl")
    Long idAppl;

    @Column(name="idModulo")
    Long idModulo;
    
    @Column(name="idVersion")
    Long idVersion;
    
    @Column(name="idTipo")
    Integer idTipo;

    @Column(name="idMsg")
    Integer idMsg;

    @Column(name="Objeto")
    String objeto;
    
    @Column(name="uid")
    String uid;

    @Column(name="tms")
    Timestamp tms;
    
    @Column(name="dato0")
    String dato0;

    @Column(name="dato1")
    String dato1;

    @Column(name="dato2")
    String dato2;

    @Column(name="dato3")
    String dato3;

    @Column(name="dato4")
    String dato4;

    @Column(name="dato5")
    String dato5;
    
    @Column(name="dato6")
    String dato6;

    @Column(name="dato7")
    String dato7;
    
    @Column(name="dato8")
    String dato8;

    @Column(name="dato9")
    String dato9;

    public Long getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(Long secuencia) {
        this.secuencia = secuencia;
    }

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

    public Integer getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Integer idTipo) {
        this.idTipo = idTipo;
    }

    public Integer getIdMsg() {
        return idMsg;
    }

    public void setIdMsg(Integer idMsg) {
        this.idMsg = idMsg;
    }

    
    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
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

    public String getDato0() {
        return dato0;
    }

    public void setDato0(String dato0) {
        this.dato0 = dato0;
    }

    public String getDato1() {
        return dato1;
    }

    public void setDato1(String dato1) {
        this.dato1 = dato1;
    }

    public String getDato2() {
        return dato2;
    }

    public void setDato2(String dato2) {
        this.dato2 = dato2;
    }

    public String getDato3() {
        return dato3;
    }

    public void setDato3(String dato3) {
        this.dato3 = dato3;
    }

    public String getDato4() {
        return dato4;
    }

    public void setDato4(String dato4) {
        this.dato4 = dato4;
    }

    public String getDato5() {
        return dato5;
    }

    public void setDato5(String dato5) {
        this.dato5 = dato5;
    }

    public String getDato6() {
        return dato6;
    }

    public void setDato6(String dato6) {
        this.dato6 = dato6;
    }

    public String getDato7() {
        return dato7;
    }

    public void setDato7(String dato7) {
        this.dato7 = dato7;
    }

    public String getDato8() {
        return dato8;
    }

    public void setDato8(String dato8) {
        this.dato8 = dato8;
    }

    public String getDato9() {
        return dato9;
    }

    public void setDato9(String dato9) {
        this.dato9 = dato9;
    }

    public void setDato(int index, String dato) {
        switch (index) {
           case 0: setDato0(dato); break;
           case 1: setDato1(dato); break;
           case 2: setDato2(dato); break;
           case 3: setDato3(dato); break;
           case 4: setDato4(dato); break;
           case 5: setDato5(dato); break;
           case 6: setDato6(dato); break;
           case 7: setDato7(dato); break;
           case 8: setDato8(dato); break;
           case 9: setDato9(dato); break;
        }
    }
    
    public String getDato(int index) {
        switch (index) {
           case 0: return getDato0(); 
           case 1: return getDato1(); 
           case 2: return getDato2(); 
           case 3: return getDato3(); 
           case 4: return getDato4(); 
           case 5: return getDato5(); 
           case 6: return getDato6(); 
           case 7: return getDato7(); 
           case 8: return getDato8(); 
           case 9: return getDato9(); 
        }
        return "";
    }

}
