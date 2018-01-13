//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.01.12 a las 11:49:12 AM CET 
//


package com.jgg.sdp.loader.jaxb.rules;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para sampleType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="sampleType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="description" type="{http://www.sdp.com/SDPRules}textType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="correct" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sampleType", propOrder = {
    "description",
    "correct",
    "bad"
})
public class SampleType {

    protected List<TextType> description;
    protected String correct;
    protected String bad;

    /**
     * Gets the value of the description property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the description property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDescription().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TextType }
     * 
     * 
     */
    public List<TextType> getDescription() {
        if (description == null) {
            description = new ArrayList<TextType>();
        }
        return this.description;
    }

    /**
     * Obtiene el valor de la propiedad correct.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCorrect() {
        return correct;
    }

    /**
     * Define el valor de la propiedad correct.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCorrect(String value) {
        this.correct = value;
    }

    /**
     * Obtiene el valor de la propiedad bad.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBad() {
        return bad;
    }

    /**
     * Define el valor de la propiedad bad.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBad(String value) {
        this.bad = value;
    }

}
