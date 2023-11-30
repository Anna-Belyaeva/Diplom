package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;


public class PaymentPage {

    private final SelenideElement paymentButton = $$(".button__text").find(exactText("Купить"));

    private final SelenideElement creditButton = $$(".button__text").find(exactText("Купить в кредит"));

    private final SelenideElement paymentHeading = $$(".heading").find(exactText("Оплата по карте"));

    private final SelenideElement creditHeading = $$(".heading").find(exactText("Кредит по данным карты"));


    public void paymentCard() {
        open("http://localhost:8080/");
        paymentButton.click();
        paymentHeading.shouldBe(visible);
    }

    public void paymentCredit() {
        open("http://localhost:8080/");
        creditButton.click();
        creditHeading.shouldBe(visible);
    }
}
