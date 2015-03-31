package sur.snapps.budgetanalyzer.domain;

import org.junit.Test;
import sur.snapps.budgetanalyzer.domain.product.Category;
import sur.snapps.budgetanalyzer.domain.product.Product;
import sur.snapps.budgetanalyzer.domain.product.ProductType;
import sur.snapps.budgetanalyzer.domain.property.Property;
import sur.snapps.budgetanalyzer.domain.property.PropertyType;
import sur.snapps.budgetanalyzer.domain.purchase.Purchase;
import sur.snapps.budgetanalyzer.domain.store.Address;
import sur.snapps.budgetanalyzer.domain.store.Store;
import sur.snapps.budgetanalyzer.domain.store.StoreLocation;
import sur.snapps.budgetanalyzer.domain.store.StoreType;
import sur.snapps.budgetanalyzer.domain.user.Account;
import sur.snapps.budgetanalyzer.domain.user.Entity;
import sur.snapps.budgetanalyzer.domain.user.User;
import sur.snapps.budgetanalyzer.util.DateUtil;

/**
 * @author sur
 * @since 26/03/2015
 */
public class MainTest {

    @Test
    public void main() {
        // existing data
        Store colruyt = Store.newStore().name("Colruyt").type(StoreType.DEPARTMENT_STORE).build();
        Address addressColruytHarelbeke = Address.newAddress().street("Deerlijksesteenweg").number("23").zipCode("8530").city("Harelbeke").country("BE").build();
        StoreLocation colruytHarelbeke = StoreLocation.newStoreLocation().name("Colruyt Harelbeke").address(addressColruytHarelbeke).build();
        colruyt.addLocation(colruytHarelbeke);

        PropertyType brand = PropertyType.newPropertyType().name("brand").build();
        Property boniSelection = brand.createProperty().value("Boni Selection").build();
        Property smiths = brand.createProperty().value("Smiths").build();
        Property lays = brand.createProperty().value("Lay's").build();

        PropertyType weight = PropertyType.newPropertyType().name("weight").build();
        // TODO create other type of property which has units
        Property weight275g = weight.createProperty().value("275g").build();
        Property weight150g = weight.createProperty().value("150g").build();
        Property weight30g = weight.createProperty().value("30g").build();

        PropertyType flavour = PropertyType.newPropertyType().name("flavour").build();
        Property nachoCheese = flavour.createProperty().value("Nacho Cheese").build();
        Property ketchup = flavour.createProperty().value("Ketchup").build();
        Property saltAndPepper = flavour.createProperty().value("Salt 'n' Pepper").build();

        // TODO if name is only property to be set, you can add it as parameter in the new method
        Category food = Category.newCategory().name("Food").build();
        Category meal = food.createSubCategory().name("Meal").build();
        ProductType pizza = meal.createProductType().name("Pizza").build();
        Product pizzaBolognese = pizza.createProduct().name("Boni Selection Pizza Bolognese 275g").property(boniSelection).property(weight275g).build();

        Category candy = food.createSubCategory().name("Candy").build();
        ProductType chips = candy.createProductType().name("Chips").build();
        Product buglesChips = chips.createProduct().name("Smiths Bugles Nacho Cheese 150g").property(smiths).property(weight150g).property(nachoCheese).build();
        Product laysKetchupChips = chips.createProduct().name("Lays Ketchup 30g").property(lays).property(weight30g).property(ketchup).build();
        Product laysSaltAndPepperChips = chips.createProduct().name("Lays Salt 'n' Pepper 30g").property(lays).property(weight30g).property(saltAndPepper).build();

        // a user registers on website
        Entity theSimpsons = Entity.newOwnedEntity().name("the Simpsons").shared(false).build();
        User homer = User.createAdmin().entity(theSimpsons).name("Homer Simpson").username("homer").email("homer.simpson@springfield.com").password("test").build();

        Account sodexoCard = Account.newAccount().name("Sodexo Card").owner(homer).build();

        // TODO store product is only used as a lookup mechanism
        // TODO store has a boolean to specify whether it makes use of store products

        Purchase purchase = Purchase.newPurchase()
            .date(DateUtil.now())
            .store(colruytHarelbeke)
            .product(pizzaBolognese, 1, 2.45)
            .product(buglesChips, 1, 1.35)
            .product(laysKetchupChips, 2, 0.85)
            .product(laysSaltAndPepperChips, 1, 0.85)
            .payment(sodexoCard, 5.5)
            .build();

    }
}
