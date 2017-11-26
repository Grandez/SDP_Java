//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2017.11.25 a las 11:37:27 AM CET 
//


package com.jgg.sdp.ivp.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para IVPComponent complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="IVPComponent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="object" type="{http://www.sdp.com/SDPIVP}IVPObjectName"/>
 *         &lt;element name="method" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="parameters" type="{http://www.sdp.com/SDPIVP}IVPParameters" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IVPComponent", namespace = "http://www.sdp.com/SDPIVP", propOrder = {
    "object",
    "method",
    "parameters"
})
public class IVPComponent {

    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected IVPObjectName object;
    @XmlElement(required = true)
    protected String method;
    protected IVPParameters parameters;

    /**
     * Obtiene el valor de la propiedad object.
     * 
     * @return
     *     possible object is
     *     {@link IVPObjectName }
     *     
     */
    public IVPObjectName getObject() {
        return object;
    }

    /**
     * Define el valor de la propiedad object.
     * 
     * @param value
     *     allowed object is
     *     {@link IVPObjectName }
     *     
     */
    public void setObject(IVPObjectName value) {
        this.object = value;
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
     * Obtiene el valor de la propiedad parameters.
     * 
     * @return
     *     possible object is
     *     {@link IVPParameters }
     *     
     */
    public IVPParameters getParameters() {
        return parameters;
    }

    /**
     * Define el valor de la propiedad parameters.
     * 
     * @param value
     *     allowed object is
     *     {@link IVPParameters }
     *     
     */
    public void setParameters(IVPParameters value) {
        this.parameters = value;
    }

}
