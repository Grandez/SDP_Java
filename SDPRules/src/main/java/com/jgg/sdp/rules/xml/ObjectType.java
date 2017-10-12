//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2017.10.12 a las 04:30:22 PM CEST 
//


package com.jgg.sdp.rules.xml;

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
 *     &lt;enumeration value="verb"/>
 *     &lt;enumeration value="word"/>
 *     &lt;enumeration value="option"/>
 *     &lt;enumeration value="lvalue"/>
 *     &lt;enumeration value="rvalue"/>
 *     &lt;enumeration value="method"/>
 *     &lt;enumeration value="expression"/>
 *     &lt;enumeration value="value"/>
 *     &lt;enumeration value="special"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "objectType")
@XmlEnum
public enum ObjectType {

    @XmlEnumValue("verb")
    VERB("verb"),
    @XmlEnumValue("word")
    WORD("word"),
    @XmlEnumValue("option")
    OPTION("option"),
    @XmlEnumValue("lvalue")
    LVALUE("lvalue"),
    @XmlEnumValue("rvalue")
    RVALUE("rvalue"),
    @XmlEnumValue("method")
    METHOD("method"),
    @XmlEnumValue("expression")
    EXPRESSION("expression"),
    @XmlEnumValue("value")
    VALUE("value"),
    @XmlEnumValue("special")
    SPECIAL("special");
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
