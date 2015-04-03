package sur.snapps.budgetanalyzer.web.controller.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sur.snapps.budgetanalyzer.business.product.ProductManager;
import sur.snapps.budgetanalyzer.business.product.summary.CategorySummary;
import sur.snapps.budgetanalyzer.domain.store.StoreProduct;
import sur.snapps.budgetanalyzer.web.controller.AbstractController;
import sur.snapps.budgetanalyzer.web.navigation.NavigateTo;
import sur.snapps.budgetanalyzer.web.navigation.PageLinks;
import sur.snapps.budgetanalyzer.web.response.ResponseHolder;

/**
 * @author sur
 * @since 23/03/2015
 */
@Controller
@RequestMapping("/products")
public class ProductController extends AbstractController {

    @Autowired
    private ProductManager productManager;

    // TODO create config role which can configure categories and stores
    // perhaps later allow categories and stores to be entity specific

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

    // TODO add param for which months we want the summary
    @ResponseBody
    @RequestMapping("/categories-summary-month")
    public CategorySummary categoriesSummaryForMonth() {
        CategorySummary summary = productManager.generateCategorySummary();
        return summary;
    }
}
