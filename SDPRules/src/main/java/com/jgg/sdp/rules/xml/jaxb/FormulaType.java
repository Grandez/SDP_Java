//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2017.10.16 a las 01:07:34 PM CEST 
//


package com.jgg.sdp.rules.xml.jaxb;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para formulaType.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * <pre>
 * &lt;simpleType name="formulaType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="function"/>
 *     &lt;enumeration value="formula"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "formulaType")
@XmlEnum
public enum FormulaType {

    @XmlEnumValue("function")
    FUNCTION("function"),
    @XmlEnumValue("formula")
    FORMULA("formula");
    private final String value;

    FormulaType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static FormulaType fromValue(String v) {
        for (FormulaType c: FormulaType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
