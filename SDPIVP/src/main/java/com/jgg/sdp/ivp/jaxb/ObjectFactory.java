//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2017.11.07 a las 07:59:34 PM CET 
//


package com.jgg.sdp.ivp.jaxb;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.jgg.sdp.ivp.jaxb package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _BlockScript_QNAME = new QName("", "script");
    private final static QName _BlockSql_QNAME = new QName("", "sql");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.jgg.sdp.ivp.jaxb
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SDPIVP }
     * 
     */
    public SDPIVP createSDPIVP() {
        return new SDPIVP();
    }

    /**
     * Create an instance of {@link Component }
     * 
     */
    public Component createComponent() {
        return new Component();
    }

    /**
     * Create an instance of {@link IVPConfigType }
     * 
     */
    public IVPConfigType createIVPConfigType() {
        return new IVPConfigType();
    }

    /**
     * Create an instance of {@link IVPCasesType }
     * 
     */
    public IVPCasesType createIVPCasesType() {
        return new IVPCasesType();
    }

    /**
     * Create an instance of {@link IVPEnvType }
     * 
     */
    public IVPEnvType createIVPEnvType() {
        return new IVPEnvType();
    }

    /**
     * Create an instance of {@link IVPGroupType }
     * 
     */
    public IVPGroupType createIVPGroupType() {
        return new IVPGroupType();
    }

    /**
     * Create an instance of {@link Block }
     * 
     */
    public Block createBlock() {
        return new Block();
    }

    /**
     * Create an instance of {@link IVPCaseType }
     * 
     */
    public IVPCaseType createIVPCaseType() {
        return new IVPCaseType();
    }

    /**
     * Create an instance of {@link IVPGeneralType }
     * 
     */
    public IVPGeneralType createIVPGeneralType() {
        return new IVPGeneralType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "script", scope = Block.class)
    public JAXBElement<String> createBlockScript(String value) {
        return new JAXBElement<String>(_BlockScript_QNAME, String.class, Block.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "sql", scope = Block.class)
    public JAXBElement<String> createBlockSql(String value) {
        return new JAXBElement<String>(_BlockSql_QNAME, String.class, Block.class, value);
    }

}
