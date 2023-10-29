package com.fftime.ffmob.model;

import java.util.List;
import java.util.Map;

//sdk广告请求
public class AdRequest {
    private String id;
    private List<Imp> imps;
    private App app;
    private Device device;

    private AdRequest(Builder builder) {
        id = builder.id;
        imps = builder.imps;
        app = builder.app;
        device = builder.device;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getId() {
        return id;
    }

    public List<Imp> getImps() {
        return imps;
    }

    public App getApp() {
        return app;
    }

    public Device getDevice() {
        return device;
    }

    public static class Imp {
        private String id;
        private String posId;
        private Map<String,String> ext;

        private Imp(Builder builder) {
            id = builder.id;
            posId = builder.posId;
            ext = builder.ext;
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public String getId() {
            return id;
        }

        public String getPosId() {
            return posId;
        }

        public Map<String, String> getExt() {
            return ext;
        }

        public static final class Builder {
            private String id;
            private String posId;
            private Map<String, String> ext;

            private Builder() {
            }

            public Builder id(String val) {
                id = val;
                return this;
            }

            public Builder posId(String val) {
                posId = val;
                return this;
            }

            public Builder ext(Map<String, String> val) {
                ext = val;
                return this;
            }

            public Imp build() {
                return new Imp(this);
            }
        }
    }

    public static class App {
        private String appId;
        private String name;
        private String version;
        private String channel;
        private Map<String,String> ext;

        private App(Builder builder) {
            appId = builder.appId;
            name = builder.name;
            version = builder.version;
            channel = builder.channel;
            ext = builder.ext;
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public String getAppId() {
            return appId;
        }

        public String getName() {
            return name;
        }

        public String getVersion() {
            return version;
        }

        public String getChannel() {
            return channel;
        }

        public Map<String, String> getExt() {
            return ext;
        }

        public static final class Builder {
            private String appId;
            private String name;
            private String version;
            private String channel;
            private Map<String, String> ext;

            private Builder() {
            }

            public Builder appId(String val) {
                appId = val;
                return this;
            }

            public Builder name(String val) {
                name = val;
                return this;
            }

            public Builder version(String val) {
                version = val;
                return this;
            }

            public Builder channel(String val) {
                channel = val;
                return this;
            }

            public Builder ext(Map<String, String> val) {
                ext = val;
                return this;
            }

            public App build() {
                return new App(this);
            }
        }
    }

    public static class Device {
        private String did;
        private String ip;
        private int carrier;
        private String make;
        private String model;
        private int os;
        private String osv;
        private int connectionType;
        private int deviceType;

        private Device(Builder builder) {
            did = builder.did;
            ip = builder.ip;
            carrier = builder.carrier;
            make = builder.make;
            model = builder.model;
            os = builder.os;
            osv = builder.osv;
            connectionType = builder.connectionType;
            deviceType = builder.deviceType;
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public String getDid() {
            return did;
        }

        public String getIp() {
            return ip;
        }

        public int getCarrier() {
            return carrier;
        }

        public String getMake() {
            return make;
        }

        public String getModel() {
            return model;
        }

        public int getOs() {
            return os;
        }

        public String getOsv() {
            return osv;
        }

        public int getConnectionType() {
            return connectionType;
        }

        public int getDeviceType() {
            return deviceType;
        }

        public static final class Builder {
            private String did;
            private String ip;
            private int carrier;
            private String make;
            private String model;
            private int os;
            private String osv;
            private int connectionType;
            private int deviceType;

            private Builder() {
            }

            public Builder did(String val) {
                did = val;
                return this;
            }

            public Builder ip(String val) {
                ip = val;
                return this;
            }

            public Builder carrier(int val) {
                carrier = val;
                return this;
            }

            public Builder make(String val) {
                make = val;
                return this;
            }

            public Builder model(String val) {
                model = val;
                return this;
            }

            public Builder os(int val) {
                os = val;
                return this;
            }

            public Builder osv(String val) {
                osv = val;
                return this;
            }

            public Builder connectionType(int val) {
                connectionType = val;
                return this;
            }

            public Builder deviceType(int val) {
                deviceType = val;
                return this;
            }

            public Device build() {
                return new Device(this);
            }
        }
    }

    public static final class Builder {
        private String id;
        private List<Imp> imps;
        private App app;
        private Device device;

        private Builder() {
        }

        public Builder id(String val) {
            id = val;
            return this;
        }

        public Builder imps(List<Imp> val) {
            imps = val;
            return this;
        }

        public Builder app(App val) {
            app = val;
            return this;
        }

        public Builder device(Device val) {
            device = val;
            return this;
        }

        public AdRequest build() {
            return new AdRequest(this);
        }
    }
}
