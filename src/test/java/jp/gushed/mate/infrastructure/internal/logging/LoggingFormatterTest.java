package jp.gushed.mate.infrastructure.internal.logging;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class LoggingFormatterTest {

    @Mock
    private LogRecord mockLogRecord;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("ログメッセージが出力されること")
    public void outputFormatMessage() {

        when(mockLogRecord.getMillis()).thenReturn(Long.parseLong("1000"));
        when(mockLogRecord.getLevel()).thenReturn(Level.INFO);
        when(mockLogRecord.getSourceClassName()).thenReturn("UnitClass");
        when(mockLogRecord.getSourceMethodName()).thenReturn("callUnitTest");
        when(mockLogRecord.getMessage()).thenReturn("call UnitTestMessage");

        final var expected = "1970/01/01 09:00:01.000 INFO UnitClass callUnitTest call UnitTestMessage"
            + System.lineSeparator();

        final var sut = new LoggingFormatter();
        final var actual = sut.format(mockLogRecord);

        assertEquals(expected, actual);

    }

    @Test
    @DisplayName("クラス名、メソッド名が取得できない場合のログメッセージが出力されること")
    public void outputMissingClassAndMethodNameFormatMessage() {

        when(mockLogRecord.getMillis()).thenReturn(Long.parseLong("1000"));
        when(mockLogRecord.getLevel()).thenReturn(Level.INFO);
        when(mockLogRecord.getLoggerName()).thenReturn("jp.gushed.unit.Test");
        when(mockLogRecord.getMessage()).thenReturn("call UnitTestMessage");

        final var expected = "1970/01/01 09:00:01.000 INFO jp.gushed.unit.Test call UnitTestMessage"
            + System.lineSeparator();

        final var sut = new LoggingFormatter();
        final var actual = sut.format(mockLogRecord);

        assertEquals(expected, actual);

    }

    @Test
    @DisplayName("メソッド名が取得できない場合のログメッセージが出力されること")
    public void outputMissingMethodNameFormatMessage() {
        when(mockLogRecord.getMillis()).thenReturn(Long.parseLong("1000"));
        when(mockLogRecord.getLevel()).thenReturn(Level.INFO);
        when(mockLogRecord.getSourceClassName()).thenReturn("UnitClass");
        when(mockLogRecord.getMessage()).thenReturn("call UnitTestMessage");

        final var expected = "1970/01/01 09:00:01.000 INFO UnitClass call UnitTestMessage"
            + System.lineSeparator();

        final var sut = new LoggingFormatter();
        final var actual = sut.format(mockLogRecord);

        assertEquals(expected, actual);

    }

    @Test
    @DisplayName("例外処理の発生時のログメッセージが出力されること")
    public void outputExceptionFormatMessage() {
        when(mockLogRecord.getMillis()).thenReturn(Long.parseLong("1000"));
        when(mockLogRecord.getLevel()).thenReturn(Level.INFO);
        when(mockLogRecord.getSourceClassName()).thenReturn("UnitClass");
        when(mockLogRecord.getSourceMethodName()).thenReturn("callUnitTest");
        when(mockLogRecord.getThrown()).thenReturn(new IllegalStateException("状態が不正です"));
        when(mockLogRecord.getMessage()).thenReturn("call UnitTestMessage");

        final var expected = List.of("1970/01/01 09:00:01.000 INFO UnitClass callUnitTest call UnitTestMessage",
            "java.lang.IllegalStateException: 状態が不正です");

        final var sut = new LoggingFormatter();
        final var actual = List.of(sut.format(mockLogRecord).split(System.lineSeparator())).subList(0, 2);

        assertLinesMatch(expected, actual);
    }

}
