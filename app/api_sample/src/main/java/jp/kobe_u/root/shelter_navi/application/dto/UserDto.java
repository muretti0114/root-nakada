package jp.kobe_u.root.shelter_navi.application.dto;

import jp.kobe_u.root.shelter_navi.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {
    String email;
    String name;
    String phoneNumber;
    int numOfHouseholds;

    public static UserDto build( User user ) {
        return new UserDto( user.getEmail(), user.getName(), user.getPhoneNumber(), user.getNumOfHouseholds() );
    }
}
