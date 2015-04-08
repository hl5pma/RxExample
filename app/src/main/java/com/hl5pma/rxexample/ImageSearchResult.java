package com.hl5pma.rxexample;

import java.util.List;

public class ImageSearchResult {

    public Channel channel;

    public static class Channel {
        public int result;
        public int pageCount;
        public String title;
        public int totalCount;
        public String description;
        public List<Item> item;
        public String lastBuildDate;
        public String link;
        public String generator;

        public static class Item {
            public String pubDate;
            public String title;
            public String thumbnail;
            public String cp;
            public int height;
            public String link;
            public int width;
            public String image;
        }
    }
}
