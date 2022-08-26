package com.luvris2.publicperfomancedisplayapp.model;

public class Data {

        int color;
        String title;

        public void DataPage(int color, String title){
            this.color = color;
            this.title = title;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

}
