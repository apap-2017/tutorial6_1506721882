package com.example.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.CourseModel;
import com.example.model.StudentModel;
import com.example.service.StudentService;

@Controller
public class StudentController
{
    @Autowired
    StudentService studentDAO;


    @RequestMapping("/")
    public String index ()
    {
        return "index";
    }


    @RequestMapping("/student/add")
    public String add ()
    {
        return "form-add";
    }
    

    @RequestMapping("/student/add/submit")
    public String addSubmit (
            @RequestParam(value = "npm", required = false) String npm,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "gpa", required = false) double gpa)
    {
    	
        StudentModel student = new StudentModel (npm, name, gpa, null);
        studentDAO.addStudent (student);

        return "success-add";
    }


    @RequestMapping("/student/view")
    public String view (Model model,
            @RequestParam(value = "npm", required = false) String npm)
    {
        StudentModel student = studentDAO.selectStudent (npm);

        if (student != null) {
            model.addAttribute ("student", student);
            return "view";
        } else {
            model.addAttribute ("npm", npm);
            return "not-found";
        }
    }


    @RequestMapping("/student/view/{npm}")
    public String viewPath (Model model,
            @PathVariable(value = "npm") String npm)
    {
        StudentModel student = studentDAO.selectStudent (npm);

        if (student != null) {
            model.addAttribute ("student", student);
            return "view";
        } else {
            model.addAttribute ("npm", npm);
            return "not-found";
        }
    }


    @RequestMapping("/student/viewall")
    public String view (Model model)
    {
        List<StudentModel> students = studentDAO.selectAllStudents ();
        model.addAttribute ("student", students);

        return "viewall";
    }

    @RequestMapping("/student/delete/{npm}")
    public String delete (Model model, @PathVariable(value = "npm") String npm)
    {
       // studentDAO.deleteStudent(npm);
        StudentModel students = studentDAO.selectStudent(npm); 
		if (students == null){
			return "not-found";
		} else {
			studentDAO.deleteStudent(npm);
			return "delete";
		}
    }
    
    @RequestMapping("/student/update/{npm}")
    public String update (Model model, @PathVariable(value = "npm") String npm){
    	 StudentModel students = studentDAO.selectStudent(npm); 
    	 model.addAttribute("students", students);
  		
    	 if (students == null){
    		 	return "not-found";
 		} else {	
 			return "form-update";
 		}
    }
    
    @RequestMapping("/student/update/submit")
   public String updateSubmit (@RequestParam(value = "npm", required = false) String npm,
		   @RequestParam(value="name", required = false) String name,
		   @RequestParam(value="gpa", required=false) double gpa) {
		
    	StudentModel students = new StudentModel(npm, name, gpa, null);
    	
    	studentDAO.updateStudent(students);
    	return "success-update";
    } 
    
    @GetMapping("/student/update-object/{npm}")
    public String updateObjectForm(Model model,  @PathVariable(value = "npm") String npm) {
    	StudentModel students = studentDAO.selectStudent(npm);
		model.addAttribute("students", students);    	
		if (students == null){
		 	return "not-found";
		} else {	
			return "form-update-object";
		}
		
    }
    
    @PostMapping("/student/update-project/submit") 
    public String updateObjectSubmit(@ModelAttribute StudentModel studentmodel ) {
    	studentDAO.updateStudent(studentmodel);
    	return "success-update";
    	
    }    
    
    @RequestMapping("/course/view/{id}")
    public String viewID (Model model, @PathVariable(value = "id") String id_course) {
		CourseModel course = studentDAO.viewID(id_course);
		
		if (course != null) {
			/*if(course.getStudents().size()== 0) {
				return "not-found-id";
			}*/
            model.addAttribute ("course", course);
            return "view-id";
        } else {
           model.addAttribute ("id_course", id_course);
           // model.addAttribute ("course", course);
            return "not-found-id";
        }    	
    }
 }



