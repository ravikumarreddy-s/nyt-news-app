package com.abc.news.consumer.model;

import java.time.ZonedDateTime;

public class Item {
        private String title;
        private String link;
        private String description;
        private String pubDate;
        private MediaContent mediaContent;

        public String getTitle() { return title; }

        public void setTitle(String title) { this.title = title; }

        public String getLink() { return link; }

        public void setLink(String link) { this.link = link; }

        public String getDescription() { return description; }

        public void setDescription(String description) { this.description = description; }

        public String getPubDate() { return pubDate; }

        public void setPubDate(String pubDate) { this.pubDate = pubDate; }

        public MediaContent getMediaContent() { return mediaContent; }
        public void setMediaContent(MediaContent mediaContent) { this.mediaContent = mediaContent; }

        public static class MediaContent {
                private String url;
                private String medium;
                private int height;
                private int width;

                public String getUrl() { return url; }
                public void setUrl(String url) { this.url = url; }

                public String getMedium() { return medium; }
                public void setMedium(String medium) { this.medium = medium; }

                public int getHeight() { return height; }
                public void setHeight(int height) { this.height = height; }

                public int getWidth() { return width; }
                public void setWidth(int width) { this.width = width; }
        }

    }

