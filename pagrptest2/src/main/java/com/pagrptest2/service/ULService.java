package com.pagrptest2.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVReader;
import com.pagrptest2.domain.MerchantDomain;

@Service
public class ULService {

	/*@Autowired
	private MerchantDao merchantDao;*/
	
	public List<MerchantDomain> uploadCSV(MultipartFile file) throws Exception {
		CSVReader csvReader=null;
		List<MerchantDomain> merchantList = new ArrayList<>();
		try {
			csvReader = new CSVReader(new InputStreamReader(file.getInputStream()));
			String[] line = csvReader.readNext();
			HashSet<String> shopName = new HashSet<>();
			while ((line  = csvReader.readNext()) != null) {
				//parse file and split
				MerchantDomain merchant = new MerchantDomain();
				String shop = line[0];
				String endDate = line[2];
				if(!checkEndDate(endDate)) {
					throw new Exception("End date must be in future only.");
				}
				shopName.add(shop);
				merchant.setShop(shop);
				merchant.setStartDate(line[1]);
				merchant.setEndDate(endDate);
				merchantList.add(merchant);
			}
			
			//duplicate file check
			if(shopName.size() < merchantList.size()) {
				throw new Exception("No duplicate shops allowed in the file.");
			}
			
		} catch (IOException e) {
			throw new Exception("Fail to read. please try again");
		} finally {
				csvReader.close();
		}
		return merchantList;
	}

	
	public boolean checkEndDate(String endDate) {
		boolean result=true;
		
		//current time
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String currentTime = String.valueOf(sdf.format(new Date()));
		
		//compare to current time and endDate
		result = currentTime.compareTo(endDate)<0?true:false;
		
		return result;
	}
	
}
