package com.liaobao.Util;

import android.text.TextUtils;

public class StringUtil {
	public static String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			// 全角空格为12288
			if (c[i] == 12288) {
				// 半角空格为32
				c[i] = (char) 32;
				continue;
			}
			// 半角(33-126)与全角(65281-65374)的对应关系是：相差65248
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	public static String CN2EN(String input) {
		String[] regs = {
				"！", "，", "。", "；","？","：","“","”","‘","’","（ ","）","──","······",
				"!", ",", ".", ";" ,"?",":","\"","\"","'","'","( ",")","-","......",
		};
		for (int i = 0; i < regs.length / 2; i++) {
			input = input.replaceAll(regs[i], regs[i + regs.length / 2]);
		}

		return ToDBC(input);
	}
	public static boolean getIsNull(String text) {
		if (text == null) {
			return true;
		} else {
			if (text.equals("")) {
				return true;
			} else {
				return false;
			}
		}
	}
	/**
	 * <br>author:jinpneg</br>
	 * <br>Time：2017/7/23 10:41</br>
	 * 正则判断手机号
	 */
	public static boolean issPhoneNumberNO(String mobiles) {
		String telRegex = "[1][3578]\\d{9}";
		if (TextUtils.isEmpty(mobiles))
			return false;
		else
			return mobiles.matches(telRegex);
	}

}
