package ru.netology.test;

import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.FormPage;
import ru.netology.page.PaymentPage;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentTest {
    FormPage formPage = new FormPage();

    @BeforeEach
    void paymentTur() {
        var payPage = new PaymentPage();
        payPage.paymentCard();
    }

    @AfterEach
    void cleanDB() {
        SQLHelper.cleanDataBase();
    }


    @Test
    void paymentApprovedCard() {
        var card = DataHelper.getValidCardInfo();
        formPage.setPaymentCardInfo(card);
        formPage.messageSuccessfulNotification();

        assertEquals("APPROVED", SQLHelper.getStatus());
    }

    @Test
    void paymentDeclinedCard() {
        var card = DataHelper.cardNumberValidate(DataHelper.getInValidCardNumber());
        formPage.setPaymentCardInfo(card);
        formPage.messageErrorNotification();

        assertEquals("DECLINED", SQLHelper.getStatus());
    }

    @Test
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
    void paymentEmptyCardNumber() {
        var card = DataHelper.cardNumberValidate("");
        formPage.setPaymentCardInfo(card);

        formPage.messageEmptyField(0);
    }

    @Test
    void paymentEmptyMonth() {
        var card = DataHelper.monthValidate("");
        formPage.setPaymentCardInfo(card);

        formPage.messageEmptyField(1);
    }

    @Test
    void paymentEmptyYear() {
        var card = DataHelper.yearValidate("");
        formPage.setPaymentCardInfo(card);

        formPage.messageEmptyField(2);
    }

    @Test
    void paymentEmptyCardholderName() {
        var card = DataHelper.cardholderNameValidate("");
        formPage.setPaymentCardInfo(card);

        formPage.messageEmptyField(3);
    }

    @Test
    void paymentEmptyCVC() {
        var card = DataHelper.cvcCodeValidate("");
        formPage.setPaymentCardInfo(card);

        formPage.messageEmptyField(4);
    }

}

