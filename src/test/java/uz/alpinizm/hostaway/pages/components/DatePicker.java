package uz.alpinizm.hostaway.pages.components;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$x;

@Getter
public class DatePicker {

    private SelenideElement dropDown = $x("//div[@class='__Popover']");

    // check in and check out labels
    private SelenideElement selectDatesLabel = dropDown.$(byText("Select check-in and check-out dates"));
    private SelenideElement checkInDateLabel = dropDown.$(withText("Check-in: "));
    private SelenideElement checkInAndCheckOutDateLabel = dropDown.$(withText("Check-out: "));
    private SelenideElement clearDatesButton = dropDown.$x(".//button[.='Clear dates']");

    // scroll months
    private SelenideElement scrollCalendarLeft = dropDown.$x(".//*[local-name()='use' and @*='#chevron-left']/..");
    private SelenideElement scrollCalendarRight = dropDown.$x(".//*[local-name()='use' and @*='#chevron-right']/..");

    // months labels
    private SelenideElement leftMonth = dropDown.$$(withText("2023")).first();
    private SelenideElement rightMonth = dropDown.$$(withText("2023")).last();

    // all days
    private ElementsCollection leftMonthAllDays = leftMonth.$$x("./following-sibling::*//div[contains(@class,'CalendarDay')]");
    private ElementsCollection rightMonthAllDays = rightMonth.$$x("./following-sibling::*//div[contains(@class,'CalendarDay')]");

}