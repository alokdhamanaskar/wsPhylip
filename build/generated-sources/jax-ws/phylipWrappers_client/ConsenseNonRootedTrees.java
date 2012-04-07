
package phylipWrappers_client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for consenseNonRootedTrees complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="consenseNonRootedTrees">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="query" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="consensusType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OutgroupRoot" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nofOutgroup" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "consenseNonRootedTrees", propOrder = {
    "query",
    "consensusType",
    "outgroupRoot",
    "nofOutgroup"
})
public class ConsenseNonRootedTrees {

    protected String query;
    protected String consensusType;
    @XmlElement(name = "OutgroupRoot")
    protected String outgroupRoot;
    protected int nofOutgroup;

    /**
     * Gets the value of the query property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuery() {
        return query;
    }

    /**
     * Sets the value of the query property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuery(String value) {
        this.query = value;
    }

    /**
     * Gets the value of the consensusType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsensusType() {
        return consensusType;
    }

    /**
     * Sets the value of the consensusType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsensusType(String value) {
        this.consensusType = value;
    }

    /**
     * Gets the value of the outgroupRoot property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutgroupRoot() {
        return outgroupRoot;
    }

    /**
     * Sets the value of the outgroupRoot property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutgroupRoot(String value) {
        this.outgroupRoot = value;
    }

    /**
     * Gets the value of the nofOutgroup property.
     * 
     */
    public int getNofOutgroup() {
        return nofOutgroup;
    }

    /**
     * Sets the value of the nofOutgroup property.
     * 
     */
    public void setNofOutgroup(int value) {
        this.nofOutgroup = value;
    }

}
