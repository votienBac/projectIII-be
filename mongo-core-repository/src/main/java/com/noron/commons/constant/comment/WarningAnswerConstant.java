package com.noron.commons.constant.comment;

import com.noron.commons.data.dto.Media;
import com.noron.commons.data.model.comment.Comment;

import static com.noron.commons.utils.CollectionUtils.getOrDefault;
import static com.noron.commons.utils.CollectionUtils.size;
import static com.noron.commons.utils.StringUtil.html2text;

public enum WarningAnswerConstant {
    video_short_content(1, 100, 0, 1,
            "Câu trả lời của bạn hơi ngắn gọn. Nội dung của bạn sẽ không được đề xuất hiển thị. Bạn có tiếp tục đăng bài này?"),
    image_short_content(2, 250, 1, 0,
            "Câu trả lời của bạn hơi ngắn gọn. Nội dung của bạn sẽ không được đề xuất hiển thị. Bạn có tiếp tục đăng bài này?"),
    short_content(3, 500, 0, 0,
            "Các noronion mong đợi những câu trả lời chất lượng. Dường như trả lời của bạn hơi ngắn gọn, chưa có hình ảnh (hoặc video) minh họa. Nội dung này sẽ không được đề xuất hiển thị. Bạn có muốn tiếp tục đăng câu trả lời này?"),
    good_answer(4, 500, 1, 1, "câu trả lời tốt");
    private Integer id;
    private Integer lengthContent;
    private Integer minImage;
    private Integer minVideo;
    private String message;

    WarningAnswerConstant(int id, int lengthContent, int minImage, int minVideo, String message) {
        this.id = id;
        this.lengthContent = lengthContent;
        this.minImage = minImage;
        this.minVideo = minVideo;
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public Integer getLengthContent() {
        return lengthContent;
    }

    public Integer getMinImage() {
        return minImage;
    }

    public Integer getMinVideo() {
        return minVideo;
    }

    public String getMessage() {
        return message;
    }

    public static WarningAnswerConstant getCodeWarning(Comment comment) {
        int length = html2text(comment.getIsAnswerFee() != null && comment.getIsAnswerFee() ?
                comment.getFullContent() : comment.getContent()).length();

        return getOrDefault(isVideoShortContent(comment.getMedia(), length),
                getOrDefault(isImageShortContent(comment.getMedia(), length), isShortContent(length)));
    }

    private static WarningAnswerConstant isShortContent(Integer length) {
        return short_content;
//        return length < short_content.getLengthContent() ? short_content : good_answer;
    }

    private static WarningAnswerConstant isImageShortContent(Media media, Integer length) {
        if (media != null && size(media.getImageSRC()) >= image_short_content.getMinImage()) {
            return length < image_short_content.getLengthContent() ? image_short_content : good_answer;
        } else return null;
    }

    private static WarningAnswerConstant isVideoShortContent(Media media, Integer length) {
        if (media != null && (size(media.getVideoSRC()) + size(media.getIframeSRC())) >= video_short_content.getMinVideo()) {
            return length < video_short_content.getLengthContent() ? video_short_content : good_answer;
        } else return null;
    }
}
