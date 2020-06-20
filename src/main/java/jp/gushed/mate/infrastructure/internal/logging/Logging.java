package jp.gushed.mate.infrastructure.internal.logging;

import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

/**
 * アプリケーションで扱うロギングの操作を行う.
 * 
 * @author onesword0618
 *
 */
public class Logging {

    private final Logger logger;

    /**
     * Loggerのインスタンスを生成する. 生成したインスタンスに主な
     * 
     * @see java.util.logging.Logger
     * 
     * @param clasz ロギング対象のクラス
     * @return Loggerのインスタンス
     */
    public static Logger newLogger(final Class<?> clasz) {
        return new Logging(clasz).logger;
    }

    private Logging(final Class<?> clasz) {
        this.logger = Logger.getLogger(clasz.getSimpleName());
        this.logger.addHandler(new ConsoleHandler());
        this.logger.setUseParentHandlers(false);
    }

    @Override
    public String toString() {
        return "Logging [logger=" + logger + "]";
    }

}
