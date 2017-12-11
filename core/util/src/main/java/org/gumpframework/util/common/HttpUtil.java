package org.gumpframework.util.common;


import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
@Slf4j
public class HttpUtil {


    public static HttpEntity sendGetRequest(String url,Map<String,String> params)throws Exception{
        final StringBuffer tmp = new StringBuffer(url);
        if (PublicUtil.isNotEmpty(params)){
            Set<Map.Entry<String,String>> paramSet = params.entrySet();
            if (paramSet.size()>0){
                tmp.append("?");
                int totalLen = paramSet.size(),index=0;
                for (Map.Entry<String,String > entry:paramSet){
                    tmp.append(entry.getKey()+"="+entry.getValue());
                    if (++index<totalLen){
                        tmp.append("&");
                    }
                }
            }
            log.info("Get params:"+params.toString());
         }
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(tmp.toString());
        HttpResponse response = httpClient.execute(httpGet);
        return response.getEntity();
    }

    public static String sendGetRequestString(String url){
        try {
            HttpEntity entity = sendGetRequest(url,null);
            return entityToString(entity);
        }catch (Exception e){
            e.printStackTrace();
            log.error("GET请求异常:{}",e.getMessage());
        }
        return "";
    }

    public static String sendGetRequestString(String url, Map<String, String> params) {
        try {
            HttpEntity entity = sendGetRequest(url, params);
            return entityToString(entity);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("GET请求异常:" + e.getMessage());
        }
        return "";
    }
    public static String entityToString(HttpEntity entity)throws Exception{
        return entity==null?null: EntityUtils.toString(entity,"utf-8");
    }

    /**
     * 发送POST请求
     *
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static HttpEntity sendPostRequest(String url, Map<String, String> params) throws Exception {
        HttpClient httpClient = HttpClients.createDefault();
        List<NameValuePair> listParams = new ArrayList<NameValuePair>();
        if (params != null) {
            Set<Map.Entry<String, String>> set = params.entrySet();
            for (Map.Entry<String, String> entry : set) {
                listParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        log.info("Post params:" + listParams.toString());
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new UrlEncodedFormEntity(listParams, "utf-8"));
        HttpResponse reponse = httpClient.execute(httpPost);
        return reponse.getEntity();
    }

    public static String sendPostRequestString(String url, Map<String, String> params) {
        HttpEntity entity;
        try {
            entity = sendPostRequest(url, params);
            return entityToString(entity);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("POST请求异常:" + e.getMessage());
        }
        return "";
    }

    public static String sendPostRequestString(String url, String param) {
        HttpEntity entity;
        HttpClient httpClient = HttpClients.createDefault();
        try {
            log.info("Post params:" + param);
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new StringEntity(param, "utf-8"));
            HttpResponse reponse = httpClient.execute(httpPost);
            entity = reponse.getEntity();
            return entityToString(entity);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("POST请求异常:" + e.getMessage());
        }
        return "";
    }

    /**
     * 下载文件
     * @param path 文件URL
     * @param savePath 保存路径
     * @param fileName 文件名
     * @throws Exception
     */
    public  static void downloadFile(String path,String savePath,String fileName)throws Exception{
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(path);
        HttpResponse response = httpClient.execute(httpGet);
        File dir = new File(savePath);
        if (!dir.exists()) dir.mkdir();
        File saveFile = new File(dir,fileName);
        BufferedInputStream bis = new BufferedInputStream(response.getEntity().getContent());
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(saveFile));
        byte tmp[] = new byte[1024];
        int len = 0;
        while ((len=bis.read(tmp))!=-1){
            bos.write(tmp,0,len);
        }
        bis.close();
        bos.flush();
        bos.close();
    }
}
