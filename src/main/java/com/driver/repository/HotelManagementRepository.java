package com.driver.repository;

import com.driver.model.Booking;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class HotelManagementRepository {

    private HashMap<String, Hotel> hotelDb;
    private HashMap<Integer, User> userDb;
    private HashMap<String, Booking> bookingDb;
    public HotelManagementRepository() {
        this.hotelDb = new HashMap<>();
        this.userDb = new HashMap<>();
        this.bookingDb = new HashMap<>();
    }

    public HashMap<String, Booking> getBookingDb() {
        return bookingDb;
    }

    public void setBookingDb(HashMap<String, Booking> bookingDb) {
        this.bookingDb = bookingDb;
    }

    public HashMap<String, Hotel> getHotelDb() {
        return hotelDb;
    }

    public void setHotelDb(HashMap<String, Hotel> hotelDb) {
        this.hotelDb = hotelDb;
    }

    public HashMap<Integer, User> getUserDb() {
        return userDb;
    }

    public void setUserDb(HashMap<Integer, User> userDb) {
        this.userDb = userDb;
    }


}
