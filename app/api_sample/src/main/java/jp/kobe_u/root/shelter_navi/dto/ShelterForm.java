package jp.kobe_u.root.shelter_navi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShelterForm {
    private String id;
    private String name;
    private String address;
    private Double lng;
    private Double lat;
    private Integer area;
    private Integer maxFamilies;
    private String contactPhone;
    private String note;
    private Long localGovernmentCode;
}