package com.studybuddy.bathtub;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * This class is the activity for the search page.
 * @author Steven (u7108792), Lana (u7103031)
 * @feature Search, [Search-Filter], [Search-Invalid], [Interact-Follow]
 */
public class SearchActivity extends AppCompatActivity {

    public static ArrayList<Course> courseList = new ArrayList<>();

    public static ArrayList<Course> addedList = new ArrayList<>();

    private CourseAdapter listAdapter;

    private CourseAdapter addedAdapter;

    private ListView searchListView;

    private ListView addedListView;

    private User user;

    private HashMap<String, RBTree> collegeTreeMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        user = getIntent().getSerializableExtra("user", User.class);


        try {
            setupData();
        } catch (JSONException | IOException e) {
            throw new RuntimeException(e);
        }
        setupList();
        setupOnClickListener();
        searchWidgets();

        Button addCourseButton = findViewById(R.id.btn_addCourses);

        addCourseButton.setOnClickListener(view -> {
            user.setCoursesEnrolled(addedList);
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(String.valueOf(user.getUid()));
            userRef.setValue(user);
            Intent intent = new Intent(SearchActivity.this, MainActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        });
    }

    /**
     * This method adds menu context bar to activity to be used as filterable.
     * @param menu the menu
     * @return true if the menu is created, false otherwise
     * @author Steven (u7108792)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.filter_menu, menu);
        return true;
    }

    /**
     * This method delegates take for each menu item.
     * @param item the menu item
     * @return true if the menu item is selected, false otherwise
     * @author Steven (u7108792)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.available_courses:
                courseList.addAll(getAvailableCourses());
                listAdapter.notifyDataSetChanged();
                searchListView.setAdapter(listAdapter);
                return true;


            case R.id.all_courses:
                courseList.clear();
                courseList.addAll(getCourses());
                listAdapter.notifyDataSetChanged();
                searchListView.setAdapter(listAdapter);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This method changes the data for the search page based on filter.
     * @author Steven (u7108792)
     */
    private ArrayList<Course> getAvailableCourses() {
        ArrayList<Course> availableCourses = new ArrayList<>();
        for (Course course : courseList) {
            if (course.getConvener() != null) {
                availableCourses.add(course);
            }
        }
        courseList.clear();
        return availableCourses;
    }

    public static String subStringBetween(String input, String to, String from) {
        return input.substring(input.indexOf(to)+1, input.lastIndexOf(from));
    }

    /**
     * search for courses on text change or submit
     * @author Steven (u7108792)
     */
    private void searchWidgets(){
        // search for widgets
        SearchView searchView = findViewById(R.id.SearchInput);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            ArrayList<Course> results = new ArrayList<>();
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
                    String s = subStringBetween(input, "(", ")"); // checks for string in between parenthesis
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
            @Override
            public boolean onQueryTextSubmit(String input){
                if(!input.contains("(") && !input.contains(")") ) {
                    // call search function
                    results = search(input.toLowerCase());
                    courseList.clear();
                    courseList.addAll(results);
                    listAdapter.notifyDataSetChanged();
                    searchListView.setAdapter(listAdapter);
                }
                return false;
            }
        });
    }

    /**
     * Searches through course trees to find the input
     * @param input course to search for
     * @return list of courses that match the input
     * @author Steven (u7108792) and Lana (u7103031) 
     */
    private ArrayList<Course> search(String input){
        // initialises parameters
        ArrayList<Course> results = new ArrayList<>();
        Tokenizer tokenizer;
        Query queryObj = null;
        RBTree collegeTree;

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
            assert queryObj != null;
            collegeTree = collegeTreeMap.get(queryObj.getCollege());

        } catch (NullPointerException e) {
            Toaster.showToast(getApplicationContext(), "Invalid search query");
            return results;
        }

        // searches for course code, will return null if a course or code is not present

        // boolean indicating what parameters are included in the query object
        boolean college = queryObj.getCollege()!=null;
        boolean code = queryObj.getCode()!=0;
        boolean course = queryObj.getCourse()!=null;
        boolean convener = queryObj.getConvener()!=null;

        if (college) {
            if (code) { //if college && code, searches through college tree
                assert collegeTree != null;
                RBTree.Node found = collegeTree.searchByCourseCode(collegeTree.root,queryObj.getCollege() + queryObj.getCode());
                if (found != null) {
                    results.add(found.getCourse());
                } else {
                    Toaster.showToast(getApplicationContext(), "Course not found" + " college= " + queryObj.getCollege() + " code= " +queryObj.getCode());
                }
            }
            else if (course || convener) {
                assert collegeTree != null;
                for (RBTree.Node n :collegeTree.inOrderTraverse()) {
                    courseList.add(n.getCourse());
                }
                if (course && convener) { //if college && course && convener, iterates though course list to find query
                    for(Course c : courseList){
                        if (c.getConvener()!=null && c.getCourseName().toLowerCase().contains(queryObj.getCourse()) && c.getConvener().toLowerCase().contains(queryObj.getConvener()) && !results.contains(c)) {
                            results.add(c);
                        }
                    }
                }
                else if (course) { //if college && course, iterates though course list to find query
                    for(Course c : courseList){
                        if (c.getCourseName().toLowerCase().contains(queryObj.getCourse()) && !results.contains(c)) {
                            results.add(c);
                        }}
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
                assert collegeTree != null;
                collegeTree.inOrderTraverse().forEach((n) -> results.add(n.getCourse()));
            }
        }
        else if (code) { // if just code , iterates though course list to find query
            for(Course c : courseList){
                if (!results.contains(c) && c.getCourseCode().contains(""+queryObj.getCode())) {
                    results.add(c);
                }
            }

        }
        else if (course) {
            if (convener) { // if course && convener, iterates though course list to find query
                for(Course c : courseList){
                    if (c.getConvener()!=null
                            && c.getConvener().toLowerCase().contains(input.split("=")[1].trim())
                            && c.getCourseName().toLowerCase().contains(queryObj.getCourse())
                            && !results.contains(c)) {
                        results.add(c);
                    }
                }
            }
            else { //if just course, iterates though course list to find query
                for(Course c : courseList){
                    if (c.getCourseName().toLowerCase().contains(queryObj.getCourse()) && !results.contains(c)) {
                        results.add(c);
                    }
                }
            }
        }
        return results;
    }


    /**
     * Sets up data for initial array adapter
     * @author Steven (u7108792)
     */
    private void setupData() throws JSONException, IOException {
        courseList = getCourses();
        if(collegeTreeMap == null){
            collegeTreeMap = getCollegeTreeMap();
        }
    }


    /**
     * Sets up both list views for the search and added courses
     * Sets up the adapters for both list views
     * @author Steven (u7108792)
     */
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
            if(addedList.size()==0){
                Toaster.showToast(getApplicationContext(), "Click on the course again to remove it from the list");
            }

            if(!addedList.contains(selectCourse)) {
                addedList.add(selectCourse);
            }
            addedAdapter.notifyDataSetChanged();
            addedListView.setAdapter(addedAdapter);
        });

        addedListView.setOnItemClickListener((adapterView, view, position, l) -> {
            Course selectCourse = (Course) (addedListView.getItemAtPosition(position));

            addedList.remove(selectCourse);
            addedAdapter.notifyDataSetChanged();
            addedListView.setAdapter(addedAdapter);
        });
    }


    /**
     * Creates a list of courses of one college
     * @param college college name such as comp
     * @return list of courses
     * @author Yanghe (u7533843), Lana (u7103031) and Steven (u7108792)
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
            String jsonString = new String(buffer, StandardCharsets.UTF_8);
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
                StringBuilder convener = new StringBuilder();
                JSONArray convenerArray = courseJson.getJSONArray("convener");
                for (int j = 0; j < convenerArray.length(); j++) {
                    if (j==convenerArray.length()-1) {
                        convener.append(convenerArray.getString(j));}
                    else {convener.append(convenerArray.getString(j)).append(", ");}
                }
                if (convener.length() == 0) {
                    convener.append("No convener");
                }
                Course newCourse = new Course(courseCode, courseName, studentTypeOfCourse, assessment, convener.toString());
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
     * @author Lana (u7103031) and Steven (u7108792) 
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
     * @author Yanghe (u7533843)
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
     * @author Lana (u7103031) 
     */
    public ArrayList<Course> getCourses() {
        // reading json based on student type
        String path = "post_courses_data.json";
        if(user.getIsUndergrad()){
            path = "under_courses_data.json";
        }
        //initialises a list of Courses
        ArrayList<Course> courses = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open(path), StandardCharsets.UTF_8))) {
            String line;
            boolean ass = false; // boolean to indicate if we are searching for assessments
            boolean conv = false;  // boolean to indicate if we are searching for conveners
            // initialising parameters to create a new Course
            Course c = new Course();
            ArrayList<String> assessments = new ArrayList<>();
            StringBuilder conveners = new StringBuilder();

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
                    c.setConvener(conveners.toString());
                    conveners = new StringBuilder();
                    if (c.getCourseCode()!=null) {
                        courses.add(c);}
                    c = new Course();
                }
                else if (conv) {
                    String[] s = line.split("\"");
                    conveners.append(s[1]);
                    if (line.contains(",")) {
                        conveners.append(", ");
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

