package jp.kobe_u.root.shelter_navi.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties( prefix="app.admin" )
public class AdminConfiguration {
    //プロパティは外部ファイルからDIするが，ない場合は下記のデフォルト値を使う
    private String username="admin";
    private String password="secret";
}