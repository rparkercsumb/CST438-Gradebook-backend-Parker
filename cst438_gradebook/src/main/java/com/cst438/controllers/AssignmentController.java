package com.cst438.controllers;

import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.domain.Assignment;
import com.cst438.domain.AssignmentListDTO;
import com.cst438.domain.AssignmentGrade;
import com.cst438.domain.AssignmentGradeRepository;
import com.cst438.domain.AssignmentRepository;
import com.cst438.domain.Course;
import com.cst438.domain.CourseDTOG;
import com.cst438.domain.CourseRepository;
import com.cst438.domain.Enrollment;
import com.cst438.domain.GradebookDTO;
import com.cst438.services.RegistrationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.domain.Course;
import com.cst438.domain.CourseRepository;
import com.cst438.domain.Enrollment;
import com.cst438.domain.EnrollmentDTO;
import com.cst438.domain.EnrollmentRepository;

@RestController
public class AssignmentController
{
   @Autowired
   AssignmentRepository assignmentRepository;
   
   @Autowired
   CourseRepository courseRepository;
   
   //As an instructor for a course , I can add a new assignment for my course.  The assignment has a name and a due date.
   @PostMapping("/assignment")
   @Transactional
   public AssignmentListDTO.AssignmentDTO addAssignment( @RequestBody CourseDTOG courseDTOG )
   {
      //TODO: change to not be hard coded
      String assignment_name = "test assignment";
      String dateStr = "2023-06-15";
      Date assignment_due_date = Date.valueOf(dateStr);
      
      Course course  = courseRepository.findById(courseDTOG.course_id).orElse(null);
      
      if (assignment_name != null && assignment_due_date != null && course != null)
      {
         Assignment assignment = new Assignment();
         assignment.setName(assignment_name);
         assignment.setDueDate(assignment_due_date);
         assignment.setCourse(course);
         assignment.setNeedsGrading(1);
         
         //TODO assignment made, but needs to be added to the database? and return correct AssignmentDTO
         
         return new AssignmentListDTO.AssignmentDTO(assignment.getId(), course.getCourse_id(), assignment_name, dateStr, course.getTitle());
      }
      else
      {
         throw  new ResponseStatusException( HttpStatus.BAD_REQUEST, "Course_id invalid or assignment name or due date not found.  "+courseDTOG.course_id);
      }
   }
   // due_date / name / needs_grading / course_id
   
   
   
   
   //As an instructor, I can change the name of the assignment for my course.
   //TODO
   
   //As an instructor, I can delete an assignment  for my course (only if there are no grades for the assignment).
   //TODO

   
   private AssignmentListDTO.AssignmentDTO createAssignmentDTO(Assignment a)
   {
      AssignmentListDTO.AssignmentDTO AssignmentDTO = new AssignmentListDTO.AssignmentDTO();
      Course c = a.getCourse();
      AssignmentDTO.id =a.getEnrollment_id();
      AssignmentDTO.building = c.getBuilding();
      AssignmentDTO.course_id = c.getCourse_id();
      AssignmentDTO.endDate = c.getEnd().toString();
      AssignmentDTO.instructor = c.getInstructor();
      AssignmentDTO.room = c.getRoom();
      AssignmentDTO.section = c.getSection();
      AssignmentDTO.startDate = c.getStart().toString();
      AssignmentDTO.times = c.getTimes();
      AssignmentDTO.title = c.getTitle();
      AssignmentDTO.grade = a.getCourseGrade();
      return AssignmentDTO;
   }
}

