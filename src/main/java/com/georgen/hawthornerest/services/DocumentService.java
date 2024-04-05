package com.georgen.hawthornerest.services;

import com.georgen.hawthorne.api.Repository;
import com.georgen.hawthorne.model.exceptions.HawthorneException;
import com.georgen.hawthornerest.model.Document;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentService {
    public Document save(Document document) throws HawthorneException {
        return Repository.save(document);
    }

    public Document get(Long id) throws Exception {
        return Repository.get(Document.class, id);
    }

    public boolean delete(Long id) throws Exception {
        return Repository.delete(Document.class, id);
    }

    public List<Document> list(int limit, int offset) throws Exception {
        return Repository.list(Document.class, limit, offset);
    }

    public long count() throws Exception {
        return Repository.count(Document.class);
    }
}
