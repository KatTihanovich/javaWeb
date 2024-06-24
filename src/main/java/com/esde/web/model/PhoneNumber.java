package com.esde.web.model;

import java.io.InputStream;

public class PhoneNumber {
    private String lastname;
    private String phone;
    private InputStream photo;
    private String base64Image;
    private int userId;

    public PhoneNumber(String lastname, String phone, InputStream photo, int userId) {
        this.lastname = lastname;
        this.phone = phone;
        this.photo = photo;
        this.userId = userId;
    }

    public PhoneNumber(String lastname, String phone, String base64Image) {
        this.lastname = lastname;
        this.phone = phone;
        this.base64Image = base64Image;
    }

    public PhoneNumber(String lastname, String phone, int userId) {
        this.lastname = lastname;
        this.phone = phone;
        this.userId = userId;
    }

    public PhoneNumber() {
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public InputStream getPhoto() {
        return photo;
    }

    public void setPhoto(InputStream photo) {
        this.photo = photo;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String toString() {
        return "Phone{" +
                ", name='" + lastname + '\'' +
                ", phone='" + phone + '\'' +
                ", photoUrl='" + photo + '\'' +
                '}';
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof PhoneNumber)) return false;
        if (!super.equals(object)) return false;
        PhoneNumber phone1 = (PhoneNumber) object;
        return java.util.Objects.equals(getLastname(), phone1.getLastname()) && java.util.Objects.equals(getPhone(), phone1.getPhone()) && java.util.Objects.equals(getPhoto(), phone1.getPhoto());
    }

    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), getLastname(), getPhone(), getPhoto());
    }
}