package com.example.doanthangcanh;

public class thanhpho {
    String id;
    public thanhpho() {
    }
    public thanhpho(String id, String anhtp, String tentp) {
        this.id = id;
        this.anhtp = anhtp;
        this.tentp = tentp;
    }
    String anhtp;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getAnhtp() {
        return anhtp;
    }
    public void setAnhtp(String anhtp) {
        this.anhtp = anhtp;
    }
    public String getTentp() {
        return tentp;
    }
    public void setTentp(String tentp) {
        this.tentp = tentp;
    }
    String tentp;

}
