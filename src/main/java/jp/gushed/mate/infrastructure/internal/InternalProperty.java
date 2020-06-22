package jp.gushed.mate.infrastructure.internal;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 内部で持ち回る設定ファイルを操作する.
 * 
 * @author onesword0618
 *
 */
class InternalProperty {

    private Properties property = new Properties();

    /**
     * リソースディレクトリのプロパティファイルを読み込む.
     * 
     * @param propertyFile 対象のプロパティファイル名
     * @return InternalProperty リソースのプロパティファイル
     * @throws IOException リソースに問題がある場合
     */
    public static InternalProperty from(final String propertyFile) throws IOException {
        return new InternalProperty(propertyFile);
    }

    private InternalProperty(final String propertyFile) throws IOException {
        final var internalProperty = InternalProperty.class.getResourceAsStream(SystemKey.SLASH.value + propertyFile);

        final var internalReader = new InputStreamReader(internalProperty);
        try (internalReader) {
            property.load(internalReader);
        } catch (IOException ioe) {
            throw new IOException("property: " + property + " can't reading." + ioe);
        }
    }

    /**
     * 保持しているプロパティの一覧を返却する.
     * 
     * @return key=valueのmapを返却
     */
    public Map<String, String> bulk() {
        final var keys = property.stringPropertyNames();
        return keys.stream().collect(Collectors.toMap(Function.identity(), key -> read(key)));
    }

    /**
     * 保持しているプロパティの一覧を標準出力する.
     */
    public void list() {
        property.list(System.out);
    }

    private String read(final String key) {
        return property.getProperty(key);
    }

}
