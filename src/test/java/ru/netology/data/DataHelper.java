package ru.netology.data;


import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;


public class DataHelper {

    private static Faker faker = new Faker(new Locale("en"));

    private DataHelper() {

    }

    public static CardInfo getValidCardInfo() {
        return new CardInfo(getValidCardNumber(), generateValidMonth(), generateValidYear(), generateLatin(), generateNumbers(3));
    }

    public static CardInfo getEmptyCardInfo() {
        return new CardInfo("", "", "", "", "");
    }

    public static CardInfo cardNumberValidate(String number) {
        return new CardInfo(number, generateValidMonth(), generateValidYear(), generateLatin(), generateNumbers(3));
    }

    public static CardInfo monthValidate(String month) {
        return new CardInfo(getValidCardNumber(), month, generateValidYear(), generateLatin(), generateNumbers(3));
    }

    public static CardInfo yearValidate(String year) {
        return new CardInfo(getValidCardNumber(), generateValidMonth(), year, generateLatin(), generateNumbers(3));
    }

    public static CardInfo cardholderNameValidate(String name) {
        return new CardInfo(getValidCardNumber(), generateValidMonth(), generateValidYear(), name, generateNumbers(3));
    }

    public static CardInfo cvcCodeValidate(String cvc) {
        return new CardInfo(getValidCardNumber(), generateValidMonth(), generateValidYear(), generateLatin(), cvc);
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

    public static String generateNumbers(int quantity) {
        return String.format(faker.number().digits(quantity));
    }

    public static String generateNumbersBetween(int min, int max) {
        return String.format("%02d", faker.number().numberBetween(min, max));
    }

    public static String generateZero(int quantity) {
        String zero = "0";
        for (int i = quantity - 1; i > 0; i--) {
            zero = zero + "0";
        }
        return zero;
    }

    public static String generateLatin() {
        return faker.name().lastName() + " " + faker.name().firstName();
    }

    public static String generateCyrillic() {
        var faker = new Faker(new Locale("ru"));
        return faker.name().lastName() + " " + faker.name().firstName();
    }

    public static String generateHieroglyph() {
        String[] genJap = {"一", "人", "七", "八", "九", "十", "入", "二", "三",
                "上", "定", "底", "的", "毒", "乳", "念", "波", "拝", "板",
                "版", "非", "表", "府", "武", "並", "放", "法", "宝", "枚"};

        return genJap[new Random().nextInt(genJap.length)];
    }

    public static String generateChar() {
        String[] genChar = {"`", "~", "!", "@", "#", "№", "$", "%",
                "^", "&", "*", "(", ")", ";", ":", "?", "_", "-", "+", "="};

        return genChar[new Random().nextInt(genChar.length)];
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
