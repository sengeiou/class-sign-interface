package cn.fzn.classsign.common.service;

import cn.fzn.classsign.common.entity.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("user")
public interface UserFeignService {
    @RequestMapping(value = "getUidByPhone/{phone}", method = RequestMethod.GET)
    public Integer getUidByPhone(@PathVariable("phone") String phone);

    @RequestMapping(value = "addUser", method = RequestMethod.POST)
    public Boolean addUser(@RequestBody User user);

    @RequestMapping(value = "getUserByUid", method = RequestMethod.POST)
    public User getUserByUid(@RequestParam Integer uid);

    @RequestMapping(value = "updatePasswordByUid", method = RequestMethod.POST)
    public Boolean updatePasswordByUid(@RequestParam Integer uid,@RequestParam  String encodePassword);

    @RequestMapping(value = "updateUser", method = RequestMethod.POST)
    public Boolean updateUser(@RequestBody User user);

    @RequestMapping(value = "listStudentsByCid", method = RequestMethod.POST)
    public List<User> listStudentsByCid(@RequestBody List<Integer> cid);

    @RequestMapping(value = "getUserByPhone/", method = RequestMethod.POST)
    public User getUserByPhone(@RequestParam String phone);
}
