package com.example.roomdemo;

import android.content.Context;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.roomdemo.dao.BookDao;
import com.example.roomdemo.dao.BookDataBase;
import com.example.roomdemo.dao.BookTypeDao;
import com.example.roomdemo.entity.Book;
import com.example.roomdemo.entity.BookType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author ： HuYajun <huyajun0707@gmail.com>
 * @version ： 1.0
 * @date ： 2019-12-16 16:39
 * @depiction ：
 */

@RunWith(AndroidJUnit4.class)
public class SimpleEntityReadWriteTest {

    private BookDao mBookDao;
    private BookDataBase mDb;
    private BookTypeDao mBookTypeDao;


    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, BookDataBase.class).build();
        mBookDao = mDb.bookDao();
        mBookTypeDao = mDb.bookTypeDao();
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void writeUserAndReadInList() throws Exception {
        Book book = new Book(1);
        book.setName("测试");
        book.setType_id(1);
        book.setPrice(5.0f);
        BookType bookType = new BookType();
        bookType.setName("测试");
        mBookTypeDao.insertAll(new BookType[]{bookType});
        BookType bookType1 = mBookTypeDao.findBookTypeByName(bookType.getName())[0];
        List<Book> books = new ArrayList<>();
        book.setType_id(bookType1.getId());
        books.add(book);
        mBookDao.insertAll(books);
        Book book1 = mBookDao.findBookByName("测试")[0];
        assertThat(book1, equalTo(book));
    }

}
