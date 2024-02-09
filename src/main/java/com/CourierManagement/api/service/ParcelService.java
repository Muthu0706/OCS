package com.CourierManagement.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CourierManagement.api.entity.Parcel;
import com.CourierManagement.api.repository.ParcelRepository;
import com.CourierManagement.api.request.ParcelRequest;

import jakarta.transaction.Transactional;

@Service
public class ParcelService {

    @Autowired
    private ParcelRepository parcelRepository;

    public Parcel addParcel(ParcelRequest parcelRequest) {
        Parcel parcel = new Parcel();
        parcel.setRecipientName(parcelRequest.getRecipientName());
        parcel.setFromAddress(parcelRequest.getFromAddress());
        parcel.setRecipientAddress(parcelRequest.getRecipientAddress());
        parcel.setParcelName(parcelRequest.getParcelName());
        parcel.setWeight(parcelRequest.getWeight());
        parcel.setRecipientPhoneNumber(parcelRequest.getRecipientPhoneNumber());
        parcel.setStatus("Pending");
        parcel.setUserName(parcelRequest.getUserName());

        
//        int cost = (parcel.getWeight() <= 50) ? 2550 : 5590;
//        parcel.setCost(cost);

        return parcelRepository.save(parcel);
    }

    public List<Parcel> getAllParcels() {
        return parcelRepository.findAll();
    }
    
    @Transactional
   public void deleteByUserName(String uname){
	    parcelRepository.deleteAllByUserName(uname);
   }
    
   

    public void changeCourierStatus(Long courierId, String newStatus) {
        
        Parcel parcel = parcelRepository.findById(courierId).orElse(null);
        if (parcel != null) {
            parcel.setStatus(newStatus);
            parcelRepository.save(parcel);
        }
    }
    
    
    public List<Parcel> getParcelsByStaffName(String staffName) {
        return parcelRepository.findByCourierStaff_StaffName(staffName);
    }
    
    
    
}
