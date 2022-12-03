package vn.noron.data.constant;

import java.util.List;

public class Constant {
    public static final class NewsInfoConstant {
        public static final String FAVICON_DEFAULT = "https://www.google.com/images/branding/googleg/1x/googleg_standard_color_128dp.png";
        public static final String LOGO_FB_DEFAULT = "https://cdn-icons-png.flaticon.com/512/124/124010.png";
        public static final String LOGO_YOUTUBE_DEFAULT = "https://www.logo.wine/a/logo/YouTube/YouTube-Icon-Full-Color-Logo.wine.svg";
        public static final String LOGO_TIKTOK_DEFAULT = "https://logos-marques.com/wp-content/uploads/2021/03/TikTok-Logo-2016.png";
    }

    public static final class Pattern {
        public static final String USERNAME_PATTERN = "^[a-z0-9]{8,64}$";
        public static final String PASSWORD_PATTERN = "^[a-zA-Z0-9_!#$%&'*+\\=?`{|}~^.-]{8,64}$";
        public static final String EMAIL_PATTERN = "^[a-zA-Z0-9_!#$%&'*+\\=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    }

    public static final String STRING_DELIMITER = ";";

    public static int invalidExceptionCode = 422;

    public static final String SDF_FORMAT_INDEX = "yyMMdd";

    public static final String SDF_FORMAT = "yyyy/MM/dd HH:mm:ss";

    public static final String PRE_TOPIC_NOTI = "topic_noti_user_id_";

    public static final Double INTERACTION_WEIGH = 0.3;
    public static final Double NUMBER_NEWS_WEIGH = 0.7;

    public static final Double THRESHOLD_SCORE_FB_PROPOSE_OBJECT = 0.0;

    public static final Double SIMILAR_SCORE = 0.5;
    public static final Long THRESHOLD_NUMBER_INTERACTION_TO_CRAWL = 0l;
    public static final List<String> NEW_PLATFORM = List.of(
            "skype",
            "telegram",
            "gapo",
            "whatsapp",
            "zalo");

    public static final class Table {
        public static final List<String> SEARCH_COLUMN_IGNORES = List.of("password", "status");
    }

//    public static final class DomainAnalyzer{
//        public static Map<String, String> DomainAnalyzer
//        static {
//
//        }
//    }

    public static final String CRAWL_FACEBOOK_PRIORITY = "crawl_page";
    public static final String CRAWL_YOUTUBE_PRIORITY = "crawl_youtube_priority";
    public static final String CRAWL_TIKTOK_PRIORITY = "crawl_tiktok_priority";
    public static final String CRAWL_NEWS_PRIORITY = "crawl_news_priority";
    public static final String ARTICLE_PROMINENT_TYPE = "prominent";
    public static final String ARTICLE_DANGEROUS_TYPE = "dangerous";
}
