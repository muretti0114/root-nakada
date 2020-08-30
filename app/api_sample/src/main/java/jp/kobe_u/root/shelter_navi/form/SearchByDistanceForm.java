package jp.kobe_u.root.shelter_navi.form;

import lombok.Data;

@Data
public class SearchByDistanceForm {
    /**
     * distance, userLng, userLat >= 0
     * 等のValidationをかける
     */
    private Double userLng;
    private Double userLat;
    private Double distance;
}