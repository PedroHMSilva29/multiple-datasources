package youtube.java.puzzle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import youtube.java.puzzle.model.Response;
import youtube.java.puzzle.student.repository.StudentRepository;

@RestController
public class CombineController {

    //@Autowired
    //CollegeRepository collegeRepository;
    @Autowired
    StudentRepository studentRepository;

    @GetMapping(value = "/")
    public Response getResponse(){
        Response response = new Response();
        response.setStudentEntities(studentRepository.findAll());
        //response.setCollegeEntities(collegeRepository.findAll());
        return response;
    }
}
