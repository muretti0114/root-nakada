package jp.kobe_u.root.shelter_navi.domain.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Checkin {
    @Id
    private Long id;

    @NonNull
    private String uid;

    @NonNull
    private Long sid;

    private Date checkin;

    private Date checkout;
}
