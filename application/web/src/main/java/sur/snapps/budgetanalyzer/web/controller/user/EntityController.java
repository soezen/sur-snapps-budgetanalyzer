package sur.snapps.budgetanalyzer.web.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sur.snapps.budgetanalyzer.business.user.EditEntityView;
import sur.snapps.budgetanalyzer.business.user.EntityManager;
import sur.snapps.budgetanalyzer.domain.user.Entity;
import sur.snapps.budgetanalyzer.web.controller.AbstractLoggedInController;
import sur.snapps.budgetanalyzer.web.exception.ValidationException;
import sur.snapps.budgetanalyzer.web.navigation.NavigateTo;
import sur.snapps.budgetanalyzer.web.navigation.PageLinks;
import sur.snapps.budgetanalyzer.web.response.ResponseHolder;
import sur.snapps.budgetanalyzer.web.util.MessageUtil;

/**
 * @author sur
 * @since 23/03/2015
 */
@Controller
@RequestMapping("/entity")
public class EntityController extends AbstractLoggedInController {

    // TODO remove this method
    @Override
    public String activePage() {
        return null;
    }

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private EntityValidator entityValidator;

    @Autowired
    private MessageUtil messageUtil;

    @RequestMapping("/update-entity-name")
    @NavigateTo(PageLinks.PROFILE)
    public @ResponseBody
    ResponseHolder editEntityName(@RequestParam("editentityview-name") String name) {
        EditEntityView editEntity = new EditEntityView(currentUser().entity());
        editEntity.setName(name);

        return updateEntity(editEntity);
    }

    private ResponseHolder updateEntity(EditEntityView editEntity) {
        BindingResult bindingResult = new BeanPropertyBindingResult(editEntity, "editEntity");
        entityValidator.validate(editEntity, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getFieldErrors());
        }

        // TODO events overview (choose icons or show text)
        // TODO make invite new user a popup
        Entity updatedEntity = entityManager.update(editEntity);
        userContext.reset();
        return ResponseHolder.success(updatedEntity, messageUtil.translate("form.profile.update_entity.success"));
    }
}
