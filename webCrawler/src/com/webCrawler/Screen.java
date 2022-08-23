package com.webCrawler;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Screen extends Canvas {

	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;

	public static void main(String[] args) {
		JFrame frame = new JFrame("Web Crawler");
		frame.setMaximumSize(new Dimension(WIDTH * 2, HEIGHT * 2));
		frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		frame.setPreferredSize(new Dimension(WIDTH * 2, HEIGHT * 2));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
