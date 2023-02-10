package uz.alpinizm.hostaway.pages.components;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

import static com.codeborne.selenide.Selenide.$x;

@Getter
public class Header {

    private SelenideElement header = $x("//header");
    private SelenideElement logo = header.$x(".//img[contains(@src,'companyLogo.png')]");
    private SelenideElement homeLink = header.$x(".//a[.='Home']");
    private SelenideElement allListingsLink = header.$x(".//a[.='All listings']");
    private SelenideElement aboutUs = header.$x(".//a[.='About Us']");

}