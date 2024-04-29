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

    public void transferMoney(int amount, String from) {
        sumAmount.setValue(valueOf(amount));
        fromAccount.setValue(String.valueOf(from));
        clickReplenish.click();
    }

    public void unsuccessfulTransfer() {

        $("[data-test-id=error-notification]").should(Condition.text("Ошибка"));
    }

}