package com.ltsh.app.chat.utils;


/**
 * 名字转换
 * 
 * @author fjz
 *
 */
public class Names {
	/**
	 * 从下划线法命名转换成为驼峰命名法
	 * @param stringWithUnderline
	 * @return
	 */
	public static String toCamelName(String stringWithUnderline) {
		return splitWordToCamel(stringWithUnderline,'_');
	}
	/**
	 * 从驼峰命名法转换成为下划线法命名
	 * @param camelName
	 * @return
	 */
	public static String toUnderlineName(String camelName) {
		return camelToSplitLowerWord(camelName,'_');
	}
	
	/**
	 * 将一个字符串由驼峰式命名变成分割符分隔单词
	 * 
	 * <pre>
	 *  lowerWord("helloWorld", '-') => "hello-world"
	 * </pre>
	 * 
	 * @param cs
	 *            字符串
	 * @param c
	 *            分隔符
	 * @return 转换后字符串
	 */
	public static String camelToSplitLowerWord(CharSequence cs, char c) {
		StringBuilder sb = new StringBuilder();
		int len = cs.length();
		for (int i = 0; i < len; i++) {
			char ch = cs.charAt(i);
			if (Character.isUpperCase(ch)) {
				if (i > 0)
					sb.append(c);
				sb.append(Character.toLowerCase(ch));
			} else {
				sb.append(ch);
			}
		}
		return sb.toString();
	}
	/**
     * 将一个字符串某一个字符后面的字母变成大写，比如
     * 
     * <pre>
     *  upperWord("hello-world", '-') => "helloWorld"
     * </pre>
     * 
     * @param cs
     *            字符串
     * @param c
     *            分隔符
     * 
     * @return 转换后字符串
     */
    public static String splitWordToCamel(CharSequence cs, char c) {
        StringBuilder sb = new StringBuilder();
        int len = cs.length();
        for (int i = 0; i < len; i++) {
            char ch = cs.charAt(i);
            if (ch == c) {
                do {
                    i++;
                    if (i >= len)
                        return sb.toString();
                    ch = cs.charAt(i);
                } while (ch == c);
                sb.append(Character.toUpperCase(ch));
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }
//	/**
//	 * 转换为驼峰标识
//	 * 
//	 * @param name
//	 * @return
//	 */
//	public static String getHumpName(String name, String split) {
//
//		return null;
//	}
//
//	/**
//	 * 转换为下划线的
//	 * 
//	 * @param name
//	 * @return
//	 */
//	public static String getUnderLineName(String name) {
//		String regexStr = "[A-Z]";
//		Matcher matcher = Pattern.compile(regexStr).matcher(name);
//		StringBuffer sb = new StringBuffer();
//		while (matcher.find()) {
//			String g = matcher.group();
//			matcher.appendReplacement(sb, "_" + g.toLowerCase());
//		}
//		matcher.appendTail(sb);
//		if (sb.charAt(0) == '_') {
//			sb.delete(0, 1);
//		}
//		return sb.toString();
//	}
//
	public static void main(String[] args) {
		System.out.println(Names.camelToSplitLowerWord("GeneratorMain", '-'));
		System.out.println(Names.camelToSplitLowerWord("GeneratEAMain", '-'));
		
		System.out.println(Names.toUnderlineName("GeneratEAMain"));
		System.out.println(Names.toCamelName("Ge_erat_aain"));
//		System.out.println(Names.camelToSplitLowerWord("GeneratEAMain", '-'));
//		System.out.println(getUnderLineName("Last_ogin_ip"));
//		System.out.println(getUnderLineName("LastLoginIp"));
	}

}
