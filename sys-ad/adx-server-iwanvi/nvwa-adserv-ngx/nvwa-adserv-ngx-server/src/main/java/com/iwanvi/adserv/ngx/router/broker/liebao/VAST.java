package com.iwanvi.adserv.ngx.router.broker.liebao;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: 郑晓东
 * @date: 2019-06-10 19:00
 * @version: v1.0
 * @Description:
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"ad"})
@XmlRootElement(name = "VAST")
public class VAST {
    @XmlElement(name = "Ad")
    protected List<Ad> ad;
    @XmlAttribute(name = "version")
    protected String version;

    public List<VAST.Ad> getAd() {
        if (ad == null) {
            ad = new ArrayList<Ad>();
        }
        return this.ad;
    }

    /**
     * 获取version属性的值。
     *
     * @return possible object is
     * {@link String }
     */
    public String getVersion() {
        return version;
    }

    /**
     * 设置version属性的值。
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setVersion(String value) {
        this.version = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "inLine"
    })
    public static class Ad {

        @XmlElement(name = "InLine")
        protected List<VAST.Ad.InLine> inLine;

        public List<VAST.Ad.InLine> getInLine() {
            if (inLine == null) {
                inLine = new ArrayList<VAST.Ad.InLine>();
            }
            return this.inLine;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "adTitle",
                "impression",
                "description",
                "adSystem",
                "extensions",
                "creatives"
        })
        public static class InLine {

            @XmlElement(name = "AdTitle")
            protected String adTitle;
            @XmlElement(name = "Impression")
            protected String impression;
            @XmlElement(name = "Description")
            protected String description;
            @XmlElement(name = "AdSystem", nillable = true)
            protected List<VAST.Ad.InLine.AdSystem> adSystem;
            @XmlElement(name = "Extensions")
            protected List<VAST.Ad.InLine.Extensions> extensions;
            @XmlElement(name = "Creatives")
            protected List<VAST.Ad.InLine.Creatives> creatives;

            /**
             * 获取adTitle属性的值。
             *
             * @return possible object is
             * {@link String }
             */
            public String getAdTitle() {
                return adTitle;
            }

            /**
             * 设置adTitle属性的值。
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setAdTitle(String value) {
                this.adTitle = value;
            }

            /**
             * 获取impression属性的值。
             *
             * @return possible object is
             * {@link String }
             */
            public String getImpression() {
                return impression;
            }

            /**
             * 设置impression属性的值。
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setImpression(String value) {
                this.impression = value;
            }

            /**
             * 获取description属性的值。
             *
             * @return possible object is
             * {@link String }
             */
            public String getDescription() {
                return description;
            }

            /**
             * 设置description属性的值。
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setDescription(String value) {
                this.description = value;
            }

            public List<VAST.Ad.InLine.AdSystem> getAdSystem() {
                if (adSystem == null) {
                    adSystem = new ArrayList<VAST.Ad.InLine.AdSystem>();
                }
                return this.adSystem;
            }

            public List<VAST.Ad.InLine.Extensions> getExtensions() {
                if (extensions == null) {
                    extensions = new ArrayList<VAST.Ad.InLine.Extensions>();
                }
                return this.extensions;
            }

            public List<VAST.Ad.InLine.Creatives> getCreatives() {
                if (creatives == null) {
                    creatives = new ArrayList<VAST.Ad.InLine.Creatives>();
                }
                return this.creatives;
            }

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "value"
            })
            public static class AdSystem {

                @XmlValue
                protected String value;
                @XmlAttribute(name = "version")
                protected String version;

                /**
                 * 获取value属性的值。
                 *
                 * @return possible object is
                 * {@link String }
                 */
                public String getValue() {
                    return value;
                }

                /**
                 * 设置value属性的值。
                 *
                 * @param value allowed object is
                 *              {@link String }
                 */
                public void setValue(String value) {
                    this.value = value;
                }

                /**
                 * 获取version属性的值。
                 *
                 * @return possible object is
                 * {@link String }
                 */
                public String getVersion() {
                    return version;
                }

                /**
                 * 设置version属性的值。
                 *
                 * @param value allowed object is
                 *              {@link String }
                 */
                public void setVersion(String value) {
                    this.version = value;
                }

            }

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "creative"
            })
            public static class Creatives {

                @XmlElement(name = "Creative")
                protected List<VAST.Ad.InLine.Creatives.Creative> creative;


                public List<VAST.Ad.InLine.Creatives.Creative> getCreative() {
                    if (creative == null) {
                        creative = new ArrayList<VAST.Ad.InLine.Creatives.Creative>();
                    }
                    return this.creative;
                }

                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                        "companionAds",
                        "universalAdId",
                        "linear"
                })
                public static class Creative {

                    @XmlElement(name = "CompanionAds")
                    protected List<VAST.Ad.InLine.Creatives.Creative.CompanionAds> companionAds;
                    @XmlElement(name = "UniversalAdId", nillable = true)
                    protected List<VAST.Ad.InLine.Creatives.Creative.UniversalAdId> universalAdId;
                    @XmlElement(name = "Linear")
                    protected List<VAST.Ad.InLine.Creatives.Creative.Linear> linear;
                    @XmlAttribute(name = "id")
                    protected String id;
                    @XmlAttribute(name = "sequence")
                    protected String sequence;


                    public List<VAST.Ad.InLine.Creatives.Creative.CompanionAds> getCompanionAds() {
                        if (companionAds == null) {
                            companionAds = new ArrayList<VAST.Ad.InLine.Creatives.Creative.CompanionAds>();
                        }
                        return this.companionAds;
                    }


                    public List<VAST.Ad.InLine.Creatives.Creative.UniversalAdId> getUniversalAdId() {
                        if (universalAdId == null) {
                            universalAdId = new ArrayList<VAST.Ad.InLine.Creatives.Creative.UniversalAdId>();
                        }
                        return this.universalAdId;
                    }


                    public List<VAST.Ad.InLine.Creatives.Creative.Linear> getLinear() {
                        if (linear == null) {
                            linear = new ArrayList<VAST.Ad.InLine.Creatives.Creative.Linear>();
                        }
                        return this.linear;
                    }

                    /**
                     * 获取id属性的值。
                     *
                     * @return possible object is
                     * {@link String }
                     */
                    public String getId() {
                        return id;
                    }

                    /**
                     * 设置id属性的值。
                     *
                     * @param value allowed object is
                     *              {@link String }
                     */
                    public void setId(String value) {
                        this.id = value;
                    }

                    /**
                     * 获取sequence属性的值。
                     *
                     * @return possible object is
                     * {@link String }
                     */
                    public String getSequence() {
                        return sequence;
                    }

                    /**
                     * 设置sequence属性的值。
                     *
                     * @param value allowed object is
                     *              {@link String }
                     */
                    public void setSequence(String value) {
                        this.sequence = value;
                    }

                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                            "companion"
                    })
                    public static class CompanionAds {

                        @XmlElement(name = "Companion")
                        protected List<VAST.Ad.InLine.Creatives.Creative.CompanionAds.Companion> companion;
                        @XmlAttribute(name = "required")
                        protected String required;


                        public List<VAST.Ad.InLine.Creatives.Creative.CompanionAds.Companion> getCompanion() {
                            if (companion == null) {
                                companion = new ArrayList<VAST.Ad.InLine.Creatives.Creative.CompanionAds.Companion>();
                            }
                            return this.companion;
                        }

                        /**
                         * 获取required属性的值。
                         *
                         * @return possible object is
                         * {@link String }
                         */
                        public String getRequired() {
                            return required;
                        }

                        /**
                         * 设置required属性的值。
                         *
                         * @param value allowed object is
                         *              {@link String }
                         */
                        public void setRequired(String value) {
                            this.required = value;
                        }


                        @XmlAccessorType(XmlAccessType.FIELD)
                        @XmlType(name = "", propOrder = {
                                "companionClickThrough",
                                "companionClickTracking",
                                "staticResource",
                                "trackingEvents"
                        })
                        public static class Companion {

                            @XmlElement(name = "CompanionClickThrough")
                            protected String companionClickThrough;
                            @XmlElement(name = "CompanionClickTracking")
                            protected String companionClickTracking;
                            @XmlElement(name = "StaticResource", nillable = true)
                            protected List<StaticResource> staticResource;
                            @XmlElement(name = "TrackingEvents")
                            protected List<TrackingEvents> trackingEvents;
                            @XmlAttribute(name = "height")
                            protected String height;
                            @XmlAttribute(name = "width")
                            protected String width;
                            @XmlAttribute(name = "id")
                            protected String id;

                            /**
                             * 获取companionClickThrough属性的值。
                             *
                             * @return possible object is
                             * {@link String }
                             */
                            public String getCompanionClickThrough() {
                                return companionClickThrough;
                            }

                            /**
                             * 设置companionClickThrough属性的值。
                             *
                             * @param value allowed object is
                             *              {@link String }
                             */
                            public void setCompanionClickThrough(String value) {
                                this.companionClickThrough = value;
                            }

                            /**
                             * 获取companionClickTracking属性的值。
                             *
                             * @return possible object is
                             * {@link String }
                             */
                            public String getCompanionClickTracking() {
                                return companionClickTracking;
                            }

                            /**
                             * 设置companionClickTracking属性的值。
                             *
                             * @param value allowed object is
                             *              {@link String }
                             */
                            public void setCompanionClickTracking(String value) {
                                this.companionClickTracking = value;
                            }


                            public List<StaticResource> getStaticResource() {
                                if (staticResource == null) {
                                    staticResource = new ArrayList<StaticResource>();
                                }
                                return this.staticResource;
                            }


                            public List<TrackingEvents> getTrackingEvents() {
                                if (trackingEvents == null) {
                                    trackingEvents = new ArrayList<TrackingEvents>();
                                }
                                return this.trackingEvents;
                            }

                            /**
                             * 获取height属性的值。
                             *
                             * @return possible object is
                             * {@link String }
                             */
                            public String getHeight() {
                                return height;
                            }

                            /**
                             * 设置height属性的值。
                             *
                             * @param value allowed object is
                             *              {@link String }
                             */
                            public void setHeight(String value) {
                                this.height = value;
                            }

                            /**
                             * 获取width属性的值。
                             *
                             * @return possible object is
                             * {@link String }
                             */
                            public String getWidth() {
                                return width;
                            }

                            /**
                             * 设置width属性的值。
                             *
                             * @param value allowed object is
                             *              {@link String }
                             */
                            public void setWidth(String value) {
                                this.width = value;
                            }

                            /**
                             * 获取id属性的值。
                             *
                             * @return possible object is
                             * {@link String }
                             */
                            public String getId() {
                                return id;
                            }

                            /**
                             * 设置id属性的值。
                             *
                             * @param value allowed object is
                             *              {@link String }
                             */
                            public void setId(String value) {
                                this.id = value;
                            }

                        }

                    }

                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                            "duration",
                            "mediaFiles",
                            "videoClicks",
                            "trackingEvents",
                            "icons"
                    })
                    public static class Linear {

                        @XmlElement(name = "Duration")
                        protected String duration;
                        @XmlElement(name = "MediaFiles")
                        protected List<VAST.Ad.InLine.Creatives.Creative.Linear.MediaFiles> mediaFiles;
                        @XmlElement(name = "VideoClicks")
                        protected List<VAST.Ad.InLine.Creatives.Creative.Linear.VideoClicks> videoClicks;
                        @XmlElement(name = "TrackingEvents")
                        protected List<TrackingEvents> trackingEvents;
                        @XmlElement(name = "Icons")
                        protected List<VAST.Ad.InLine.Creatives.Creative.Linear.Icons> icons;

                        /**
                         * 获取duration属性的值。
                         *
                         * @return possible object is
                         * {@link String }
                         */
                        public String getDuration() {
                            return duration;
                        }

                        /**
                         * 设置duration属性的值。
                         *
                         * @param value allowed object is
                         *              {@link String }
                         */
                        public void setDuration(String value) {
                            this.duration = value;
                        }

                        public List<VAST.Ad.InLine.Creatives.Creative.Linear.MediaFiles> getMediaFiles() {
                            if (mediaFiles == null) {
                                mediaFiles = new ArrayList<VAST.Ad.InLine.Creatives.Creative.Linear.MediaFiles>();
                            }
                            return this.mediaFiles;
                        }


                        public List<VAST.Ad.InLine.Creatives.Creative.Linear.VideoClicks> getVideoClicks() {
                            if (videoClicks == null) {
                                videoClicks = new ArrayList<VAST.Ad.InLine.Creatives.Creative.Linear.VideoClicks>();
                            }
                            return this.videoClicks;
                        }

                        public List<TrackingEvents> getTrackingEvents() {
                            if (trackingEvents == null) {
                                trackingEvents = new ArrayList<TrackingEvents>();
                            }
                            return this.trackingEvents;
                        }

                        public List<VAST.Ad.InLine.Creatives.Creative.Linear.Icons> getIcons() {
                            if (icons == null) {
                                icons = new ArrayList<VAST.Ad.InLine.Creatives.Creative.Linear.Icons>();
                            }
                            return this.icons;
                        }

                        @XmlAccessorType(XmlAccessType.FIELD)
                        @XmlType(name = "", propOrder = {
                                "icon"
                        })
                        public static class Icons {

                            @XmlElement(name = "Icon")
                            protected List<VAST.Ad.InLine.Creatives.Creative.Linear.Icons.Icon> icon;


                            public List<VAST.Ad.InLine.Creatives.Creative.Linear.Icons.Icon> getIcon() {
                                if (icon == null) {
                                    icon = new ArrayList<VAST.Ad.InLine.Creatives.Creative.Linear.Icons.Icon>();
                                }
                                return this.icon;
                            }


                            @XmlAccessorType(XmlAccessType.FIELD)
                            @XmlType(name = "", propOrder = {
                                    "staticResource"
                            })
                            public static class Icon {

                                @XmlElement(name = "StaticResource", nillable = true)
                                protected List<StaticResource> staticResource;


                                public List<StaticResource> getStaticResource() {
                                    if (staticResource == null) {
                                        staticResource = new ArrayList<StaticResource>();
                                    }
                                    return this.staticResource;
                                }

                            }

                        }

                        @XmlAccessorType(XmlAccessType.FIELD)
                        @XmlType(name = "", propOrder = {
                                "mediaFile"
                        })
                        public static class MediaFiles {

                            @XmlElement(name = "MediaFile", nillable = true)
                            protected List<VAST.Ad.InLine.Creatives.Creative.Linear.MediaFiles.MediaFile> mediaFile;


                            public List<VAST.Ad.InLine.Creatives.Creative.Linear.MediaFiles.MediaFile> getMediaFile() {
                                if (mediaFile == null) {
                                    mediaFile = new ArrayList<VAST.Ad.InLine.Creatives.Creative.Linear.MediaFiles.MediaFile>();
                                }
                                return this.mediaFile;
                            }

                            @XmlAccessorType(XmlAccessType.FIELD)
                            @XmlType(name = "", propOrder = {
                                    "value"
                            })
                            public static class MediaFile {

                                @XmlValue
                                protected String value;
                                @XmlAttribute(name = "delivery")
                                protected String delivery;
                                @XmlAttribute(name = "width")
                                protected String width;
                                @XmlAttribute(name = "height")
                                protected String height;
                                @XmlAttribute(name = "type")
                                protected String type;
                                @XmlAttribute(name = "encode")
                                protected String encode;

                                /**
                                 * 获取value属性的值。
                                 *
                                 * @return possible object is
                                 * {@link String }
                                 */
                                public String getValue() {
                                    return value;
                                }

                                /**
                                 * 设置value属性的值。
                                 *
                                 * @param value allowed object is
                                 *              {@link String }
                                 */
                                public void setValue(String value) {
                                    this.value = value;
                                }

                                /**
                                 * 获取delivery属性的值。
                                 *
                                 * @return possible object is
                                 * {@link String }
                                 */
                                public String getDelivery() {
                                    return delivery;
                                }

                                /**
                                 * 设置delivery属性的值。
                                 *
                                 * @param value allowed object is
                                 *              {@link String }
                                 */
                                public void setDelivery(String value) {
                                    this.delivery = value;
                                }

                                /**
                                 * 获取width属性的值。
                                 *
                                 * @return possible object is
                                 * {@link String }
                                 */
                                public String getWidth() {
                                    return width;
                                }

                                /**
                                 * 设置width属性的值。
                                 *
                                 * @param value allowed object is
                                 *              {@link String }
                                 */
                                public void setWidth(String value) {
                                    this.width = value;
                                }

                                /**
                                 * 获取height属性的值。
                                 *
                                 * @return possible object is
                                 * {@link String }
                                 */
                                public String getHeight() {
                                    return height;
                                }

                                /**
                                 * 设置height属性的值。
                                 *
                                 * @param value allowed object is
                                 *              {@link String }
                                 */
                                public void setHeight(String value) {
                                    this.height = value;
                                }

                                /**
                                 * 获取type属性的值。
                                 *
                                 * @return possible object is
                                 * {@link String }
                                 */
                                public String getType() {
                                    return type;
                                }

                                /**
                                 * 设置type属性的值。
                                 *
                                 * @param value allowed object is
                                 *              {@link String }
                                 */
                                public void setType(String value) {
                                    this.type = value;
                                }

                                /**
                                 * 获取encode属性的值。
                                 *
                                 * @return possible object is
                                 * {@link String }
                                 */
                                public String getEncode() {
                                    return encode;
                                }

                                /**
                                 * 设置encode属性的值。
                                 *
                                 * @param value allowed object is
                                 *              {@link String }
                                 */
                                public void setEncode(String value) {
                                    this.encode = value;
                                }

                            }

                        }

                        @XmlAccessorType(XmlAccessType.FIELD)
                        @XmlType(name = "", propOrder = {
                                "clickThrough",
                                "clickTracking"
                        })
                        public static class VideoClicks {

                            @XmlElement(name = "ClickThrough")
                            protected String clickThrough;
                            @XmlElement(name = "ClickTracking")
                            protected String clickTracking;

                            /**
                             * 获取clickThrough属性的值。
                             *
                             * @return possible object is
                             * {@link String }
                             */
                            public String getClickThrough() {
                                return clickThrough;
                            }

                            /**
                             * 设置clickThrough属性的值。
                             *
                             * @param value allowed object is
                             *              {@link String }
                             */
                            public void setClickThrough(String value) {
                                this.clickThrough = value;
                            }

                            /**
                             * 获取clickTracking属性的值。
                             *
                             * @return possible object is
                             * {@link String }
                             */
                            public String getClickTracking() {
                                return clickTracking;
                            }

                            /**
                             * 设置clickTracking属性的值。
                             *
                             * @param value allowed object is
                             *              {@link String }
                             */
                            public void setClickTracking(String value) {
                                this.clickTracking = value;
                            }

                        }

                    }


                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                            "value"
                    })
                    public static class UniversalAdId {

                        @XmlValue
                        protected String value;
                        @XmlAttribute(name = "idRegistry")
                        protected String idRegistry;
                        @XmlAttribute(name = "idValue")
                        protected String idValue;

                        /**
                         * 获取value属性的值。
                         *
                         * @return possible object is
                         * {@link String }
                         */
                        public String getValue() {
                            return value;
                        }

                        /**
                         * 设置value属性的值。
                         *
                         * @param value allowed object is
                         *              {@link String }
                         */
                        public void setValue(String value) {
                            this.value = value;
                        }

                        /**
                         * 获取idRegistry属性的值。
                         *
                         * @return possible object is
                         * {@link String }
                         */
                        public String getIdRegistry() {
                            return idRegistry;
                        }

                        /**
                         * 设置idRegistry属性的值。
                         *
                         * @param value allowed object is
                         *              {@link String }
                         */
                        public void setIdRegistry(String value) {
                            this.idRegistry = value;
                        }

                        /**
                         * 获取idValue属性的值。
                         *
                         * @return possible object is
                         * {@link String }
                         */
                        public String getIdValue() {
                            return idValue;
                        }

                        /**
                         * 设置idValue属性的值。
                         *
                         * @param value allowed object is
                         *              {@link String }
                         */
                        public void setIdValue(String value) {
                            this.idValue = value;
                        }

                    }

                }

            }

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "extension"
            })
            public static class Extensions {

                @XmlElement(name = "Extension")
                protected List<VAST.Ad.InLine.Extensions.Extension> extension;


                public List<VAST.Ad.InLine.Extensions.Extension> getExtension() {
                    if (extension == null) {
                        extension = new ArrayList<VAST.Ad.InLine.Extensions.Extension>();
                    }
                    return this.extension;
                }

                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                        "mType",
                        "rating",
                        "downloadNum",
                        "button",
                        "caption"
                })
                public static class Extension {

                    @XmlElement(name = "MType")
                    protected String mType;
                    @XmlElement(name = "Rating")
                    protected String rating;
                    @XmlElement(name = "DownloadNum")
                    protected String downloadNum;
                    @XmlElement(name = "Button", nillable = true)
                    protected List<VAST.Ad.InLine.Extensions.Extension.Button> button;
                    @XmlElement(name = "Caption", nillable = true)
                    protected List<VAST.Ad.InLine.Extensions.Extension.Caption> caption;
                    @XmlAttribute(name = "type")
                    protected String type;

                    /**
                     * 获取mType属性的值。
                     *
                     * @return possible object is
                     * {@link String }
                     */
                    public String getMType() {
                        return mType;
                    }

                    /**
                     * 设置mType属性的值。
                     *
                     * @param value allowed object is
                     *              {@link String }
                     */
                    public void setMType(String value) {
                        this.mType = value;
                    }

                    /**
                     * 获取rating属性的值。
                     *
                     * @return possible object is
                     * {@link String }
                     */
                    public String getRating() {
                        return rating;
                    }

                    /**
                     * 设置rating属性的值。
                     *
                     * @param value allowed object is
                     *              {@link String }
                     */
                    public void setRating(String value) {
                        this.rating = value;
                    }

                    /**
                     * 获取downloadNum属性的值。
                     *
                     * @return possible object is
                     * {@link String }
                     */
                    public String getDownloadNum() {
                        return downloadNum;
                    }

                    /**
                     * 设置downloadNum属性的值。
                     *
                     * @param value allowed object is
                     *              {@link String }
                     */
                    public void setDownloadNum(String value) {
                        this.downloadNum = value;
                    }

                    public List<VAST.Ad.InLine.Extensions.Extension.Button> getButton() {
                        if (button == null) {
                            button = new ArrayList<VAST.Ad.InLine.Extensions.Extension.Button>();
                        }
                        return this.button;
                    }


                    public List<VAST.Ad.InLine.Extensions.Extension.Caption> getCaption() {
                        if (caption == null) {
                            caption = new ArrayList<VAST.Ad.InLine.Extensions.Extension.Caption>();
                        }
                        return this.caption;
                    }

                    /**
                     * 获取type属性的值。
                     *
                     * @return possible object is
                     * {@link String }
                     */
                    public String getType() {
                        return type;
                    }

                    /**
                     * 设置type属性的值。
                     *
                     * @param value allowed object is
                     *              {@link String }
                     */
                    public void setType(String value) {
                        this.type = value;
                    }

                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                            "value"
                    })
                    public static class Button {

                        @XmlValue
                        protected String value;
                        @XmlAttribute(name = "name")
                        protected String name;

                        /**
                         * 获取value属性的值。
                         *
                         * @return possible object is
                         * {@link String }
                         */
                        public String getValue() {
                            return value;
                        }

                        /**
                         * 设置value属性的值。
                         *
                         * @param value allowed object is
                         *              {@link String }
                         */
                        public void setValue(String value) {
                            this.value = value;
                        }

                        /**
                         * 获取name属性的值。
                         *
                         * @return possible object is
                         * {@link String }
                         */
                        public String getName() {
                            return name;
                        }

                        /**
                         * 设置name属性的值。
                         *
                         * @param value allowed object is
                         *              {@link String }
                         */
                        public void setName(String value) {
                            this.name = value;
                        }

                    }


                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                            "value"
                    })
                    public static class Caption {

                        @XmlValue
                        protected String value;
                        @XmlAttribute(name = "name")
                        protected String name;

                        /**
                         * 获取value属性的值。
                         *
                         * @return possible object is
                         * {@link String }
                         */
                        public String getValue() {
                            return value;
                        }

                        /**
                         * 设置value属性的值。
                         *
                         * @param value allowed object is
                         *              {@link String }
                         */
                        public void setValue(String value) {
                            this.value = value;
                        }

                        /**
                         * 获取name属性的值。
                         *
                         * @return possible object is
                         * {@link String }
                         */
                        public String getName() {
                            return name;
                        }

                        /**
                         * 设置name属性的值。
                         *
                         * @param value allowed object is
                         *              {@link String }
                         */
                        public void setName(String value) {
                            this.name = value;
                        }

                    }

                }

            }

        }

    }

}
