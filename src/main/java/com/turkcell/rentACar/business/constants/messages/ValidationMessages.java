package com.turkcell.rentACar.business.constants.messages;


public final class ValidationMessages {

    /********************************* GLOBAL STATUS STRINGS *********************************/

    public static final String ID_CANNOT_LESS_THEN_ONE="Id cannot less than or equal zero.";
    public static final String PRICE_CANNOT_NEGATIVE="Price cannot negative.";


    //FOR ALL VALIDATIONS IN REQUESTS PACKAGE
    public static final String ADDITION_NAME_VALIDATION_ERROR = "Addition name size has to be 2-50 characters. And name cannot contains special characters.";
    public static final String BRAND_NAME_VALIDATION_ERROR = "Brand name size has to be 2-50 characters. And name cannot contains special characters.";
    public static final String MAINTENANCE_DESCRIPTION_VALIDATION_ERROR = "Maintenance description size has to be 10-200 characters. And description cannot contains special characters.";
    public static final String CAR_DESCRIPTION_VALIDATION_ERROR = "Car description size has to be 3-100 characters. And description cannot contains special characters.";
    public static final String CAR_MODEL_YEAR_VALIDATION_ERROR = "We don't sell car that older than 1990.";
    public static final String CITY_NAME_VALIDATION_ERROR = "City name size has to be 3-50 characters. And name cannot contains special characters.";
    public static final String COLOR_NAME_VALIDATION_ERROR = "Color name size has to be 3-50 characters. And name cannot contains special characters.";
    public static final String TAX_NUMBER_VALIDATION_ERROR = "Tax number length has to be 10 characters and each character has to be a number.";
    public static final String COMPANY_NAME_VALIDATION_ERROR = "Company name size has to be 5-50. And name cannot contains special characters.";
    public static final String CUSTOMER_NAME_VALIDATION_ERROR = "Last name size has to be 2-50. And name cannot contains special characters.";
    public static final String CUSTOMER_LAST_NAME_VALIDATION_ERROR = "Last name size has to be 2-50. And name cannot contains special characters.";
    public static final String NATIONAL_IDENTITY_VALIDATION_ERROR = "National identity length has to be 11 characters and each characters has to be number.";
    public static final String PASSWORD_VALIDATION_ERROR = "Password must contain at least digit, lowercase and uppercase and at least once special chars and password size has to be 8-20 chars.";
    public static final String DAMAGE_DESCRIPTION_VALIDATION_ERROR = "Damage description size has to be 3-100 characters. And description cannot contains special characters.";
    public static final String KILOMETER_VALIDATION_ERROR = "Kilometer informations cannot be less than zero.";
    public static final String PAYMENT_CARDHOLDER_VALIDATION_ERROR = "Bu alan 5 ila 40 karakter boyutunda harflerden oluşmalıdır.";
    public static final String PAYMENT_MONTH_VALIDATION_ERROR = "Bu alan 4 karakter boyutunda olmalı ve rakamlardan oluşmalıdır.";
    public static final String PAYMENT_YEAR_VALIDATION_ERROR = "Bu alan 2 karakter boyutunda olmalı ve 1 ila 12 arasındaki sayılardan oluşmalıdır.";
    public static final String PAYMENT_CVV_VALIDATION_ERROR = "Bu alan 3 karakter boyutunda olmalı ve 0 ile başlamayan rakamlardan oluşmalıdır.";
    public static final String PAYMENT_CARD_NUMBER_VALIDATION_ERROR = "Bu alan 16 karakter boyutunda olmalı ve rakamlardan oluşmalıdır.";
}
