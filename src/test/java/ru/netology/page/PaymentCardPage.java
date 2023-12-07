package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentCardPage {
    private final SelenideElement paymentHeading = $$(".heading").find(exactText("Оплата по карте"));

    public PaymentCardPage() {
        paymentHeading.shouldBe(visible);
    }
}
