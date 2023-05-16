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

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
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

        try {
            setupData();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    private void setupData() throws JSONException, IOException {
        RBTree t = createTree();
        int i =1;
        //createCourseTree("undergrad","comp");

        // call from tree and add to courseList
//        courseTree.insert(new Course("COMP1010", "Intro to Computer Science 1", "punit"));
//        courseTree.insert(new Course("COMP2160", "Software Engineering", "horatio"));
//        courseTree.insert(new Course("COMP2140", "Data Structures and Algorithms", "bernardo"));
//        courseTree.insert(new Course("COMP2080", "Intro to Computer Science 2", "punit"));
//

        //createCourseTree("undergrad","comp");
//        List<RBTree.Node> tree = courseTree.inOrderTraverse();
//        tree.forEach((n) -> {
//            courseList.add(n.getCourse());
//        });
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




    ///

    public ArrayList<Course> getCourses(String studentType) throws IOException, JSONException {
        String path = "post_courses_data.json";
        if(studentType.equals("undergrad")){
            path = "under_courses_data.json";
        }

        ArrayList undergrads = new ArrayList<Course>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open(path), StandardCharsets.UTF_8))) {
            String line;
            boolean ass = false;
            boolean conv = false;

            ArrayList assessments = new ArrayList<String>();
            String conveners = "";
            Course c = new Course();
            while ((line = reader.readLine()) != null) {
                if (line.contains("\"course_code\":")) {
                    String s = line.substring(22,30);
                    c.setCourseCode(s);
                }
                if (line.contains("\"course_name\":")) {
                    String s = line.substring(22).split("\"")[0];
                    c.setCourseName(s);
                }
                if (line.contains("\"student_type\":")) {
                    String s = line.substring(23).split("\"")[0];
                    c.setStudentType(s);
                }
                if (ass && line.contains("],")) {
                    ass=false;
                    assessments = new ArrayList<>();
                    c.setAssessment(assessments);
                }
                if (ass) {
                    String[] s = line.split("\"");
                    if (s.length>1){
                        assessments.add(s[1]);
                    }
                }
                if (line.contains("\"assessment\":")) {  //need to fix
                    ass=true;
                    //c.setStudentType(line.split(" ")[1]);
                }
                if(line.contains("\"convener\": []")) {
                    c.setConvener(null);
                    if (c.getCourseCode()!=null) {
                    undergrads.add(c);}
                    c= new Course();
                    conv=false;
                }
                if(line.contains("\"convener\":") && !line.contains("]")) {
                    conv=true;
                }
                if (conv) {
                    conveners+=(line);

                    undergrads.add(c);
                    c= new Course();
                    conv=false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return undergrads;
    }

    public ArrayList<Course> getCollegeCourses(String studentType, String course) throws IOException, JSONException {
        String path = "post_courses_data.json";
        if(studentType.equals("undergrad")){
            path = "under_courses_data.json";
        }

        ArrayList undergrads = new ArrayList<Course>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open(path), StandardCharsets.UTF_8))) {
            String line;
            boolean ass = false;
            boolean conv = false;

            ArrayList assessments = new ArrayList<String>();
            String conveners = "";
            Course c = new Course();
            while ((line = reader.readLine()) != null) {
                if (line.contains("\"course_code\":")) {
                    String s = line.substring(22,30);
                    c.setCourseCode(s);
                }
                if (line.contains("\"student_type\":")) {
                    String s = line.substring(23).split("\"")[0];
                    c.setStudentType(s);
                }
                if (ass && line.contains("],")) {
                    ass=false;
                    assessments = new ArrayList<>();
                    c.setAssessment(assessments);
                }
                if (ass) {
                    String[] s = line.split("\"");
                    if (s.length>1){
                        assessments.add(s[1]);
                    }
                }
                if (line.contains("\"assessment\":")) {  //need to fix
                    ass=true;
                    //c.setStudentType(line.split(" ")[1]);
                }
                if(line.contains("\"convener\": []")) {
                    c.setConvener(null);
                    if (c.getCourseCode()!=null && c.getCourseCode().contains(course.toUpperCase())) {
                        undergrads.add(c);
                        c= new Course();
                        conv=false;
                    }
                }
                if(line.contains("\"convener\":") && !line.contains("]")) {
                    conv=true;
                }
                if (conv) {
                    conveners+=(line);
                    if (c.getCourseCode()!=null && c.getCourseCode().contains(course.toUpperCase())) {
                        undergrads.add(c);
                        c= new Course();
                        conv=false;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return undergrads;
    }

    public RBTree createTree() throws JSONException, IOException {
        String studentType = "postgrad";
        if(user.getIsUndergrad()) {
            studentType = "undergrad";
        }
        RBTree tree = new RBTree();
        for (Course course:getCourses(studentType)) {
            tree.insert(course);
        }
        return tree;
    }

    public RBTree createCourseTree(String studentType, String course) throws JSONException, IOException {
        RBTree tree = new RBTree();
        for (Course c:getCollegeCourses(studentType,course)) {
            tree.insert(c);
        }
        return tree;
    }

//    private void setupOnKeyListener()
//    {
//        searchList.setOnKeyListener((view, i, keyEvent) -> {
//            return false;
//        });
//    }

}