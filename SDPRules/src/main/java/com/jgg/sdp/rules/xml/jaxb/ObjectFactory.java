//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2017.10.16 a las 01:07:34 PM CEST 
//


package com.jgg.sdp.rules.xml.jaxb;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.jgg.sdp.rules.xml.jaxb package. 
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

    private final static QName _HeaderReplace_QNAME = new QName("", "replace");
    private final static QName _HeaderComment_QNAME = new QName("", "comment");
    private final static QName _HeaderTitle_QNAME = new QName("", "title");
    private final static QName _SampleTypeCorrect_QNAME = new QName("", "correct");
    private final static QName _SampleTypeBad_QNAME = new QName("", "bad");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.jgg.sdp.rules.xml.jaxb
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Item }
     * 
     */
    public Item createItem() {
        return new Item();
    }

    /**
     * Create an instance of {@link ConditionType }
     * 
     */
    public ConditionType createConditionType() {
        return new ConditionType();
    }

    /**
     * Create an instance of {@link Description }
     * 
     */
    public Description createDescription() {
        return new Description();
    }

    /**
     * Create an instance of {@link Text }
     * 
     */
    public Text createText() {
        return new Text();
    }

    /**
     * Create an instance of {@link Rule }
     * 
     */
    public Rule createRule() {
        return new Rule();
    }

    /**
     * Create an instance of {@link SampleType }
     * 
     */
    public SampleType createSampleType() {
        return new SampleType();
    }

    /**
     * Create an instance of {@link Header }
     * 
     */
    public Header createHeader() {
        return new Header();
    }

    /**
     * Create an instance of {@link Formula }
     * 
     */
    public Formula createFormula() {
        return new Formula();
    }

    /**
     * Create an instance of {@link SDPRules }
     * 
     */
    public SDPRules createSDPRules() {
        return new SDPRules();
    }

    /**
     * Create an instance of {@link Group }
     * 
     */
    public Group createGroup() {
        return new Group();
    }

    /**
     * Create an instance of {@link ExpressionType }
     * 
     */
    public ExpressionType createExpressionType() {
        return new ExpressionType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "replace", scope = Header.class, defaultValue = "false")
    public JAXBElement<Boolean> createHeaderReplace(Boolean value) {
        return new JAXBElement<Boolean>(_HeaderReplace_QNAME, Boolean.class, Header.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "comment", scope = Header.class)
    public JAXBElement<String> createHeaderComment(String value) {
        return new JAXBElement<String>(_HeaderComment_QNAME, String.class, Header.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "title", scope = Header.class)
    public JAXBElement<String> createHeaderTitle(String value) {
        return new JAXBElement<String>(_HeaderTitle_QNAME, String.class, Header.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "correct", scope = SampleType.class)
    public JAXBElement<String> createSampleTypeCorrect(String value) {
        return new JAXBElement<String>(_SampleTypeCorrect_QNAME, String.class, SampleType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "bad", scope = SampleType.class)
    public JAXBElement<String> createSampleTypeBad(String value) {
        return new JAXBElement<String>(_SampleTypeBad_QNAME, String.class, SampleType.class, value);
    }

}
