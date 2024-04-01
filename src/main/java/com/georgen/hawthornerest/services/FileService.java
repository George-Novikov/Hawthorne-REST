package com.georgen.hawthornerest.services;

import com.georgen.hawthorne.api.Repository;
import com.georgen.hawthorne.model.exceptions.HawthorneException;
import com.georgen.hawthornerest.model.FileContainer;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    public FileContainer save(String id, String description, MultipartFile file) throws IOException, HawthorneException {
        FileContainer fileContainer = new FileContainer(id, description, file);
        return Repository.save(fileContainer);
    }

    public FileContainer getFileMetadata(String id) throws Exception {
        return Repository.get(FileContainer.class, id);
    }

    public byte[] getFileBinaryData(String id) throws Exception {
        FileContainer fileContainer = Repository.get(FileContainer.class, id);
        return fileContainer.getBinaryData();
    }

    public boolean delete(String id) throws Exception {
        return Repository.delete(FileContainer.class, id);
    }

    public List<FileContainer> list(int limit, int offset) throws Exception {
        return Repository.list(FileContainer.class, limit, offset);
    }

    public long count() throws Exception {
        return Repository.count(FileContainer.class);
    }
}
