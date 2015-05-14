package sur.snapps.budgetanalyzer.web.controller.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sur.snapps.budgetanalyzer.business.product.EditCategoryView;
import sur.snapps.budgetanalyzer.business.product.ProductManager;
import sur.snapps.budgetanalyzer.business.product.summary.CategorySummary;
import sur.snapps.budgetanalyzer.domain.product.Category;
import sur.snapps.budgetanalyzer.domain.store.StoreProduct;
import sur.snapps.budgetanalyzer.util.DateUtil;
import sur.snapps.budgetanalyzer.web.controller.AbstractLoggedInController;
import sur.snapps.budgetanalyzer.web.navigation.NavigateTo;
import sur.snapps.budgetanalyzer.web.navigation.PageLinks;
import sur.snapps.budgetanalyzer.web.response.ResponseHolder;

import javax.validation.Valid;

/**
 * @author sur
 * @since 23/03/2015
 */
@Controller
@RequestMapping("/products")
public class ProductController extends AbstractLoggedInController {

    @Autowired
    private ProductManager productManager;

    @Autowired
    private CategoryValidator categoryValidator;

    // TODO create config role which can configure categories and stores
    // perhaps later allow categories and stores to be entity specific

    @RequestMapping("/overview")
    public String openOverviewPage() {
        return PageLinks.PRODUCTS_OVERVIEW.page();
    }

    @ResponseBody
    @RequestMapping("/findSubCategories")
    public ResponseHolder findSubCategories(@RequestParam("categoryId") String categoryId) {
        Category category = productManager.findCategoryById(categoryId);
        return ResponseHolder.success(productManager.findCategoriesWithParent(category));
    }

    @ResponseBody
    @RequestMapping("/findProductTypes")
    public ResponseHolder findProductTypes(@RequestParam("categoryId") String categoryId) {
        return ResponseHolder.success(productManager.findProductTypes(categoryId));
    }

    @ResponseBody
    @RequestMapping("/findProducts")
    public ResponseHolder findProducts(@RequestParam("productTypeId") String productTypeId) {
        return ResponseHolder.success(productManager.findProductsOfType(productTypeId));
    }

    @ResponseBody
    @RequestMapping("/findProductByCode")
    @NavigateTo(PageLinks.PROFILE)
    public ResponseHolder findProductByCode(@RequestParam("storeId") String storeId, @RequestParam("productCode") String productCode) {
        StoreProduct product = productManager.findByCode(storeId, productCode);
        if (product != null) {
            return ResponseHolder.success(product);
        }
        return ResponseHolder.failure("product with code " + productCode + " not found for store " + storeId);
    }

    @RequestMapping("/search")
    public String openSearchPopup(Model model) {
        model.addAttribute("categories", productManager.findCategoriesWithParent(null));
        model.addAttribute("products", productManager.findAll());
        return PageLinks.PRODUCT_SEARCH.page();
    }

    @RequestMapping("/openNewCategoryPopup")
    public String openNewCategoryPopup(Model model) {
        model.addAttribute("category", new EditCategoryView());
        return PageLinks.CATEGORY_CREATE.page();
    }

    @ResponseBody
    @RequestMapping("/postNewCategory")
    public ResponseHolder createNewCategory(@ModelAttribute("category") @Valid EditCategoryView category, BindingResult bindingResult) {
        categoryValidator.validate(category, bindingResult);

        if (bindingResult.hasErrors()) {
            // TODO translate errors
            return ResponseHolder.failure("form.errors.validation");
        }

        Category savedCategory = productManager.create(category);
        return ResponseHolder.success(savedCategory);
    }

    // TODO add param for which months we want the summary
    @ResponseBody
    @RequestMapping("/categories-summary-month")
    public CategorySummary categoriesSummaryForMonth() {
        CategorySummary summary = productManager.generateCategorySummary(DateUtil.now());
        return summary;
    }

    @Override
    public String activePage() {
        return "products";
    }
}
