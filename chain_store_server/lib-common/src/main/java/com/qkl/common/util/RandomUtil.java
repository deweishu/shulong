package com.qkl.common.util;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * Created by 6212 on 2017/7/13.
 * 获取随机验证码字符串
 *
 */
public class RandomUtil {
	public enum CodeLevel {
		/**
		 * 简单，只包含数字
		 */
		Simple,
		/**
		 * 中等，包含数字、小写字母
		 */
		Medim,
		/**
		 * 困难，包含数字、大小写字母
		 */
		Hard,
		/**
		 * 超级困难，汉字
		 */
		SuperHard
	}

	private static final char[] c = { '2', '3', '4', '5', '6', '7', '8', '9',
			'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n', 'p',
			'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C',
			'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'P', 'Q',
			'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	private static final String str = "的一了是我不在人们有来他这上着个地到大里说就去子得也和那要下看天时过出小么起你都把好还多没为又可家学只以主会样年想生同老中十从自面前头道它后然走很像见两用她国动进成回什边作对开而己些现山民候经发工向事命给长水几义三声于高手知理眼志点心战二问但身方实吃做叫当住听革打呢真全才四已所敌之最光产情路分总条白话东席次亲如被花口放儿常气五第使写军吧文运再果怎定许快明行因别飞外树物活部门无往船望新带队先力完却站代员机更九您每风级跟笑啊孩万少直意夜比阶连车重便斗马哪化太指变社似士者干石满日决百原拿群究各六本思解立河村八难早论吗根共让相研今其书坐接应关信觉步反处记将千找争领或师结块跑谁草越字加脚紧爱等习阵怕月青半火法题建赶位唱海七女任件感准张团屋离色脸片科倒睛利世刚且由送切星导晚表够整认响雪流未场该并底深刻平伟忙提确近亮轻讲农古黑告界拉名呀土清阳照办史改历转画造嘴此治北必服雨穿内识验传业菜爬睡兴形量咱观苦体众通冲合破友度术饭公旁房极南枪读沙岁线野坚空收算至政城劳落钱特围弟胜教热展包歌类渐强数乡呼性音答哥际旧神座章帮啦受系令跳非何牛取入岸敢掉忽种装顶急林停息句区衣般报叶压慢叔背细";

	/**
	 * @author nie_jq
	 * @time Dec 31, 2014
	 * @param isCanRepeat
	 *            是否可重复
	 * @param level
	 *            验证码级别
	 * @param codLen
	 *            验证码长度
	 * @return String
	 * @desc 获取随机验证码字符串
	 */
	public static String getRandomCode(boolean isCanRepeat, CodeLevel level,
			int codLen) {
		char[] results = new char[codLen];
		char[] codes;
		Random r = new Random();
		if (level.equals(CodeLevel.Simple)) {
			// 简单，只包含数字
			codes = Arrays.copyOfRange(c, 0, 8);
		} else if (level.equals(CodeLevel.Medim)) {
			// 中等，数字，小写字母
			codes = Arrays.copyOfRange(c, 0, 31);
		} else if (level.equals(CodeLevel.Hard)) {
			codes = c;
		} else {
			String re = "";
			for (int i = 0; i < codLen; i++) {
				int start = r.nextInt(str.length());
				String key = str.substring(start, start + 1);
				re = re + key;
			}
			return re;
		}
		int len = codes.length;
		if (isCanRepeat) {
			for (int i = 0; i < codLen; i++) {
				int key = r.nextInt(len);
				results[i] = codes[key];
			}
		} else {
			for (int i = 0; i < codLen; i++) {
				int key = r.nextInt(len);
				results[i] = codes[key];
				codes[key] = codes[len - 1];
				len--;
			}
		}
		return String.valueOf(results);
	}

	/**
	 * @author nie_jq
	 * @time Dec 31, 2014
	 * @param length
	 *            长度
	 * @return String
	 * @desc 获取简单可重复验证码字符串(数字)
	 */
	public static String getSimpleRandomCode(int length) {
		return getRandomCode(true, CodeLevel.Simple, length);
	}
	/**
	 * @author nie_jq
	 * @time Dec 31, 2014
	 * @param length 长度
	 * @return String
	 * @desc 获取简单不重复验证码字符串(数字)
	 */
	public static String getSimpleRandomCodeNotRepeat(int length){
		return getRandomCode(false, CodeLevel.Simple, length);
	}

	/**
	 * @author nie_jq
	 * @time Dec 31, 2014
	 * @param length 长度
	 * @return String
	 * @desc 获取中等可重复验证码字符串(包含数字、小写字母)
	 */
	public static String getMedimRandomCode(int length) {
		return getRandomCode(true, CodeLevel.Medim, length);
	}

	/**
	 * @author nie_jq
	 * @time Dec 31, 2014
	 * @param length 长度
	 * @return String
	 * @desc 获取中等不重复验证码字符串(包含数字、小写字母)
	 */
	public static String getMedimRandomCodeNotRepeat(int length){
		return getRandomCode(false, CodeLevel.Medim, length);
	}

	/**
	 * @author nie_jq
	 * @time Dec 31, 2014
	 * @param length 长度
	 * @return String
	 * @desc 获取中等可重复验证码字符串(包含数字、大小写字母)
	 */
	public static String getHardRandomCode(int length) {
		return getRandomCode(true, CodeLevel.Hard, length);
	}

	/**
	 * @author nie_jq
	 * @time Dec 31, 2014
	 * @param length 长度
	 * @return String
	 * @desc 获取中等不重复验证码字符串(包含数字、大小写字母)
	 */
	public static String getHardRandomCodeNotRepeat(int length){
		return getRandomCode(false, CodeLevel.Hard, length);
	}

	/**
	 * @author nie_jq
	 * @time Dec 31, 2014
	 * @param
	 * @return String
	 * @desc 获取中文汉字验证码字符串
	 */
	public static String getCNRandomCode(int length) {
		return getRandomCode(true, CodeLevel.SuperHard, length);
	}

	/**
	 * @author nie_jq
	 * @time Dec 31, 2014
	 * @param level 困难级别
	 * @param length 字符串长度
	 * @return String
	 * @desc 获取随机字符串
	 */
	public static String getRandomCode(int level, int length) {
		switch (level) {
		case 1:
			return getSimpleRandomCode(length);
		case 2:
			return getMedimRandomCode(length);
		case 3:
			return getHardRandomCode(length);
		case 4:
			return getCNRandomCode(length);
		default:
			return null;
		}
	}

	/**
	 * @author nie_jq
	 * @time Dec 31, 2014
	 * @param level 困难级别
	 * @param length 字符串长度
	 * @return String
	 * @desc 获取随机字符串(不重复)
	 */
	public static String getRandomCodeNotRepeat(int level, int length) {
		switch (level) {
		case 1:
			return getSimpleRandomCodeNotRepeat(length);
		case 2:
			return getMedimRandomCodeNotRepeat(length);
		case 3:
			return getHardRandomCodeNotRepeat(length);
		case 4:
			return getCNRandomCode(length);
		default:
			return null;
		}
	}
}
