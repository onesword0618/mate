package jp.gushed.mate.infrastructure.internal;

enum FolderKey {

    INITIAL("initial.directory"), WORK("work.directory"), ENV("env.directory"), INPUT("input.directory"),
    LOGS("logs.directory");

    public String value;

    FolderKey(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

}
