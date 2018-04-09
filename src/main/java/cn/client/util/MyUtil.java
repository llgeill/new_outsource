package cn.client.util;

import cn.client.pojo.*;
import cn.client.ui.admin.CompanyTaskUI;
import cn.client.ui.file.FileProgressController;
import com.alibaba.fastjson.JSON;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.stage.StageStyle;
import org.bytedeco.javacpp.opencv_videoio;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.*;

public class MyUtil {
    private static LoginVo loginVo=new LoginVo();
    private static AttendanceUserVo attendanceUserVo=null;
    private static CompanyTaskUI companyTaskUI=null;
    private static Properties properties=new Properties();
    static {
        try {
            properties.load(MyUtil.class.getResourceAsStream("/client.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static <T> T parseJSON(String json,Class<T> thisClass){
        if(json.isEmpty()||json.equals(""))return null;
        T t=JSON.parseObject(json,thisClass);
        return t;
    }

    public static <T> List<T> parseJSONArray(String json, Class<T> thisClass){
        if(json.isEmpty()||json.equals(""))return null;
        List<T> t=JSON.parseArray(json,thisClass);
        return t;
    }

    public static String toJSON(Object object){
        if(object==null)return "";
        return JSON.toJSONString(object);
    }

    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    //发送JSON字符串 如果成功则返回成功标识。
    public static String doJsonPost(String urlPath, String Json) {
        String path=properties.getProperty("server.url")+urlPath;
        String result = "";
        Scanner reader = null;
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            // 设置文件类型:
            conn.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            // 设置接收类型否则返回415错误
            //conn.setRequestProperty("accept","*/*")此处为暴力方法设置接受所有类型，以此来防范返回415;
            conn.setRequestProperty("accept","application/json");
            // 往服务器里面发送数据
            if (Json != null && !Json.equals("")) {
                byte[] writebytes = Json.getBytes("utf-8");
                // 设置文件长度
                conn.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
                OutputStreamWriter outwritestream = new OutputStreamWriter(conn
                        .getOutputStream(), "UTF-8");
                outwritestream.write(Json);
                outwritestream.flush();
                outwritestream.close();
            }
            //读取数据
            if (conn.getResponseCode() == 200) {
                reader = new Scanner(new InputStreamReader(conn.getInputStream(),"utf-8"));
                if(reader.hasNext()) result = reader.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return result;
    }





    /**
     * urlPath 服务器地址
     * fileName 文件名(带后缀)
     * file 文件
     */
    public static void uploadFile(String urlPath,String fileName,File files) {

        try {
            if(files.length()>new FileInputStream(files).available()){
                AlertProxy.showErrorAlert("文件大小超过限制!");
            }else{
                Dialog dialog=new Dialog();
                FXMLLoader loader=new FXMLLoader();
                loader.setLocation(MyUtil.class.getClass().getResource("/static/fxml/file/Progress.fxml"));
                DialogPane dialogPane = loader.load();
                FileProgressController controller=loader.getController();
                ProgressBar progressBar=controller.getFileProgress();
                AnchorPane anchorPane=controller.getTempPane();
                dialog.setDialogPane(dialogPane);
                dialog.initStyle(StageStyle.TRANSPARENT);
                dialog.show();
                Service<Integer> service = new Service<Integer>() {
                    @Override
                    protected Task<Integer> createTask() {
                        return new Task<Integer>() {
                            @Override
                            protected Integer call() throws Exception {
                                String path=properties.getProperty("server.url")+urlPath;
                                HttpURLConnection conn = null;
                                /// boundary就是request头和上传文件内容的分隔符(可自定义任意一组字符串)
                                String BOUNDARY = "---------------------------123821742118716";
                                // 用来标识payLoad+文件流的起始位置和终止位置(相当于一个协议,告诉你从哪开始,从哪结束)
                                String  preFix = ("\r\n--" + BOUNDARY + "\r\n");
                                String hostFix = ("\r\n--" + BOUNDARY +"--"+ "\r\n");
                                try {
                                    URL url = new URL(path);
                                    conn = (HttpURLConnection) url.openConnection();
                                    conn.setConnectTimeout(2000);
                                    conn.setReadTimeout(30000);
                                    conn.setDoOutput(true);
                                    conn.setDoInput(true);
                                    conn.setUseCaches(false);
                                    // 设置请求方法
                                    conn.setRequestMethod("POST");
                                    // 设置header
                                    conn.setRequestProperty("Accept","*/*");
                                    conn.setRequestProperty("Connection", "keep-alive");
                                    conn.setRequestProperty("Content-Type",
                                            "multipart/form-data; boundary=" + BOUNDARY);
                                    conn.setRequestProperty("RANGE","bytes=0");
                                    // 获取写输入流
                                    OutputStream out = conn.getOutputStream();
                                    // 获取上传文件
                                    File file = files;
                                    // 要上传的数据
                                    StringBuffer strBuf = new StringBuffer();
                                    // 标识payLoad + 文件流的起始位置
                                    strBuf.append(preFix);
                                    // 下面这三行代码,用来标识服务器表单接收文件的name和filename的格式
                                    // 在这里,我们是file和filename.后缀[后缀是必须的]。
                                    // 这里的fileName必须加个.jpg,因为后台会判断这个东西。
                                    // 这里的Content-Type的类型,必须与fileName的后缀一致。
                                    // 如果不太明白,可以问一下后台同事,反正这里的name和fileName得与后台协定！
                                    // 这里只要把.jpg改成.txt，把Content-Type改成上传文本的类型，就能上传txt文件了。
                                    strBuf.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + fileName + "\"\r\n");
                                    if(fileName.endsWith("jpg")){
                                        strBuf.append("Content-Type: image/jpeg"  + "\r\n\r\n");
                                    }else if(fileName.endsWith("png")){
                                        strBuf.append("Content-Type: application/x-png"  + "\r\n\r\n");
                                    }else{
                                        strBuf.append("Content-Type: application/octet-stream"  + "\r\n\r\n");
                                    }
                                    out.write(strBuf.toString().getBytes());
                                    // 获取文件流
                                    FileInputStream fileInputStream = new FileInputStream(file);
                                    DataInputStream inputStream = new DataInputStream(fileInputStream);
                                    // 每次上传文件的大小(文件会被拆成几份上传)
                                    int bytes = 0;
                                    // 计算上传进度
                                    float count = 0;
                                    // 获取文件总大小
                                    int fileSize = fileInputStream.available();
                                    // 每次上传的大小
                                    byte[] bufferOut = new byte[1024];
                                    // 上传文件
                                    while ((bytes = inputStream.read(bufferOut)) != -1) {
                                        out.write(bufferOut);
                                        count += bytes;
                                        System.out.println("progress:" +(count / fileSize * 100) +"%");
                                        updateProgress(count, fileSize);
                                    }
                                    out.write(hostFix.getBytes());
                                    inputStream.close();
                                    if (conn.getResponseCode() == 200) {
                                        Scanner reader = new Scanner(new InputStreamReader(conn.getInputStream()));
                                        String result = reader.next();
                                        reader.close();
                                    }
                                    out.flush();
                                    out.close();
                                } catch (Exception e) {
                                    //utils.logD("上传图片出错:"+e.toString());
                                } finally {
                                    if (conn != null) {
                                        conn.disconnect();
                                    }

                                }
                                return null;
                            };
                        };
                    }

                };
                progressBar.progressProperty().bind(service.progressProperty());
                service.restart();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * urlPath 服务器地址
     * fileName 文件名(带后缀)
     * file 文件
     */
    public static void uploadFiles(String urlPath,String fileName,File files) {
        String path=properties.getProperty("server.url")+urlPath;
        HttpURLConnection conn = null;
        /// boundary就是request头和上传文件内容的分隔符(可自定义任意一组字符串)
        String BOUNDARY = "---------------------------123821742118716";
        // 用来标识payLoad+文件流的起始位置和终止位置(相当于一个协议,告诉你从哪开始,从哪结束)
        String  preFix = ("\r\n--" + BOUNDARY + "\r\n");
        String hostFix = ("\r\n--" + BOUNDARY +"--"+ "\r\n");
        try {
            URL url = new URL(path);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(2000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方法
            conn.setRequestMethod("POST");
            // 设置header
            conn.setRequestProperty("Accept","*/*");
            conn.setRequestProperty("Connection", "keep-alive");
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + BOUNDARY);
            conn.setRequestProperty("RANGE","bytes=0");
            // 获取写输入流
            OutputStream out = conn.getOutputStream();
            // 获取上传文件
            File file = files;
            // 要上传的数据
            StringBuffer strBuf = new StringBuffer();
            // 标识payLoad + 文件流的起始位置
            strBuf.append(preFix);
            // 下面这三行代码,用来标识服务器表单接收文件的name和filename的格式
            // 在这里,我们是file和filename.后缀[后缀是必须的]。
            // 这里的fileName必须加个.jpg,因为后台会判断这个东西。
            // 这里的Content-Type的类型,必须与fileName的后缀一致。
            // 如果不太明白,可以问一下后台同事,反正这里的name和fileName得与后台协定！
            // 这里只要把.jpg改成.txt，把Content-Type改成上传文本的类型，就能上传txt文件了。
            strBuf.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + fileName + "\"\r\n");
            if(fileName.endsWith("jpg")){
                strBuf.append("Content-Type: image/jpeg"  + "\r\n\r\n");
            }else if(fileName.endsWith("png")){
                strBuf.append("Content-Type: application/x-png"  + "\r\n\r\n");
            }else{
                strBuf.append("Content-Type: application/octet-stream"  + "\r\n\r\n");
            }
            out.write(strBuf.toString().getBytes());
            // 获取文件流
            FileInputStream fileInputStream = new FileInputStream(file);
            DataInputStream inputStream = new DataInputStream(fileInputStream);
            // 每次上传文件的大小(文件会被拆成几份上传)
            int bytes = 0;
            // 计算上传进度
            float count = 0;
            // 获取文件总大小
            int fileSize = fileInputStream.available();
            // 每次上传的大小
            byte[] bufferOut = new byte[1024];
            // 上传文件
            while ((bytes = inputStream.read(bufferOut)) != -1) {
                out.write(bufferOut);
                count += bytes;
                System.out.println("progress:" +(count / fileSize * 100) +"%");
            }
            out.write(hostFix.getBytes());
            inputStream.close();
            if (conn.getResponseCode() == 200) {
                Scanner reader = new Scanner(new InputStreamReader(conn.getInputStream()));
                String result = reader.next();
                reader.close();
            }
            out.flush();
            out.close();
        } catch (Exception e) {
            //utils.logD("上传图片出错:"+e.toString());
        } finally {
            if (conn != null) {
                conn.disconnect();
            }

        }


    }

    /**
     * 从网络Url中下载文件
     * @param urlStr
     * @param fileName
     * @param savePath
     * @throws IOException
     */
    public static void  downLoadFromUrl(String urlStr,String fileName,String savePath) throws IOException{

        try {

                Dialog dialog=new Dialog();
                FXMLLoader loader=new FXMLLoader();
                loader.setLocation(MyUtil.class.getClass().getResource("/static/fxml/file/Progress.fxml"));
                DialogPane dialogPane = loader.load();
                FileProgressController controller=loader.getController();
                ProgressBar progressBar=controller.getFileProgress();
                AnchorPane anchorPane=controller.getTempPane();
                dialog.setDialogPane(dialogPane);
                dialog.initStyle(StageStyle.TRANSPARENT);
                dialog.show();
                Service<Integer> service = new Service<Integer>() {
                    @Override
                    protected Task<Integer> createTask() {
                        return new Task<Integer>() {
                            @Override
                            protected Integer call() throws Exception {
                                URL url = new URL(urlStr);
                                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                                //设置超时间为3秒
                                conn.setConnectTimeout(3*1000);
                                //防止屏蔽程序抓取而返回403错误
                                conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
                                //得到输入流
                                InputStream inputStream = conn.getInputStream();
                                //获取自己数组
                               // byte[] getData = readInputStream(inputStream);
                                byte[] buffer = new byte[1024];
                                int len = 0;
                                int count=0;
                                int fileSize=conn.getContentLength();
                                FileOutputStream fos = new FileOutputStream(savePath);
                                while((len = inputStream.read(buffer)) != -1) {
                                    fos.write(buffer);
                                    count+=len;
                                    System.out.println("progress:" +(count / fileSize * 100) +"%");
                                    updateProgress(count, fileSize);
                                }
                                fos.close();
                                inputStream.close();
                                conn.disconnect();
                                return null;
                            };
                        };
                    }

                };
                progressBar.progressProperty().bind(service.progressProperty());
                service.restart();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }


    public static void  downLoadFromUrlSmall(String urlStr,String savePath) throws IOException{
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(3*1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        //得到输入流
        InputStream inputStream = conn.getInputStream();
        //获取自己数组
        byte[] getData = readInputStream(inputStream);


        File file = new File(savePath);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if(fos!=null){
            fos.close();
        }
        if(inputStream!=null){
            inputStream.close();
        }

        System.out.println("info:"+url+" download success");
    }

    /**
     * 从输入流中获取字节数组
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static  byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }




    public static MyCode isOpenCamera(){
        opencv_videoio.VideoCapture capture = new opencv_videoio.VideoCapture(0);
        if(!capture.isOpened()){
            capture.release();
            return new MyCode(false,"摄像头没有开启,无法进行人脸识别！！");
        }
        capture.release();
        return new MyCode(true,"");
    }

    public static void endAttendance(int status){
        if(MyUtil.getAttendanceUserVo()!=null){
            Attendance attendance=MyUtil.getAttendanceUserVo().getAttendance();
            attendance.setStatus(status);
            attendance.setEndTime(new Timestamp(new Date().getTime()));
            MyUtil.getAttendanceUserVo().setAttendance(attendance);
            String json=MyUtil.doJsonPost("/updateAttendance",MyUtil.toJSON((MyUtil.getAttendanceUserVo())));
        }
    }

    public static LoginVo getLoginVo() {
        return loginVo;
    }

    public static void setLoginVo(LoginVo loginVo) {
        MyUtil.loginVo = loginVo;
    }

    public static AttendanceUserVo getAttendanceUserVo() {
        return attendanceUserVo;
    }

    public static void setAttendanceUserVo(AttendanceUserVo attendanceUserVo) {
        MyUtil.attendanceUserVo = attendanceUserVo;
    }

    public static CompanyTaskUI getCompanyTaskUI() {
        return companyTaskUI;
    }

    public static void setCompanyTaskUI(CompanyTaskUI companyTaskUI) {
        MyUtil.companyTaskUI = companyTaskUI;
    }

    public static Properties getProperties() {
        return properties;
    }

    public static void setProperties(Properties properties) {
        MyUtil.properties = properties;
    }
}
