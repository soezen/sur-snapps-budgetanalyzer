package sur.snapps.budgetanalyzer.business.product.summary;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import sur.snapps.budgetanalyzer.domain.product.Category;
import sur.snapps.budgetanalyzer.domain.product.ProductType;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author sur
 * @since 03/04/2015
 */
public class CategorySummarySteps {

    private CategorySummary summary;
    private Gson gson;

    @Before
    public void setup() {
        gson = new GsonBuilder()
            .registerTypeAdapter(CategorySummary.class, new CategorySummaryDeserializer())
            .create();
    }

    @Given("^existing summary$")
    public void jsonCategorySummary(String json) {
        summary = gson.fromJson(json, CategorySummary.class);
    }

    @When("^adding ProductType (.*) with categories: (.*)$")
    public void addProductType(String productTypeId, List<String> categories) {
        assertFalse(categories.isEmpty());

        LinkedList<String> categoryIds = Lists.newLinkedList(categories);
        String firstCategoryId = categoryIds.pollFirst();
        Category parent = Category.newCategory().id(firstCategoryId).name(firstCategoryId).build();
        for (String category : categoryIds) {
            parent = parent.createSubCategory().id(category).name(category).build();
        }
        ProductType productType = parent.createProductType().id(productTypeId).name(productTypeId).build();
        summary.add(productType);
    }

    @Then("^expected summary$")
    public void assertJson(String json) {
        assertCategorySummaryEquals(gson.fromJson(json, CategorySummary.class), summary);
    }

    private void assertCategorySummaryEquals(CategorySummary expected, CategorySummary actual) {
        if (expected == null) {
            assertNull(actual);
        }
        assertNotNull(actual);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());

        if (expected instanceof ChildCategorySummary) {
            assertTrue(actual instanceof ChildCategorySummary);
            assertEquals(((ChildCategorySummary) expected).getSize(), ((ChildCategorySummary) actual).getSize());
        } else if (expected instanceof ParentCategorySummary) {
            assertTrue(actual instanceof ParentCategorySummary);
            ParentCategorySummary expectedParent = (ParentCategorySummary) expected;
            ParentCategorySummary actualParent = (ParentCategorySummary) actual;
            assertEquals("nbr of children not equal for " + expectedParent.getId() + " and " + actualParent.getId(),
                expectedParent.getChildren().size(), actualParent.getChildren().size());

            for (int i = 0; i < expectedParent.getChildren().size(); i++) {
                assertCategorySummaryEquals(expectedParent.getChildren().get(i), actualParent.getChildren().get(i));
            }
        } else {
            fail("not yet implemented");
        }
    }

}
