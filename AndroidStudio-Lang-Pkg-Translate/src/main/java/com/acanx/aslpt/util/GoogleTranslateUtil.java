package com.acanx.aslpt.util;

import com.acanx.aslpt.App;
import com.acanx.aslpt.constant.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringEscapeUtils.unescapeHtml4;

public class GoogleTranslateUtil {

    private static final Logger logger = LoggerFactory.getLogger(GoogleTranslateUtil.class);

    /**
     *  缺省英语翻译成中文
     * @param text
     * @return
     * @throws MalformedURLException
     * @throws UnsupportedEncodingException
     */
    public static String googleTranslate(String text) throws MalformedURLException, UnsupportedEncodingException {
        return googleTranslate(text, Constant.LANG_EN, Constant.LANG_ZH_CN);
    }

    public static String googleTranslate(String text, String fromLang, String toLang) throws UnsupportedEncodingException, MalformedURLException {
        StringBuilder response = new StringBuilder();
        URL url = new URL(String.format("https://translate.google.com/m?sl=auto&tl=%s&hl=%s&q=%s", toLang, Constant.LANG_EN, URLEncoder.encode(text.trim(), Constant.ENCODE_UTF8)));
        try {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), Constant.ENCODE_UTF8))) {
                String line;
                while ((line = br.readLine()) != null)
                    response.append(line + "\n");
            }
        } catch (IOException e) {
            logger.error((e.getMessage() + e.getStackTrace()));
        }
        Matcher matcher = Pattern.compile("class=\"result-container\">([^<]*)<\\/div>", Pattern.MULTILINE).matcher(response);
        matcher.find();
        String match = matcher.group(1);
        if (match == null || match.isEmpty()) {
            logger.error("["+ text + "]翻译为["+ toLang+"]失败！");
            throw new RuntimeException("翻译失败，未获取到预期的翻译结果");
        }
        return unescapeHtml4(match);
    }



}
