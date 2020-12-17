package jp.kobe_u.root.shelter_navi.domain.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Shelter {
    @Id
    private Long id;
    private String name;
    private String address;
    private Double lng;
    private Double lat;
    private Integer area;
    private Integer max_families;
    private String contact_phone;
    private String note;
    private Long local_government_code;
}