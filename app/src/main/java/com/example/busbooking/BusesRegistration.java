package com.example.busbooking;

public class BusesRegistration {
    String company, busName, park, telephone, route, fee, image;

    public BusesRegistration() {
    }

    public BusesRegistration(String company, String busName, String park, String telephone, String route, String fee, String image) {
        this.company=company;
        this.busName = busName;
        this.park = park;
        this.telephone = telephone;
        this.route=route;
        this.fee=fee;
        this.image = image;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getPark() {
        return park;
    }

    public void setPark(String park) {
        this.park = park;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }
}
