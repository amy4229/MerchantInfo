package com.pagrptest2.domain;

import java.io.Serializable;


import org.springframework.data.mongodb.core.mapping.Document;


/**
 * store DTO
 * @author Hwasun Kim
 *
 */
@Document(collection="shop")
public class MerchantDomain implements Serializable{
	
	private static final long serialVersionUID=1L;
	
	
	/**
	 * shop name
	 * String 
	 */
	private String shop;
	
    /**
     * startDate
     * date
     */
    private String startDate;
    
    	
    /**
     * endDate
     * date
     */
    private String endDate;

	/**
	 * @return the shop
	 */
	public String getShop() {
		return shop;
	}

	/**
	 * @param shop the shop to set
	 */
	public void setShop(String shop) {
		this.shop = shop;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
    
    
}
