package tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import data.SQLHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.HomePage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentPageTest {

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
        String url = System.getProperty("sut.url");
        open(url);
    }

    @AfterEach
    public void cleanBase() {
        SQLHelper.cleanDatabase();
    }

    @Test
    void shouldBuyAllFieldValidApprovedCard() {
        var startPage = new HomePage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getApprovedCard());
        payment.notificationSuccessIsVisible();
        assertEquals("APPROVED", SQLHelper.getDebitPaymentStatus());
    }

    @Test
    void shouldBuyAllFieldValidDeclinedCard() {
        var startPage = new HomePage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getDeclinedCard());
        payment.notificationErrorIsVisible();
        assertEquals("DECLINED", SQLHelper.getDebitPaymentStatus());
    }

    @Test
    void shouldBuyWithNonExistDebitCard() {
        var startPage = new HomePage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getNonExistCard());
        payment.notificationErrorIsVisible();
        assertEquals(null, SQLHelper.getDebitPaymentStatus());
    }

    @Test
    void shouldBuyInvalidDebitCard() {
        var startPage = new HomePage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getInvalidCardNumber());
        payment.waitForWrongFormatMessage();
    }

    @Test
    void shouldBuyWithEmptyCardNumber() {
        var startPage = new HomePage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getEmptyFieldCardNumber());
        payment.waitForWrongFormatMessage();
    }

    @Test
    void shouldBuyAllZeroNumberDebitCard() {
        var startPage = new HomePage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getNonExistCardAllZero());
        payment.notificationErrorIsVisible();
        assertEquals(null, SQLHelper.getDebitPaymentStatus());
    }

    @Test
    void shouldBuyWithMonthOver12() {
        var startPage = new HomePage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getMonthOver12());
        payment.waitForWrongCardExpirationMessage();
    }

    @Test
    void shouldBuyWithMonthIsEmpty() {
        var startPage = new HomePage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getEmptyMonth());
        payment.waitForWrongFormatMessage();
    }

    @Test
    void shouldBuyWithCardPastMonth() {
        var startPage = new HomePage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getInvalidPastMonth());
        payment.waitForWrongCardExpirationMessage();
    }

    @Test
    void shouldBuyWithYearIsEmpty() {
        var startPage = new HomePage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getEmptyYear());
        payment.waitForWrongFormatMessage();
    }

    @Test
    void shouldBuyWithFieldInvalidYear() {
        var startPage = new HomePage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getNotComingYear());
        payment.waitForWrongCardExpirationMessage();
    }

    @Test
    void shouldBuyWithEmptyHolder() {
        var startPage = new HomePage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getEmptyHolderCard());
        payment.waitForValidationFieldMessage();
    }

    @Test
    void shouldBuyWithHolderRus() {
        var startPage = new HomePage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getInvalidHolderRusCard());
        payment.waitForWrongFormatMessage();
    }

    @Test
    void shouldBuyWithHolderOnlyNumbers() {
        var startPage = new HomePage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getInvalidHolderNumbersCard());
        payment.waitForWrongFormatMessage();
    }
        @Test
        void shouldBuyWithCVCZeros () {
            var startPage = new HomePage();
            var payment = startPage.goToPaymentPage();
            payment.fillData(DataHelper.getZeroNumberCVC());
            payment.waitForWrongFormatMessage();
        }
        @Test
        void shouldBuyWithEmptyCvcField () {
            var startPage = new HomePage();
            var payment = startPage.goToPaymentPage();
            payment.fillData(DataHelper.getEmptyCVC());
            payment.waitForValidationFieldMessage();
        }
    }

