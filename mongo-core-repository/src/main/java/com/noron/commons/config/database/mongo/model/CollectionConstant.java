package com.noron.commons.config.database.mongo.model;

public class CollectionConstant {

    // NOTIFICATION
    public static final String NOTIFICATION = "notification";
    public static final String SHORT_NOTIFICATION = "short_notification";
    public static final String CURRENT_NOTIFICATION = "current_notification";
    public static final String USER_NOTIFICATION_STATUS = "user_notification_status";
    public static final String ERROR_NOTIFICATION = "error_notification";
    public static final String NOTIFICATION_LOG = "notification";

    // SMS
    public static final String SMS_LOG = "sms_log";
    public static final String SMS_MESSAGE_QUEUE = "sms_message_queue";

    // CONTEST
    public static final String USER_CONTEST_ERROR = "user_contest_error";
    public static final String USER_CONTEST_QUESTION = "user_contest_question"; // log trả lời câu hỏi
    public static final String USER_CONTEST_RANKING = "user_contest_ranking"; // ranking all contest
    public static final String USER_CONTEST_RANKING_WEEK = "user_contest_ranking_week"; // ranking theo từng tuần
    public static final String USER_EXAM_RANKING = "user_exam_ranking"; // ranking từng bài thi
    public static final String CONTEST_HISTORY_SEND_NOTIFICATION = "contest_history_send_notification";

    // POST
    public static final String POST = "post";
    public static final String POST_MEDIA = "post_media";
    public static final String COMMENT_MEDIA = "comment_media";
    public static final String POST_REMIND_REPLY = "post_remind_reply";
    public static final String VOTE_POST = "vote_post";
    public static final String POST_LOG = "post_log"; // log chỉnh sữa title... của bài post
    public static final String POST_GROUP = "post_group"; // admin có thể move bài post từ group A sang group B
    public static final String CONFIG_POST = "config_post"; // show ở màn hình khám phá
    public static final String NEWEST_ACTION_POST = "newest_action_post";
    public static final String QUESTION_EXPERT = "question_expert";
    public static final String QUESTION_EXPERT_DRAFT = "question_expert_draft";
    public static final String POST_PRIORITY = "post_priority";

    // POST-FEED
    public static final String POST_GROUP_CONFIG = "post_group_config"; // admin push bài post vào từng block
    public static final String USER_POST_BLOCK = "user_post_block"; // từ config -> từng user
    public static final String USER_POST_PREDICTION = "user_post_prediction";
    public static final String FEED_RANKING_RESULT = "feed_ranking_result";
    public static final String POST_TAGS = "post_tags";
    public static final String FEED_NEWEST_CONTENT = "feed_newest_content";
    public static final String MUST_ANSWER = "must_answer";
    public static final String MUST_COMMENT = "must_comment";
    public static final String FEED_CONTENT = "feed_content";
    public static final String NEW_FEED_CONTENT = "new_feed_content";
    public static final String FOR_YOU_FEED_CONTENT = "for_you_feed_content";
    public static final String HOT_FEED_CONTENT = "hot_feed_content";
    public static final String FEED_USER_POST = "feed_user_post";
    public static final String POST_TOP_SEARCH_GOOGLE = "post_top_search_google";

    // COMMENT
    public static final String COMMENT_REPORT = "comment_report";
    public static final String COMMENT_SPELLING_ERROR = "comment_spelling_error";
    public static final String POST_SPELLING_ERROR = "post_spelling_error";
    public static final String COMMENT = "user-comments";
    public static final String HOT_COMMENT = "hot_comment";
    public static final String USER_HOT_COMMENT = "user_hot_comment";
    public static final String VOTE_COMMENT = "vote_comment";

    // COIN
    public static final String COIN = "coin";

    // TOPIC
    public static final String TOPIC = "topic"; // config must_read của từng topic, số lượng bài post
    public static final String TOPIC_INDEX = "topic_index";
    public static final String POST_MUST_READ = "post_must_read"; // config must_read của từng topic, số lượng bài post
    //PAGE
    public static final String PAGE_MEDIA = "page_media";

    // SESSION
    public static final String SESSION = "session";
    public static final String METADATA = "metadata";
    public static final String FOLLOW_SESSION = "follow_session";

    //SEARCH
    public static final String TOP_SEARCH_KEYWORD = "top_search_keyword";

    public static final String CONTENT_CAMPAIGN_STATS = "content_campaign_stats";
    // USER
    public static final String FOLLOWER = "follower"; // danh sách user theo dõi user
    public static final String FOLLOW_USER = "follow_user"; // cái này không dùng nưa -> follower
    public static final String USER_SEGMENT = "user_segment";
    public static final String ORGANIZATION = "organization";
    public static final String USER_EXPERIENCE = "user_experience";
    public static final String USER_HISTORY = "user_history";
    public static final String TOKEN_USER_DEVICE = "token_user_device";
    public static final String USER_DEVICE_INFO = "user_device_info";
    public static final String USER_SOCIAL_RAW = "user_social_raw";

    // USER-POST
    public static final String USER_SAVE = "user_save";
    public static final String BAD_REPORT = "bad_report";
    public static final String USER_CONTENT = "user_content";
    public static final String USER_TOKEN = "user_token";
    public static final String USER_TAGS = "user_tags";
    public static final String USER_UNLOCK_ANSWER = "user_unlock_answer";

    public static final String USER_SCORE = "user_score"; // log điểm của user
    public static final String USER_SCORE_WEEK = "user_score_week"; // cronjob chạy tính dựa trên user_score
    public static final String USER_SCORE_TOPIC = "user_score_topic"; // cronjob chạy tính dựa trên user_score

    public static final String POST_SCORE_WEEK = "post_score_week";  // log điểm post theo tuần, ko phân biệt category
    public static final String POST_SCORE_CATEGORY_WEEK = "post_score_category_week"; // log điểm post theo tuần, có phân biệt category
    public static final String COMMENT_SCORE_WEEK = "comment_score_week"; // log điểm comment theo tuần cho từng post đc tạo trong tuần
    public static final String HISTORY_CONTENT_HONOR_WEEK = "history_content_honor_week"; // lịch sử vinh danh nội dung trong tuần


    public static final String FOLLOW_POST = "follow_post";
    public static final String REQUEST_MERGE = "request_merge"; // gộp câu hỏi

    // FEE
    public static final String ANSWER_EXPERT_REVIEW = "answer_expert_review";
    public static final String ANSWER_EXPERT = "answer_expert";
    public static final String VOTE_ANSWER_EXPERT = "vote_answer_expert";
    public static final String QUESTION_EXPERT_UNLOCK = "question_expert_unlock";
    public static final String QUESTION_FEE_REPORT = "question_fee_report";
    public static final String ANSWER_EXPERT_LOG = "answer_expert_log";
    public static final String ANSWER_EXPERT_SAVED = "answer_expert_saved";
    public static final String COMMENT_EXPERT = "comment_expert";

    public static final String RAW_LOG_ACTION = "raw_log_action";

    // Log, analytics
    public static final String ERROR_INDEXING = "error_indexing";
    public static final String ADMIN_ACTION_LOG = "admin_action_log";
    public static final String USER_SEGMENT_REPORT = "user_segment_report";
    public static final String MYCLIP_URL = "myclip_url";
    // user topic
    public static final String USER_TOPIC_POST_STATS = "user_topic_post_stats";
    public static final String USER_TOPIC_POST_STATS_DAILY = "user_topic_post_stats_daily";

    public static final String OPTION_MENU_USER = "option_menu_user";

    public static final String HOT_TAG = "hot_tag";

    public static final String VIEW_POST = "view_post";
    public static final String HASH_TAG_OF_POST = "hash_tag_of_post";

    public static final String USER_STATISTICS_YEAR = "user_statistics_year";
    public static final String USER_GRADE_LOG_DAILY = "user_grade_log_daily";
    public static final String AMBASSADOR_POINT_ACTIVE = "ambassador_point_active";

    public static final String USER_HISTORY_SEARCH = "user_history_search";

    //review
    public static final String REVIEW_CONTENT_LOG = "review_content_log";
    // crawler
    public static final String BLOG_FIVE_FISH_CRAWLER = "blog_five_fish_crawler";
    public static final String RAW_POST_CRAWLER = "raw_post_crawler";

    // Noron chat
    public static final String MESSAGES = "messages";
    public static final String MESSAGE_MEDIA = "message_media";
    public static final String PAGEVIEW_POST_COUTER = "pageview_post_counter";
    public static final String PAGEVIEW_POST_DAILY = "pageview_post_daily";
    public static final String POST_STATISTIC_DAILY = "post_statistic_daily";
    public static final String POST_STATISTIC_MONTHLY = "post_statistic_monthly";
    public static final String CLIENT_LOG_HOURLY = "client_log_hourly";
    public static final String POST_PAGE_VIEW_HOURLY = "post_page_view_hourly";
    public static final String USER_POST_FREQUENCY_DAILY = "user_post_frequency_daily";
    public static final String SERIES = "series";
    public static final String OPERATOR_POINT = "operator_point";
    public static final String USER_ACTIVE_POINT = "user_active_point";
    public static final String SESSION_POST = "session_post";
    public static final String VIDEO = "video";
    public static final String MEETING = "meeting";
    public static final String LOGIN_LOG = "login_log";
    public static final String BLOCK_BLOG_LOG = "block_blog_log";
}
