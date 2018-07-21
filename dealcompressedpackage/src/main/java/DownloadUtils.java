import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class DownloadUtils {
    /**
     * @param args
     */
    public static void main(String[] args) {

        /*String res = downloadFromUrl("https://sh-ctfs.ftn.qq.com/ftn_handler/923ee32fea01ce3e9c0736bc5b1ce392a81bc3924a8ca5e5adf9b954351d293a75730022a0c71b90eae965f5f4c6cf34779abbda6ec55961e1a0bb2135bab0e4/?fname=%E5%9B%BE%E7%89%87.zip&k=0f3732644ce6cc9d38bc40171563004d50075652045704031f005050014e060606561f5d0605064f0b54025c0157065354060b07336b32af8ef19e4a490a42620f&code=272d3c2b&fr=00&&txf_fid=1d6962e4f567989c932d9a48964567bb152235bc&xffz=96897", "E:/");
        System.out.println(res);*/
        File file = new File("temp");
        extract(new File("E:\\图片.zip"), file);
        File[] innerFiles = file.listFiles();
        System.out.println("包含文件个数：" + innerFiles.length);
        /*file.delete()方法删除目录时，需要目录为空*/
        System.out.println(file.delete());
        try {
            FileUtils.deleteDirectory(file);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("删除目录出错！");
        }
        System.out.println("包含文件个数：" + file.listFiles().length);
    }


    public static String downloadFromUrl(String url, String dir) {

        try {
            URL httpurl = new URL(url);
            String fileName = "haha.zip";
            System.out.println(fileName);
            File f = new File(dir + fileName);
            FileUtils.copyURLToFile(httpurl, f);
        } catch (Exception e) {
            e.printStackTrace();
            return "Fault!";
        }
        return "Successful!";
    }

    public static String getFileNameFromUrl(String url) {
        String name = new Long(System.currentTimeMillis()).toString() + ".X";
        int index = url.lastIndexOf("/");
        if (index > 0) {
            name = url.substring(index + 1);
            if (name.trim().length() > 0) {
                System.out.println("name0 : " + name);
                return name;
            }
        }
        System.out.println("name : " + name);
        return name;
    }

    public static void extract(File src, File dest){
        String path = src.getPath();
        String ext = path.substring(path.lastIndexOf('.') + 1).toLowerCase();

        Expander expander = null;

        if(ext.equals("tar")) {
            expander = new Expander("untar","untar");
        } else if(ext.equals("jar")) {
            expander = new Expander("unzip","unzip");
        } else if(ext.equals("zip")) {
            expander = new Expander("unzip","unzip");
        } else {
            // ...
        }

        expander.setSrc(src);
        expander.setDest(dest);
        expander.execute();
    }

}

