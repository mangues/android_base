package com.mangues.lifecircleapp.model;

import com.mangues.lifecircleapp.data.cache.SecureSharedPreferences;

import java.io.Serializable;

/**
 * Created by mangues on 16/8/1.
 */

public class CircleModel implements Serializable {

        private String id;

        private String name;

        private String content;

        private Double longitude;

        private Double latitude;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id == null ? null : id.trim();
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name == null ? null : name.trim();
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content == null ? null : content.trim();
        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }

        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }
}
