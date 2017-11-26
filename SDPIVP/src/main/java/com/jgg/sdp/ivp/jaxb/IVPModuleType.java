//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2017.11.25 a las 11:37:27 AM CET 
//


package com.jgg.sdp.ivp.jaxb;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para IVPModuleType.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * <pre>
 * &lt;simpleType name="IVPModuleType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="SDPAnalyzer"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "IVPModuleType", namespace = "http://www.sdp.com/SDPIVP")
@XmlEnum
public enum IVPModuleType {

    @XmlEnumValue("SDPAnalyzer")
    SDP_ANALYZER("SDPAnalyzer");
    private final String value;

    IVPModuleType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static IVPModuleType fromValue(String v) {
        for (IVPModuleType c: IVPModuleType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
