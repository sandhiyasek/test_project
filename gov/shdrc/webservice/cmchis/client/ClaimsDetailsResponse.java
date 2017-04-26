
package gov.shdrc.webservice.cmchis.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ClaimsDetailsResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "claimsDetailsResult"
})
@XmlRootElement(name = "ClaimsDetailsResponse")
public class ClaimsDetailsResponse {

    @XmlElement(name = "ClaimsDetailsResult")
    protected String claimsDetailsResult;

    /**
     * Gets the value of the claimsDetailsResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClaimsDetailsResult() {
        return claimsDetailsResult;
    }

    /**
     * Sets the value of the claimsDetailsResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClaimsDetailsResult(String value) {
        this.claimsDetailsResult = value;
    }

}
