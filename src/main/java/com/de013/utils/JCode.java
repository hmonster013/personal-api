package com.de013.utils;

public class JCode {

	public static String SUCCESS ="000";
	public static String CODE ="200";
   //000 --> thành công, 0xx --> Error
	public static enum CommonCode {
		ACCESS_DENY("001"),
		REQUEST_INVALID("002"),
		NOT_FOUND("003"),
		CONFLICT("004"),
		PROCESSING("005"),
		SIGN_INVALID("006"),
		STATUS_INVALID("007"),
		CONTACT_SUPPORT("008"),
        TIMEOUT("098"),
		SYSTEM_ERROR("099"), 
		 C300("300"),
		;
        public final String code;
		CommonCode(String code) {
			this.code = code;
		}
    }
	
	//1xx khac 000
	public static enum UserCode {
		PHONE_EXISTED("101"),
		EMAIL_EXISTED("102"), 
		USER_NAME_EXISTED("103"), 
		IDENTITY_INVALID("104"), 
		NOT_ACTIVE("105"), 
		NOT_LINK_BANK("106"),
		USER_ID_NOT_FOUND("107"),
		PASSWORD_INVALID("108"),
		USER_NAME_INVALID("109"),
		EMAIL_INVALID("110"),
		PIN_FAIL("110"),// LOGIN
		PIN_FAIL_WARN("111"),// LOGIN WARN
		PIN_FAIL_LOCK("112"),// LOGIN LOCK
		OTP_FAIL("113"),
		OTP_FAIL_WARN("114"),
		OTP_FAIL_LOCK("115"),
		USER_BLOCK("116"),
		NOT_ENOUGH_BALANCE("119"),
		DEVICE_INVALID("120"), 
		DEVICE_EXISTED("121"), 
		CODE_EXISTED("122"),
		EMAIL_NOT_EXISTS("123"),
		NOT_ENOUGH_POKE("124"),
		NOT_ENOUGH_TICKET("125")
		;
		public final String code;

		UserCode(String code) {
			this.code = code;
		}
	}
	
	//1xx khac 000
		public static enum TourCode {
			MAX_TOUR_LIMIT("201"),
			AMOUNT_INVALID("202"),
			MAX_ROOM_LIMIT("203"),
			MAX_DEPOSIT_TICKET("204"),
			HAS_BUY_PRODUCT("205"),
			;
			public final String code;

			TourCode(String code) {
				this.code = code;
			}
		}
   
}

