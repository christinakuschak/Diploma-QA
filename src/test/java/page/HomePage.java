package page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$$;

public class HomePage {
    private SelenideElement heading = $$("h2").find(text("Путешествие дня"));
    private SelenideElement buyButton = $$("button").find(exactText("Купить"));
    private SelenideElement creditButton = $$("button").find(exactText("Купить в кредит"));

    public HomePage() {
        heading.shouldBe(visible);
    }

    public PaymentPage goToPaymentPage() {
        buyButton.click();
        return new PaymentPage();
    }

    public CreditPage goToCreditPage() {
        creditButton.click();
        return new CreditPage();
    }
}
