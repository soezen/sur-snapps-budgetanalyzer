package sur.snapps.budgetanalyzer.domain.purchase;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.hibernate.envers.Audited;
import sur.snapps.budgetanalyzer.domain.BaseAuditedEntity;
import sur.snapps.budgetanalyzer.domain.product.Product;
import sur.snapps.budgetanalyzer.domain.store.StoreLocation;
import sur.snapps.budgetanalyzer.domain.user.Account;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

/**
 * User: SUR
 * Date: 26/04/14
 * Time: 11:44
 */
@Entity
@Table(name = "PURCHASES")
@Audited
public class Purchase extends BaseAuditedEntity {

    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "STORE_LOCATION_ID")
    private StoreLocation store;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "PURCHASE_ID")
    private List<PurchasedProduct> products;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "PURCHASE_ID")
    private List<Payment> payments;

    private Purchase() {}

    private Purchase(Builder builder) {
        this.date = builder.date;
        this.store = builder.store;
        this.products = builder.products;
        this.payments = builder.payments;
    }

    public Date date() {
        return date;
    }

    public StoreLocation store() {
        return store;
    }

    public List<PurchasedProduct> products() {
        return ImmutableList.copyOf(products);
    }

    public static Builder newPurchase() {
        return new Builder();
    }

    @Override
    public String getDisplayValue() {
        return null;
    }

    public static class Builder {
        private Date date;
        private StoreLocation store;
        private List<PurchasedProduct> products = Lists.newArrayList();
        private List<Payment> payments = Lists.newArrayList();

        public Purchase build() {
            return new Purchase(this);
        }

        public Builder date(Date date) {
            this.date = date;
            return this;
        }

        public Builder store(StoreLocation store) {
            this.store = store;
            return this;
        }

        public Builder product(Product product, double amount, double unitPrice) {
            PurchasedProduct purchasedProduct = PurchasedProduct.newPurchasedProduct()
                .product(product)
                .amount(amount)
                .unitPrice(unitPrice)
                .build();
            products.add(purchasedProduct);
            return this;
        }

        public Builder payment(Account account, double amount) {
            Payment payment = Payment.newPayment()
                .account(account)
                .amount(amount)
                .build();
            payments.add(payment);
            return this;
        }
    }
}
