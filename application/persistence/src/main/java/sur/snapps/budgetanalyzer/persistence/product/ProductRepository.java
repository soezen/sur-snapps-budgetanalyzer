package sur.snapps.budgetanalyzer.persistence.product;

import sur.snapps.budgetanalyzer.domain.product.Product;
import sur.snapps.budgetanalyzer.domain.product.ProductType;
import sur.snapps.budgetanalyzer.domain.store.Store;
import sur.snapps.budgetanalyzer.domain.store.StoreProduct;
import sur.snapps.budgetanalyzer.persistence.AbstractRepository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * User: SUR
 * Date: 22/04/14
 * Time: 19:24
 */
public class ProductRepository extends AbstractRepository {

    // TODO test this class
    public StoreProduct findByCode(String storeId, String productCode) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<StoreProduct> query = builder.createQuery(StoreProduct.class);

        Root<StoreProduct> fromStoreProduct = query.from(StoreProduct.class);
        Join<StoreProduct, Store> joinStore = fromStoreProduct.join("store");

        Predicate codeCondition = builder.equal(fromStoreProduct.get("code"), productCode);
        Predicate storeCondition = builder.equal(joinStore.get("id"), storeId);
        query.where(builder.and(codeCondition, storeCondition));

        try {
            return entityManager.createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Product findById(String id) {
        return entityManager.find(Product.class, id);
    }

    public List<ProductType> findProductTypes() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProductType> query = builder.createQuery(ProductType.class);

        Root<ProductType> fromProductTypes = query.from(ProductType.class);
        return entityManager.createQuery(query).getResultList();
    }
}
