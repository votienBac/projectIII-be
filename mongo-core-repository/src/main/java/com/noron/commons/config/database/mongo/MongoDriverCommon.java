package com.noron.commons.config.database.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.noron.commons.config.database.mongo.model.MongoProperties;
import org.bson.Document;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import static com.noron.commons.config.database.mongo.model.CollectionConstant.*;


@Component
public class MongoDriverCommon {
    private final MongoDatabase apiMongoDatabase;
    private final MongoDatabase trainingMongoDatabase;
    private final MongoDatabase chatMongoDatabase;
    private final MongoDatabase analyticsMongoDatabase;

    public MongoDriverCommon(MongoProperties noronApiConfig,
                             MongoProperties trainingConfig,
                             MongoProperties chatConfig,
                             MongoProperties analyticsConfig) {
        this.apiMongoDatabase = new MongoClient(new MongoClientURI(noronApiConfig.getUri()))
                .getDatabase(noronApiConfig.getDatabase());

        this.trainingMongoDatabase = new MongoClient(new MongoClientURI(trainingConfig.getUri()))
                .getDatabase(trainingConfig.getDatabase());

        this.chatMongoDatabase = new MongoClient(new MongoClientURI(chatConfig.getUri()))
                .getDatabase(chatConfig.getDatabase());

        this.analyticsMongoDatabase = new MongoClient(new MongoClientURI(analyticsConfig.getUri()))
                .getDatabase(analyticsConfig.getDatabase());

        final long l = this.chatMongoDatabase.getCollection(MESSAGES).countDocuments();
    }

    @Bean
    @ConditionalOnMissingBean(name = "postCollection")
    public MongoCollection<Document> postCollection() {
        return apiMongoDatabase.getCollection(POST);
    }

    @Bean
    @ConditionalOnMissingBean(name = "topSearchKeywordCollection")
    public MongoCollection<Document> topSearchKeywordCollection() {
        return apiMongoDatabase.getCollection(TOP_SEARCH_KEYWORD);
    }

    @Bean
    @ConditionalOnMissingBean(name = "followPostCollection")
    public MongoCollection<Document> followPostCollection() {
        return apiMongoDatabase.getCollection(FOLLOW_POST);
    }

    @Bean
    @ConditionalOnMissingBean(name = "notificationCollection")
    public MongoCollection<Document> notificationCollection() {
        return apiMongoDatabase.getCollection(NOTIFICATION);
    }

    @Bean
    @ConditionalOnMissingBean(name = "topicMetaCollection")
    public MongoCollection<Document> topicMetaCollection() {
        return apiMongoDatabase.getCollection(TOPIC);
    }

    @Bean
    @ConditionalOnMissingBean(name = "userHistoryCollection")
    public MongoCollection<Document> userHistoryCollection() {
        return apiMongoDatabase.getCollection(USER_HISTORY);
    }

    @Bean
    @ConditionalOnMissingBean(name = "userScoreCollection")
    public MongoCollection<Document> userScoreCollection() {
        return apiMongoDatabase.getCollection(USER_SCORE);
    }

    @Bean
    @ConditionalOnMissingBean(name = "userScoreWeekCollection")
    public MongoCollection<Document> userScoreWeekCollection() {
        return apiMongoDatabase.getCollection(USER_SCORE_WEEK);
    }

    @Bean
    @ConditionalOnMissingBean(name = "userScoreTopicCollection")
    public MongoCollection<Document> userScoreTopicCollection() {
        return apiMongoDatabase.getCollection(USER_SCORE_TOPIC);
    }

    @Bean
    @ConditionalOnMissingBean(name = "postScoreWeekCollection")
    public MongoCollection<Document> postScoreWeekCollection() {
        return apiMongoDatabase.getCollection(POST_SCORE_WEEK);
    }

    @Bean
    @ConditionalOnMissingBean(name = "postScoreCategoryWeekCollection")
    public MongoCollection<Document> postScoreCategoryWeekCollection() {
        return apiMongoDatabase.getCollection(POST_SCORE_CATEGORY_WEEK);
    }

    @Bean
    @ConditionalOnMissingBean(name = "commentScoreWeekCollection")
    public MongoCollection<Document> commentScoreWeekCollection() {
        return apiMongoDatabase.getCollection(COMMENT_SCORE_WEEK);
    }

    @Bean
    @ConditionalOnMissingBean(name = "historyContentHonorCollection")
    public MongoCollection<Document> historyContentHonorCollection() {
        return apiMongoDatabase.getCollection(HISTORY_CONTENT_HONOR_WEEK);
    }

    @Bean
    @ConditionalOnMissingBean(name = "followUserCollection")
    public MongoCollection<Document> followUserCollection() {
        return apiMongoDatabase.getCollection(FOLLOW_USER);
    }

    @Bean
    @ConditionalOnMissingBean(name = "followerCollection")
    public MongoCollection<Document> followerCollection() {
        return apiMongoDatabase.getCollection(FOLLOWER);
    }

    @Bean
    @ConditionalOnMissingBean(name = "userContentCollection")
    public MongoCollection<Document> userContentCollection() {
        return apiMongoDatabase.getCollection(USER_CONTENT);
    }

    @Bean
    @ConditionalOnMissingBean(name = "userSaveCollection")
    public MongoCollection<Document> userSaveCollection() {
        return apiMongoDatabase.getCollection(USER_SAVE);
    }

    @Bean
    @ConditionalOnMissingBean(name = "userCommentsCollection")
    public MongoCollection<Document> userCommentsCollection() {
        return apiMongoDatabase.getCollection(COMMENT);
    }

    @Bean
    @ConditionalOnMissingBean(name = "hotCommentsCollection")
    public MongoCollection<Document> hotCommentsCollection() {
        return apiMongoDatabase.getCollection(HOT_COMMENT);
    }

    @Bean
    @ConditionalOnMissingBean(name = "userHotCommentCollection")
    public MongoCollection<Document> userHotCommentCollection() {
        return apiMongoDatabase.getCollection(USER_HOT_COMMENT);
    }

    @Bean
    @ConditionalOnMissingBean(name = "topicCollection")
    public MongoCollection<Document> topicCollection() {
        return apiMongoDatabase.getCollection(TOPIC);
    }

    @Bean
    @ConditionalOnMissingBean(name = "topicIndexCollection")
    public MongoCollection<Document> topicIndexCollection() {
        return apiMongoDatabase.getCollection(TOPIC_INDEX);
    }

    @Bean
    @ConditionalOnMissingBean(name = "postMustReadCollection")
    public MongoCollection<Document> postMustReadCollection() {
        return apiMongoDatabase.getCollection(POST_MUST_READ);
    }

    @Bean
    @ConditionalOnMissingBean(name = "coinCollection")
    public MongoCollection<Document> coinCollection() {
        return apiMongoDatabase.getCollection(COIN);
    }

    @Bean
    @ConditionalOnMissingBean(name = "newestActionPostCollection")
    public MongoCollection<Document> newestActionPostCollection() {
        return apiMongoDatabase.getCollection(NEWEST_ACTION_POST);
    }

    @Bean
    @ConditionalOnMissingBean(name = "shortNotificationCollection")
    public MongoCollection<Document> shortNotificationCollection() {
        return apiMongoDatabase.getCollection(SHORT_NOTIFICATION);
    }

    @Bean
    @ConditionalOnMissingBean(name = "currentNotificationCollection")
    public MongoCollection<Document> currentNotificationCollection() {
        return apiMongoDatabase.getCollection(CURRENT_NOTIFICATION);
    }

    @Bean
    @ConditionalOnMissingBean(name = "userNotificationStatusCollection")
    public MongoCollection<Document> userNotificationStatusCollection() {
        return apiMongoDatabase.getCollection(USER_NOTIFICATION_STATUS);
    }

    @Bean
    @ConditionalOnMissingBean(name = "votePostCollection")
    public MongoCollection<Document> votePostCollection() {
        return apiMongoDatabase.getCollection(VOTE_POST);
    }

    @Bean
    @ConditionalOnMissingBean(name = "voteCommentCollection")
    public MongoCollection<Document> voteCommentCollection() {
        return apiMongoDatabase.getCollection(VOTE_COMMENT);
    }

    @Bean
    @ConditionalOnMissingBean(name = "sessionCollection")
    public MongoCollection<Document> sessionCollection() {
        return apiMongoDatabase.getCollection(SESSION);
    }

    @Bean
    @ConditionalOnMissingBean(name = "metadataCollection")
    public MongoCollection<Document> metadataCollection() {
        return apiMongoDatabase.getCollection(METADATA);
    }

    @Bean
    @ConditionalOnMissingBean(name = "followSessionCollection")
    public MongoCollection<Document> followSessionCollection() {
        return apiMongoDatabase.getCollection(FOLLOW_SESSION);
    }

    @Bean

    @ConditionalOnMissingBean(name = "userContestQuestionCollection")
    public MongoCollection<Document> userContestQuestionCollection() {
        return apiMongoDatabase.getCollection(USER_CONTEST_QUESTION);
    }

    @Bean
    @ConditionalOnMissingBean(name = "userExamRankingCollection")
    public MongoCollection<Document> userExamRankingCollection() {
        return apiMongoDatabase.getCollection(USER_EXAM_RANKING);
    }

    @Bean
    @ConditionalOnMissingBean(name = "userContestRankingCollection")
    public MongoCollection<Document> userContestRankingCollection() {
        return apiMongoDatabase.getCollection(USER_CONTEST_RANKING);
    }

    @Bean
    @ConditionalOnMissingBean(name = "userContestRankingWeekCollection")
    public MongoCollection<Document> userContestRankingWeekCollection() {
        return apiMongoDatabase.getCollection(USER_CONTEST_RANKING_WEEK);
    }

    @Bean
    @ConditionalOnMissingBean(name = "userContestErrorCollection")
    public MongoCollection<Document> userContestErrorCollection() {
        return apiMongoDatabase.getCollection(USER_CONTEST_ERROR);
    }

    @Bean
    @ConditionalOnMissingBean(name = "smsMessageQueueErrorCollection")
    public MongoCollection<Document> smsMessageQueueErrorCollection() {
        return apiMongoDatabase.getCollection(SMS_MESSAGE_QUEUE);
    }

    @Bean
    @ConditionalOnMissingBean(name = "smsLogCollection")
    public MongoCollection<Document> smsLogCollection() {
        return apiMongoDatabase.getCollection(SMS_LOG);
    }

    @Bean
    @ConditionalOnMissingBean(name = "smsMessageQueueCollection")
    public MongoCollection<Document> smsMessageQueueCollection() {
        return apiMongoDatabase.getCollection(SMS_MESSAGE_QUEUE);
    }

    @Bean
    @ConditionalOnMissingBean(name = "organizationCollection")
    public MongoCollection<Document> organizationCollection() {
        return apiMongoDatabase.getCollection(ORGANIZATION);
    }

    @Bean
    @ConditionalOnMissingBean(name = "userExperienceCollection")
    public MongoCollection<Document> userExperienceCollection() {
        return apiMongoDatabase.getCollection(USER_EXPERIENCE);
    }

    @Bean
    @ConditionalOnMissingBean(name = "userPostBlockCollection")
    public MongoCollection<Document> userPostBlockCollection() {
        return apiMongoDatabase.getCollection(USER_POST_BLOCK);
    }

    @Bean
    @ConditionalOnMissingBean(name = "questionExpertCollection")
    public MongoCollection<Document> questionExpertCollection() {
        return apiMongoDatabase.getCollection(QUESTION_EXPERT);
    }

    @Bean
    @ConditionalOnMissingBean(name = "questionExpertDraftCollection")
    public MongoCollection<Document> questionExpertDraftCollection() {
        return apiMongoDatabase.getCollection(QUESTION_EXPERT_DRAFT);
    }

    @Bean
    @ConditionalOnMissingBean(name = "answerExpertReviewCollection")
    public MongoCollection<Document> answerExpertReviewCollection() {
        return apiMongoDatabase.getCollection(ANSWER_EXPERT_REVIEW);
    }

    @Bean
    @ConditionalOnMissingBean(name = "answerExpertCollection")
    public MongoCollection<Document> answerExpertCollection() {
        return apiMongoDatabase.getCollection(ANSWER_EXPERT);
    }

    @Bean
    @ConditionalOnMissingBean(name = "voteAnswerExpertCollection")
    public MongoCollection<Document> voteAnswerExpertCollection() {
        return apiMongoDatabase.getCollection(VOTE_ANSWER_EXPERT);
    }

    @Bean
    @ConditionalOnMissingBean(name = "questionExpertUnlockCollection")
    public MongoCollection<Document> questionExpertUnlockCollection() {
        return apiMongoDatabase.getCollection(QUESTION_EXPERT_UNLOCK);
    }

    @Bean
    @ConditionalOnMissingBean(name = "rawLogActionCollection")
    public MongoCollection<Document> rawLogActionCollection() {
        return apiMongoDatabase.getCollection(RAW_LOG_ACTION);
    }

    @Bean
    @ConditionalOnMissingBean(name = "configPostCollection")
    public MongoCollection<Document> configPostCollection() {
        return apiMongoDatabase.getCollection(CONFIG_POST);
    }

    @Bean
    @ConditionalOnMissingBean(name = "postGroupCollection")
    public MongoCollection<Document> postGroupCollection() {
        return apiMongoDatabase.getCollection(POST_GROUP);
    }

    @Bean
    @ConditionalOnMissingBean(name = "userPostPredictionCollection")
    public MongoCollection<Document> userPostPredictionCollection() {
        return apiMongoDatabase.getCollection(USER_POST_PREDICTION);
    }

    @Bean
    @ConditionalOnMissingBean(name = "postLogCollection")
    public MongoCollection<Document> postLogCollection() {
        return apiMongoDatabase.getCollection(POST_LOG);
    }

    @Bean
    @ConditionalOnMissingBean(name = "commentSpellingErrorCollection")
    public MongoCollection<Document> commentSpellingErrorCollection() {
        return apiMongoDatabase.getCollection(COMMENT_SPELLING_ERROR);
    }

    @Bean
    @ConditionalOnMissingBean(name = "postSpellingErrorCollection")
    public MongoCollection<Document> postSpellingErrorCollection() {
        return apiMongoDatabase.getCollection(POST_SPELLING_ERROR);
    }

    @Bean
    @ConditionalOnMissingBean(name = "userSegmentCollection")
    public MongoCollection<Document> userSegmentCollection() {
        return apiMongoDatabase.getCollection(USER_SEGMENT);
    }

    @Bean
    @ConditionalOnMissingBean(name = "requestMergeCollection")
    public MongoCollection<Document> requestMergeCollection() {
        return apiMongoDatabase.getCollection(REQUEST_MERGE);
    }

    @Bean
    @ConditionalOnMissingBean(name = "badCommentCollection")
    public MongoCollection<Document> badCommentCollection() {
        return apiMongoDatabase.getCollection(COMMENT_REPORT);
    }

    @Bean
    @ConditionalOnMissingBean(name = "feedRankingResultCollection")
    public MongoCollection<Document> feedRankingResultCollection() {
        return apiMongoDatabase.getCollection(FEED_RANKING_RESULT);
    }

    @Bean
    @ConditionalOnMissingBean(name = "feedNewestContentCollection")
    public MongoCollection<Document> feedNewestContentCollection() {
        return apiMongoDatabase.getCollection(FEED_NEWEST_CONTENT);
    }

    @Bean
    @ConditionalOnMissingBean(name = "mustAnswerCollection")
    public MongoCollection<Document> mustAnswerCollection() {
        return apiMongoDatabase.getCollection(MUST_ANSWER);
    }

    @Bean
    @ConditionalOnMissingBean(name = "mustCommentCollection")
    public MongoCollection<Document> mustCommentCollection() {
        return apiMongoDatabase.getCollection(MUST_COMMENT);
    }

    @Bean
    @ConditionalOnMissingBean(name = "postTopSearchGoogleCollection")
    public MongoCollection<Document> postTopSearchGoogleCollection() {
        return apiMongoDatabase.getCollection(POST_TOP_SEARCH_GOOGLE);
    }


    @Bean
    @ConditionalOnMissingBean(name = "postPriorityCollection")
    public MongoCollection<Document> postPriorityCollection() {
        return apiMongoDatabase.getCollection(POST_PRIORITY);
    }

    @Bean
    @ConditionalOnMissingBean(name = "questionFeeReportCollection")
    public MongoCollection<Document> questionFeeReportCollection() {
        return apiMongoDatabase.getCollection(QUESTION_FEE_REPORT);
    }

    @Bean
    @ConditionalOnMissingBean(name = "commentReportCollection")
    public MongoCollection<Document> commentReportCollection() {
        return apiMongoDatabase.getCollection(COMMENT_REPORT);
    }

    @Bean
    @ConditionalOnMissingBean(name = "answerExpertLogCollection")
    public MongoCollection<Document> answerExpertLogCollection() {
        return apiMongoDatabase.getCollection(ANSWER_EXPERT_LOG);
    }

    @Bean
    @ConditionalOnMissingBean(name = "answerExpertSavedCollection")
    public MongoCollection<Document> answerExpertSavedCollection() {
        return apiMongoDatabase.getCollection(ANSWER_EXPERT_SAVED);
    }

    @Bean
    @ConditionalOnMissingBean(name = "notificationLogCollection")
    public MongoCollection<Document> notificationLogCollection() {
        return apiMongoDatabase.getCollection(NOTIFICATION_LOG);
    }

    @Bean
    @ConditionalOnMissingBean(name = "userExperienceTopicCollection")
    public MongoCollection<Document> userExperienceTopicCollection() {
        return apiMongoDatabase.getCollection(USER_EXPERIENCE);
    }

    @Bean
    @ConditionalOnMissingBean(name = "userReportCollection")
    public MongoCollection<Document> userReportCollection() {
        return apiMongoDatabase.getCollection(BAD_REPORT);
    }

    @Bean
    @ConditionalOnMissingBean(name = "userTokenCollection")
    public MongoCollection<Document> userTokenCollection() {
        return apiMongoDatabase.getCollection(USER_TOKEN);
    }

    @Bean
    @ConditionalOnMissingBean(name = "hotCommentCollection")
    public MongoCollection<Document> hotCommentCollection() {
        return apiMongoDatabase.getCollection(HOT_COMMENT);
    }

    @Bean
    @ConditionalOnMissingBean(name = "userCommentCollection")
    public MongoCollection<Document> userCommentCollection() {
        return apiMongoDatabase.getCollection(COMMENT);
    }

    @Bean
    @ConditionalOnMissingBean(name = "contestHistorySendNotificationCollection")
    public MongoCollection<Document> contestHistorySendNotificationCollection() {
        return apiMongoDatabase.getCollection(CONTEST_HISTORY_SEND_NOTIFICATION);
    }

    @Bean
    @ConditionalOnMissingBean(name = "adminActionLogCollection")
    public MongoCollection<Document> adminActionLogCollection() {
        return apiMongoDatabase.getCollection(ADMIN_ACTION_LOG);
    }


    @Bean
    @ConditionalOnMissingBean(name = "userSegmentReportCollection")
    public MongoCollection<Document> userSegmentReportCollection() {
        return apiMongoDatabase.getCollection(USER_SEGMENT_REPORT);
    }

    @Bean
    @ConditionalOnMissingBean(name = "myclipUrlCollection")
    public MongoCollection<Document> myclipUrlCollection() {
        return analyticsMongoDatabase.getCollection(MYCLIP_URL);
    }

    @Bean
    @ConditionalOnMissingBean(name = "postGroupConfigCollection")
    public MongoCollection<Document> postGroupConfigCollection() {
        return apiMongoDatabase.getCollection(POST_GROUP_CONFIG);
    }

    @Bean
    @ConditionalOnMissingBean(name = "tokenUserDeviceCollection")
    public MongoCollection<Document> tokenUserDeviceCollection() {
        return apiMongoDatabase.getCollection(TOKEN_USER_DEVICE);
    }

    @Bean
    @ConditionalOnMissingBean(name = "userDeviceInfoCollection")
    public MongoCollection<Document> userDeviceInfoCollection() {
        return apiMongoDatabase.getCollection(USER_DEVICE_INFO);
    }

    @Bean
    @ConditionalOnMissingBean(name = "userSocialRawCollection")
    public MongoCollection<Document> userSocialRawCollection() {
        return apiMongoDatabase.getCollection(USER_SOCIAL_RAW);
    }

    @Bean
    @ConditionalOnMissingBean(name = "errorIndexingCollection")
    public MongoCollection<Document> errorIndexingCollection() {
        return apiMongoDatabase.getCollection(ERROR_INDEXING);
    }

    @Bean
    @ConditionalOnMissingBean(name = "commentCollection")
    public MongoCollection<Document> commentCollection() {
        return apiMongoDatabase.getCollection(COMMENT);
    }

    @Bean
    @ConditionalOnMissingBean(name = "pageMediaCollection")
    public MongoCollection<Document> pageMediaCollection() {
        return apiMongoDatabase.getCollection(PAGE_MEDIA);
    }

    @Bean
    @ConditionalOnMissingBean(name = "userTopicPostStatsCollection")
    MongoCollection<Document> userTopicPostStatsCollection() {
        return apiMongoDatabase.getCollection(USER_TOPIC_POST_STATS);
    }

    @Bean
    @ConditionalOnMissingBean(name = "optionMenuUserCollection")
    MongoCollection<Document> optionMenuUserCollection() {
        return apiMongoDatabase.getCollection(OPTION_MENU_USER);
    }

    @Bean
    @ConditionalOnMissingBean(name = "hotTagCollection")
    MongoCollection<Document> hotTagCollection() {
        return apiMongoDatabase.getCollection(HOT_TAG);
    }

    @Bean
    @ConditionalOnMissingBean(name = "viewPostCollection")
    MongoCollection<Document> viewPostCollection() {
        return apiMongoDatabase.getCollection(VIEW_POST);
    }

    @Bean
    @ConditionalOnMissingBean(name = "userTopicPostStatsDailyCollection")
    MongoCollection<Document> userTopicPostStatsDailyCollection() {
        return apiMongoDatabase.getCollection(USER_TOPIC_POST_STATS_DAILY);
    }

    @Bean
    @ConditionalOnMissingBean(name = "errorNotificationCollection")
    public MongoCollection<Document> errorNotificationCollection() {
        return apiMongoDatabase.getCollection(ERROR_NOTIFICATION);
    }


    @Bean
    @ConditionalOnMissingBean(name = "contentCampaignStats")
    public MongoCollection<Document> contentCampaignStatsCollection() {
        return apiMongoDatabase.getCollection(CONTENT_CAMPAIGN_STATS);
    }

    @Bean
    @ConditionalOnMissingBean(name = "commentExpertCollection")
    public MongoCollection<Document> commentExpertCollection() {
        return apiMongoDatabase.getCollection(COMMENT_EXPERT);
    }

    @Bean
    @ConditionalOnMissingBean(name = "userTagsCollection")
    public MongoCollection<Document> userTagsCollection() {
        return apiMongoDatabase.getCollection(USER_TAGS);
    }

    @Bean
    @ConditionalOnMissingBean(name = "userUnlockAnswerCollection")
    public MongoCollection<Document> userUnlockAnswerCollection() {
        return apiMongoDatabase.getCollection(USER_UNLOCK_ANSWER);
    }

    @Bean
    @ConditionalOnMissingBean(name = "postTagsCollection")
    public MongoCollection<Document> postTagsCollection() {
        return apiMongoDatabase.getCollection(POST_TAGS);
    }

    @Bean
    @ConditionalOnMissingBean(name = "postRemindReplyCollection")
    public MongoCollection<Document> postRemindReplyCollection() {
        return apiMongoDatabase.getCollection(POST_REMIND_REPLY);
    }

    @Bean
    @ConditionalOnMissingBean(name = "hashTagOfPostCollection")
    public MongoCollection<Document> hashTagOfPostCollection() {
        return apiMongoDatabase.getCollection(HASH_TAG_OF_POST);
    }

    @Bean
    @ConditionalOnMissingBean(name = "UserStatisticsYear")
    public MongoCollection<Document> userStatisticsYearCollection() {
        return apiMongoDatabase.getCollection(USER_STATISTICS_YEAR);
    }

    @Bean
    @ConditionalOnMissingBean(name = "userHistorySearchCollection")
    public MongoCollection<Document> userHistorySearchCollection() {
        return apiMongoDatabase.getCollection(USER_HISTORY_SEARCH);
    }

    @Bean
    @ConditionalOnMissingBean(name = "userGradeLogDailyCollection")
    public MongoCollection<Document> userGradeLogDailyCollection() {
        return apiMongoDatabase.getCollection(USER_GRADE_LOG_DAILY);
    }

    @Bean
    @ConditionalOnMissingBean(name = "ambassadorPointActiveCollection")
    public MongoCollection<Document> ambassadorPointActiveCollection() {
        return apiMongoDatabase.getCollection(AMBASSADOR_POINT_ACTIVE);
    }

    @Bean
    @ConditionalOnMissingBean(name = "postMediaCollection")
    public MongoCollection<Document> postMediaCollection() {
        return apiMongoDatabase.getCollection(POST_MEDIA);
    }

    @Bean
    @ConditionalOnMissingBean(name = "commentMediaCollection")
    public MongoCollection<Document> commentMediaCollection() {
        return apiMongoDatabase.getCollection(COMMENT_MEDIA);
    }

    @Bean
    @ConditionalOnMissingBean(name = "reviewContentLogCollection")
    public MongoCollection<Document> reviewContentLogCollection() {
        return apiMongoDatabase.getCollection(REVIEW_CONTENT_LOG);
    }

    @Bean
    @ConditionalOnMissingBean(name = "blogFiveFishCollection")
    public MongoCollection<Document> blogFiveFishCollection() {
        return trainingMongoDatabase.getCollection(BLOG_FIVE_FISH_CRAWLER);
    }

    @Bean
    @ConditionalOnMissingBean(name = "rawPostCrawlerCollection")
    public MongoCollection<Document> rawPostCrawlerCollection() {
        return apiMongoDatabase.getCollection(RAW_POST_CRAWLER);
    }

    @Bean
    @ConditionalOnMissingBean(name = "messagesCollection")
    public MongoCollection<Document> messagesCollection() {
        return chatMongoDatabase.getCollection(MESSAGES);
    }

    @Bean
    @ConditionalOnMissingBean(name = "pageviewPostCounterCollection")
    public MongoCollection<Document> pageviewPostCounterCollection() {
        return analyticsMongoDatabase.getCollection(PAGEVIEW_POST_COUTER);
    }

    @Bean
    @ConditionalOnMissingBean(name = "pageviewPostDailyCollection")
    public MongoCollection<Document> pageviewPostDailyCollection() {
        return analyticsMongoDatabase.getCollection(PAGEVIEW_POST_DAILY);
    }

    @Bean
    @ConditionalOnMissingBean(name = "postStatisticDailyCollection")
    public MongoCollection<Document> postStatisticDailyCollection() {
        return analyticsMongoDatabase.getCollection(POST_STATISTIC_DAILY);
    }

    @Bean
    @ConditionalOnMissingBean(name = "postStatisticMonthlyCollection")
    public MongoCollection<Document> postStatisticMonthlyCollection() {
        return analyticsMongoDatabase.getCollection(POST_STATISTIC_MONTHLY);
    }

    @Bean
    @ConditionalOnMissingBean(name = "clientLogHourlyCollection")
    public MongoCollection<Document> clientLogHourlyCollection() {
        return analyticsMongoDatabase.getCollection(CLIENT_LOG_HOURLY);
    }

    @Bean
    @ConditionalOnMissingBean(name = "postPageViewHourlyCollection")
    public MongoCollection<Document> postPageViewHourlyCollection() {
        return analyticsMongoDatabase.getCollection(POST_PAGE_VIEW_HOURLY);
    }

    @Bean
    @ConditionalOnMissingBean(name = "userPostFrequencyDailyCollection")
    public MongoCollection<Document> userPostFrequencyDailyCollection() {
        return analyticsMongoDatabase.getCollection(USER_POST_FREQUENCY_DAILY);
    }

    @Bean
    @ConditionalOnMissingBean(name = "seriesCollection")
    public MongoCollection<Document> seriesCollection() {
        return apiMongoDatabase.getCollection(SERIES);
    }

    @Bean
    @ConditionalOnMissingBean(name = "feedContentCollection")
    public MongoCollection<Document> feedContentCollection() {
        return apiMongoDatabase.getCollection(FEED_CONTENT);
    }

    @Bean
    @ConditionalOnMissingBean(name = "newFeedContentCollection")
    public MongoCollection<Document> newFeedContentCollection() {
        return apiMongoDatabase.getCollection(NEW_FEED_CONTENT);
    }

    @Bean
    @ConditionalOnMissingBean(name = "forYouFeedContentCollection")
    public MongoCollection<Document> forYouFeedContentCollection() {
        return apiMongoDatabase.getCollection(FOR_YOU_FEED_CONTENT);
    }

    @Bean
    @ConditionalOnMissingBean(name = "hotFeedContentCollection")
    public MongoCollection<Document> hotFeedContentCollection() {
        return apiMongoDatabase.getCollection(HOT_FEED_CONTENT);
    }

    @Bean
    @ConditionalOnMissingBean(name = "feedUserPostCollection")
    public MongoCollection<Document> feedUserPostCollection() {
        return apiMongoDatabase.getCollection(FEED_CONTENT);
    }

    @Bean
    @ConditionalOnMissingBean(name = "operatorPointCollection")
    public MongoCollection<Document> operatorPointCollection() {
        return apiMongoDatabase.getCollection(OPERATOR_POINT);
    }

    @Bean
    @ConditionalOnMissingBean(name = "userActivePointCollection")
    public MongoCollection<Document> userActivePointCollection() {
        return apiMongoDatabase.getCollection(USER_ACTIVE_POINT);
    }

    @Bean
    @ConditionalOnMissingBean(name = "sessionPostCollection")
    public MongoCollection<Document> sessionPostCollection() {
        return apiMongoDatabase.getCollection(SESSION_POST);
    }

    @Bean
    @ConditionalOnMissingBean(name = "videoCollection")
    public MongoCollection<Document> videoCollection() {
        return apiMongoDatabase.getCollection(VIDEO);
    }

    @Bean
    @ConditionalOnMissingBean(name = "meetingCollection")
    public MongoCollection<Document> meetingCollection() {
        return apiMongoDatabase.getCollection(MEETING);
    }

    @Bean
    @ConditionalOnMissingBean(name = "loginLogCollection")
    public MongoCollection<Document> loginLogCollection() {
        return apiMongoDatabase.getCollection(LOGIN_LOG);
    }

    @Bean
    @ConditionalOnMissingBean(name = "blockBlogLogCollection")
    public MongoCollection<Document> blockBlogLogCollection() {
        return apiMongoDatabase.getCollection(BLOCK_BLOG_LOG);
    }

}
