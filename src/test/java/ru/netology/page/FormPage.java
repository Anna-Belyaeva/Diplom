package ru.netology.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$$;

public class FormPage {

    private final ElementsCollection inner = $$(".input__inner");
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

    public static String getValueAttribute(int place){
        SelenideElement attributePlace = $$(".input__control").get(place);
        return attributePlace.getAttribute("value");
    }

    public void messageSuccessfulNotification() {
        successfulMessage.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void messageErrorNotification() {
        errorMessage.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void messageWrongFormat(int place) {
        inner.get(place).shouldHave(text("Неверный формат")).shouldBe(visible, Duration.ofSeconds(15));
    }

    public void messageEmptyField(int place) {
        inner.get(place).shouldHave(text("Поле обязательно для заполнения")).shouldBe(visible, Duration.ofSeconds(15));
    }

    public void messageWrongValidity(int place) {
        inner.get(place).shouldHave(text("Неверно указан срок действия карты")).shouldBe(visible, Duration.ofSeconds(15));
    }

    public void messageExpiredValidity(int place) {
        inner.get(place).shouldHave(text("Истёк срок действия карты")).shouldBe(visible, Duration.ofSeconds(15));
    }

    public void emptySub(int p1, int p2, int p3, int p4) {
        inner.get(p1).shouldNotHave(text("Неверный формат"));
        inner.get(p1).shouldNotHave(text("Поле обязательно для заполнения"));
        inner.get(p1).shouldNotHave(text("Неверно указан срок действия карты"));
        inner.get(p1).shouldNotHave(text("Истёк срок действия карты"));

        inner.get(p2).shouldNotHave(text("Неверный формат"));
        inner.get(p2).shouldNotHave(text("Поле обязательно для заполнения"));
        inner.get(p2).shouldNotHave(text("Неверно указан срок действия карты"));
        inner.get(p2).shouldNotHave(text("Истёк срок действия карты"));

        inner.get(p3).shouldNotHave(text("Неверный формат"));
        inner.get(p3).shouldNotHave(text("Поле обязательно для заполнения"));
        inner.get(p3).shouldNotHave(text("Неверно указан срок действия карты"));
        inner.get(p3).shouldNotHave(text("Истёк срок действия карты"));

        inner.get(p4).shouldNotHave(text("Неверный формат"));
        inner.get(p4).shouldNotHave(text("Поле обязательно для заполнения"));
        inner.get(p4).shouldNotHave(text("Неверно указан срок действия карты"));
        inner.get(p4).shouldNotHave(text("Истёк срок действия карты"));
    }
}
