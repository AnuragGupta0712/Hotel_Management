package com.example.hotel.controller;

import com.example.hotel.exceptions.BadRequestException;
import com.example.hotel.model.Hotel;
import com.example.hotel.service.HotelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotel")
public class HotelController {
@Autowired
HotelService hotelService;
    @PostMapping("/create")
    public void createHotel(@Valid @RequestBody Hotel hotel, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
           throw new BadRequestException("Bad request,please try again");
        }
        hotelService.createHotel(hotel);
    }
    @GetMapping("/id/{id}")
    public Hotel getHotelById(@PathVariable String id) {
        return hotelService.getHotelById(id);
    }

    @GetMapping("/getAll")
    public List<Hotel> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @DeleteMapping("/remove/id/{id}")
    public void deleteHotelById(@PathVariable String id) {
        hotelService.deleteHotelById(id);
    }

    @PutMapping("/update")
    public void updateHotel(@RequestBody Hotel hotel) {
        hotelService.updateHotel(hotel);
    }

    @PutMapping("/updatebyID/id/{id}")
    public void updateHotel(@PathVariable String id, @RequestBody Hotel hotel) {
        hotelService.updateHotelWithId(id,hotel);
    }
}
