package com.chips.xmlhandler;

public interface TagStateEnum {
	enum ShoppingItemTagState implements TagStateEnum {
		CREATED_AT, DESC, ID, UPDATED_AT, USER_ID
	}
	enum FridgeFoodTagState implements TagStateEnum {
		CREATED_AT, DESC, EXPIRATION, ID, UPDATED_AT, USER_ID
	}	
}
