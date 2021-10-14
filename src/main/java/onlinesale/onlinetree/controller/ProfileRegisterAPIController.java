package onlinesale.onlinetree.controller;

import onlinesale.onlinetree.model.service.ProfileRegisterRepository;
import onlinesale.onlinetree.model.table.DiscountForFriend;
import onlinesale.onlinetree.model.table.PayFor;
import onlinesale.onlinetree.model.table.ProfileRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    //for admin update user to cancel
    @PostMapping("/update_user")
    public Object updateUser(ProfileRegister profileRegister){
        APIResponse res = new APIResponse();
        try {
            Integer status = profileRegisterRepository.updateProfileRegisterByProfileRegisterId(
                    profileRegister.getProfileRegisterId()
            );
            System.out.println("status : " + status);
            if(status == 1){
                res.setStatus(1);
                res.setMessage("edit");
                res.setData(profileRegisterRepository.findByProfileRegisterId(
                        profileRegister.getProfileRegisterId()
                ));
            }
        }catch (Exception err){
            res.setStatus(-1);
            res.setMessage("error : " + err.toString());
        }
        return res;
    }

    @PostMapping("/delete")
    public Object delete(ProfileRegister profileRegister){
        APIResponse res = new APIResponse();
        try{
            profileRegisterRepository.deleteById(profileRegister.getProfileRegisterId());
            res.setStatus(1);
            res.setMessage("delete profile");
            res.setData(profileRegister);
        }catch (Exception err){
            res.setStatus(-1);
            res.setMessage("err : " + err.toString());
        }
        return res;
    }

    @PostMapping("/detail")
    public Object detail(ProfileRegister profileRegister){
        APIResponse res = new APIResponse();
        try {
            ProfileRegister dbProfile = profileRegisterRepository.findByProfileRegisterId(profileRegister.getProfileRegisterId());
            if( dbProfile == null ){
                res.setStatus(0);
                res.setMessage("no user");
                res.setData(profileRegister);
            } else {
                res.setStatus(1);
                res.setMessage("success");
                res.setData(dbProfile);
            }
        } catch (Exception err){
            res.setStatus(-1);
            res.setMessage("err : " + err.toString());
        }

        return res;
    }

    //list from status = true, false
    @PostMapping("/list_profile_register")
    public Object listProfileRegister(ProfileRegister profileRegister){
        APIResponse res = new APIResponse();
        try {
            List<ProfileRegister> lstData = profileRegisterRepository.lstProfileRegisterByStatus(
                    profileRegister.isStatus()
            );
            System.out.println("********* lstData ********" + lstData);
            if (lstData == null){
                res.setStatus(0);
                res.setMessage("don't have profile");
            } else {
                res.setStatus(1);
                res.setMessage("show list");
                res.setData(lstData);
            }
        } catch (Exception err){
            res.setStatus(-1);
            res.setMessage("err : " + err.toString());
        }
        return res;
    }

    @PostMapping("/list_all")
    public Object listAll(){
        APIResponse res = new APIResponse();
        try {
                res.setStatus(1);
                res.setMessage("show list");
                res.setData(profileRegisterRepository.findAll());
        } catch (Exception err){
            res.setStatus(-1);
            res.setMessage("err : " + err.toString());
        }
        return res;
    }

    @PostMapping("/update_address")
    public Object updateAddress(ProfileRegister profileRegister){
        APIResponse res = new APIResponse();
        try{
            Integer update = profileRegisterRepository.updateAddress(profileRegister.getProfileRegisterId(),profileRegister.getAddress());
            if(update==1){
                res.setStatus(1);
                res.setMessage("update address");
            }
        }catch (Exception err){
            res.setStatus(-1);
            res.setMessage("err : " + err.toString());
        }

        return res;
    }


}
