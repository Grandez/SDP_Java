//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2017.12.02 a las 09:47:50 PM CET 
//


package com.jgg.sdp.loader.jaxb.rules;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para operatorTypeU.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * <pre>
 * &lt;simpleType name="operatorTypeU">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="MANDATORY"/>
 *     &lt;enumeration value="EQUAL"/>
 *     &lt;enumeration value="EQ"/>
 *     &lt;enumeration value="IS"/>
 *     &lt;enumeration value="GT"/>
 *     &lt;enumeration value="LT"/>
 *     &lt;enumeration value="GE"/>
 *     &lt;enumeration value="LE"/>
 *     &lt;enumeration value="START"/>
 *     &lt;enumeration value="END"/>
 *     &lt;enumeration value="CONTAINS"/>
 *     &lt;enumeration value="MATCH"/>
 *     &lt;enumeration value="HAS"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "operatorTypeU")
@XmlEnum
public enum OperatorTypeU {

    MANDATORY,
    EQUAL,
    EQ,
    IS,
    GT,
    LT,
    GE,
    LE,
    START,
    END,
    CONTAINS,
    MATCH,
    HAS;

    public String value() {
        return name();
    }

    public static OperatorTypeU fromValue(String v) {
        return valueOf(v);
    }

}
