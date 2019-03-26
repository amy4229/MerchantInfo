package com.pagrptest2.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVReader;
import com.pagrptest2.domain.MerchantDomain;

/**
 * Merchant info UL Service class
 * @author Amy
 *
 */
/**
 * @author Amy
 *
 */
@Service
public class MerchantULService {

	private static final String SAVE_PATH="C:\\kimULsys\\";
	private static final Logger LOGGER = LogManager.getLogger(MerchantULService.class);
	
	/**
	 * upload file
	 * @param file
	 * @return merchant info
	 * @throws Exception
	 */
	public List<MerchantDomain> uploadCSV(MultipartFile file) throws Exception {
		//save path
		CSVReader csvReader=null;
		List<MerchantDomain> merchantList = new ArrayList<>();
		FileOutputStream fos=null;
		try {
			csvReader = new CSVReader(new InputStreamReader(file.getInputStream()));
			String[] line = csvReader.readNext();
			int count=1;
			//file header check
			if(line.length<3 || !line[0].equals("shop")||!line[1].equals("start_date")||!line[2].equals("end_date")) {
				LOGGER.error("Header Check : " + line);
				throw new Exception("please check your file");
			}
			
			HashSet<String> shopName = new HashSet<>();
			while ((line  = csvReader.readNext()) != null) {
				//parse file and split
				MerchantDomain merchant = new MerchantDomain();
				String shop = line[0];
				String startDate = line[1];
				String endDate = line[2];
				//validate2 EndDate check
				if(!checkEndDate(endDate)) {
					LOGGER.error("EndDate check : " + endDate);
					throw new Exception("End date must be in future only.");
				}
				//validate date format check
				if(!validateDate(endDate)||!validateDate(startDate)) {
					LOGGER.error("date format check : startdate [ "+startDate+" ]  end_date:[ " + endDate+" ]");
					throw new Exception("please check your date format(YYYYMMdd) ");
				}
				shopName.add(shop);
				merchant.setShop(shop);
				merchant.setStartDate(line[1]);
				merchant.setEndDate(endDate);
				merchantList.add(merchant);
				LOGGER.debug(count+" : "+merchant.toString());
			}
			
			//duplicate file check
			if(shopName.size() < merchantList.size()) {
				LOGGER.error("duplicate file check");
				throw new Exception("No duplicate shops allowed in the file.");
			}
			//file upload
			File saveDir = new File(SAVE_PATH);
			File uploadFile= new File(SAVE_PATH,System.currentTimeMillis()+file.getOriginalFilename());
			if(!saveDir.exists()) {
				saveDir.mkdirs();
			}
			fos= new FileOutputStream(uploadFile);
    		IOUtils.copy(file.getInputStream(),fos);
    		
		} catch (IOException e) {
			LOGGER.error("IOException :" + e.getMessage() +"  caused by" + e.getCause());
			throw new Exception("Fail to upload. please try again");
		} finally {
				csvReader.close();
		}
		return merchantList;
	}

	
	/**
	 * method for checking endDate
	 * @param endDate
	 * @return result
	 */
	private boolean checkEndDate(String endDate) {
		boolean result=true;
		
		//current time
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String currentTime = String.valueOf(sdf.format(new Date()));
		
		//compare to current time and endDate
		result = currentTime.compareTo(endDate)<0?true:false;
		
		return result;
	}
	
	
	private boolean validateDate(String date) {
		SimpleDateFormat dateFormatParser = new SimpleDateFormat("YYYYMMdd");
        dateFormatParser.setLenient(false);
        try {
            dateFormatParser.parse(date);
            return true;
        } catch (Exception Ex) {
            return false;
        }
	}
}
