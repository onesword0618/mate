package jp.gushed.mate.infrastructure.internal;

/**
 * システムで扱う、特殊記号を表現した定数クラス.
 * 
 * @author onesword0618
 *
 */
public enum SystemKey {

    HOME("user.home"), SEPARATOR("file.separator"), BLANK(" "), SLASH("/");

    public String value;

    SystemKey(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

}
