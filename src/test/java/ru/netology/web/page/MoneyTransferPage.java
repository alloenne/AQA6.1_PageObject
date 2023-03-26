package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class MoneyTransferPage {
    private SelenideElement heading = $("h1").shouldHave(exactText("Пополнение карты"));
    private SelenideElement transactionAmount = $("[data-test-id='amount'] input");
    private SelenideElement transactionFrom = $("[data-test-id='from'] input");
    private SelenideElement transactionButton = $("[data-test-id='action-transfer'] .button__content");
    private SelenideElement error = $("[data-test-id='error-notification'] .notification__content");


    public DashboardPage moneyTransfer(int amount, DataHelper.CardNumber card) {
        transactionAmount.setValue(String.valueOf(amount));
        transactionFrom.setValue(card.getNumber());
        transactionButton.click();
        return new DashboardPage();
    }

    public void errorAlert() {
        error.shouldHave(exactText("Ошибка!")).shouldBe(visible);
    }


}
