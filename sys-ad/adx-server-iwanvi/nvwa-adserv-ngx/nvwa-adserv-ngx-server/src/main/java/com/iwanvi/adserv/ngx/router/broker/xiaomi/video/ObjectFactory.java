
package com.iwanvi.adserv.ngx.router.broker.xiaomi.video;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.dongle.test package. 
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

    private final static QName _ClickTracking_QNAME = new QName("", "ClickTracking");
    private final static QName _Description_QNAME = new QName("", "Description");
    private final static QName _DownloadTrackingUrl_QNAME = new QName("", "DownloadTrackingUrl");
    private final static QName _PackageName_QNAME = new QName("", "PackageName");
    private final static QName _AppSize_QNAME = new QName("", "AppSize");
    private final static QName _Error_QNAME = new QName("", "Error");
    private final static QName _Duration_QNAME = new QName("", "Duration");
    private final static QName _Impression_QNAME = new QName("", "Impression");
    private final static QName _Thumbnail_QNAME = new QName("", "Thumbnail");
    private final static QName _TotalDownloadNum_QNAME = new QName("", "TotalDownloadNum");
    private final static QName _ClickThrough_QNAME = new QName("", "ClickThrough");
    private final static QName _TargetType_QNAME = new QName("", "TargetType");
    private final static QName _AdTitle_QNAME = new QName("", "AdTitle");
    private final static QName _DownloadUrl_QNAME = new QName("", "DownloadUrl");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.dongle.test
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Tracking }
     * 
     */
    public Tracking createTracking() {
        return new Tracking();
    }

    /**
     * Create an instance of {@link Ad }
     * 
     */
    public Ad createAd() {
        return new Ad();
    }

    /**
     * Create an instance of {@link InLine }
     * 
     */
    public InLine createInLine() {
        return new InLine();
    }

    /**
     * Create an instance of {@link AdSystem }
     * 
     */
    public AdSystem createAdSystem() {
        return new AdSystem();
    }

    /**
     * Create an instance of {@link Creatives }
     * 
     */
    public Creatives createCreatives() {
        return new Creatives();
    }

    /**
     * Create an instance of {@link Creative }
     * 
     */
    public Creative createCreative() {
        return new Creative();
    }

    /**
     * Create an instance of {@link Linear }
     * 
     */
    public Linear createLinear() {
        return new Linear();
    }

    /**
     * Create an instance of {@link MediaFiles }
     * 
     */
    public MediaFiles createMediaFiles() {
        return new MediaFiles();
    }

    /**
     * Create an instance of {@link MediaFile }
     * 
     */
    public MediaFile createMediaFile() {
        return new MediaFile();
    }

    /**
     * Create an instance of {@link VideoClicks }
     * 
     */
    public VideoClicks createVideoClicks() {
        return new VideoClicks();
    }

    /**
     * Create an instance of {@link TrackingEvents }
     * 
     */
    public TrackingEvents createTrackingEvents() {
        return new TrackingEvents();
    }

    /**
     * Create an instance of {@link Extensions }
     * 
     */
    public Extensions createExtensions() {
        return new Extensions();
    }

    /**
     * Create an instance of {@link Extension }
     * 
     */
    public Extension createExtension() {
        return new Extension();
    }

    /**
     * Create an instance of {@link VAST }
     * 
     */
    public VAST createVAST() {
        return new VAST();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ClickTracking")
    public JAXBElement<String> createClickTracking(String value) {
        return new JAXBElement<String>(_ClickTracking_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Description")
    public JAXBElement<String> createDescription(String value) {
        return new JAXBElement<String>(_Description_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "DownloadTrackingUrl")
    public JAXBElement<String> createDownloadTrackingUrl(String value) {
        return new JAXBElement<String>(_DownloadTrackingUrl_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "PackageName")
    public JAXBElement<String> createPackageName(String value) {
        return new JAXBElement<String>(_PackageName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "AppSize")
    public JAXBElement<String> createAppSize(String value) {
        return new JAXBElement<String>(_AppSize_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Error")
    public JAXBElement<String> createError(String value) {
        return new JAXBElement<String>(_Error_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Duration")
    public JAXBElement<String> createDuration(String value) {
        return new JAXBElement<String>(_Duration_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Impression")
    public JAXBElement<String> createImpression(String value) {
        return new JAXBElement<String>(_Impression_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Thumbnail")
    public JAXBElement<String> createThumbnail(String value) {
        return new JAXBElement<String>(_Thumbnail_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "TotalDownloadNum")
    public JAXBElement<String> createTotalDownloadNum(String value) {
        return new JAXBElement<String>(_TotalDownloadNum_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ClickThrough")
    public JAXBElement<String> createClickThrough(String value) {
        return new JAXBElement<String>(_ClickThrough_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "TargetType")
    public JAXBElement<String> createTargetType(String value) {
        return new JAXBElement<String>(_TargetType_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "AdTitle")
    public JAXBElement<String> createAdTitle(String value) {
        return new JAXBElement<String>(_AdTitle_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "DownloadUrl")
    public JAXBElement<String> createDownloadUrl(String value) {
        return new JAXBElement<String>(_DownloadUrl_QNAME, String.class, null, value);
    }

}
