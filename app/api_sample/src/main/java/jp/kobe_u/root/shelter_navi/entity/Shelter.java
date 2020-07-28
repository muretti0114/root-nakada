package jp.kobe_u.root.shelter_navi.entity;

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
    private long id;
    private String local_government;
    private long local_government_coad;
    private int serial_number;
    private String shelter_name;
    private String address;
    private double lng;
    private double lat;
    private double area;
    private int num_of_households;
    private String telephone_number;
}