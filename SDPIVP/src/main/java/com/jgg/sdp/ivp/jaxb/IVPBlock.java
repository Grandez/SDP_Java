//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2017.11.25 a las 11:37:27 AM CET 
//


package com.jgg.sdp.ivp.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para IVPBlock complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="IVPBlock">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="preProcess" type="{http://www.sdp.com/SDPIVP}IVPCommands" minOccurs="0"/>
 *         &lt;element name="postProcess" type="{http://www.sdp.com/SDPIVP}IVPCommands" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IVPBlock", namespace = "http://www.sdp.com/SDPIVP", propOrder = {
    "preProcess",
    "postProcess"
})
public class IVPBlock {

    protected IVPCommands preProcess;
    protected IVPCommands postProcess;
    @XmlAttribute(name = "id", required = true)
    protected int id;

    /**
     * Obtiene el valor de la propiedad preProcess.
     * 
     * @return
     *     possible object is
     *     {@link IVPCommands }
     *     
     */
    public IVPCommands getPreProcess() {
        return preProcess;
    }

    /**
     * Define el valor de la propiedad preProcess.
     * 
     * @param value
     *     allowed object is
     *     {@link IVPCommands }
     *     
     */
    public void setPreProcess(IVPCommands value) {
        this.preProcess = value;
    }

    /**
     * Obtiene el valor de la propiedad postProcess.
     * 
     * @return
     *     possible object is
     *     {@link IVPCommands }
     *     
     */
    public IVPCommands getPostProcess() {
        return postProcess;
    }

    /**
     * Define el valor de la propiedad postProcess.
     * 
     * @param value
     *     allowed object is
     *     {@link IVPCommands }
     *     
     */
    public void setPostProcess(IVPCommands value) {
        this.postProcess = value;
    }

    /**
     * Obtiene el valor de la propiedad id.
     * 
     */
    public int getId() {
        return id;
    }

    /**
     * Define el valor de la propiedad id.
     * 
     */
    public void setId(int value) {
        this.id = value;
    }

}
