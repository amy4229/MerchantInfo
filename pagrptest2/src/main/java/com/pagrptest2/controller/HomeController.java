package com.pagrptest2.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.pagrptest2.domain.MerchantDomain;
import com.pagrptest2.service.ULService;

/**
 * Controller
 * @author Amy
 *
 */
@Controller
public class HomeController {
	/**
	 * ulService
	 */
	@Autowired
	ULService ulService;
	
	/**
	 * Controller for upload
	 * @param file
	 * @param model
	 * @return result
	 */
	@RequestMapping(value = "/uploadCSV", method = RequestMethod.POST)
	public @ResponseBody Map <String, Object> uploadCSV(MultipartFile file,Model model) {
		Map <String, Object> result= new HashMap<>();
		try {
			List<MerchantDomain> merchantList = ulService.uploadCSV(file);
			result.put("result", merchantList);
			model.addAttribute("result", merchantList);
			
		}catch (Exception e) {
			result.put("error", e.getMessage());
		} 
		return result;
	}
	
	/**
	 * Controller for main
	 * @param model
	 */
	@RequestMapping("/index")
	public void index(Model model) {
		String msg = "";
		model.addAttribute("msg",msg);
	}
	
	
}
