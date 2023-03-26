package ru.netology.web.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPage;



import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {


    @Test
    void shouldTransferMoneyBetweenOwnCards() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        int cardBalance0001 = dashboardPage.getCardBalance(DataHelper.getCardNumber0001());
        int cardBalance0002 = dashboardPage.getCardBalance(DataHelper.getCardNumber0002());
        int amount = 500;
        var moneyTransferPage = dashboardPage.cardRefill(DataHelper.getCardNumber0002());
        moneyTransferPage.moneyTransfer(amount, DataHelper.getCardNumber0001());
        assertEquals(cardBalance0001 - amount, dashboardPage.getCardBalance(DataHelper.getCardNumber0001()));
        assertEquals(cardBalance0002 + amount, dashboardPage.getCardBalance(DataHelper.getCardNumber0002()));
    }

    @Test
    void shouldTransferMoneyToTheSameCard() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        int cardBalance0001 = dashboardPage.getCardBalance(DataHelper.getCardNumber0001());
        int cardBalance0002 = dashboardPage.getCardBalance(DataHelper.getCardNumber0002());
        int amount = 500;
        var moneyTransferPage = dashboardPage.cardRefill(DataHelper.getCardNumber0002());
        moneyTransferPage.moneyTransfer(amount, DataHelper.getCardNumber0002());
        assertEquals(cardBalance0001, dashboardPage.getCardBalance(DataHelper.getCardNumber0001()));
        assertEquals(cardBalance0002, dashboardPage.getCardBalance(DataHelper.getCardNumber0002()));
    }

    @Test
    void shouldTransferMoneyIfAmountMoreThenBalance() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        int cardBalance0001 = dashboardPage.getCardBalance(DataHelper.getCardNumber0001());
        int cardBalance0002 = dashboardPage.getCardBalance(DataHelper.getCardNumber0002());
        int amount = cardBalance0001 + 5000;
        var moneyTransferPage = dashboardPage.cardRefill(DataHelper.getCardNumber0002());
        moneyTransferPage.moneyTransfer(amount, DataHelper.getCardNumber0001());
        moneyTransferPage.errorAlert();
        assertEquals(cardBalance0001, dashboardPage.getCardBalance(DataHelper.getCardNumber0001()));
        assertEquals(cardBalance0002, dashboardPage.getCardBalance(DataHelper.getCardNumber0002()));
    }

}

