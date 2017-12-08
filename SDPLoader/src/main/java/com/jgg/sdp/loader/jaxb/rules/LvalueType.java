//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2017.12.02 a las 09:47:50 PM CET 
//


package com.jgg.sdp.loader.jaxb.rules;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para lvalueType.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * <pre>
 * &lt;simpleType name="lvalueType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="string"/>
 *     &lt;enumeration value="integer"/>
 *     &lt;enumeration value="long"/>
 *     &lt;enumeration value="decimal"/>
 *     &lt;enumeration value="boolean"/>
 *     &lt;enumeration value="date"/>
 *     &lt;enumeration value="time"/>
 *     &lt;enumeration value="timestamp"/>
 *     &lt;enumeration value="verb"/>
 *     &lt;enumeration value="option"/>
 *     &lt;enumeration value="lvalue"/>
 *     &lt;enumeration value="rvalue"/>
 *     &lt;enumeration value="value"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "lvalueType")
@XmlEnum
public enum LvalueType {

    @XmlEnumValue("string")
    STRING("string"),
    @XmlEnumValue("integer")
    INTEGER("integer"),
    @XmlEnumValue("long")
    LONG("long"),
    @XmlEnumValue("decimal")
    DECIMAL("decimal"),
    @XmlEnumValue("boolean")
    BOOLEAN("boolean"),
    @XmlEnumValue("date")
    DATE("date"),
    @XmlEnumValue("time")
    TIME("time"),
    @XmlEnumValue("timestamp")
    TIMESTAMP("timestamp"),
    @XmlEnumValue("verb")
    VERB("verb"),
    @XmlEnumValue("option")
    OPTION("option"),
    @XmlEnumValue("lvalue")
    LVALUE("lvalue"),
    @XmlEnumValue("rvalue")
    RVALUE("rvalue"),
    @XmlEnumValue("value")
    VALUE("value");
    private final String value;

    LvalueType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static LvalueType fromValue(String v) {
        for (LvalueType c: LvalueType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
