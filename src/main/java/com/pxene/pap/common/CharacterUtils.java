package com.pxene.pap.common;

/**
 * 字符串处理类
 * Created by wangshuai on 2017/7/12.
 */
public class CharacterUtils {
    /**
     * 判断是否有乱码,有乱码返回true
     * @param code
     * @return
     */
    public static boolean isMessyCode(String code){
        int len = code.length();
        for (int i = 0; i < len; i++) {
            char codePoint = code.charAt(i);
//            System.out.println(codePoint + "--" + Integer.toHexString(codePoint));
            // 当从Unicode编码向某个字符集转换时，如果在该字符集中没有对应的编码，则得到0x3f（即问号字符?）
            //从其他字符集向Unicode编码转换时，如果这个二进制数在该字符集中没有标识任何的字符，则得到的结果是0xfffd
            if((int)codePoint == 0xfffd){
                return true;
            }
        }
        return false;
    }

    /**
     * 是否是标点符号，如果是，返回true
     * @param codePoint
     * @return
     */
    public static boolean isSymbols(char codePoint){
        String symbols = "^[@$!#$%&\\()~-\\+\\[\\]\\;'/.=_\\<\\>?\\{}\\',，。 *\\`|:]+$·【】\"“”";

        for (int j = 0; j < symbols.length(); j++) {
            if (symbols.charAt(j) == codePoint ) {
                return true ;
            }
        }
        return false;

    }

    /**
     * 判断字符串是否含有非正常字符
     * @param strName
     * @return
     */
    public static boolean isGarbledCode(String strName) {
        if (null == strName || 0 == strName.trim().length()) {
            return true;
        }

//        Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");
//        Matcher m = p.matcher(strName);
//        String after = m.replaceAll("");
//        String temp = after.replaceAll("\\p{P}", "");
//        char[] ch = temp.trim().toCharArray();
        char[] ch = strName.trim().toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (!Character.isLetterOrDigit(c)) {

                if (!isChinese(c) && !isSymbols(c)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 判断是否为cjk字符
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 检测是否有emoji字符,如果含有，返回true
     * @param source
     * @return 一旦含有就抛出
     */

    public static boolean containsEmoji(String source) {
        if (source != null && !source.equals("")) {
            return false;
        }

        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);

            if (!isNotEmojiCharacter(codePoint)) {
                //do nothing，判断到了这里表明，确认有表情字符
                return true;

            }

        }

        return false;

    }


    /**
     * 判断一个字符是不是emoji字符,如果不是,返回true
     * @param codePoint
     * @return
     */
    private static boolean isNotEmojiCharacter(char codePoint) {

        return (codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));

    }

    /**
     * 过滤emoji 或者 其他非文字类型的字符
     * @param source
     * @return
     */
    public static String filterEmoji(String source) {

        if (!containsEmoji(source)) {
            return source;//如果不包含，直接返回
        }
        //到这里铁定包含
        StringBuilder buf = null;

        int len = source.length();

        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            System.out.println(codePoint+"--"+Integer.toHexString(codePoint));
            if (isNotEmojiCharacter(codePoint)) {
                if (buf == null) {
                    buf = new StringBuilder(source.length());
                }
                buf.append(codePoint);
            } else {
                //处理emoji表情
//                 System.out.println(codePoint+"=="+Integer.toHexString(codePoint));
            }

        }

        if (buf == null) {
            return "";//如果全部是emoji表情，则返回""
        } else {
            return buf.toString();//如果不全是，返回处理后的结果
        }

    }

    /**
     * 首个字符校验
     * @param strName
     * @return
     */
    public static boolean checkfirstChar(String strName){
        char ch = strName.charAt(0);
        if (ch == '\'' || ch == '/' || ch == '.' || ch == ')' || ch == '^') {
            return true;
        }

        return false;
    }

}
