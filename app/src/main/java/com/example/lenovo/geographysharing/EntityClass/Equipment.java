package com.example.lenovo.geographysharing.EntityClass;


public class Equipment {
    private String equip_name;//设备名称
    private String equip_property;//设备性能
    private String equip_address;//设备地址
    private String equip_price;//设备价格
    private int equip_image_id;//设备图片

    //构造函数，注意传参
    public Equipment(String equip_name, String equip_property, String equip_address, String equip_price, int equip_image_id) {
        this.equip_name = equip_name;
        this.equip_property = equip_property;
        this.equip_address = equip_address;
        this.equip_price = equip_price;
        this.equip_image_id = equip_image_id;
    }

    //设置设备名称
    public void setEquipName(String name) {
        equip_name = name;
    }

    //设置设备性能
    public void setEquipproperty(String property) {
        equip_property = property;
    }

    //设置设备的地址
    public void setAddress(String address) {
        equip_address = address;
    }

    //设置设备的价格
    public void setPrice(String price) {
        equip_price = price;
    }

    //设置设备的图片
    public void setEquipImageid(int imageid) {
        equip_image_id = imageid;
    }


    //获取设备名称
    public String getEquipName() {
        return equip_name;
    }

    //获取设备性能
    public String getEquipProperty() {
        return equip_property;
    }

    //获取设备地点
    public String getEquipAdress() {
        return equip_address;
    }

    //获取设备价格
    public String getEquipPrice() {
        return equip_price;
    }

    //获取设备图片
    public int getEquipImageID() {
        return equip_image_id;
    }
}
