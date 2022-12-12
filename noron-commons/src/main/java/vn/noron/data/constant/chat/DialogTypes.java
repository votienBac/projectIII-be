
package vn.noron.data.constant.chat;


public enum DialogTypes {
    USER(1), BOT(2), GROUP_USER(3), UNKNOWN(-1);

    private int code;

    DialogTypes(int code) {
        this.code = code;
    }

    public static DialogTypes from(int code) {
        for (DialogTypes status : values()) {
            if (status.getCode() == code)
                return status;
        }

        return UNKNOWN;
    }

    public int getCode() {
        return this.code;
    }

    public byte byteCode() {
        return (byte) this.code;
    }
}
