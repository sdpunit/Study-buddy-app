package com.studybuddy.bathtub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.studybuddy.R;
import com.studybuddy.search.Colleges;
import com.studybuddy.search.CourseAdapter;
import com.studybuddy.search.Query;
import com.studybuddy.search.RBTree;
import com.studybuddy.search.SearchParser;
import com.studybuddy.search.Tokenizer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    public static ArrayList<Course> courseList = new ArrayList<Course>();

    public static ArrayList<Course> addedList = new ArrayList<Course>();

    private CourseAdapter listAdapter;

    private CourseAdapter addedAdapter;

    private ListView searchListView;

    private ListView addedListView;

    private Button addCourseButton;

    public RBTree courseTree = new RBTree();

    private User user;

    private HashMap<String, RBTree> collegeTreeMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        user = getIntent().getSerializableExtra("user", User.class);

        if(collegeTreeMap == null){
            collegeTreeMap = getCollegeTreeMap();
        }

        try {
            setupData();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setupList();
        setupOnClickListener();
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
    }

    public static String subStringBetween(String input, String to, String from) {
        return input.substring(input.indexOf(to)+1, input.lastIndexOf(from));
    }

    private void searchWidgets(){
        // search for widgets
        SearchView searchView = findViewById(R.id.SearchInput);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() { // "math (" fails
            ArrayList<Course> results = new ArrayList<Course>();
            @Override
            public boolean onQueryTextSubmit(String input){
                if(!input.contains("(") && !input.contains(")") ) {
                    // call search function
                    results = search(input.toLowerCase());
                    courseList.clear();
                    courseList.addAll(results);
                }
                listAdapter.notifyDataSetChanged();
                searchListView.setAdapter(listAdapter);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String input){
                // call search function)
                if(input.isEmpty()){
                    courseList.clear();
                    courseList.addAll(getCourses());
                    listAdapter.notifyDataSetChanged();
                    searchListView.setAdapter(listAdapter);
                }
                results.clear(); // has to reset results so that the live update functionality works

                if(input.contains("(") && input.contains(")") && input.indexOf("(") < input.indexOf(")")){
                    String s = subStringBetween(input, "(", ")"); // checks for string inbetween parenthesis
                    String[] strs =s.toLowerCase().split(" ");
                    for(Course course : courseList){
                        for (String str : strs) {
                            if (course.getCourseName().toLowerCase().contains(str)) {
                                results.add(course);
                            }
                            CourseAdapter adapter = new CourseAdapter(getApplicationContext(), 0, results);
                            searchListView.setAdapter(adapter);
                        }
                    }
                }
                return false;
            }
        });
    }

    /**
     * Searches through course trees to find the input
     * @param input course to search for
     * @return list of courses that match the input
     * @author Steven and Lana
     */
    private ArrayList search(String input){
        // initialises parameters
        ArrayList<Course> results = new ArrayList();
        Tokenizer tokenizer = null;
        Query queryObj = null;
        RBTree collegeTree = null;

        // tokenize and create query object
        try {
            tokenizer = new Tokenizer(input); // tokenize input
            SearchParser parser = new SearchParser(tokenizer); // parse tokens
            queryObj = parser.parseQuery(); // get query object
        }
        // catch errors
        catch (IllegalArgumentException e) {
            Toaster.showToast(getApplicationContext(), "Invalid token");
        }
        catch (Exception e) {
            Toaster.showToast(getApplicationContext(), "Invalid search query");
        }

        try {
            collegeTree = collegeTreeMap.get(queryObj.getCollege());
            if (collegeTree == null) {
                Toaster.showToast(getApplicationContext(), "Invalid search query");
                return results;
            }
        } catch (NullPointerException e) {
            Toaster.showToast(getApplicationContext(), "Invalid search query");
            return results;
        }

        // searches for course code, will return null if a course or code is not present
        RBTree.Node found = collegeTree.searchByCourseCode(collegeTree.root,queryObj.getCollege() + queryObj.getCode());
        // boolean indicating what parameters are included in the query object
        boolean college = queryObj.getCollege()!=null;
        boolean code = queryObj.getCode()!=0;
        boolean course = queryObj.getCourse()!=null;
        boolean convener = queryObj.getConvener()!=null;

        if (college) {
            if (code) { //if college && code returns found
                if (found != null) {
                    results.add(found.getCourse());
                } else {
                    Toaster.showToast(getApplicationContext(), "Course not found" + " college= " + queryObj.getCollege() + " code= " +queryObj.getCode());
                }
            }
            else if (course || convener) {
                for (RBTree.Node n :collegeTree.inOrderTraverse()) {
                    courseList.add(n.getCourse());
                }
                if (course) { //if college && course, iterates though course list to find query
                    for(Course c : courseList){
                        if (c.getCourseName().toLowerCase().contains(queryObj.getCourse()) && !results.contains(c)) {
                            results.add(c);
                        }
                    }
                }
                else { //if college && convener, iterates though course list to find query
                    for(Course c : courseList){
                        if (c.getConvener().toLowerCase().contains(input.split("=")[1].trim())) {
                            results.add(c);
                        }
                    }
                }
                return results;
            }
            else { //if college preforms inOrderTraversal of a course tree
                collegeTree.inOrderTraverse().forEach((n) -> {
                    results.add(n.getCourse());
                });
            }
        }
        return results;
    }


    /**
     * Sets up data for initial array adapter
     * @author Steven
     */
    private void setupData() throws JSONException, IOException {
        courseList = getCourses();
    }

    private void setupList(){
        searchListView = findViewById(R.id.SearchList);
        listAdapter = new CourseAdapter(getApplicationContext(), 0, courseList);
        searchListView.setAdapter(listAdapter);

        addedListView = findViewById(R.id.AddedList);
        addedAdapter = new CourseAdapter(getApplicationContext(), 0, addedList);
        addedListView.setAdapter(addedAdapter);
    }

    private void setupOnClickListener()
    {
        searchListView.setOnItemClickListener((adapterView, view, position, l) -> {
            Course selectCourse = (Course) (searchListView.getItemAtPosition(position));

            if(!addedList.contains(selectCourse)) {
                addedList.add(selectCourse);
            }
            addedAdapter.notifyDataSetChanged();
            addedListView.setAdapter(addedAdapter);
        });

        addedListView.setOnItemClickListener((adapterView, view, position, l) -> {
            Course selectCourse = (Course) (addedListView.getItemAtPosition(position));

            if (addedList.contains(selectCourse)){
                addedList.remove(selectCourse);
            }
            addedAdapter.notifyDataSetChanged();
            addedListView.setAdapter(addedAdapter);
        });
    }


    /**
     * Creates a list of courses of one college
     * @param college college name such as comp
     * @return list of courses
     * @author Yanghe, Lana and Steven
     */
    public List<Course> getCollegeCourses(String college) throws JSONException {
        // reading json based on student type
        String path = "post_courses_data.json";
        if(user.getIsUndergrad()){
            path = "under_courses_data.json";
        }
        List<Course> courses = new ArrayList<>();
        try {
            InputStream inputStream = getAssets().open(path);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            // Convert the contents to a string
            String jsonString = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray collegeArray = jsonObject.getJSONArray(college.toUpperCase());

            for (int i = 0; i < collegeArray.length(); i++) {
                JSONObject courseJson = collegeArray.getJSONObject(i);
                //sets code
                String courseCode = courseJson.getString("course_code");
                //sets name
                String courseName = courseJson.getString("course_name");
                //sets student type
                String studentTypeOfCourse = courseJson.getString("student_type");
                //sets assessment
                ArrayList<String> assessment = new ArrayList<>();
                JSONArray assessmentArray = courseJson.getJSONArray("assessment");
                for (int j = 0; j < assessmentArray.length(); j++) {
                    assessment.add(assessmentArray.getString(j));
                }
                //sets convener
                String convener = "";
                JSONArray convenerArray = courseJson.getJSONArray("convener");
                for (int j = 0; j < convenerArray.length(); j++) {
                    convener+=(convenerArray.getString(j))+", ";
                    if (j==convenerArray.length()-1) {convener+=(convenerArray.getString(j));}
                }
                if (convener.length() == 0) {
                    convener+=("No convener");
                }
                Course newCourse = new Course(courseCode, courseName, studentTypeOfCourse, assessment, convener);
                courses.add(newCourse);
            }
            return courses;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return courses;
    }

    /**
     * Creates a course tree for a specified course
     * @param course college name such as comp
     * @return list of courses
     * @author Lana and Steven
     */
    public RBTree createCourseTree(String course) throws JSONException, IOException {
        RBTree tree = new RBTree();
        for (Course c:getCollegeCourses(course)) {
            tree.insert(c);
        }
        return tree;
    }


    /**
     * Creates a hashmap of college names and their respective trees.
     */
    public HashMap<String, RBTree> getCollegeTreeMap () {
        HashMap<String, RBTree> collegeTreeMap = new HashMap<>();
        Colleges colleges = new Colleges();
        for (String college:colleges.colleges) {
            try {
                collegeTreeMap.put(college, createCourseTree(college));
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
        }
        return collegeTreeMap;
    }

    /**
     * Creates a list of courses based on the Users student type
     * @return list of courses
     * @author Lana
     */
    public ArrayList<Course> getCourses() {
        // reading json based on student type
        String path = "post_courses_data.json";
        if(user.getIsUndergrad()){
            path = "under_courses_data.json";
        }
        //initialises a list of Courses
        ArrayList courses = new ArrayList<Course>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open(path), StandardCharsets.UTF_8))) {
            String line;
            boolean ass = false; // boolean to indicate if we are searching for assessments
            boolean conv = false;  // boolean to indicate if we are searching for conveners
            // initialising parameters to create a new Course
            Course c = new Course();
            ArrayList assessments = new ArrayList<String>();
            String conveners = "";

            while ((line = reader.readLine()) != null) {
                //setting code
                if (line.contains("\"course_code\":")) {
                    String s = line.substring(22,30);
                    c.setCourseCode(s);
                }
                //setting name
                else if (line.contains("\"course_name\":")) {
                    String s = line.substring(22).split("\"")[0];
                    c.setCourseName(s);
                }
                //setting student type
                else if (line.contains("\"student_type\":")) {
                    String s = line.substring(23).split("\"")[0];
                    c.setStudentType(s);
                }
                //setting assessment
                else if(line.contains("\"assessment\": []")) {
                    c.setAssessment(null);
                }
                else if (ass && line.contains("],")) {
                    ass=false;
                    c.setAssessment(assessments);
                    assessments = new ArrayList<>();
                }
                else if (ass) {
                    String[] s = line.split("\"");
                    assessments.add(s[1]);
                }
                else if (line.contains("\"assessment\":")) {
                    ass=true;
                }
                //setting convener
                else if(line.contains("\"convener\": []")) {
                    c.setConvener(null);
                    if (c.getCourseCode()!=null) {
                        courses.add(c);}
                    c = new Course();
                    conv=false;
                }
                else if (conv && line.contains("]")) {
                    conv=false;
                    c.setConvener(conveners);
                    conveners = "";
                    if (c.getCourseCode()!=null) {
                        courses.add(c);}
                    c = new Course();
                }
                else if (conv) {
                    String[] s = line.split("\"");
                    conveners+=(s[1]);
                    if (line.contains(",")) {
                        conveners+=", ";
                    }
                }
                else if (line.contains("\"convener\":")) {
                    conv=true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return courses;
    }

}

