//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.01.12 a las 11:49:12 AM CET 
//


package com.jgg.sdp.loader.jaxb.rules;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 *         &lt;element name="idRule" type="{http://www.sdp.com/SDPRules}idType" minOccurs="0"/>
 *         &lt;element name="idItem" type="{http://www.sdp.com/SDPRules}idType" minOccurs="0"/>
 *         &lt;element name="idGroup" type="{http://www.sdp.com/SDPRules}idType" minOccurs="0"/>
 *         &lt;element name="priority" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="severity" type="{http://www.sdp.com/SDPRules}severityType"/>
 *         &lt;element name="active" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element ref="{http://www.sdp.com/SDPRules}message" minOccurs="0"/>
 *         &lt;element ref="{http://www.sdp.com/SDPRules}description" minOccurs="0"/>
 *         &lt;choice minOccurs="0">
 *           &lt;element name="onCondition" type="{http://www.sdp.com/SDPRules}conditionType"/>
 *           &lt;element name="onConditions" type="{http://www.sdp.com/SDPRules}conditionTypeList"/>
 *         &lt;/choice>
 *         &lt;element name="condition" type="{http://www.sdp.com/SDPRules}conditionType" minOccurs="0"/>
 *         &lt;element name="sample" type="{http://www.sdp.com/SDPRules}sampleType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" type="{http://www.sdp.com/SDPRules}nameType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "idRule",
    "idItem",
    "idGroup",
    "priority",
    "severity",
    "active",
    "message",
    "description",
    "onCondition",
    "onConditions",
    "condition",
    "sample"
})
@XmlRootElement(name = "rule")
public class Rule {

    protected Long idRule;
    protected Long idItem;
    protected Long idGroup;
    @XmlElement(defaultValue = "0")
    protected Integer priority;
    @XmlSchemaType(name = "integer")
    protected int severity;
    @XmlElement(defaultValue = "true")
    protected Boolean active;
    @XmlElement(namespace = "http://www.sdp.com/SDPRules")
    protected Message message;
    @XmlElement(namespace = "http://www.sdp.com/SDPRules")
    protected Description description;
    protected ConditionType onCondition;
    protected ConditionTypeList onConditions;
    protected ConditionType condition;
    protected SampleType sample;
    @XmlAttribute(name = "name")
    protected String name;

    /**
     * Obtiene el valor de la propiedad idRule.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIdRule() {
        return idRule;
    }

    /**
     * Define el valor de la propiedad idRule.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIdRule(Long value) {
        this.idRule = value;
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
     * Obtiene el valor de la propiedad active.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isActive() {
        return active;
    }

    /**
     * Define el valor de la propiedad active.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setActive(Boolean value) {
        this.active = value;
    }

    /**
     * Obtiene el valor de la propiedad message.
     * 
     * @return
     *     possible object is
     *     {@link Message }
     *     
     */
    public Message getMessage() {
        return message;
    }

    /**
     * Define el valor de la propiedad message.
     * 
     * @param value
     *     allowed object is
     *     {@link Message }
     *     
     */
    public void setMessage(Message value) {
        this.message = value;
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
     * Obtiene el valor de la propiedad onCondition.
     * 
     * @return
     *     possible object is
     *     {@link ConditionType }
     *     
     */
    public ConditionType getOnCondition() {
        return onCondition;
    }

    /**
     * Define el valor de la propiedad onCondition.
     * 
     * @param value
     *     allowed object is
     *     {@link ConditionType }
     *     
     */
    public void setOnCondition(ConditionType value) {
        this.onCondition = value;
    }

    /**
     * Obtiene el valor de la propiedad onConditions.
     * 
     * @return
     *     possible object is
     *     {@link ConditionTypeList }
     *     
     */
    public ConditionTypeList getOnConditions() {
        return onConditions;
    }

    /**
     * Define el valor de la propiedad onConditions.
     * 
     * @param value
     *     allowed object is
     *     {@link ConditionTypeList }
     *     
     */
    public void setOnConditions(ConditionTypeList value) {
        this.onConditions = value;
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
     * Obtiene el valor de la propiedad sample.
     * 
     * @return
     *     possible object is
     *     {@link SampleType }
     *     
     */
    public SampleType getSample() {
        return sample;
    }

    /**
     * Define el valor de la propiedad sample.
     * 
     * @param value
     *     allowed object is
     *     {@link SampleType }
     *     
     */
    public void setSample(SampleType value) {
        this.sample = value;
    }

    /**
     * Obtiene el valor de la propiedad name.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Define el valor de la propiedad name.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

}
