package com.driver.service;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import com.driver.repository.HotelManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.Array;
import java.util.*;

@Service
public class HotelManagementService {
    @Autowired
    HotelManagementRepository hotelManagementRepositoryObj;
    public String addHotel(Hotel hotel){
        if(hotel == null || hotel.getHotelName() == null){
            return null;
        }
        HashMap<String, Hotel> hotelDb = hotelManagementRepositoryObj.getHotelDb();
        if(hotelDb.containsKey(hotel.getHotelName())){
            return "FAILURE";
        }
        hotelDb.put(hotel.getHotelName(), hotel);
        hotelManagementRepositoryObj.setHotelDb(hotelDb);
        return "SUCCESS";
    }

    public Integer addUser(@RequestBody User user){
        if(user == null){
            return 0;
        }
        HashMap<Integer, User> userDb = hotelManagementRepositoryObj.getUserDb();
        if(userDb.containsKey(user.getaadharCardNo())){
            return user.getaadharCardNo();
        }
        userDb.put(user.getaadharCardNo(), user);
        hotelManagementRepositoryObj.setUserDb(user);
        return  user.getaadharCardNo();
    }

    public String getHotelWithMostFacilities(){
        HashMap<String, Hotel> hotelDb = hotelManagementRepositoryObj.getHotelDb();
        int maxFacilities = 0;
        String hotelName = "";
        for(String  hName : hotelDb.keySet()){
            int curFacilities = hotelDb.get(hName).getFacilities().size();
            if(curFacilities > maxFacilities){
                curFacilities = maxFacilities;
                hotelName = hName;
            }else if(curFacilities == maxFacilities){
                // Compare lexographically and update hotelName accordingly
                if (hName.compareTo(hotelName) < 0) {
                    hotelName = hName; //Update hotelName if hName comes before hotelName lexographically
                }

            }
        }
        return hotelName;
    }
    public int bookARoom(Booking booking){
        //setBooking & amountToBePaid, save the booking Entity and keep the bookingId as a primary key
        String bookingId = String.valueOf(UUID.randomUUID());
        booking.setBookingId(bookingId);
        HashMap<String, Booking> bookingDb = hotelManagementRepositoryObj.getBookingDb();
        HashMap<String, Hotel> hotelDb = hotelManagementRepositoryObj.getHotelDb();
        int amountToBePaid = 0;

        //Calculate the total amount paid by the person based on no. of rooms booked and price of the room per night.
        int numOfRoomsReq = booking.getNoOfRooms();//trying to book these numOfRooms
        String hotel = booking.getHotelName();
        int hotelHasRooms = hotelDb.get(hotel).getAvailableRooms();
        amountToBePaid = numOfRoomsReq * hotelDb.get(hotel).getPricePerNight();
        if(hotelHasRooms < numOfRoomsReq){
            return -1;//If there arent enough rooms available in the hotel that we are trying to book return -1
        }
        booking.setAmountToBePaid(amountToBePaid);
        bookingDb.put(bookingId, booking);
        hotelManagementRepositoryObj.setBookingDb(bookingDb);
        return amountToBePaid;
    }

    public int getBookings(Integer aadharCard)
    {
        HashMap<String, Booking> bookingDb = hotelManagementRepositoryObj.getBookingDb();
        int count = 0;
        for(Booking b : bookingDb.values()){
            int bookingAadhar = b.getBookingAadharCard();
            if(bookingAadhar == aadharCard) count++;
        }
        return  count;
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName){
    //We are having a new facilites that a hotel is planning to bring.
        //If the hotel is already having that facility ignore that facility otherwise add that facility in the hotelDb
        //return the final updated List of facilities and also update that in your hotelDb
        //Note that newFacilities can also have duplicate facilities possible
        HashMap<String, Hotel> hotelDb = hotelManagementRepositoryObj.getHotelDb();
        List<Facility> facilities = hotelDb.get(hotelName).getFacilities();
        for(Facility f : newFacilities){
           if(!facilities.contains(f)){
               facilities.add(f);
           }
        }
        Hotel updatedHotel = hotelDb.get(hotelName);
        updatedHotel.setFacilities(facilities);
        hotelDb.put(hotelName, updatedHotel);
        hotelManagementRepositoryObj.setHotelDb(hotelDb);
        return updatedHotel;
    }
}
