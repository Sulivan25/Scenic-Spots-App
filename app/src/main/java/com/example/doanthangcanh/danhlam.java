package com.example.doanthangcanh;

public class danhlam {
   String id;
    String anh1;
    String video;
    public String getVideo() {
        return video;
    }
    public void setVideo(String video) {
        this.video = video;
    }
    public String getMotachitiet() {
        return motachitiet;
    }
    public void setMotachitiet(String motachitiet) {
        this.motachitiet = motachitiet;
    }
    String motachitiet;
    public String getAnh2() {
        return anh2;
    }
    public void setAnh2(String anh2) {
        this.anh2 = anh2;
    }
    String anh2;
    public String getIdtp() {
        return idtp;
    }
    public void setIdtp(String idtp) {
        this.idtp = idtp;
    }
    String idtp;



    String lat;
    String lon;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public danhlam() {
    }
    public danhlam(String id, String anh1,String anh2, String ten, String mota,String motachitiet, String diadiem, boolean thich,String idtp,String video,String lat,String lon) {
        this.id = id;
        this.anh1 = anh1;
        this.ten = ten;
        this.mota = mota;
        this.diadiem = diadiem;
        this.thich = thich;
        this.anh2=anh2;
        this.motachitiet=motachitiet;
        this.idtp=idtp;
        this.video=video;
        this.lat=lat;
        this.lon=lon;
    }
    String ten;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getAnh1() {
        return anh1;
    }
    public void setAnh1(String anh1) {
        this.anh1 = anh1;
    }
    public String getTen() {
        return ten;
    }
    public void setTen(String ten) {
        this.ten = ten;
    }
    public String getMota() {
        return mota;
    }
    public void setMota(String mota) {
        this.mota = mota;
    }
    public String getDiadiem() {
        return diadiem;
    }
    public void setDiadiem(String diadiem) {
        this.diadiem = diadiem;
    }
    boolean thich;
    public void setThich(boolean thich) {
        this.thich = thich;
    }
    String mota;
    String diadiem;
    public boolean isThich() {
        return thich;
    }
}