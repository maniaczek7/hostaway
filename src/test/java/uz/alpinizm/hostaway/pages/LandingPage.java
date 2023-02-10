package uz.alpinizm.hostaway.pages;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

import static com.codeborne.selenide.Selenide.$x;

@Getter
public class LandingPage extends BasePage{

    private SelenideElement searchButton = $x("//button[.='Search']");

}