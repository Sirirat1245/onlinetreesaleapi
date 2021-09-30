package onlinesale.onlinetree.controller;

import onlinesale.onlinetree.model.service.ProfileRegisterRepository;
import onlinesale.onlinetree.model.table.DiscountForFriend;
import onlinesale.onlinetree.model.table.ProfileRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
public class ProfileRegisterAPIController {

    @Autowired
    private ProfileRegisterRepository profileRegisterRepository;

    @PostMapping("/save")
    public Object save(ProfileRegister profileRegister){

        APIResponse res = new APIResponse();
        ProfileRegister _profile = profileRegisterRepository.findByUserNameAndPassWord(profileRegister.getUserName(), profileRegister.getPassWord());
        System.out.println("_profile: " + _profile);
        try {
            if(_profile == null){
                profileRegisterRepository.save(profileRegister);
                res.setData(profileRegister);
                res.setStatus(1);
                res.setMessage("save ok");
            }else{
                res.setData(profileRegister);
                res.setStatus(0);
                res.setMessage("repeat");
            }
        }catch (Exception err){
            res.setStatus(0);
            res.setMessage("error : " + err.toString());
        }
        return res;
    }

    @PostMapping("/login")
    public Object login(ProfileRegister profileRegister) {
        APIResponse res = new APIResponse();
        System.out.println("profileRegister: " + profileRegister);
        try {
            ProfileRegister _profileRegister = profileRegisterRepository.findByUserNameAndPassWord(profileRegister.getUserName(), profileRegister.getPassWord());
            System.out.println("_profileRegister: " + _profileRegister);
            if (_profileRegister == null) {
                System.out.println("no user");
                res.setStatus(0);
                res.setMessage("no username");
            } else {
                System.out.println("login");
                res.setStatus(1);
                res.setMessage("Login");
                res.setData(_profileRegister);
            }
        } catch (Exception err){
            res.setStatus(-1);
            res.setMessage("err : " + err.toString());
        }
        return res;
    }

    //suspend ที่นี่ได้
    @PostMapping("/update")
    public Object update(ProfileRegister profileRegister){
        APIResponse res = new APIResponse();
        try {
            Integer status = profileRegisterRepository.updateProfileRegisterByUserNameAndPassWord(
                    profileRegister.getPrefix(),
                    profileRegister.getFirstName(),
                    profileRegister.getLastName(),
                    profileRegister.getEmail(),
                    profileRegister.getTel(),
                    profileRegister.getAddress(),
                    profileRegister.isStatus(),
                    profileRegister.getUserName(),
                    profileRegister.getPassWord()
            );
            System.out.println("status : " + status);
            if(status == 1){
                res.setStatus(1);
                res.setMessage("edit");
                res.setData(profileRegisterRepository.findByUserNameAndPassWord(
                        profileRegister.getUserName(),
                        profileRegister.getPassWord()
                ));
            }
        }catch (Exception err){
            res.setStatus(-1);
            res.setMessage("error : " + err.toString());
        }
        return res;
    }
}
