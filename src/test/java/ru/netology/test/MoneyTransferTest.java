package ru.netology.test;

import com.codeborne.selenide.Configuration;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.*;
import static ru.netology.page.DashboardPage.*;

public class MoneyTransferTest {

    DashboardPage dashboardPage;

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @Test
    public void shouldTransferMoneyFromFirstToSecond() {
        int amount = 5000;
        val cardBalance = dashboardPage;
        val firstCardBalanceStart = cardBalance.getCardBalance(1);
        val secondCardBalanceStart = cardBalance.getCardBalance(2);
        val transferPage = pushCardButton(2);
        transferPage.transferMoney(amount, getCard(1).getCardNumber());
        val firstCardBalanceFinish = firstCardBalanceStart - amount;
        val secondCardBalanceFinish = secondCardBalanceStart + amount;
        assertEquals(firstCardBalanceFinish, cardBalance.getCardBalance(1));
        assertEquals(secondCardBalanceFinish, cardBalance.getCardBalance(2));
    }

    @Test
    public void shouldTransferMoneyFromSecondToFirst() {
        int amount = 3000;
        val cardBalance = dashboardPage;
        val firstCardBalanceStart = cardBalance.getCardBalance(1);
        val secondCardBalanceStart = cardBalance.getCardBalance(2);
        val transferPage = pushCardButton(1);
        transferPage.transferMoney(amount, getCard(2).getCardNumber());
        val firstCardBalanceFinish = firstCardBalanceStart + amount;
        val secondCardBalanceFinish = secondCardBalanceStart - amount;
        assertEquals(firstCardBalanceFinish, cardBalance.getCardBalance(1));
        assertEquals(secondCardBalanceFinish, cardBalance.getCardBalance(2));
    }

    @Test
    public void shouldTransferMoneyFromSecondToSecond() {
        int amount = 2000;
        val cardBalance = dashboardPage;
        val secondCardBalanceStart = cardBalance.getCardBalance(2);
        val transferPage = pushCardButton(2);
        transferPage.transferMoney(amount, getCard(2).getCardNumber());
        transferPage.unsuccessfulTransfer();
    }

    @Test
    public void shouldNotTransferMoreThanAvailable() {
        val cardBalance = dashboardPage;
        int amount = cardBalance.getCardBalance(1) + 1;
        val transferPage = pushCardButton(2);
        transferPage.transferMoney(amount, getCard(1).getCardNumber());
        transferPage.unsuccessfulTransfer();
    }
}