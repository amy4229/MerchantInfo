package com.pagrptest2.domain;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShopRepo extends MongoRepository<MerchantDomain, Long>{
	public List<MerchantDomain> findByShop(String shop);
	public List<MerchantDomain> findAll();
}
