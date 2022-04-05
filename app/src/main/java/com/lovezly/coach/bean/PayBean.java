package com.lovezly.coach.bean;

import java.util.List;

public class PayBean {

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
        private Integer user_id;
        private Integer order_id;
        private String order_sn;
        private Integer exam_id;
        private Integer exam_site_id;
        private Integer exam_item_id;
        private String book_date;
        private String name;
        private String cardNum;
        private String mobile;
        private Integer pay_status;
        private Integer status;
        private String passtime;
        private String createtime;
        private String updatetime;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getUser_id() {
            return user_id;
        }

        public void setUser_id(Integer user_id) {
            this.user_id = user_id;
        }

        public Integer getOrder_id() {
            return order_id;
        }

        public void setOrder_id(Integer order_id) {
            this.order_id = order_id;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public Integer getExam_id() {
            return exam_id;
        }

        public void setExam_id(Integer exam_id) {
            this.exam_id = exam_id;
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

        public String getBook_date() {
            return book_date;
        }

        public void setBook_date(String book_date) {
            this.book_date = book_date;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCardNum() {
            return cardNum;
        }

        public void setCardNum(String cardNum) {
            this.cardNum = cardNum;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public Integer getPay_status() {
            return pay_status;
        }

        public void setPay_status(Integer pay_status) {
            this.pay_status = pay_status;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getPasstime() {
            return passtime;
        }

        public void setPasstime(String passtime) {
            this.passtime = passtime;
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
    }
}
