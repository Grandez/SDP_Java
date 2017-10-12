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
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;element name="conditionPositive" type="{http://www.sdp.com/SDPRULES}conditionType"/>
 *           &lt;element name="conditionNegative" type="{http://www.sdp.com/SDPRULES}conditionType"/>
 *         &lt;/choice>
 *         &lt;element ref="{http://www.sdp.com/SDPRULES}description" minOccurs="0"/>
 *         &lt;element ref="{http://www.sdp.com/SDPRULES}sample" minOccurs="0"/>
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
    "id",
    "conditionPositive",
    "conditionNegative",
    "description",
    "sample"
})
@XmlRootElement(name = "condition")
public class Condition {

    protected Long id;
    protected ConditionType conditionPositive;
    protected ConditionType conditionNegative;
    @XmlElement(namespace = "http://www.sdp.com/SDPRULES")
    protected Description description;
    @XmlElement(namespace = "http://www.sdp.com/SDPRULES")
    protected Sample sample;

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
     * Obtiene el valor de la propiedad sample.
     * 
     * @return
     *     possible object is
     *     {@link Sample }
     *     
     */
    public Sample getSample() {
        return sample;
    }

    /**
     * Define el valor de la propiedad sample.
     * 
     * @param value
     *     allowed object is
     *     {@link Sample }
     *     
     */
    public void setSample(Sample value) {
        this.sample = value;
    }

}
