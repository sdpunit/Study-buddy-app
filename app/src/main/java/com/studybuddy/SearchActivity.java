package com.studybuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.studybuddy.search.CourseAdapter;
import com.studybuddy.search.Query;
import com.studybuddy.search.RBTree;
import com.studybuddy.search.SearchParser;
import com.studybuddy.search.Tokenizer;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    public static ArrayList<Course> courseList = new ArrayList<Course>();

    public static ArrayList<Course> addedList = new ArrayList<Course>();

    private ListView searchListView;

    private ListView addedListView;

    private Button addCourseButton;

    public RBTree courseTree = new RBTree();

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        user = getIntent().getSerializableExtra("user", User.class);

        setupData();
        setupList();
        setupOnClickListener();
        setupAdded();
        searchWidgets();

        addCourseButton = findViewById(R.id.btn_addCourses);

        addCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setCoursesEnrolled(addedList);
                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(String.valueOf(user.getUid()));
                userRef.setValue(user);
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
//        setupOnKeyListener();
    }

    private void searchWidgets(){
        // search for widgets
        SearchView searchView = findViewById(R.id.SearchInput);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String input){
                // call search function
                ArrayList<Course> results = search(courseTree, input);
                CourseAdapter adapter = new CourseAdapter(getApplicationContext(), 0, results);
                if(!results.isEmpty()){
                    searchListView.setAdapter(adapter);
                }

                return false;
            }
            @Override
            public boolean onQueryTextChange(String input){
                // call search function

                return false;
            }
        });
    }

    private ArrayList search(RBTree tree, String input){
        // search for course
        ArrayList results = new ArrayList(); //new array list
        Tokenizer tokenizer = new Tokenizer(input); // tokenize input
        SearchParser parser = new SearchParser(tokenizer); // parse tokens
        Query queryObj = parser.parseQuery(); // get query object
        if(queryObj == null){
            return results;
        }
        try {
            int code = queryObj.getCode();
            String name = queryObj.getCourse();
            String codeString = name + code;

            RBTree.Node resultCourse = tree.searchByCourseCode(tree.root, codeString); // temporary until we have a tree access
            if(resultCourse != null){
                results.add(resultCourse.getCourse());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return results;
    }

    private void setupData(){
        // call from tree and add to courseList
//        courseTree.insert(new Course("COMP1010", "Intro to Computer Science 1", "punit"));
//        courseTree.insert(new Course("COMP2160", "Software Engineering", "horatio"));
//        courseTree.insert(new Course("COMP2140", "Data Structures and Algorithms", "bernardo"));
//        courseTree.insert(new Course("COMP2080", "Intro to Computer Science 2", "punit"));
//
//        List<RBTree.Node> tree = courseTree.inOrderTraverse();
//                tree.forEach((n) -> {
//                    courseList.add(n.getCourse());
//                });
//        courseTree.createCourseTree();
    }

    private void setupList(){
        searchListView = findViewById(R.id.SearchList);
        CourseAdapter adaptor = new CourseAdapter(getApplicationContext(), 0, courseList);
        searchListView.setAdapter(adaptor);


    }

    private void setupAdded(){
        addedListView = findViewById(R.id.AddedList);
        CourseAdapter adaptor = new CourseAdapter(getApplicationContext(), 0, addedList);
        addedListView.setAdapter(adaptor);
    }

    private void setupOnClickListener()
    {
        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                Course selectCourse = (Course) (searchListView.getItemAtPosition(position));

                if(!addedList.contains(selectCourse)) {
                    addedList.add(selectCourse);
                }
                CourseAdapter adaptor = new CourseAdapter(getApplicationContext(), 0, addedList);
                addedListView.setAdapter(adaptor);
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