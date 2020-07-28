package jp.kobe_u.root.shelter_navi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	@GetMapping("/hello")
	public String hello(@RequestParam("name") String name) {
		return "Hello " + name + "!";
	}
}