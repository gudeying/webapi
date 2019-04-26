package cn.web.util;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class CutImage
{
  public static void zoomImage(String src, String dest)
    throws Exception
  {
    int w = 230;
    int h = 230;
    double wr = 0.0D;double hr = 0.0D;
    File srcFile = new File(src);
    File destFile = new File(dest);
    
    BufferedImage bufImg = ImageIO.read(srcFile);
    Image Itemp = bufImg.getScaledInstance(w, h, 4);
    
    wr = w * 1.0D / bufImg.getWidth();
    hr = h * 1.0D / bufImg.getHeight();
    
    AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);
    Itemp = ato.filter(bufImg, null);
    try
    {
      ImageIO.write((BufferedImage)Itemp, dest.substring(dest.lastIndexOf(".") + 1), destFile);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }
}
