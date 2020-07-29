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
/**
 * idはシステム側が決定する
 * localGovernmentCodeもシステム側で調べる？
 * デモ版では入力してもらう？
 */
public class Shelter {
    @Id
    private Long id;
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