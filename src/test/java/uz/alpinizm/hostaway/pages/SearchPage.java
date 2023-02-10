package uz.alpinizm.hostaway.pages;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import uz.alpinizm.hostaway.pages.components.DatePicker;
import uz.alpinizm.hostaway.pages.components.FiltersDialog;
import uz.alpinizm.hostaway.pages.components.SearchBar;

import static com.codeborne.selenide.Selenide.$x;

@Getter
public class SearchPage extends BasePage {

    // components
    private SearchBar searchBar = new SearchBar();
    private DatePicker datePicker = new DatePicker();
    private FiltersDialog filtersDialog = new FiltersDialog();

    // elements
    private SelenideElement searchButton = $x("//button[.='Search']");

}