//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.05.13 at 03:41:53 PM IST 
//


package gov.shdrc.webservice.reader;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the gov.shdrc.webservice.reader package. 
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


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: gov.shdrc.webservice.reader
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link NewDataSet }
     * 
     */
    public NewDataSet createNewDataSet() {
        return new NewDataSet();
    }

    /**
     * Create an instance of {@link NewDataSet.Shrdcpa }
     * 
     */
    public NewDataSet.Shrdcpa createNewDataSetShrdcpa() {
        return new NewDataSet.Shrdcpa();
    }

    /**
     * Create an instance of {@link NewDataSet.Shrdccl }
     * 
     */
    public NewDataSet.Shrdccl createNewDataSetShrdccl() {
        return new NewDataSet.Shrdccl();
    }

    /**
     * Create an instance of {@link NewDataSet.Shrdcdccl }
     * 
     */
    public NewDataSet.Shrdcdccl createNewDataSetShrdcdccl() {
        return new NewDataSet.Shrdcdccl();
    }

    /**
     * Create an instance of {@link NewDataSet.Shrdcpayment }
     * 
     */
    public NewDataSet.Shrdcpayment createNewDataSetShrdcpayment() {
        return new NewDataSet.Shrdcpayment();
    }

    /**
     * Create an instance of {@link NewDataSet.Shrdchosp }
     * 
     */
    public NewDataSet.Shrdchosp createNewDataSetShrdchosp() {
        return new NewDataSet.Shrdchosp();
    }

}
