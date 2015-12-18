package com.enjoy.love.common.image.code;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import org.apache.commons.lang.StringUtils;

public class ImageCode {
	
	/**
	 * 获取验证码内存图片
	 * @param randomsCode
	 * @return
	 */
	public static BufferedImage getBufferedImageByRandomsCode(String randomsCode) {
		int width = 60;
		int height =20;
		BufferedImage image = getBufferedImage(width, height);
		// 获取图形上下文
		Graphics g = image.getGraphics();
		// 设定背景色
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);
		// 设定字体
		g.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
		g.setColor(getRandColor(160, 200));
		cycleDrawLine(g, width, height, 115);
		cycleDrawRandomsCode(g, randomsCode);
		g.dispose();
		return image;
	}
	
	/**
	 * 绘画干扰线
	 * @param g
	 * @param width
	 * @param height
	 * @param cycleNum
	 */
	private static void cycleDrawLine(Graphics g, int width, int height, int cycleNum) {
		Random random = new Random();
		for(int i=0; i<cycleNum; i++) {
			int x1 = random.nextInt(width);
			int y1 = random.nextInt(height);
			int x2 = random.nextInt(12);
			int y2 = random.nextInt(12);
			drawLine(g,x1, y1, x2, y2);
		}
	}
	
	/**
	 * 绘画随机验证码
	 * @param g
	 * @param randomsCode
	 */
	private static void cycleDrawRandomsCode(Graphics g, String randomsCode) {
		String randomsCodeArray[] = getRandomsCode(randomsCode);
		Random random = new Random();
		for(int i=0; i<randomsCodeArray.length; i++) {
			String randomCode = randomsCodeArray[i];
			// 将认证码显示到图象中
			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
			g.drawString(randomCode, 13 * i + 6, 16);
		}
	}
	
	private static String[] getRandomsCode(String randomsCode) {
		if(StringUtils.isBlank(randomsCode)) {
			return null;
		}
		String randomsCodeArray[] = new String[randomsCode.length()];
		for(int i=0; i< randomsCode.length(); i++) {
			randomsCodeArray[i] = String.valueOf(randomsCode.charAt(i));
		}
		return randomsCodeArray;
	}
	
	private static void drawLine(Graphics g, int x1, int y1, int x2, int y2) {
		g.drawLine(x1, y1, x2, y2);
	}
	
	private static BufferedImage getBufferedImage(int width, int height) {
		return new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	}
	
	
	private static Color getRandColor(int fc, int bc) {
		// 给定范围获得随机颜色
		Random random = new Random();
		if (fc > 255) {
			fc = 255;
		}
		if (bc > 255) {
			bc = 255;
		}
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
	
	/**
	 * 获取指定长度的随机验证码
	 * @param length
	 * @return
	 */
	public static String getRandChar(int length) {
		String code = "";
		for (int i = 0; i < length; i++) {
			code += String.valueOf(getRandom());
		}
		return code;
	}
	
	private static char getRandom() {
		String value = "2345679ACDEFHJKLMNPRSTUVWXYZacdefhjklmnprstuvwxyz";
		int randomNumber = 0;
		randomNumber = (int) (Math.random() * value.length());
		return value.toCharArray()[randomNumber];
	}
}
