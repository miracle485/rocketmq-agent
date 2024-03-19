package top.magic.rocketmqagent.service.store;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.magic.rocketmqagent.util.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MsgFileQueue {
    private static final Logger LOGGER = LoggerFactory.getLogger(MsgFileQueue.class);
    private CopyOnWriteArrayList<MsgFile> msgFiles;
    private final String filePath;
    private final int fileSize;

    public MsgFileQueue(String filePath, int fileSize) {
        this.filePath = filePath;
        this.fileSize = fileSize;

    }

    public boolean loadFiles() {
        if (StringUtils.isBlank(filePath)) {
            return false;
        }
        FileUtils.checkAndCreateFilePath(filePath);
        File file = new File(filePath);
        File[] files = file.listFiles();
        if (files != null) {
            return doLoad(Arrays.asList(files));
        }
        return true;
    }

    public boolean doLoad(List<File> files) {
        if (CollectionUtils.isEmpty(files)) {
            return false;
        }
        files.sort(Comparator.comparing(File::getName));
        for (File file : files) {
            if (file.isDirectory()) {
                continue;
            }
            try {
                MsgFile msgFile = new MsgFile(file.getName(), fileSize);
                msgFiles.add(msgFile);
            } catch (IOException e) {
                LOGGER.error("error when load File, file name is {}, error is ", file.getName(), e);
                return false;
            }
        }

        return true;
    }

    public MsgFile getFileByPos(int pos) {
        if (pos < 0) {
            return null;
        }
        int index = pos % fileSize;
        MsgFile[] files = msgFiles.toArray(new MsgFile[0]);
        return index >= files.length ? null : files[index];
    }


    public MsgFile getLastMsgFile() {
        MsgFile[] msgFilesList = this.msgFiles.toArray(new MsgFile[0]);
        return msgFilesList.length == 0 ? null : msgFilesList[msgFilesList.length - 1];
    }

    public MsgFile createFile(long offset) {
        if (offset < 0) {
            return null;
        }
        long startOffset = -1;

        MsgFile lastMsgFile = getLastMsgFile();
        if (lastMsgFile == null) {
            startOffset = 0;
        }
        if (lastMsgFile != null && lastMsgFile.fileIsFull()) {
            startOffset = lastMsgFile.getFileFromOffset() + fileSize;
        }
        return tryCreateFile(startOffset);
    }

    public MsgFile tryCreateFile(long offset) {
        if (offset < 0) {
            return null;
        }
        String path = filePath + File.separator + FileUtils.formatFileName(offset);
        MsgFile msgFile = null;
        try {
            msgFile = new MsgFile(path, fileSize);
            this.msgFiles.add(msgFile);
        } catch (IOException e) {
            LOGGER.error("error when createNextFile, FileName is {},error is ", filePath, e);
        }
        return msgFile;
    }


}
