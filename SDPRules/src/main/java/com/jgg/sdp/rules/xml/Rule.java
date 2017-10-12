//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2017.10.12 a las 04:30:22 PM CEST 
//


package com.jgg.sdp.rules.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idGroup" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="idItem" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="active" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="priority" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="severity" type="{http://www.sdp.com/SDPRULES}severityType"/>
 *         &lt;element ref="{http://www.sdp.com/SDPRULES}description" minOccurs="0"/>
 *         &lt;element name="ruleCondition" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;choice>
 *                   &lt;element name="conditionPositive" type="{http://www.sdp.com/SDPRULES}conditionType"/>
 *                   &lt;element name="conditionNegative" type="{http://www.sdp.com/SDPRULES}conditionType"/>
 *                 &lt;/choice>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;choice>
 *           &lt;element name="condition" type="{http://www.sdp.com/SDPRULES}conditionType"/>
 *           &lt;element name="conditionPositive" type="{http://www.sdp.com/SDPRULES}conditionType"/>
 *           &lt;element name="conditionNegative" type="{http://www.sdp.com/SDPRULES}conditionType"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "idGroup",
    "idItem",
    "id",
    "active",
    "priority",
    "severity",
    "description",
    "ruleCondition",
    "condition",
    "conditionPositive",
    "conditionNegative"
})
@XmlRootElement(name = "rule")
public class Rule {

    protected Long idGroup;
    protected Long idItem;
    protected Long id;
    @XmlElement(defaultValue = "true")
    protected boolean active;
    @XmlElement(defaultValue = "0")
    protected Integer priority;
    @XmlSchemaType(name = "integer")
    protected int severity;
    @XmlElement(namespace = "http://www.sdp.com/SDPRULES")
    protected Description description;
    protected Rule.RuleCondition ruleCondition;
    protected ConditionType condition;
    protected ConditionType conditionPositive;
    protected ConditionType conditionNegative;

    /**
     * Obtiene el valor de la propiedad idGroup.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIdGroup() {
        return idGroup;
    }

    /**
     * Define el valor de la propiedad idGroup.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIdGroup(Long value) {
        this.idGroup = value;
    }

    /**
     * Obtiene el valor de la propiedad idItem.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIdItem() {
        return idItem;
    }

    /**
     * Define el valor de la propiedad idItem.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIdItem(Long value) {
        this.idItem = value;
    }

    /**
     * Obtiene el valor de la propiedad id.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getId() {
        return id;
    }

    /**
     * Define el valor de la propiedad id.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setId(Long value) {
        this.id = value;
    }

    /**
     * Obtiene el valor de la propiedad active.
     * 
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Define el valor de la propiedad active.
     * 
     */
    public void setActive(boolean value) {
        this.active = value;
    }

    /**
     * Obtiene el valor de la propiedad priority.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * Define el valor de la propiedad priority.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPriority(Integer value) {
        this.priority = value;
    }

    /**
     * Obtiene el valor de la propiedad severity.
     * 
     */
    public int getSeverity() {
        return severity;
    }

    /**
     * Define el valor de la propiedad severity.
     * 
     */
    public void setSeverity(int value) {
        this.severity = value;
    }

    /**
     * Obtiene el valor de la propiedad description.
     * 
     * @return
     *     possible object is
     *     {@link Description }
     *     
     */
    public Description getDescription() {
        return description;
    }

    /**
     * Define el valor de la propiedad description.
     * 
     * @param value
     *     allowed object is
     *     {@link Description }
     *     
     */
    public void setDescription(Description value) {
        this.description = value;
    }

    /**
     * Obtiene el valor de la propiedad ruleCondition.
     * 
     * @return
     *     possible object is
     *     {@link Rule.RuleCondition }
     *     
     */
    public Rule.RuleCondition getRuleCondition() {
        return ruleCondition;
    }

    /**
     * Define el valor de la propiedad ruleCondition.
     * 
     * @param value
     *     allowed object is
     *     {@link Rule.RuleCondition }
     *     
     */
    public void setRuleCondition(Rule.RuleCondition value) {
        this.ruleCondition = value;
    }

    /**
     * Obtiene el valor de la propiedad condition.
     * 
     * @return
     *     possible object is
     *     {@link ConditionType }
     *     
     */
    public ConditionType getCondition() {
        return condition;
    }

    /**
     * Define el valor de la propiedad condition.
     * 
     * @param value
     *     allowed object is
     *     {@link ConditionType }
     *     
     */
    public void setCondition(ConditionType value) {
        this.condition = value;
    }

    /**
     * Obtiene el valor de la propiedad conditionPositive.
     * 
     * @return
     *     possible object is
     *     {@link ConditionType }
     *     
     */
    public ConditionType getConditionPositive() {
        return conditionPositive;
    }

    /**
     * Define el valor de la propiedad conditionPositive.
     * 
     * @param value
     *     allowed object is
     *     {@link ConditionType }
     *     
     */
    public void setConditionPositive(ConditionType value) {
        this.conditionPositive = value;
    }

    /**
     * Obtiene el valor de la propiedad conditionNegative.
     * 
     * @return
     *     possible object is
     *     {@link ConditionType }
     *     
     */
    public ConditionType getConditionNegative() {
        return conditionNegative;
    }

    /**
     * Define el valor de la propiedad conditionNegative.
     * 
     * @param value
     *     allowed object is
     *     {@link ConditionType }
     *     
     */
    public void setConditionNegative(ConditionType value) {
        this.conditionNegative = value;
    }


    /**
     * <p>Clase Java para anonymous complex type.
     * 
     * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;choice>
     *         &lt;element name="conditionPositive" type="{http://www.sdp.com/SDPRULES}conditionType"/>
     *         &lt;element name="conditionNegative" type="{http://www.sdp.com/SDPRULES}conditionType"/>
     *       &lt;/choice>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "conditionPositive",
        "conditionNegative"
    })
    public static class RuleCondition {

        protected ConditionType conditionPositive;
        protected ConditionType conditionNegative;

        /**
         * Obtiene el valor de la propiedad conditionPositive.
         * 
         * @return
         *     possible object is
         *     {@link ConditionType }
         *     
         */
        public ConditionType getConditionPositive() {
            return conditionPositive;
        }

        /**
         * Define el valor de la propiedad conditionPositive.
         * 
         * @param value
         *     allowed object is
         *     {@link ConditionType }
         *     
         */
        public void setConditionPositive(ConditionType value) {
            this.conditionPositive = value;
        }

        /**
         * Obtiene el valor de la propiedad conditionNegative.
         * 
         * @return
         *     possible object is
         *     {@link ConditionType }
         *     
         */
        public ConditionType getConditionNegative() {
            return conditionNegative;
        }

        /**
         * Define el valor de la propiedad conditionNegative.
         * 
         * @param value
         *     allowed object is
         *     {@link ConditionType }
         *     
         */
        public void setConditionNegative(ConditionType value) {
            this.conditionNegative = value;
        }

    }

}
