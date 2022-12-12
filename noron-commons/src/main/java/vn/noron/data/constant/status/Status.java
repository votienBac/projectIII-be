package vn.noron.data.constant.status;

import static java.util.Arrays.asList;

public class Status {
    public static final int DELETED = -1;
    public static final int ACTIVE = 1;
    public static final byte ACTIVE_USER = 1;
    public static final byte NOT_ACTIVE_USER = 0;
    public static final int DRAFT = 0;
    public static final int WAITING_POST = 2;
    public static final int ONLY_ASK = 3;
    public static final int PUBLIC = 0;
    public static final int ANONYMOUS = 1;
    public static final Byte SUCCESS = (byte) 1;
    public static final Byte ERROR = (byte) 0;
    public static final Byte SEEDER = (byte) 1;
    //
    public static final int IGNORE = -2;
    // vote
    public static final int VOTE = 1;
    public static final int DOWN_VOTE = -1;
    public static final int NON_VOTE = 0;

    // notification
    public static final int NOT_VIEWED_YET = 0;
    public static final int NOT_READ_YET = 1;
    public static final int READ = 2;
    public static final int DISABLE = -1;
    public static final int PIN = 1;
    public static final int NOT_PIN = 0;

    // expert
    //Expert inviting
    public static final byte IGNORE_INVITED_ITEM = -1;
    public static final byte VIEWED_INVITED_ITEM = 1;
    public static final byte UN_VIEWED_INVITED_ITEM = 0;

    //History
    public static final byte PUBLIC_HISTORY = 0;

    //Followed Topic order
    public static final int TITLE_TOPIC = 2;
    public static final int PROFILE_TOPIC = 1;
    public static final int NORMAL_TOPIC = 0;
    public static final byte FAVOURITE = 1;
    public static final byte UN_FAVOURITE = -1;
    public static final byte NORMAL_FAVOURITE = 0;
    public static final byte TOPIC_NOTIFICATION = 1;
    public static final byte TOPIC_UN_NOTIFICATION = -1;

    public static final int MAX_NUM_TITLE_TOPIC = 1;
    public static final int MAX_NUM_PROFILE_TOPIC = 3;

    //Topic status
    public static final short ACTIVE_TOPIC = 1;
    public static final short IN_ACTIVE_TOPIC = 0;

    public static final byte ONBOARDING = 1;
    public static final byte NOT_ONBOARDING = 0;

    //Quest status
    public static final Byte ACTIVE_QUEST = 1;

    //Session status

    public static final Integer DRAFT_SESSION = 0;
    public static final Integer PUBLIC_SESSION = 1;
    public static final Integer QUESTIONABLE_SESSION = 2;
    public static final Integer UN_QUESTIONABLE_SESSION = 3;

    // expert status
    public static final Integer INVITE_DEFAULT = 0;
    public static final Integer INVITE_ME = 1;
    public static final Integer INVITE_USER = 2;

    // expert expertAnswered
    public static final Integer EXPERT_ANS = 1;

    // question fee notification unlock
    public static final int SENT = 1;
    public static final int NOT_SEND = 0;

    public enum BooleanToByte {
        TRUE((byte) 1), FALSE((byte) 0);
        private byte value;

        BooleanToByte(byte value) {
            this.value = value;
        }

        public byte getValue() {
            return value;
        }
    }

    //config post
    public static final int ON = 1;
    public static final int OFF = 0;

    // contest
    public static final int RIGHT = 1;
    public static final int WRONG = 0;
    public static final byte CORRECTED = (byte) 1;
    public static final int START_POSITION_EXAM = 1;
    public static final int END_POSITION_EXAM = 10;
    public static final int IS_VALID_EXAM = 1;
    public static final int IS_NOT_TESTING = 0;
    public static final int NOT_VALID_EXAM = 0;

    public static final int MUST_ANSWER_NOT_DONE = 0;
    public static final int MUST_COMMENT_NOT_DONE = 0;
    public static final int MUST_ANSWER_DONE = 1;
    public static final int MUST_COMMENT_DONE = 1;
    public static final int MUST_ANSWER_IGNORE = 2;
    public static final int MUST_COMMENT_IGNORE = 2;

    public static final int OPERATOR_DONE = 1;
    public static final int OPERATOR_NOT_DONE = 0;
    public static final int OPERATOR_IGNORE = 2;

    public static boolean isValidStatusInsertComment(Integer status) {
        return asList(DELETED, ACTIVE, WAITING_POST, ONLY_ASK).contains(status);
    }
}
