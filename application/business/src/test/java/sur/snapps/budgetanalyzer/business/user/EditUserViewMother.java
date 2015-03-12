package sur.snapps.budgetanalyzer.business.user;

import sur.snapps.budgetanalyzer.domain.user.Email;
import sur.snapps.budgetanalyzer.domain.user.Entity;
import sur.snapps.budgetanalyzer.domain.user.Token;

/**
 * User: SUR
 * Date: 17/08/14
 * Time: 14:31
 */
public class EditUserViewMother {

    public static EditUserView michellePfeiffer() {
        EditUserView view = new EditUserView();
        view.setUsername("michelle");
        view.setName("Michelle Pfeiffer");
        view.setEmail("michelle.pfeiffer@mail.com");
        view.setConfirmPassword("password");
        view.setNewPassword("password");
        return view;
    }

    public static EditUserView antonioBanderas() {
        EditUserView view = new EditUserView(valid());
        view.setUsername("antonio");
        view.setName("Antonio Banderas");
        view.setNewPassword("password");
        view.setConfirmPassword("password");
        return view;
    }

    // TODO move to different class (TokenMother)
    public static Token valid() {
        Entity entity = Entity.newOwnedEntity()
                .name("entity-name")
                .build();
        Token token = Token.createUserInvitationToken()
                .generateToken()
                .email(Email.create("antonio.banderas@mail.com"))
                .entity(entity)
                .build();
        return token;
    }
}
