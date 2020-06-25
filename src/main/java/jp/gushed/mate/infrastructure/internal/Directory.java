package jp.gushed.mate.infrastructure.internal;

import java.util.List;
import java.util.Map;

/**
 * ディレクトリを構成する文字列を生成する.
 * 
 * @author onesword0618
 *
 */
class Directory {

    private final transient String directory;

    /**
     * 引数を元にディレクトリを作成する.
     * 
     * @param directories 組み合わせるディレクトリのリスト
     * @param property    プロパティ
     * @return Directoryのインスタンス
     */
    public static Directory valueOf(final List<String> directories, final Map<String, String> property) {
        return new Directory(directories, property);
    }

    private Directory(final List<String> directories, final Map<String, String> property) {
        this.directory = adaptBaseDirectory() + adaptWorkDirectory(property) + adjust(directories, property);
    }

    /**
     * directoryの値を返却する.
     * 
     * @return 区切り文字を含んだディレクトリの文字列
     */
    public String compose() {
        return directory;
    }

    @Override
    public String toString() {
        return "Directory [directory=" + directory + "]";
    }

    private String adaptBaseDirectory() {
        return new StringBuilder().append(System.getProperty(SystemKey.HOME.value))
            .append(System.getProperty(SystemKey.SEPARATOR.value)).toString();
    }

    private String adaptWorkDirectory(final Map<String, String> properties) {
        return adjust(List.of(FolderKey.INITIAL.value, FolderKey.WORK.value), properties);
    }

    private String adjust(final List<String> directories, final Map<String, String> properties) {
        final var composeDirectory = new StringBuilder();

        directories.stream().forEach(dir -> composeDirectory.append(properties.get(dir))
            .append(System.getProperty(SystemKey.SEPARATOR.value)));

        return composeDirectory.toString();
    }

}