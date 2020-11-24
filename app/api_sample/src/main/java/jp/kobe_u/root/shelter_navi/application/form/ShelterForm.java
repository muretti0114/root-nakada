package jp.kobe_u.root.shelter_navi.application.form;

import jp.kobe_u.root.shelter_navi.domain.entity.Shelter;
//import javax.validation.constrains.Size;
import lombok.Data;

@Data
public class ShelterForm {
    //@Size
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

    public Shelter createShelter() {
        return new Shelter( id, name, address, lng, lat, area, max_families, contact_phone, note, local_government_code );
    }
}