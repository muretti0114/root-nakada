package jp.kobe_u.root.shelter_navi.domain.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @NotBlank
    @Email
    String email;

    @NotBlank
    @Size( max = 128 )
    String password;

    @NotBlank
    @Size( max = 32 )
    String name;

    @Size( max = 32 )
    String phoneNumber; // 入力は任意

    @Size( max = 128 )
    String address;

    //@NotBlank
    int numOfHouseholds; // 世帯数

    // 住所も加えておく，入力は任意

    Role role;

    public enum Role {
        CITIZEN,
        GOVERNMENT,
        ADMIN
    }
}
