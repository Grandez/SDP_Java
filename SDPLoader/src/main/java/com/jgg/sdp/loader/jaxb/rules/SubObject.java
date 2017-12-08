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
 * <p>Clase Java para subObject.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * <pre>
 * &lt;simpleType name="subObject">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="verb"/>
 *     &lt;enumeration value="option"/>
 *     &lt;enumeration value="lvalue"/>
 *     &lt;enumeration value="rvalue"/>
 *     &lt;enumeration value="list"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "subObject")
@XmlEnum
public enum SubObject {

    @XmlEnumValue("verb")
    VERB("verb"),
    @XmlEnumValue("option")
    OPTION("option"),
    @XmlEnumValue("lvalue")
    LVALUE("lvalue"),
    @XmlEnumValue("rvalue")
    RVALUE("rvalue"),
    @XmlEnumValue("list")
    LIST("list");
    private final String value;

    SubObject(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SubObject fromValue(String v) {
        for (SubObject c: SubObject.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
