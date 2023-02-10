package uz.alpinizm.hostaway.pages;

import lombok.Getter;
import uz.alpinizm.hostaway.pages.components.Header;

@Getter
public class BasePage {

    public static final String INPUT_TYPE_CHECKBOX = ".//input[@type='checkbox']";

    private Header header = new Header();

}