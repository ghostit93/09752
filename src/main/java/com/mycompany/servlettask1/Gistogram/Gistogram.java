/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.servlettask1.Gistogram;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Map;

/**
 *
 * @author Artur
 */
public class Gistogram {
    
    private BufferedImage bufferedImage;
    private Map<String,Integer> series;

    public Gistogram(Map<String, Integer> series) {
        this.series = series;
    }    
    
    public BufferedImage getBufferedImage(int width,int hight){
        BufferedImage bufferedImage=new BufferedImage(width,hight,BufferedImage.TYPE_INT_RGB);
        Graphics2D  graphics=bufferedImage.createGraphics();
        int sizeOfColumn=width/series.size();
        int max=0;
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, hight);
        graphics.setColor(Color.black);
        for (String y:series.keySet()){
            if (max<series.get(y))
                max=series.get(y);
        }
        double scaleY=(hight-100)/max;
        int x=0;
        for (String yName:series.keySet()){
            Long columnHight=Math.round(scaleY*(double)(series.get(yName)));
            int y=hight-Integer.parseInt(columnHight.toString());
            graphics.fillRect(x, y, sizeOfColumn, Integer.parseInt(String.valueOf(Math.round(series.get(yName)*scaleY))));
            graphics.drawChars(yName.toCharArray(), 0, yName.length(), x, y-10);
            graphics.setColor(Color.WHITE);
            if (series.get(yName)>0){
                graphics.drawChars(series.get(yName).toString().toCharArray(), 0, series.get(yName).toString().toCharArray().length, x, y+12);
            }
            graphics.setColor(Color.black);
            x+=sizeOfColumn;      
        }
        return bufferedImage;
    }
    
}
