package utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.swetake.util.Qrcode;

import play.Play;

public class QRCode {
	public static final String UPLOAD_ROOT_DIR = "public";
	
	public static final String folder_con = "control";
	public static final String folder_sen ="sensor";
	public static final String folder_radio ="radiotube";
	public static final String folder_liquid ="liquid";
	public static final String folder_wifi = "wifi";
	public static final String folder_mosq="mosq";
	//二维码颜色  
    private static final int BLACK = 0xFF000000;  
    //二维码颜色  
    private static final int WHITE = 0xFFFFFFFF;  
	public static boolean zxingCodeCreate(String text, int width, int height, String outPutPath, String imageType){ 
		Map<EncodeHintType, String> his = new HashMap<EncodeHintType, String>();
		his.put(EncodeHintType.CHARACTER_SET, "utf-8");  
		
		try {
			BitMatrix encode = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, his);
			//2、获取二维码宽高  
            int codeWidth = encode.getWidth();  
            int codeHeight = encode.getHeight();  
           //3、将二维码放入缓冲流  
            BufferedImage image = new BufferedImage(codeWidth, codeHeight, BufferedImage.TYPE_INT_RGB);  
            for (int i = 0; i < codeWidth; i++) {  
                for (int j = 0; j < codeHeight; j++) {  
                    //4、循环将二维码内容定入图片  
                    image.setRGB(i, j, encode.get(i, j) ? BLACK : WHITE);  
                }  
            }  
            File outPutImage = new File(outPutPath);  
          //如果图片不存在创建图片  
            if(!outPutImage.exists())  
              outPutImage.createNewFile();  
             //5、将二维码写入图片  
             ImageIO.write(image, imageType, outPutImage);  
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {  
            e.printStackTrace();  
            System.out.println("生成二维码图片失败");  
        }  
		return true;
	}
	public static boolean create(String savePath,String qrData) throws IOException{
		 Qrcode qrcode = new Qrcode();  
		 qrcode.setQrcodeErrorCorrect('M');//纠错等级（分为L、M、H三个等级）  
	     qrcode.setQrcodeEncodeMode('B');//N代表数字，A代表a-Z，B代表其它字符  
	     qrcode.setQrcodeVersion(7);//版本  
	     //生成二维码中要存储的信息  
	     //设置一下二维码的像素  
	     int width = 500;  
	     int height = 500;  
	     BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
	     //绘图  
	     Graphics2D gs = bufferedImage.createGraphics();  
	     gs.setBackground(Color.WHITE);  
	     gs.setColor(Color.BLACK);  
	     gs.clearRect(0, 0, width, height);//清除下画板内容  
	     //设置下偏移量,如果不加偏移量，有时会导致出错。  
	     int pixoff = 2;  
	     byte[] d = qrData.getBytes("gb2312");  
	     if(d.length > 0 && d.length <120){  
	           boolean[][] s = qrcode.calQrcode(d);  
	           for(int i=0;i<s.length;i++){  
	               for(int j=0;j<s.length;j++){  
	                   if(s[j][i]){  
	                       gs.fillRect(j*3+pixoff, i*3+pixoff, 3, 3);  
	                   }  
	               }  
	           }  
	       }  
	       gs.dispose();  
	       bufferedImage.flush();  
	       ImageIO.write(bufferedImage, "png", new File(savePath));  
	       return true;
	}
	public static String getUploadPath(String type) {
		Calendar ca = Calendar.getInstance();
		int year = ca.get(Calendar.YEAR);
		int month = ca.get(Calendar.MONTH) + 1;
		int day = ca.get(Calendar.DAY_OF_MONTH);
		String savePath  = "";
		if("A".equals(type)){
			savePath = Play.roots.get(0).child(UPLOAD_ROOT_DIR)
					.child("tmp").child(folder_con).getRealFile().getAbsolutePath()
					+ File.separator
					+ year
					+ File.separator
					+ month
					+ File.separator + day + File.separator;
		}
		else if("B".equals(type)){
			savePath = Play.roots.get(0).child(UPLOAD_ROOT_DIR)
					.child("tmp").child(folder_sen).getRealFile().getAbsolutePath()
					+ File.separator
					+ year
					+ File.separator
					+ month
					+ File.separator + day + File.separator;
		}
		else if("C".equals(type)){
			savePath = Play.roots.get(0).child(UPLOAD_ROOT_DIR)
					.child("tmp").child(folder_liquid).getRealFile().getAbsolutePath()
					+ File.separator
					+ year
					+ File.separator
					+ month
					+ File.separator + day + File.separator;
		}
		else if("D".equals(type)){
			savePath = Play.roots.get(0).child(UPLOAD_ROOT_DIR)
					.child("tmp").child(folder_radio).getRealFile().getAbsolutePath()
					+ File.separator
					+ year
					+ File.separator
					+ month
					+ File.separator + day + File.separator;
		}
		else if("E".equals(type)){
			savePath = Play.roots.get(0).child(UPLOAD_ROOT_DIR)
					.child("tmp").child(folder_mosq).getRealFile().getAbsolutePath()
					+ File.separator
					+ year
					+ File.separator
					+ month
					+ File.separator + day + File.separator;
		}
		else if("F".equals(type)){
			savePath = Play.roots.get(0).child(UPLOAD_ROOT_DIR)
					.child("tmp").child(folder_wifi).getRealFile().getAbsolutePath()
					+ File.separator
					+ year
					+ File.separator
					+ month
					+ File.separator + day + File.separator;
		}
		
		File saveDir = new File(savePath);
		if (!saveDir.exists())
			saveDir.mkdirs();
		return savePath;
	}
}
