package sur.snapps.budgetanalyzer.business.transaction;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import sur.snapps.budgetanalyzer.business.product.EditProductView;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import static sur.snapps.budgetanalyzer.util.DateUtil.now;
import static sur.snapps.budgetanalyzer.util.DateUtil.removeTime;

/**
 * @author sur
 * @since 17/03/2015
 */
public class EditPurchaseView implements Serializable {

    private Date date;
    private String storeLocationId;
    private List<EditProductView> products;

    public EditPurchaseView() {
        date = removeTime(now());
        products = Lists.newArrayList();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStoreLocationId() {
        return storeLocationId;
    }

    public void setStoreLocationId(String storeLocationId) {
        this.storeLocationId = storeLocationId;
    }

    public List<EditProductView> getProducts() {
        return products;
    }

    public List<EditProductView> getProductsFiltered() {
        return FluentIterable.from(products)
            .filter(new Predicate<EditProductView>() {
                @Override
                public boolean apply(EditProductView input) {
                    return input.getId() != null;
                }
            }).toList();
    }

    public void setProducts(List<EditProductView> products) {
        this.products = products;
    }

    public void addProduct(EditProductView product) {
        products.add(product);
    }
}
