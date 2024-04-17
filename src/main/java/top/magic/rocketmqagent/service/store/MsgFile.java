package top.magic.rocketmqagent.service.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.magic.rocketmqagent.model.file.FileMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class MsgFile {
    private static final Logger LOGGER = LoggerFactory.getLogger(MsgFile.class);
    private volatile int wrotePosition;
    private static AtomicIntegerFieldUpdater<MsgFile> WROTE_POSITION_UPDATER;
    private final int fileSize;
    private String fileName;
    private File file;
    private FileChannel channel;
    private MappedByteBuffer mappedByteBuffer;
    private volatile int readPosition;
    private long fileFromOffset;

    static {
        WROTE_POSITION_UPDATER = AtomicIntegerFieldUpdater.newUpdater(MsgFile.class, "wrotePosition");
    }

    public MsgFile(String fileName, int fileSize) throws IOException {
        this.fileName = fileName;
        this.fileSize = fileSize;

        init(fileName, fileSize);
    }

    public void init(String fileName, int fileSize) throws IOException {
        boolean fileOpen = false;
        this.file = new File(fileName);
        this.fileFromOffset = Long.parseLong(fileName);
        try {
            this.channel = new RandomAccessFile(fileName, FileMode.WRITE_AND_READ).getChannel();
            this.mappedByteBuffer = this.channel.map(FileChannel.MapMode.READ_WRITE, 0, fileSize);

            fileOpen = true;
        } catch (FileNotFoundException e) {
            LOGGER.error("error get file channel fileName is {}, error is", fileName, e);
            throw e;
        } catch (IOException e) {
            LOGGER.error("error when mmap file, fileName is {}, error is ", fileName, e);
            throw e;
        } finally {
            if (fileOpen && this.channel != null) {
                this.channel.close();
            }
        }

    }

    public boolean getData(int position, int size, ByteBuffer byteBuffer) {
        if (position + size > WROTE_POSITION_UPDATER.get(this)) {
            LOGGER.error("position + size > wrotePosition, please check, position is {},size is {}", position, size);
            return false;
        }

        try {
            int read = this.channel.read(byteBuffer, position);
            return read == size;
        } catch (IOException e) {
            LOGGER.error("error when getData, position is {},error is ", position, e);
            return false;
        }
    }
    public boolean writeData(int position){

        return true;
    }


    public String getFileName() {
        return fileName;
    }

    public void setWrotePosition(int position) {
        WROTE_POSITION_UPDATER.set(this, position);
    }

    public long getFileFromOffset() {
        return fileFromOffset;
    }

    public boolean fileIsFull() {
        return WROTE_POSITION_UPDATER.get(this) == fileSize;
    }


    public int getFileSize() {
        return this.fileSize;
    }

}
