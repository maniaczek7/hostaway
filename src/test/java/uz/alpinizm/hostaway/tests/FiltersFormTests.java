package uz.alpinizm.hostaway.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uz.alpinizm.hostaway.pages.SearchPage;
import uz.alpinizm.hostaway.pages.components.DatePicker;
import uz.alpinizm.hostaway.pages.components.FiltersDialog;
import uz.alpinizm.hostaway.pages.components.SearchBar;

import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.allMatch;
import static com.codeborne.selenide.CollectionCondition.sizeGreaterThanOrEqual;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static java.lang.String.valueOf;
import static uz.alpinizm.hostaway.pages.BasePage.INPUT_TYPE_CHECKBOX;

public class FiltersFormTests extends BaseTest {

    // components
    private final SearchBar searchBar = new SearchBar();
    private final FiltersDialog filtersDialog = new FiltersDialog();
    private final DatePicker datePicker = new DatePicker();
    private final SearchPage searchPage = new SearchPage();

    @BeforeEach
    public void openTestPage() {
        open("search");
        searchBar.getFilterButton().click();
        filtersDialog.getAmenitiesLabels().shouldBe(sizeGreaterThanOrEqual(10));
    }

    // 1. Check Filters form: minimum and maximum values, Amenities checkboxes are clickable, Clear all is functional
    // (on https://kamil-demo.alpinizm.uz/ press Search button, press Filter button, …)
    @Test
    public void roomsAndBedsMinAndMaxValuesTest() {

        // check initial form MIN (0)
        verifyAllRoomsAndBedsCounts(text("0"));
        verifyAllRoomsAndBedsMinusButtonsState(disabled);
        verifyAllRoomsAndBedsPlusButtonsState(enabled);

        // check if click on minus works (should not - disabled)
        clickAllRoomsAndBedsMinusButtons();
        verifyAllRoomsAndBedsCounts(text("0"));
        verifyAllRoomsAndBedsMinusButtonsState(disabled);
        verifyAllRoomsAndBedsPlusButtonsState(enabled);

        // check if +1 works
        clickAllRoomsAndBedsPlusButtons();
        verifyAllRoomsAndBedsCounts(text("1"));
        verifyAllRoomsAndBedsMinusButtonsState(enabled);
        verifyAllRoomsAndBedsPlusButtonsState(enabled);

        // check MAX values (10)
        clickAllRoomsAndBedsPlusButtonsUntilDisabled();
        verifyAllRoomsAndBedsCounts(text("10"));
        verifyAllRoomsAndBedsMinusButtonsState(enabled);
        verifyAllRoomsAndBedsPlusButtonsState(disabled);
    }

    @Test
    public void amenitiesCheckboxesClickableTest() {
        verifyAllAmenitiesStatus(not(checked));
        clickAllAmenities();
        verifyAllAmenitiesStatus(checked);
        clickAllAmenities();
        verifyAllAmenitiesStatus(not(checked));
    }

    @Test
    public void amenitiesCheckboxesClickableUsingStreamsTest() {
        filtersDialog.getAmenitiesLabels().should(allMatch("all amenities checkboxes unchecked", label -> $(label).$x(INPUT_TYPE_CHECKBOX).is(not(checked))));
        filtersDialog.getAmenitiesLabels().asFixedIterable().forEach(SelenideElement::click);
        filtersDialog.getAmenitiesLabels().should(allMatch("all amenities checkboxes checked", label -> $(label).$x(INPUT_TYPE_CHECKBOX).is(checked)));
        filtersDialog.getAmenitiesLabels().asFixedIterable().forEach(SelenideElement::click);
        filtersDialog.getAmenitiesLabels().should(allMatch("all amenities checkboxes unchecked", label -> $(label).$x(INPUT_TYPE_CHECKBOX).is(not(checked))));
        filtersDialog.getAmenitiesLabels().asFixedIterable().forEach(SelenideElement::click);
    }

    @Test
    public void clearAllButtonFunctionalTest() {

        // price
        verifyPriceFromAndToState(disabled);
        verifyPriceFromAndToState(empty);
        filtersDialog.getPriceInfoMessage().shouldHave(text("To filter by price, please select dates"));
        selectDates();
        verifyPriceFromAndToState(enabled);
        filtersDialog.getPriceTo().sendKeys("1");
        filtersDialog.getPriceTo().shouldHave(value("1"));
        filtersDialog.getPriceFrom().sendKeys("1");
        filtersDialog.getPriceFrom().shouldHave(value("1"));

        // rooms and beds
        verifyAllRoomsAndBedsCounts(text("0"));
        verifyAllRoomsAndBedsMinusButtonsState(disabled);
        verifyAllRoomsAndBedsPlusButtonsState(enabled);
        clickAllRoomsAndBedsPlusButtons();
        verifyAllRoomsAndBedsCounts(text("1"));
        verifyAllRoomsAndBedsMinusButtonsState(enabled);
        verifyAllRoomsAndBedsPlusButtonsState(enabled);

        // Amenities
        verifyAllAmenitiesStatus(not(checked));
        clickAllAmenities();
        verifyAllAmenitiesStatus(checked);

        // clear all and check
        filtersDialog.getClearAll().click();
        verifyPriceFromAndToState(empty);
        verifyAllRoomsAndBedsCounts(text("0"));
        verifyAllRoomsAndBedsMinusButtonsState(disabled);
        verifyAllRoomsAndBedsPlusButtonsState(enabled);
        verifyAllAmenitiesStatus(not(checked));
    }

    @Test
    public void verifyPriceLabelWithoutDatesTest(){

        // check price without dates selected (message and disabled)
        filtersDialog.getPriceInfoMessage().shouldHave(text("To filter by price, please select dates"));
        verifyPriceFromAndToState(disabled);
        verifyPriceFromAndToState(empty);
    }

    @Test
    public void verifyPriceLabelWithDatesTest(){

        selectDates();

        // check price with  dates selected (message and disabled)
        filtersDialog.getPriceInfoMessage().shouldNot(exist);
        verifyPriceFromAndToState(enabled);
        verifyPriceFromAndToState(empty);
    }

    @Test
    public void verifyMaximumPriceValuesTest(){

        selectDates();

        // check FROM max value
        filtersDialog.getPriceFrom().clear();
        filtersDialog.getPriceFrom().sendKeys("999999999999999");
        filtersDialog.getPriceFrom().shouldHave(value("999999999999999"));
        filtersDialog.getPriceFrom().sendKeys("9");
        filtersDialog.getPriceFrom().shouldHave(value("10000000000000000"));
        filtersDialog.getPriceFrom().sendKeys("9");
        filtersDialog.getPriceFrom().shouldHave(value("100000000000000020"));
        filtersDialog.getPriceFrom().sendKeys("9");
        filtersDialog.getPriceFrom().shouldHave(value("1000000000000000300"));
        filtersDialog.getPriceFrom().sendKeys("9");
        filtersDialog.getPriceFrom().shouldHave(value("10000000000000002000"));
        filtersDialog.getPriceFrom().sendKeys("9");
        filtersDialog.getPriceFrom().shouldHave(value("100000000000000020000"));
        filtersDialog.getPriceFrom().sendKeys("9");
        filtersDialog.getPriceFrom().shouldHave(value("1.0000000000000003e+21"));
        filtersDialog.getPriceFrom().sendKeys("9");
        filtersDialog.getPriceFrom().shouldHave(value("1.0000000000000003e+21"));

        // check TO max value
        filtersDialog.getPriceTo().clear();
        filtersDialog.getPriceTo().sendKeys("999999999999999");
        filtersDialog.getPriceTo().shouldHave(value("999999999999999"));
        filtersDialog.getPriceTo().sendKeys("9");
        filtersDialog.getPriceTo().shouldHave(value("10000000000000000"));
        filtersDialog.getPriceTo().sendKeys("9");
        filtersDialog.getPriceTo().shouldHave(value("100000000000000020"));
        filtersDialog.getPriceTo().sendKeys("9");
        filtersDialog.getPriceTo().shouldHave(value("1000000000000000300"));
        filtersDialog.getPriceTo().sendKeys("9");
        filtersDialog.getPriceTo().shouldHave(value("10000000000000002000"));
        filtersDialog.getPriceTo().sendKeys("9");
        filtersDialog.getPriceTo().shouldHave(value("100000000000000020000"));
        filtersDialog.getPriceTo().sendKeys("9");
        filtersDialog.getPriceTo().shouldHave(value("1.0000000000000003e+21"));
        filtersDialog.getPriceTo().sendKeys("9");
        filtersDialog.getPriceTo().shouldHave(value("1.0000000000000003e+21"));

        // verify MAX long inputs
        clearFromAndToPriceInputs();
        filtersDialog.getPriceFrom().sendKeys(valueOf(Long.MAX_VALUE)); // 9,223,372,036,854,775,807
        filtersDialog.getPriceFrom().shouldHave(value("9223372036854778000")); // a bit different from MAX Long

        clearFromAndToPriceInputs();
        filtersDialog.getPriceTo().sendKeys(valueOf(Long.MAX_VALUE)); // 9,223,372,036,854,775,807
        filtersDialog.getPriceTo().shouldHave(value("9223372036854778000")); // a bit different from MAX Long

        // verify MAX integer inputs
        clearFromAndToPriceInputs();
        filtersDialog.getPriceFrom().sendKeys(valueOf(Integer.MAX_VALUE)); // 2,147,483,647
        filtersDialog.getPriceFrom().shouldHave(value("2147483647"));

        clearFromAndToPriceInputs();
        filtersDialog.getPriceTo().sendKeys(valueOf(Integer.MAX_VALUE)); // 2,147,483,647
        filtersDialog.getPriceTo().shouldHave(value("2147483647"));
    }

    @Test
    public void verifyMinimumPriceValuesTest(){

        selectDates();

        // check FROM min value
        filtersDialog.getPriceFrom().sendKeys("0");
        filtersDialog.getPriceFrom().shouldHave(value(""));
        filtersDialog.getPriceFrom().sendKeys("1");
        filtersDialog.getPriceFrom().shouldHave(value("1"));
        filtersDialog.getPriceFrom().clear();
        filtersDialog.getPriceFrom().sendKeys("-1");
        filtersDialog.getPriceFrom().shouldHave(value("1"));
        filtersDialog.getPriceFrom().clear();

        // check FROM min value
        filtersDialog.getPriceTo().sendKeys("0");
        filtersDialog.getPriceTo().shouldHave(value(""));
        filtersDialog.getPriceTo().sendKeys("1");
        filtersDialog.getPriceTo().shouldHave(value("1"));
        filtersDialog.getPriceTo().clear();
        filtersDialog.getPriceTo().sendKeys("-1");
        filtersDialog.getPriceTo().shouldHave(value("1"));
        filtersDialog.getPriceTo().clear();
    }

    @Test
    public void verifyPlus5IfFromLowerThanToTest(){

        selectDates();

        filtersDialog.getPriceTo().sendKeys("1");
        filtersDialog.getPriceTo().shouldHave(value("1"));

        filtersDialog.getPriceFrom().sendKeys("2");
        filtersDialog.getPriceFrom().shouldHave(value("2"));
        filtersDialog.getPriceTo().click();
        filtersDialog.getPriceTo().shouldHave(value("7"));

        filtersDialog.getPriceFrom().clear();
        filtersDialog.getPriceFrom().sendKeys("8");
        filtersDialog.getPriceFrom().shouldHave(value("8"));
        filtersDialog.getPriceTo().click();
        filtersDialog.getPriceTo().shouldHave(value("13"));

        filtersDialog.getPriceFrom().clear();
        filtersDialog.getPriceFrom().sendKeys("14");
        filtersDialog.getPriceFrom().shouldHave(value("14"));
        filtersDialog.getPriceTo().click();
        filtersDialog.getPriceTo().shouldHave(value("19"));

        filtersDialog.getPriceFrom().clear();
        filtersDialog.getPriceFrom().sendKeys("20");
        filtersDialog.getPriceFrom().shouldHave(value("20"));
        filtersDialog.getPriceTo().click();
        filtersDialog.getPriceTo().shouldHave(value("25"));
    }

    @Test
    public void verifyMinus5IfPriceToHigherThanPriceFromTest(){

        selectDates();

        filtersDialog.getPriceTo().sendKeys("99");
        filtersDialog.getPriceTo().shouldHave(value("99"));

        filtersDialog.getPriceFrom().sendKeys("100");
        filtersDialog.getPriceFrom().shouldHave(value("100"));

        filtersDialog.getPriceTo().clear();
        filtersDialog.getPriceTo().sendKeys("99");
        filtersDialog.getPriceTo().shouldHave(value("99"));
        filtersDialog.getPriceFrom().click();
        filtersDialog.getPriceFrom().shouldHave(value("94"));

        filtersDialog.getPriceTo().clear();
        filtersDialog.getPriceTo().sendKeys("93");
        filtersDialog.getPriceTo().shouldHave(value("93"));
        filtersDialog.getPriceFrom().click();
        filtersDialog.getPriceFrom().shouldHave(value("88"));

        filtersDialog.getPriceTo().clear();
        filtersDialog.getPriceTo().sendKeys("83");
        filtersDialog.getPriceTo().shouldHave(value("83"));
        filtersDialog.getPriceFrom().click();
        filtersDialog.getPriceFrom().shouldHave(value("78"));
    }

    private void selectDates() {
        // close filter and select dates to enable price inputs
        filtersDialog.getClose().click();
        searchBar.getCheckIn().click();
        pickADay(datePicker.getRightMonthAllDays(),1);
        pickADay(datePicker.getRightMonthAllDays(),2);
        searchPage.getListings().shouldBe(sizeGreaterThanOrEqual(1), Duration.ofSeconds(20));
        searchBar.getFilterButton().click();
    }

    private void clickAllRoomsAndBedsPlusButtonsUntilDisabled() {
        clickButtonUntilDisabled(filtersDialog.getBedsPlus());
        clickButtonUntilDisabled(filtersDialog.getBedroomsPlus());
        clickButtonUntilDisabled(filtersDialog.getBathroomsPlus());
    }

    private void clickAllRoomsAndBedsPlusButtons() {
        filtersDialog.getBedsPlus().click();
        filtersDialog.getBedroomsPlus().click();
        filtersDialog.getBathroomsPlus().click();
    }

    private void clickAllRoomsAndBedsMinusButtons() {
        filtersDialog.getBedsMinus().click();
        filtersDialog.getBedroomsMinus().click();
        filtersDialog.getBathroomsMinus().click();
    }

    private void verifyAllRoomsAndBedsCounts(Condition condition) {
        filtersDialog.getBedsCount().shouldBe(condition);
        filtersDialog.getBedroomsCount().shouldBe(condition);
        filtersDialog.getBathroomsCount().shouldBe(condition);
    }

    private void verifyAllRoomsAndBedsMinusButtonsState(Condition condition) {
        filtersDialog.getBedsMinus().shouldBe(condition);
        filtersDialog.getBedroomsMinus().shouldBe(condition);
        filtersDialog.getBathroomsMinus().shouldBe(condition);
    }

    private void verifyAllRoomsAndBedsPlusButtonsState(Condition condition) {
        filtersDialog.getBedsPlus().shouldBe(condition);
        filtersDialog.getBedroomsPlus().shouldBe(condition);
        filtersDialog.getBathroomsPlus().shouldBe(condition);
    }

    private void clickButtonUntilDisabled(SelenideElement selenideElement) {
        while (selenideElement.isEnabled()){
            selenideElement.click();
        }
    }

    private void clickAllAmenities() {
        filtersDialog.getBeachFrontLabel().click();
        filtersDialog.getSwimmingPoolLabel().click();
        filtersDialog.getFreeWiFiLabel().click();
        filtersDialog.getKitchenLabel().click();
        filtersDialog.getAirConditioningLabel().click();
        filtersDialog.getWashingMachineLabel().click();
        filtersDialog.getPetsAllowedLabel().click();
        filtersDialog.getHotTubLabel().click();
        filtersDialog.getStreetParkingLabel().click();
        filtersDialog.getSuitableForChildrenLabel().click();
    }

    private void verifyAllAmenitiesStatus(Condition condition) {
        filtersDialog.getBeachFrontCheckbox().shouldBe(condition);
        filtersDialog.getSwimmingPoolCheckbox().shouldBe(condition);
        filtersDialog.getFreeWiFiCheckbox().shouldBe(condition);
        filtersDialog.getKitchenCheckbox().shouldBe(condition);
        filtersDialog.getAirConditioningCheckbox().shouldBe(condition);
        filtersDialog.getWashingMachineCheckbox().shouldBe(condition);
        filtersDialog.getPetsAllowedCheckbox().shouldBe(condition);
        filtersDialog.getHotTubCheckbox().shouldBe(condition);
        filtersDialog.getStreetParkingCheckbox().shouldBe(condition);
        filtersDialog.getSuitableForChildrenCheckbox().shouldBe(condition);
    }

    private void verifyPriceFromAndToState(Condition condition) {
        filtersDialog.getPriceFrom().shouldBe(condition);
        filtersDialog.getPriceTo().shouldBe(condition);
    }

    private void clearFromAndToPriceInputs() {
        filtersDialog.getPriceFrom().clear();
        filtersDialog.getPriceTo().clear();
    }
}


