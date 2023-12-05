package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.FormPage;
import ru.netology.page.PaymentPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void paymentTur() {
        var payPage = new PaymentPage();
        payPage.paymentCard();
    }

    @AfterEach
    void cleanDB() {
        SQLHelper.cleanDataBase();
    }

    FormPage formPage = new FormPage();

    @Test
    @DisplayName("1.Payment with a approved card")
    void paymentApprovedCard() {
        var card = DataHelper.getValidCardInfo();
        formPage.setPaymentCardInfo(card);
        formPage.messageSuccessfulNotification();

        assertEquals("APPROVED", SQLHelper.getStatusPayment());
    }

    @Test
    @DisplayName("2.Payment with a declined card")
    void paymentDeclinedCard() {
        var card = DataHelper.cardNumberValidate(DataHelper.getInValidCardNumber());
        formPage.setPaymentCardInfo(card);
        formPage.messageErrorNotification();

        assertEquals("DECLINED", SQLHelper.getStatusPayment());
    }

    @Test
    @DisplayName("3.Payment.Empty form")
    void paymentEmptyForm() {
        var card = DataHelper.getEmptyCardInfo();
        formPage.setPaymentCardInfo(card);

        formPage.messageEmptyField(0);
        formPage.messageEmptyField(1);
        formPage.messageEmptyField(2);
        formPage.messageEmptyField(3);
        formPage.messageEmptyField(4);
    }

    @Test
    @DisplayName("4.Payment.Empty card number")
    void paymentEmptyCardNumber() {
        var card = DataHelper.cardNumberValidate("");
        formPage.setPaymentCardInfo(card);

        formPage.messageEmptyField(0);
    }

    @Test
    @DisplayName("5.Payment.Empty month")
    void paymentEmptyMonth() {
        var card = DataHelper.monthValidate("");
        formPage.setPaymentCardInfo(card);

        formPage.messageEmptyField(1);
    }

    @Test
    @DisplayName("6.Payment.Empty year")
    void paymentEmptyYear() {
        var card = DataHelper.yearValidate("");
        formPage.setPaymentCardInfo(card);

        formPage.messageEmptyField(2);
    }

    @Test
    @DisplayName("7.Payment.Empty cardholder name")
    void paymentEmptyCardholderName() {
        var card = DataHelper.cardholderNameValidate("");
        formPage.setPaymentCardInfo(card);

        formPage.messageEmptyField(3);
    }

    @Test
    @DisplayName("8.Payment.Empty cvc-code")
    void paymentEmptyCVC() {
        var card = DataHelper.cvcCodeValidate("");
        formPage.setPaymentCardInfo(card);

        formPage.messageEmptyField(4);
    }

    @Test
    @DisplayName("9.Payment.Card number validation.15 numbers")
    void paymentCardNumber15numbers() {
        var card = DataHelper.cardNumberValidate(DataHelper.generateNumbers(15));
        formPage.setPaymentCardInfo(card);

        formPage.messageWrongFormat(0);
    }

    @Test
    @DisplayName("10.Payment.Card number validation.17 numbers")
    void paymentCardNumber17numbers() {
        var number = DataHelper.generateNumbers(17);
        var card = DataHelper.cardNumberValidate(number);
        formPage.setPaymentCardInfo(card);


        formPage.messageErrorNotification();
        assertEquals(number.substring(0, 16), FormPage.getValueAttribute(0).replaceAll(" ", ""));
    }

    @Test
    @DisplayName("11.Payment.Card number validation.Cyrillic")
    void paymentCardNumberCyrillic() {
        var card = DataHelper.cardNumberValidate(DataHelper.generateCyrillic());
        formPage.setPaymentCardInfo(card);

        formPage.messageWrongFormat(0);
        assertEquals("", FormPage.getValueAttribute(0));
    }

    @Test
    @DisplayName("12.Payment.Card number validation.Latin")
    void paymentCardNumberLatin() {
        var card = DataHelper.cardNumberValidate(DataHelper.generateLatin());
        formPage.setPaymentCardInfo(card);

        formPage.messageWrongFormat(0);
        assertEquals("", FormPage.getValueAttribute(0));
    }

    @Test
    @DisplayName("13.Payment.Card number validation.Hieroglyph")
    void paymentCardNumberHieroglyph() {
        var card = DataHelper.cardNumberValidate(DataHelper.generateHieroglyph());
        formPage.setPaymentCardInfo(card);

        formPage.messageWrongFormat(0);
        assertEquals("", FormPage.getValueAttribute(0));
    }

    @Test
    @DisplayName("14.Payment.Card number validation.Char")
    void paymentCardNumberChar() {
        var card = DataHelper.cardNumberValidate(DataHelper.generateChar());
        formPage.setPaymentCardInfo(card);

        formPage.messageWrongFormat(0);
        assertEquals("", FormPage.getValueAttribute(0));
    }

    @Test
    @DisplayName("15.Payment.Card number validation.Zero")
    void paymentCardNumberZero() {
        var card = DataHelper.cardNumberValidate(DataHelper.generateZero(16));
        formPage.setPaymentCardInfo(card);

        formPage.messageWrongFormat(0);
    }

    @Test
    @DisplayName("16.Payment.Month.1 number")
    void paymentMonth1number() {
        var card = DataHelper.monthValidate(DataHelper.generateNumbers(1));
        formPage.setPaymentCardInfo(card);

        formPage.messageWrongFormat(1);
    }

    @Test
    @DisplayName("17.Payment.Month.3 number")
    void paymentMonth3number() {
        var number = DataHelper.generateNumbers(3);
        var card = DataHelper.monthValidate(number);
        formPage.setPaymentCardInfo(card);

        assertEquals(number.substring(0, 2), FormPage.getValueAttribute(1));
    }

    @Test
    @DisplayName("18.Payment.Not exist month")
    void paymentNotExistMonth() {
        var card = DataHelper.monthValidate(DataHelper.generateNumbersBetween(13, 99));
        formPage.setPaymentCardInfo(card);

        formPage.messageWrongValidity(1);
    }

    @Test
    @DisplayName("19.Payment.Expired month")
    void paymentExpiredMonth() {

        var monthInt = Byte.parseByte(DataHelper.generateValidMonth()) - 1;
        String monthStr = String.valueOf(monthInt);

        var card = DataHelper.monthValidate(monthStr);
        formPage.setPaymentCardInfo(card);

        formPage.messageExpiredValidity(1);
    }

    @Test
    @DisplayName("20.Payment.Month.Cyrillic")
    void paymentMonthCyrillic() {
        var card = DataHelper.monthValidate(DataHelper.generateCyrillic());
        formPage.setPaymentCardInfo(card);

        formPage.messageWrongFormat(1);
        assertEquals("", FormPage.getValueAttribute(1));
    }

    @Test
    @DisplayName("21.Payment.Month.Latin")
    void paymentMonthLatin() {
        var card = DataHelper.monthValidate(DataHelper.generateLatin());
        formPage.setPaymentCardInfo(card);

        formPage.messageWrongFormat(1);
        assertEquals("", FormPage.getValueAttribute(1));
    }

    @Test
    @DisplayName("22.Payment.Month.Hieroglyph")
    void paymentMonthHieroglyph() {
        var card = DataHelper.monthValidate(DataHelper.generateHieroglyph());
        formPage.setPaymentCardInfo(card);

        formPage.messageWrongFormat(1);
        assertEquals("", FormPage.getValueAttribute(1));
    }

    @Test
    @DisplayName("23.Payment.Month.Char")
    void paymentMonthChar() {
        var card = DataHelper.monthValidate(DataHelper.generateChar());
        formPage.setPaymentCardInfo(card);

        formPage.messageWrongFormat(1);
        assertEquals("", FormPage.getValueAttribute(1));
    }

    @Test
    @DisplayName("24.Payment.Year.1 number")
    void paymentYear1number() {
        var card = DataHelper.yearValidate(DataHelper.generateNumbers(1));
        formPage.setPaymentCardInfo(card);

        formPage.messageWrongFormat(2);
    }

    @Test
    @DisplayName("25.Payment.Year.3 number")
    void paymentYear3number() {
        var number = DataHelper.generateNumbers(3);
        var card = DataHelper.yearValidate(number);
        formPage.setPaymentCardInfo(card);

        assertEquals(number.substring(0, 2), FormPage.getValueAttribute(2));
    }

    @Test
    @DisplayName("26.Payment.Not exist year")
    void paymentNotExistYear() {
        var yearInt = Byte.parseByte(DataHelper.generateValidYear()) + 11;
        String yearStr = String.valueOf(yearInt);

        var card = DataHelper.yearValidate(yearStr);
        formPage.setPaymentCardInfo(card);

        formPage.messageWrongValidity(2);
    }

    @Test
    @DisplayName("27.Payment.Expired year")
    void paymentExpiredYear() {

        var yearInt = Byte.parseByte(DataHelper.generateValidYear()) - 1;
        String yearStr = String.valueOf(yearInt);

        var card = DataHelper.yearValidate(yearStr);
        formPage.setPaymentCardInfo(card);

        formPage.messageExpiredValidity(2);
    }

    @Test
    @DisplayName("28.Payment.Year.Cyrillic")
    void paymentYearCyrillic() {
        var card = DataHelper.yearValidate(DataHelper.generateCyrillic());
        formPage.setPaymentCardInfo(card);

        formPage.messageWrongFormat(2);
        assertEquals("", FormPage.getValueAttribute(2));
    }

    @Test
    @DisplayName("29.Payment.Year.Latin")
    void paymentYearLatin() {
        var card = DataHelper.yearValidate(DataHelper.generateLatin());
        formPage.setPaymentCardInfo(card);

        formPage.messageWrongFormat(2);
        assertEquals("", FormPage.getValueAttribute(2));
    }

    @Test
    @DisplayName("30.Payment.Year.Hieroglyph")
    void paymentYearHieroglyph() {
        var card = DataHelper.yearValidate(DataHelper.generateHieroglyph());
        formPage.setPaymentCardInfo(card);

        formPage.messageWrongFormat(2);
        assertEquals("", FormPage.getValueAttribute(2));
    }

    @Test
    @DisplayName("31.Payment.Year.Char")
    void paymentYearChar() {
        var card = DataHelper.yearValidate(DataHelper.generateChar());
        formPage.setPaymentCardInfo(card);

        formPage.messageWrongFormat(2);
        assertEquals("", FormPage.getValueAttribute(2));
    }

    @Test
    @DisplayName("32.Payment.CardholderName.Partial name")
    void paymentPartialCardholderName() {
        var card = DataHelper.cardholderNameValidate(DataHelper.generateLatin().substring(0, 1));
        formPage.setPaymentCardInfo(card);

        formPage.messageWrongFormat(3);
    }

    @Test
    @DisplayName("33.Payment.CardholderName.Numbers")
    void paymentCardholderNameNumbers() {
        var card = DataHelper.cardholderNameValidate(DataHelper.generateNumbers(2));
        formPage.setPaymentCardInfo(card);

        formPage.messageWrongFormat(3);
        assertEquals("", FormPage.getValueAttribute(3));
    }

    @Test
    @DisplayName("34.Payment.CardholderName.Cyrillic")
    void paymentCardholderNameCyrillic() {
        var card = DataHelper.cardholderNameValidate(DataHelper.generateCyrillic());
        formPage.setPaymentCardInfo(card);

        formPage.messageWrongFormat(3);
        assertEquals("", FormPage.getValueAttribute(3));
    }

    @Test
    @DisplayName("35.Payment.CardholderName.Char")
    void paymentCardholderNameChar() {
        var card = DataHelper.cardholderNameValidate(DataHelper.generateChar());
        formPage.setPaymentCardInfo(card);

        formPage.messageWrongFormat(3);
        assertEquals("", FormPage.getValueAttribute(3));
    }

    @Test
    @DisplayName("36.Payment.CardholderName.Hieroglyph")
    void paymentCardholderNameHieroglyph() {
        var card = DataHelper.cardholderNameValidate(DataHelper.generateHieroglyph());
        formPage.setPaymentCardInfo(card);

        formPage.messageWrongFormat(3);
        assertEquals("", FormPage.getValueAttribute(3));
    }

    @Test
    @DisplayName("37.Payment.CVC.2 number")
    void paymentCVC2number() {
        var card = DataHelper.cvcCodeValidate(DataHelper.generateNumbers(2));
        formPage.setPaymentCardInfo(card);

        formPage.messageWrongFormat(4);
    }

    @Test
    @DisplayName("38.Payment.CVC.4 number")
    void paymentCVC4number() {
        var number = DataHelper.generateNumbers(4);
        var card = DataHelper.cvcCodeValidate(number);
        formPage.setPaymentCardInfo(card);

        assertEquals(number.substring(0, 3), FormPage.getValueAttribute(4));
    }

    @Test
    @DisplayName("39.Payment.CVC.Zero")
    void paymentCVCZero() {
        var card = DataHelper.cvcCodeValidate(DataHelper.generateZero(3));
        formPage.setPaymentCardInfo(card);

        formPage.messageWrongFormat(4);
    }

    @Test
    @DisplayName("40.Payment.CVC.Char")
    void paymentCVCChar() {
        var card = DataHelper.cvcCodeValidate(DataHelper.generateChar());
        formPage.setPaymentCardInfo(card);

        formPage.messageWrongFormat(4);
        assertEquals("", FormPage.getValueAttribute(4));
    }

    @Test
    @DisplayName("41.Payment.CVC.Hieroglyph")
    void paymentCVCHieroglyph() {
        var card = DataHelper.cvcCodeValidate(DataHelper.generateHieroglyph());
        formPage.setPaymentCardInfo(card);

        formPage.messageWrongFormat(4);
        assertEquals("", FormPage.getValueAttribute(4));
    }

    @Test
    @DisplayName("42.Payment.CVC.Latin")
    void paymentCVCLatin() {
        var card = DataHelper.cvcCodeValidate(DataHelper.generateLatin());
        formPage.setPaymentCardInfo(card);

        formPage.messageWrongFormat(4);
        assertEquals("", FormPage.getValueAttribute(4));
    }

    @Test
    @DisplayName("43.Payment.CVC.Cyrillic")
    void paymentCVCCyrillic() {
        var card = DataHelper.cvcCodeValidate(DataHelper.generateCyrillic());
        formPage.setPaymentCardInfo(card);

        formPage.messageWrongFormat(4);
        assertEquals("", FormPage.getValueAttribute(4));
    }
}

