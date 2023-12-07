package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentCreditPage {
    private final SelenideElement creditHeading = $$(".heading").find(exactText("Кредит по данным карты"));

    public PaymentCreditPage() {
        creditHeading.shouldBe(visible);
    }
}
