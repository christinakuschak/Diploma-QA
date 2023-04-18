package tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import data.SQLHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.HomePage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditPageTest {
    public static String url = System.getProperty("sut.url");

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach

    public void openPage() {
        System.setProperty("chromeoptions.args", "--remote-allow-origins=*");
        open(url);
    }

    @AfterEach
    public void cleanBase() {
        SQLHelper.cleanDatabase();
    }

    @Test
    void shouldBuyCreditAllFieldValidApprovedCard() {
        var startPage = new HomePage();
        var credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getApprovedCard());
        credit.notificationSuccessIsVisible();
        assertEquals("APPROVED", SQLHelper.getCreditPaymentStatus());
    }

    @Test
    void shouldBuyCreditAllFieldValidDeclinedCard() {
        var startPage = new HomePage();
        var credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getDeclinedCard());
        credit.notificationErrorIsVisible();
        assertEquals("DECLINED", SQLHelper.getCreditPaymentStatus());
    }

    @Test
    void shouldBuyCreditWithNonExistCard() {
        var startPage = new HomePage();
        var credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getNonExistCard());
        credit.notificationErrorIsVisible();
        assertEquals(null, SQLHelper.getCreditPaymentStatus());
    }

    @Test
    void shouldBuyCreditWithInvalidCardNumber() {
        var startPage = new HomePage();
        var credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getInvalidCardNumber());
        credit.waitForWrongFormatMessage();
    }

    @Test
    void shouldBuyCreditWithEmptyMonth() {
        var startPage = new HomePage();
        var credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getEmptyMonth());
        credit.waitForWrongFormatMessage();
    }

    @Test
    void shouldBuyCreditWithMonthOver12() {
        var startPage = new HomePage();
        var credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getMonthOver12());
        credit.waitForWrongCardExpirationMessage();
    }

    @Test
    void shouldBuyCreditWithEmptyFieldYear(){
        var startPage = new HomePage();
        var credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getEmptyYear());
        credit.waitForWrongFormatMessage();
    }
    @Test
    void shouldBuyCreditWithLastYear(){
        var startPage = new HomePage();
        var credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getLastYear());
        credit.waitForCardExpiredMessage();
    }
    @Test
    void shouldBuyCreditWithFutureYear(){
        var startPage = new HomePage();
        var credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getNotComingYear());
        credit.waitForWrongCardExpirationMessage();
    }
    void shouldBuyCreditHolderIsEmpty(){
        var startPage = new HomePage();
        var credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getEmptyHolderCard());
        credit.waitForValidationFieldMessage();
    }

    @Test
    void shouldBuyCreditHolderIsRus(){
        var startPage = new HomePage();
        var credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getInvalidHolderRusCard());
        credit.waitForWrongFormatMessage();
    }
    @Test
    void shouldBuyCreditWithCvcIsEmpty(){
        var startPage = new HomePage();
        var credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getEmptyCVC());
        credit.waitForValidationFieldMessage();
    }
}
