//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2017.12.27 a las 01:08:04 PM CET 
//


package com.jgg.sdp.loader.jaxb.rules;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
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
 *         &lt;element name="idGroup" type="{http://www.sdp.com/SDPRules}idType" minOccurs="0"/>
 *         &lt;element name="idParent" type="{http://www.sdp.com/SDPRules}idType" minOccurs="0"/>
 *         &lt;element name="prefix" type="{http://www.sdp.com/SDPRules}prefixType"/>
 *         &lt;element name="active" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element ref="{http://www.sdp.com/SDPRules}title" minOccurs="0"/>
 *         &lt;element ref="{http://www.sdp.com/SDPRules}description" minOccurs="0"/>
 *         &lt;choice minOccurs="0">
 *           &lt;element name="onCondition" type="{http://www.sdp.com/SDPRules}conditionType"/>
 *           &lt;element name="onConditions" type="{http://www.sdp.com/SDPRules}conditionTypeList"/>
 *         &lt;/choice>
 *         &lt;element name="sample" type="{http://www.sdp.com/SDPRules}sampleType" minOccurs="0"/>
 *         &lt;element ref="{http://www.sdp.com/SDPRules}item" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{http://www.sdp.com/SDPRules}nameType" />
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
    "idParent",
    "prefix",
    "active",
    "title",
    "description",
    "onCondition",
    "onConditions",
    "sample",
    "item"
})
@XmlRootElement(name = "group")
public class Group {

    protected Long idGroup;
    protected Long idParent;
    @XmlElement(required = true)
    protected String prefix;
    @XmlElement(defaultValue = "true")
    protected Boolean active;
    @XmlElement(namespace = "http://www.sdp.com/SDPRules")
    protected Title title;
    @XmlElement(namespace = "http://www.sdp.com/SDPRules")
    protected Description description;
    protected ConditionType onCondition;
    protected ConditionTypeList onConditions;
    protected SampleType sample;
    @XmlElement(namespace = "http://www.sdp.com/SDPRules")
    protected List<Item> item;
    @XmlAttribute(name = "name", required = true)
    protected String name;

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
     * Obtiene el valor de la propiedad idParent.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIdParent() {
        return idParent;
    }

    /**
     * Define el valor de la propiedad idParent.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIdParent(Long value) {
        this.idParent = value;
    }

    /**
     * Obtiene el valor de la propiedad prefix.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Define el valor de la propiedad prefix.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrefix(String value) {
        this.prefix = value;
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
     * Obtiene el valor de la propiedad title.
     * 
     * @return
     *     possible object is
     *     {@link Title }
     *     
     */
    public Title getTitle() {
        return title;
    }

    /**
     * Define el valor de la propiedad title.
     * 
     * @param value
     *     allowed object is
     *     {@link Title }
     *     
     */
    public void setTitle(Title value) {
        this.title = value;
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
     * Gets the value of the item property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the item property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Item }
     * 
     * 
     */
    public List<Item> getItem() {
        if (item == null) {
            item = new ArrayList<Item>();
        }
        return this.item;
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
