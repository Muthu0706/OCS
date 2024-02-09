 package com.CourierManagement.api.controller;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CourierManagement.api.entity.CourierStaff;
import com.CourierManagement.api.entity.Parcel;
import com.CourierManagement.api.entity.User;
import com.CourierManagement.api.repository.ParcelRepository;
import com.CourierManagement.api.repository.UserRepository;
import com.CourierManagement.api.request.ParcelRequest;
import com.CourierManagement.api.response.CourierDetailsResponse;
import com.CourierManagement.api.service.ParcelService;
import com.CourierManagement.api.service.UserService;

import jakarta.servlet.http.HttpSession;



@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/login/user/parcels")
public class ParcelController {

    @Autowired
    private ParcelService parcelService;
    
    @Autowired
    private ParcelRepository parcelRepository;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private JavaMailSender javaMailSender;
    
//    //@PathVariable Long userId
//    @PostMapping("/create")
//    public ResponseEntity<String> createParcel(HttpSession session, @RequestBody ParcelRequest parcelRequest) {
//        
//    	
//    	Long userId = (Long) session.getAttribute("userId");
//        Optional<User> userOptional = userRepository.findById(userId);
//
//        if (userOptional.isPresent()) {
//            
//            parcelService.addParcel(parcelRequest, userOptional.get());
//
//            return ResponseEntity.ok("Parcel created successfully!");
//        } else {
//           
//            return ResponseEntity.badRequest().body("Error: User with ID " + userId + " not found!");
//        }
//  
//    }  
    
    
    
    @PostMapping("/create")
    public ResponseEntity<String> createParcel(HttpSession session, @RequestBody ParcelRequest parcelRequest) {
//
//        Long userId = (Long) session.getAttribute("userId");
//        Optional<User> userOptional = userRepository.findById(userId);

        //if (userOptional.isPresent()) {

            
            Parcel parcel = parcelService.addParcel(parcelRequest);//, userOptional.get())

           
            //sendEmail(userOptional.get(), parcel);

            return ResponseEntity.ok("Parcel created successfully!");
//        } else {
//
//            return ResponseEntity.badRequest().body("Error: User with ID " + userId + " not found!");
//        }

    }

    private void sendEmail(User user, Parcel parcel) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail()); 
        message.setSubject("Welcome to our courier service");
        message.setText("Dear " + user.getUsername() + ",\n\n"
                + "Thank you for using our courier service. Your parcel has been successfully created!\n\n"
                + "Sender Name: " + parcel.getUserName() + "\n"
                + "Receiver Name: " + parcel.getRecipientName() + "\n"
                + "Cost: " + parcel.getCost() + "\n\n"
                + "We appreciate your inicitives and look forward to serving you again.");

        javaMailSender.send(message);
    }   
    
//	@PutMapping("/PerformUpdate")
//	public void performUpdate(@RequestBody Parcel parcelRepository) {
//		parcelRepository.save(parcelRepository);
//	}
	  @PutMapping("/deleted/{uname}")
	    public String deleteByUName(@PathVariable String uname) {
	    	parcelService.deleteByUserName(uname);
	    	return "deleted Successfully";
	    }
	
//    @GetMapping("/all")
//    public ResponseEntity<List<Parcel>> getAllParcels(HttpSession session) {
//    	
//        List<Parcel> parcels = parcelService.getAllParcels();
//        return new ResponseEntity<>(parcels, HttpStatus.OK);
//    }
//    
    @GetMapping("/all")
	public List<Parcel> getAllParcels() {
		System.out.println("Inside view All");
		Iterator<Parcel> it = parcelRepository.findAll().iterator();

		List<Parcel> list = new ArrayList<Parcel>();
		while (it.hasNext()) {
			list.add(it.next());
		}
		return list;
	}
  
//	@GetMapping("/courier-details")
//    public void getCourierDetails() {
//                  List<Parcel> parcels = parcelService.getAllParcels();
////        List<CourierDetailsResponse> courierDetailsList = new ArrayList<>();
////        List<CourierStaff> availableStaffList = courierstaffService.getAllStaffNamess();
//
//
//        for (Parcel parcel : parcels) {
//            CourierDetailsResponse courierDetails = new CourierDetailsResponse();
////            courierDetails.setSenderName(parcel.getUser().getUsername());
////            System.out.println(parcel.getUser().getUsername());
//            courierDetails.setReceiverName(parcel.getRecipientName());
//            courierDetails.setSenderName(parcel.getUserName());
//            System.out.println(parcel.getRecipientName());
//            courierDetails.setParcelId(parcel.getId());
//            courierDetails.setCost(parcel.getCost());
////            courierDetails.setStaffNameFromParcel(availableStaffList);
//            courierDetails.setStatus(parcel.getStatus());
////            courierDetailsList.add(courierDetails);
//        }
//

	}

    


