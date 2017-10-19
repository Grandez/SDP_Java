//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2017.10.16 a las 01:07:34 PM CEST 
//


package com.jgg.sdp.rules.xml.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para conditionType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="conditionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element name="objectType" type="{http://www.sdp.com/SDPRules}objectType"/>
 *           &lt;element name="method" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="expression" type="{http://www.sdp.com/SDPRules}expressionType"/>
 *         &lt;/choice>
 *         &lt;element name="comparator" type="{http://www.sdp.com/SDPRules}comparatorType"/>
 *         &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *       &lt;attribute name="negated" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "conditionType", propOrder = {
    "objectType",
    "method",
    "expression",
    "comparator",
    "value"
})
public class ConditionType {

    @XmlSchemaType(name = "string")
    protected ObjectType objectType;
    protected String method;
    protected ExpressionType expression;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected ComparatorType comparator;
    @XmlElement(required = true)
    protected String value;
    @XmlAttribute(name = "negated")
    protected Boolean negated;

    /**
     * Obtiene el valor de la propiedad objectType.
     * 
     * @return
     *     possible object is
     *     {@link ObjectType }
     *     
     */
    public ObjectType getObjectType() {
        return objectType;
    }

    /**
     * Define el valor de la propiedad objectType.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectType }
     *     
     */
    public void setObjectType(ObjectType value) {
        this.objectType = value;
    }

    /**
     * Obtiene el valor de la propiedad method.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMethod() {
        return method;
    }

    /**
     * Define el valor de la propiedad method.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMethod(String value) {
        this.method = value;
    }

    /**
     * Obtiene el valor de la propiedad expression.
     * 
     * @return
     *     possible object is
     *     {@link ExpressionType }
     *     
     */
    public ExpressionType getExpression() {
        return expression;
    }

    /**
     * Define el valor de la propiedad expression.
     * 
     * @param value
     *     allowed object is
     *     {@link ExpressionType }
     *     
     */
    public void setExpression(ExpressionType value) {
        this.expression = value;
    }

    /**
     * Obtiene el valor de la propiedad comparator.
     * 
     * @return
     *     possible object is
     *     {@link ComparatorType }
     *     
     */
    public ComparatorType getComparator() {
        return comparator;
    }

    /**
     * Define el valor de la propiedad comparator.
     * 
     * @param value
     *     allowed object is
     *     {@link ComparatorType }
     *     
     */
    public void setComparator(ComparatorType value) {
        this.comparator = value;
    }

    /**
     * Obtiene el valor de la propiedad value.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Define el valor de la propiedad value.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Obtiene el valor de la propiedad negated.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isNegated() {
        return negated;
    }

    /**
     * Define el valor de la propiedad negated.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNegated(Boolean value) {
        this.negated = value;
    }

}
