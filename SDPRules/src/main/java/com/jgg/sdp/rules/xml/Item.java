//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2017.10.12 a las 04:30:22 PM CEST 
//


package com.jgg.sdp.rules.xml;

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
 *         &lt;element name="group" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="active" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="object" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="itemCondition" minOccurs="0">
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
 *         &lt;element ref="{http://www.sdp.com/SDPRULES}description" minOccurs="0"/>
 *         &lt;element ref="{http://www.sdp.com/SDPRULES}rule" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "group",
    "id",
    "active",
    "object",
    "itemCondition",
    "description",
    "rule"
})
@XmlRootElement(name = "item")
public class Item {

    protected Long group;
    protected Long id;
    @XmlElement(defaultValue = "true")
    protected Boolean active;
    @XmlElement(required = true)
    protected String object;
    protected Item.ItemCondition itemCondition;
    @XmlElement(namespace = "http://www.sdp.com/SDPRULES")
    protected Description description;
    @XmlElement(namespace = "http://www.sdp.com/SDPRULES")
    protected List<Rule> rule;
    @XmlAttribute(name = "name", required = true)
    protected String name;

    /**
     * Obtiene el valor de la propiedad group.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getGroup() {
        return group;
    }

    /**
     * Define el valor de la propiedad group.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setGroup(Long value) {
        this.group = value;
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
     * Obtiene el valor de la propiedad object.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObject() {
        return object;
    }

    /**
     * Define el valor de la propiedad object.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObject(String value) {
        this.object = value;
    }

    /**
     * Obtiene el valor de la propiedad itemCondition.
     * 
     * @return
     *     possible object is
     *     {@link Item.ItemCondition }
     *     
     */
    public Item.ItemCondition getItemCondition() {
        return itemCondition;
    }

    /**
     * Define el valor de la propiedad itemCondition.
     * 
     * @param value
     *     allowed object is
     *     {@link Item.ItemCondition }
     *     
     */
    public void setItemCondition(Item.ItemCondition value) {
        this.itemCondition = value;
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
     * Gets the value of the rule property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rule property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRule().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Rule }
     * 
     * 
     */
    public List<Rule> getRule() {
        if (rule == null) {
            rule = new ArrayList<Rule>();
        }
        return this.rule;
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
    public static class ItemCondition {

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
