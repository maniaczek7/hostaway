package uz.alpinizm.hostaway.tests;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import uz.alpinizm.hostaway.pages.AllListingsPage;
import uz.alpinizm.hostaway.pages.LandingPage;

import java.time.Duration;

import static com.codeborne.selenide.Condition.cssValue;
import static java.lang.Integer.parseInt;
import static org.apache.commons.lang3.StringUtils.substringBetween;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AllListingsTests extends BaseTest {

    // pages
    private LandingPage landingPage = new LandingPage();
    private AllListingsPage allListingsPage = new AllListingsPage();

    // 3. Check that ‘All listings’ page (https://kamil-demo.alpinizm.uz/all-listings)
    @Test
    public void allListingsLabelsCountTest() {
        // labels show the correct number of listings
        // fixme: app bug, sometimes listing starts with second page (with offset), try clicking All label couple of times and check the results
        landingPage.getHeader().getAllListingsLink().click();
        verifyListingsCount(allListingsPage.getFireplace());
        verifyListingsCount(allListingsPage.getTest());
        verifyListingsCount(allListingsPage.getVilla());
        verifyListingsCount(allListingsPage.getAll());
    }

    private void verifyListingsCount(SelenideElement label) {
        label.click();
        int expectedFireplaceCount = parseInt(substringBetween(label.text(), "(", ")"));
        scrollToTheEndOfTheList(this.allListingsPage.getListings());
        assertEquals(expectedFireplaceCount, this.allListingsPage.getListings().size());
    }

    private void scrollToTheEndOfTheList(ElementsCollection listings) {
        waitForListToLoad();
        int listSize = listings.size();
        allListingsPage.getListings().last().scrollIntoView(true);
        waitForListToLoad();

        while (listSize != listings.size()){
            listSize = listings.size();
            allListingsPage.getListings().last().scrollIntoView(true);
            waitForListToLoad();
        }
    }

    private void waitForListToLoad() {
        allListingsPage.getLoaded().shouldHave(cssValue("display","block"), Duration.ofSeconds(15));
    }
}


