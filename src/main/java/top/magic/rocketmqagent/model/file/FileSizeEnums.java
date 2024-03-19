package top.magic.rocketmqagent.model.file;

/**
 * @author = wangyilin29
 * @date = 2024/3/19
 **/
public enum FileSizeEnums {
    BYTE("byte") {
    },
    KB("KB") {

    },
    MB("MB") {

    },
    GB("GB") {
        @Override
        public int toByte(int count) {
            return super.toByte(count);
        }

        @Override
        public int toMB(int count) {
            return count * 1024;
        }

        @Override
        public int toKB(int count) {
            return count * 1024 * 1024;
        }
    };

    private String name;

    FileSizeEnums(String name) {
        this.name = name;
    }

    public int toByte(int count) {
        return count;
    }

    public int toMB(int count) {
        return count;
    }

    public int toGB(int count) {
        return count;
    }

    public int toKB(int count) {
        return count;
    }
}
