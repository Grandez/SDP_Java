//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantaci�n de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perder�n si se vuelve a compilar el esquema de origen. 
// Generado el: 2017.11.25 a las 11:37:27 AM CET 
//


package com.jgg.sdp.ivp.jaxb;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para IVPCaseLabel.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * <pre>
 * &lt;simpleType name="IVPCaseLabel">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="directory"/>
 *     &lt;enumeration value="resource"/>
 *     &lt;enumeration value="case"/>
 *     &lt;enumeration value="pattern"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "IVPCaseLabel", namespace = "http://www.sdp.com/SDPIVP")
@XmlEnum
public enum IVPCaseLabel {

    @XmlEnumValue("directory")
    DIRECTORY("directory"),
    @XmlEnumValue("resource")
    RESOURCE("resource"),
    @XmlEnumValue("case")
    CASE("case"),
    @XmlEnumValue("pattern")
    PATTERN("pattern");
    private final String value;

    IVPCaseLabel(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static IVPCaseLabel fromValue(String v) {
        for (IVPCaseLabel c: IVPCaseLabel.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
