//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2017.11.25 a las 11:37:27 AM CET 
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

    private final static QName _IVPCommandsComponent_QNAME = new QName("", "component");
    private final static QName _IVPCommandsModule_QNAME = new QName("", "module");
    private final static QName _IVPCommandsComment_QNAME = new QName("", "comment");
    private final static QName _IVPCommandsScript_QNAME = new QName("", "script");
    private final static QName _IVPCommandsObject_QNAME = new QName("", "object");
    private final static QName _IVPCommandsSql_QNAME = new QName("", "sql");

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
     * Create an instance of {@link IVPCommands }
     * 
     */
    public IVPCommands createIVPCommands() {
        return new IVPCommands();
    }

    /**
     * Create an instance of {@link IVPGroupType }
     * 
     */
    public IVPGroupType createIVPGroupType() {
        return new IVPGroupType();
    }

    /**
     * Create an instance of {@link IVPObject }
     * 
     */
    public IVPObject createIVPObject() {
        return new IVPObject();
    }

    /**
     * Create an instance of {@link IVPModule }
     * 
     */
    public IVPModule createIVPModule() {
        return new IVPModule();
    }

    /**
     * Create an instance of {@link IVPParameters }
     * 
     */
    public IVPParameters createIVPParameters() {
        return new IVPParameters();
    }

    /**
     * Create an instance of {@link IVPCaseType }
     * 
     */
    public IVPCaseType createIVPCaseType() {
        return new IVPCaseType();
    }

    /**
     * Create an instance of {@link IVPComponent }
     * 
     */
    public IVPComponent createIVPComponent() {
        return new IVPComponent();
    }

    /**
     * Create an instance of {@link IVPBlock }
     * 
     */
    public IVPBlock createIVPBlock() {
        return new IVPBlock();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IVPComponent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "component", scope = IVPCommands.class)
    public JAXBElement<IVPComponent> createIVPCommandsComponent(IVPComponent value) {
        return new JAXBElement<IVPComponent>(_IVPCommandsComponent_QNAME, IVPComponent.class, IVPCommands.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IVPModule }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "module", scope = IVPCommands.class)
    public JAXBElement<IVPModule> createIVPCommandsModule(IVPModule value) {
        return new JAXBElement<IVPModule>(_IVPCommandsModule_QNAME, IVPModule.class, IVPCommands.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "comment", scope = IVPCommands.class)
    public JAXBElement<String> createIVPCommandsComment(String value) {
        return new JAXBElement<String>(_IVPCommandsComment_QNAME, String.class, IVPCommands.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "script", scope = IVPCommands.class)
    public JAXBElement<String> createIVPCommandsScript(String value) {
        return new JAXBElement<String>(_IVPCommandsScript_QNAME, String.class, IVPCommands.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IVPObject }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "object", scope = IVPCommands.class)
    public JAXBElement<IVPObject> createIVPCommandsObject(IVPObject value) {
        return new JAXBElement<IVPObject>(_IVPCommandsObject_QNAME, IVPObject.class, IVPCommands.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "sql", scope = IVPCommands.class)
    public JAXBElement<String> createIVPCommandsSql(String value) {
        return new JAXBElement<String>(_IVPCommandsSql_QNAME, String.class, IVPCommands.class, value);
    }

}
