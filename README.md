This project is a RESTful object storage based on my [Hawthorne-DB library.](https://github.com/George-Novikov/Hawthorne-DB)

It can be used as an example for your projects, extended with your code, or used right away since it's a ready-to-use application.  

The idea is — while being able to store complex objects and binaries, your web application is light-weight and easy to deploy.  

Hawthorne performs all data storage operations within your file system, without using any standalone SQL/NoSQL database.  

For instance, your @Service class can look like this:  
```java
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
```

This allows more flexibility while building your app — it might even seem like @Service class is not needed at all.  
<u>But I still recommend that you consider a "conventional" layering, having Controller, Service, and Repository layers.</u>  

This project also implements JWT authentication and authorization, based on Spring Security — see "security" package.  

REST API documentation is available after starting the project: http://{host}:8080/openapi  

[Learn more about Hawthorne library](https://github.com/George-Novikov/Hawthorne-DB/blob/master/README.md)  
