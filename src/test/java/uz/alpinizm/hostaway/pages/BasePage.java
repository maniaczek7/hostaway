package uz.alpinizm.hostaway.pages;

import com.codeborne.selenide.ElementsCollection;
import lombok.Getter;
import uz.alpinizm.hostaway.pages.components.Header;

import static com.codeborne.selenide.Selenide.$$x;

@Getter
public class BasePage {

    public static final String INPUT_TYPE_CHECKBOX = ".//input[@type='checkbox']";
    protected ElementsCollection listings = $$x("//a[contains(@href,'/listings/')]");

    private final Header header = new Header();

}