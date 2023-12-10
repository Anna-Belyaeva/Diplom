package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import lombok.Value;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$$;

public class FormPage {
    public FormPage() {

        paymentHeading.shouldBe(visible);
        creditHeading.shouldBe(visible);
    }

    private final SelenideElement paymentHeading = $$(".heading").find(exactText("Оплата по карте"));
    private final SelenideElement creditHeading = $$(".heading").find(exactText("Кредит по данным карты"));

    private final ElementsCollection inner = $$(".input__inner");
    private final ElementsCollection attributePlace = $$(".input__control");

    private final SelenideElement number = inner.find(exactText("Номер карты")).$(".input__control");
    private final SelenideElement month = inner.find(exactText("Месяц")).$(".input__control");
    private final SelenideElement year = inner.find(exactText("Год")).$(".input__control");
    private final SelenideElement name = inner.find(exactText("Владелец")).$(".input__control");
    private final SelenideElement cvc = inner.find(exactText("CVC/CVV")).$(".input__control");
    private final SelenideElement completeButton = $$(".button__text").find(exactText(" Продолжить"));

    private final SelenideElement successfulMessage = $$(".notification__content")
            .find(exactText("Операция одобрена Банком."));
    private final SelenideElement errorMessage = $$(".notification__content")
            .find(exactText("Ошибка! Банк отказал в проведении операции."));

    public void setPaymentCardInfo(DataHelper.CardInfo info) {
        number.setValue(info.getCardNumber());
        month.setValue(info.getMonthValidity());
        year.setValue(info.getYearValidity());
        name.setValue(info.getCardholderName());
        cvc.setValue(info.getCvcCode());

        completeButton.click();
    }
    public void messageSuccessfulNotification() {

        successfulMessage.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void messageErrorNotification() {

        errorMessage.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void messageWrong(int place, String message) {
        inner.get(place).shouldHave(text(message)).shouldBe(visible);
    }

    public void valueAttribute(int place, String value) {

        var placeVal = attributePlace.get(place);
        placeVal.shouldBe(visible);
        Condition.value(value);
    }

    public void emptySub(int p1, int p2, int p3, int p4) {
        var mess = new MessageWrong();

        inner.get(p1).shouldNotHave(text(mess.wrongFormat)).
                shouldNotHave(text(mess.emptyField)).
                shouldNotHave(text(mess.wrongValidity)).
                shouldNotHave(text(mess.expiredValidity));
        inner.get(p2).shouldNotHave(text(mess.wrongFormat)).
                shouldNotHave(text(mess.emptyField)).
                shouldNotHave(text(mess.wrongValidity)).
                shouldNotHave(text(mess.expiredValidity));
        inner.get(p3).shouldNotHave(text(mess.wrongFormat)).
                shouldNotHave(text(mess.emptyField)).
                shouldNotHave(text(mess.wrongValidity)).
                shouldNotHave(text(mess.expiredValidity));
        inner.get(p4).shouldNotHave(text(mess.wrongFormat)).
                shouldNotHave(text(mess.emptyField)).
                shouldNotHave(text(mess.wrongValidity)).
                shouldNotHave(text(mess.expiredValidity));

    }

    @Getter
    @Value
    public static class MessageWrong {
        String wrongFormat = "Неверный формат";
        String emptyField = "Поле обязательно для заполнения";
        String wrongValidity = "Неверно указан срок действия карты";
        String expiredValidity = "Истёк срок действия карты";

    }
}
