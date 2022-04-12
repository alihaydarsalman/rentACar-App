package com.turkcell.rentACar.business.constants.messages;

public final class BusinessMessages {


    /********************************* GLOBAL STATUS STRINGS *********************************/

    //SUCCESS
    public static final String SUCCESS_LIST = "Listed Successfully.";
    public static final String SUCCESS_GET = "Getted Successfully.";
    public static final String SUCCESS_ADD = "Added Successfully.";
    public static final String SUCCESS_UPDATE = "Updated Successfully.";
    public static final String SUCCESS_DELETE = "Deleted Successfully.";
    public static final String SUCCESS_LIST_PAGED = "Paged Listed Successfully.";


    /********************************* SPECIAL STATUS STRINGS *********************************/



    //COLOR
    public static final String ERROR_COLOR_NAME_EXISTS = "Color name has already exists.";
    public static final String ERROR_COLOR_NOT_FOUND = "Color not found! Color id is not exists.";


    //BRAND
    public static final String ERROR_BRAND_NAME_EXISTS = "Brand name has already exists.";
    public static final String ERROR_BRAND_NOT_FOUND = "Brand not found! Brand id is not exists.";


    //CAR
    public static final String ERROR_CAR_NOT_FOUND = "Car not found! Car id is not exists.";
    public static final String ERROR_BRAND_CANNOT_DELETE = "The brand cannot delete. Because brand is using by car.";
    public static final String ERROR_COLOR_CANNOT_DELETE = "The color cannot delete. Because brand is using by car.";
    public static final String ERROR_CAR_CANNOT_DELETE = "The car cannot delete. Because car is under maintenance.";


    //CAR_MAINTENANCE
    public static final String ERROR_MAINTENANCE_NOT_FOUND = "Car maintenance information not found! Car maintenance id is not exists.";
    public static final String ERROR_MAINTENANCE_NOT_FOUND_BY_CAR_ID = "Car maintenance information not found! Car is not exists on maintenance table.";
    public static final String ERROR_MAINTENANCE_RETURN_DATE_CANNOT_BEFORE_NOW = "Car maintenance return date cannot before now.";
    public static final String ERROR_CAR_ALREADY_UNDER_MAINTENANCE = "The car has already under maintenance now.";
    public static final String ERROR_CAR_ALREADY_UNDER_MAINTENANCE_CANNOT_RENTED = "You cannot rent this car. The car has already under maintenance to: ";
    public static final String ERROR_CAR_RETURNED_FROM_MAINTENANCE_CANNOT_UPDATE = "The car that has been returned from maintenance, so this maintenance cannot be updated.";
    public static final String ERROR_CAR_ALREADY_UNDER_MAINTENANCE_UNKNOWN_RETURN_DATE = "The car has already under maintenance, return date is unknown! So this car cannot rented.";

    //RENTAL
    public static final String ERROR_RENTAL_NOT_FOUND = "Rental information not found! Rent id is not exists.";
    public static final String ERROR_RENTAL_NOT_FOUND_BY_CAR_ID = "Rental information not found! Car is not exists on rental table.";
    public static final String ERROR_CAR_STILL_RENTED_CANNOT_GO_MAINTENANCE = "The car is still rented. Car cannot go maintenance.";
    public static final String ERROR_CAR_ALREADY_RENTED = "The car reserved or rented. You cannot rent this car between these dates that you entered.";
    public static final String ERROR_RENT_DATE_CANNOT_AFTER_RETURN_DATE = "Rent date cannot after return date.";
    public static final String ERROR_INVALID_DATES = "Rent date cannot before now.";

    //ADDITIONS
    public static final String ERROR_ADDITION_NOT_FOUND = "Addition not found! Addition id is not exists.";
    public static final String ERROR_ADDITION_NAME_EXISTS = "Addition name has already exists.";

    //CITY
    public static final String ERROR_CITY_NOT_FOUND = "City not found! City id is not exists.";

    //CUSTOMERS-USERS
    public static final String ERROR_USER_NOT_FOUND = "User not found! User id is not exists.";
    public static final String ERROR_TAX_NUMBER_ALREADY_EXISTS = "Tax number has already exists.";
    public static final String ERROR_EMAIL_ALREADY_EXISTS = "Email already has exists.";
    public static final String ERROR_NATIONAL_IDENTITY_ALREADY_EXISTS = "National identity has already exists.";
    public static final String ERROR_CORPORATE_CUSTOMER_NOT_FOUND = "Customer not found! Corporate customer id is not exists.";
    public static final String ERROR_INDIVIDUAL_CUSTOMER_NOT_FOUND = "Customer not found! Individual customer id is not exists.";
    public static final String ERROR_CUSTOMER_NOT_FOUND = "Customer not found! Customer id is not exists.";

    //INVOICE
    public static final String ERROR_INVOICE_NOT_FOUND = "Invoice not found! Invoice id is not exists.";
    public static final String INVOICE_NOT_FOUND_BY_CUSTOMER = "No purchase history found for this user.";


}
