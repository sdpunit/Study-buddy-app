package com.studybuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.studybuddy.search.CourseAdapter;
import com.studybuddy.search.Query;
import com.studybuddy.search.RBTree;
import com.studybuddy.search.SearchParser;
import com.studybuddy.search.Tokenizer;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    public static ArrayList<Course> courseList = new ArrayList<Course>();


    private ListView searchList;

    public RBTree courseTree = new RBTree();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);



        setupData();
        setupList();
        setupOnClickListener();
//        setupOnKeyListener();
    }

    private void searchWidgets(){
        // search for widgets
        SearchView searchView = (SearchView) findViewById(R.id.SearchInput);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query){
                // call search function
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText){
                // call search function
//                ArrayList<Course> results = new ArrayList<Course>();
//                for (Course course : courseList){
//                    if (course.getCourseName().toLowerCase().contains(newText.toLowerCase())){
//                        results.add(course);
//                    }
//                }
                boolean b = false;
                try {
                    int code = Integer.parseInt(newText);
                    ArrayList results = search(courseTree, newText);
                    b = true;
                } catch (NumberFormatException e){
                    // not a number
                    throw e;

                }
                return b;
            }
        });
    }

    private ArrayList search(RBTree tree, String query){
        // search for course
        Tokenizer tokenizer = new Tokenizer(query);
        SearchParser parser = new SearchParser(tokenizer);
        Query queryObj = parser.parseQuery();

        tree.searchByCourseCode(courseTree.root,Integer.toString(queryObj.getCode())); // temporary until we have a tree access

        return null;
    }

    private void setupData(){
        // call from tree and add to





    }

    private void setupList(){
        searchList = (ListView) findViewById(R.id.SearchList);
        CourseAdapter adaptor = new CourseAdapter(getApplicationContext(), R.layout.item_course, courseList);
        searchList.setAdapter(adaptor);


    }

    private void setupOnClickListener()
    {
        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                Course selectCourse = (Course) (searchList.getItemAtPosition(position));
                Intent showDetail = new Intent(getApplicationContext(), DetailActivity.class);
                showDetail.putExtra("id",selectCourse.getCourseCode());
                startActivity(showDetail);
            }
        });
    }

//    private void setupOnKeyListener()
//    {
//        searchList.setOnKeyListener((view, i, keyEvent) -> {
//            return false;
//        });
//    }

}