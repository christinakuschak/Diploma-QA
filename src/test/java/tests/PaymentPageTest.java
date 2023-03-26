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
        String url = System.getProperty("sut.url");
        open(url);
    }

    @AfterEach
    public void cleanBase() {
        SQLHelper.cleanDatabase();
    }

    //Happy
    @Test
    void shouldBuyAllFieldValidApprovedCard() {
        var startPage = new HomePage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getApprovedCard());
        payment.notificationSuccessIsVisible();
        assertEquals("APPROVED", SQLHelper.getDebitPaymentStatus());
    }
}
