package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;

public class FormPage {

    private final SelenideElement number = $$(".input__inner").find(exactText("Номер карты")).$(".input__control");

    private final SelenideElement month = $$(".input__inner").find(exactText("Месяц")).$(".input__control");

    private final SelenideElement year = $$(".input__inner").find(exactText("Год")).$(".input__control");

    private final SelenideElement name = $$(".input__inner").find(exactText("Владелец")).$(".input__control");

    private final SelenideElement cvc = $$(".input__inner").find(exactText("CVC/CVV")).$(".input__control");

    private final SelenideElement completeButton = $$(".button__text").find(exactText(" Продолжить"));


    public void setPaymentCardInfo(DataHelper.CardInfo info) {
        number.setValue(info.getCardNumber());
        month.setValue(info.getMonthValidity());
        year.setValue(info.getYearValidity());
        name.setValue(info.getCardholderName());
        cvc.setValue(info.getCvcCode());

        completeButton.click();
    }

    public void messageSuccessful(){
        SelenideElement successfulMessage = $$(".notification__content")
                .find(exactText("Операция одобрена Банком."))
                .shouldBe(visible, Duration.ofSeconds(15));
    }

    public void messageError(){
        SelenideElement errorMessage = $$(".notification__content")
                .find(exactText("Ошибка! Банк отказал в проведении операции."))
                .shouldBe(visible, Duration.ofSeconds(15));
    }
    private final SelenideElement wrongFormat = $$(".input__sub").find(exactText("Неверный формат"));

    private final SelenideElement wrongValidity = $$(".input__sub").find(exactText("Неверно указан срок действия карты"));

    private final SelenideElement emptyField = $$(".input__sub").find(exactText("Поле обязательно для заполнения"));

    private final SelenideElement expired = $$(".input__sub").find(exactText("Истёк срок действия карты"));
}
