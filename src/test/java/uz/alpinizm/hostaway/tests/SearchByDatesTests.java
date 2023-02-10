package uz.alpinizm.hostaway.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uz.alpinizm.hostaway.pages.components.DatePicker;
import uz.alpinizm.hostaway.pages.components.SearchBar;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.TextStyle;

import static com.codeborne.selenide.CollectionCondition.allMatch;
import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static java.lang.String.valueOf;
import static java.time.format.TextStyle.FULL;
import static java.time.format.TextStyle.SHORT;
import static java.util.Locale.ENGLISH;
import static java.util.stream.IntStream.rangeClosed;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchByDatesTests extends BaseTest {

    // components
    private SearchBar searchBar = new SearchBar();
    private DatePicker datePicker = new DatePicker();

    // dates related variables
    LocalDate localDateTime = LocalDateTime.now().toLocalDate();
    int today = localDateTime.getDayOfMonth();
    Month currentMonth = localDateTime.getMonth();
    Month prevMonth = localDateTime.minusMonths(1).getMonth();
    Month nextMonth = localDateTime.plusMonths(1).getMonth();
    int prevMonthLength = prevMonth.length(localDateTime.isLeapYear());
    int nextMonthLength = nextMonth.length(localDateTime.isLeapYear());

    @BeforeEach
    public void openTestPage() {
        open("search");
    }

    // 2. Check search by dates (Check-in, Check-out)
    @Test
    public void noDatesSelectedLabelTest() {

        // Check that if dates are cleared ‘Select check-in and check-out dates’ is shown.
        searchBar.getCheckIn().click();
        datePicker.getSelectDatesLabel().shouldBe(visible);
    }

    @Test
    public void datesShownInExpandedCalendarTest(){

        // Check that set dates are shown in expanded calendar and in Check-in, Check-out fields.
        // select check in date and verify it is displayed
        searchBar.getCheckOut().click();
        String expectedCheckIn = String.format("Check-in: %s %s", getMonthName(currentMonth, SHORT), today);
        String expectedCheckOut = String.format("Check-out: %s %s", getMonthName(nextMonth, SHORT), 1);
        String expectedCheckInCheckOut = expectedCheckIn + ", " + expectedCheckOut;
        pickADay(datePicker.getLeftMonthAllDays(), today);
        verifyCheckInCheckOutLabel(expectedCheckIn);

        // select checkout date, open data picker again and verify proper dates are displayed
        pickADay(datePicker.getRightMonthAllDays(),1);
        searchBar.getCheckOut().click();
        verifyCheckInCheckOutLabel(expectedCheckInCheckOut);
        datePicker.getClearDatesButton().click();
        searchBar.getCheckOut().click();
    }

    @Test
    public void defaultCalendarMonthsTest(){

        // Check that if dates weren’t set clicking in Check-in or Check-out field calendar shows current and next month.
        searchBar.getCheckIn().click();
        String actualCurrentMonth = datePicker.getLeftMonth().text().split(" ")[0];
        String actualNextMonth = datePicker.getRightMonth().text().split(" ")[0];
        assertEquals(getMonthName(currentMonth, FULL), actualCurrentMonth);
        assertEquals(getMonthName(nextMonth, FULL), actualNextMonth);
    }

    @Test
    public void selectedDatesCalendarMonthsTest(){

        // Check that if dates were set clicking in Check-in or Check-out field calendar shows current and next month.
        // fixme: does not work like that, only when current, current and next or only next month dates are selected - if other dates selected, it opens on last view
        searchBar.getCheckIn().click();
        pickADay(datePicker.getLeftMonthAllDays(), today);
        pickADay(datePicker.getRightMonthAllDays(),1);
        searchBar.getCheckIn().click();
        String actualCurrentMonth = datePicker.getLeftMonth().text().split(" ")[0];
        String actualNextMonth = datePicker.getRightMonth().text().split(" ")[0];
        assertEquals(getMonthName(currentMonth, FULL), actualCurrentMonth);
        assertEquals(getMonthName(nextMonth, FULL), actualNextMonth);
        datePicker.getClearDatesButton().click();
    }

    @Test
    public void disabledDaysTest(){

        // Check that date that is earlier than today cannot be selected.
        // if first day of month check previous month
        searchBar.getCheckIn().click();
        if (today == 1) {
            datePicker.getScrollCalendarLeft().click();
            verifyWholeMonthDisabled(datePicker.getLeftMonthAllDays(), prevMonthLength);
            pickADay(datePicker.getLeftMonthAllDays(), prevMonthLength);
            datePicker.getScrollCalendarRight().click();
        } else {
            verifyPartOfMonthDisabled(datePicker.getLeftMonthAllDays(), today - 1);
            pickADay(datePicker.getLeftMonthAllDays(), today - 1);
        }
        // one of disabled days clicked - check if "empty dates" label displayed
        datePicker.getSelectDatesLabel().shouldBe(visible);
    }

    @Test
    public void highlightedDaysTest(){

        // Check that selected dates and dates in between are highlighted with color.
        searchBar.getCheckIn().click();
        pickADay(datePicker.getRightMonthAllDays(),1);
        pickADay(datePicker.getRightMonthAllDays(), nextMonthLength);
        searchBar.getCheckIn().click();

        // verify selected days fill color
        verifyStatusOfDaysBetweenDatesInOneMonth(datePicker.getRightMonthAllDays(),1,1, attribute("fill","primary"));
        verifyStatusOfDaysBetweenDatesInOneMonth(datePicker.getRightMonthAllDays(), nextMonthLength,nextMonthLength, attribute("fill","primary"));

        // verify days between color
        verifyStatusOfDaysBetweenDatesInOneMonth(datePicker.getRightMonthAllDays(),2,nextMonthLength - 1, attribute("fill","secondary"));
    }

    private void verifyWholeMonthDisabled(ElementsCollection allDaysInMonth, int expectedLastDisabledDayOfMonth) {
        // check all prev month days are disabled in 3 ways: allMatch, exclude not disabled from list, int stream all
        allDaysInMonth.should(allMatch("all days in month disabled", day -> $(day).is(attribute("disabled"))));
        verifyDaysInMontDisabledTillTheDay(allDaysInMonth, expectedLastDisabledDayOfMonth);
    }

    private void verifyPartOfMonthDisabled(ElementsCollection allDaysInMonth, int expectedLastDisabledDayOfMonth) {
        // check current month disabled days in 3 ways: yesterday disabled, exclude not disabled size, int stream disabled
        allDaysInMonth.findBy(text(valueOf(expectedLastDisabledDayOfMonth))).shouldHave(attribute("disabled"));
        verifyDaysInMontDisabledTillTheDay(allDaysInMonth, expectedLastDisabledDayOfMonth);
    }

    private void verifyDaysInMontDisabledTillTheDay(ElementsCollection allDaysInMonth, int expectedLastDisabledDayOfMonth) {
        allDaysInMonth.exclude(not(attribute("disabled"))).shouldBe(size(expectedLastDisabledDayOfMonth));
        verifyStatusOfDaysBetweenDatesInOneMonth(allDaysInMonth, 1 , expectedLastDisabledDayOfMonth, attribute("disabled"));
    }

    private void verifyStatusOfDaysBetweenDatesInOneMonth(ElementsCollection allDaysInMonth, int startDay, int  endDay, Condition condition){
        rangeClosed(startDay, endDay).forEachOrdered(dayNumber -> allDaysInMonth
                .findBy(exactText(valueOf(dayNumber)))
                .shouldHave(condition));
    }

    private static String getMonthName(Month currentMonth, TextStyle textStyle) {
        return currentMonth.getDisplayName(textStyle, ENGLISH);
    }

    private void verifyCheckInCheckOutLabel(String expectedCheckInCheckOutLabel) {
        String actualCheckInCheckOutLabel = datePicker.getCheckInDateLabel().text().split("\n")[0];
        assertEquals(expectedCheckInCheckOutLabel, actualCheckInCheckOutLabel);
    }

    private void pickADay(ElementsCollection availableDays, int day) {
        availableDays.findBy(text(valueOf(day))).click();
    }
}


