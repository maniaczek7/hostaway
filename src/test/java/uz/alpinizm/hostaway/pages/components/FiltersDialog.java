package uz.alpinizm.hostaway.pages.components;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

import static com.codeborne.selenide.Selenide.$x;
import static uz.alpinizm.hostaway.pages.BasePage.INPUT_TYPE_CHECKBOX;

@Getter
public class FiltersDialog {

    // filters
    private SelenideElement modal = $x("//div[@class='__modal']");

    // price
    private SelenideElement priceFrom = modal.$x(".//input[@placeholder='From']");
    private SelenideElement priceTo = modal.$x(".//input[@placeholder='To']");
    private SelenideElement priceMessage = modal.$x(".//p[.='To filter by price, please select dates']");;

    // Rooms and beds
    // beds
    private SelenideElement bedsLabel = modal.$x(".//div[.='Beds']");
    private SelenideElement bedsMinus = bedsLabel.$x("(./following-sibling::*//button)[1]");
    private SelenideElement bedsCount = bedsLabel.$x("./following-sibling::*//span");
    private SelenideElement bedsPlus = bedsLabel.$x("(./following-sibling::*//button)[2]");

    // bedrooms
    private SelenideElement bedroomsLabel = modal.$x(".//div[.='Bedrooms']");
    private SelenideElement bedroomsMinus = bedroomsLabel.$x("(./following-sibling::*//button)[1]");
    private SelenideElement bedroomsCount = bedroomsLabel.$x("./following-sibling::*//span");
    private SelenideElement bedroomsPlus = bedroomsLabel.$x("(./following-sibling::*//button)[2]");

    // bathrooms
    private SelenideElement bathroomsLabel = modal.$x(".//div[.='Bathrooms']");
    private SelenideElement bathroomsMinus = bathroomsLabel.$x("(./following-sibling::*//button)[1]");
    private SelenideElement bathroomsCount = bathroomsLabel.$x("./following-sibling::*//span");
    private SelenideElement bathroomsPlus = bathroomsLabel.$x("(./following-sibling::*//button)[2]");

    private ElementsCollection allRoomsAndBedsLabels = modal.$$x(".//div[.='Beds']|.//div[.='Bedrooms']|.//div[.='Bathrooms']");

    // Amenities
    // all
    private ElementsCollection amenitiesLabels = modal.$$x(".//label");

    // labels
    private SelenideElement beachFrontLabel = modal.$x(".//label[.='Beach front']");
    private SelenideElement swimmingPoolLabel = modal.$x(".//label[.='Swimming pool']");
    private SelenideElement freeWiFiLabel = modal.$x(".//label[.='Free WiFi']");
    private SelenideElement kitchenLabel = modal.$x(".//label[.='Kitchen']");
    private SelenideElement airConditioningLabel = modal.$x(".//label[.='Air conditioning']");
    private SelenideElement washingMachineLabel = modal.$x(".//label[.='Washing Machine']");
    private SelenideElement petsAllowedLabel = modal.$x(".//label[.='Pets allowed']");
    private SelenideElement hotTubLabel = modal.$x(".//label[.='Hot tub']");
    private SelenideElement streetParkingLabel = modal.$x(".//label[.='Street parking']");
    private SelenideElement suitableForChildrenLabel = modal.$x(".//label[.='Suitable for children']");

    // inputs
    private SelenideElement beachFrontCheckbox = modal.$x(INPUT_TYPE_CHECKBOX);
    private SelenideElement swimmingPoolCheckbox = modal.$x(INPUT_TYPE_CHECKBOX);
    private SelenideElement freeWiFiCheckbox = modal.$x(INPUT_TYPE_CHECKBOX);
    private SelenideElement kitchenCheckbox = modal.$x(INPUT_TYPE_CHECKBOX);
    private SelenideElement airConditioningCheckbox = modal.$x(INPUT_TYPE_CHECKBOX);
    private SelenideElement washingMachineCheckbox = modal.$x(INPUT_TYPE_CHECKBOX);
    private SelenideElement petsAllowedCheckbox = modal.$x(INPUT_TYPE_CHECKBOX);
    private SelenideElement hotTubCheckbox = modal.$x(INPUT_TYPE_CHECKBOX);
    private SelenideElement streetParkingCheckbox = modal.$x(INPUT_TYPE_CHECKBOX);
    private SelenideElement suitableForChildrenCheckbox = modal.$x(INPUT_TYPE_CHECKBOX);

    // buttons
    private SelenideElement clearAll = modal.$x(".//b[.='Clear all']");
    private SelenideElement apply = modal.$x(".//button[.='Apply']");
    private SelenideElement close = modal.$x(".//*[local-name()='use' and @*='#close']/..");

}