//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2017.11.07 a las 07:59:34 PM CET 
//


package com.jgg.sdp.ivp.jaxb;

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
 *         &lt;element name="name" type="{http://www.sdp.com/SDPIVP}IVPModuleType"/>
 *         &lt;element name="config" type="{http://www.sdp.com/SDPIVP}IVPConfigType" minOccurs="0"/>
 *         &lt;element name="cases" type="{http://www.sdp.com/SDPIVP}IVPCasesType"/>
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
    "name",
    "config",
    "cases"
})
@XmlRootElement(name = "component")
public class Component {

    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected IVPModuleType name;
    protected IVPConfigType config;
    @XmlElement(required = true)
    protected IVPCasesType cases;

    /**
     * Obtiene el valor de la propiedad name.
     * 
     * @return
     *     possible object is
     *     {@link IVPModuleType }
     *     
     */
    public IVPModuleType getName() {
        return name;
    }

    /**
     * Define el valor de la propiedad name.
     * 
     * @param value
     *     allowed object is
     *     {@link IVPModuleType }
     *     
     */
    public void setName(IVPModuleType value) {
        this.name = value;
    }

    /**
     * Obtiene el valor de la propiedad config.
     * 
     * @return
     *     possible object is
     *     {@link IVPConfigType }
     *     
     */
    public IVPConfigType getConfig() {
        return config;
    }

    /**
     * Define el valor de la propiedad config.
     * 
     * @param value
     *     allowed object is
     *     {@link IVPConfigType }
     *     
     */
    public void setConfig(IVPConfigType value) {
        this.config = value;
    }

    /**
     * Obtiene el valor de la propiedad cases.
     * 
     * @return
     *     possible object is
     *     {@link IVPCasesType }
     *     
     */
    public IVPCasesType getCases() {
        return cases;
    }

    /**
     * Define el valor de la propiedad cases.
     * 
     * @param value
     *     allowed object is
     *     {@link IVPCasesType }
     *     
     */
    public void setCases(IVPCasesType value) {
        this.cases = value;
    }

}
