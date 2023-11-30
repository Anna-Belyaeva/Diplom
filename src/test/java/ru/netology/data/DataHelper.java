package ru.netology.data;


import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class DataHelper {

    private static Faker faker = new Faker(new Locale("en"));

    private DataHelper() {

    }

    public static CardInfo getValidCardInfo() {
        return new CardInfo(getValidCardNumber(), generateValidMonth(), generateValidYear(), generateValidCardholderName(), generateCVC());
    }

    public static CardInfo cardNumberValidate(String number) {
        return new CardInfo(number, generateValidMonth(), generateValidYear(), generateValidCardholderName(), generateCVC());
    }

    public static CardInfo monthValidate(String month) {
        return new CardInfo(getValidCardNumber(), month, generateValidYear(), generateValidCardholderName(), generateCVC());
    }

    public static CardInfo yearValidate(String year) {
        return new CardInfo(getValidCardNumber(), generateValidMonth(), year, generateValidCardholderName(), generateCVC());
    }

    public static CardInfo cardholderNameValidate(String name) {
        return new CardInfo(getValidCardNumber(), generateValidMonth(), generateValidYear(), name, generateCVC());
    }

    public static CardInfo cvcCodeValidate(String cvc) {
        return new CardInfo(getValidCardNumber(), generateValidMonth(), generateValidYear(), generateValidCardholderName(), cvc);
    }

    public static String getValidCardNumber() {
        return "4444 4444 4444 4441";
    }

    public static String getInValidCardNumber() {

        return "4444 4444 4444 4442";
    }

    public static String generateValidMonth() {

        return LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String generateValidYear() {

        return LocalDate.now().format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String generateValidCardholderName() {
        return faker.name().lastName() + " " + faker.name().firstName();
    }

    public static String generateCVC() {

        return String.format(faker.number().digits(3));
    }


    @Value
    public static class CardInfo {
        String cardNumber;
        String monthValidity;
        String yearValidity;
        String cardholderName;
        String cvcCode;
    }

    @Value
    public static class PaymentStatus {
        String status;
    }
}
