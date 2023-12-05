package com.example.busbooking;

public class BookingBusHelperClass {
    String userID, userName, company, busName, park, route, date, time, seatNO, fee;

    public BookingBusHelperClass() {
    }

    public BookingBusHelperClass(String userID, String userName, String company, String busName, String park, String route, String date, String time, String seatNO, String fee) {
        this.userID=userID;
        this.userName = userName;
        this.company=company;
        this.busName = busName;
        this.park=park;
        this.route = route;
        this.date = date;
        this.time = time;
        this.seatNO = seatNO;
        this.fee=fee;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSeatNO() {
        return seatNO;
    }

    public void setSeatNO(String seatNO) {
        this.seatNO = seatNO;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPark() {
        return park;
    }

    public void setPark(String park) {
        this.park = park;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }
}
