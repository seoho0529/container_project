package pack.controller.owner;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import pack.model.owner.OwnerDao;
import pack.model.owner.OwnerDto;
import pack.model.user.UserDto;


//**** 광진 ****//
@Controller
public class OwnerController {
	
	@Autowired
	private OwnerDao ownerDao;
	
	// 회원가입 선택란에서 공급자를 선택할 때, 공급자 로그인 페이지에서 회원가입을 클릭했을 때 (성공)
	@GetMapping("ownerJoinGo")
	public String ownerChoice() {
		return "../templates/owner/ownerjoin";
	}
	
	// 공급자 로그인 페이지에서 사용자 로그인을 클릭했을 때 (성공)
	@GetMapping("userlogingo")
	public String userLoginGo() {
		return "../templates/user/userlogin";
	}
	
	
	// 공급자 회원가입에서 가입 버튼을 클릭하고 성공 했을 때 (성공)
	@PostMapping("ownerJoinClick")
	public String ownerloginOK(OwnerDto ownerDto) {
		boolean b = ownerDao.ownerinsertData(ownerDto);
		if(b) {
			return "../templates/owner/ownerlogin";  
		} else {
			return "../templates/user/userfail";  
		}
		
	}
	
	// 공급자 로그인 페이지에서 요청 처리 (성공)
    @PostMapping("ownerLogSuccess")
    public String processLoginForm(@RequestParam("business_num") String business_num,
            					   @RequestParam("owner_pwd") String owner_pwd,
                                   Model model, HttpSession session){
        // 사용자 로그인 처리
    	OwnerDto owner = ownerDao.ownerloginProcess(business_num, owner_pwd);

        if (owner != null) {
        	session.setAttribute("owner", owner);
            return "../templates/owner/ownermain"; // 로그인 성공 시 ownermain.html로 이동
            
        } else {
            // 로그인 실패
            return "../templates/user/userfail"; 
        }
    }
    
    // 공급자 마이페이지 에서 회원수정 클릭할 때 
    @GetMapping("/ownerupdate")
    public String ownerUpdatePage (Model model, HttpSession session) {
    	OwnerDto owner = (OwnerDto) session.getAttribute("owner");
    	model.addAttribute("owner", owner);
    	
    	return "../templates/owner/ownerupdate";
    }
    
    // 공급자 회원수정 페이지에서 회원수정 클릭할 때 (일단 성공이지만 세션값 최신으로 유지되게 설정하기)
    @PostMapping("ownerInfoUpdate")
    public String ownerInfoupdate(OwnerDto ownerDto, Model model, HttpSession session) {
    	boolean b = ownerDao.ownerupdate(ownerDto);
		if(b) {
			OwnerDto owner = (OwnerDto) session.getAttribute("owner");
			model.addAttribute("owner", owner);
			return "../templates/owner/ownermypage";  
		} else {
			return "../templates/user/userfail";  
		}
    }
      
}
