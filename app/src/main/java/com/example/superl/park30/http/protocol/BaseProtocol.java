package com.example.superl.park30.http.protocol;

import com.example.superl.park30.http.HttpHelper;
import com.example.superl.park30.util.IOUtils;
import com.example.superl.park30.util.StringUtils;
import com.example.superl.park30.util.UIUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by SuperL on 2018/8/18.
 */

public abstract class BaseProtocol<T> {

    //获取数据
    public T getData(){
        //先判断是否有缓存， 有的话先在缓存中获取，否则直接请求网络获取
        String result = getCatch();
        if (StringUtils.isEmpty(result)){   //如果没有缓存或者缓存失效， 请求网络
            result = getDateFromServer();
            if (!StringUtils.isEmpty(result)){
                //网络请求数据成功，将数据设入缓存
                setCatch(result);
            }else {
                //网络请求失败

            }
        }
        if (result != null){
            //解析数据
            T data = parseData();
            return data;
        }
        return null;
    }
    //抽象方法由子类来实现
    public abstract T parseData();
    public abstract String getKey();
    public abstract String getParams();
    //从网络中获取数据
    private String getDateFromServer(){
        HttpHelper.HttpResult httpResult = HttpHelper.get(HttpHelper.URL+getKey()+"?"+getParams());
        if (httpResult != null){
           String result = httpResult.getString();
           return result;
        }
        return null;
    }

    private String getCatch(){
        //本地缓存文件夹
        File cacheDir = UIUtils.getContext().getCacheDir();
        //生成的文件夹
        File cacheFile = new File(cacheDir, getKey()+"?"+getParams());
        //如果缓存文件存在
        if (cacheFile.exists()){
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(cacheFile));
                String str = br.readLine();
                long deadline = Long.parseLong(str);
                if (System.currentTimeMillis() < deadline) { //如果文件还没有过有效期
                    StringBuffer sb = new StringBuffer();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }
                    return sb.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                IOUtils.close(br);
            }
        }
        return null;
    }
    //写缓存
    //以url为key（文件名）， 以json为value（数据）来创建一个文件来保存缓存数据
    private void setCatch(String json){
        //本地应用的缓存文件夹
        File cacheDir = UIUtils.getContext().getCacheDir();
        //生成的文件夹
        File cacheFile = new File(cacheDir,getKey()+"?"+getParams());

        FileWriter writer = null;
        try {
            writer = new FileWriter(cacheFile);//获取文件输出流
            //写入一个字符串代表文件有效期
            long deadline = System.currentTimeMillis() + (30 * 60 * 1000);
            writer.write(deadline+ "\n");//在文件第一行写入文件戒指日期
            //写入json
            writer.write(json);
            writer.flush(); //flush将在缓冲区的数据写入文件中

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IOUtils.close(writer);
        }
    }
}
