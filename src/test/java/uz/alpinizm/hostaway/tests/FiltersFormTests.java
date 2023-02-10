package uz.alpinizm.hostaway.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uz.alpinizm.hostaway.pages.components.FiltersDialog;
import uz.alpinizm.hostaway.pages.components.SearchBar;

import static com.codeborne.selenide.CollectionCondition.allMatch;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static uz.alpinizm.hostaway.pages.BasePage.INPUT_TYPE_CHECKBOX;

public class FiltersFormTests extends BaseTest {

    // components
    private SearchBar searchBar = new SearchBar();
    private FiltersDialog filtersDialog = new FiltersDialog();

    @BeforeEach
    public void openTestPage() {
        open("search");
        searchBar.getFilterButton().click();
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

        // check initial form status
        verifyAllRoomsAndBedsCounts(text("0"));
        verifyAllRoomsAndBedsMinusButtonsState(disabled);
        verifyAllRoomsAndBedsPlusButtonsState(enabled);
        clickAllRoomsAndBedsPlusButtons();
        verifyAllRoomsAndBedsCounts(text("1"));
        verifyAllRoomsAndBedsMinusButtonsState(enabled);
        verifyAllRoomsAndBedsPlusButtonsState(enabled);

        // Amenities initial status
        verifyAllAmenitiesStatus(not(checked));
        clickAllAmenities();
        verifyAllAmenitiesStatus(checked);

        // clear all and check
        filtersDialog.getClearAll().click();
        verifyAllRoomsAndBedsCounts(text("0"));
        verifyAllRoomsAndBedsMinusButtonsState(disabled);
        verifyAllRoomsAndBedsPlusButtonsState(enabled);
        verifyAllAmenitiesStatus(not(checked));
    }

    @Test
    public void clearAllButtonFunctionalUsingStreamsTest() {

        // check initial form status
        String countXpath = "./following-sibling::*//span";
        String minusButtonXpath = "(./following-sibling::*//button)[1]";
        String plusButtonXpath = "(./following-sibling::*//button)[2]";
        filtersDialog.getAllRoomsAndBedsLabels().asFixedIterable().forEach(label -> $(label).$x(countXpath).shouldHave(text("0")));
        filtersDialog.getAllRoomsAndBedsLabels().asFixedIterable().forEach(label -> $(label).$x(minusButtonXpath).is(disabled));
        filtersDialog.getAllRoomsAndBedsLabels().asFixedIterable().forEach(label -> $(label).$x(plusButtonXpath).is(enabled));
        filtersDialog.getAllRoomsAndBedsLabels().asFixedIterable().forEach(label -> $(label).$x(plusButtonXpath).click());
        filtersDialog.getAllRoomsAndBedsLabels().asFixedIterable().forEach(label -> $(label).$x(countXpath).shouldHave(text("1")));
        filtersDialog.getAllRoomsAndBedsLabels().asFixedIterable().forEach(label -> $(label).$x(minusButtonXpath).is(enabled));
        filtersDialog.getAllRoomsAndBedsLabels().asFixedIterable().forEach(label -> $(label).$x(plusButtonXpath).is(enabled));

        // Amenities initial status
        filtersDialog.getAmenitiesLabels().should(allMatch("all amenities checkboxes unchecked", label -> $(label).$x(INPUT_TYPE_CHECKBOX).is(not(checked))));
        filtersDialog.getAmenitiesLabels().asFixedIterable().forEach(SelenideElement::click);
        filtersDialog.getAmenitiesLabels().should(allMatch("all amenities checkboxes checked", label -> $(label).$x(INPUT_TYPE_CHECKBOX).is(checked)));

        // clear all and check
        filtersDialog.getClearAll().click();
        filtersDialog.getAmenitiesLabels().should(allMatch("all amenities checkboxes unchecked", label -> $(label).$x(INPUT_TYPE_CHECKBOX).is(not(checked))));
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
}


