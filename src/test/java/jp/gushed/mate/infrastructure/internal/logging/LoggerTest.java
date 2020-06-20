package jp.gushed.mate.infrastructure.internal.logging;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.logging.FileHandler;
import java.util.logging.LogManager;
import java.util.stream.Collectors;
import jp.gushed.mate.infrastructure.internal.SystemKey;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LoggerTest {

    @Test
    @DisplayName("ログファイルに指定のメッセージが出力されること")
    public void outputLoggingFile() throws SecurityException, IOException {

        final var property = Logging.class.getResourceAsStream("/logging.properties");
        LogManager.getLogManager().readConfiguration(property);

        final var fileHandler = new FileHandler("src/test/resources/test.log");
        final var logger = Logging.newLogger(this.getClass());
        logger.addHandler(fileHandler);
        logger.info("Unit Testing.");

        // 本来は、1970/01/01 09:00:01.000 INFO ...
        // ログファイルの書き込みで、時刻の内部処理をしているみたいなので、外部からモックを差し込むことできない.
        // 日付を比較するのは他の試験ですることにした.
        final var expected = "INFO jp.gushed.mate.infrastructure.internal.logging.LoggerTest outputLoggingFile Unit Testing."
            + System.lineSeparator();

        final var sut = Files.readString(Paths.get("src/test/resources/test.log"));
        final var actual = Arrays.stream(sut.split(SystemKey.BLANK.value)).skip(2)
            .collect(Collectors.joining(SystemKey.BLANK.value));

        assertEquals(expected, actual);
    }

}
