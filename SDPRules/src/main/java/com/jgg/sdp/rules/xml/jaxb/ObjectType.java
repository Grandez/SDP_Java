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
 * <p>Clase Java para objectType.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * <pre>
 * &lt;simpleType name="objectType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="string"/>
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
@XmlType(name = "objectType")
@XmlEnum
public enum ObjectType {

    @XmlEnumValue("string")
    STRING("string"),
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

    ObjectType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ObjectType fromValue(String v) {
        for (ObjectType c: ObjectType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
