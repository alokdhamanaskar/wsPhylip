
package phylipWrappers_client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the phylipWrappers_client package. 
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

    private final static QName _Protdist_QNAME = new QName("http://phylipWrappers/", "protdist");
    private final static QName _ConsenseRootedTreesResponse_QNAME = new QName("http://phylipWrappers/", "consenseRootedTreesResponse");
    private final static QName _ConsenseNonRootedTrees_QNAME = new QName("http://phylipWrappers/", "consenseNonRootedTrees");
    private final static QName _ProtdistResponse_QNAME = new QName("http://phylipWrappers/", "protdistResponse");
    private final static QName _ConsenseRootedTrees_QNAME = new QName("http://phylipWrappers/", "consenseRootedTrees");
    private final static QName _ConsenseNonRootedTreesResponse_QNAME = new QName("http://phylipWrappers/", "consenseNonRootedTreesResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: phylipWrappers_client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ConsenseRootedTreesResponse }
     * 
     */
    public ConsenseRootedTreesResponse createConsenseRootedTreesResponse() {
        return new ConsenseRootedTreesResponse();
    }

    /**
     * Create an instance of {@link ProtdistResponse }
     * 
     */
    public ProtdistResponse createProtdistResponse() {
        return new ProtdistResponse();
    }

    /**
     * Create an instance of {@link ConsenseNonRootedTrees }
     * 
     */
    public ConsenseNonRootedTrees createConsenseNonRootedTrees() {
        return new ConsenseNonRootedTrees();
    }

    /**
     * Create an instance of {@link ConsenseRootedTrees }
     * 
     */
    public ConsenseRootedTrees createConsenseRootedTrees() {
        return new ConsenseRootedTrees();
    }

    /**
     * Create an instance of {@link ConsenseNonRootedTreesResponse }
     * 
     */
    public ConsenseNonRootedTreesResponse createConsenseNonRootedTreesResponse() {
        return new ConsenseNonRootedTreesResponse();
    }

    /**
     * Create an instance of {@link Protdist }
     * 
     */
    public Protdist createProtdist() {
        return new Protdist();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Protdist }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://phylipWrappers/", name = "protdist")
    public JAXBElement<Protdist> createProtdist(Protdist value) {
        return new JAXBElement<Protdist>(_Protdist_QNAME, Protdist.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsenseRootedTreesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://phylipWrappers/", name = "consenseRootedTreesResponse")
    public JAXBElement<ConsenseRootedTreesResponse> createConsenseRootedTreesResponse(ConsenseRootedTreesResponse value) {
        return new JAXBElement<ConsenseRootedTreesResponse>(_ConsenseRootedTreesResponse_QNAME, ConsenseRootedTreesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsenseNonRootedTrees }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://phylipWrappers/", name = "consenseNonRootedTrees")
    public JAXBElement<ConsenseNonRootedTrees> createConsenseNonRootedTrees(ConsenseNonRootedTrees value) {
        return new JAXBElement<ConsenseNonRootedTrees>(_ConsenseNonRootedTrees_QNAME, ConsenseNonRootedTrees.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProtdistResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://phylipWrappers/", name = "protdistResponse")
    public JAXBElement<ProtdistResponse> createProtdistResponse(ProtdistResponse value) {
        return new JAXBElement<ProtdistResponse>(_ProtdistResponse_QNAME, ProtdistResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsenseRootedTrees }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://phylipWrappers/", name = "consenseRootedTrees")
    public JAXBElement<ConsenseRootedTrees> createConsenseRootedTrees(ConsenseRootedTrees value) {
        return new JAXBElement<ConsenseRootedTrees>(_ConsenseRootedTrees_QNAME, ConsenseRootedTrees.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsenseNonRootedTreesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://phylipWrappers/", name = "consenseNonRootedTreesResponse")
    public JAXBElement<ConsenseNonRootedTreesResponse> createConsenseNonRootedTreesResponse(ConsenseNonRootedTreesResponse value) {
        return new JAXBElement<ConsenseNonRootedTreesResponse>(_ConsenseNonRootedTreesResponse_QNAME, ConsenseNonRootedTreesResponse.class, null, value);
    }

}
