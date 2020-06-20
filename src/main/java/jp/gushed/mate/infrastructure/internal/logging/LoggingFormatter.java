package jp.gushed.mate.infrastructure.internal.logging;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UncheckedIOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import jp.gushed.mate.infrastructure.internal.SystemKey;

/**
 * {@docRoot}
 * 
 * ロギングを行う際の独自フォーマット。内部リソースのプロパティファイルに記載して利用する。
 * 
 * @author onesword0618
 *
 */
public class LoggingFormatter extends Formatter {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS");

    private static final Map<Level, String> LEVEL_MESSAGES = Collections.unmodifiableMap(

        new HashMap<Level, String>() {

            private static final long serialVersionUID = -4831326971448849122L;

            {
                put(Level.SEVERE, "SEVERE");
                put(Level.WARNING, "WARN");
                put(Level.INFO, "INFO");
                put(Level.CONFIG, "CONF");
                put(Level.FINE, "FINE");
                put(Level.FINER, "FINE");
                put(Level.FINEST, "FINE");
            }

        });

    /**
     * イベント発生時のログ出力の内容を定義する。
     */
    @Override
    public String format(final LogRecord record) {

        final var message = new StringBuilder();

        final var localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(record.getMillis()),
            ZoneId.systemDefault());

        message.append(FORMATTER.format(localDateTime));
        message.append(SystemKey.BLANK.value);

        message.append(LEVEL_MESSAGES.get(record.getLevel()));
        message.append(SystemKey.BLANK.value);

        final var sourceName = deriveSourceName(record);
        if (sourceName.isEmpty()) {
            message.append(record.getLoggerName());
            message.append(SystemKey.BLANK.value);
        } else {
            message.append(sourceName);
        }

        message.append(formatMessage(record));
        message.append(System.lineSeparator());

        if (record.getThrown() != null) {

            try (
                var stringWriter = new StringWriter();
                var printwriter = new PrintWriter(stringWriter)) {
                record.getThrown().printStackTrace(printwriter);
                printwriter.close();
                message.append(stringWriter.toString());
            } catch (IOException io) {
                throw new UncheckedIOException("Could not catch exception log messages. :", io);
            }
        }
        return message.toString();
    }

    private String deriveSourceName(final LogRecord record) {

        final var message = new StringBuilder();

        if (record.getSourceClassName() != null) {
            message.append(record.getSourceClassName());
            message.append(SystemKey.BLANK.value);

            if (record.getSourceMethodName() != null) {
                message.append(record.getSourceMethodName());
                message.append(SystemKey.BLANK.value);
            }
        }
        return message.toString();
    }

}
