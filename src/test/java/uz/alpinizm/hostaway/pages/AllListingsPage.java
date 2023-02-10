package uz.alpinizm.hostaway.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

@Getter
public class AllListingsPage extends BasePage{

    // filter labels
    private SelenideElement all = $x("//span[contains(.,'All (')]");
    private SelenideElement villa = $x("//span[contains(.,'Villa (')]");
    private SelenideElement test = $x("//span[contains(.,'Test (')]");
    private SelenideElement fireplace = $x("//span[contains(.,'Fireplace (')]");

    private ElementsCollection listings = $$x("//a[contains(@href,'/listings/')]");
    private SelenideElement loaded = $x("(//div[contains(@style,'display: ')])[last()]");

}