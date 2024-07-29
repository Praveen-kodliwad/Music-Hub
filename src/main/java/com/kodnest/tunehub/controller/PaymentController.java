package com.kodnest.tunehub.controller;

import org.json.JSONObject; // Import the correct class
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kodnest.tunehub.entity.User;
import com.kodnest.tunehub.services.UserService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;

import jakarta.servlet.http.HttpSession;

@Controller
public class PaymentController {
    @Autowired
    UserService userService;

    @GetMapping("/pay")
    public String pay() {
        return "pay";
    }

    @SuppressWarnings("finally")
    @PostMapping("/createOrder")
    @ResponseBody
    public String createOrder(HttpSession session) {
        int amount = 5;
        Order order = null; 
        try {
            RazorpayClient razorpay = new RazorpayClient("rzp_test_D1QhAHfO8VAV8q", "P1gpfcN4DEJV18Tu25jHw34m");

            JSONObject orderRequest = new JSONObject(); 
            orderRequest.put("amount", amount * 100); 
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "order_rcptid_11");

            order = razorpay.orders.create(orderRequest);
        } catch (RazorpayException e) {
            e.printStackTrace();
        } finally {
            return order.toString();
        }
    }
    @PostMapping("/verify")
	@ResponseBody
	public boolean verifyPayment(@RequestParam  String orderId, @RequestParam String paymentId,
											@RequestParam String signature) {
	    try {
	        // Initialize Razorpay client with your API key and secret
	        RazorpayClient razorpayClient = new RazorpayClient("rzp_test_D1QhAHfO8VAV8q", 
	        								"P1gpfcN4DEJV18Tu25jHw34m");
	        // Create a signature verification data string
	        String verificationData = orderId + "|" + paymentId;

	        // Use Razorpay's utility function to verify the signature
	        boolean isValidSignature = Utils.verifySignature(verificationData, signature, 
	        													"P1gpfcN4DEJV18Tu25jHw34m");

	        return isValidSignature;
	    } catch (RazorpayException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
    @GetMapping("/Payment-success")
    public String paymentSuccess(HttpSession session) {
      String mail = (String) session.getAttribute("email"); 
      
      User user = userService.getUser(mail);
      user.setIspremium(true);
      userService.updateUser(user);
        return "customerhome";
    }
    @GetMapping("/Payment-failed")
    public String paymentFailure(@RequestParam String param) {
        return null;
    }
    
    
}
