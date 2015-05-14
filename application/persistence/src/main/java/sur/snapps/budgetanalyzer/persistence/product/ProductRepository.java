package sur.snapps.budgetanalyzer.persistence.product;

import sur.snapps.budgetanalyzer.domain.product.Category;
import sur.snapps.budgetanalyzer.domain.product.Product;
import sur.snapps.budgetanalyzer.domain.product.ProductType;
import sur.snapps.budgetanalyzer.domain.product.ProductTypeForPeriod;
import sur.snapps.budgetanalyzer.domain.purchase.Purchase;
import sur.snapps.budgetanalyzer.domain.purchase.PurchasedProduct;
import sur.snapps.budgetanalyzer.domain.store.Store;
import sur.snapps.budgetanalyzer.domain.store.StoreProduct;
import sur.snapps.budgetanalyzer.persistence.AbstractRepository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

/**
 * User: SUR
 * Date: 22/04/14
 * Time: 19:24
 */
public class ProductRepository extends AbstractRepository {

    public Category save(Category category) {
        entityManager.persist(category);
        return category;
    }

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

    // TODO load all products and categories at once in context and that for entire application scope
    public List<ProductType> findProductTypes(String categoryId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProductType> query = builder.createQuery(ProductType.class);

        Root<ProductType> fromProductTypes = query.from(ProductType.class);
        Predicate categoryCondition = builder.equal(fromProductTypes.get("category").get("id"), categoryId);
        query.where(categoryCondition);

        return entityManager.createQuery(query).getResultList();
    }

    public List<Product> findProductsOfType(String productTypeId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = builder.createQuery(Product.class);

        Root<Product> fromProducts = query.from(Product.class);
        Predicate productTypeCondition = builder.equal(fromProducts.get("type").get("id"), productTypeId);
        query.where(productTypeCondition);

        return entityManager.createQuery(query).getResultList();
    }

    public Product findById(String id) {
        return entityManager.find(Product.class, id);
    }

    public Category findCategoryById(String id) {
        return entityManager.find(Category.class, id);
    }

    public List<ProductTypeForPeriod> findProductTypesForPeriod(Date startDate, Date endDate) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProductTypeForPeriod> query = builder.createQuery(ProductTypeForPeriod.class);

        Root<Purchase> fromPurchases = query.from(Purchase.class);
        Join<Purchase, PurchasedProduct> joinPurchasedProducts = fromPurchases.join("products");
        Join<PurchasedProduct, Product> joinProducts = joinPurchasedProducts.join("product");
        Join<Product, ProductTypeForPeriod> joinProductTypes = joinProducts.join("type");

        query.multiselect(joinProductTypes, builder.sum(
            builder.prod(
                joinPurchasedProducts.<Number>get("amount"),
                joinPurchasedProducts.<Number>get("unitPrice"))));
        query.groupBy(joinProductTypes);

        Path<Date> purchaseDate = fromPurchases.get("date");
        query.where(builder.and(
            builder.greaterThanOrEqualTo(purchaseDate, startDate)),
            builder.lessThanOrEqualTo(purchaseDate, endDate));

        return entityManager.createQuery(query).getResultList();
    }

    // TODO put categories in application scoped controller

    public List<Category> findCategoriesWithParent(Category parent) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Category> query = builder.createQuery(Category.class);

        Root<Category> fromCategories = query.from(Category.class);
        Path<Object> parentField = fromCategories.get("parent");
        if (parent == null) {
            query.where(builder.isNull(parentField));
        } else {
            query.where(builder.equal(parentField, parent));
        }

        return entityManager.createQuery(query).getResultList();
    }

    public List<Product> findAll() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = builder.createQuery(Product.class);
        query.from(Product.class);

        return entityManager.createQuery(query).getResultList();
    }
}
