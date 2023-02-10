package uz.alpinizm.hostaway.pages.components;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

import static com.codeborne.selenide.Selenide.$x;

@Getter
public class SearchBar {

    private DatePicker datePicker = new DatePicker();
    private FiltersDialog filtersDialog = new FiltersDialog();

    private SelenideElement location = $x("//div[.='Location']");
    private SelenideElement checkIn = $x("//div[.='Check-in']");
    private SelenideElement checkOut = $x("//div[.='Check-out']");
    private SelenideElement guests = $x("//div[.='Guests']");
    private SelenideElement filterButton = $x("//button[.='Filter']");

}