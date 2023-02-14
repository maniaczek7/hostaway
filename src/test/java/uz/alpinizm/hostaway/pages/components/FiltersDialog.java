package uz.alpinizm.hostaway.pages.components;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

import static com.codeborne.selenide.Selenide.$x;
import static uz.alpinizm.hostaway.pages.BasePage.INPUT_TYPE_CHECKBOX;

@Getter
public class FiltersDialog {

    // filters
    private final SelenideElement modal = $x("//div[@class='__modal']");

    // price
    private final SelenideElement priceFrom = modal.$x(".//input[@placeholder='From']");
    private final SelenideElement priceTo = modal.$x(".//input[@placeholder='To']");
    private final SelenideElement priceInfoMessage = modal.$x(".//p[.='To filter by price, please select dates']");

    // Rooms and beds
    // beds
    private final SelenideElement bedsLabel = modal.$x(".//div[.='Beds']");
    private final SelenideElement bedsMinus = bedsLabel.$x("(./following-sibling::*//button)[1]");
    private final SelenideElement bedsCount = bedsLabel.$x("./following-sibling::*//span");
    private final SelenideElement bedsPlus = bedsLabel.$x("(./following-sibling::*//button)[2]");

    // bedrooms
    private final SelenideElement bedroomsLabel = modal.$x(".//div[.='Bedrooms']");
    private final SelenideElement bedroomsMinus = bedroomsLabel.$x("(./following-sibling::*//button)[1]");
    private final SelenideElement bedroomsCount = bedroomsLabel.$x("./following-sibling::*//span");
    private final SelenideElement bedroomsPlus = bedroomsLabel.$x("(./following-sibling::*//button)[2]");

    // bathrooms
    private final SelenideElement bathroomsLabel = modal.$x(".//div[.='Bathrooms']");
    private final SelenideElement bathroomsMinus = bathroomsLabel.$x("(./following-sibling::*//button)[1]");
    private final SelenideElement bathroomsCount = bathroomsLabel.$x("./following-sibling::*//span");
    private final SelenideElement bathroomsPlus = bathroomsLabel.$x("(./following-sibling::*//button)[2]");

    private final ElementsCollection allRoomsAndBedsLabels = modal.$$x(".//div[.='Beds']|.//div[.='Bedrooms']|.//div[.='Bathrooms']");

    // Amenities
    // all
    private final ElementsCollection amenitiesLabels = modal.$$x(".//label");

    // labels
    private final SelenideElement beachFrontLabel = modal.$x(".//label[.='Beach front']");
    private final SelenideElement swimmingPoolLabel = modal.$x(".//label[.='Swimming pool']");
    private final SelenideElement freeWiFiLabel = modal.$x(".//label[.='Free WiFi']");
    private final SelenideElement kitchenLabel = modal.$x(".//label[.='Kitchen']");
    private final SelenideElement airConditioningLabel = modal.$x(".//label[.='Air conditioning']");
    private final SelenideElement washingMachineLabel = modal.$x(".//label[.='Washing Machine']");
    private final SelenideElement petsAllowedLabel = modal.$x(".//label[.='Pets allowed']");
    private final SelenideElement hotTubLabel = modal.$x(".//label[.='Hot tub']");
    private final SelenideElement streetParkingLabel = modal.$x(".//label[.='Street parking']");
    private final SelenideElement suitableForChildrenLabel = modal.$x(".//label[.='Suitable for children']");

    // inputs
    private final SelenideElement beachFrontCheckbox = modal.$x(INPUT_TYPE_CHECKBOX);
    private final SelenideElement swimmingPoolCheckbox = modal.$x(INPUT_TYPE_CHECKBOX);
    private final SelenideElement freeWiFiCheckbox = modal.$x(INPUT_TYPE_CHECKBOX);
    private final SelenideElement kitchenCheckbox = modal.$x(INPUT_TYPE_CHECKBOX);
    private final SelenideElement airConditioningCheckbox = modal.$x(INPUT_TYPE_CHECKBOX);
    private final SelenideElement washingMachineCheckbox = modal.$x(INPUT_TYPE_CHECKBOX);
    private final SelenideElement petsAllowedCheckbox = modal.$x(INPUT_TYPE_CHECKBOX);
    private final SelenideElement hotTubCheckbox = modal.$x(INPUT_TYPE_CHECKBOX);
    private final SelenideElement streetParkingCheckbox = modal.$x(INPUT_TYPE_CHECKBOX);
    private final SelenideElement suitableForChildrenCheckbox = modal.$x(INPUT_TYPE_CHECKBOX);

    // buttons
    private final SelenideElement clearAll = modal.$x(".//b[.='Clear all']");
    private final SelenideElement apply = modal.$x(".//button[.='Apply']");
    private final SelenideElement close = modal.$x(".//*[local-name()='use' and @*='#close']/..");

}