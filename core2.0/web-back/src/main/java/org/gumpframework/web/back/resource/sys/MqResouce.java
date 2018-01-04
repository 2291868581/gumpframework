package org.gumpframework.web.back.resource.sys;


import org.gumpframework.web.base.resource.BaseResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/sysUser")
public class MqResouce extends BaseResource {



    @RequestMapping(value = "/",method = RequestMethod.GET)
    public List<Map<String,Object>> index(){

        return null;
    }

    @RequestMapping(value = "/save",method = RequestMethod.GET)
    public String  save(){
        return "成功";
    }

}
