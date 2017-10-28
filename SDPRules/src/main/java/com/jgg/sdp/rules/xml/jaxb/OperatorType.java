//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2017.10.28 a las 12:35:34 PM CEST 
//


package com.jgg.sdp.rules.xml.jaxb;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para operatorType.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * <pre>
 * &lt;simpleType name="operatorType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="mandatory"/>
 *     &lt;enumeration value="equal"/>
 *     &lt;enumeration value="eq"/>
 *     &lt;enumeration value="is"/>
 *     &lt;enumeration value="gt"/>
 *     &lt;enumeration value="lt"/>
 *     &lt;enumeration value="ge"/>
 *     &lt;enumeration value="le"/>
 *     &lt;enumeration value="start"/>
 *     &lt;enumeration value="end"/>
 *     &lt;enumeration value="contains"/>
 *     &lt;enumeration value="match"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "operatorType")
@XmlEnum
public enum OperatorType {

    @XmlEnumValue("mandatory")
    MANDATORY("mandatory"),
    @XmlEnumValue("equal")
    EQUAL("equal"),
    @XmlEnumValue("eq")
    EQ("eq"),
    @XmlEnumValue("is")
    IS("is"),
    @XmlEnumValue("gt")
    GT("gt"),
    @XmlEnumValue("lt")
    LT("lt"),
    @XmlEnumValue("ge")
    GE("ge"),
    @XmlEnumValue("le")
    LE("le"),
    @XmlEnumValue("start")
    START("start"),
    @XmlEnumValue("end")
    END("end"),
    @XmlEnumValue("contains")
    CONTAINS("contains"),
    @XmlEnumValue("match")
    MATCH("match");
    private final String value;

    OperatorType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static OperatorType fromValue(String v) {
        for (OperatorType c: OperatorType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
