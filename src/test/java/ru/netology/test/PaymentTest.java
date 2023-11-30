package ru.netology.test;

import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.FormPage;
import ru.netology.page.PaymentPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentTest {
    FormPage formPage = new FormPage();

    @BeforeEach
    void paymentTur() {
        var payPage = new PaymentPage();
        payPage.paymentCard();
    }

    @AfterEach
    void cleanDB(){
        SQLHelper.cleanDataBase();
    }


    @Test
    void paymentApprovedCard() {
        var card = DataHelper.getValidCardInfo();
        formPage.setPaymentCardInfo(card);
        formPage.messageSuccessful();

        String status = SQLHelper.getStatus();

        assertEquals("APPROVED", status);
    }


}

