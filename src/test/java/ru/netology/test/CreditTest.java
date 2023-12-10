package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.FormPage;
import ru.netology.page.PaymentPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditTest {
    FormPage formPage;
    FormPage.MessageWrong mess = new FormPage.MessageWrong();

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
        formPage = payPage.validPaymentCredit();

    }

    @AfterEach
    void cleanDB() {
        SQLHelper.cleanDataBase();
    }

    @Test
    @DisplayName("44.Credit with a approved card")
    void creditApprovedCard() {
        var card = DataHelper.getValidCardInfo();
        formPage.setPaymentCardInfo(card);

        formPage.messageSuccessfulNotification();
        assertEquals("APPROVED", SQLHelper.getStatusCredit());
    }

    @Test
    @DisplayName("45.Credit with a declined card")
    void creditDeclinedCard() {
        var card = DataHelper.cardNumberValidate(DataHelper.getInValidCardNumber());
        formPage.setPaymentCardInfo(card);

        formPage.messageErrorNotification();
        assertEquals("DECLINED", SQLHelper.getStatusCredit());
    }

    @Test
    @DisplayName("46.Credit.Empty form")
    void creditEmptyForm() {
        var card = DataHelper.getEmptyCardInfo();
        formPage.setPaymentCardInfo(card);

        formPage.messageWrong(0, mess.getEmptyField());
        formPage.messageWrong(1, mess.getEmptyField());
        formPage.messageWrong(2, mess.getEmptyField());
        formPage.messageWrong(3, mess.getEmptyField());
        formPage.messageWrong(4, mess.getEmptyField());
    }

    @Test
    @DisplayName("47.Credit.Empty card number")
    void creditEmptyCardNumber() {
        var card = DataHelper.cardNumberValidate("");
        formPage.setPaymentCardInfo(card);

        formPage.emptySub(1, 2, 3, 4);
        formPage.messageWrong(0, mess.getEmptyField());
    }

    @Test
    @DisplayName("48.Credit.Empty month")
    void creditEmptyMonth() {
        var card = DataHelper.monthValidate("");
        formPage.setPaymentCardInfo(card);

        formPage.emptySub(0, 2, 3, 4);
        formPage.messageWrong(1, mess.getEmptyField());
    }

    @Test
    @DisplayName("49.Credit.Empty year")
    void creditEmptyYear() {
        var card = DataHelper.yearValidate("");
        formPage.setPaymentCardInfo(card);

        formPage.emptySub(0, 1, 3, 4);
        formPage.messageWrong(2, mess.getEmptyField());
    }

    @Test
    @DisplayName("50.Credit.Empty cardholder name")
    void creditEmptyCardholderName() {
        var card = DataHelper.cardholderNameValidate("");
        formPage.setPaymentCardInfo(card);

        formPage.emptySub(0, 1, 2, 4);
        formPage.messageWrong(3, mess.getEmptyField());
    }

    @Test
    @DisplayName("51.Credit.Empty cvc-code")
    void creditEmptyCVC() {
        var card = DataHelper.cvcCodeValidate("");
        formPage.setPaymentCardInfo(card);

        formPage.emptySub(0, 1, 2, 3);
        formPage.messageWrong(4, mess.getEmptyField());
    }

    @Test
    @DisplayName("52.Credit.Card number validation.15 numbers")
    void creditCardNumber15numbers() {
        var card = DataHelper.cardNumberValidate(DataHelper.generateNumbers(15));
        formPage.setPaymentCardInfo(card);

        formPage.emptySub(1, 2, 3, 4);
        formPage.messageWrong(0, mess.getWrongFormat());
    }

    @Test
    @DisplayName("53.Credit.Card number validation.17 numbers")
    void creditCardNumber17numbers() {
        var number = DataHelper.generateNumbers(17);
        var card = DataHelper.cardNumberValidate(number);
        formPage.setPaymentCardInfo(card);


        formPage.messageErrorNotification();
        formPage.valueAttribute(0, number.substring(0, 16));
    }

    @Test
    @DisplayName("54.Credit.Card number validation.Cyrillic")
    void creditCardNumberCyrillic() {
        var card = DataHelper.cardNumberValidate(DataHelper.generateCyrillic());
        formPage.setPaymentCardInfo(card);


        formPage.emptySub(1, 2, 3, 4);
        formPage.messageWrong(0, mess.getWrongFormat());
        formPage.valueAttribute(0, "");
    }

    @Test
    @DisplayName("55.Credit.Card number validation.Latin")
    void creditCardNumberLatin() {
        var card = DataHelper.cardNumberValidate(DataHelper.generateLatin());
        formPage.setPaymentCardInfo(card);

        formPage.emptySub(1, 2, 3, 4);
        formPage.messageWrong(0, mess.getWrongFormat());
        formPage.valueAttribute(0, "");
    }

    @Test
    @DisplayName("56.Credit.Card number validation.Hieroglyph")
    void creditCardNumberHieroglyph() {
        var card = DataHelper.cardNumberValidate(DataHelper.generateHieroglyph());
        formPage.setPaymentCardInfo(card);

        formPage.emptySub(1, 2, 3, 4);
        formPage.messageWrong(0, mess.getWrongFormat());
        formPage.valueAttribute(0, "");
    }

    @Test
    @DisplayName("57.Credit.Card number validation.Char")
    void creditCardNumberChar() {
        var card = DataHelper.cardNumberValidate(DataHelper.generateChar());
        formPage.setPaymentCardInfo(card);

        formPage.emptySub(1, 2, 3, 4);
        formPage.messageWrong(0, mess.getWrongFormat());
        formPage.valueAttribute(0, "");
    }

    @Test
    @DisplayName("58.Credit.Card number validation.Zero")
    void creditCardNumberZero() {
        var card = DataHelper.cardNumberValidate(DataHelper.generateZero(16));
        formPage.setPaymentCardInfo(card);

        formPage.emptySub(1, 2, 3, 4);
        formPage.messageWrong(0, mess.getWrongFormat());
    }

    @Test
    @DisplayName("59.Credit.Month.1 number")
    void creditMonth1number() {
        var card = DataHelper.monthValidate(DataHelper.generateNumbers(1));
        formPage.setPaymentCardInfo(card);

        formPage.emptySub(0, 2, 3, 4);
        formPage.messageWrong(1, mess.getWrongFormat());
    }

    @Test
    @DisplayName("60.Credit.Month.3 number")
    void creditMonth3number() {
        var number = DataHelper.generateNumbers(3);
        var card = DataHelper.monthValidate(number);
        formPage.setPaymentCardInfo(card);

        formPage.valueAttribute(1, number.substring(0, 2));
    }

    @Test
    @DisplayName("61.Credit.Not exist month")
    void creditNotExistMonth() {
        var card = DataHelper.monthValidate(DataHelper.generateNumbersBetween(13, 99));
        formPage.setPaymentCardInfo(card);

        formPage.emptySub(0, 2, 3, 4);
        formPage.messageWrong(1, mess.getWrongValidity());
    }

    @Test
    @DisplayName("62.Credit.Expired month")
    void creditExpiredMonth() {

        var monthInt = Byte.parseByte(DataHelper.generateValidMonth()) - 1;
        String monthStr = String.valueOf(monthInt);

        var card = DataHelper.monthValidate(monthStr);
        formPage.setPaymentCardInfo(card);


        formPage.emptySub(0, 2, 3, 4);
        formPage.messageWrong(1, mess.getExpiredValidity());
    }

    @Test
    @DisplayName("63.Credit.Month.Cyrillic")
    void creditMonthCyrillic() {
        var card = DataHelper.monthValidate(DataHelper.generateCyrillic());
        formPage.setPaymentCardInfo(card);

        formPage.emptySub(0, 2, 3, 4);
        formPage.messageWrong(1, mess.getWrongFormat());
        formPage.valueAttribute(1, "");
    }

    @Test
    @DisplayName("64.Credit.Month.Latin")
    void creditMonthLatin() {
        var card = DataHelper.monthValidate(DataHelper.generateLatin());
        formPage.setPaymentCardInfo(card);

        formPage.emptySub(0, 2, 3, 4);
        formPage.messageWrong(1, mess.getWrongFormat());
        formPage.valueAttribute(1, "");
    }

    @Test
    @DisplayName("65.Credit.Month.Hieroglyph")
    void creditMonthHieroglyph() {
        var card = DataHelper.monthValidate(DataHelper.generateHieroglyph());
        formPage.setPaymentCardInfo(card);

        formPage.emptySub(0, 2, 3, 4);
        formPage.messageWrong(1, mess.getWrongFormat());
        formPage.valueAttribute(1, "");
    }

    @Test
    @DisplayName("66.Credit.Month.Char")
    void creditMonthChar() {
        var card = DataHelper.monthValidate(DataHelper.generateChar());
        formPage.setPaymentCardInfo(card);

        formPage.emptySub(0, 2, 3, 4);
        formPage.messageWrong(1, mess.getWrongFormat());
        formPage.valueAttribute(1, "");
    }

    @Test
    @DisplayName("67.Credit.Year.1 number")
    void creditYear1number() {
        var card = DataHelper.yearValidate(DataHelper.generateNumbers(1));
        formPage.setPaymentCardInfo(card);

        formPage.emptySub(0, 1, 3, 4);
        formPage.messageWrong(2, mess.getWrongFormat());
    }

    @Test
    @DisplayName("68.Credit.Year.3 number")
    void creditYear3number() {
        var number = DataHelper.generateNumbers(3);
        var card = DataHelper.yearValidate(number);
        formPage.setPaymentCardInfo(card);

        formPage.valueAttribute(2, number.substring(0, 2));
    }

    @Test
    @DisplayName("69.Credit.Not exist year")
    void creditNotExistYear() {
        var yearInt = Byte.parseByte(DataHelper.generateValidYear()) + 11;
        String yearStr = String.valueOf(yearInt);

        var card = DataHelper.yearValidate(yearStr);
        formPage.setPaymentCardInfo(card);

        formPage.emptySub(0, 1, 3, 4);
        formPage.messageWrong(2, mess.getWrongValidity());
    }

    @Test
    @DisplayName("70.Credit.Expired year")
    void creditExpiredYear() {

        var yearInt = Byte.parseByte(DataHelper.generateValidYear()) - 1;
        String yearStr = String.valueOf(yearInt);

        var card = DataHelper.yearValidate(yearStr);
        formPage.setPaymentCardInfo(card);

        formPage.emptySub(0, 1, 3, 4);
        formPage.messageWrong(2, mess.getExpiredValidity());
    }

    @Test
    @DisplayName("71.Credit.Year.Cyrillic")
    void creditYearCyrillic() {
        var card = DataHelper.yearValidate(DataHelper.generateCyrillic());
        formPage.setPaymentCardInfo(card);

        formPage.emptySub(0, 1, 3, 4);
        formPage.messageWrong(2, mess.getWrongFormat());
        formPage.valueAttribute(2, "");
    }

    @Test
    @DisplayName("72.Credit.Year.Latin")
    void creditYearLatin() {
        var card = DataHelper.yearValidate(DataHelper.generateLatin());
        formPage.setPaymentCardInfo(card);

        formPage.emptySub(0, 1, 3, 4);
        formPage.messageWrong(2, mess.getWrongFormat());
        formPage.valueAttribute(2, "");
    }

    @Test
    @DisplayName("73.Credit.Year.Hieroglyph")
    void creditYearHieroglyph() {
        var card = DataHelper.yearValidate(DataHelper.generateHieroglyph());
        formPage.setPaymentCardInfo(card);

        formPage.emptySub(0, 1, 3, 4);
        formPage.messageWrong(2, mess.getWrongFormat());
        formPage.valueAttribute(2, "");
    }

    @Test
    @DisplayName("74.Credit.Year.Char")
    void creditYearChar() {
        var card = DataHelper.yearValidate(DataHelper.generateChar());
        formPage.setPaymentCardInfo(card);

        formPage.emptySub(0, 1, 3, 4);
        formPage.messageWrong(2, mess.getWrongFormat());
        formPage.valueAttribute(2, "");
    }

    @Test
    @DisplayName("75.Credit.CardholderName.Partial name")
    void creditPartialCardholderName() {
        var card = DataHelper.cardholderNameValidate(DataHelper.generateLatin().substring(0, 1));
        formPage.setPaymentCardInfo(card);

        formPage.emptySub(0, 1, 2, 4);
        formPage.messageWrong(3, mess.getWrongFormat());
    }

    @Test
    @DisplayName("76.Credit.CardholderName.Numbers")
    void creditCardholderNameNumbers() {
        var card = DataHelper.cardholderNameValidate(DataHelper.generateNumbers(2));
        formPage.setPaymentCardInfo(card);

        formPage.emptySub(0, 1, 2, 4);
        formPage.messageWrong(3, mess.getWrongFormat());
        formPage.valueAttribute(3, "");
    }

    @Test
    @DisplayName("77.Credit.CardholderName.Cyrillic")
    void creditCardholderNameCyrillic() {
        var card = DataHelper.cardholderNameValidate(DataHelper.generateCyrillic());
        formPage.setPaymentCardInfo(card);

        formPage.emptySub(0, 1, 2, 4);
        formPage.messageWrong(3, mess.getWrongFormat());
        formPage.valueAttribute(3, "");
    }

    @Test
    @DisplayName("78.Credit.CardholderName.Char")
    void creditCardholderNameChar() {
        var card = DataHelper.cardholderNameValidate(DataHelper.generateChar());
        formPage.setPaymentCardInfo(card);

        formPage.emptySub(0, 1, 2, 4);
        formPage.messageWrong(3, mess.getWrongFormat());
        formPage.valueAttribute(3, "");
    }

    @Test
    @DisplayName("79.Credit.CardholderName.Hieroglyph")
    void creditCardholderNameHieroglyph() {
        var card = DataHelper.cardholderNameValidate(DataHelper.generateHieroglyph());
        formPage.setPaymentCardInfo(card);

        formPage.emptySub(0, 1, 2, 4);
        formPage.messageWrong(3, mess.getWrongFormat());
        formPage.valueAttribute(3, "");
    }

    @Test
    @DisplayName("80.Credit.CVC.2 number")
    void creditCVC2number() {
        var card = DataHelper.cvcCodeValidate(DataHelper.generateNumbers(2));
        formPage.setPaymentCardInfo(card);

        formPage.emptySub(0, 1, 2, 3);
        formPage.messageWrong(4, mess.getWrongFormat());
    }

    @Test
    @DisplayName("81.Credit.CVC.4 number")
    void creditCVC4number() {
        var number = DataHelper.generateNumbers(4);
        var card = DataHelper.cvcCodeValidate(number);
        formPage.setPaymentCardInfo(card);

        formPage.valueAttribute(4, number.substring(0, 3));
    }

    @Test
    @DisplayName("82.Credit.CVC.Zero")
    void creditCVCZero() {
        var card = DataHelper.cvcCodeValidate(DataHelper.generateZero(3));
        formPage.setPaymentCardInfo(card);

        formPage.emptySub(0, 1, 2, 3);
        formPage.messageWrong(4, mess.getWrongFormat());
    }

    @Test
    @DisplayName("83.Credit.CVC.Char")
    void creditCVCChar() {
        var card = DataHelper.cvcCodeValidate(DataHelper.generateChar());
        formPage.setPaymentCardInfo(card);

        formPage.emptySub(0, 1, 2, 3);
        formPage.messageWrong(4, mess.getWrongFormat());
        formPage.valueAttribute(4, "");
    }

    @Test
    @DisplayName("84.CVC.Hieroglyph")
    void creditCVCHieroglyph() {
        var card = DataHelper.cvcCodeValidate(DataHelper.generateHieroglyph());
        formPage.setPaymentCardInfo(card);

        formPage.emptySub(0, 1, 2, 3);
        formPage.messageWrong(4, mess.getWrongFormat());
        formPage.valueAttribute(4, "");
    }

    @Test
    @DisplayName("85.Credit.CVC.Latin")
    void creditCVCLatin() {
        var card = DataHelper.cvcCodeValidate(DataHelper.generateLatin());
        formPage.setPaymentCardInfo(card);

        formPage.emptySub(0, 1, 2, 3);
        formPage.messageWrong(4, mess.getWrongFormat());
        formPage.valueAttribute(4, "");
    }

    @Test
    @DisplayName("86.Credit.CVC.Cyrillic")
    void creditCVCCyrillic() {
        var card = DataHelper.cvcCodeValidate(DataHelper.generateCyrillic());
        formPage.setPaymentCardInfo(card);

        formPage.emptySub(0, 1, 2, 3);
        formPage.messageWrong(4, mess.getWrongFormat());
        formPage.valueAttribute(4, "");
    }
}


