package cn.web.tools;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class imghelper
{
  public Boolean cutCenterImage(InputStream in, String dest, int w, int h, String formatname)
    throws IOException
  {
    Iterator<ImageReader> iterator = ImageIO.getImageReadersByFormatName(formatname);
    ImageReader reader = (ImageReader)iterator.next();
    ImageInputStream iis = ImageIO.createImageInputStream(in);
    reader.setInput(iis, true);
    ImageReadParam param = reader.getDefaultReadParam();
    int imageIndex = 0;
    Rectangle rect = new Rectangle((reader.getWidth(imageIndex) - w) / 2, (reader.getHeight(imageIndex) - h) / 2, w, h);
    param.setSourceRegion(rect);
    BufferedImage bi = reader.read(0, param);
    return Boolean.valueOf(ImageIO.write(bi, formatname, new File(dest)));
  }
}
