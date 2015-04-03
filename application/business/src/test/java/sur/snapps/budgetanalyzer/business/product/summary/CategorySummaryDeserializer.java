package sur.snapps.budgetanalyzer.business.product.summary;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * @author sur
 * @since 03/04/2015
 */
public class CategorySummaryDeserializer implements JsonDeserializer<CategorySummary> {

    @Override
    public CategorySummary deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.getAsJsonObject().get("size") != null) {
            return context.deserialize(json, ChildCategorySummary.class);
        }
        return context.deserialize(json, ParentCategorySummary.class);
    }
}
