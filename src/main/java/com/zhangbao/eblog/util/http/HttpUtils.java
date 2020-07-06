package com.zhangbao.eblog.util.http;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class HttpUtils {
    private static Logger log = LoggerFactory.getLogger(HttpUtils.class);

    private final static int CONNECT_TIMEOUT = 5000; // in milliseconds
    private final static String DEFAULT_ENCODING = "UTF-8";

    public static String postData(String urlStr, String data) {
        return postData(urlStr, data, "application/x-www-form-urlencoded");
    }

    public static String postData(String urlStr, String data, String contentType) {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlStr);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(CONNECT_TIMEOUT);
                conn.setReadTimeout(CONNECT_TIMEOUT);
                if (contentType != null)
                    conn.setRequestProperty("content-type", contentType);
                OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), DEFAULT_ENCODING);
                if (data == null) {
                    writer.write("");
                } else {
                writer.write(data);
            }
            writer.flush();
            writer.close();

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), DEFAULT_ENCODING));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\r\n");
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getLocalizedMessage());
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                log.error(e.getLocalizedMessage());
            }
        }
        return null;
    }

    public static String postHttp(String url, Map<String, Object> map) {
        HttpClient httpclient = null;
        PostMethod post = null;
        SimpleHttpConnectionManager simpleHttpConnectionManager = null;
        String info = null;
        try {
            httpclient = new HttpClient();
            post = new PostMethod(url);
            // 设置编码方式
            post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
            httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
            httpclient.getHttpConnectionManager().getParams().setSoTimeout(30000);
            // 添加参数
            if (map != null) {
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    String key = entry.getKey();
                    post.addParameter(key, entry.getValue().toString());
                }
            }
            // 执行
            httpclient.executeMethod(post);
            // 接口返回信息
            info = new String(post.getResponseBody(), "UTF-8");
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            // 关闭连接，释放资源
            if (post != null) {
                post.releaseConnection();
            }
            if (httpclient != null) {
                simpleHttpConnectionManager = ((SimpleHttpConnectionManager) httpclient.getHttpConnectionManager());
                if (simpleHttpConnectionManager != null) {
                    simpleHttpConnectionManager.shutdown();
                }
            }

        }
        return info;
    }

    /**
     * 处理get请求
     *
     * @param url
     *            请求地址 如
     *            http://localhost:9090/base_rpc/basicData/getInvoice?t=1507513445960&invoiceId=039D906D07C74306B635DD89F87584CD&token=bd302857fbd4a01af7401fe229d43918
     * @return
     */
    public static String getHttp(String url) {
        HttpClient httpClient = null;
        GetMethod get = null;
        SimpleHttpConnectionManager simpleHttpConnectionManager = null;
        String info = null;

        try {
            httpClient = new HttpClient();
            get = new GetMethod(url);
            // 执行
            httpClient.executeMethod(get);
            // 接口返回信息
            info = new String(get.getResponseBody(), "UTF-8");
            log.info("接口【" + url + "】 \n返回:" + info);
        } catch (Exception e) {
            log.info("调用接口【" + url + "】  \n出错:" + e);
            return null;
        } finally {
            if (get != null) {
                get.releaseConnection();
            }
            if (httpClient != null) {
                simpleHttpConnectionManager = (SimpleHttpConnectionManager) httpClient.getHttpConnectionManager();
                if (simpleHttpConnectionManager != null) {
                    simpleHttpConnectionManager.shutdown();
                }
            }
        }
        return info;
    }


    /**
     * post请求(用于key-value格式的参数)
     * @param url
     * @param obj
     * @return
     */
    public static String doPost(String url, Object obj){
        Map params = new HashMap<String,Object>();
        BufferedReader in = null;
        try {
            params = objectToMap(obj);
            // 定义HttpClient
            DefaultHttpClient client = new DefaultHttpClient();
            // 实例化HTTP方法
            HttpPost request = new HttpPost();
            request.setURI(new URI(url));

            //设置参数
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            for (Iterator iter = params.keySet().iterator(); iter.hasNext();) {
                String name = (String) iter.next();
                String value = String.valueOf(params.get(name));
                nvps.add(new BasicNameValuePair(name, value));

                //System.out.println(name +"-"+value);
            }
            request.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

            HttpResponse response = client.execute(request);
            int code = response.getStatusLine().getStatusCode();
            if(code == 200){	//请求成功
                in = new BufferedReader(new InputStreamReader(response.getEntity()
                        .getContent(),"utf-8"));
                StringBuffer sb = new StringBuffer("");
                String line = "";
                String NL = System.getProperty("line.separator");
                while ((line = in.readLine()) != null) {
                    sb.append(line + NL);
                }

                in.close();

                return sb.toString();
            }
            else{	//
                System.out.println("状态码：" + code);
                return null;
            }
        }catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 利用反射获取类的属性和属性值
     * @param obj
     * @return
     * @throws IllegalAccessException
     */
    public static Map<String,Object> objectToMap(Object obj) throws IllegalAccessException {
        Map<String,Object> map = new HashMap<>();
        Class<?> clazz = obj.getClass();
        for (Field field: clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = field.get(obj);
            map.put(fieldName,value);
        }
        return map;
    }
}
