package jp.kobe_u.root.shelter_navi.entity;

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
    private String email;

    @NotBlank
    @Size( max=128 )
    private String password;

    @NotBlank
    @Size(max=32)
    private String name;

    @Size(max=32)
    private String phoneNumber;

    @NotBlank
    private Integer numOfHouseholds; // 世帯数

    private Role role;

    public enum Role {
        CITIZEN,
        GOVERNMENT,
        ADMIN
    }
}
