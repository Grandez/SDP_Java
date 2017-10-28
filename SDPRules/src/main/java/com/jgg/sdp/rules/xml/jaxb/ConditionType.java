//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2017.10.28 a las 12:35:34 PM CEST 
//


package com.jgg.sdp.rules.xml.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 *       &lt;choice>
 *         &lt;element name="property" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;sequence>
 *           &lt;choice>
 *             &lt;element name="type" type="{http://www.sdp.com/SDPRules}objectType"/>
 *             &lt;element name="attribute" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *             &lt;element name="lvalue" type="{http://www.sdp.com/SDPRules}operandType"/>
 *             &lt;element name="script" type="{http://www.sdp.com/SDPRules}scriptType"/>
 *           &lt;/choice>
 *           &lt;element name="operator" type="{http://www.sdp.com/SDPRules}operatorType"/>
 *           &lt;element name="rvalue" type="{http://www.sdp.com/SDPRules}operandType"/>
 *         &lt;/sequence>
 *       &lt;/choice>
 *       &lt;attribute name="negated" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "conditionType", propOrder = {
    "property",
    "type",
    "attribute",
    "lvalue",
    "script",
    "operator",
    "rvalue"
})
public class ConditionType {

    protected String property;
    @XmlSchemaType(name = "string")
    protected ObjectType type;
    protected String attribute;
    protected OperandType lvalue;
    protected ScriptType script;
    @XmlSchemaType(name = "string")
    protected OperatorType operator;
    protected OperandType rvalue;
    @XmlAttribute(name = "negated")
    protected Boolean negated;

    /**
     * Obtiene el valor de la propiedad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProperty() {
        return property;
    }

    /**
     * Define el valor de la propiedad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProperty(String value) {
        this.property = value;
    }

    /**
     * Obtiene el valor de la propiedad type.
     * 
     * @return
     *     possible object is
     *     {@link ObjectType }
     *     
     */
    public ObjectType getType() {
        return type;
    }

    /**
     * Define el valor de la propiedad type.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectType }
     *     
     */
    public void setType(ObjectType value) {
        this.type = value;
    }

    /**
     * Obtiene el valor de la propiedad attribute.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttribute() {
        return attribute;
    }

    /**
     * Define el valor de la propiedad attribute.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttribute(String value) {
        this.attribute = value;
    }

    /**
     * Obtiene el valor de la propiedad lvalue.
     * 
     * @return
     *     possible object is
     *     {@link OperandType }
     *     
     */
    public OperandType getLvalue() {
        return lvalue;
    }

    /**
     * Define el valor de la propiedad lvalue.
     * 
     * @param value
     *     allowed object is
     *     {@link OperandType }
     *     
     */
    public void setLvalue(OperandType value) {
        this.lvalue = value;
    }

    /**
     * Obtiene el valor de la propiedad script.
     * 
     * @return
     *     possible object is
     *     {@link ScriptType }
     *     
     */
    public ScriptType getScript() {
        return script;
    }

    /**
     * Define el valor de la propiedad script.
     * 
     * @param value
     *     allowed object is
     *     {@link ScriptType }
     *     
     */
    public void setScript(ScriptType value) {
        this.script = value;
    }

    /**
     * Obtiene el valor de la propiedad operator.
     * 
     * @return
     *     possible object is
     *     {@link OperatorType }
     *     
     */
    public OperatorType getOperator() {
        return operator;
    }

    /**
     * Define el valor de la propiedad operator.
     * 
     * @param value
     *     allowed object is
     *     {@link OperatorType }
     *     
     */
    public void setOperator(OperatorType value) {
        this.operator = value;
    }

    /**
     * Obtiene el valor de la propiedad rvalue.
     * 
     * @return
     *     possible object is
     *     {@link OperandType }
     *     
     */
    public OperandType getRvalue() {
        return rvalue;
    }

    /**
     * Define el valor de la propiedad rvalue.
     * 
     * @param value
     *     allowed object is
     *     {@link OperandType }
     *     
     */
    public void setRvalue(OperandType value) {
        this.rvalue = value;
    }

    /**
     * Obtiene el valor de la propiedad negated.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isNegated() {
        if (negated == null) {
            return false;
        } else {
            return negated;
        }
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
