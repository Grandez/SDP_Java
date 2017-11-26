//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2017.11.25 a las 11:37:27 AM CET 
//


package com.jgg.sdp.ivp.jaxb;

import java.util.ArrayList;
import java.util.List;
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
 *       &lt;choice>
 *         &lt;element ref="{http://www.sdp.com/SDPIVP}component" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;sequence>
 *           &lt;element name="module" type="{http://www.sdp.com/SDPIVP}IVPModuleType"/>
 *           &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="config" type="{http://www.sdp.com/SDPIVP}IVPConfigType" minOccurs="0"/>
 *           &lt;element name="preProcess" type="{http://www.sdp.com/SDPIVP}IVPCommands" minOccurs="0"/>
 *           &lt;element name="group" type="{http://www.sdp.com/SDPIVP}IVPGroupType" maxOccurs="unbounded"/>
 *           &lt;element name="postProcess" type="{http://www.sdp.com/SDPIVP}IVPCommands" minOccurs="0"/>
 *         &lt;/sequence>
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
    "component",
    "module",
    "description",
    "config",
    "preProcess",
    "group",
    "postProcess"
})
@XmlRootElement(name = "SDPIVP", namespace = "http://www.sdp.com/SDPIVP")
public class SDPIVP {

    @XmlElement(namespace = "http://www.sdp.com/SDPIVP")
    protected List<Component> component;
    @XmlSchemaType(name = "string")
    protected IVPModuleType module;
    protected String description;
    protected IVPConfigType config;
    protected IVPCommands preProcess;
    protected List<IVPGroupType> group;
    protected IVPCommands postProcess;

    /**
     * Gets the value of the component property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the component property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getComponent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Component }
     * 
     * 
     */
    public List<Component> getComponent() {
        if (component == null) {
            component = new ArrayList<Component>();
        }
        return this.component;
    }

    /**
     * Obtiene el valor de la propiedad module.
     * 
     * @return
     *     possible object is
     *     {@link IVPModuleType }
     *     
     */
    public IVPModuleType getModule() {
        return module;
    }

    /**
     * Define el valor de la propiedad module.
     * 
     * @param value
     *     allowed object is
     *     {@link IVPModuleType }
     *     
     */
    public void setModule(IVPModuleType value) {
        this.module = value;
    }

    /**
     * Obtiene el valor de la propiedad description.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Define el valor de la propiedad description.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
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
     * Gets the value of the group property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the group property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGroup().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IVPGroupType }
     * 
     * 
     */
    public List<IVPGroupType> getGroup() {
        if (group == null) {
            group = new ArrayList<IVPGroupType>();
        }
        return this.group;
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

}
