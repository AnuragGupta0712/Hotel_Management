package com.example.hotel.service;

import com.example.hotel.communicator.RatingServiceCommunicator;
import com.example.hotel.exceptions.HotelNotFoundException;
import com.example.hotel.model.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HotelService {
List<Hotel> hotelList = new ArrayList<>();
Map<String, Hotel> hotelMap = new HashMap<>();
@Autowired
    RatingServiceCommunicator ratingServiceCommunicator;
    public void createHotel(Hotel hotel) {
        Map<String,Long> ratingsMap = new HashMap<>();
        hotelList.add(hotel);
        hotelMap.put(hotel.getId() , hotel);
        ratingsMap.put(hotel.getId() , hotel.getRating());
        ratingServiceCommunicator.addRating(ratingsMap);
    }

    public Hotel getHotelById(String id) throws HotelNotFoundException {
        if(ObjectUtils.isEmpty(hotelMap.get(id))){
            throw new HotelNotFoundException("Hotel not found");
        }
        Hotel hotel = hotelMap.get(id);
        // rest service to fetch the rating by id
        long updatedRating = ratingServiceCommunicator.getRating(id);
        hotel.setRating(updatedRating);
        //return hotelMap.get(id);
        return hotel;

    }

    public List<Hotel> getAllHotels() {
        return hotelList;
    }

    public void deleteHotelById(String id) {
        Hotel hotel = hotelMap.get(id);
        hotelList.remove(hotel);
        hotelMap.remove(id);
        ratingServiceCommunicator.deleteRating(id);
    }

    public void updateHotel(Hotel UpdatedHotel) {
        //1.Get the previous data of the hotel
        //2.Remove this old data from list
        //3.Add the updated Data to the List

        Hotel existingHotel = getHotelById(UpdatedHotel.getId());
        hotelList.remove(existingHotel);
        hotelList.add(UpdatedHotel);

        //4.Update the previous data with the new data
        //5.Update the map with the new data
        hotelMap.put(UpdatedHotel.getId(), UpdatedHotel);
        Map<String,Long> updatedRating = new HashMap<>();
        updatedRating.put(UpdatedHotel.getId(),UpdatedHotel.getRating());
        ratingServiceCommunicator.updateRating(updatedRating);

    }

    public void updateHotelWithId(String id, Hotel updatedHotel) {
        Hotel existingHotel = getHotelById(id);
        hotelList.remove(existingHotel);
        updatedHotel.setId(id);
        hotelList.add(updatedHotel);
        hotelMap.put(id, updatedHotel);
    }
}
