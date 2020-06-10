package com.elasticsearch;

import com.elasticsearch.entity.Book;
import com.elasticsearch.repository.BookRepository;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

@SpringBootTest
class ElasticsearchApplicationTests {

    @Autowired
    BookRepository bookRepository;

    @Test
    public void ESRepositoryTest(){
        Book book=new Book();
        book.setId(2);
        book.setAuthor("lic");
        book.setBookName("Hello world2!");
        bookRepository.index(book);
    }

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void ESTemplateTest01(){
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchQuery("bookName","Hello"))//匹配条件
                .withIndices("lv1")//指定索引
                .withTypes("lv2")//指定类型
//                .withFields("id")//指定返回某一个属性
                .build();
        List<Book> list=elasticsearchTemplate.queryForList(searchQuery,Book.class);
        for(Book book:list) {
            System.out.println(book);
        }
    }

    @Test
    public void ESTemplateTest02(){
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.termQuery("bookName","Hello"))//匹配条件
                .withIndices("lv1")//指定索引
                .withTypes("lv2")//指定类型
                .build();
        List<Book> list=elasticsearchTemplate.queryForList(searchQuery,Book.class);
        for(Book book:list) {
            System.out.println(book);
        }
    }
}
