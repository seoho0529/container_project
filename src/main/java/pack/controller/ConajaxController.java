package pack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//templates 폴더 내의 파일은 URL에서 직접 접근할 수 없으므로 컨트롤러를 통해 접근, conajax.html을 호출
@Controller
public class ConajaxController {
    @GetMapping("/conajax")
    public String index() {
        return "conajax";
    }
}
