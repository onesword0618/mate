package jp.gushed.mate.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 選択番号の生成を検証するクラス.
 * 
 * @author onesword0618
 *
 */
public class ChooseNumberTest {

    @DisplayName("選択番号の生成が行えること")
    @Test
    void create() {
        final var sut = new ChooseNumber("1");
        final var actual = sut.value;
        final var expected = "1";
        assertEquals(expected, actual);
    }

    @DisplayName("指定の番号ではないときにメッセージが出力されること")
    @Test
    void illeagalNumber() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new ChooseNumber("5"));
        assertEquals("Invalid number:  [5]", exception.getMessage());
    }

    @DisplayName("全角数字のときにメッセージが出力されること")
    @Test
    void illeagalFullWideNumber() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new ChooseNumber("１"));
        assertEquals("Invalid number:  [１]", exception.getMessage());
    }

    @DisplayName("空白のときにメッセージが出力されること")
    @Test
    void illeagalBlank() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new ChooseNumber("　"));
        assertEquals("Invalid number:  [　]", exception.getMessage());
    }

    @DisplayName("nullのときにメッセージが出力されること")
    @Test
    void illeagalNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new ChooseNumber(null));
        assertEquals("Invalid number:  [null]", exception.getMessage());
    }

}
