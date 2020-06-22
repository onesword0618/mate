package jp.gushed.mate.infrastructure.internal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * 内部で取り扱うプロパティファイルの検証クラス.
 * 
 * @author onesword0618
 *
 */
public class InternalPropertyTest {

    @DisplayName("読み込んだプロパティファイルからプロパティを取得すること")
    @ParameterizedTest(name = "{index} ==> key=''{0}''")
    @ValueSource(strings = { "initial.directory", "work.directory", "logs.directory", "log.file", "dbms", "1", "2", "3",
        "4" })
    public void bulk(final String keys) throws Exception {

        final Map<String, String> expected = new HashMap<>();
        expected.put("initial.directory", "Documents");
        expected.put("work.directory", "files");
        expected.put("logs.directory", "logs");
        expected.put("log.file", "output.log");
        expected.put("dbms", "mysql.properties");
        expected.put("1", "first.properties");
        expected.put("2", "second.properties");
        expected.put("3", "third.properties");
        expected.put("4", "fourth.properties");

        final var file = "initialize.properties";
        final var sut = InternalProperty.from(file).bulk();
        final var actual = sut.get(keys);
        assertEquals(expected.get(keys), actual);
    }

    @Test
    @DisplayName("標準出力に読み込んだプロパティファイルの一覧を出力すること")
    public void list() throws Exception {

        final var actual = new ByteArrayOutputStream();
        System.setOut(new PrintStream(actual));

        final var expected = new StringBuilder()
            .append("-- listing properties --").append(System.lineSeparator())
            .append("initial.directory=Documents").append(System.lineSeparator())
            .append("1=first.properties").append(System.lineSeparator())
            .append("2=second.properties").append(System.lineSeparator())
            .append("3=third.properties").append(System.lineSeparator())
            .append("4=fourth.properties").append(System.lineSeparator())
            .append("logs.directory=logs").append(System.lineSeparator())
            .append("dbms=mysql.properties").append(System.lineSeparator())
            .append("input.directory=input").append(System.lineSeparator())
            .append("log.file=output.log").append(System.lineSeparator())
            .append("work.directory=files").append(System.lineSeparator())
            .append("env.directory=env").append(System.lineSeparator()).toString();

        final var file = "initialize.properties";
        InternalProperty.from(file).list();
        assertEquals(expected, actual.toString());
    }

}
