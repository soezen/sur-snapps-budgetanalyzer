package sur.snapps.budgetanalyzer.web.controller.store;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sur.snapps.budgetanalyzer.business.store.StoreManager;
import sur.snapps.budgetanalyzer.domain.store.Store;
import sur.snapps.budgetanalyzer.web.controller.AbstractController;
import sur.snapps.budgetanalyzer.web.response.ResponseHolder;

import java.util.List;

import static org.springframework.web.context.WebApplicationContext.SCOPE_APPLICATION;

/**
 * @author sur
 * @since 26/03/2015
 */
@Controller
@Scope(SCOPE_APPLICATION)
@RequestMapping("/stores")
public class StoreController extends AbstractController {

    @Autowired
    private StoreManager storeManager;

    public List<Store> stores;

    public List<Store> findStores() {
        if (stores == null) {
            stores = storeManager.findAll();
        }
        return stores;
    }


    @RequestMapping("/findStoreLocations")
    @ResponseBody
    public ResponseHolder findStoreLocations(@RequestParam("storeId") final String storeId) {
        if (storeId == null) {
            return ResponseHolder.success(Lists.newArrayList());
        }
        Predicate<Store> WITH_ID = new Predicate<Store>() {
            @Override
            public boolean apply(Store input) {
                return input.getId().equals(storeId);
            }
        };
        Store store = FluentIterable.from(stores).firstMatch(WITH_ID).orNull();
        if (store != null) {
            return ResponseHolder.success(store.locations());
        }
        return ResponseHolder.success(Lists.newArrayList());
    }
}
