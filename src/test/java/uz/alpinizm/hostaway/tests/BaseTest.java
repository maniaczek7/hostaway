package uz.alpinizm.hostaway.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

public class BaseTest {

    @BeforeAll
    public static void setUpBeforeAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void openTestPage() {
        open("");
    }

    @AfterAll
    public static void close(){
        closeWebDriver();
    }
}