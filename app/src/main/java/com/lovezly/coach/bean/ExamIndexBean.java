package com.lovezly.coach.bean;

import java.util.List;

public class ExamIndexBean {

    private Integer total;
    private Integer per_page;
    private Integer current_page;
    private Integer last_page;
    private List<DataBean> data;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPer_page() {
        return per_page;
    }

    public void setPer_page(Integer per_page) {
        this.per_page = per_page;
    }

    public Integer getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(Integer current_page) {
        this.current_page = current_page;
    }

    public Integer getLast_page() {
        return last_page;
    }

    public void setLast_page(Integer last_page) {
        this.last_page = last_page;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private Integer id;
        private String name;
        private String item_name;
        private String image;
        private Integer exam_site_id;
        private Integer exam_item_id;
        private String address;
        private String images;
        private String description;
        private double price;
        private double agent_price;
        private String book_date;
        private String trainingtime;
        private String createtime;
        private String updatetime;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public double getAgent_price() {
            return agent_price;
        }

        public void setAgent_price(double agent_price) {
            this.agent_price = agent_price;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getItem_name() {
            return item_name;
        }

        public void setItem_name(String item_name) {
            this.item_name = item_name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public Integer getExam_site_id() {
            return exam_site_id;
        }

        public void setExam_site_id(Integer exam_site_id) {
            this.exam_site_id = exam_site_id;
        }

        public Integer getExam_item_id() {
            return exam_item_id;
        }

        public void setExam_item_id(Integer exam_item_id) {
            this.exam_item_id = exam_item_id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getBook_date() {
            return book_date;
        }

        public void setBook_date(String book_date) {
            this.book_date = book_date;
        }

        public String getTrainingtime() {
            return trainingtime;
        }

        public void setTrainingtime(String trainingtime) {
            this.trainingtime = trainingtime;
        }
    }
}
