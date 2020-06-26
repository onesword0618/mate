package jp.gushed.mate.domain.model;

import java.util.Objects;
import java.util.regex.Pattern;

import com.google.common.base.Preconditions;

/**
 * 選択番号を生成する.
 * 
 * @author onesword0618
 *
 */
public class ChooseNumber {

    private static final String USE_NUMBER = "[1-4]";
    private static final String FULL_WIDE_SPACE = "　";

    /**
     * 保持している値.
     */
    public final String value;

    /**
     * 実行時の引数を受け取る. 引数の値が妥当な生成する.
     * 
     * @param number 半角数字
     */
    public ChooseNumber(final String number) {
        Preconditions.checkArgument(requiredValue(number), "Invalid number: ", number);
        this.value = number;
    }

    @Override
    public String toString() {
        return "ChooseNumber [value=" + value + "]";
    }

    private boolean requiredValue(final String number) {
        return notBlankAndEmpty(number) && number(number) ? true : false;
    }

    private boolean notBlankAndEmpty(final String number) {
        return Objects.isNull(number) || number.isBlank() || number.equals(FULL_WIDE_SPACE) ? false : true;
    }

    private boolean number(final String number) {
        return Pattern.matches(USE_NUMBER, number);
    }

}
