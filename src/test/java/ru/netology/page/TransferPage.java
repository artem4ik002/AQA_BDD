package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;
import static java.lang.String.valueOf;

public class TransferPage {
    private SelenideElement sumAmount = $("[data-test-id=amount] input");
    private SelenideElement fromAccount = $("[data-test-id=from] input");
    private SelenideElement clickReplenish = $("[data-test-id=action-transfer]");

    //перевод
    public void transferMoney(int amount, DataHelper.CardsInfo from) {
        sumAmount.setValue(valueOf(amount));
        fromAccount.setValue(String.valueOf(from));
        clickReplenish.click();
        new DashboardPage();
    }

    public void errorLimit() {
        $(".notification__content").should(Condition.exactText("Ошибка"));
    }

    public void invalidCard() {
        $(".notification__content").should(Condition.text("Ошибка! Произошла ошибка"));
    }
}