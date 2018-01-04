package org.gumpframework.web.base.resource;

import org.gumpframework.web.base.config.GumpProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping(value = "${gump.adminPath}/file")
public class FileResource extends BaseResource {
    @Resource
    private GumpProperties gumpProperties;
    public void upload(@RequestParam("uploadFile")MultipartFile files, HttpServletResponse response){
        List<String> fileList = new LinkedList<>();
        String directory = gumpProperties.getAdminPicPath();

    }
}
