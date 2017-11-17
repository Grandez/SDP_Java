//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2017.11.07 a las 07:59:34 PM CET 
//


package com.jgg.sdp.ivp.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *              Set the default information for COBOL modules
 *              baseDir: Default directory for all
 *              workingDir: Working directory
 *              binDir: Directory for executables / scripts
 *              scriptDir: 
 *          
 * 
 * <p>Clase Java para IVPConfigType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="IVPConfigType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="baseDir" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;element name="workingDir" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *           &lt;element name="binDir" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *           &lt;element name="cobolDir" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
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
@XmlType(name = "IVPConfigType", propOrder = {
    "baseDir",
    "workingDir",
    "binDir",
    "cobolDir"
})
public class IVPConfigType {

    @XmlSchemaType(name = "anyURI")
    protected String baseDir;
    @XmlSchemaType(name = "anyURI")
    protected String workingDir;
    @XmlSchemaType(name = "anyURI")
    protected String binDir;
    @XmlSchemaType(name = "anyURI")
    protected String cobolDir;

    /**
     * Obtiene el valor de la propiedad baseDir.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBaseDir() {
        return baseDir;
    }

    /**
     * Define el valor de la propiedad baseDir.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBaseDir(String value) {
        this.baseDir = value;
    }

    /**
     * Obtiene el valor de la propiedad workingDir.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWorkingDir() {
        return workingDir;
    }

    /**
     * Define el valor de la propiedad workingDir.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWorkingDir(String value) {
        this.workingDir = value;
    }

    /**
     * Obtiene el valor de la propiedad binDir.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBinDir() {
        return binDir;
    }

    /**
     * Define el valor de la propiedad binDir.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBinDir(String value) {
        this.binDir = value;
    }

    /**
     * Obtiene el valor de la propiedad cobolDir.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCobolDir() {
        return cobolDir;
    }

    /**
     * Define el valor de la propiedad cobolDir.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCobolDir(String value) {
        this.cobolDir = value;
    }

}
