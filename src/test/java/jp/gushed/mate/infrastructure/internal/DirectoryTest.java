package jp.gushed.mate.infrastructure.internal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * ディレクトリを構成する文字列を生成の検証クラス
 * 
 * @author onesword0618
 *
 */
public class DirectoryTest {

    @DisplayName("ディレクトリ構成用の文字列が返却されること")
    @Test
    void compose() {
        final List<String> keys = new ArrayList<>();
        keys.add("input.directory");

        final Map<String, String> property = new HashMap<>();
        property.put("initial.directory", "Documents");
        property.put("work.directory", "files");
        property.put("input.directory", "input");

        final var sut = Directory.valueOf(keys, property);
        final var actual = sut.compose();
        final var expected = System.getProperty("user.home")
            + System.getProperty("file.separator")
            + "Documents"
            + System.getProperty("file.separator")
            + "files"
            + System.getProperty("file.separator")
            + "input"
            + System.getProperty("file.separator");
        assertEquals(expected, actual);
    }

}
