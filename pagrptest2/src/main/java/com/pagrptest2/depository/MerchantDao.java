package com.pagrptest2.depository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.pagrptest2.domain.MerchantDomain;
import com.pagrptest2.domain.ShopRepo;

public class MerchantDao {
	@Autowired
	private ShopRepo shopRepo;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	
	public List<MerchantDomain> findbyShop(String shop){
		return shopRepo.findByShop(shop);
	}
	
	public void insert(MerchantDomain merchant){
		mongoTemplate.insert(merchant);
	}
	
	public List<MerchantDomain> findActiveMerchant(String inputDate){
		Query query = new Query();
		query.addCriteria(Criteria.where("startDate").gte(inputDate)).addCriteria(Criteria.where("endDate").lte(inputDate));
		return mongoTemplate.find(query, MerchantDomain.class);
	}
	
	
	
	
}
