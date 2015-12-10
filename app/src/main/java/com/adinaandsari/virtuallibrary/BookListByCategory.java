package com.adinaandsari.virtuallibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import entities.Book;
import entities.Category;
import model.datasource.BackendFactory;

public class BookListByCategory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list_by_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //set the list of the book
        initItemByListView();
    }

    private ArrayList<BookRow> bookRowList;

    class BookRow {
        String title,author;
        double rate;

        public BookRow(String title, String author, double rate) {
            this.title = title;
            this.author = author;
            this.rate = rate;
        }

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        public double getRate() {
            return rate;
        }
    }

    void initItem () throws Exception{
        bookRowList = new ArrayList<BookRow>();
        //get the category and set the list
        Intent intent = getIntent();
        Category category = Category.valueOf(intent.getStringExtra("Category from customer activity").toUpperCase(Locale.ENGLISH));
        ArrayList<Book> bookList = BackendFactory.getInstance().bookListSortedByCategory(category);
        for (Book b : bookList)
        {
            bookRowList.add(new BookRow(b.getBookName(),b.getAuthor(), b.getRateAVR()));
        }
    }

    void initItemByListView()
    {
        try {
            initItem();
            ListView listView = new ListView(this);
            ArrayAdapter<BookRow>  mAdapter = new ArrayAdapter<BookRow>(this,R.layout.row_of_book_layout,
                    bookRowList)
            {
                @Override
                public View getView(int position, View convertView, ViewGroup parent)
                {
                    if (convertView == null)
                    {
                        convertView = View.inflate(BookListByCategory.this,R.layout.row_of_book_layout,null);
                    }
                    TextView productTitleTextView = (TextView) convertView.findViewById(R.id.bookTitleTextView);
                    TextView productAuthorTextView = (TextView) convertView.findViewById(R.id.bookAuthorTextView);
                    TextView productRateTextView = (TextView) convertView.findViewById(R.id.bookRateTextView);

                    productTitleTextView.setText(bookRowList.get(position).getTitle());
                    productAuthorTextView.setText(bookRowList.get(position).getAuthor());
                    productRateTextView.setText(((Double)bookRowList.get(position).getRate()).toString());

                    return convertView;
                }
            };
            listView.setAdapter(mAdapter);
            this.setContentView(listView);
        }
        catch (Exception e){
            //print the exception in a toast view
            Toast.makeText(BookListByCategory.this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

}
