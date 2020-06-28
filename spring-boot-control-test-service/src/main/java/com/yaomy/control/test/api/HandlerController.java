package com.yaomy.control.test.api;

import com.sgrain.boot.common.exception.BusinessException;
import com.sgrain.boot.common.log.UserAction;
import com.sgrain.boot.common.po.ResponseData;
import com.sgrain.boot.common.utils.LoggerUtils;
import com.sgrain.boot.common.utils.json.JSONUtils;
import com.sgrain.boot.web.conf.properties.PropertyService;
import com.sgrain.boot.web.httpclient.HttpClientService;
import com.yaomy.control.test.po.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Description 测试类
 * @Date 2019/8/19 11:32
 * @Version  1.0
 */
@RequestMapping
@RestController
public class HandlerController {
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private HttpClientService httpClientService;

    @GetMapping(value = "/handler/test")
    public ResponseEntity<ResponseData> getName(){
        System.out.println(propertyService.getProperty("test"));
        LoggerUtils.info(HandlerController.class, "测试。。。");
        User user = new User();
        user.setAge(12);
        LoggerUtils.module("TRACE_NAME_MODULE", JSONUtils.toJSONString(user));

        return ResponseEntity.ok(ResponseData.buildResponse(10006, "自定义测试", user));
    }

    @PostMapping(value = "/handler/test2")
    public ResponseEntity<List> testNull1(@RequestBody @Validated User user){
        System.out.println("----------------3434334"+propertyService.getProperty("test.a"));
        UserAction userAction = new UserAction();
        userAction.setNumber("21");
        userAction.setUsername("name");
        LoggerUtils.module("user_info1", JSONUtils.toJSONString(userAction));
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("name", user.getName()+"12");
        map.put("age", user.getAge());
        list.add(map);
        return ResponseEntity.ok(list);
    }
    @RequestMapping(value = "/handler/upload")
    public String upload(String fileName, MultipartFile jarFile) {
        System.out.println(fileName+"==============");
        System.out.println(jarFile.getOriginalFilename());
        // 下面是测试代码
        String originalFilename = jarFile.getOriginalFilename();
        //System.out.println(originalFilename);
        try {
            String string = new String(jarFile.getBytes(), "UTF-8");
            System.out.println(string+"---------------");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "OK";
    }
    @RequestMapping(value = "/handler/test3")
    public void testNull13(@RequestBody @Validated User user){
        LoggerUtils.info(HandlerController.class, propertyService.getProperty("sms.code")+"---"+propertyService.getProperty("sms.message"));
        UserAction userAction = new UserAction();
        userAction.setNumber("12222");
        userAction.setUsername("hhhhhhh");
        System.out.println("----------------deee");
    }
    @RequestMapping(value = "/handler/url")
    public void testUrl(@RequestBody @Validated User user){
        String url = "https://peecapi.uufund.com/seal_label/list";
        Map<String, Object> params = new HashMap<>();
        params.put("token", "8b6deca08f174355a0da393fbf182a44");
      /*  params.put("age", 12);
        params.put("weight", Arrays.asList(12,34,66));*/
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("yaomy", "test");
        Map<String, Object> result = httpClientService.post(url, params, headers, Map.class);
        System.out.println(result);
    }
    @RequestMapping(value = "/handler/test4")
    public void testUrl1(@RequestBody @Validated User user) throws IOException{
        String url = "http://172.30.67.122:9000/handler/upload";
        FileSystemResource resource = new FileSystemResource(new File("D:\\work\\ssr\\pac.txt"));
        FileSystemResource resource1 = new FileSystemResource(new File("D:\\work\\ssr\\gui-config.json"));
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.put("jarFile", Arrays.asList(resource, resource1));
        params.put("fileName", Arrays.asList("liming", "hello"));

    }
    @PostMapping(value = "/handler/client")
    public ResponseEntity<String> testClient(String name) {
        System.out.println("------PARAM--------"+name);
      return ResponseEntity.ok(name);
    }


    @RequestMapping(value = "/handler/client2")
    public ResponseEntity<String> testClient2(String name, Integer age, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("------PARAM--------"+name);
        return ResponseEntity.ok(name);
    }
    @RequestMapping(value = "/handler/client3")
    public User testClient3(@RequestBody User user) throws Exception{
        /*System.out.println("------PARAM--------"+user.getName());
        user.setName("2");
        user.setWeight(new String[]{"2","2"});
        System.out.println(ObjectSizeUtil.getObjectSizeUnit(user));*/
        String url = "http://172.30.67.122:9000/handler/client4?n=300";
        String s = httpClientService.post(url, null, null, String.class);
        user.setName(s);
        return user;
    }

    @RequestMapping(value = "/handler/client4")
    public String testClient4(Long n) {
        String s = "";
        for(int i=0;i<n;i++){
            s = StringUtils.join(s, i);
            System.out.println(s);
        }
        return s;
    }
    @RequestMapping(value = "/handler/client5")
    public ResponseEntity<String> testClient5(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        String age = request.getParameter("age");
        System.out.println("------PARAM--------"+name+"--"+age);
        return ResponseEntity.ok(name);
    }
    @GetMapping(value = "/handler/client6")
    public ResponseData testClient6(HttpServletRequest request, HttpServletResponse response) {
        String name = "";
        for(int i=0; i<1000;i++){
            name = StringUtils.join(name, UUID.randomUUID());
        }
        System.out.println(name.getBytes().length+"--------------------------------------------------------");
        return  new ResponseData.Builder<>().setStatus(1).setMessage("saf").setData("hellowd").builder();
    }


}
