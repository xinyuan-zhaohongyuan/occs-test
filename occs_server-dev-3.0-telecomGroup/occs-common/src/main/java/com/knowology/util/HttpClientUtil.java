package com.knowology.util;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {
    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     * 发送GET请求
     */
    public static String getRequest(String get_url, Map<String, String> params, int conn_timeout, int read_timeout) {
        String rps = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            // 创建参数队列
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            for (String key : params.keySet()) {
                formparams.add(new BasicNameValuePair(key, params.get(key)));
            }


            URIBuilder builder = new URIBuilder(get_url);
            builder.setParameters(formparams).setCharset(Charset.defaultCharset());
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(conn_timeout)
                    .setSocketTimeout(read_timeout).build();
            HttpGet httpGet = new HttpGet(builder.build());
            httpGet.setConfig(requestConfig);
            httpGet.setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));
            logger.info("Executing get request url:" + httpGet.getURI());
            // 执行get请求.
            CloseableHttpResponse response = httpclient.execute(httpGet);
            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    rps = EntityUtils.toString(entity, "UTF-8");
                    logger.info("Response content: " + rps);
                }
            } finally {
                response.close();
            }
        } catch (URISyntaxException e) {
            logger.error("Httpclient get request failed, the reason is:" ,e);
        } catch (IOException e) {
            logger.error("Httpclient get request failed, the reason is:" ,e);
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                logger.error("Something has gone wrong when closing httpclient:" ,e);
            }
        }
        return rps;
    }


    /**
     * 发送post请求
     */
    public static String postRequest(String post_url, Map<String, Object> params, int conn_timeout, int socket_timeout) {
        String rps = null;//响应结果
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建httppost
        HttpPost httppost = new HttpPost(post_url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(conn_timeout)
                .setSocketTimeout(socket_timeout).build();
        httppost.setConfig(requestConfig);
        // 创建参数队列
        List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
        for (String key : params.keySet()) {
            formparams.add(new BasicNameValuePair(key, params.get(key).toString()));
        }
        UrlEncodedFormEntity uefEntity = null;
        try {
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
            httppost.setEntity(uefEntity);
            httppost.setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));
            logger.debug("Executing post request " + httppost.getURI());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    rps = EntityUtils.toString(entity, "UTF-8");
                    logger.info("Response content: " + rps);
                }
            } finally {
                response.close();
            }
        } catch (IOException e) {
            logger.error("Httpclient post request failed, the reason is:" ,e);
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                logger.error("Something has gone wrong when closing httpclient:" ,e);
            }
        }
        return rps;
    }
}
