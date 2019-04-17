package com.qkl.common.util;

import com.qkl.common.bean.FileEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dengjihai
 * @create 2018-03-19
 **/
public class FileUtils {

    private static Logger logger= LoggerFactory.getLogger(FileUtils.class);

    static List<FileEntity> listname;

    /**
     * 读取指定目录下所有的文件
     * @param filepath
     * @return
     */
    public static List<FileEntity> readAllFile(String filepath) {
        File file= new File(filepath);
        FileEntity fileEntity=new FileEntity();
        if(!file.isDirectory()){
            logger.info("\n###fileName={}, filepath={}", file.getName(), filepath);
            fileEntity.setFileName(file.getName());
            fileEntity.setFilePath(filepath);
            listname.add(fileEntity);
        }else if(file.isDirectory()){
            String[] filelist=file.list();
            for(int i = 0;i<filelist.length;i++){
                File readfile = new File(filepath);
                if (!readfile.isDirectory()) {
                    logger.info("\n###fileName={}, filepath={}", readfile.getName(), filepath);
                    fileEntity.setFileName(readfile.getName());
                    fileEntity.setFilePath(filepath);
                    listname.add(fileEntity);
                } else if (readfile.isDirectory()) {
                    readAllFile(filepath + File.separator + filelist[i]);//递归
                }
            }
        }
        return listname;
    }

    /**
     *
     * @param path
     * @return
     * @throws Exception
     */
    public static List<String> getFileContent(String path) throws Exception {
        List<String> strList = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String readLine = null;
            while ((readLine = br.readLine()) != null) {
                strList.add(readLine);
            }
            br.close();
            return strList;
        } catch (IOException e) {
            throw new Exception("私钥数据读取错误");
        } catch (NullPointerException e) {
            throw new Exception("私钥输入流为空");
        }
    }


    // 判断文件是否存在
    public static void judeDirExists(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                logger.info("dir exists");
            } else {
                logger.info("the same name file exists, can not create dir");
            }
        } else {
            logger.info("dir not exists, create it ...");
            file.mkdir();
        }
    }


    public static List<String> readFileByLine(String filePath) throws FileNotFoundException {
        List<String> strList = new ArrayList<>();
        File file = new File(filePath);
        FileChannel fcin = new RandomAccessFile(file,"r").getChannel();
        ByteBuffer rBuffer = ByteBuffer.allocate(10);
        byte[] lineByte = new byte[0];
        try {
            //temp：由于是按固定字节读取，在一次读取中，第一行和最后一行经常是不完整的行，因此定义此变量来存储上次的最后一行和这次的第一行的内容，
            //并将之连接成完成的一行，否则会出现汉字被拆分成2个字节，并被提前转换成字符串而乱码的问题
            byte[] temp = new byte[0];
            while (fcin.read(rBuffer) != -1) {//fcin.read(rBuffer)：从文件管道读取内容到缓冲区(rBuffer)
                int rSize = rBuffer.position();//读取结束后的位置，相当于读取的长度
                byte[] bs = new byte[rSize];//用来存放读取的内容的数组
                rBuffer.rewind();//将position设回0,所以你可以重读Buffer中的所有数据,此处如果不设置,无法使用下面的get方法
                rBuffer.get(bs);//相当于rBuffer.get(bs,0,bs.length())：从position初始位置开始相对读,读bs.length个byte,并写入bs[0]到bs[bs.length-1]的区域
                rBuffer.clear();

                int startNum = 0;
                int LF = 10;//换行符
                int CR = 13;//回车符
                boolean hasLF = false;//是否有换行符
                for(int i = 0; i < rSize; i++){
                    if(bs[i] == LF){
                        hasLF = true;
                        int tempNum = temp.length;
                        int lineNum = i - startNum;
                        lineByte = new byte[tempNum + lineNum];//数组大小已经去掉换行符

                        System.arraycopy(temp, 0, lineByte, 0, tempNum);//填充了lineByte[0]~lineByte[tempNum-1]
                        temp = new byte[0];
                        System.arraycopy(bs, startNum, lineByte, tempNum, lineNum);//填充lineByte[tempNum]~lineByte[tempNum+lineNum-1]

                        String line = new String(lineByte, 0, lineByte.length);//一行完整的字符串(过滤了换行和回车)
                        strList.add(line);

                        //过滤回车符和换行符
                        if(i + 1 < rSize && bs[i + 1] == CR){
                            startNum = i + 2;
                        }else{
                            startNum = i + 1;
                        }
                    }
                }
                if(hasLF){
                    temp = new byte[bs.length - startNum];
                    System.arraycopy(bs, startNum, temp, 0, temp.length);
                }else{//兼容单次读取的内容不足一行的情况
                    byte[] toTemp = new byte[temp.length + bs.length];
                    System.arraycopy(temp, 0, toTemp, 0, temp.length);
                    System.arraycopy(bs, 0, toTemp, temp.length, bs.length);
                    temp = toTemp;
                }
            }
            if(temp != null && temp.length > 0){//兼容文件最后一行没有换行的情况
                String line = new String(temp, 0, temp.length);
                strList.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strList;
    }


    public static List<FileEntity> getListname() {
        return listname;
    }

    public static void setListname(List<FileEntity> listname) {
        FileUtils.listname = listname;
    }
}
