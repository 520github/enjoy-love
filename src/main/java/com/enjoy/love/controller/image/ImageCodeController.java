package com.enjoy.love.controller.image;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.enjoy.love.common.image.code.ImageCode;
import com.enjoy.love.controller.BaseController;

@Controller
@RequestMapping("/image/code")
public class ImageCodeController extends BaseController {
	
	@RequestMapping("/getImageCode")
	public void getImageCode(HttpServletResponse response, HttpSession session) throws IOException {
		String randomsCode = ImageCode.getRandChar(4);
		BufferedImage bufferedImage = ImageCode.getBufferedImageByRandomsCode(randomsCode);
		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(bufferedImage, "jpg", out);
		out.flush();
		out.close();
	}
}
