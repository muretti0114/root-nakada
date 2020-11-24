package jp.kobe_u.root.shelter_navi.application.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import jp.kobe_u.root.shelter_navi.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserForm {
    @NotBlank
    @Email
    String email;

    @NotBlank
    @Size( min = 8, message = "パスワードは8文字以上" )
    String password;

    @NotBlank
    @Size( min = 8, message = "パスワードは8文字以上" )
    String password2;

    @Size( max = 32 )
    String name;

    @Size( max = 32 )
    String phoneNumber;

    @Size( max = 128 )
    String address;

    //@NotBlank
    int numOfHouseholds;

    public User toEntity() {
        return new User( email, password, name, phoneNumber, address, numOfHouseholds, User.Role.CITIZEN );
    }

    public static UserForm toForm( User user ) {
        return new UserForm( user.getEmail(), user.getPassword(), user.getPassword(), user.getName(), user.getPhoneNumber(), user.getAddress(), user.getNumOfHouseholds() );
    } 
}
