//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2017.12.02 a las 09:47:50 PM CET 
//


package com.jgg.sdp.loader.jaxb.rules;

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
 *       &lt;sequence>
 *         &lt;choice minOccurs="0">
 *           &lt;element name="lvalue" type="{http://www.sdp.com/SDPRules}operandType"/>
 *           &lt;element name="script" type="{http://www.sdp.com/SDPRules}scriptType"/>
 *           &lt;element name="type" type="{http://www.sdp.com/SDPRules}lvalueType"/>
 *           &lt;element name="configuration" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;/choice>
 *         &lt;element name="operator" type="{http://www.sdp.com/SDPRules}operatorType" minOccurs="0"/>
 *         &lt;choice minOccurs="0">
 *           &lt;element name="property" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="attribute" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="method" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="expression" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="rvalue" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="rscript" type="{http://www.sdp.com/SDPRules}scriptType"/>
 *         &lt;/choice>
 *       &lt;/sequence>
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
    "lvalue",
    "script",
    "type",
    "configuration",
    "operator",
    "property",
    "attribute",
    "method",
    "expression",
    "value",
    "rvalue",
    "rscript"
})
public class ConditionType {

    protected OperandType lvalue;
    protected ScriptType script;
    @XmlSchemaType(name = "string")
    protected LvalueType type;
    protected String configuration;
    @XmlSchemaType(name = "anySimpleType")
    protected String operator;
    protected String property;
    protected String attribute;
    protected String method;
    protected String expression;
    protected String value;
    protected String rvalue;
    protected ScriptType rscript;
    @XmlAttribute(name = "negated")
    protected Boolean negated;

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
     * Obtiene el valor de la propiedad type.
     * 
     * @return
     *     possible object is
     *     {@link LvalueType }
     *     
     */
    public LvalueType getType() {
        return type;
    }

    /**
     * Define el valor de la propiedad type.
     * 
     * @param value
     *     allowed object is
     *     {@link LvalueType }
     *     
     */
    public void setType(LvalueType value) {
        this.type = value;
    }

    /**
     * Obtiene el valor de la propiedad configuration.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConfiguration() {
        return configuration;
    }

    /**
     * Define el valor de la propiedad configuration.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConfiguration(String value) {
        this.configuration = value;
    }

    /**
     * Obtiene el valor de la propiedad operator.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperator() {
        return operator;
    }

    /**
     * Define el valor de la propiedad operator.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperator(String value) {
        this.operator = value;
    }

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
     *     {@link String }
     *     
     */
    public String getExpression() {
        return expression;
    }

    /**
     * Define el valor de la propiedad expression.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpression(String value) {
        this.expression = value;
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
     * Obtiene el valor de la propiedad rvalue.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRvalue() {
        return rvalue;
    }

    /**
     * Define el valor de la propiedad rvalue.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRvalue(String value) {
        this.rvalue = value;
    }

    /**
     * Obtiene el valor de la propiedad rscript.
     * 
     * @return
     *     possible object is
     *     {@link ScriptType }
     *     
     */
    public ScriptType getRscript() {
        return rscript;
    }

    /**
     * Define el valor de la propiedad rscript.
     * 
     * @param value
     *     allowed object is
     *     {@link ScriptType }
     *     
     */
    public void setRscript(ScriptType value) {
        this.rscript = value;
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
