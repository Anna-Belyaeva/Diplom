package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;


public class PaymentPage {

    public PaymentPage() {

        open("http://localhost:8080/");
    }

    private final SelenideElement paymentButton = $$(".button__text").find(exactText("Купить"));

    private final SelenideElement creditButton = $$(".button__text").find(exactText("Купить в кредит"));

   public FormPage validPaymentCard() {
        paymentButton.click();
        return new FormPage();
    }

    public FormPage validPaymentCredit() {
        creditButton.click();
        return new FormPage();
    }
}
