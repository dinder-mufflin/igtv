package com.igtv.pic.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.igtv.structures.Note;

public class PicUtil {

  /**
   * 
   * @param list
   */
  static public void drawTab(ArrayList list) {
    try {
      int width = list.size() * 50, height = 500, index = 50;

      BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

      Graphics2D ig2 = bi.createGraphics();

      Font font = new Font("TimesRoman", Font.BOLD, 20);
      ig2.setFont(font);

      ig2.setPaint(Color.black);

      printLines(ig2, width);

      for (int i = 0; i < list.size(); i++) {
        ArrayList<Note> secondList = (ArrayList<Note>) list.get(i);
        printSecondList(secondList, index, ig2);
        index += 50;
      }
      ImageIO.write(bi, "PNG", new File("test.png"));
    } catch (Exception ie) {
      ie.printStackTrace();
    }
  }

  private static void printLines(Graphics2D ig2, int width) {
    for (int i = 0; i < width; i++) {
      for (int j = 200; j < 500; j += 50) {
        ig2.drawString("-", i, j);
      }
    }
  }

  private static void printSecondList(ArrayList<Note> secondList, int index, Graphics2D ig2) {
    for (Note note : secondList) {
      if (note.getStringNo() < 0) {
        continue;
      }
      int height = getHeight(note.getStringNo());
      ig2.drawString(note.getFret() + "", index, height);
    }
  }

  private static int getHeight(int string) {
    if (string == 0)
      return 450;
    else if (string == 1)
      return 400;
    else if (string == 2)
      return 350;
    else if (string == 3)
      return 300;
    else if (string == 4)
      return 250;
    else if (string == 5)
      return 200;
    else
      return 0;
  }
}
